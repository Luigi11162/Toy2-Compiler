package esercitazione5.Test;

import esercitazione5.Lexer;
import esercitazione5.Nodes.Expr.Op;
import esercitazione5.Nodes.ProgramOp;
import esercitazione5.Nodes.Type;
import esercitazione5.Visitors.OpTable.OpRow;
import esercitazione5.Visitors.OpTable.OpTableCombinations;
import esercitazione5.Visitors.TypeVisitor;
import esercitazione5.parser;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TestTypeVisitor {

    public static void main(String[] args) throws Exception {
        if (args.length != 1){
            throw new Exception("File mancante");
        }
        String filePath = args[0];
        FileInputStream stream = new FileInputStream(filePath);
        Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        Lexer scanner = new Lexer(reader);
        parser p = new parser(scanner);

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) p.parse().value;

        ((ProgramOp) root).accept(new TypeVisitor());

        int a = 0;
        while (!scanner.yyatEOF()) {
            p.debug_parse();
        }
    }


}
