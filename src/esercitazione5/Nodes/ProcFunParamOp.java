package esercitazione5.Nodes;

import esercitazione5.Nodes.Expr.ID;
import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;

public class ProcFunParamOp extends DefaultMutableTreeNode implements NodeVisitor {
    private Mode mode;
    private ID id;
    private Type type;

    public ProcFunParamOp(Mode mode, ID id, Type type) {
        super("ProcFunParamOp");
        super.add(mode);
        super.add(id);
        super.add(type);

        this.mode = mode;
        this.id = id;
        this.type = type;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
