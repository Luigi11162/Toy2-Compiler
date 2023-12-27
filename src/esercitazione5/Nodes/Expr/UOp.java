package esercitazione5.Nodes.Expr;

import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

public class UOp extends Expr implements NodeVisitor {

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

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
