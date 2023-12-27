package esercitazione5.Nodes.UtilsClass;

import esercitazione5.Nodes.Expr.ID;
import esercitazione5.Nodes.Mode;

public class PairModeId {

    private Mode mode;
    private ID id;

    public PairModeId(Mode mode, ID id) {
        this.mode = mode;
        this.id = id;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
