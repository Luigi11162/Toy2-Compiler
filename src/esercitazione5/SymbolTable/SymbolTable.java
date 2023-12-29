package esercitazione5.SymbolTable;

import esercitazione5.Nodes.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class SymbolTable extends HashMap<String, ArrayList<SymbolRow>> {

    private SymbolTable father;
    private String name;
    private ArrayList<SymbolRow> symbolRowList;

    public SymbolTable(SymbolTable father, String name, ArrayList<SymbolRow> symbolRowList) {
        super.put(name, symbolRowList);

        this.father = father;
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
        if (!probe(symbolRow.getName())) {
            this.symbolRowList.add(symbolRow);
        } else {
            throw new Exception("Elemento già dichiarato");
        }
    }

    public SymbolTable getFather() {
        return father;
    }

    public void setFather(SymbolTable father) {
        this.father = father;
    }

    public boolean probe(String name) {
        return this.getSymbolRowList().stream().anyMatch(symbolRow -> symbolRow.getName().equals(name));
    }

    public boolean checkIdDeclared(String name) {
        if (this.probe(name))
            return true;
        else if (father != null)
            return this.father.probe(name);
        else
            return false;
    }

    public SymbolType returnTypeOfId(String name) {
        Optional<SymbolRow> symbolRowOptional = this.getSymbolRowList().stream().filter(symbolRow -> symbolRow.getName().equals(name)).findFirst();
        if (symbolRowOptional.isPresent()) {
            return symbolRowOptional.get().getSymbolType();
        } else if (father != null) {
            return father.returnTypeOfId(name);
        } else {
            throw new RuntimeException("L'id "+name+" non è stato dichiarato");
        }
    }
}
