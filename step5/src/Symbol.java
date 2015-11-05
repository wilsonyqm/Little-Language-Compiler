import java.util.*;
public class Symbol {
	private String name;
	private String type;
	private String value;
	
	public Symbol(String name, String type){
		this.name = name;
		this.type = type;
		this.value = null;
	}
	
	public Symbol(String name, String type, String value){
		this.name = name;
		this.type = type;
		this.value = value;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getValue(){
		return this.value;
	}
	
}