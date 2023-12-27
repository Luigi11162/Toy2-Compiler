package esercitazione5.Nodes.Expr;

import javax.swing.tree.DefaultMutableTreeNode;

public class Expr extends DefaultMutableTreeNode {
    private String name;

    public Expr(String name) {
        super(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
