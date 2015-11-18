import java.util.*;

public class CodeGenerater{
	ArrayList<IRNode> iRNodes;
    ArrayList<TinyNode> tinyNodes;
    ArrayList<Symbol> symbols;
    ArrayList<ArrayList<IRNode>> incrNodes;
	private int tinycount;
	private HashMap<String, String> regMap;
	private HashSet<String> compareSet;
	private int paraNum;
	private int localVarNum;
    public CodeGenerater(){
    	iRNodes = new ArrayList<IRNode>();
    	tinyNodes = new ArrayList<TinyNode>();
    	incrNodes = new ArrayList<ArrayList<IRNode>>();
		symbols = new ArrayList<>();
		tinycount = 0;
		regMap = new HashMap<>();
		compareSet = new HashSet<>();
		init_compareSet(compareSet);
    }
	private int getTinyCount() {
		return tinycount;
	}
	private void setTinyCount(int a) {
		tinycount = a;
	}
	private void setparaNum(int a) {
		paraNum = a;
	}
	private void setlocalVarNum(int a) {
		localVarNum = a;
	}
	private int getparaNum() {
		return paraNum;
	}
	private int getlocalVarNum() {
		return localVarNum;
	}
	private String getTinyRegister(String str) {
		if (isRegister(str) && regMap.containsKey(str)) {
			return regMap.get(str);
		}
		else if (isRegister(str) && str.length() > 2) {
			String tmp;
			if (str.charAt(1) == 'L') {
				tmp = "$-" + str.substring(2);
				regMap.put(str, tmp);
				return tmp;
			}
			else if (str.charAt(1) == 'P') {
				tmp = "$" + (6 + paraNum - Integer.parseInt(str.substring(2)));
			}
			else if (str.charAt(1) == 'R') {
				tmp = "$" + (6 + paraNum);
			}
			else {
				tmp = "r" + tinycount++;
			}
			regMap.put(str, tmp);
			return tmp;
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
    
    public void createIncrArray(){
    	incrNodes.add(new ArrayList<IRNode>());
    }
    
    public void addIncrNode(IRNode node){
    	incrNodes.get(incrNodes.size()-1).add(node);
    }
    
    public void dropIncrArray(){
    	incrNodes.remove(incrNodes.size()-1);
    }
    
    public void addIncrToIR(){
    	iRNodes.addAll(incrNodes.get(incrNodes.size()-1));
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
    	System.out.println(";tiny code");
		for (int i = 0; i < symbols.size(); i++) {
			if (symbols.get(i).getAttr().equals("global")) {
				if (symbols.get(i).getType().equals("STRING")) {
					System.out.println("str " + symbols.get(i).getName() + " " + symbols.get(i).getValue());
					// System.out.println("str " + symbols.get(i).getName() + "\"" + "\\" + "n" + "\"");
				}
				else {
					System.out.println("var " + symbols.get(i).getName());
				}
			}
		}
		//start main function
		System.out.println("push");
		System.out.println("push r0");
		System.out.println("push r1");
		System.out.println("push r2");
		System.out.println("push r3");
		System.out.println("jsr main");
						
		System.out.println();
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
			else if (isRegister(r)){
				s2 = getTinyRegister(irnode.getResult());
				s1 = irnode.getOperand1();
			}
			else {
				String newReg = getTinyRegister(op1);
				res.add(new TinyNode("move", op1, newReg));
				s1 = newReg;
				s2 = r;
			}
			res.add(new TinyNode("move", s1, s2));
		}
		else if (opCode == null) {
			//doing nothing
		}
		else if (opCode.equals("WRITEF") || opCode.equals("WRITEI")) {
			res.add(new TinyNode(cmmd, null, r));
		}
		else if (opCode.equals("READI") || opCode.equals("READF") || opCode.equals("WRITES")) {
			res.add(new TinyNode("sys", cmmd, r));
		}
		else if (compareSet.contains(opCode)) {
			String newReg = getTinyRegister(op2);
			if (!isRegister(op2)) {
				res.add(new TinyNode("move", op2, newReg));
			}
			// Get Op1 Type
			String op1Type = "";
			for (int i = 0; i < symbols.size(); i++) {
				if (symbols.get(i).getName().equals(op1)) {
					op1Type = symbols.get(i).getType();
				}
			}
			if (op1Type.equals("")) System.out.println("ERROR, NOT FIND SYMBOL");
			
			//Insert Type compare 
			if (op1Type.equals("INT")) {
				res.add(new TinyNode("cmpi", op1, newReg));
			}
			else {
				res.add(new TinyNode("cmpr", op1, newReg));
			}
			//Jump 
			res.add(new TinyNode(cmmd, null, r));
		}
		else if (opCode.equals("LABEL") || opCode.equals("JUMP")) {
			
			res.add(new TinyNode(cmmd, null, r));
		}
		else if (opCode.equals("LINK")) {
			res.add(new TinyNode(cmmd, null, localVarNum));
		}
		else if (opCode.equals("PUSH")) {
			if (isRegister(r)) {
				res.add(new TinyNode("push", null, null));
				res.add(new TinyNode("push", null, getTinyRegister(r)));
				res.add(new TinyNode("push", null, "r0"));
				res.add(new TinyNode("push", null, "r1"));
				res.add(new TinyNode("push", null, "r2"));
				res.add(new TinyNode("push", null, "r3"));
			}
		}
		else if (opCode.equals("JSR")) {
			res.add(new TinyNode("jsr", null, r));
		}
		else if (opCode.equals("POP")) {
			if (isRegister(r)) {
				res.add(new TinyNode("pop", null, "r3"));
				res.add(new TinyNode("pop", null, "r2"));
				res.add(new TinyNode("pop", null, "r1"));
				res.add(new TinyNode("pop", null, "r0"));
				res.add(new TinyNode("pop", null, null));
				res.add(new TinyNode("pop", null, getTinyRegister(r)));
			}
		}
		else if (opCode.equals("RET")) {
			res.add(new TinyNode("unlnk", null, null));
			res.add(new TinyNode("ret", null, null));
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
	
	private void init_compareSet(HashSet<String> set) {
		set.add("GE");
		set.add("GT");
		set.add("LE");
		set.add("LT");
		set.add("NE");
		set.add("EQ");
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
			case "DIVF" :
				cmmd = "divr";
				break;
			case "MULTF" :
				cmmd = "mulr";
				break;
			case "WRITEF": 
				cmmd = "sys writer";
				break;
			case "WRITEI"	:
				cmmd = "sys writei";
				break;
			case "LT" :
				cmmd = "jlt";
				break;
			case "LE" :
				cmmd = "jle";
				break;
			case "EQ": 
				cmmd = "jeq";
				break;
			case "GE"	:
				cmmd = "jge";
				break;
			case "GT" :
				cmmd = "jgt";
				break;	
			case "NE" :
				cmmd = "jne";
				break;
			case "LABEL" :
				cmmd = "label";
				break;
			case "JUMP" :
				cmmd = "jmp";
				break;
			case "READI" :
				cmmd = "readi";
				break;
			case "READF" :
				cmmd = "readr";
				break;
			case "WRITES" :
				cmmd = "writes";
				break;
			case "LINK" :
				cmmd = "link";
				break;
			default:
				cmmd = "ERROR";
				break;
		}
		return cmmd;
	}
	private boolean isRegister(String str) {
		if (str == null) return false;
		return str.contains("$");
	}	
	public boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}  
	public boolean isSymbol(String str) {
		return !(isRegister(str) || isNumeric(str));
	}
}