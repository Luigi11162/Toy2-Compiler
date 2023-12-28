package esercitazione5;

import esercitazione5.Nodes.ProgramOp;
import esercitazione5.Visitors.ScopeVisitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class TestScopeVisitor {

    public static void main(String[] args) throws Exception {
        String filePath = "ProgramEs5.inp";
        FileInputStream stream = new FileInputStream(filePath);
        Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        Lexer scanner = new Lexer(reader);
        parser p = new parser(scanner);

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) p.parse().value;

        ((ProgramOp) root).accept(new ScopeVisitor());
        int a = 0;
        while (!scanner.yyatEOF()) {
            p.debug_parse();
        }
    }


}
