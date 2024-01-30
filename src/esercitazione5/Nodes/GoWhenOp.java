package esercitazione5.Nodes;

import esercitazione5.Visitors.NodeVisitor;
import esercitazione5.Nodes.Expr.Expr;
import esercitazione5.Nodes.Stat.Stat;
import esercitazione5.Visitors.Visitor;


import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class GoWhenOp extends DefaultMutableTreeNode implements NodeVisitor {
    private Expr expr;
    private ArrayList<Stat> statList;

    public GoWhenOp(Expr expr, ArrayList<Stat> statList) {
        super("GoWhenOp");
        super.add(expr);
        statList.forEach(super::add);

        this.expr = expr;
        this.statList = statList;
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public ArrayList<Stat> getStatList() {
        return statList;
    }

    public void setStatList(ArrayList<Stat> statList) {
        this.statList = statList;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
