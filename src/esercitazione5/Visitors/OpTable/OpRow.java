package esercitazione5.Visitors.OpTable;

import esercitazione5.Nodes.Type;

import java.util.ArrayList;

public class OpRow {
    private ArrayList<Type> operandList = new ArrayList<>();
    private Type result;

    public OpRow(ArrayList<Type> operandList, Type result) {
        this.operandList = operandList;
        this.result = result;
    }

    public ArrayList<Type> getOperandList() {
        return operandList;
    }

    public void setOperandList(ArrayList<Type> operandList) {
        this.operandList = operandList;
    }

    public void addOperand(Type operand){
        this.operandList.add(operand);
    }

    public Type getResult() {
        return result;
    }

    public void setResult(Type result) {
        this.result = result;
    }
}
