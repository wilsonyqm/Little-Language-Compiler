public class NodeInfo {

	 private String opCode;
	 private String temp;
	 private String type;
	
	
	public NodeInfo(String opCode, String temp, String type){
		this.opCode = opCode;
		this.temp = temp;
		this.type = type;		
	}
	
	public String getOpCode(){
		return opCode;
	}

	public String getTemp(){
		return temp;
	}	
	
	public String getType(){
		return type;
	}
	
}