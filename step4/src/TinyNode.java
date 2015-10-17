public class TinyNode{
	private String opCode;
	private String oprand1;
	private String oprand2;
	
	public TinyNode(String opCode, String oprand1, String oprand2){
		this.opCode = opCode;
		this.oprand1 = oprand1;
		this.oprand2 = oprand2;
	}
	public String getOpCode(){
		return opCode;
	}

	public String getOperand1(){
		return oprand1;
	}
	
	public String getOperand2(){
		return oprand2;
	}
	@Override
	public String toString() {
		String str = "";
		if (oprand1 == null) {
			str += opCode + " " + oprand2;
		}
		else {
			str += opCode + " " + oprand1 + " " + oprand2;
		}
		return str;
	}
}