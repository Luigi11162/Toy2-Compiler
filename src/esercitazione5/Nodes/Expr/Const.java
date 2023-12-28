package esercitazione5.Nodes.Expr;

import esercitazione5.Nodes.Type;
import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

public class Const extends Expr implements NodeVisitor {

    private String value;
    private Type type;

    public Const(String name, String value) {
        super(name + ": " + value);
        this.value = value;
        this.type = new Type(name);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
