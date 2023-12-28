package esercitazione5.Nodes.Expr;

import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

import java.util.ArrayList;

public class CallFunOp extends Expr implements NodeVisitor {

    private ID id;
    private ArrayList<Expr> exprList = new ArrayList<>();

    public CallFunOp(ID id, ArrayList<Expr> exprList) {
        super("CallFunOp");
        super.add(id);
        exprList.forEach(super::add);

        this.id = id;
        this.exprList = exprList;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public ArrayList<Expr> getExprList() {
        return exprList;
    }

    public void setExprList(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }

    public void addExprList(ArrayList<Expr> exprList) {
        exprList.forEach(super::add);

        this.exprList.addAll(exprList);
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
