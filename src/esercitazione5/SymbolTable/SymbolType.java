package esercitazione5.SymbolTable;

import esercitazione5.Nodes.Type;

import java.util.ArrayList;

public class SymbolType {
    private ArrayList<Type> inTypeList = new ArrayList<>();
    private ArrayList<Type> outTypeList =  new ArrayList<>();


    public SymbolType(ArrayList<Type> inTypeList, ArrayList<Type> outTypeList) {
        this.inTypeList = inTypeList;
        this.outTypeList = outTypeList;
    }
    public SymbolType( ArrayList<Type> outTypeList) {
        this.outTypeList = outTypeList;
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
    public void addOutType(Type type){
        this.outTypeList.add(type);
    }
    public void addOutTypeList(ArrayList<Type> typeList){
        this.outTypeList.addAll(typeList);
    }
}
