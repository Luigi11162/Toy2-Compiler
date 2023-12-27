package esercitazione5.Nodes;

import esercitazione5.Nodes.Expr.Expr;
import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;
import java_cup.runtime.SyntaxTreeDFS;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class ElifOp extends DefaultMutableTreeNode implements NodeVisitor {

    private Expr expr;
    private BodyOp bodyOp;

    public ElifOp(Expr expr, BodyOp bodyOp) {
        super("ElifOp");
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
