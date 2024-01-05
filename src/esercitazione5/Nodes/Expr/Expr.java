package esercitazione5.Nodes.Expr;

import esercitazione5.Nodes.Mode;
import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;

public class Expr extends DefaultMutableTreeNode implements NodeVisitor {
    private String name;
    private Mode mode;
    public Expr(String name) {
        super(name);
        this.name = name;
    }

    public Expr(String name, Mode mode) {
        super(name);

        this.mode = mode;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
