package esercitazione5.Nodes.Stat;

import esercitazione5.Nodes.BodyOp;
import esercitazione5.Nodes.Expr.Expr;
import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

public class WhileOp extends Stat implements NodeVisitor {
    private Expr expr;
    private BodyOp bodyOp;

    public WhileOp(Expr expr, BodyOp bodyOp) {
        super("WhileOp");
        super.add(expr);
        super.add(bodyOp);

        this.expr = expr;
        this.bodyOp = bodyOp;
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public BodyOp getBodyOp() {
        return bodyOp;
    }

    public void setBodyOp(BodyOp bodyOp) {
        this.bodyOp = bodyOp;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
