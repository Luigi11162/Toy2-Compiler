package esercitazione5.SymbolTable;

import java.util.HashMap;

public class SymbolTable extends HashMap<String, SymbolRow> {

    String name;
    SymbolRow symbolRow;

    public SymbolTable(String name, SymbolRow symbolRow) {
        super.put(name, symbolRow);

        this.name = name;
        this.symbolRow = symbolRow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SymbolRow getSymbolRow() {
        return symbolRow;
    }

    public void setSymbolRow(SymbolRow symbolRow) {
        this.symbolRow = symbolRow;
    }
}
