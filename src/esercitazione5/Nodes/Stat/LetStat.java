package esercitazione5.Nodes.Stat;

import esercitazione5.Nodes.GoWhenOp;
import esercitazione5.Nodes.VarDeclOp;
import esercitazione5.SymbolTable.SymbolTable;
import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

import java.util.ArrayList;

public class LetStat extends Stat implements NodeVisitor {

    private ArrayList<VarDeclOp> varDeclList;
    private ArrayList<GoWhenOp> goWhenList;
    private ArrayList<Stat> statList;
    private SymbolTable symbolTable;

    public LetStat(ArrayList<VarDeclOp> varDeclList, ArrayList<GoWhenOp> goWhenList, ArrayList<Stat> statList) {
        super("LetStat");
        varDeclList.forEach(super::add);
        goWhenList.forEach(super::add);
        statList.forEach(super::add);

        this.varDeclList = varDeclList;
        this.goWhenList = goWhenList;
        this.statList = statList;
    }

    public ArrayList<VarDeclOp> getVarDeclList() {
        return varDeclList;
    }

    public void setVarDeclList(ArrayList<VarDeclOp> varDeclList) {
        this.varDeclList = varDeclList;
    }

    public ArrayList<GoWhenOp> getGoWhenList() {
        return goWhenList;
    }

    public void setGoWhenList(ArrayList<GoWhenOp> goWhenList) {
        this.goWhenList = goWhenList;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public ArrayList<Stat> getStatList() {
        return statList;
    }

    public void setStatList(ArrayList<Stat> statList) {
        this.statList = statList;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
