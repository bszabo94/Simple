package simple;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import simple.antlr.SimpleErrorListener;
import simple.antlr.SimpleLexer;
import simple.antlr.SimpleParser;

public class Interpreter {
	
	public void execute(String filename) throws IOException{
		File f = new File(filename);
		String input = readFile(f);
		parse(input);	
	}
	
	private static String readFile(File f) throws IOException{
		Scanner sc = new Scanner(f);
		StringBuilder sb = new StringBuilder();
		
		while(sc.hasNextLine()){
			sb.append(sc.nextLine());
			sb.append("\n");
		}
		
		sc.close();
		return sb.toString();
	}
	
	private static boolean parse(String inp){
		try{
			ANTLRInputStream input = new ANTLRInputStream(inp);
			SimpleLexer lexer = new SimpleLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			SimpleParser parser = new SimpleParser(tokens);
//			parser.setTrace(true);

			parser.removeErrorListeners();
			
			lexer.addErrorListener(new SimpleErrorListener());
			parser.addErrorListener(new SimpleErrorListener());
			
//			System.out.println(inp);
			ParseTree tree = parser.program();
			
			
			Compiler v = new Compiler();
			v.visit(tree);

			
			return true;
		} catch (Exception e){
			System.out.print("Syntax error: ");
			System.out.println(e.getMessage());
			return false;
		}
	
	}
}
