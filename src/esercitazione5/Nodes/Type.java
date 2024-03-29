package esercitazione5.Nodes;

import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;

public class Type extends DefaultMutableTreeNode implements NodeVisitor {

    private String name;

    public Type(String name) {
        super(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
