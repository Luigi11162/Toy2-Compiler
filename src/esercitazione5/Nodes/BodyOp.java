package esercitazione5.Nodes;

import esercitazione5.Nodes.Stat.Stat;
import esercitazione5.SymbolTable.SymbolTable;
import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class BodyOp extends DefaultMutableTreeNode implements NodeVisitor {
    private ArrayList<VarDeclOp> varDeclOpList = new ArrayList<>();
    private ArrayList<Stat> statList = new ArrayList<>();
    private SymbolTable symbolTable;

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
