package simple.lang;

import java.util.HashMap;
import java.util.Map;

public class ReferenceTable {
	private Map<String, Evaluable> references;
	private boolean isCycle;
	
	public ReferenceTable(){
		references = new HashMap<String, Evaluable>();
		isCycle = false;
	}
	
	public boolean isCycle() {
		return isCycle;
	}

	public void setCycle(boolean isCycle) {
		this.isCycle = isCycle;
	}

	public boolean isDeclared(String ref){
		return references.containsKey(ref);
	}
	
	public Evaluable get(String ref){
		return references.get(ref);
	}
	
	public void add(String ref, Evaluable eval){
		references.put(ref, eval);
	}
	
	public Map<String, Evaluable> getReferences(){
		return this.references;
	}
	
	@Override
	public String toString() {
		return references.toString();
	}
}
