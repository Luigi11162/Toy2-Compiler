package esercitazione5.Test;

import esercitazione5.Lexer;
import esercitazione5.Nodes.ProgramOp;
import esercitazione5.Visitors.ScopeVisitor;
import esercitazione5.parser;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class TestScopeVisitor {

    public static void main(String[] args) throws Exception {
        if (args.length != 1){
            throw new Exception("File mancante");
        }
        String filePath = args[0];
        FileInputStream stream = null;
        stream = new FileInputStream(filePath);
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
