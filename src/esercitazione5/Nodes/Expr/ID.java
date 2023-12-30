package esercitazione5.Nodes.Expr;


import esercitazione5.Nodes.Mode;
import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

public class ID extends Expr implements NodeVisitor {
    private String value;
    private Mode mode;

    public ID(String value) {
        super("ID: " + value);

        this.value = value;
    }
    public ID(String value, Mode mode){
        super("ID: " + value);

        this.value = value;
        this.mode = mode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
