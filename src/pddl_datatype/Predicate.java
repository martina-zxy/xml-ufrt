package pddl_datatype;

import java.util.ArrayList;

public class Predicate {
	public String name;
	public ArrayList<Param> params = new ArrayList<Param>();
	
	public Predicate(){
		
	}
	
	public Predicate(String name, ArrayList<Param> param) {
		this.name = name;
		this.params = param;
	}
	
	public String toString() {
		StringBuilder pred = new StringBuilder();
		pred.append(name);
		
		for (Param par : params) {
			pred.append (" " + par.type + " ?" + par.parameter);	
		}
		return pred.toString();
	}
	
	@Override
    public int hashCode() {
		StringBuilder hash = new StringBuilder(name);
		for (Param p : params) {
			hash.append(p.type);
		}
		return hash.toString().hashCode();
    }
	
	@Override
    public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Predicate other = (Predicate) obj;
      
      if (!name.equals(other.name))
         return false;
      if (params.size() != other.params.size())
    	  return false;
      if (!params.containsAll(other.params))
    	  return false;
      if (!other.params.containsAll(params))
    	  return false;
      return true;
    }
}
