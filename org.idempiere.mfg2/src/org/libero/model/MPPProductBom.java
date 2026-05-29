package org.libero.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.eevolution.model.MPPProductBOM;

public class MPPProductBom extends MPPProductBOM{
	private static int Qty;
	public MPPProductBom(MPPProductBOM copy) {
		super(copy);
		// TODO Auto-generated constructor stub
	}

	public MPPProductBom(Properties ctx, int PP_Product_BOM_ID, String trxName, String... virtualColumns) {
		super(ctx, PP_Product_BOM_ID, trxName, virtualColumns);
		// TODO Auto-generated constructor stub
	}

	public MPPProductBom(Properties ctx, int PP_Product_BOM_ID, String trxName) {
		super(ctx, PP_Product_BOM_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MPPProductBom(Properties ctx, MPPProductBOM copy, String trxName) {
		super(ctx, copy, trxName);
		// TODO Auto-generated constructor stub
	}

	public MPPProductBom(Properties ctx, MPPProductBOM copy) {
		super(ctx, copy);
		// TODO Auto-generated constructor stub
	}

	public MPPProductBom(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public static int getQty() {
		return Qty;
	}

	public static void setQty(int qty) {
		Qty = qty;
	}
	
}
