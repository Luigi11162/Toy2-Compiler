package esercitazione5.Nodes;

import esercitazione5.Nodes.Expr.ID;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

    public class ProcOp extends DefaultMutableTreeNode {
        ID id;
        ArrayList<ProcFunParamOp> procFunParamOpList;
        BodyOp bodyOp;

        public ProcOp(ID idNode, ArrayList<ProcFunParamOp> procFunParamOpList, BodyOp bodyOp) {
            super("ProcOp");
            super.add(idNode);
            if(!procFunParamOpList.isEmpty()){
                procFunParamOpList.forEach(super::add);
            }
            super.add(bodyOp);

            this.id = idNode;
            this.procFunParamOpList = procFunParamOpList;
            this.bodyOp = bodyOp;
        }

        public ID getIdNode() {
            return id;
        }

        public void setIdNode(ID idNode) {
            this.id = idNode;
        }

        public ArrayList<ProcFunParamOp> getProcFunParamOpList() {
            return procFunParamOpList;
        }

        public void setProcFunParamOpList(ArrayList<ProcFunParamOp> procFunParamOpList) {
            this.procFunParamOpList = procFunParamOpList;
        }

        public BodyOp getBodyOp() {
            return bodyOp;
        }

        public void setBodyOp(BodyOp bodyOp) {
            this.bodyOp = bodyOp;
        }

        public void addProcFunParamOpList(ArrayList<ProcFunParamOp> procFunParamOpList){
            procFunParamOpList.forEach(super::add);

            this.procFunParamOpList.addAll(procFunParamOpList);
        }

}
