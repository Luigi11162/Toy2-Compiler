package esercitazione5.Nodes;

import javax.swing.tree.DefaultMutableTreeNode;

public class Mode extends DefaultMutableTreeNode {
    String name;

    public Mode(String name) {
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
