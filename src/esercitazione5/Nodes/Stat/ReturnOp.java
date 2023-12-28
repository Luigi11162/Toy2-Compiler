package esercitazione5.Nodes.Stat;

import esercitazione5.Nodes.Expr.Expr;
import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Visitors.Visitor;

import java.util.ArrayList;

public class ReturnOp extends Stat implements NodeVisitor {
    private ArrayList<Expr> exprList = new ArrayList<>();

    public ReturnOp(ArrayList<Expr> exprList) {
        super("ReturnOp");
        exprList.forEach(super::add);

        this.exprList = exprList;
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
