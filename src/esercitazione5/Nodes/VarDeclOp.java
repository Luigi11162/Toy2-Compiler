package esercitazione5.Nodes;

import esercitazione5.Nodes.Expr.Const;
import esercitazione5.Nodes.Expr.ID;
import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class VarDeclOp extends DefaultMutableTreeNode implements NodeVisitor {
    private ArrayList<ID> idList = new ArrayList<>();
    private ArrayList<Const> constList = new ArrayList<>();
    private Type type;

    public VarDeclOp(ArrayList<ID> idList, Type type) {
        super("VarDeclOp");
        idList.forEach(super::add);
        super.add(type);

        this.idList = idList;
        this.type = type;
    }

    public VarDeclOp(ArrayList<ID> idList, ArrayList<Const> constList) {
        super("VarDeclOp");
        idList.forEach(super::add);
        constList.forEach(super::add);

        this.idList = idList;
        this.constList = constList;

    }

    public ArrayList<ID> getidList() {
        return idList;
    }

    public void setidList(ArrayList<ID> idList) {
        this.idList = idList;
    }


    public ArrayList<Const> getConstList() {
        return constList;
    }

    public void setConstList(ArrayList<Const> constList) {
        this.constList = constList;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void addIdList(ArrayList<ID> idList) {
        idList.forEach(super::add);

        this.idList.addAll(idList);
    }

    public void addConstList(ArrayList<Const> constList) {
        constList.forEach(super::add);

        this.constList.addAll(constList);
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
