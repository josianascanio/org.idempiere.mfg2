/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.libero.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for CDS_QM_SpecificationResult
 *  @author iDempiere (generated) 
 *  @version Release 10 - $Id$ */
@org.adempiere.base.Model(table="CDS_QM_SpecificationResult")
public class X_CDS_QM_SpecificationResult extends PO implements I_CDS_QM_SpecificationResult, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250110L;

    /** Standard Constructor */
    public X_CDS_QM_SpecificationResult (Properties ctx, int CDS_QM_SpecificationResult_ID, String trxName)
    {
      super (ctx, CDS_QM_SpecificationResult_ID, trxName);
      /** if (CDS_QM_SpecificationResult_ID == 0)
        {
			setCDS_QM_SpecificationResult_ID (0);
			setM_AttributeSet_ID (0);
			setM_Product_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_CDS_QM_SpecificationResult (Properties ctx, int CDS_QM_SpecificationResult_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, CDS_QM_SpecificationResult_ID, trxName, virtualColumns);
      /** if (CDS_QM_SpecificationResult_ID == 0)
        {
			setCDS_QM_SpecificationResult_ID (0);
			setM_AttributeSet_ID (0);
			setM_Product_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CDS_QM_SpecificationResult (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_CDS_QM_SpecificationResult[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Workflow getAD_Workflow() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Workflow)MTable.get(getCtx(), org.compiere.model.I_AD_Workflow.Table_ID)
			.getPO(getAD_Workflow_ID(), get_TrxName());
	}

	/** Set Workflow.
		@param AD_Workflow_ID Workflow or combination of tasks
	*/
	public void setAD_Workflow_ID (int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1)
			set_Value (COLUMNNAME_AD_Workflow_ID, null);
		else
			set_Value (COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
	}

	/** Get Workflow.
		@return Workflow or combination of tasks
	  */
	public int getAD_Workflow_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Workflow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Specification Result.
		@param CDS_QM_SpecificationResult_ID Specification Result
	*/
	public void setCDS_QM_SpecificationResult_ID (int CDS_QM_SpecificationResult_ID)
	{
		if (CDS_QM_SpecificationResult_ID < 1)
			set_ValueNoCheck (COLUMNNAME_CDS_QM_SpecificationResult_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_CDS_QM_SpecificationResult_ID, Integer.valueOf(CDS_QM_SpecificationResult_ID));
	}

	/** Get Specification Result.
		@return Specification Result	  */
	public int getCDS_QM_SpecificationResult_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CDS_QM_SpecificationResult_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CDS_QM_SpecificationResult_UU.
		@param CDS_QM_SpecificationResult_UU CDS_QM_SpecificationResult_UU
	*/
	public void setCDS_QM_SpecificationResult_UU (String CDS_QM_SpecificationResult_UU)
	{
		set_Value (COLUMNNAME_CDS_QM_SpecificationResult_UU, CDS_QM_SpecificationResult_UU);
	}

	/** Get CDS_QM_SpecificationResult_UU.
		@return CDS_QM_SpecificationResult_UU	  */
	public String getCDS_QM_SpecificationResult_UU()
	{
		return (String)get_Value(COLUMNNAME_CDS_QM_SpecificationResult_UU);
	}

	/** Set Description.
		@param Description Optional short description of the record
	*/
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription()
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	public org.compiere.model.I_M_AttributeSet getM_AttributeSet() throws RuntimeException
	{
		return (org.compiere.model.I_M_AttributeSet)MTable.get(getCtx(), org.compiere.model.I_M_AttributeSet.Table_ID)
			.getPO(getM_AttributeSet_ID(), get_TrxName());
	}

	/** Set Attribute Set.
		@param M_AttributeSet_ID Product Attribute Set
	*/
	public void setM_AttributeSet_ID (int M_AttributeSet_ID)
	{
		if (M_AttributeSet_ID < 0)
			set_Value (COLUMNNAME_M_AttributeSet_ID, null);
		else
			set_Value (COLUMNNAME_M_AttributeSet_ID, Integer.valueOf(M_AttributeSet_ID));
	}

	/** Get Attribute Set.
		@return Product Attribute Set
	  */
	public int getM_AttributeSet_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
	{
		return (I_M_AttributeSetInstance)MTable.get(getCtx(), I_M_AttributeSetInstance.Table_ID)
			.getPO(getM_AttributeSetInstance_ID(), get_TrxName());
	}

	/** Set Attribute Set Instance.
		@param M_AttributeSetInstance_ID Product Attribute Set Instance
	*/
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0)
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Attribute Set Instance.
		@return Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_ID)
			.getPO(getM_Product_ID(), get_TrxName());
	}

	/** Set Product.
		@param M_Product_ID Product, Service, Item
	*/
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1)
			set_Value (COLUMNNAME_M_Product_ID, null);
		else
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name Alphanumeric identifier of the entity
	*/
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName()
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException
	{
		return (org.eevolution.model.I_PP_Order)MTable.get(getCtx(), org.eevolution.model.I_PP_Order.Table_ID)
			.getPO(getPP_Order_ID(), get_TrxName());
	}

	/** Set Manufacturing Order.
		@param PP_Order_ID Manufacturing Order
	*/
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1)
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	/** Get Manufacturing Order.
		@return Manufacturing Order
	  */
	public int getPP_Order_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_PP_Order_Node getPP_Order_Node() throws RuntimeException
	{
		return (org.eevolution.model.I_PP_Order_Node)MTable.get(getCtx(), org.eevolution.model.I_PP_Order_Node.Table_ID)
			.getPO(getPP_Order_Node_ID(), get_TrxName());
	}

	/** Set Manufacturing Order Activity.
		@param PP_Order_Node_ID Workflow Node (activity), step or process
	*/
	public void setPP_Order_Node_ID (int PP_Order_Node_ID)
	{
		if (PP_Order_Node_ID < 1)
			set_ValueNoCheck (COLUMNNAME_PP_Order_Node_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_PP_Order_Node_ID, Integer.valueOf(PP_Order_Node_ID));
	}

	/** Get Manufacturing Order Activity.
		@return Workflow Node (activity), step or process
	  */
	public int getPP_Order_Node_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_Node_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM() throws RuntimeException
	{
		return (org.eevolution.model.I_PP_Product_BOM)MTable.get(getCtx(), org.eevolution.model.I_PP_Product_BOM.Table_ID)
			.getPO(getPP_Product_BOM_ID(), get_TrxName());
	}

	/** Set BOM &amp; Formula.
		@param PP_Product_BOM_ID BOM &amp; Formula
	*/
	public void setPP_Product_BOM_ID (int PP_Product_BOM_ID)
	{
		if (PP_Product_BOM_ID < 1)
			set_Value (COLUMNNAME_PP_Product_BOM_ID, null);
		else
			set_Value (COLUMNNAME_PP_Product_BOM_ID, Integer.valueOf(PP_Product_BOM_ID));
	}

	/** Get BOM &amp; Formula.
		@return BOM &amp; Formula
	  */
	public int getPP_Product_BOM_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Product_BOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Valid from.
		@param ValidFrom Valid from including this date (first day)
	*/
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom()
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo Valid to including this date (last day)
	*/
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo()
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}

	/** Set Search Key.
		@param Value Search key for the record in the format required - must be unique
	*/
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue()
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}