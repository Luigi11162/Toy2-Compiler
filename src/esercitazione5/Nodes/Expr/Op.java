package esercitazione5.Nodes.Expr;

import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

public class Op extends Expr implements NodeVisitor {

    private Expr valueL;
    private Expr valueR;

    public Op(String name, Expr valueL, Expr valueR) {
        super(name);
        super.add(valueL);
        super.add(valueR);

        this.valueL = valueL;
        this.valueR = valueR;
    }

    public Expr getValueL() {
        return valueL;
    }

    public void setValueL(Expr valueL) {
        this.valueL = valueL;
    }

    public Expr getValueR() {
        return valueR;
    }

    public void setValueR(Expr valueR) {
        this.valueR = valueR;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
