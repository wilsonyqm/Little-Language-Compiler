public class IRNode {

	 private String opCode;
	 private String operand1;
	 private String operand2;
	 private String result;
	 private HashSet<String> liveInSet;
	 private HashSet<String> liveOutSet;
	 private HashSet<String> genSet;
	 private HashSet<String> killSet;
	 private boolean isLead;
	
	public IRNode(String opCode,String operand1, String operand2, String result){
		this.opCode = opCode;
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.result = result;	
		this.liveInSet = new HashSet<String>();
		this.liveOutSet = new HashSet<String>();
		this.genSet = new HashSet<String>();
		this.killSet = new HashSet<String>();
		this.isLead = false;
			
	}
	
	public String getOpCode(){
		return opCode;
	}
	
	public String getOperand1(){
		return operand1;
	}
	
	public String getOperand2(){
		return operand2;
	}
	
	public String  getResult(){
		return result;
	}	
	
	public void setLead(boolean var){
		this.isLead = var;
	}
	
	public HashSet<String> getLiveInSet(){
		return this.liveInSet;
	}
	
	public void addLiveIn(String var){
		this.liveInSet.add(var);
	}
	
	public HashSet<String> getLiveOutSet(){
		return this.liveOutSet;
	}
	
	public void addLiveOut(String var){
		this.liveOutSet.add(var);
	}
	
	public HashSet<String> getGenSet(){
		return this.genSet;
	}
	
	public void addGenSet(String var){
		this.genSet.add(var);
	}
	
	public HashSet<String> getKillSet(){
		return this.killSet;
	}
	
	public void addKillSet(String var){
		this.killSet.add(var);
	}
	
	@Override
	public String toString(){
		if (opCode == null)
			return result;
		else if(operand1 == null && operand2 == null)
			return opCode+" "+result;
		else if (operand1 == null)
			return opCode+" "+operand2+" "+result;
		else if (operand2 == null)
			return opCode+" "+operand1+" "+result;
		else
			return opCode+" "+operand1+" "+operand2+" "+result;
		}
}