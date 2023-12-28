package esercitazione5.Nodes;

import esercitazione5.Nodes.Expr.ID;
import esercitazione5.SymbolTable.SymbolTable;
import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

    public class ProcOp extends DefaultMutableTreeNode implements NodeVisitor {
        private ID id;
        private ArrayList<ProcFunParamOp> procFunParamOpList = new ArrayList<>();
        private BodyOp bodyOp;
        private SymbolTable symbolTable;

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

        public ID getId() {
            return id;
        }

        public void setId(ID id) {
            this.id = id;
        }

        public SymbolTable getSymbolTable() {
            return symbolTable;
        }

        public void setSymbolTable(SymbolTable symbolTable) {
            this.symbolTable = symbolTable;
        }
        @Override
        public Object accept(Visitor visitor) {
            return visitor.visit(this);
        }
    }
