package esercitazione5.Nodes;

import esercitazione5.Nodes.Stat.Stat;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class BodyOp extends DefaultMutableTreeNode {
    ArrayList<VarDeclOp> varDeclOpList;
    ArrayList<Stat> statList;

    public BodyOp(ArrayList<VarDeclOp> varDeclOpList, ArrayList<Stat> statList) {
        super("BodyOp");
        varDeclOpList.forEach(super::add);
        statList.forEach(super::add);

        this.varDeclOpList = varDeclOpList;
        this.statList = statList;
    }

    public ArrayList<VarDeclOp> getVarDeclOpList() {
        return varDeclOpList;
    }

    public void setVarDeclOpList(ArrayList<VarDeclOp> varDeclOpList) {
        this.varDeclOpList = varDeclOpList;
    }

    public ArrayList<Stat> getStatList() {
        return statList;
    }

    public void setStatList(ArrayList<Stat> statList) {
        this.statList = statList;
    }

    public void addVarDeclOpList(ArrayList<VarDeclOp> varDeclOpList){
        varDeclOpList.forEach(super::add);

        this.varDeclOpList.addAll(varDeclOpList);
    }

    public void addStatList(ArrayList<Stat> statList){
        statList.forEach(super::add);

        this.statList.addAll(statList);
    }
}
