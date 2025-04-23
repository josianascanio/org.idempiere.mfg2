package org.libero.process;


import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.wf.MWFNode;
import org.compiere.wf.MWFNodeNext;


import java.util.*;

/**
 * CopyWorkflowStructure
 * Copia todos los nodos, productos, herramientas y transiciones de un AD_Workflow a otro
 *
 * @author Josian
 */
public class CopyWorkflowStructure extends SvrProcess {

    private static final CLogger log = CLogger.getCLogger(CopyWorkflowStructure.class);

    private int sourceWorkflowID = 0;
    private int targetWorkflowID = 0;
    private Map<Integer, Integer> nodeIDMap = new HashMap<>();

    @Override
    protected void prepare() {
        for (ProcessInfoParameter param : getParameter()) {
            String name = param.getParameterName();
            if ("AD_Workflow_ID".equals(name))
                sourceWorkflowID = param.getParameterAsInt();
            else
                log.warning("Unknown Parameter: " + name);
        }
        targetWorkflowID = getRecord_ID();
    }

    @Override
    protected String doIt() throws Exception {
        if (sourceWorkflowID == 0 || targetWorkflowID == 0)
            throw new IllegalArgumentException("Invalid Workflow IDs");

        log.info("Copying from AD_Workflow_ID=" + sourceWorkflowID + " to " + targetWorkflowID);

        List<MWFNode> sourceNodes = new Query(getCtx(), MWFNode.Table_Name, "AD_Workflow_ID=?", get_TrxName())
                .setParameters(sourceWorkflowID).list();

        for (MWFNode sourceNode : sourceNodes) {
            MWFNode targetNode = new MWFNode(getCtx(), 0, get_TrxName());
            MWFNode.copyValues(sourceNode, targetNode);
            targetNode.setAD_Workflow_ID(targetWorkflowID);
            targetNode.saveEx();
            nodeIDMap.put(sourceNode.getAD_WF_Node_ID(), targetNode.getAD_WF_Node_ID());

            copyNodeProducts(sourceNode.getAD_WF_Node_ID(), targetNode.getAD_WF_Node_ID());
            copyNodeAssets(sourceNode.getAD_WF_Node_ID(), targetNode.getAD_WF_Node_ID());
        }

        copyNodeTransitions();

        addLog("@Copied@ @AD_WF_Node_ID@= " + nodeIDMap.size());

        return "@Success@ Copied " + nodeIDMap.size() + " Nodes";
    }

    
    private void copyNodeProducts(int sourceNodeID, int targetNodeID) {
        List<PO> products = new Query(getCtx(), "PP_WF_Node_Product", "AD_WF_Node_ID=?", get_TrxName())
                .setParameters(sourceNodeID).list();

        for (PO product : products) {
            PO newProduct = MTable.get(getCtx(), "PP_WF_Node_Product").getPO(0, get_TrxName());
            PO.copyValues(product, newProduct);
            newProduct.set_ValueOfColumn("AD_WF_Node_ID", targetNodeID);
            newProduct.saveEx();
        }
    }


    private void copyNodeAssets(int sourceNodeID, int targetNodeID) {
        List<PO> assets = new Query(getCtx(), "PP_WF_Node_Asset", "AD_WF_Node_ID=?", get_TrxName())
                .setParameters(sourceNodeID).list();

        for (PO asset : assets) {
            PO newAsset = MTable.get(getCtx(), "PP_WF_Node_Asset").getPO(0, get_TrxName());
            PO.copyValues(asset, newAsset);
            newAsset.set_ValueOfColumn("AD_WF_Node_ID", targetNodeID);
            newAsset.saveEx();
        }
    }


    private void copyNodeTransitions() {
        List<MWFNodeNext> transitions = new Query(getCtx(), MWFNodeNext.Table_Name,
                "AD_WF_Node_ID IN (" + String.join(",",
                nodeIDMap.keySet().stream().map(String::valueOf).toArray(String[]::new)) + ")",
                get_TrxName()).list();

        for (MWFNodeNext transition : transitions) {
            Integer newFromID = nodeIDMap.get(transition.getAD_WF_Node_ID());
            Integer newToID = nodeIDMap.get(transition.getAD_WF_Next_ID());

            if (newFromID == null || newToID == null)
                continue;

            MWFNodeNext newTransition = new MWFNodeNext(getCtx(), 0, get_TrxName());
            MWFNodeNext.copyValues(transition, newTransition);
            newTransition.setAD_WF_Node_ID(newFromID);
            newTransition.setAD_WF_Next_ID(newToID);
            newTransition.saveEx();
        }
    }
} 
