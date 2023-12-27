package esercitazione5.Nodes;

import javax.swing.tree.DefaultMutableTreeNode;

public class Type extends DefaultMutableTreeNode {

    String name;

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
}
