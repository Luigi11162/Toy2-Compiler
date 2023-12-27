package esercitazione5.Nodes.Expr;


public class ID extends Expr {
    private String value;

    public ID(String value) {
        super("ID: " + value);

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
