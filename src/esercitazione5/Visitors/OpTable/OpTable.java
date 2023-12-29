package esercitazione5.Visitors.OpTable;

import java.util.ArrayList;

public class OpTable {
    private String operator;
    private ArrayList<OpRow> opRowList;

    public OpTable(String operator, ArrayList<OpRow> opRowList) {
        this.operator = operator;
        this.opRowList = opRowList;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public ArrayList<OpRow> getOpRowList() {
        return opRowList;
    }

    public void setOpRowList(ArrayList<OpRow> opRowList) {
        this.opRowList = opRowList;
    }
}
