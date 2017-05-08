package pddl_datatype;

public class Param {
	public String parameter = null;
	public String type = null;
	
	public Param(){
		
	}
	
	public Param(String parameter, String type) {
		this.parameter = parameter;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return parameter + " " + type;
	}
	
	@Override
    public int hashCode() {
		String hash = parameter + "" + type;
		return hash.hashCode();
    }
	
	@Override
    public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Param other = (Param) obj;
      if (!parameter.equals(other.parameter) || !type.equals(other.type))
         return false;
      return true;
    }
}
