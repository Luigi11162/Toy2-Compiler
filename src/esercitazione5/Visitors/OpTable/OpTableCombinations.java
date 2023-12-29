package esercitazione5.Visitors.OpTable;

import esercitazione5.Nodes.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class OpTableCombinations {

    public enum EnumOpTable {
        UMINUSOP,
        NOTOP,
        ARITOP,
        CONCATOP,
        LOGICOP,
        RELOP
    }

    private static final OpTable UMINUSOP = new OpTable(
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
    private static final OpTable NOTOP = new OpTable(
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

    private static final OpTable ARITOP = new OpTable(
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

    private static final OpTable CONCATOP = new OpTable(
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

    private static final OpTable LOGICOP = new OpTable(
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

    private static final OpTable RELOP = new OpTable(
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
        try {
            OpTable opTable = (OpTable) OpTableCombinations.class.getDeclaredField(enumOpTable.name()).get(OpTableCombinations.class);
            for (OpRow opRow : opTable.getOpRowList()) {
                boolean flag = true;
                Iterator<Type> itType = opRowToCheck.getOperandList().iterator();
                Iterator<Type> itTypeTable = opRow.getOperandList().iterator();

                while (itType.hasNext() || itTypeTable.hasNext())
                    if (!itType.next().getName().equals(itTypeTable.next().getName())) {
                        flag = false;
                        break;
                    }

                if (flag && opRowToCheck.getResult().getName().equals(opRow.getResult().getName()))
                    return true;
            }
            return false;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
