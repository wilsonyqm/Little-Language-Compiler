public class IRNode {

	 private String opCode;
	 private String operand1;
	 private String operand2;
	 private String result;
	
	
	public IRNode(String opCode,String operand1, String operand2, String result){
		this.opCode = opCode;
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.result = result;		
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
	
	@Override
	public String toString(){
		if(operand1 == null && operand2 == null)
			return opCode+" "+result;
		else if (operand1 == null)
			return opCode+" "+operand2+" "+result;
		else if (operand2 == null)
			return opCode+" "+operand1+" "+result;
		else
			return opCode+" "+operand1+" "+operand2+" "+result;
		}
}