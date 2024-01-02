package esercitazione5.SymbolTable;

import esercitazione5.Nodes.Expr.ID;

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

    public SymbolType returnTypeOfId(String name) {
        Optional<SymbolRow> symbolRowOptional = this.getSymbolRowList().stream().filter(symbolRow -> symbolRow.getName().equals(name)).findFirst();
        if (symbolRowOptional.isPresent()) {
            return symbolRowOptional.get().getSymbolType();
        } else if (this.father != null) {
            return this.father.returnTypeOfId(name);
        } else {
            throw new RuntimeException("L'id " + name + " non è stato dichiarato");
        }
    }

    //Metodo che controlla se id è stato precedentemente assegnato
    //Restituisce un boolean per indicare se gli può essere assegnato un valore
    public boolean checkAssign(ID id) {
        Optional<SymbolRow> symbolRow = this.getSymbolRowList().stream().filter(symRow -> symRow.getName().equals(id.getValue())).findFirst();
        if (symbolRow.isPresent()) {
            return !symbolRow.get().getProperties().equals("in");
        } else if (this.father != null) {
            return this.father.checkAssign(id);
        } else {
            throw new RuntimeException("L'id " + id.getValue() + " non è stato dichiarato");
        }
    }

    public void checkProcOut(ID id) {
        Optional<SymbolRow> symbolRow = this.getSymbolRowList().stream().filter(symRow -> symRow.getName().equals(id.getValue())).findFirst();
        if (symbolRow.isPresent()) {
            if (symbolRow.get().getProperties().equals("out")) {
                if(!(id.getMode() != null && symbolRow.get().getProperties().equals(id.getMode().getName())))
                    throw new RuntimeException("L'id " + id.getValue() + " deve essere passato per riferimento");
            } else if (id.getMode() != null && id.getMode().getName().equals("out")) {
                throw new RuntimeException("L'id " + id.getValue() + " non deve essere passato per riferimento");
            }
            return;
        } else if (this.father != null) {
            this.father.checkProcOut(id);
        }
        throw new RuntimeException("L'id " + id.getValue() + " non è stato dichiarato");
    }
}
