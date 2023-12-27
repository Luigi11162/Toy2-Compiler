package esercitazione5.Nodes;

import esercitazione5.Nodes.Expr.ID;
import esercitazione5.SymbolTable.SymbolTable;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class FunOp extends DefaultMutableTreeNode {
    private ID id;
    private ArrayList<ProcFunParamOp> procFunParamOpList;
    private ArrayList<Type> typeList;
    private BodyOp bodyOp;
    private SymbolTable symbolTable;

    public FunOp(ID idNode, ArrayList<ProcFunParamOp> procFunParamOpList, ArrayList<Type> typeList, BodyOp bodyOp) {
        super("FunOp");
        super.add(idNode);
        if(!procFunParamOpList.isEmpty()){
            procFunParamOpList.forEach(super::add);
        }
        typeList.forEach(super::add);
        super.add(bodyOp);

        this.id = idNode;
        this.procFunParamOpList = procFunParamOpList;
        this.typeList = typeList;
        this.bodyOp = bodyOp;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID idNode) {
        this.id = idNode;
    }

    public ArrayList<ProcFunParamOp> getProcFunParamOpList() {
        return procFunParamOpList;
    }

    public void setProcFunParamOpList(ArrayList<ProcFunParamOp> procFunParamOpList) {
        this.procFunParamOpList = procFunParamOpList;
    }

    public ArrayList<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(ArrayList<Type> typeList) {
        this.typeList = typeList;
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

    public void addTypeList(ArrayList<Type> typeList){
        typeList.forEach(super::add);

        this.typeList.addAll(typeList);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
