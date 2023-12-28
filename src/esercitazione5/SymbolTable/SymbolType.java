package esercitazione5.SymbolTable;

import esercitazione5.Nodes.Type;

import java.util.ArrayList;

public class SymbolType {
    private Type type;
    private ArrayList<Type> inTypeList = new ArrayList<>();
    private ArrayList<Type> outTypeList =  new ArrayList<>();

    public SymbolType(Type type) {
        this.type = type;
    }

    public SymbolType(ArrayList<Type> inTypeList, ArrayList<Type> outTypeList) {
        this.inTypeList = inTypeList;
        this.outTypeList = outTypeList;
    }

    public SymbolType(ArrayList<Type> inTypeList, Type type){
        this.inTypeList = inTypeList;
        this.outTypeList.add(type);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ArrayList<Type> getInTypeList() {
        return inTypeList;
    }

    public void setInTypeList(ArrayList<Type> inTypeList) {
        this.inTypeList = inTypeList;
    }

    public void addInTypeList(Type type){
        this.inTypeList.add(type);
    }

    public ArrayList<Type> getOutTypeList() {
        return outTypeList;
    }

    public void setOutTypeList(ArrayList<Type> outTypeList) {
        this.outTypeList = outTypeList;
    }

    public void addOutTypeList(Type type){
        this.outTypeList.add(type);
    }
}
