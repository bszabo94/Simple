package simple;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
	
		Interpreter i = new Interpreter();
		
		try {
			if(args.length == 0)
				i.execute("input.dat");
			else
				i.execute(args[1]);
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

	
	
}
