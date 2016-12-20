package simple.lang;

import simple.antlr.SimpleParser.FunctiondeclarationContext;

public class Function implements Evaluable{
	private String name;
	private Type type;
	private FunctiondeclarationContext context;
	private Object returnValue;
	
	public Function(String name, String type, FunctiondeclarationContext context) throws SimpleCompileException{
		if(type.equals("int "))
			this.type = Type.INT;
		else if (type.equals("str "))
			this.type = Type.STR;
		else
			throw new SimpleCompileException("Invalid type: '" + type + "' for function: " + name + ".");
		this.name = name;
		this.context = context;
		this.returnValue = null;
	};
	
	public Function(String name, Type type, FunctiondeclarationContext context){
		this.type = type;
		this.name = name;
		this.context = context;
		this.returnValue = null;
	};
	
	public FunctiondeclarationContext getContext(){
		return this.context;
	}
	
	public Function duplicate(){
		return new Function(this.name, this.type, this.context);
	}
	
	public String getName(){
		return name;
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public Object getValue() {
		return this.returnValue;
	}

	@Override
	public void setValue(Object value) {
		this.returnValue = value;
	}


	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		sb.append(this.name);
		sb.append(", ");
		sb.append(this.type);
		sb.append(">");
		return sb.toString();
	}

}
