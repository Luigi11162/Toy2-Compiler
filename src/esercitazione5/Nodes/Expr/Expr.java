package esercitazione5.Nodes.Expr;

import esercitazione5.Nodes.Mode;
import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class Expr extends DefaultMutableTreeNode implements NodeVisitor {
    private String name;
    private Mode modeExpr;
    public Expr(String name) {
        super(name);
        this.name = name;
    }

    public Expr(String name, Mode modeExpr) {
        super(name);

        this.modeExpr = modeExpr;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mode getModeExpr() {
        return modeExpr;
    }

    public void setModeExpr(Mode modeExpr) {
        this.modeExpr = modeExpr;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
