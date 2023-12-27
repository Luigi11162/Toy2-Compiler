package esercitazione5.Nodes.Stat;

import esercitazione5.Nodes.Expr.Expr;
import esercitazione5.Nodes.Mode;

import java.util.ArrayList;

public class WriteOp extends Stat {
    private Mode mode;
    private ArrayList<Expr> exprList;

    public WriteOp(Mode mode, ArrayList<Expr> exprList) {
        super("WriteOp");
        super.add(mode);
        exprList.forEach(super::add);

        this.add(mode);
        this.exprList = exprList;
    }

    public ArrayList<Expr> getExprList() {
        return exprList;
    }

    public void setExprList(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void addExprList(ArrayList<Expr> exprList) {
        exprList.forEach(super::add);

        this.exprList.addAll(exprList);
    }
}
