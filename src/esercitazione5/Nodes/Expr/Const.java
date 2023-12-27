package esercitazione5.Nodes.Expr;

import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

public class Const extends Expr implements NodeVisitor {

    private String value;

    public Const(String name, String value) {
        super(name + ": " + value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
