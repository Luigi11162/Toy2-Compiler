package esercitazione5.Test;

import esercitazione5.Lexer;
import esercitazione5.Nodes.ProgramOp;
import esercitazione5.Visitors.CodeVisitor;
import esercitazione5.Visitors.ScopeVisitor;
import esercitazione5.Visitors.TypeVisitor;
import esercitazione5.parser;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class Compiler {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception("File mancante");
        }
        String filePath = args[0];
        File file = new File(filePath);

        FileInputStream stream = new FileInputStream(filePath);
        Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        Lexer scanner = new Lexer(reader);
        parser p = new parser(scanner);

        try {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) p.parse().value;

            ((ProgramOp) root).accept(new ScopeVisitor());
            ((ProgramOp) root).accept(new TypeVisitor());
            ((ProgramOp) root).accept(new CodeVisitor(file.getName().substring(0, file.getName().lastIndexOf('.'))));
        } catch (Exception e) {
            System.out.println("Errore di compilazione nel file: " + filePath + "\n" + e.getMessage());
        }
    }

}
