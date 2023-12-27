package esercitazione5.Nodes.Stat;

import esercitazione5.Nodes.Expr.Expr;
import esercitazione5.Nodes.Expr.ID;

import java.util.ArrayList;

public class AssignOp extends Stat{
    private ArrayList<ID> idList;
    private ArrayList<Expr> exprList;

    public AssignOp(ArrayList<ID> idList, ArrayList<Expr> exprList) {
        super("AssignOp");
        idList.forEach(super::add);
        exprList.forEach(super::add);

        this.idList = idList;
        this.exprList = exprList;
    }

    public ArrayList<ID> getIdList() {
        return idList;
    }

    public void setIdList(ArrayList<ID> idList) {
        this.idList = idList;
    }

    public ArrayList<Expr> getExprList() {
        return exprList;
    }

    public void setExprList(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }

    public void addIdList(ArrayList<ID> idList) {
        idList.forEach(super::add);

        this.idList.addAll(idList);
    }

    public void addExprList(ArrayList<Expr> exprList) {
        exprList.forEach(super::add);

        this.exprList.addAll(exprList);
    }
}
