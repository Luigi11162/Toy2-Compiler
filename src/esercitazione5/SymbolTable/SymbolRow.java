package esercitazione5.SymbolTable;

public class SymbolRow {
    private String name;
    private String kind;
    private SymbolType symbolType;
    private String properties;

    public SymbolRow(String name, String kind, SymbolType symbolType, String properties) {
        this.name = name;
        this.kind = kind;
        this.symbolType = symbolType;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public SymbolType getSymbolType() {
        return symbolType;
    }

    public void setSymbolType(SymbolType symbolType) {
        this.symbolType = symbolType;
    }

    public String getProperties() {
        return this.properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
