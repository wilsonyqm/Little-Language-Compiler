public class NodeInfo {

	 private String opCode;
	 private String temp;
	 private String type;
	 private String branch;
	 private String note;
	 private boolean infunction;
	
	public NodeInfo(String opCode,String temp, String type){
		this.opCode = opCode;
		this.temp = temp;
		this.type = type;
		this.branch = null;
		this.note = null;
		this.infunction = false;
	}
	
	public NodeInfo(String opCode, String temp, String type, String branch){
		this.opCode = opCode;
		this.temp = temp;
		this.type = type;	
		this.branch = branch;
		this.note = null;
		this.infunction = false;	
	}
	
	public NodeInfo(String opCode, String temp, String type, String branch, String note){
		this.opCode = opCode;
		this.temp = temp;
		this.type = type;
		this.branch = branch;
		this.note = note;
		this.infunction = false;
	}
	
	public NodeInfo(String opCode, String temp, String type, String branch, String note, boolean infunction){
		this.opCode = opCode;
		this.temp = temp;
		this.type = type;
		this.branch = branch;
		this.note = note;
		this.infunction = infunction;
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
	
	public String getBranch(){
		return branch;
	}
	
	public String getNote(){
		return note;
	}
	
	public void setNote(String note){
		this.note = note;
	}
	
	public boolean isInFunction(){
		return infunction;
	}
	
}