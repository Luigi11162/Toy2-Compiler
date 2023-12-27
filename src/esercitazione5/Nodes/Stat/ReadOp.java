package esercitazione5.Nodes.Stat;

import esercitazione5.Nodes.Expr.Expr;

import java.util.ArrayList;

public class ReadOp extends Stat {
    ArrayList<Expr> exprList;

    public ReadOp( ArrayList<Expr> exprList) {
        super("ReadOp");
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
