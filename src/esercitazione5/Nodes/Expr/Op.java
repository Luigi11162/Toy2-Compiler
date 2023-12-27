package esercitazione5.Nodes.Expr;

public class Op extends Expr {

    Expr valueL;
    Expr valueR;

    public Op(String name, Expr valueL, Expr valueR){
        super(name);
        super.add(valueL);
        super.add(valueR);

        this.valueL = valueL;
        this.valueR = valueR;
    }

    public Expr getValueL() {
        return valueL;
    }

    public void setValueL(Expr valueL) {
        this.valueL = valueL;
    }

    public Expr getValueR() {
        return valueR;
    }

    public void setValueR(Expr valueR) {
        this.valueR = valueR;
    }
}
