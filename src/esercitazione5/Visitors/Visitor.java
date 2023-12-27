package esercitazione5.Visitors;

import esercitazione5.Nodes.*;
import esercitazione5.Nodes.Expr.*;
import esercitazione5.Nodes.Stat.*;

public interface Visitor {

    public Object visit(ProgramOp programOp);
    public Object visit(VarDeclOp varDeclOp);
    public Object visit(FunOp funOp);
    public Object visit(ProcOp procOp);
    public Object visit(BodyOp bodyOp);
    public Object visit(ProcFunParamOp procFunParamOp);
    public Object visit(ElifOp elifOp);
    public Object visit(Mode mode);
    public Object visit(Type type);
    public Object visit(Stat stat);
    public Object visit(AssignOp assignOp);
    public Object visit(IfStatOp ifStatOp);
    public Object visit(ProcCallOp procCallOp);
    public Object visit(ReadOp readOp);
    public Object visit(ReturnOp returnOp);
    public Object visit(WhileOp whileOp);
    public Object visit(WriteOp writeOp);
    public Object visit(Expr expr);
    public Object visit(CallFunOp callFunOp);
    public Object visit(Const const1);
    public Object visit(ID id);
    public Object visit(Op op);
    public Object visit(UOp uOp);
}
