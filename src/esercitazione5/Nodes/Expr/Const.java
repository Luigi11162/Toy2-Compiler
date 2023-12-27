package esercitazione5.Nodes.Expr;

public class Const extends Expr {

    private String value;

    public Const(String name, String value) {
        super(name+": " + value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
