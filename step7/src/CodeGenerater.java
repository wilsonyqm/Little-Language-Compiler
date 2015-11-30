import java.util.*;

public class CodeGenerater{
	ArrayList<IRNode> iRNodes;
	ArrayList<CFGNode> cfgNodes;
    ArrayList<TinyNode> tinyNodes;
    ArrayList<Symbol> symbols;
    ArrayList<Symbol> globalSymbols;
    ArrayList<ArrayList<IRNode>> incrNodes;
    ArrayList<CFGNode> workList;
	private int tinycount;
	private HashMap<String, String> regMap;
	private HashSet<String> compareSet;
	private int paraNum;
	private int localVarNum;
	private Stack<IRNode> paraStack;
	private int stackSize;
	private boolean haspush;
	private String funcId;
    public CodeGenerater(){
    	this.iRNodes = new ArrayList<IRNode>();
    	this.cfgNodes = new ArrayList<CFGNode>();
    	this.tinyNodes = new ArrayList<TinyNode>();
    	this.incrNodes = new ArrayList<ArrayList<IRNode>>();
    	this.workList = new ArrayList<CFGNode>();
    	this.paraStack = new Stack<IRNode>();
    	this.stackSize = 0;
		this.symbols = new ArrayList<Symbol>();
		this.globalSymbols = new ArrayList<Symbol>();
		this.tinycount = 0;
		this.regMap = new HashMap<>();
		this.compareSet = new HashSet<>();
		this.init_compareSet(compareSet);
		this.haspush = false;
    }
    
    public void setFuncId(String id){
    	this.funcId = new String(id);
    }
    
    public void addReturn(){
    	IRNode node = iRNodes.get(iRNodes.size()-1);
    	if(!node.getOpCode().equals("RET"))
    		iRNodes.add(new IRNode("RET",null,null,""));
    }
    public void pushIRNode(IRNode node){
    	paraStack.push(node);	
    	stackSize ++;
    }
    
    public void popIRNode(){
    	while(!paraStack.isEmpty()){
    		IRNode node = paraStack.pop();
    		iRNodes.add(node);
    	}
    }
    
    public void popopIRNode(){
    	for(int i = 0; i<= stackSize; i++)
    		iRNodes.add(new IRNode("POP",null,null,""));
    	stackSize = 0;
    }
    
	public int getTinyCount() {
		return tinycount;
	}
	public void setTinyCount(int a) {
		tinycount = a;
	}
	public void setparaNum(int a) {
		paraNum = a;
	}
	public void setlocalVarNum(int a) {
		localVarNum = a;
	}
	public int getparaNum() {
		return paraNum;
	}
	public int getlocalVarNum() {
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
	private String getTinyRegister() {
		String tmp = "r" + tinycount++;
		return tmp;
	}
    public void setSymbols(ArrayList<Symbol> list) {
    	symbols.addAll(list);
    	symbols.addAll(this.globalSymbols);
    }
    
    public void setGlobalSymbols(ArrayList<Symbol> global){
    	globalSymbols.addAll(global);
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
    
    public boolean hasMultiSetVar(String str){
    	return (str.equals("ADDI") || str.equals("ADDF") || str.equals("SUBI")
    			|| str.equals("SUBF") || str.equals("MULTI") || str.equals("MULTF")
    			|| str.equals("DIVI") || str.equals("DIVF") || str.equals("STOREI")
    			|| str.equals("STOREF"));
    }
    
    public CFGNode findLabel(String label){
    	for(CFGNode cfgNode : cfgNodes){
    		IRNode irNode = cfgNode.getIRNode();
    		if(irNode.getOpCode().equals("LABEL") && irNode.getResult().equals(label))
    			return cfgNode;
    	}
    	return null;
    }
    
    public boolean isBranch(String str){
    	return (str.equals("GT") || str.equals("GE") || str.equals("LT") || str.equals("LE")
    			|| str.equals("NE") || str.equals("EQ"));
    }
    
    public void computeLiveInOut(CFGNode cfgNode){
    	if (!cfgNode.getSuccessor().isEmpty()){
    			ArrayList<CFGNode> successor = cfgNode.getSuccessor();
    			for( CFGNode snode: successor){
    				cfgNode.addAllLiveOut(snode.getLiveInSet());
    			}
    		}
    		cfgNode.setLiveInSet(cfgNode.getLiveOutSet());
    		cfgNode.removeAllLiveIn(cfgNode.getKillSet());
    		cfgNode.addAllLiveIn(cfgNode.getGenSet());
    }
    
    public void buildCFGNodes(){
    	for (IRNode node : iRNodes){
    		CFGNode cfgNode = new CFGNode(node);
    		if (hasMultiSetVar(node.getOpCode()) == true){
    			String operand1 = node.getOperand1();
    			String operand2 = node.getOperand2();
    			String result = node.getResult();
    			if(operand1!= null && !isNumeric(operand1)){
    				cfgNode.getGenSet().add(operand1);
    			}
    			if(operand2!= null && !isNumeric(operand2)){
    				cfgNode.getGenSet().add(operand2);
    			}
    			if(result != null && !isNumeric(result)){
    				cfgNode.getKillSet().add(result);
    			}
    		}
    		else{
    			String opCode = node.getOpCode();
    			if(opCode.equals("GT") || opCode.equals("GE") || opCode.equals("LT") || opCode.equals("LE")
    				||opCode.equals("NE") || opCode.equals("EQ")){
    				String operand1 = node.getOperand1();
    				String operand2 = node.getOperand2();
    				if(operand1!= null && !isNumeric(operand1)){
    					cfgNode.getGenSet().add(operand1);
    				}
    				if(operand2!= null && !isNumeric(operand2)){
    					cfgNode.getGenSet().add(operand2);
    				}
    			}
    			else if(opCode.equals("READI") || opCode.equals("READF") || opCode.equals("POP")){
    				String result = node.getResult();
    				if(result != null && !result.equals("") && !isNumeric(result)){
    					cfgNode.getKillSet().add(result);
    				}
    			}
    			else if(opCode.equals("WRITEI") || opCode.equals("WRITEF")|| opCode.equals("WRITES")|| opCode.equals("PUSH")){
    				String result = node.getResult();
    				if(result != null && !result.equals("") && !isNumeric(result)){
    					cfgNode.getGenSet().add(result);
    				}
    			}
    //			else if(opCode.equals("JSR")){
    //				String id = node.getResult();
    //				if (!this.funcId.equals(id)){
    //					for(Symbol symbol : globalSymbols){
    //						cfgNode.getGenSet().add(symbol.getName());
    //					}
    //				}
    //			}
    		}
    	//	if (node.getOpCode().equals("RET")){
    	//		for (int i=0; i<globalSymbols.size();i++){
    	//			cfgNode.addLiveOut(globalSymbols.get(i).getName());
    	//		}
    	//	}
    		this.cfgNodes.add(cfgNode);	
    	}
    	
    	//TODO: set successor and predecessor and lead label
    	for(int i = 0; i< cfgNodes.size(); i++){
    		if(i==0){
    			CFGNode cfgNode = cfgNodes.get(i);
    			cfgNode.setLead(true);
    			cfgNode.addSuccessor(cfgNodes.get(i+1));
    			continue;
    		}
    		CFGNode cfgNode = cfgNodes.get(i);
    		IRNode irNode = cfgNode.getIRNode();
    		String opCode = irNode.getOpCode();
    		if (opCode.equals("JUMP")){
    			String label = irNode.getResult();
    			CFGNode labelNode = findLabel(label);
    			if(labelNode != null){
    				labelNode.addPredecessor(cfgNode);
    				cfgNode.addSuccessor(labelNode);
    				labelNode.setLead(true);
    			}
    			else{
    				System.out.println("CFGNode with label "+label+" not found");
    			}
    		}
    		else if(isBranch(opCode)) {
    			cfgNode.setLead(true);
    			String label = irNode.getResult();
    			CFGNode labelNode = findLabel(label);
    			if(labelNode != null){
    				labelNode.addPredecessor(cfgNode);
    				cfgNode.addSuccessor(labelNode);
    				cfgNode.addSuccessor(cfgNodes.get(i+1));
    				cfgNode.setLead(true);
    				labelNode.setLead(true);
    			}
    			else{
    				System.out.println("CFGNode with label "+label+" not found");
    			}
    		}
    		else{
    			if (i+1 < cfgNodes.size()) {
    				cfgNode.addSuccessor(cfgNodes.get(i+1));
    				cfgNodes.get(i+1).addPredecessor(cfgNode);
    			}
    		}
    	}
    	//TODO: build up liveness table
    	for (int i = cfgNodes.size()-1; i>=0; i--){
    		CFGNode cfgNode = cfgNodes.get(i);
    		computeLiveInOut(cfgNode);
    	}
    	this.workList.addAll(cfgNodes);
    	
    	while(!workList.isEmpty()){
    		CFGNode cfgNode = workList.remove(workList.size()-1);
    		Set<String> preLiveInSet = cfgNode.getLiveInSet();
    		computeLiveInOut(cfgNode);
    		Set<String> afterLiveInSet = cfgNode.getLiveInSet();
    		if(! (preLiveInSet.containsAll(afterLiveInSet) && afterLiveInSet.containsAll(preLiveInSet))){
    			workList.addAll(cfgNode.getPredecessor());
    		}
    	} 		 	
    }
    
    public void printIRNodes(){
   // 	System.out.println(";IR code");
    	for(int i=0; i<iRNodes.size();i++){
    		System.out.println(";"+iRNodes.get(i).toString());
    	}	
    	buildCFGNodes();
    }
	public void printGlobalTiny() {
    	System.out.println(";tiny code");
		for (int i = 0; i < symbols.size(); i++) {
			if (symbols.get(i).getAttr()== null) {
				if (symbols.get(i).getType().equals("STRING")) {
					System.out.println("str " + symbols.get(i).getName() + " " + symbols.get(i).getValue());
					// System.out.println("str " + symbols.get(i).getName() + "\"" + "\\" + "n" + "\"");
				}
//				else if (symbols.get(i).getType().equals("FLOAT")){
//					System.out.println("float " + symbols.get(i).getName());
//				}
//				else if (symbols.get(i).getType().equals("INT")){
//					System.out.println("int " + symbols.get(i).getName());
//				}
				else{
					System.out.println("var " + symbols.get(i).getName());
				}
			}
		}
		System.out.println("push");
		System.out.println("push r0");
		System.out.println("push r1");
		System.out.println("push r2");
		System.out.println("push r3");
		System.out.println("jsr main");
		System.out.println("sys halt");
		
	}
	
    public void printTinyNodes(){

System.out.println(";-----------------------------used for debug-----------------------");
		convertListIRtoTiny(iRNodes, tinyNodes);
System.out.println(";-----------------------------used for debug-----------------------");    					
    	for(int i=0; i<tinyNodes.size();i++){
    		System.out.println(tinyNodes.get(i).toString());
    	}	
		
    }

	private void convertListIRtoTiny(ArrayList<IRNode> irlist, ArrayList<TinyNode> tinylist) {
		IRNode irnode;
		for(int k=0;k<irlist.size();k++){
		irnode = irlist.get(k);
//		for (IRNode irnode : irlist) {
			tinylist.addAll(convertNodeIRtoTiny(irnode));
			
//-----------used for debug--------------------------------
	System.out.println(";"+irnode.toString());
//	Set<String> genSet = cfgNodes.get(k).getGenSet();
//	System.out.print(";GenSet is "+Arrays.toString(genSet.toArray(new String[genSet.size()])));
//	Set<String> killSet = cfgNodes.get(k).getKillSet();
//	System.out.print("  KillSet is "+Arrays.toString(killSet.toArray(new String[killSet.size()])));
//	Set<String> liveInSet = cfgNodes.get(k).getLiveInSet();
//	System.out.print("  LiveInSet is "+Arrays.toString(liveInSet.toArray(new String[liveInSet.size()])));
//	Set<String> liveOutSet = cfgNodes.get(k).getLiveOutSet();
//	System.out.println("  LiveOutSet is "+Arrays.toString(liveOutSet.toArray(new String[liveOutSet.size()])));
	for(int i=0; i<convertNodeIRtoTiny(irnode).size();i++){
		System.out.println(";-----------------------------"+convertNodeIRtoTiny(irnode).get(i));
	}
//------------used for debug----------------------------------
		}
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
				if (isRegister(r) && !(r.equals("$R") && op1.startsWith("$T"))) {
					s2 = getTinyRegister(r);
				}
				else {
					s2 = r;
				}
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
			if (r.equals("$R") && !op1.startsWith("$L")) {
				res.add(new TinyNode("move", s1, getTinyRegister(r + "R")));
			}
			else {
				res.add(new TinyNode("move", s1, s2));
				if (r.equals("$R")) {
					res.add(new TinyNode("move", s2, getTinyRegister(r + "R")));
				}
			}
		}
		else if (opCode == null) {
			//doing nothing
		}
		else if (opCode.equals("WRITEF") || opCode.equals("WRITEI")) {
			if (!isRegister(r))
				res.add(new TinyNode(cmmd, null, r));
			else 
				res.add(new TinyNode(cmmd, null, getTinyRegister(r)));
		}
		else if (opCode.equals("READI") || opCode.equals("READF") || opCode.equals("WRITES")) {
			if (!isRegister(r))
				res.add(new TinyNode("sys", cmmd, r));
			else 
				res.add(new TinyNode("sys", cmmd, getTinyRegister(r)));
		}
		else if (compareSet.contains(opCode)) {
			String newReg = getTinyRegister(op2);
			if (!isRegister(op2)) {
				res.add(new TinyNode("move", op2, newReg));
			}
			// Get Op1 Type
			String op1Type = "";
			
			for (int i = 0; i < symbols.size(); i++) {
	//	System.out.println(symbols.get(i).getName());
				if (symbols.get(i).getName().equals(op1) ||(symbols.get(i).getAttr()!=null && symbols.get(i).getAttr().equals(op1))) {
					op1Type = symbols.get(i).getType();
					break;
				}
			}
			
			if (op1Type.equals("")) System.out.println("ERROR, NOT FIND SYMBOL");
			if (isRegister(op1)) {
				op1 = getTinyRegister(op1);
			}
			//Insert Type compare 
			if (!op2.startsWith("$T")) {
				String reg2 = getTinyRegister(op2);
				newReg = getTinyRegister();
				res.add(new TinyNode("move", reg2, newReg));
			}
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
			res.add(new TinyNode(cmmd, null, "" + localVarNum));
		}
		else if (opCode.equals("PUSH")) {
			if (isRegister(r)) {
				if (!haspush)	
					res.add(new TinyNode("push", null, null));
				haspush = true;
				res.add(new TinyNode("push", null, getTinyRegister(r)));
			}
		}
		else if (opCode.equals("JSR")) {
			res.add(new TinyNode("push", null, "r0"));
			res.add(new TinyNode("push", null, "r1"));
			res.add(new TinyNode("push", null, "r2"));
			res.add(new TinyNode("push", null, "r3"));
			res.add(new TinyNode("jsr", null, r));
			res.add(new TinyNode("pop", null, "r3"));
			res.add(new TinyNode("pop", null, "r2"));
			res.add(new TinyNode("pop", null, "r1"));
			res.add(new TinyNode("pop", null, "r0"));
		}
		else if (opCode.equals("POP")) {
			if (isRegister(r)) {
				res.add(new TinyNode("pop", null, getTinyRegister(r)));
			}
			else {
				res.add(new TinyNode("pop", null, null));
				haspush = false;
			}
		}
		else if (opCode.equals("RET")) {
			res.add(new TinyNode("unlnk", null, null));
			res.add(new TinyNode("ret", null, null));
		}
		else {
			if (isRegister(op1) && isRegister(op2) && isRegister(r)) {
				s1 = getTinyRegister(op1);
				s2 = getTinyRegister(op2);
				String newreg = getTinyRegister(r);
				res.add(new TinyNode("move", s1, newreg));
				res.add(new TinyNode(cmmd, s2, newreg));
				return res;
			}
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
