package simple.lang;

public class Var implements Evaluable{
	
	private Type type;
	private String name;
	private Object value;
	
	public Var(String name, Type type, Object value){
		this.name = name;
		this.type = type;
		this.value = value;
	}
	
	public Var(String name, String type, Object value) throws SimpleCompileException{
		if(type.equals("int "))
			this.type = Type.INT;
		else if (type.equals("str "))
			this.type = Type.STR;
		else
			throw new SimpleCompileException("Invalid type: '" + type + "' for variable: " + name + ".");
		this.name = name;
		this.value = value;
	}
	
	public Var(String name, String type) throws SimpleCompileException{
		if(type.equals("int "))
			this.type = Type.INT;
		else if (type.equals("str "))
			this.type = Type.STR;
		else
			throw new SimpleCompileException("Invalid type: '" + type + "' for variable: " + name + ".");
		
		this.name = name;
		
		if(this.type == Type.INT)
			this.value = 0;
		else
			this.value = "";
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[")
		.append(this.name)
		.append(",")
		.append(this.type.toString())
		.append(",")
		.append(this.value)
		.append("]");
		
		return sb.toString();
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public Object getValue() {
		if(this.value == null){
			if(this.type == Type.INT)
				return 0;
			else
				return "";
		} else if (this.type == Type.INT)
			return Integer.valueOf(this.value.toString());
		return this.value.toString();
	}

	public String getName() {
		return this.name;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

}
