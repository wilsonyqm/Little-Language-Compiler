import java.util.*;

public class CodeGenerater{
	ArrayList<IRNode> iRNodes;
    ArrayList<TinyNode> tinyNodes;
    ArrayList<Symbol> symbols;
	private int tinycount;
	private HashMap<String, String> regMap;
    public CodeGenerater(){
    	iRNodes = new ArrayList<IRNode>();
    	tinyNodes = new ArrayList<TinyNode>();
		symbols = new ArrayList<>();
		tinycount = 0;
		regMap = new HashMap<>();
    }
	private String getTinyRegister(String str) {
		if (isRegister(str) && regMap.containsKey(str)) {
			return regMap.get(str);
		}
		else {
			String tmp = "r" + tinycount++;
			regMap.put(str, tmp);
			return tmp;
		}
	}
    public void setSymbols(ArrayList<Symbol> list) {
    	symbols.addAll(list);
    }
    public void addIRNode(IRNode node){
    	iRNodes.add(node);
    }
    
    public void addTinyNode(TinyNode node){
    	tinyNodes.add(node);
    }
    
    public void printIRNodes(){
    	System.out.println(";IR code");
    	for(int i=0; i<iRNodes.size();i++){
    		System.out.println(";"+iRNodes.get(i).toString());
    	}	
    }
    public void printTinyNodes(){
		convertListIRtoTiny(iRNodes, tinyNodes);
    	System.out.println(";Tiny code");
		for (int i = 0; i < symbols.size(); i++) {
			System.out.println("var " + symbols.get(i).getName());
		}
    	for(int i=0; i<tinyNodes.size();i++){
    		System.out.println(tinyNodes.get(i).toString());
    	}	
    }
	private void convertListIRtoTiny(ArrayList<IRNode> irlist, ArrayList<TinyNode> tinylist) {
		for (IRNode irnode : irlist) {
			tinylist.addAll(convertNodeIRtoTiny(irnode));
		}
		tinylist.add(new TinyNode("sys halt", null, null));
	}
	private ArrayList<TinyNode> convertNodeIRtoTiny(IRNode irnode) {
		String opCode = irnode.getOpCode();
		ArrayList<TinyNode> res = new ArrayList<>();
		String op1 = irnode.getOperand1();
		String op2 = irnode.getOperand2();
		String r = irnode.getResult();
		String s1, s2;
		String cmmd = getCmmd(opCode);
		if (opCode.equals("STOREI") || opCode.equals("STOREF")) {
			if (isRegister(irnode.getOperand1())) {
				s1 = getTinyRegister(irnode.getOperand1());
				s2 = irnode.getResult();
			}
			else {
				s2 = getTinyRegister(irnode.getResult());
				s1 = irnode.getOperand1();
			}
			res.add(new TinyNode("move", s1, s2));
		}
		else if (opCode.equals("WRITEF") || opCode.equals("WRITEI")) {
			res.add(new TinyNode(cmmd, null, r));
		}
		else {
			if (isRegister(op1) && isRegister(op2)) {
				s1 = getTinyRegister(op2);
				s2 = getTinyRegister(op1);
			}
			else if (isRegister(op1)) {
				s2 = getTinyRegister(op1);
				s1 = op2;
			}
			else if (isRegister(op2)) {
				s2 = getTinyRegister(op1);
				res.add(new TinyNode("move", op1, s2));
				s1 = getTinyRegister(op2);
			}
			else { 
				// no register, both are number
				s2 = getTinyRegister(op1);
				res.add(new TinyNode("move", op1, s2));
				s1 = op2;
			}
			regMap.put(r, s2);
			res.add(new TinyNode(cmmd, s1, s2));
		}
		return res;
	}
	/* Type Distinguish
	*
	*/
	private String getCmmd(String str) {
		String cmmd;
		switch (str) {
			case "ADDI": 
				cmmd = "addi";
				break;
			case "SUBI"	:
				cmmd = "subi";
				break;
			case "SUBF": 
				cmmd = "subr";
				break;
			case "ADDF":
				cmmd = "addr";
				break;
			case "MULTI": 
				cmmd = "muli";
				break;			
			case "DIVI"	:
				cmmd = "divi";
				break;
			case "WRITEF": 
				cmmd = "sys writer";
				break;
			case "WRITEI"	:
				cmmd = "sys writei";
				break;
			default:
				cmmd = "ERROR";
				break;
		}
		return cmmd;
	}
	private boolean isRegister(String str) {
		return str.contains("$");
	}	
	public boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}  
	public boolean isSymbol(String str) {
		return !(isRegister(str) || isNumeric(str));
	}
}