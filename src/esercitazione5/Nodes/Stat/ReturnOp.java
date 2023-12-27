package esercitazione5.Nodes.Stat;

import esercitazione5.Nodes.Expr.Expr;

import java.util.ArrayList;

public class ReturnOp extends Stat{
    private ArrayList<Expr> exprList;

    public ReturnOp( ArrayList<Expr> exprList) {
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

}
