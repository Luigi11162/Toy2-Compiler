package esercitazione5;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main (String[] args) throws Exception {
        JTree tree;
        String filePath = "ProgramEs5.inp";
        FileInputStream stream = new FileInputStream(filePath);
        Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        Lexer scanner = new Lexer(reader);
        parser p = new parser(scanner);

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) p.parse().value;
        tree=new JTree(root);

        JFrame framePannello=new JFrame();
        framePannello.setSize(400, 400);
        JScrollPane treeView = new JScrollPane(tree);
        framePannello.add(treeView);
        framePannello.setVisible(true);

        while (!scanner.yyatEOF()){
            p.debug_parse();
        }
    }
}
