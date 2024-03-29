package esercitazione5.Visitors.OpTable;

import esercitazione5.Nodes.Expr.Op;
import esercitazione5.Nodes.Type;
import esercitazione5.SymbolTable.SymbolType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OpTableCombinations {

    public enum EnumOpTable {
        UMINUSOP,
        NOTOP,
        ARITOP,
        CONCATOP,
        LOGICOP,
        RELOP,
        COMPOP,
        DIVOP
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
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("String"),
                                                    new Type("Integer")
                                            )
                                    ),
                                    new Type("String")
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Integer"),
                                                    new Type("String")
                                            )
                                    ),
                                    new Type("String")
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("String"),
                                                    new Type("Real")
                                            )
                                    ),
                                    new Type("String")
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Real"),
                                                    new Type("String")
                                            )
                                    ),
                                    new Type("String")
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("String"),
                                                    new Type("Boolean")
                                            )
                                    ),
                                    new Type("String")
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Boolean"),
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

    private static final OpTable COMPOP = new OpTable(
            "CompOp",
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
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Boolean"),
                                                    new Type("Boolean")
                                            )
                                    ),
                                    new Type("Boolean")
                            ),
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("String"),
                                                    new Type("String")
                                            )
                                    ),
                                    new Type("Boolean")
                            )
                    )
            )
    );
        private static final OpTable DIVOP = new OpTable(
            "DivOp",
            new ArrayList<>(
                    List.of(
                            new OpRow(
                                    new ArrayList<>(
                                            List.of(
                                                    new Type("Integer"),
                                                    new Type("Integer")
                                            )
                                    ),
                                    new Type("Real")
                            )
                    )
            )
    );

    public static SymbolType checkCombination(ArrayList<SymbolType> symbolTypeList, EnumOpTable enumOpTable) {
        try {
            //Prendo l'oggetto dato l'enum fornito
            OpTable opTable = (OpTable) OpTableCombinations.class.getDeclaredField(enumOpTable.name()).get(OpTableCombinations.class);
            //Controllo il match dei tipi forniti con quelli dichiarati in tabella
            for (OpRow opRow : opTable.getOpRowList()) {
                boolean flag = true;
                Iterator<Type> itType = symbolTypeList.stream().flatMap(symbolType -> symbolType.getOutTypeList().stream()).iterator();
                Iterator<Type> itTypeTable = opRow.getOperandList().iterator();

                while (itType.hasNext() && itTypeTable.hasNext())
                    if (!itType.next().getName().equals(itTypeTable.next().getName())) {
                        flag = false;
                    }

                if (itType.hasNext() || itTypeTable.hasNext())
                    throw new RuntimeException("Utilizzati più operandi di quelli consentiti");

                //Restituisce il tipo fornito dal match
                if (flag) {
                    return new SymbolType(new ArrayList<>(List.of(opRow.getResult())));
                }
            }
            throw new RuntimeException("I tipi "+symbolTypeList.stream().flatMap(symbolType -> symbolType.getOutTypeList().stream()).map(Type::getName).toList() + " non sono supportati");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}