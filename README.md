# org.idempiere.mfg2

- Copyright: 2026 https://www.casadelsoftware.com
- Repository: https://github.com/josianascanio/org.idempiere.mfg2
- License: GPL 2

## Description

`org.idempiere.mfg2` provides manufacturing, material planning, capacity planning, distribution order, BOM, routing, quality, costing, and production accounting functionality for iDempiere. The plugin is based on the Libero Manufacturing work from ADempiere/e-Evolution and adapts it as an iDempiere OSGi bundle with process registrations, web forms, model factories, document accounting handlers, callouts, and MRP event handling.

## Contributors

- 2019 Peter Shepetko <pshepetko@gmail.com>.
- 2021 Logilite Technologies LLP.

## Components

- iDempiere Plugin [org.idempiere.mfg2](org.idempiere.mfg2)

## Prerequisites

- Java 17, commands `java` and `javac`.
- iDempiere 12.

## Features/Documentation

### Source Structure

```text
org.idempiere.mfg2/src
|-- org
|   |-- adempiere
|   |   `-- model
|   |       `-- engines
|   |           |-- CostDimension.java
|   |           |-- CostEngine.java
|   |           |-- CostEngineFactory.java
|   |           |-- IDocumentLine.java
|   |           |-- IInventoryAllocation.java
|   |           `-- StorageEngine.java
|   |-- compiere
|   |   |-- acct
|   |   |   |-- DocLine_CostCollector.java
|   |   |   |-- Doc_DDOrder.java
|   |   |   |-- Doc_PPCostCollector.java
|   |   |   `-- Doc_PPOrder.java
|   |   `-- process
|   |       `-- BOMVerify.java
|   |-- idempiere
|   |   `-- component
|   |       |-- MFG_DocFactory.java
|   |       |-- MFG_ModelFactory.java
|   |       `-- MFG_Validator.java
|   `-- libero
|       |-- bom
|       |   `-- drop
|       |       |-- IRendererListener.java
|       |       |-- ISupportRadioNode.java
|       |       |-- ProductBOMRendererListener.java
|       |       |-- ProductBOMTreeNode.java
|       |       |-- SupportRadioTreeModel.java
|       |       `-- SupportRadioTreeitemRenderer.java
|       |-- callouts
|       |   |-- CalloutBOM.java
|       |   |-- CalloutCostCollector.java
|       |   |-- CalloutDistributionOrder.java
|       |   |-- CalloutOrder.java
|       |   |-- Callout_DD_OrderLine.java
|       |   |-- Callout_PP_Cost_Collector.java
|       |   |-- Callout_PP_Order.java
|       |   |-- Callout_PP_Order_BOMLine.java
|       |   |-- Callout_PP_Product_BOM.java
|       |   `-- Callout_PP_Product_BOMLine.java
|       |-- exceptions
|       |   |-- ActivityProcessedException.java
|       |   |-- BOMExpiredException.java
|       |   |-- CRPException.java
|       |   |-- NoBPartnerLinkedforOrgException.java
|       |   |-- NoPlantForWarehouseException.java
|       |   `-- RoutingExpiredException.java
|       |-- form
|       |   |-- CRP.java
|       |   |-- CRPDetail.java
|       |   |-- MRPDetailed.java
|       |   |-- OrderReceiptIssue.java
|       |   |-- TreeBOM.java
|       |   |-- WBOMDropConfigurator.java
|       |   |-- WCRP.java
|       |   |-- WCRPDetail.java
|       |   |-- WMRPDetailed.java
|       |   |-- WOrderReceiptIssue.java
|       |   |-- WTreeBOM.java
|       |   |-- crp
|       |   |   |-- CRPDatasetFactory.java
|       |   |   |-- CRPModel.java
|       |   |   `-- DiagramTreeCellRenderer.java
|       |   `-- tree
|       |       |-- CachableTreeCellRenderer.java
|       |       `-- MapTreeCellRenderer.java
|       |-- infowindow
|       |   |-- CalculateMaterialPlan.java
|       |   `-- OrderReceiptIssue.java
|       |-- model
|       |   |-- I_CDS_QM_SpecificationResult.java
|       |   |-- I_PP_Order_BOMLineMA.java
|       |   |-- LiberoMovementLine.java
|       |   |-- MCostDetail.java
|       |   |-- MDDNetworkDistribution.java
|       |   |-- MDDNetworkDistributionLine.java
|       |   |-- MPPCostCollector.java
|       |   |-- MPPCostCollectorMA.java
|       |   |-- MPPMRP.java
|       |   |-- MPPOrder.java
|       |   |-- MPPOrderBOM.java
|       |   |-- MPPOrderBOMLine.java
|       |   |-- MPPOrderBOMLineMA.java
|       |   |-- MPPOrderCost.java
|       |   |-- MPPOrderNode.java
|       |   |-- MPPOrderNodeAsset.java
|       |   |-- MPPOrderNodeNext.java
|       |   |-- MPPOrderNodeProduct.java
|       |   |-- MPPOrderWorkflow.java
|       |   |-- MPPProductBom.java
|       |   |-- MPPWFNodeAsset.java
|       |   |-- MPPWFNodeProduct.java
|       |   |-- MQMSpecification.java
|       |   |-- MQMSpecificationLine.java
|       |   |-- MRequisition.java
|       |   |-- RoutingService.java
|       |   |-- RoutingServiceFactory.java
|       |   |-- X_CDS_QM_SpecificationResult.java
|       |   |-- X_PP_Order_BOMLineMA.java
|       |   |-- impl
|       |   |   `-- DefaultRoutingServiceImpl.java
|       |   |-- reasoner
|       |   |   |-- CRPReasoner.java
|       |   |   `-- StorageReasoner.java
|       |   `-- wrapper
|       |       |-- AbstractPOWrapper.java
|       |       |-- BOMLineWrapper.java
|       |       `-- BOMWrapper.java
|       |-- msg
|       |   `-- HTMLMessenger.java
|       |-- process
|       |   |-- CRP.java
|       |   |-- CRPSummary.java
|       |   |-- CalculateLowLevel.java
|       |   |-- CompletePrintOrder.java
|       |   |-- ComponentChange.java
|       |   |-- CopyFromBOM.java
|       |   |-- CopyPriceToStandard.java
|       |   |-- CopyWorkflowStructure.java
|       |   |-- CreateCostElement.java
|       |   |-- CreateDocType.java
|       |   |-- CreateProductPlanning.java
|       |   |-- DistributionRunOrders.java
|       |   |-- FixPaymentCashLine.java
|       |   |-- ImportProductPlanning.java
|       |   |-- MRP.java
|       |   |-- MRPFullRun.java
|       |   |-- MRPUpdate.java
|       |   |-- MovementGenerate.java
|       |   |-- PP_Product_BOM_Check.java
|       |   |-- PrintBOM.java
|       |   |-- ProcessInfoHandler.java
|       |   |-- RollupBillOfMaterial.java
|       |   |-- RollupWorkflow.java
|       |   `-- eam
|       |       |-- AddMeter4eAM.java
|       |       |-- CreateForecastLine4eAM.java
|       |       `-- CreateMOFromForecastLine4eAM.java
|       |-- report
|       |   `-- CostBillOfMaterial.java
|       |-- tables
|       |   |-- I_DD_NetworkDistribution.java
|       |   |-- I_DD_NetworkDistributionLine.java
|       |   |-- I_DD_Order.java
|       |   |-- I_DD_OrderLine.java
|       |   |-- I_M_Product_Acct.java
|       |   |-- I_PP_BatchCharge.java
|       |   |-- I_PP_Cost_Collector.java
|       |   |-- I_PP_Cost_CollectorMA.java
|       |   |-- I_PP_MRP.java
|       |   |-- I_PP_Order.java
|       |   |-- I_PP_Order_BOM.java
|       |   |-- I_PP_Order_BOMLine.java
|       |   |-- I_PP_Order_BatchCharge.java
|       |   |-- I_PP_Order_Cost.java
|       |   |-- I_PP_Order_Node.java
|       |   |-- I_PP_Order_NodeNext.java
|       |   |-- I_PP_Order_Node_Asset.java
|       |   |-- I_PP_Order_Node_Product.java
|       |   |-- I_PP_Order_Workflow.java
|       |   |-- I_PP_WF_Node_Asset.java
|       |   |-- I_PP_WF_Node_Product.java
|       |   |-- I_QM_Specification.java
|       |   |-- I_QM_SpecificationLine.java
|       |   |-- I_T_BOMLine.java
|       |   |-- I_T_MRP_CRP.java
|       |   |-- X_DD_NetworkDistribution.java
|       |   |-- X_DD_NetworkDistributionLine.java
|       |   |-- X_DD_Order.java
|       |   |-- X_DD_OrderLine.java
|       |   |-- X_PP_BatchCharge.java
|       |   |-- X_PP_Cost_Collector.java
|       |   |-- X_PP_Cost_CollectorMA.java
|       |   |-- X_PP_MRP.java
|       |   |-- X_PP_Order.java
|       |   |-- X_PP_Order_BOM.java
|       |   |-- X_PP_Order_BOMLine.java
|       |   |-- X_PP_Order_BatchCharge.java
|       |   |-- X_PP_Order_Cost.java
|       |   |-- X_PP_Order_Node.java
|       |   |-- X_PP_Order_NodeNext.java
|       |   |-- X_PP_Order_Node_Asset.java
|       |   |-- X_PP_Order_Node_Product.java
|       |   |-- X_PP_Order_Workflow.java
|       |   |-- X_PP_WF_Node_Asset.java
|       |   |-- X_PP_WF_Node_Product.java
|       |   |-- X_QM_Specification.java
|       |   |-- X_QM_SpecificationLine.java
|       |   |-- X_T_BOMLine.java
|       |   `-- X_T_MRP_CRP.java
|       `-- tools
|           |-- PLoader.java
|           `-- worker
|               |-- MultiWorker.java
|               `-- SingleWorker.java
`-- test
    `-- functional
        `-- mfg
            |-- AbstractMakeToOrder.java
            `-- AdempiereTestCase.java
```

### Processes

| Class Name                                            | Purpose                                                                              | Main Parameters                                                                                                                                                                                                                                                                                                            | Key Logic & Results                                                                                                            |
| ----------------------------------------------------- | ------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------ |
| `org.compiere.process.BOMVerify`                      | Verifies and optionally revalidates product BOM records.                             | `M_Product_ID`, `M_Product_Category_ID`, `IsReValidate`.                                                                                                                                                                                                                                                                   | Checks product BOM definitions and validation state for one product or product category.                                       |
| `org.libero.process.CalculateLowLevel`                | Calculates product low-level codes for planning.                                     | Uses process context and product/BOM data.                                                                                                                                                                                                                                                                                 | Updates low-level planning values used by MRP and BOM explosion logic.                                                         |
| `org.libero.process.CompletePrintOrder`               | Completes and prints a manufacturing order.                                          | Manufacturing order context.                                                                                                                                                                                                                                                                                               | Completes the selected order and triggers print-oriented output.                                                               |
| `org.libero.process.ComponentChange`                  | Applies component substitutions or component changes in BOM/order structures.        | Component and BOM/order context parameters.                                                                                                                                                                                                                                                                                | Updates affected manufacturing components according to the requested change.                                                   |
| `org.libero.process.CopyFromBOM`                      | Copies a product BOM structure into another manufacturing document or BOM context.   | Source BOM and target record context.                                                                                                                                                                                                                                                                                      | Creates target BOM lines from the selected source structure.                                                                   |
| `org.libero.process.CopyPriceToStandard`              | Copies cost or price information into standard cost records.                         | Costing and product context.                                                                                                                                                                                                                                                                                               | Updates standard cost values from configured source prices/costs.                                                              |
| `org.libero.process.CreateCostElement`                | Creates manufacturing cost elements.                                                 | Cost element setup context.                                                                                                                                                                                                                                                                                                | Generates required cost element records for manufacturing costing.                                                             |
| `org.libero.process.CreateDocType`                    | Creates document types required by manufacturing and distribution flows.             | Organization/document setup context.                                                                                                                                                                                                                                                                                       | Adds document type definitions used by manufacturing documents.                                                                |
| `org.libero.process.CreateProductPlanning`            | Creates or updates product planning records.                                         | `M_Product_Category_ID`, `M_Warehouse_ID`, `S_Resource_ID`, `CreatePlan`, `MPS`, `DD_NetworkDistribution_ID`, `AD_Workflow_ID`, `TimeFence`, `TransferTime`, `SafetyStock`, `Order_Min`, `Order_Max`, `Order_Pack`, `Order_Qty`, `WorkingTime`, `Yield`, `DeliveryTime_Promised`, `OrderPeriod`, `OrderPolicy`, `Planner`. | Generates product planning data using warehouse, resource, workflow, ordering policy, safety stock, and distribution settings. |
| `org.libero.process.CRP`                              | Runs Capacity Requirements Planning.                                                 | `S_Resource_ID`, `ScheduleType`.                                                                                                                                                                                                                                                                                           | Calculates capacity load for the selected resource and schedule type.                                                          |
| `org.libero.process.CRPSummary`                       | Produces CRP summary data.                                                           | CRP/resource context.                                                                                                                                                                                                                                                                                                      | Summarizes capacity planning results for analysis.                                                                             |
| `org.compiere.process.DistributionRun`                | Runs base distribution run generation through the manufacturing plugin registration. | Distribution run context.                                                                                                                                                                                                                                                                                                  | Generates distribution demand according to distribution run setup.                                                             |
| `org.libero.process.DistributionRunOrders`            | Creates distribution orders from distribution demand.                                | `AD_Org_ID`, `M_Warehouse_ID`, `M_DistributionList_ID`, `DatePromised`, `ConsolidateDocument`, `BasedInDemand`, `IsTest`.                                                                                                                                                                                                  | Generates or simulates distribution orders, optionally consolidating documents.                                                |
| `org.libero.process.FixPaymentCashLine`               | Maintenance process for payment cash line data.                                      | Payment/cash line context.                                                                                                                                                                                                                                                                                                 | Corrects related cash/payment line records.                                                                                    |
| `org.libero.process.ImportProductPlanning`            | Imports product planning setup data.                                                 | Import table/process context.                                                                                                                                                                                                                                                                                              | Reads imported planning records and creates or updates planning configuration.                                                 |
| `org.libero.process.MovementGenerate`                 | Generates inventory movements.                                                       | Movement planning and warehouse context.                                                                                                                                                                                                                                                                                   | Creates movement documents from distribution or planning requirements.                                                         |
| `org.libero.process.MRP`                              | Runs Material Requirements Planning.                                                 | Planning context such as product, warehouse, resource, and date filters when configured in the dictionary.                                                                                                                                                                                                                 | Calculates demand/supply records and updates `PP_MRP`.                                                                         |
| `org.libero.process.MRPFullRun`                       | Executes a full MRP run.                                                             | Full planning scope context.                                                                                                                                                                                                                                                                                               | Rebuilds MRP data for a broader planning scope.                                                                                |
| `org.libero.process.MRPUpdate`                        | Updates MRP records after planning changes.                                          | MRP context.                                                                                                                                                                                                                                                                                                               | Refreshes affected planning records without necessarily running a full plan.                                                   |
| `org.libero.process.PP_Product_BOM_Check`             | Checks product BOM consistency.                                                      | Product BOM context.                                                                                                                                                                                                                                                                                                       | Validates BOM structure and flags issues.                                                                                      |
| `org.libero.process.PrintBOM`                         | Prints or renders BOM information.                                                   | Product/BOM context.                                                                                                                                                                                                                                                                                                       | Produces BOM output for review or printing.                                                                                    |
| `org.libero.process.RollupBillOfMaterial`             | Performs BOM cost rollup.                                                            | Product/BOM/costing context.                                                                                                                                                                                                                                                                                               | Rolls component costs into parent BOM cost.                                                                                    |
| `org.libero.process.RollupWorkflow`                   | Performs routing/workflow cost or time rollup.                                       | Workflow/routing context.                                                                                                                                                                                                                                                                                                  | Updates routing-derived planning or cost values.                                                                               |
| `org.libero.report.CostBillOfMaterial`                | Reports BOM costs.                                                                   | `AD_Org_ID`, `C_AcctSchema_ID`, `M_CostType_ID`, costing method, `M_Product_ID`.                                                                                                                                                                                                                                           | Calculates and reports costed BOM details for the selected accounting schema and cost type.                                    |
| `org.libero.infowindow.CalculateMaterialPlan`         | Calculates material planning data from an info window action.                        | Info window selection context.                                                                                                                                                                                                                                                                                             | Runs material plan calculation for selected records.                                                                           |
| `org.libero.process.eam.AddMeter4eAM`                 | Adds meter information for enterprise asset maintenance scenarios.                   | eAM meter context.                                                                                                                                                                                                                                                                                                         | Creates or updates meter data used by maintenance flows.                                                                       |
| `org.libero.process.eam.CreateForecastLine4eAM`       | Creates forecast lines for eAM planning.                                             | eAM forecast context.                                                                                                                                                                                                                                                                                                      | Generates forecast lines used for maintenance-driven planning.                                                                 |
| `org.libero.process.eam.CreateMOFromForecastLine4eAM` | Creates manufacturing orders from eAM forecast lines.                                | eAM forecast line context.                                                                                                                                                                                                                                                                                                 | Converts selected maintenance forecast lines into manufacturing orders.                                                        |

### Events

| Target Model / Table                 | Event Timing / Topic                                                                                   | Functional Rule & Objective                                                                                                                                                              |
| ------------------------------------ | ------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Login context                        | `AFTER_LOGIN`                                                                                          | Logs login context when MRP event handling is enabled.                                                                                                                                   |
| `M_Movement`                         | `PO_BEFORE_NEW`, `DOC_AFTER_COMPLETE`                                                                  | Prepares movement handling and updates distribution order quantities after movements complete.                                                                                           |
| `M_InOut`                            | `DOC_AFTER_COMPLETE`                                                                                   | Updates Make-To-Kit production orders for shipments/returns and completes related cost collectors for purchase receipts.                                                                 |
| `C_Order`, `C_OrderLine`             | `PO_AFTER_NEW`, `PO_AFTER_CHANGE`, `PO_BEFORE_DELETE`, `DOC_BEFORE_REACTIVATE`, `DOC_AFTER_REACTIVATE` | Maintains MRP records from sales and purchase orders; avoids workflow-only approval changes from retriggering MRP; cleans hanging workflow processes before purchase order reactivation. |
| `M_Requisition`, `M_RequisitionLine` | `PO_AFTER_NEW`, `PO_AFTER_CHANGE`, `PO_BEFORE_DELETE`                                                  | Creates, updates, or deletes MRP demand from requisition changes.                                                                                                                        |
| `M_Forecast`, `M_ForecastLine`       | `PO_AFTER_NEW`, `PO_AFTER_CHANGE`, `PO_BEFORE_DELETE`, `DOC_BEFORE_PREPARE`, `DOC_BEFORE_COMPLETE`     | Maintains MRP demand from forecast headers and lines.                                                                                                                                    |
| `DD_Order`, `DD_OrderLine`           | `PO_AFTER_NEW`, `PO_AFTER_CHANGE`, `PO_BEFORE_DELETE`                                                  | Maintains MRP and distribution quantities from distribution orders.                                                                                                                      |
| `PP_Order`, `PP_Order_BOMLine`       | `PO_AFTER_NEW`, `PO_AFTER_CHANGE`, `PO_BEFORE_DELETE`                                                  | Maintains MRP supply and material requirements from manufacturing orders and BOM lines.                                                                                                  |
| `M_Product`                          | `PO_BEFORE_CHANGE`                                                                                     | Prevents changing `C_UOM_ID` when product-related MRP records exist.                                                                                                                     |

### Callouts

| Callout Class                | Target Field / Column                                                                                                                           | Business Validation & UI Impact                                                              |
| ---------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------- |
| `Callout_PP_Product_BOMLine` | `PP_Product_BOMLine.M_Product_ID`                                                                                                               | Updates BOM line data when the component product changes.                                    |
| `Callout_PP_Product_BOM`     | `PP_Product_BOM.M_Product_ID`                                                                                                                   | Updates product BOM header values when the product changes.                                  |
| `Callout_PP_Order`           | `PP_Order.QtyEntered`, `PP_Order.M_Product_ID`                                                                                                  | Recalculates manufacturing order quantities and product-dependent values.                    |
| `Callout_DD_OrderLine`       | `DD_OrderLine.M_Product_ID`, `M_AttributeSetInstance_ID`, `C_UOM_ID`, `ConfirmedQty`, `M_AttributeSetInstanceTo_ID`, `QtyOrdered`, `QtyEntered` | Recalculates distribution order line quantities, UOM, locator, and attribute-related values. |
| `Callout_DD_OrderLine`       | `DD_Order.AD_Org_ID`                                                                                                                            | Updates distribution order values affected by organization changes.                          |
| `Callout_PP_Cost_Collector`  | `PP_Cost_Collector.PP_Order_ID`, `PP_Order_Node_ID`, `MovementQty`                                                                              | Updates cost collector order, routing node, and movement quantity values.                    |
| `Callout_PP_Order_BOMLine`   | `PP_Order_BOMLine.QtyEntered`, `PP_Order_BOMLine.QtyRequired`                                                                                   | Keeps manufacturing order BOM line entered and required quantities synchronized.             |

### Web Forms

| Form Registration      | Class                                  | Purpose                                       |
| ---------------------- | -------------------------------------- | --------------------------------------------- |
| `WOrderReceiptIssue`   | `org.libero.form.WOrderReceiptIssue`   | Receipt/issue form for manufacturing orders.  |
| `WTreeBOM`             | `org.libero.form.WTreeBOM`             | BOM tree visualization.                       |
| `WMRPDetailed`         | `org.libero.form.WMRPDetailed`         | Detailed MRP review form.                     |
| `WCRP`                 | `org.libero.form.WCRP`                 | Capacity requirements planning form.          |
| `WCRPDetail`           | `org.libero.form.WCRPDetail`           | Detailed capacity requirements planning form. |
| `WBOMDropConfigurator` | `org.libero.form.WBOMDropConfigurator` | BOM drop/configurator UI.                     |

### Models, Factories, and Accounting

| Component              | Registered By                                      | Functional Scope                                                                                                                                              |
| ---------------------- | -------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Model factory          | `OSGI-INF/MFGModelFactory.xml`, `MFG_ModelFactory` | Registers manufacturing, distribution, MRP, quality, batch charge, and selected order/movement model classes so iDempiere resolves custom PO implementations. |
| Document factory       | `OSGI-INF/MFGDocFactory.xml`, `MFG_DocFactory`     | Provides accounting document implementations for `PP_Order`, `DD_Order`, and `PP_Cost_Collector`.                                                             |
| Event handler          | `OSGI-INF/MFGValidator.xml`, `MFG_Validator`       | Handles MRP maintenance, document lifecycle reactions, distribution order updates, and Make-To-Kit shipment updates.                                          |
| Cost engine classes    | `org.adempiere.model.engines`                      | Provides costing and storage calculation support for manufacturing transactions.                                                                              |
| Generated table models | `org.libero.tables`                                | Defines generated interfaces and persistent classes for `DD_*`, `PP_*`, `QM_*`, and temporary planning tables.                                                |

### Application Dictionary Metadata (2Pack)

| Package / File Name         | Purpose & Dictionary Configurations                   |
| --------------------------- | ----------------------------------------------------- |
| `META-INF/2Pack_1.0.0.zip`  | Initial manufacturing application dictionary package. |
| `META-INF/2Pack_1.0.1.zip`  | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_1.0.2.zip`  | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_1.0.3.zip`  | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_1.0.4.zip`  | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_1.0.5.zip`  | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_1.0.6.zip`  | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_1.0.7.zip`  | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_1.0.8.zip`  | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_1.0.9.zip`  | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_1.0.10.zip` | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_1.0.11.zip` | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_1.0.12.zip` | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_1.0.13.zip` | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_2.0.0.zip`  | Major manufacturing dictionary update.                |
| `META-INF/2Pack_2.0.1.zip`  | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_2.0.2.zip`  | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_2.0.10.zip` | Incremental manufacturing dictionary update.          |
| `META-INF/2Pack_2.0.11.zip` | Incremental manufacturing dictionary update.          |

The bundle uses `org.adempiere.plugin.utils.Incremental2PackActivator`, so these packages are intended to be applied incrementally during plugin activation/update.

## Instructions

- Install or deploy the `org.idempiere.mfg2` OSGi bundle in an iDempiere 12 environment.
- Ensure the custom and UI dependencies listed in prerequisites are available in the target platform/runtime.
- Start or refresh iDempiere so the OSGi components and `Incremental2PackActivator` can run.
- Verify the manufacturing 2Pack packages have been applied successfully.
- Grant role access to the installed manufacturing windows, processes, forms, and reports as needed.
- Enable or disable event-driven MRP behavior with the `MRP_ENABLED` system configuration key; the validator defaults to enabled when the key is absent.

## Extra Links

- Original project reference: https://github.com/logilite/org.idempiere.mfg2
