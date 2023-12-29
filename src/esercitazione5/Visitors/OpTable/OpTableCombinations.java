package esercitazione5.Visitors.OpTable;

import esercitazione5.Nodes.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OpTableCombinations {

    public enum EnumOpTable {
        UMINUSOPTABLE,
        NOTOPTABLE,
        ARITOPTABLE,
        CONCATOPTABLE,
        LOGICOPTABLE,
        RELOPTABLE
    }

    public static final OpTable UMINUSOP = new OpTable(
            "UMinus",
            new ArrayList<>(
                    List.of(
                            new OpRow(
                                    new ArrayList<>(List.of(new Type("Integer"))),
                                    new Type("Integer")
                            ),
                            new OpRow(
                                    new ArrayList<>(List.of(new Type("Real"))),
                                    new Type("Real")
                            )
                    )
            )
    );
    public static final OpTable NOTOP = new OpTable(
            "Not",
            new ArrayList<>(
                    List.of(
                            new OpRow(
                                    new ArrayList<>(List.of(new Type("Boolean"))),
                                    new Type("Boolean")
                            )
                    )
            )
    );

    public static final OpTable ARITOP = new OpTable(
            "AritOp",
            new ArrayList<>(
                    List.of(
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Integer"),
                                                    new Type("Integer")
                                            )
                                    ),
                                    new Type("Integer")
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Integer"),
                                                    new Type("Real")
                                            )
                                    ),
                                    new Type("Real")
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Real"),
                                                    new Type("Integer")
                                            )
                                    ),
                                    new Type("Real")
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Real"),
                                                    new Type("Real")
                                            )
                                    ),
                                    new Type("Real")
                            )
                    )
            )
    );

    public static final OpTable CONCATOP = new OpTable(
            "ConcatOp",
            new ArrayList<>(
                    List.of(
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("String"),
                                                    new Type("String")
                                            )
                                    ),
                                    new Type("String")
                            )
                    )
            )
    );

    public static final OpTable LOGICOP = new OpTable(
            "LogicOp",
            new ArrayList<>(
                    List.of(
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Boolean"),
                                                    new Type("Boolean")
                                            )
                                    ),
                                    new Type("Boolean")
                            )
                    )
            )
    );

    public static final OpTable RELOP = new OpTable(
            "RelOp",
            new ArrayList<>(
                    List.of(
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Integer"),
                                                    new Type("Integer")
                                            )
                                    ),
                                    new Type("Boolean")
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Real"),
                                                    new Type("Integer")
                                            )
                                    ),
                                    new Type("Boolean")
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Integer"),
                                                    new Type("Real")
                                            )
                                    ),
                                    new Type("Boolean")
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Real"),
                                                    new Type("Real")
                                            )
                                    ),
                                    new Type("Boolean")
                            )
                    )
            )
    );

    public static boolean checkCombination(OpRow opRowToCheck, EnumOpTable enumOpTable) {
        Iterator<OpRow> opRowIterator = null;
        switch (enumOpTable) {
            case UMINUSOPTABLE:
                 opRowIterator = UMINUSOP.getOpRowList().iterator();
                return checkIterator(opRowIterator, opRowToCheck);
            case NOTOPTABLE:
                opRowIterator = NOTOP.getOpRowList().iterator();
                return checkIterator(opRowIterator, opRowToCheck);
            case ARITOPTABLE:
                opRowIterator = ARITOP.getOpRowList().iterator();
                return checkIterator(opRowIterator, opRowToCheck);
            case CONCATOPTABLE:
                opRowIterator = CONCATOP.getOpRowList().iterator();
                return checkIterator(opRowIterator, opRowToCheck);
            case LOGICOPTABLE:
                opRowIterator = LOGICOP.getOpRowList().iterator();
                return checkIterator(opRowIterator, opRowToCheck);
            case RELOPTABLE:
                opRowIterator = RELOP.getOpRowList().iterator();
                return checkIterator(opRowIterator, opRowToCheck);
            default:
                return false;
        }
    }

    private static boolean checkIterator(Iterator<OpRow> opRowIterator, OpRow opRowToCheck){
        while (opRowIterator.hasNext()){
            Iterator<Type> itType = opRowToCheck.getOperandList().iterator();
            OpRow opRowTable = opRowIterator.next();
            Iterator<Type> itTypeTable = opRowTable.getOperandList().iterator();

            while (itType.hasNext() || itTypeTable.hasNext()){
                if (!itType.next().getName().equals(itTypeTable.next().getName()))
                    return false;
            }

            if (!opRowToCheck.getResult().getName().equals(opRowTable.getResult().getName()))
                return false;
        }
        return true;
    }
}
