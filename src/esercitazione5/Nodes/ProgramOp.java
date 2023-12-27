package esercitazione5.Nodes;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;

public class ProgramOp extends DefaultMutableTreeNode {
    ArrayList<VarDeclOp> varDeclOpList;
    ArrayList<ProcOp> procOpList;
    ArrayList<FunOp> funOpList;

    public ProgramOp(ArrayList<VarDeclOp> varDeclOpList, ArrayList<ProcOp> procOpList, ArrayList<FunOp> funOpList){
        super("ProgramOp");
        varDeclOpList.forEach(super::add);
        procOpList.forEach(super::add);
        funOpList.forEach(super::add);

        this.varDeclOpList = varDeclOpList;
        this.procOpList = procOpList;
        this.funOpList = funOpList;
    }

    public ArrayList<VarDeclOp> getVarDeclOpList() {
        return varDeclOpList;
    }

    public void setVarDeclOpList(ArrayList<VarDeclOp> varDeclOpList) {
        this.varDeclOpList = varDeclOpList;
    }

    public ArrayList<ProcOp> getProcOpList() {
        return procOpList;
    }

    public void setProcOpList(ArrayList<ProcOp> procOpList) {
        this.procOpList = procOpList;
    }

    public ArrayList<FunOp> getFunOpList() {
        return funOpList;
    }

    public void setFunOpList(ArrayList<FunOp> funOpList) {
        this.funOpList = funOpList;
    }

    public void addVarDeclOpList(ArrayList<VarDeclOp> varDeclOpList){
        varDeclOpList.forEach(super::add);

        this.varDeclOpList.addAll(varDeclOpList);
    }
    public void addProcOpList(ArrayList<ProcOp> procOpList){
        procOpList.forEach(super::add);

        this.procOpList.addAll(procOpList);
    }

    public void addProcOp(ProcOp procOp){
        super.add(procOp);

        this.procOpList.add(procOp);
    }

    public void addFunOpList(ArrayList<FunOp> funOpList){
        funOpList.forEach(super::add);

        this.funOpList.addAll(funOpList);
    }

}
