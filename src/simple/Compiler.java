package simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simple.antlr.SimpleBaseVisitor;
import simple.antlr.SimpleParser.AssignmentContext;
import simple.antlr.SimpleParser.AtomContext;
import simple.antlr.SimpleParser.BackTrimContext;
import simple.antlr.SimpleParser.BlockContext;
import simple.antlr.SimpleParser.CompareopContext;
import simple.antlr.SimpleParser.DeclarationContext;
import simple.antlr.SimpleParser.ExpContext;
import simple.antlr.SimpleParser.FirstIndexOfContext;
import simple.antlr.SimpleParser.ForblockContext;
import simple.antlr.SimpleParser.FromNthCharContext;
import simple.antlr.SimpleParser.FromNthToMthCharContext;
import simple.antlr.SimpleParser.FrontTrimContext;
import simple.antlr.SimpleParser.FunctioncallContext;
import simple.antlr.SimpleParser.FunctiondeclarationContext;
import simple.antlr.SimpleParser.IfblockContext;
import simple.antlr.SimpleParser.LastIndexOFContext;
import simple.antlr.SimpleParser.LiteralContext;
import simple.antlr.SimpleParser.MexpContext;
import simple.antlr.SimpleParser.NthCharContext;
import simple.antlr.SimpleParser.PexpContext;
import simple.antlr.SimpleParser.ProgramContext;
import simple.antlr.SimpleParser.ReturnstatementContext;
import simple.antlr.SimpleParser.StatementContext;
import simple.antlr.SimpleParser.ToNthCharContext;
import simple.antlr.SimpleParser.ValueContext;
import simple.antlr.SimpleParser.VardeclarationContext;
import simple.antlr.SimpleParser.WhileblockContext;
import simple.lang.Evaluable;
import simple.lang.Function;
import simple.lang.ReferenceTable;
import simple.lang.SimpleCompileException;
import simple.lang.Type;
import simple.lang.Var;

public class Compiler extends SimpleBaseVisitor<Object>{
	
	//------------------------------VISITORS-----------------------------------------------
	
	List<ReferenceTable> referenceTable;
	List<Function> currentlyExecuting;
	Function currentFunction;
	Object returnValue;
	List<String> warnings;
	private static BufferedReader br;

	public Compiler() {
		referenceTable = new ArrayList<ReferenceTable>();
		currentlyExecuting = new ArrayList<Function>();
		warnings = new ArrayList<String>();
		currentFunction = null;
		returnValue = null;
		br = new BufferedReader(new InputStreamReader(System.in));		
		
	}
	
	@Override
	public Object visitProgram(ProgramContext ctx) {
		enterScope();
		FunctiondeclarationContext main = null;
		
		for(DeclarationContext d: ctx.declaration()){
			if(d.functiondeclaration() != null && d.functiondeclaration().NAME().getText().equals("main")){
				main = d.functiondeclaration();
			}
		}
		
		if(main == null)
			abort("Error: Missing function main()");
		
		for(DeclarationContext d: ctx.declaration()){
			visitDeclaration(d);
		}
		
		Object exit = callMain(main);
		printWarns();
		System.out.println("exit = " + exit);
		
		exitScope();
		return false;
	}

	@Override
	public Object visitDeclaration(DeclarationContext ctx) {
		
		if(ctx.functiondeclaration() != null){
			visitFunctiondeclaration(ctx.functiondeclaration());
		} else if (ctx.vardeclaration() != null){
			visitVardeclaration(ctx.vardeclaration());
		} else {
			abort("Something unexpected happened at: '" + ctx.getText() + "'.");
		}
		
		return true;
	}

	@Override
	public Object visitVardeclaration(VardeclarationContext ctx){
		String type = ctx.type().getText();
		String name = ctx.NAME().getText();
		
		if(getScope().get(name) != null)
			abort("Reference '" + name + "' is already in use.");
		
		try{
			getScope().add(name, new Var(name, type));
		}catch (SimpleCompileException e) {
			abort(e.getMessage());
		}

		return true;
	}
	
	@Override
	public Object visitFunctiondeclaration(FunctiondeclarationContext ctx) {
		String type = ctx.type().getText();
		String name = ctx.NAME().getText();
		
		if(name.equals("len") || name.equals("print") || name.equals("println") || name.equals("read") || name.equals("kmp") || name.equals("bm") || name.equals("concat"))
			abort("System function '" + name + "' cannot be overrided.");
		
		if(getScope().get(name) != null)
			abort("Reference '" + name + "' is already in use.");
		
		try{
			getScope().add(name, new Function(name, type, ctx));
		}catch (SimpleCompileException e) {
			abort(e.getMessage());
		}
		
		return true;
	}
	
	@Override
	public Object visitBlock(BlockContext ctx) {
		
		for(StatementContext s: ctx.statement()){
			visitStatement(s);
			if(currentFunction != null){
				if(currentFunction.getValue() != null){
					//this is just a warning
					if(ctx.statement().indexOf(s) < (ctx.statement().size() - 1))
						warning("Unreached code found, after 'return' statement in function '" + currentFunction.getName() + "'.");
					
					break;
				}				
			}
		}
				
		return true;
	}
	
	@Override
	public Object visitStatement(StatementContext ctx) {

		if(ctx.declaration() != null){
			visitDeclaration(ctx.declaration());
			return true;
		}
		
		if(ctx.assignment() != null){
			visitAssignment(ctx.assignment());
			return true;
		}
		
		if(ctx.value() != null){
			visitValue(ctx.value());
			return true;
		}

		
		if(ctx.returnstatement() != null){
			currentFunction.setValue(visitReturnstatement(ctx.returnstatement()));
			return true;
		}
		
		
		if(ctx.ifblock() != null){
			visitIfblock(ctx.ifblock());
			return true;
		}
		
		if(ctx.whileblock() != null){
			visitWhileblock(ctx.whileblock());
			return true;
		}
		
		if(ctx.forblock() != null){
			visitForblock(ctx.forblock());
			return true;
		}
		
		abort("Something unexpected happened at : " + ctx.getText());
					
		return true;
	}
	
	@Override
	public Object visitAssignment(AssignmentContext ctx) {
		Object value;
		Var var = findVar(ctx.NAME().getText()); // Left side of assignment

		if(ctx.value() != null)
			value = visitValue(ctx.value());
		else
			value = visitCompareop(ctx.compareop());
		
		if(var.getType() == Type.INT){
			try{
				value = Integer.valueOf(value.toString());
			}catch (Exception e) {
				abort("Conversion error at: " + ctx.getText() + " . Unable to convert '" + value.toString() + "' to INT.");
			}
		} else {
			value = value.toString();
		}
			
		var.setValue(value);
		
		return true;
	}
	
	@Override
	public Object visitIfblock(IfblockContext ctx) {
		enterScope();
		Object condition = null;
		if(ctx.value() != null)
			condition = visitValue(ctx.value());

		if(ctx.compareop() != null)	
			condition = visitCompareop(ctx.compareop());


		if (condition == null)
			abort("Invalid 'IF' condition given at '" + ctx.getText() + "'.");
		
		if(condition.getClass() == Integer.class){
			if((Integer) condition == 0){
				//when the if statement is false
				if(ctx.block().size() > 1){
					visitBlock(ctx.block(1));
				}
			} else {
				//when the if statement is true
				visitBlock(ctx.block(0));
			}
		} else {
			if(condition.toString().equals("")){
				//when the if statement is false
				if(ctx.block().size() > 1){
					visitBlock(ctx.block(1));
				}
			} else {
				//when the if statement is true
				visitBlock(ctx.block(0));
			}
		}
		
		exitScope();
		return true;
	}
	
	
	@Override
	public Object visitWhileblock(WhileblockContext ctx) {
		enterScope();
		Object condition = null;
		
		getScope().setCycle(true);
		
		while(true){
			if(ctx.value() != null)
				condition = visitValue(ctx.value());

			if(ctx.compareop() != null)	
				condition = visitCompareop(ctx.compareop());
			
			
			if(condition.getClass() == Integer.class){
				if((Integer) condition == 0){
					//when the if statement is false
					break;
				} else {
					//when the if statement is true
					visitBlock(ctx.block());
					if(getScope().isCycle())
						continue;
					else
						break;
				}
			} else {
				if(condition.toString().equals("")){
					//when the if statement is false
					break;
				} else {
					//when the if statement is true
					visitBlock(ctx.block());
					if(getScope().isCycle()){
						System.out.println("whileblock: continue");
						continue;
					}
					else{
						System.out.println("whileblock: break");
						break;
					}
						
				}
			}
			
			
		}
		
		
		exitScope();
//		inLoop = false;
		return true;
	}
	
	
	@Override
	public Object visitForblock(ForblockContext ctx) {
		enterScope();
		
		String name = ctx.NAME().getText();
		String type = ctx.INTTYPE().getText();
		
		try {
			getScope().add(name, new Var(name, type));
		} catch (SimpleCompileException e) {
			abort(e.getMessage());
		}
		
		Integer beginValue = Integer.valueOf(ctx.NUMBER(0).getText());
		Integer endValue = Integer.valueOf(ctx.NUMBER(1).getText());
		Integer increment = endValue < beginValue ? -1 : 1 ;
		
		if(beginValue == endValue)
			warning("Innecifent 'For' loop at: '" + ctx.getText() + "'.");
		
		Var iterator = findVar(name);
		iterator.setValue(beginValue);
		
		getScope().setCycle(true);
		
		while(iterator.getValue() != endValue){
			visitBlock(ctx.block());
			iterator.setValue((Integer) iterator.getValue() + increment);

			if(getScope().isCycle())
				continue;
			else
				break;
			
		}
		
		exitScope();

		return true;
	}
	
	@Override
	public Object visitValue(ValueContext ctx) {

		if(ctx.literal() != null)
			return visitLiteral(ctx.literal());
		
		if(ctx.functioncall() != null)
			return visitFunctioncall(ctx.functioncall());
		
		if(ctx.exp() != null){
			return visitExp(ctx.exp());
		}
		
		if(ctx.NAME() != null){
			return findVar(ctx.NAME().getText()).getValue();
		}
		
		if(ctx.strop() != null){
			
			try{
				return visitNthChar((NthCharContext) ctx.strop());
			}catch (Exception e) {}
			try{
				return visitToNthChar((ToNthCharContext) ctx.strop());
			}catch (Exception e) {}
			try{
				return visitFromNthChar((FromNthCharContext) ctx.strop());
			}catch (Exception e) {}
			try{
				return visitFromNthToMthChar((FromNthToMthCharContext) ctx.strop());
			}catch (Exception e) {}
			try{
				return visitFirstIndexOf((FirstIndexOfContext) ctx.strop());
			}catch (Exception e) {}
			try{
				return visitLastIndexOF((LastIndexOFContext) ctx.strop());
			}catch (Exception e) {}
			try{
				return visitFrontTrim((FrontTrimContext) ctx.strop());
			}catch (Exception e) {}
			try{
				return visitBackTrim((BackTrimContext) ctx.strop());
			}catch (Exception e) {}

			
			
		}
		
		abort("Something unexpected happened at: " + ctx.getText());
		return null;
	}
	
	@Override
	public Integer visitExp(ExpContext ctx) {
		Integer retValue = visitMexp(ctx.mexp(0));
		
		for(int i=0; i<ctx.mexp().size()-1; i++){
			if(ctx.PLUSMINUS(i).getText().equals("+")){
				retValue += visitMexp(ctx.mexp(i+1));
			} else {
				retValue -= visitMexp(ctx.mexp(i+1));
			}
		}
		return retValue;
	}
	
	@Override
	public Integer visitMexp(MexpContext ctx) {
		Integer retValue = visitPexp(ctx.pexp(0));
		
		for(int i=0; i<ctx.pexp().size()-1; i++){
			if(ctx.TIMESDIVMOD(i).getText().equals("*")){
				retValue *= visitPexp(ctx.pexp(i+1));
			} else if(ctx.TIMESDIVMOD(i).getText().equals("//")){
				retValue /= visitPexp(ctx.pexp(i+1));
			} else {
				retValue = retValue % visitPexp(ctx.pexp(i+1));
			}
		}

		return retValue;
	}
	
	
	@Override
	public Integer visitPexp(PexpContext ctx) {
		Integer retValue = visitAtom(ctx.atom(0));
		
		for(int i=0; i<ctx.atom().size()-1; i++){
			retValue = (int) Math.pow(retValue, visitAtom(ctx.atom(i+1)));
		}
		return retValue;
	}
	
	@Override
	public Integer visitAtom(AtomContext ctx) {
		if(ctx.NUMBER() != null)
			return Integer.valueOf(ctx.getText());
		
		if(ctx.NAME() != null){
			Var var = findVar(ctx.NAME().getText());

			try{
				return (Integer) var.getValue();
			}catch (Exception e) {
				abort("Wrong type for operation at variable '" + var.getName() + "'.");
			}
		}
		
		if(ctx.functioncall() != null){
			if(findFunction(ctx.functioncall().NAME().getText()).getType() != Type.INT)
				abort("Invalid type for arithmetic expression: " + ctx.functioncall().NAME().getText() + ".");
			return (Integer) visitFunctioncall(ctx.functioncall());
		}
		
		if(ctx.strop() != null){
			try{
				return (Integer) visitFirstIndexOf((FirstIndexOfContext) ctx.strop());
			}catch (Exception e) {}
			try{
				return (Integer) visitLastIndexOF((LastIndexOFContext) ctx.strop());
			}catch (Exception e) {}
			
			abort("Invalid type for arithmetic expression: " + ctx.strop().getText() + ".");
		}
			
		return visitExp(ctx.exp());
	}
	
	@Override
	public Object visitCompareop(CompareopContext ctx) {
		Integer returnValue = null;
		
		Object val1, val2;
		
		val1 = visitValue(ctx.value(0));
		val2 = visitValue(ctx.value(1));
		
		if(val1.getClass() != val2.getClass())
			abort("Value '" + val1.toString() + "' and value '" + val2.toString() + "' must be the same type.");
		
		if(val1.getClass() == Integer.class){
			switch(ctx.COP().getText()){
				case "==": if(val1.equals(val2)){
						returnValue = 1;
					} else {
						returnValue = 0;
					}
					break;
				case "!=": if(val1.equals(val2)){
					returnValue = 0;
				} else {
					returnValue = 1;
				}
				break;
				case ">": if((Integer) val1 > (Integer) val2){
					returnValue = 1;
				} else {
					returnValue = 0;
				}
				break;
				case "<": if((Integer) val1 < (Integer) val2){
					returnValue = 1;
				} else {
					returnValue = 0;
				}
				break;
				case ">=": if((Integer) val1 < (Integer) val2){
					returnValue = 0;
				} else {
					returnValue = 1;
				}
				break;
				case "<=": if((Integer) val1 > (Integer) val2){
					returnValue = 0;
				} else {
					returnValue = 1;
				}
				break;
				default:
					abort("Something unexpected happened using compare operator '" + ctx.COP().getText() + "'.");
			}
		} else {
			int compareOrder = ((String) val1).compareTo((String) val2); 
			switch(ctx.COP().getText()){
				case "==": if(val1.equals(val2)){
						returnValue = 1;
					} else {
						returnValue = 0;
					}
					break;
				case "!=": if(val1.equals(val2)){
					returnValue = 0;
				} else {
					returnValue = 1;
				}
				break;
				case ">": if(compareOrder > 0){
					returnValue = 1;
				} else {
					returnValue = 0;
				}
				break;
				case "<": if(compareOrder < 0){
					returnValue = 1;
				} else {
					returnValue = 0;
				}
				break;
				case ">=": if(compareOrder < 0){
					returnValue = 0;
				} else {
					returnValue = 1;
				}
				break;
				case "<=": if(compareOrder > 0){
					returnValue = 0;
				} else {
					returnValue = 1;
				}
				break;
				default:
					abort("Something unexpected happened using compare operator '" + ctx.COP().getText() + "'.");
			}
		}
		
		if(returnValue == null)
			abort("Something unexpected happened at condition '" + ctx.getText() + "'.");
		return returnValue;
	}
	
	
	@Override
	public Object visitFunctioncall(FunctioncallContext ctx) {
		/*****System Functions*****/
		if(ctx.NAME().getText().equals("print")){
			if(ctx.callargs() == null || ctx.callargs().value().size() != 1)
				abort("Wrong calling arguments: " + ctx.callargs().getText() + " for function 'print'.");
			
			String toPrint = visitValue(ctx.callargs().value(0)).toString();
			System.out.print(toPrint);
			
			return toPrint;
		}
		
		if(ctx.NAME().getText().equals("println")){
			if(ctx.callargs() == null || ctx.callargs().value().size() != 1)
				abort("Wrong calling arguments: " + ctx.callargs().getText() + " for function 'println'.");
			
			String toPrint = visitValue(ctx.callargs().value(0)).toString();
			System.out.println(toPrint);
			
			return toPrint;
		}
		
		if(ctx.NAME().getText().equals("read")){
			if(ctx.callargs() != null)
				abort("Wrong calling arguments: " + ctx.callargs().getText() + " for function 'read'.");
			String input = null;
			try {
				input = br.readLine();
			} catch (IOException e) {
				System.out.println("E: " + e.getMessage());
				e.printStackTrace();
			}
			return input;
		}
		
		if(ctx.NAME().getText().equals("len")){
			if(ctx.callargs() == null || ctx.callargs().value().size() != 1)
				abort("Wrong calling arguments: " + ctx.callargs().getText() + " for function 'print'.");
			
			return (Integer) visitValue(ctx.callargs().value(0)).toString().length();
				
		}
		
		if(ctx.NAME().getText().equals("kmp")){
			if(ctx.callargs() == null || ctx.callargs().value().size() != 2)
				abort("Wrong calling arguments: " + ctx.callargs().getText() + " for function 'print'.");
			
			Object val1 = visitValue(ctx.callargs().value(0));
			Object val2 = visitValue(ctx.callargs().value(1));
			
			if(val1.getClass() != String.class || val2.getClass() != String.class)
				abort("Wrong calling arguments: " + ctx.callargs().getText() + " for function 'print'.");
			
			return KMPsearch((String) val1, (String) val2);
			
		}
		
		if(ctx.NAME().getText().equals("bm")){
			if(ctx.callargs() == null || ctx.callargs().value().size() != 2)
				abort("Wrong calling arguments: " + ctx.callargs().getText() + " for function 'print'.");
			
			Object val1 = visitValue(ctx.callargs().value(0));
			Object val2 = visitValue(ctx.callargs().value(1));
			
			if(val1.getClass() != String.class || val2.getClass() != String.class)
				abort("Wrong calling arguments: " + ctx.callargs().getText() + " for function 'print'.");
			
			return BMsearch((String) val1, (String) val2);
		}
		
		if(ctx.NAME().getText().equals("concat")){
			if(ctx.callargs() == null || ctx.callargs().value().size() != 2)
				abort("Wrong calling arguments: " + ctx.callargs().getText() + " for function 'print'.");
			
			Object val1 = visitValue(ctx.callargs().value(0));
			Object val2 = visitValue(ctx.callargs().value(1));
			
			if(val1.getClass() != String.class || val2.getClass() != String.class)
				abort("Wrong calling arguments: " + ctx.callargs().getText() + " for function 'print'.");
			
			return new String((String) val1 +  (String) val2);
		}
		
		/*****System Functions*****/
		
		/*****Storing Values of Args*****/
		List<Object> storedValues = new ArrayList<Object>();
		if(ctx.callargs() != null)
			for(int i=0; i<ctx.callargs().value().size(); i++)
			storedValues.add(visitValue(ctx.callargs().value(i)));
		
		enterScope();
		
		currentlyExecuting.add(findFunction(ctx.NAME().getText()).duplicate());
		currentFunction = currentlyExecuting.get(currentlyExecuting.size()-1);
		
		FunctiondeclarationContext decCtx = currentFunction.getContext();
		Object returnValue = null;
		
		if(decCtx.args() != null){
			for(VardeclarationContext v: decCtx.args().vardeclaration()){
				visitVardeclaration(v);
			}
			
			if(ctx.callargs() == null)
				abort("Wrong calling arguments: " + ctx.callargs().getText() + " for function '" + ctx.NAME().getText() + "'.");
			if(ctx.callargs().value().size() != decCtx.args().vardeclaration().size())
				abort("Wrong calling arguments: " + ctx.callargs().getText() + " for function '" + ctx.NAME().getText() + "'.");
			
			for(int i=0; i<ctx.callargs().value().size(); i++){
				
				Var var = findVar(decCtx.args().vardeclaration(i).NAME().getText());
				Object value = storedValues.get(i);

				if(var.getValue().getClass() == value.getClass()){
					var.setValue(value);
				} else
					abort("Wrong calling argument types: " + ctx.callargs().getText() + " for function '" + ctx.NAME().getText() + "'.");
			}
		} else {
			if(ctx.callargs() != null)
				abort("Wrong calling arguments: " + ctx.callargs().getText() + " for function '" + ctx.NAME().getText() + "'.");
		}
		
		visitBlock(decCtx.block());
	
		exitScope();
		
		if(currentFunction.getValue() == null)
			abort("Executed function '" + currentFunction.getName() + "' without accessing any 'return' statement.");
		
		if(currentFunction.getType() == Type.INT){
			try{
				returnValue = Integer.valueOf(currentFunction.getValue().toString());
			}catch (Exception e) {
				abort("Invalid return value for function '" + currentFunction.getName() + "'. Value: " + currentFunction.getValue().toString() + " cannot be converted to INT.");
			}
		}
		else{
			returnValue = currentFunction.getValue().toString();
		}
			
		currentlyExecuting.remove(currentlyExecuting.size()-1);
		
		if(!currentlyExecuting.isEmpty())
			currentFunction = currentlyExecuting.get(currentlyExecuting.size()-1);
		else
			currentFunction = null;
		
		return returnValue;
	}
	
	private Object callMain(FunctiondeclarationContext ctx) {
		enterScope();
		
		try {
			getScope().add("print", new Function("print", "int ", null));
			getScope().add("println", new Function("println", "int ", null));
			getScope().add("read", new Function("read", "str ", null));
			getScope().add("len", new Function("len", "int ", null));
			getScope().add("kmp", new Function("kmp", "int ", null));
			getScope().add("bm", new Function("bm", "int ", null));
			getScope().add("concat", new Function("concat", "str ", null));
		} catch (SimpleCompileException e1) {
			abort(e1.getMessage());
		}
		
		currentlyExecuting.add(findFunction(ctx.NAME().getText()).duplicate());
		currentFunction = currentlyExecuting.get(currentlyExecuting.size()-1);
		FunctiondeclarationContext decCtx = currentFunction.getContext();
		Object returnValue = null;
		
		if(decCtx.args() != null){
			for(VardeclarationContext v: decCtx.args().vardeclaration()){
				visitVardeclaration(v);
			}
		}
		
		
		visitBlock(decCtx.block());
	
		exitScope();
		
		if(currentFunction.getValue() == null)
			abort("Executed function '" + currentFunction.getName() + "' without accessing any 'return' statement.");
		
		if(currentFunction.getType() == Type.INT){
			try{
				returnValue = Integer.valueOf(currentFunction.getValue().toString());
			}catch (Exception e) {
				abort("Invalid return value for function '" + currentFunction.getName() + "'. Value: " + currentFunction.getValue().toString() + " cannot be converted to INT.");
			}
		}
		else{
			returnValue = currentFunction.getValue().toString();
		}
			
		currentlyExecuting.remove(currentlyExecuting.size()-1);
		
		if(!currentlyExecuting.isEmpty())
			currentFunction = currentlyExecuting.get(currentlyExecuting.size()-1);
		else
			currentFunction = null;
		
		try {
			br.close();
		} catch (IOException e) {
			abort(e.getMessage());
		}
		return returnValue;
	}
	
	@Override
	public Object visitReturnstatement(ReturnstatementContext ctx) {
		if(referenceTable.size() > 1 && getScope().isCycle()){
			getScope().setCycle(false);
		}
		return visitValue(ctx.value());
	}
	
	
	@Override
	public Object visitLiteral(LiteralContext ctx) {

		if(ctx.NUMBER() != null)
			return Integer.valueOf(ctx.NUMBER().getText());
		
		if(ctx.STRING() != null){
			String val = ctx.STRING().getText();
			return val.substring(1, val.length() -1);
		}
		
		return null;
	}
	
	@Override
	public Object visitNthChar(NthCharContext ctx) {
		Object val1, val2;
		String r = new String();
		
		if(ctx.NAME() != null)
			val1 = findVar(ctx.NAME().getText()).getValue();
		else{
			String s = ctx.STRING().getText();
			val1 = s.substring(1, s.length() -1);
		}
			
		val2 = visitValue(ctx.value());
		
		if(val1.getClass() != String.class || val2.getClass() != Integer.class)
			abort(ctx.getText() + " has wrong type of arguments for operation 'charAtIndex'.");
		
		try{
			r = String.valueOf(val1.toString().charAt(Integer.valueOf(val2.toString())));
		}catch (Exception e) {
			abort("At operation " + ctx.getText() + " , " + e.getMessage());
		}
		
		return r;	
	}
	
	@Override
	public Object visitToNthChar(ToNthCharContext ctx) {
		Object val1, val2;
		String r = new String();
		
		if(ctx.NAME() != null)
			val1 = findVar(ctx.NAME().getText()).getValue();
		else{
			String s = ctx.STRING().getText();
			val1 = s.substring(1, s.length() -1);
		}
			
		val2 = visitValue(ctx.value());
		
		if(val1.getClass() != String.class || val2.getClass() != Integer.class)
			abort(ctx.getText() + " has wrong type of arguments for operation 'charAtIndex'.");
		
		try{
			r = val1.toString().substring(0, (Integer) val2);
		}catch (Exception e) {
			abort("At operation " + ctx.getText() + " , " + e.getMessage());
		}
		
		
		return r;
	}
	
	@Override
	public Object visitFromNthChar(FromNthCharContext ctx) {
		Object val1, val2;
		String r = new String();
		
		if(ctx.NAME() != null)
			val1 = findVar(ctx.NAME().getText()).getValue();
		else{
			String s = ctx.STRING().getText();
			val1 = s.substring(1, s.length() -1);
		}
			
		val2 = visitValue(ctx.value());
		
		if(val1.getClass() != String.class || val2.getClass() != Integer.class)
			abort(ctx.getText() + " has wrong type of arguments for operation 'charAtIndex'.");
		
		try{
			r = val1.toString().substring((Integer) val2, val1.toString().length());
		}catch (Exception e) {
			abort("At operation " + ctx.getText() + " , " + e.getMessage());
		}

		return r;
	}
	
	@Override
	public Object visitFromNthToMthChar(FromNthToMthCharContext ctx) {
		Object val1, val2, val3;
		String r = new String();
		
		if(ctx.NAME() != null)
			val1 = findVar(ctx.NAME().getText()).getValue();
		else{
			String s = ctx.STRING().getText();
			val1 = s.substring(1, s.length() -1);
		}
			
		val2 = visitValue(ctx.value(0));
		val3 = visitValue(ctx.value(1));
		
		if(val1.getClass() != String.class || val2.getClass() != Integer.class || val3.getClass() != Integer.class)
			abort(ctx.getText() + " has wrong type of arguments for operation 'charAtIndex'.");
		
		try{
			r = val1.toString().substring((Integer) val2, (Integer) val3);
		}catch (Exception e) {
			abort("At operation " + ctx.getText() + " , " + e.getMessage());
		}

		return r;
	}
	
	@Override
	public Object visitFirstIndexOf(FirstIndexOfContext ctx) {
		Object val1, val2;
		
		if(ctx.NAME() != null)
			val1 = findVar(ctx.NAME().getText()).getValue();
		else{
			String s = ctx.STRING().getText();
			val1 = s.substring(1, s.length() -1);
		}
			
		val2 = visitValue(ctx.value());
		
		if(val1.getClass() != String.class || val2.getClass() != String.class)
			abort(ctx.getText() + " has wrong type of arguments for operation 'charAtIndex'.");
		
		
		
		return (Integer) val1.toString().indexOf(val2.toString());
	}
	
	@Override
	public Object visitLastIndexOF(LastIndexOFContext ctx) {
		Object val1, val2;
		
		if(ctx.NAME() != null)
			val1 = findVar(ctx.NAME().getText()).getValue();
		else{
			String s = ctx.STRING().getText();
			val1 = s.substring(1, s.length() -1);
		}
			
		val2 = visitValue(ctx.value());
		
		if(val1.getClass() != String.class || val2.getClass() != String.class)
			abort(ctx.getText() + " has wrong type of arguments for operation 'charAtIndex'.");

		return (Integer) val1.toString().lastIndexOf(val2.toString());
	}
	
	@Override
	public Object visitFrontTrim(FrontTrimContext ctx) {
		Object val1, val2;
		
		if(ctx.NAME() != null)
			val1 = findVar(ctx.NAME().getText()).getValue();
		else{
			String s = ctx.STRING().getText();
			val1 = s.substring(1, s.length() -1);
		}
			
		val2 = visitValue(ctx.value());
		
		if(val1.getClass() != String.class || val2.getClass() != String.class)
			abort(ctx.getText() + " has wrong type of arguments for operation 'charAtIndex'.");
		
		String r = new String();
		
		int indx = val1.toString().indexOf(val2.toString());
		
		if(indx != 0)
			return new String("");
		
		r = val1.toString().substring(val2.toString().length(), val1.toString().length());
		
		findVar(ctx.NAME().getText()).setValue(r);
		
		
		return val2.toString();
	}
	
	
	@Override
	public Object visitBackTrim(BackTrimContext ctx) {
		Object val1, val2;
		
		if(ctx.NAME() != null)
			val1 = findVar(ctx.NAME().getText()).getValue();
		else{
			String s = ctx.STRING().getText();
			val1 = s.substring(1, s.length() -1);
		}
			
		val2 = visitValue(ctx.value());
		
		if(val1.getClass() != String.class || val2.getClass() != String.class)
			abort(ctx.getText() + " has wrong type of arguments for operation 'charAtIndex'.");
		
		String r = new String();
		
		int indx = val1.toString().lastIndexOf(val2.toString());
		if(indx != (val1.toString().length() - val2.toString().length()) || indx == -1)
			return new String("");
		
		r = val1.toString().substring(0, indx);
		
		findVar(ctx.NAME().getText()).setValue(r);

		return val2.toString();
	}	
	
	//------------------------------NOT VISITORS-----------------------------------------------
	
	@SuppressWarnings("unused")
	private void printAllRefs(){
		System.out.println(" --- All References ---");
		System.out.println(getAllReferences());
		System.out.println("---- All References ----");
	}
	
	@SuppressWarnings("unused")
	private void printVars(){
		System.out.println(" --- All Variables ---");
		for(Evaluable e : getAllReferences().values()){
			if(e.getClass() == Var.class)
				System.out.println(e);
		}
		System.out.println("---- All Variables ----");
	}
	
	@SuppressWarnings("unused")
	private void printFunctions(){
		System.out.println(" --- All Functions ---");
		for(Evaluable e : getAllReferences().values()){
			if(e.getClass() == Function.class)
				System.out.println(e);
		}
		System.out.println("---- All Functions ----");
	}
	
	private Var findVar(String ref){
		
		Evaluable temp = getAllReferences().get(ref);
		
		if (temp != null)
			return (Var) temp;
		else
			abort("Cannot find Variable with reference: '" + ref + "'");
		return null;
	}
	
	private Function findFunction(String ref){
		Evaluable temp = getAllReferences().get(ref);
		
		if(temp != null)
			return (Function) temp;
		else
			abort("Cannot find Function with reference: '" + ref + "'");
		return null;
	}
	
	private void enterScope(){
		referenceTable.add(new ReferenceTable());
	}
	
	private ReferenceTable getScope(){
		return referenceTable.get(referenceTable.size()-1);
	}
	
	private void exitScope(){
		if(referenceTable.isEmpty())
			abort("Something with the reference table");
		referenceTable.remove(referenceTable.size()-1);
	}
	
	private Map<String, Evaluable> getAllReferences(){
		Map<String, Evaluable> unitedRefs = new HashMap<String, Evaluable>();
		
		for(ReferenceTable rt: referenceTable){
			unitedRefs.putAll(rt.getReferences());
		}
		
		return unitedRefs;
	}
	
	private void warning(String e){
		warnings.add(e);
	}
	
	private void printWarns(){
		for(String s: warnings){
			System.out.println("Warning " + (warnings.indexOf(s)+1) + ": " + s);
		}
	}
	
	private static void abort(String e){
		try {
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(e);
		System.exit(0);
	}
	
	/************************KMP*******************************/
	

    static int KMPsearch(String pattern, String text) {
        int[] lsp = computeLspTable(pattern);
	        int j = 0;  
        for (int i = 0; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {                
                j = lsp[j - 1];
            }
            if (text.charAt(i) == pattern.charAt(j)) {                
                j++;
                if (j == pattern.length())
                    return i - (j - 1);
            }
        }
        return -1;  // Not found
    }
	    
    static int[] computeLspTable(String pattern) {
        int[] lsp = new int[pattern.length()];
        lsp[0] = 0; 
        for (int i = 1; i < pattern.length(); i++) {           
            int j = lsp[i - 1];
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j))
                j = lsp[j - 1];
            if (pattern.charAt(i) == pattern.charAt(j))
                j++;
            lsp[i] = j;
        }
        return lsp;
    }
    
    /**************************BM**********************************/
    
    
    public static int BMsearch(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }
        int charTable[] = makeCharTable(needle);
        int offsetTable[] = makeOffsetTable(needle);
        for (int i = needle.length()- 1, j; i < haystack.length();) {
            for (j = needle.length() - 1; needle.charAt(j) == haystack.charAt(i); --i, --j) {
                if (j == 0) {
                    return i;
                }
            }
           
            i += Math.max(offsetTable[needle.length() - 1 - j], charTable[haystack.charAt(i)]);
        }
        return -1;
    }
    
 
    private static int[] makeCharTable(String needle) {
        final int ALPHABET_SIZE = 256;
        int[] table = new int[ALPHABET_SIZE];
        for (int i = 0; i < table.length; ++i) {
            table[i] = needle.length();
        }
        for (int i = 0; i < needle.length() - 1; ++i) {
            table[needle.charAt(i)] = needle.length() - 1 - i;
        }
        return table;
    }
 
    private static int[] makeOffsetTable(String needle) {
        int[] table = new int[needle.length()];
        int lastPrefixPosition = needle.length();
        for (int i = needle.length() - 1; i >= 0; --i) {
            if (isPrefix(needle, i + 1)) {
                lastPrefixPosition = i + 1;
            }
            table[needle.length() - 1 - i] = lastPrefixPosition - i + needle.length() - 1;
        }
        for (int i = 0; i < needle.length() - 1; ++i) {
            int slen = suffixLength(needle, i);
            table[slen] = needle.length() - 1 - i + slen;
        }
        return table;
    }
 
    private static boolean isPrefix(String needle, int p) {
        for (int i = p, j = 0; i < needle.length(); ++i, ++j) {
            if (needle.charAt(i) != needle.charAt(j)) {
                return false;
            }
        }
        return true;
    }
  
    private static int suffixLength(String needle, int p) {
        int len = 0;
        for (int i = p, j = needle.length() - 1;
                 i >= 0 && needle.charAt(i) == needle.charAt(j); --i, --j) {
            len += 1;
        }
        return len;
    }
   	
}
