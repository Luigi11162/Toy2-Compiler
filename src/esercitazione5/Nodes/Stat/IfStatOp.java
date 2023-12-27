package esercitazione5.Nodes.Stat;

import esercitazione5.Nodes.BodyOp;
import esercitazione5.Nodes.ElifOp;
import esercitazione5.Nodes.Expr.Expr;

import java.util.ArrayList;

public class IfStatOp extends Stat {

    private Expr expr;
    private BodyOp bodyOp;
    private ArrayList<ElifOp> elifOpList;
    private BodyOp bodyOp2;

    public IfStatOp(Expr expr, BodyOp bodyOp, ArrayList<ElifOp> elifOpList, BodyOp bodyOp2) {
        super("IfStatOp");
        super.add(expr);
        super.add(bodyOp);
        if(!elifOpList.isEmpty()){
            elifOpList.forEach(super::add);
        }
        if(bodyOp2!=null){
            super.add(bodyOp2);
        }

        this.expr = expr;
        this.bodyOp = bodyOp;
        this.elifOpList = elifOpList;
        this.bodyOp2 = bodyOp2;
    }

    public Expr getExprNode() {
        return expr;
    }

    public void setExprNode(Expr expr) {
        this.expr = expr;
    }

    public BodyOp getBodyOp() {
        return bodyOp;
    }

    public void setBodyOp(BodyOp bodyOp) {
        this.bodyOp = bodyOp;
    }

    public ArrayList<ElifOp> getElifOpList() {
        return elifOpList;
    }

    public void setElifOpList(ArrayList<ElifOp> elifOpList) {
        this.elifOpList = elifOpList;
    }

    public BodyOp getBodyOp2() {
        return bodyOp2;
    }

    public void setBodyOp2(BodyOp bodyOp2) {
        this.bodyOp2 = bodyOp2;
    }

    public void addElifOpList(ArrayList<ElifOp> elifOpList) {
        elifOpList.forEach(super::add);

        this.elifOpList.addAll(elifOpList);
    }

}
