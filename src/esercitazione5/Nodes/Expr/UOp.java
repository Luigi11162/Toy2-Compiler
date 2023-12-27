package esercitazione5.Nodes.Expr;

public class UOp extends Expr {

    private Expr value;

    public UOp(String name, Expr value) {
        super(name);
        super.add(value);

        this.value = value;
    }

    public Expr getValue() {
        return value;
    }

    public void setValue(Expr value) {
        this.value = value;
    }
}
