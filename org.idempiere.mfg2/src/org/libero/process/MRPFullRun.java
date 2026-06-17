/******************************************************************************
 * Product: iDempiere Manufacturing                                            *
 *****************************************************************************/
package org.libero.process;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.compiere.util.Util;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductPlanning;
import org.libero.model.MPPMRP;

/**
 * Runs the complete MRP flow from one process and reports why selected sales
 * order lines did or did not create manufacturing orders.
 */
public class MRPFullRun extends SvrProcess
{
	private int p_AD_Org_ID;
	private int p_S_Resource_ID;
	private int p_M_Warehouse_ID;
	private boolean p_IsRequiredDRP;
	private boolean p_DeleteMRP;
	private boolean p_RecalculateLowLevel = true;
	private boolean p_CreateMRPRecords = true;
	private boolean p_CalculateMaterialPlan = true;
	private String p_C_Order_ID;
	private Timestamp p_DatePromisedFrom;
	private Timestamp p_DatePromisedTo;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParameter())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				continue;

			if ("AD_Org_ID".equals(name))
				p_AD_Org_ID = para.getParameterAsInt();
			else if ("S_Resource_ID".equals(name))
				p_S_Resource_ID = para.getParameterAsInt();
			else if ("M_Warehouse_ID".equals(name))
				p_M_Warehouse_ID = para.getParameterAsInt();
			else if ("IsRequiredDRP".equals(name))
				p_IsRequiredDRP = para.getParameterAsBoolean();
			else if ("DeleteMRP".equals(name))
				p_DeleteMRP = para.getParameterAsBoolean();
			else if ("RecalculateLowLevel".equals(name))
				p_RecalculateLowLevel = para.getParameterAsBoolean();
			else if ("CreateMRPRecords".equals(name))
				p_CreateMRPRecords = para.getParameterAsBoolean();
			else if ("CalculateMaterialPlan".equals(name))
				p_CalculateMaterialPlan = para.getParameterAsBoolean();
			else if ("C_Order_ID".equals(name))
				p_C_Order_ID = para.getParameter().toString();
			else if ("DatePromisedFrom".equals(name))
				p_DatePromisedFrom = (Timestamp) para.getParameter();
			else if ("DatePromisedTo".equals(name))
				p_DatePromisedTo = (Timestamp) para.getParameter();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		List<Integer> orderLineIds = getSelectedOrderLineIds();
		Set<Integer> beforeOrderIds = getManufacturingOrderIds(orderLineIds);
		List<String> phaseResults = new ArrayList<>();

		if (p_RecalculateLowLevel)
			phaseResults.add(runChildProcess("Calculate Low Level", new CalculateLowLevel(), new ProcessInfoParameter[0]));

		if (p_CreateMRPRecords)
		{
			phaseResults.add(runChildProcess("Create MRP Records", new MRPUpdate(), getMRPUpdateParameters()));
			propagateSalesOrderLineToGeneratedMRP();
		}

		if (p_CalculateMaterialPlan)
			phaseResults.add(runChildProcess("Calculate Material Plan", new MRP(), getMRPParameters()));

		return buildSummary(phaseResults, orderLineIds, beforeOrderIds);
	}

	private String runChildProcess(String title, SvrProcess process, ProcessInfoParameter[] parameters)
	{
		ProcessInfo pi = new ProcessInfo(title, 0);
		pi.setAD_Client_ID(getAD_Client_ID());
		pi.setAD_User_ID(getAD_User_ID());
		pi.setParameter(parameters);
		boolean ok = process.startProcess(getCtx(), pi, (Trx)null);
		String summary = Util.isEmpty(pi.getSummary(), true) ? (ok ? "OK" : "Error") : pi.getSummary();
		if (!ok)
			throw new AdempiereException(title + ": " + summary);
		return title + ": " + summary;
	}

	private ProcessInfoParameter[] getMRPUpdateParameters()
	{
		List<ProcessInfoParameter> parameters = new ArrayList<>();
		addParameter(parameters, "AD_Org_ID", p_AD_Org_ID);
		addParameter(parameters, "S_Resource_ID", p_S_Resource_ID);
		addParameter(parameters, "M_Warehouse_ID", p_M_Warehouse_ID);
		return parameters.toArray(new ProcessInfoParameter[0]);
	}

	private ProcessInfoParameter[] getMRPParameters()
	{
		List<ProcessInfoParameter> parameters = new ArrayList<>();
		addParameter(parameters, "DeleteMRP", p_DeleteMRP);
		addParameter(parameters, "AD_Org_ID", p_AD_Org_ID);
		addParameter(parameters, "S_Resource_ID", p_S_Resource_ID);
		addParameter(parameters, "M_Warehouse_ID", p_M_Warehouse_ID);
		addParameter(parameters, "IsRequiredDRP", p_IsRequiredDRP);
		if (!Util.isEmpty(p_C_Order_ID, true))
			addParameter(parameters, "C_Order_ID", p_C_Order_ID);
		if (p_DatePromisedFrom != null)
			addParameter(parameters, "DatePromisedFrom", p_DatePromisedFrom);
		if (p_DatePromisedTo != null)
			addParameter(parameters, "DatePromisedTo", p_DatePromisedTo);
		return parameters.toArray(new ProcessInfoParameter[0]);
	}

	private void addParameter(List<ProcessInfoParameter> parameters, String name, Object value)
	{
		if (value instanceof Integer && ((Integer)value).intValue() <= 0)
			return;
		parameters.add(new ProcessInfoParameter(name, value, null, null, null));
	}

	private List<Integer> getSelectedOrderLineIds()
	{
		List<Integer> orderIds = getSelectedOrderIds();
		if (orderIds.isEmpty())
			return new ArrayList<>();

		String ids = orderIds.stream().map(String::valueOf).collect(Collectors.joining(","));
		return getFirstColumnIds(
				"SELECT C_OrderLine_ID FROM C_OrderLine WHERE C_Order_ID IN (" + ids + ") ORDER BY C_Order_ID, Line");
	}

	private List<Integer> getSelectedOrderIds()
	{
		List<Integer> ids = new ArrayList<>();
		if (Util.isEmpty(p_C_Order_ID, true))
			return ids;

		for (String id : p_C_Order_ID.split(","))
		{
			String trimmed = id.trim();
			if (!trimmed.isEmpty())
				ids.add(Integer.valueOf(trimmed));
		}
		return ids;
	}

	private Set<Integer> getManufacturingOrderIds(List<Integer> orderLineIds)
	{
		Set<Integer> ids = new HashSet<>();
		for (Integer orderLineId : orderLineIds)
		{
			List<Integer> lineOrderIds = getFirstColumnIds(
					"SELECT PP_Order_ID FROM PP_Order WHERE C_OrderLine_ID=?", orderLineId);
			ids.addAll(lineOrderIds);
		}
		return ids;
	}

	private void propagateSalesOrderLineToGeneratedMRP() throws SQLException
	{
		String sql = "UPDATE PP_MRP mrp "
				+ "SET C_Order_ID=ol.C_Order_ID, C_OrderLine_ID=o.C_OrderLine_ID "
				+ "FROM PP_Order o "
				+ "INNER JOIN C_OrderLine ol ON (ol.C_OrderLine_ID=o.C_OrderLine_ID) "
				+ "WHERE mrp.PP_Order_ID=o.PP_Order_ID "
				+ "AND mrp.C_OrderLine_ID IS NULL "
				+ "AND o.C_OrderLine_ID IS NOT NULL "
				+ "AND mrp.AD_Client_ID=?";
		DB.executeUpdateEx(sql, new Object[] { getAD_Client_ID() }, get_TrxName());
		commitEx();
	}

	private String buildSummary(List<String> phaseResults, List<Integer> orderLineIds, Set<Integer> beforeOrderIds)
	{
		StringBuilder msg = new StringBuilder("MRP Full Run finalizado");
		msg.append("<br><br>Fases:");
		for (String phaseResult : phaseResults)
			msg.append("<br>- ").append(phaseResult);

		if (orderLineIds.isEmpty())
		{
			msg.append("<br><br>No se informo C_Order_ID; se ejecuto como MRP general con los filtros de organizacion/almacen/planta/fecha.");
			return msg.toString();
		}

		Set<Integer> afterOrderIds = getManufacturingOrderIds(orderLineIds);
		Set<Integer> createdOrderIds = new HashSet<>(afterOrderIds);
		createdOrderIds.removeAll(beforeOrderIds);

		msg.append("<br><br>Resumen orden filtrada:");
		msg.append("<br>Lineas evaluadas: ").append(orderLineIds.size());
		msg.append("<br>Ordenes de manufactura nuevas: ").append(createdOrderIds.size());
		msg.append("<br>Ordenes de manufactura asociadas total: ").append(afterOrderIds.size());
		msg.append("<br><br>Detalle:");

		for (Integer orderLineId : orderLineIds)
			appendLineSummary(msg, orderLineId, beforeOrderIds);

		return msg.toString();
	}

	private void appendLineSummary(StringBuilder msg, int C_OrderLine_ID, Set<Integer> beforeOrderIds)
	{
		MOrderLine line = new MOrderLine(getCtx(), C_OrderLine_ID, get_TrxName());
		MOrder order = line.getParent();
		MProduct product = line.getM_Product_ID() > 0 ? MProduct.get(getCtx(), line.getM_Product_ID()) : null;
		List<Integer> orderIds = getFirstColumnIds(
				"SELECT PP_Order_ID FROM PP_Order WHERE C_OrderLine_ID=? ORDER BY PP_Order_ID", C_OrderLine_ID);
		List<Integer> newOrderIds = orderIds.stream().filter(id -> !beforeOrderIds.contains(id)).collect(Collectors.toList());

		msg.append("<br>- ").append(order.getDocumentNo()).append(" / Linea ").append(line.getLine());
		if (product != null)
			msg.append(" / ").append(product.getValue());

		if (!newOrderIds.isEmpty())
		{
			msg.append(": MO creada(s) ").append(newOrderIds);
			return;
		}

		if (!orderIds.isEmpty())
		{
			msg.append(": no se creo nueva MO porque ya existe PP_Order asociada ").append(orderIds);
			return;
		}

		msg.append(": no se creo MO. Motivo probable: ").append(getNoOrderReason(line, order, product));
	}

	private String getNoOrderReason(MOrderLine line, MOrder order, MProduct product)
	{
		if (line.getM_Product_ID() <= 0)
			return "la linea no tiene producto";
		if (!MOrder.DOCSTATUS_InProgress.equals(order.getDocStatus()) && !MOrder.DOCSTATUS_Completed.equals(order.getDocStatus()))
			return "la orden de venta no esta en estado IP/CO";
		if (line.getQtyOrdered().subtract(line.getQtyDelivered()).signum() == 0)
			return "la linea no tiene cantidad pendiente";
		if (p_M_Warehouse_ID > 0 && line.getM_Warehouse_ID() != p_M_Warehouse_ID)
			return "el almacen de la linea no coincide con el filtro";
		if (p_DatePromisedFrom != null && line.getDatePromised() != null && line.getDatePromised().compareTo(p_DatePromisedFrom) < 0)
			return "la fecha prometida es menor al filtro desde";
		if (p_DatePromisedTo != null && line.getDatePromised() != null && line.getDatePromised().compareTo(p_DatePromisedTo) > 0)
			return "la fecha prometida es mayor al filtro hasta";
		if (product == null)
			return "no se pudo cargar el producto";
		if (product.isPurchased())
			return "el producto esta marcado como comprado; MRP genera requisicion, no MO";
		if (!product.isBOM())
			return "el producto no esta marcado como BOM";

		int plantId = p_S_Resource_ID > 0 ? p_S_Resource_ID : MPPProductPlanning.getPlantForWarehouse(line.getM_Warehouse_ID());
		MPPProductPlanning planning = MPPProductPlanning.find(getCtx(), line.getAD_Org_ID(), line.getM_Warehouse_ID(),
				plantId, line.getM_Product_ID(), get_TrxName());
		if (planning == null)
			return "no existe PP_Product_Planning para org/almacen/planta/producto";
		if (!planning.isCreatePlan())
			return "PP_Product_Planning tiene Create Plan desactivado";
		if (planning.getPP_Product_BOM_ID() <= 0 && MPPProductBOM.getBOMSearchKey(product) <= 0)
			return "no se encontro BOM de manufactura";
		if (!hasMRPDemand(line.getC_OrderLine_ID()))
			return "no se encontro demanda PP_MRP para la linea";

		BigDecimal onHand = MPPMRP.getQtyOnHand(getCtx(), line.getM_Warehouse_ID(), line.getM_Product_ID(), get_TrxName());
		return "la demanda pudo estar cubierta por inventario/suministro existente o fue acumulada por politica de planificacion. QtyOnHand=" + onHand;
	}

	private boolean hasMRPDemand(int C_OrderLine_ID)
	{
		int count = DB.getSQLValueEx(get_TrxName(),
				"SELECT COUNT(*) FROM PP_MRP WHERE C_OrderLine_ID=? AND TypeMRP=? AND OrderType=?",
				C_OrderLine_ID, MPPMRP.TYPEMRP_Demand, MPPMRP.ORDERTYPE_SalesOrder);
		return count > 0;
	}

	private List<Integer> getFirstColumnIds(String sql, Object... params)
	{
		List<Integer> ids = new ArrayList<>();
		List<List<Object>> rows = DB.getSQLArrayObjectsEx(get_TrxName(), sql, params);
		if (rows == null)
			return ids;

		for (List<Object> row : rows)
		{
			if (!row.isEmpty() && row.get(0) != null)
				ids.add(((Number)row.get(0)).intValue());
		}
		return ids;
	}
}
