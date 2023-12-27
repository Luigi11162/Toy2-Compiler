package esercitazione5.Nodes.Stat;

import javax.swing.tree.DefaultMutableTreeNode;

public class Stat extends DefaultMutableTreeNode {

    private String name;
    public Stat(String name) {
        super(name);
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
