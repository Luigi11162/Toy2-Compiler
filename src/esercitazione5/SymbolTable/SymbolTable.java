package esercitazione5.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable extends HashMap<String, ArrayList<SymbolRow>> {

    private String name;
    private ArrayList<SymbolRow> symbolRowList;

    public SymbolTable(String name, ArrayList<SymbolRow> symbolRowList) {
        super.put(name, symbolRowList);

        this.name = name;
        this.symbolRowList = symbolRowList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SymbolRow> getSymbolRowList() {
        return symbolRowList;
    }

    public void setSymbolRowList(ArrayList<SymbolRow> symbolRowList) {
        this.symbolRowList = symbolRowList;
    }

    public void addSymbolRow(SymbolRow symbolRow) throws Exception {
        if(!probe(symbolRow.getName())) {
            this.symbolRowList.add(symbolRow);
        }else{
            throw new Exception("Elemento già dichiarato");
        }
    }

    public boolean probe(String name){
        return this.getSymbolRowList().stream().anyMatch(symbolRow -> symbolRow.getName().equals(name));
    }
}
