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
	private HashSet<String> dirtySet;
	private int tNum;
	private int k; // index for the current IRNode in irlist
    public CodeGenerater(){
		this.dirtySet = new HashSet<>();
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
		this.tNum = 0;
		this.k = 0;
    }
    
    public void setFuncId(String id){
    	this.funcId = new String(id);
    }
    public void setTnum(int a) {
    	this.tNum = a;
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

	private String getTinyRegister(String str, String op1, CFGNode cfgNode, ArrayList<TinyNode> res) {
		if (regMap.containsKey(str)) {
			return regMap.get(str);
		}
		else {
		// else if (isRegister(str) && str.length() > 2) {
			String tmp = getNewRegister(op1, cfgNode, res);
			System.out.println(";REG MAP  "+ str + ": " + tmp);
			regMap.put(str, tmp);
			return tmp;
		}
	}

	
	private String getNewRegister(String op1, CFGNode cfgNode, ArrayList<TinyNode> res) {
		String notStr = op1.equals("") ? "" : regMap.get(op1);
		String[] A = new String[4];
		for (int i = 0; i < 4; i++) {
			A[i] = "";
		}
		
		HashMap<String, String> tmpMap = new HashMap<>();
		for (String str : regMap.keySet()) {
			tmpMap.put(regMap.get(str), str);
		}
		for (String str : regMap.values()) {
			int idx = Integer.parseInt(str.charAt(1) + "");
			A[idx] = tmpMap.get(str);
		}
		
		// If the register is not full
		int i = A.length - 1;
		for (; i >= 0; i--) {
			if (A[i].equals("")) {
				return "r" + i;
			}
			else if (!("r"+i).equals(notStr) && !cfgNode.getLiveOutSet().contains(A[i])) {
				System.out.println(";HIHI1");
				res.add(new TinyNode("move", "r" + i, calculateTinyRegister(A[i])));
				System.out.println(";REMOVE " + A[i]);
				if (dirtySet.contains(A[i])) dirtySet.remove(A[i]);
				regMap.remove(A[i]);
				return "r" + i;
			}
		}
		// If all the register is full, find the first non-dirty one
		i = A.length - 1;
		for (; i >= 0; i--) {
			if (!("r"+i).equals(notStr)&& !A[i].startsWith("$T") && !dirtySet.contains(A[i])) {
				System.out.println(";HIHI2");
				System.out.println(";REMOVE " + A[i]);
				if (dirtySet.contains(A[i])) dirtySet.remove(A[i]);
				regMap.remove(A[i]);
				return "r" + i;
			}
			
		}
		// If all the register is full, and all of them are dirty.
		Set<String> regSet = new HashSet<String>(regMap.keySet());
		// Have to remove self.
		if (regSet.contains(op1)) regSet.remove(op1);
		return regMap.get(getFurthestRegister(regSet));
		// for (i = 0; i < A.length; i++) {
// 			if (!("r"+i).equals(notStr)) {
//
// 				System.out.println(";HIHI3");
//
// 				res.add(new TinyNode("move", "r" + i, calculateTinyRegister(A[i])));
// 				regMap.remove(A[i]);
// 				return "r" + i;
// 			}
// 		}


	}
	private String getFurthestRegister(Set<String> set) {
		int idx = k + 1;
		while (true) {
			if (set.size() == 1) {
				String ret = "";
				for (String s : set) {
					ret = s;
				}
				return ret;
			}
			IRNode ir = cfgNodes.get(idx).getIRNode();
			String op1 = ir.getOperand1();
			String op2 = ir.getOperand2();
			String r = ir.getResult();
			if (set.contains(r)) {
				set.remove(r);
				if (set.size() == 1) continue;
			}
			if (set.contains(op2)) {
				set.remove(op2);
				if (set.size() == 1) continue;
			}
			if (set.contains(op2)) {
				set.remove(op1);
				if (set.size() == 1) continue;
			}
			idx++;
		}
	}
	
	private String calculateTinyRegister(String str) {
		if (isRegister(str)) {
			if (str.startsWith("$L")) {
				return "$-" + str.substring(2);
			}
			else if (str.startsWith("$T")) {
				return "$-" + (localVarNum + Integer.parseInt(str.substring(2)));
			}
			else if (str.startsWith("$P")) {
				return "$" + (paraNum + 6 - Integer.parseInt(str.substring(2)));
			}
			else if (str.equals("$R")){
				return "$" + (paraNum + 6);
			}
		}
		
		return str;
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
				cfgNode.setLead(true);
    			String label = irNode.getResult();
    			CFGNode labelNode = findLabel(label);
    			if(labelNode != null){
    				labelNode.addPredecessor(cfgNode);
    				cfgNode.addSuccessor(labelNode);
    				labelNode.setLead(true);
    			}
    			else{
    				System.out.println(";CFGNode with label "+label+" not found");
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
    				System.out.println(";CFGNode with label "+label+" not found");
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
		convertListIRtoTiny(cfgNodes, tinyNodes);
		System.out.println(";-----------------------------used for debug-----------------------");    					
		for(int i=0; i<tinyNodes.size();i++){
			System.out.println(tinyNodes.get(i).toString());
		}	
		
	}

	private void convertListIRtoTiny(ArrayList<CFGNode> cfglist, ArrayList<TinyNode> tinylist) {
		CFGNode cfgnode;
		for(k=0;k<cfglist.size();k++){
			cfgnode = cfglist.get(k);
//		for (IRNode irnode : irlist) {
			ArrayList<TinyNode> newlist = convertNodeIRtoTiny(cfgnode);
			tinylist.addAll(newlist);
			IRNode irnode = cfgnode.getIRNode();
			
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
			
			for(int i=0; i<newlist.size();i++){
				System.out.println(";-----------------------------"+newlist.get(i));
			}
		//------------used for debug----------------------------------
		}
	}
	
	private ArrayList<TinyNode> convertNodeIRtoTiny(CFGNode cfgNode) {
		IRNode irnode = cfgNode.getIRNode();
		String opCode = irnode.getOpCode();
		ArrayList<TinyNode> res = new ArrayList<>();
		String op1 = irnode.getOperand1();
		String op2 = irnode.getOperand2();
		String r = irnode.getResult();
		String s1, s2;
		String cmmd = getCmmd(opCode);
		Stack<TinyNode> stk = new Stack<>();
		for (String regKey : regMap.keySet()) {
			System.out.print(";"+regMap.get(regKey)+ "->" + regKey + ", ");
		}
		System.out.println();
		System.out.print(";DirtySet");
		
		for (String dirtyKey : dirtySet) {
			System.out.print(";" + dirtyKey + ",");
		}
		System.out.println();
		System.out.print(";LiveSet");
		for (String dirtyKey : cfgNode.getLiveOutSet()) {
			System.out.print(";" + dirtyKey + ",");
		}
		System.out.println();
		if (opCode.equals("STOREI") || opCode.equals("STOREF")) {
			boolean isNumBranch = false;
			if (isRegister(irnode.getOperand1())) {
				boolean b = false;
				// if (isGlobal(r)) {
				// 	b = regMap.containsKey(r);
				// }
				if (op1.startsWith("$L") && !regMap.containsKey(op1)) {
					b = true;
				}
				s1 = getTinyRegister(irnode.getOperand1(), r, cfgNode, res);
				// if (isRegister(r) && !(r.equals("$R") && op1.startsWith("$T"))) {
// 					s2 = getTinyRegister(r);
// 				}
// 				else {
// 					s2 = r;
// 				}
				if (b) {
					res.add(new TinyNode("move", calculateTinyRegister(op1), s1));
				}
				if(r.equals("$R")){
						s2 = calculateTinyRegister(r);
					//	System.out.println("in store statement, s2 is"+ s2);
				}
				else{
               	 	s2 = getTinyRegister(r, op1, cfgNode, res);
				// if (!b) res.add(new TinyNode("move", r, s2));
				}
			}
			else if (isRegister(r)){
				//boolean b = true;
				if(r.equals("$R")){
					s2 = calculateTinyRegister(r);
				}
				else{
					s2 = getTinyRegister(irnode.getResult(), op1, cfgNode, res);
				}
				s1 = irnode.getOperand1();
				// if (isGlobal(op1)) {
			// 		b = regMap.containsKey(op1);
			// 		s1 = getTinyRegister(op1, r, cfgNode, res);
			//			}
				// if (!b) res.add(new TinyNode("move", op1, s1));
			}
			else if (isGlobal(op1) && isGlobal(r)) {
				boolean b1 = regMap.containsKey(op1);
				s1 = getTinyRegister(op1, r, cfgNode, res);
				s2 = getTinyRegister(r, op1, cfgNode, res);
				if (!b1) res.add(new TinyNode("move", op1, s1));
				
				
			}
			// Fisrt Num second Global
			else {
				String newReg = getTinyRegister(op1, r, cfgNode, res);
				res.add(new TinyNode("move", op1, newReg));
				s1 = newReg;
				s2 = r;
				isNumBranch = true;
			}
			// Remove the original register
			if (!isNumBranch) {
				Set<String> tmpSet = new HashSet<>(regMap.keySet());
				for (String regKey : tmpSet) {
					if (regMap.containsKey(regKey) && regMap.get(regKey) != null && regMap.get(regKey).equals(s2)) {
						// When empty if it is a dirty global, then you have to memorize it.
						if(isGlobal(regKey) && dirtySet.contains(regKey)) {
							res.add(new TinyNode("move", s2, regKey));
						}
						// If this removed node is not live and dirty, then we need to spill it.
						else if (regKey.startsWith("$T") && !cfgNode.getLiveOutSet().contains(regKey)) {
							res.add(new TinyNode("move", s2, calculateTinyRegister(regKey)));
						}
						else if (cfgNode.getLiveOutSet().contains(regKey) && !regKey.startsWith("$T") && dirtySet.contains(regKey)) {
							res.add(new TinyNode("move", s2, calculateTinyRegister(regKey)));
						}
						
						if (dirtySet.contains(regKey)) {
							System.out.println(";Dirty REMOVE " + regKey);
							dirtySet.remove(regKey);
						}
						System.out.println(";REMOVE " + regKey);
						regMap.remove(regKey);
					}
				}
				System.out.println(";Reg Map" + r + " "+ s2);
				regMap.put(r, s2);
			}
			if (r.equals("$R") && !op1.startsWith("$L")) {
			//	res.add(new TinyNode("move", s1, getTinyRegister(r + "R", op1, cfgNode, res)));
				res.add(new TinyNode("move",s1,s2));
			}
			else {
				if ((op1.startsWith("$L") || isGlobal(op1)) && !regMap.containsKey(op1)) {
					res.add(new TinyNode("move", calculateTinyRegister(op1), s1));
				}
				res.add(new TinyNode("move", s1, s2));
				// if (r.equals("$R")) {
// 					res.add(new TinyNode("move", s2, getTinyRegister(r + "R", op1, cfgNode, res)));
// 				}
			}
			// Mark the local/glocal as dirty
			if (r.startsWith("$L") || r.startsWith("$T") || !isRegister(r)) {
				dirtySet.add(r);
			}
		}
		else if (opCode == null) {
			//doing nothing
		}
		else if (opCode.equals("WRITEF") || opCode.equals("WRITEI")) {
			if (!isRegister(r) || r.startsWith("$L") || r.startsWith("$P")) {
				if (isGlobal(r) || r.startsWith("$L") || r.startsWith("$P")) {
					boolean b = regMap.containsKey(r);
					String newReg = getTinyRegister(r, "", cfgNode, res);
					if (!b) {
						if (isGlobal(r))
							res.add(new TinyNode("move", r, newReg));
						else 
							res.add(new TinyNode("move", calculateTinyRegister(r), newReg));
					}
					res.add(new TinyNode(cmmd, null, newReg));
				}
				else res.add(new TinyNode(cmmd, null, r));
			}
			else 
				res.add(new TinyNode(cmmd, null, getTinyRegister(r, "", cfgNode, res)));
		}
		else if (opCode.equals("WRITES")){
			res.add(new TinyNode("sys",cmmd,r));
		}
		
		else if (opCode.equals("READI") || opCode.equals("READF")) {
			if (!isRegister(r)) {
				if (isGlobal(r)) {
					boolean b = regMap.containsKey(r);
					String newReg = getTinyRegister(r, "", cfgNode, res);
					// If read a new value into global so we need memorize this global
					if (b && dirtySet.contains(r)) {
						res.add(new TinyNode("move", newReg, r));
					}
					dirtySet.add(r);
					res.add(new TinyNode("sys", cmmd, newReg));
				}
				else res.add(new TinyNode("sys", cmmd, r));
				
			}
			else {
				dirtySet.add(r);
				res.add(new TinyNode("sys", cmmd, getTinyRegister(r, "", cfgNode, res)));
			}
		}
		else if (compareSet.contains(opCode)) {
			
			
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
			// if (isRegister(op1) || symbols.contains(op1)) {
				
				
				
			// }
			// reload from memory
			if (!regMap.containsKey(op1) && (isGlobal(op1) || op1.startsWith("$P") || op1.startsWith("$L"))) {
				String tmp = getTinyRegister(op1, op2, cfgNode, res);
				if (op1.startsWith("$P") || op1.startsWith("$L")) {
					res.add(new TinyNode("move", calculateTinyRegister(op1), tmp));
				}
				else
					res.add(new TinyNode("move", op1, tmp));
			}

			op1 = getTinyRegister(op1, op2, cfgNode, res);
			
			//Insert Type compare 
			// if (!op2.startsWith("$T")) {
// 				String reg2 = getTinyRegister(op2, op1, cfgNode, res);
// 				// Some Problem HERE
// 				// newReg = getTinyRegister();
//
// 				res.add(new TinyNode("move", reg2, newReg));
// 			}
			if (!regMap.containsKey(op2) && (isGlobal(op2) || op2.startsWith("$P") || op2.startsWith("$L"))) {
				String tmp = getTinyRegister(op2, op1, cfgNode, res);
				if (op2.startsWith("$P") || op2.startsWith("$L")) {
					res.add(new TinyNode("move", calculateTinyRegister(op2), tmp));
				}
				else
					res.add(new TinyNode("move", op2, tmp));
			}
			String newReg = getTinyRegister(op2, op1, cfgNode, res);
			if (!isRegister(op2)) {
				res.add(new TinyNode("move", op2, newReg));
			}
			if (op1Type.equals("INT")) {
				res.add(new TinyNode("cmpi", op1, newReg));
			}
			else {
				res.add(new TinyNode("cmpr", op1, newReg));
			}
			//Jump 
			stk.push(new TinyNode(cmmd, null, r));
		}
		else if (opCode.equals("LABEL") || opCode.equals("JUMP")) {
			
			stk.push(new TinyNode(cmmd, null, r));
		}
		else if (opCode.equals("LINK")) {
			res.add(new TinyNode(cmmd, null, "" + (tNum - 1 + localVarNum)));
		}
		else if (opCode.equals("PUSH")) {
			if (isRegister(r)) {
				if (!haspush)	
					res.add(new TinyNode("push", null, null));
				haspush = true;
				if (isGlobal(r) || r.startsWith("$L")) {
					if (!regMap.containsKey(r)) {
						res.add(new TinyNode("move", calculateTinyRegister(r), getTinyRegister(r,"", cfgNode, res)));
					}
				}
				res.add(new TinyNode("push", null, getTinyRegister(r,"", cfgNode, res)));
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
				res.add(new TinyNode("pop", null, getTinyRegister(r,"", cfgNode, res)));
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
				Set<String> liveSet = cfgNode.getLiveOutSet();
				boolean bp1 = regMap.containsKey(op1);
				boolean bp2 = regMap.containsKey(op2);
				s1 = getTinyRegister(op1, op2, cfgNode, res);
				s2 = getTinyRegister(op2, op1, cfgNode, res);
				if (op1.startsWith("$P") && !bp1) {
					res.add(new TinyNode("move",calculateTinyRegister(op1) , s1));
				}
				if (op2.startsWith("$P") && !bp2) {
					res.add(new TinyNode("move",calculateTinyRegister(op2) , s2));
				}
				if (!liveSet.contains(op1) && !liveSet.contains(op2)) {
					if (dirtySet.contains(op1) || op1.startsWith("$T")) {
						res.add(new TinyNode("move", s1, calculateTinyRegister(op1)));
						dirtySet.remove(op1);
					}
					System.out.println(";REMOVE " + op1);
					regMap.remove(op1);
				}
				else if (liveSet.contains(op1) && liveSet.contains(op2)) {
					String temReg = getNewRegister(op1, cfgNode, res);
					res.add(new TinyNode("move", s1, temReg));
					s1 = temReg;
				}
				else if (liveSet.contains(op1)) {
					if (dirtySet.contains(op1)) {
						res.add(new TinyNode("move", s2, calculateTinyRegister(op1)));
						dirtySet.remove(op1);
					}
					System.out.println(";REMOVE " + op1);
					regMap.remove(op1);
				}
				else {
					if (dirtySet.contains(op1)|| op1.startsWith("$T")) {
						res.add(new TinyNode("move", s1, calculateTinyRegister(op1)));
						dirtySet.remove(op1);
					}
					System.out.println(";REMOVE " + op1);
					regMap.remove(op1);
				}
				// String newreg = getNewRegister(r, res);
				res.add(new TinyNode(cmmd, s2, s1));
				System.out.println(";REG MAP " + r + " " + s1);
				regMap.put(r, s1);
				try{
					// Spilling for every IR sentence
					spilling(liveSet, res, false);
					// Considering the end of block
					// for (CFGNode acfgNode : cfgNode.getSuccessor()) {
					if (cfgNode.isLead()) {
						System.out.println(";End Of Block");
						spilling(liveSet, res, true);
					}
					// }
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				// Add some Jumps
				while (!stk.isEmpty()) {
					res.add(stk.pop());
				}
		
				return res;
			}
			else if (isRegister(op1) && isRegister(op2)) {
				s1 = getTinyRegister(op2,op1, cfgNode, res);
				s2 = getTinyRegister(op1,op2, cfgNode, res);
			}
			else if (isRegister(op1) && !isGlobal(op2)) {
				s2 = getTinyRegister(op1,op2, cfgNode, res);
				s1 = op2;
			}
			else if (!isGlobal(op1) && isRegister(op2)) {
				s1 = getTinyRegister(op2,op1, cfgNode, res);
				s2 = getTinyRegister(op1,op2, cfgNode, res);
				res.add(new TinyNode("move", op1, s2));
				
			}
			else if (isGlobal(op1) && isGlobal(op2)) {
				boolean b1 = regMap.containsKey(op1);
				boolean b2 = regMap.containsKey(op2);
				s1 = getTinyRegister(op2,op1, cfgNode, res);
				s2 = getTinyRegister(op1,op2, cfgNode, res);
				if (!b1) res.add(new TinyNode("move", op1, s2));
				if (!b2) res.add(new TinyNode("move", op2, s1));
			}
			else if (isGlobal(op1) && !isRegister(op2)) {
				boolean b = regMap.containsKey(op1);
				s2 = getTinyRegister(op1,op2, cfgNode, res);
				s1 = op2;
				if (!b) res.add(new TinyNode("move", op1, s2));
			}
			else if (isGlobal(op1) && isRegister(op2)) {
				boolean b = regMap.containsKey(op1);
				s1 = getTinyRegister(op2,op1, cfgNode, res);
				s2 = getTinyRegister(op1,op2, cfgNode, res);
				if (!b) res.add(new TinyNode("move", op1, s2));
			}
			else if (isGlobal(op2) && !isRegister(op1)) {
				boolean b = regMap.containsKey(op2);
				s1 = op1;
				s2 = getTinyRegister(op1,op2, cfgNode, res);
				if (!b) res.add(new TinyNode("move", op2, s2));
			}
			else if (isGlobal(op2) && isRegister(op1)){
				boolean b = regMap.containsKey(op2);
				s1 = getTinyRegister(op2,op1, cfgNode, res);
				s2 = getTinyRegister(op1,op2, cfgNode, res);
				if (!b) res.add(new TinyNode("move", op2, s1));
			}
			else { 
				// no register, both are number
				s2 = getTinyRegister(op1,"", cfgNode, res);
				res.add(new TinyNode("move", op1, s2));
				s1 = op2;
			}
			try {
			// Empty the same register
			Set<String> tmpSet = new HashSet<>(regMap.keySet());
			for (String regKey : tmpSet) {
				if (regMap.containsKey(regKey) && regMap.get(regKey) != null && regMap.get(regKey).equals(s2)) {
					// When empty if it is a dirty global, then you have to memorize it.
					if(isGlobal(regKey) && dirtySet.contains(regKey)) {
						res.add(new TinyNode("move", s2, regKey));
					}
					// If this removed node is not live and dirty, then we need to spill it.
					else if (!cfgNode.getLiveOutSet().contains(regKey) && (regKey.startsWith("$T") || dirtySet.contains(regKey))) {
						res.add(new TinyNode("move", s2, calculateTinyRegister(regKey)));
					}
					if (dirtySet.contains(regKey)) dirtySet.remove(regKey);
					System.out.println(";REMOVE " + regKey);
					regMap.remove(regKey);
				}
			}
			
			regMap.put(r, s2);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			res.add(new TinyNode(cmmd, s1, s2));
		}

		Set<String> liveSet = cfgNode.getLiveOutSet();
		// Has to initialize here to get rid of cucurrency Error
		
		try{
			
			// Spilling for every IR sentence
			spilling(liveSet, res, false);
			// Considering the end of block
			// for (CFGNode acfgNode : cfgNode.getSuccessor()) {
			if (cfgNode.isLead()) {
				System.out.println(";End Of Block");
				spilling(liveSet, res, true);
			}
			// }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		// Add some Jumps
		while (!stk.isEmpty()) {
			res.add(stk.pop());
		}
		
		return res;
	}

	private void spilling(Set<String> liveSet, ArrayList<TinyNode> res, boolean endOfBlock) {
		Set<String> keySet = new HashSet<String>(regMap.keySet());
		for (String str : keySet) {
			System.out.println(";IREND SPILLING " + str);
			
			if (str == null || str.equals("")) {
				System.out.println(";NULL");
				break;
			}
			if (!endOfBlock && !liveSet.contains(str)) {
				System.out.println(";LIVE");
				if (str.startsWith("$T") || dirtySet.contains(str)) {
					System.out.println(";Dirty");
					res.add(new TinyNode("move", regMap.get(str), calculateTinyRegister(str)));
					if (dirtySet.contains(str)) dirtySet.remove(str);
				}
				System.out.println(";REMOVE " + str);
				regMap.remove(str);
			}
			if (endOfBlock && liveSet.contains(str)) {
				System.out.println(";LIVE");
				if (dirtySet.contains(str)) {
					System.out.println(";Dirty");
					res.add(new TinyNode("move", regMap.get(str), calculateTinyRegister(str)));
					dirtySet.remove(str);
				}
				System.out.println(";REMOVE " + str);
				regMap.remove(str);
			}
		}
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
	private boolean isGlobal(String str) {
		for (Symbol s : globalSymbols) {
			if (s.getName().equals(str)) {
				return true;
			}
		}
		return false;
	}
	private boolean isRegister(String str) {
		if (str == null) return false;
		return (str.contains("$"));
	}	
	public boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}  
	public boolean isSymbol(String str) {
		return !(isRegister(str) || isNumeric(str));
	}
}
