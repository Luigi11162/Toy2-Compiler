package esercitazione5.Nodes.Stat;

import esercitazione5.Nodes.BodyOp;
import esercitazione5.Nodes.Expr.Expr;

public class WhileOp extends  Stat{
    Expr expr;
    BodyOp bodyOp;

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
}
