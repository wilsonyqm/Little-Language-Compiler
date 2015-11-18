import java.util.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MicroIRListener extends MicroBaseListener{
	
	CodeGenerater codeGenerater;
	
    SymbolTableTree tree;
      
    ParseTreeProperty<NodeInfo> parseTreeValue;
    
    ParseTreeProperty<Function> parseTreeFunction;
    
    ArrayList<Function> functionList;
     
	private int blocknum;
	
//	private int labelnum;
	
//	private int registernum;
	
	
	MicroIRListener(){
		this.tree = new SymbolTableTree();
//		this.codeGenerater = new CodeGenerater();
		this.functionList = new ArrayList<Function>();
		this.parseTreeValue = new ParseTreeProperty<NodeInfo>();
		this.parseTreeFunction = new ParseTreeProperty<Function>();
		this.blocknum = 1;
//		this.labelnum = 1;
//		this.registernum = 1;
	}
	
	//********************helper functions************************************
	private void addtype(String curr_val, SymbolTable table, String attr) {
		if (curr_val.startsWith("STRING", 0)) {
			String[] str_val = curr_val.substring(6).split(":=");
			Symbol symbol = new Symbol(str_val[0], "STRING",str_val[1],attr);
			table.addEntry(symbol);
		}
		else if (curr_val.startsWith("INT", 0)) {
			String[] int_val = curr_val.substring(3).split(",");
			for (String str : int_val) {
				Symbol symbol = new Symbol(str, "INT",attr);
				table.addEntry(symbol);
			}
		}
		else if (curr_val.startsWith("FLOAT", 0)) {
			String[] float_val = curr_val.substring(5).split(",");
			for (String str : float_val) {
				Symbol symbol = new Symbol(str, "FLOAT",attr);
				table.addEntry(symbol);
			}
		}
		
	}
	
	private void func_addtype(String curr_val, SymbolTable table, String attr) {
		String[] int_val = curr_val.split(",");
		for (String str : int_val) {
		if (curr_val.startsWith("INT", 0)) {
				str = str.substring(3);
				Symbol symbol = new Symbol(str, "INT",attr);
				table.addEntry(symbol);
		}
		else if (curr_val.startsWith("FLOAT", 0)) {
				str = str.substring(5);
				Symbol symbol = new Symbol(str, "FLOAT",attr);
				table.addEntry(symbol);
			}
		}
	}
	
	private ParseTree getNearestFor(ParseTree ctx){
		while(getValue(ctx)==null || getValue(ctx).getType()!="FOR"){
			ctx = ctx.getParent();
		}
		return ctx;
	}
	
	private void annotateTree(ParseTree ctx, String note){
		if(ctx == null || ctx.getText() == "")
			return;
		NodeInfo node = new NodeInfo(null,null,null,null,note);
		setValue(ctx,node);
		int num = ctx.getChildCount();
		for(int i=0; i<num;i++){
			annotateTree(ctx.getChild(i), note);
		}
	}
	
	private void annotateTreeFunc(ParseTree ctx, Function function){
		if(ctx == null || ctx.getText() == "")
			return;
		setFunction(ctx,function);
		int num = ctx.getChildCount();
		for(int i=0; i<num; i++){
			annotateTreeFunc(ctx.getChild(i),function);
		}
	}
	
	private NodeInfo getValue(ParseTree ctx) {
		if(ctx == null)
			return null;
		return parseTreeValue.get(ctx);
	}
	
	private void setValue(ParseTree ctx, NodeInfo value){
		parseTreeValue.put(ctx,value);
	}
	
	private Function getFunction(ParseTree ctx) {
		if(ctx == null)
			return null;
		return parseTreeFunction.get(ctx);
	}
	
	private void setFunction(ParseTree ctx, Function function){
		parseTreeFunction.put(ctx,function);
	}
	
		
	private String getBlkName() {
		return "BLOCK " + blocknum++; 
	}
	
//	private String getRegister(){
//			
//		return "$T"+registernum++; 
//	}
	
	private String getLabel(){
		return "label"+labelnum++;
		
	}	
	
	private String lookupOpCode(String operator, String type) {
		if (operator.equals("+")) {
			if (type.equals("INT"))
				return "ADDI";
			if (type.equals("FLOAT"))
				return "ADDF";
		}
		if (operator.equals("-")) {
			if (type.equals("INT"))
				return "SUBI";
			if (type.equals("FLOAT"))
				return "SUBF";
		}
		if (operator.equals("*")) {
			if (type.equals("INT"))
				return "MULTI";
			if (type.equals("FLOAT"))
				return "MULTF";
		}
		if (operator.equals("/")) {
			if (type.equals("INT"))
				return "DIVI";
			if (type.equals("FLOAT"))
				return "DIVF";
		}
		return "ERROR";
	}
	
	private String lookupStoreCode(String type){
		if(type.equals("INT"))
			return "STOREI";
		else if(type.equals("FLOAT"))
			return "STOREF";
		else
			return "ERROR";
	}
	
	private String lookupCompCode(String operator){
		if(operator.equals("<"))
			return "GE";
		if(operator.equals("<="))
			return "GT";
		if(operator.equals(">"))
			return "LE";
		if(operator.equals(">="))
			return "LT";
		if(operator.equals("="))
			return "NE";
		if(operator.equals("!="))
			return "EQ";
			
		return "ERROR";
			
	}
	
	private int getSymbolNum(SymbolTable table) {
		if (table == null || table.getChild().size() == 0) return 0;
		int sum = table.getTable().size();
		for (SymbolTable t : table.getChild()) {
			sum += getSymbolNum(t);
		}
		return sum;
	}
	//*******************************Listener functions*************************************
	
	@Override public void enterPgm_body(MicroParser.Pgm_bodyContext ctx) {
//	System.out.println("enterPgm_body");
		if (ctx.getChild(0) == null ||ctx.getChild(0).getText().equals("")) return;
		String[] global_vars = ctx.getChild(0).getText().split(";");
		for (int i = 0; i < global_vars.length; i++) {
			String curr_val = global_vars[i];
			addtype(curr_val, tree.root,"global");
		}
	}
	@Override public void exitPgm_body(MicroParser.Pgm_bodyContext ctx) { 
//	System.out.println("exitPgm_body");
		//tree.printAll(tree.root);
		codeGenerater.setSymbols(tree.getAllsymbols(tree.root));
		codeGenerater.printIRNodes();
		codeGenerater.printTinyNodes();
	}

	@Override public void enterFunc_decl(MicroParser.Func_declContext ctx) {
	
		SymbolTable table = new SymbolTable(ctx.getChild(2).getText());
		tree.currscope.addChild(table);
		tree.enterscope();
		int paraNum = 0;
		if (ctx.getChild(4) != null && !ctx.getChild(4).getText().equals("")) {
			paraNum = ctx.getChild(4).getText().split(",").length;
			func_addtype(ctx.getChild(4).getText(), table,"para");
		}
		if (ctx.getChild(7) != null && ctx.getChild(7).getChild(0) != null) {
			String str = ctx.getChild(7).getChild(0).getText();
			if (str.length() == 0) return;
			addtype(str.substring(0, str.length() - 1), table,"local");
		}
		
		Function function = new Function(table);
		annotateTreeFunction(ctx, function);
		CodeGenerater codeGenerater = function.getCodeGenerater();
		NodeInfo func_head = new NodeInfo("LABEL",null,null,ctx.getChild(2).getText());
		NodeInfo link = new NodeInfo("LINK",null,null,null);
		codeGenerater.addIRNode(fun_head);
		codeGenerater.addIRNode(link);
		codeGenerater.setparaNum(paraNum);
	}
	@Override public void exitFunc_decl(MicroParser.Func_declContext ctx) {
		int symbolnum = getSymbolNum(tree.root);
		CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
		codeGenerater.setlocalVarNum(symbolnum);
		
		tree.exitscope();
	}
	
	@Override public void enterIf_stmt(MicroParser.If_stmtContext ctx) {
//	System.out.println("enterIf_stmt");
		SymbolTable table = new SymbolTable(getBlkName());
		tree.currscope.addChild(table);
		tree.enterscope();
		
		String label1 = getLabel();
		String label2 = getLabel();
		NodeInfo cond = new NodeInfo(null,null,null,label1);
		setValue(ctx.getChild(2),cond);
		NodeInfo stmt = new NodeInfo("JUMP,LABEL",null,null,label2+","+label1);
		setValue(ctx.getChild(5),stmt);
		if(ctx.getChild(6).getText()!=""){
			NodeInfo else_part = new NodeInfo("JUMP",null,null,label2);
			setValue(ctx.getChild(6),else_part);
		}
		NodeInfo if_stmt = new NodeInfo("LABEL",null,null,label2);
		setValue(ctx, if_stmt);
		
		if(ctx.getChild(4)==null || ctx.getChild(4).getText() == "")
			return;
		String[] global_vars = ctx.getChild(4).getText().split(";");
		for (int i = 0; i < global_vars.length; i++) {
			String curr_val = global_vars[i];
			addtype(curr_val, table,"local");
		}
			
	}
	
	@Override public void exitIf_stmt(MicroParser.If_stmtContext ctx) {
//	System.out.println("exitIf_stmt");
		CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
		tree.exitscope();	
		NodeInfo if_stmt = getValue(ctx);
		String opCode = if_stmt.getOpCode();
		String label = if_stmt.getBranch();
		IRNode node = new IRNode(opCode,null,null,label);
		codeGenerater.addIRNode(node);
		
	}
	
	@Override public void enterElse_part(MicroParser.Else_partContext ctx) {
//	System.out.println("enterElse_part");
	
		if (ctx.getChild(0) == null)
			return;
		SymbolTable table = new SymbolTable(getBlkName());
		tree.currscope.addChild(table);
		tree.enterscope();
		if(ctx.getChild(1) == null || ctx.getChild(1).getText()=="")
			return;
		String[] global_vars = ctx.getChild(1).getText().split(";");
		for (int i = 0; i < global_vars.length; i++) {
			String curr_val = global_vars[i];
			addtype(curr_val, table,"local");
		}
		
	}
	
	@Override public void exitElse_part(MicroParser.Else_partContext ctx) {
//	System.out.println("exitElse_part");
		
		if(ctx.getChild(0) == null)
			return;
		CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
		NodeInfo else_part = getValue(ctx);
		String opCode = else_part.getOpCode();
		String label = else_part.getBranch();
		IRNode node = new IRNode(opCode,null,null,label);
		codeGenerater.addIRNode(node);
		
		tree.exitscope();
	}
	
	@Override public void enterFor_stmt(MicroParser.For_stmtContext ctx) { 
//	System.out.println("enterFor_stmt");
	
		SymbolTable table = new SymbolTable(getBlkName());
		tree.currscope.addChild(table);
		tree.enterscope();
		
		String label1 = getLabel();
		String label2 = getLabel();
		String label3 = getLabel();
		
		NodeInfo init_stmt = new NodeInfo("LABEL",null,null,label1);
		setValue(ctx.getChild(2),init_stmt);

		NodeInfo cond = new NodeInfo(null,null,null,label3);
		setValue(ctx.getChild(4),cond);
		
		
		NodeInfo incr_stmt = new NodeInfo("JUMP",null,null,label1);
		setValue(ctx.getChild(6),incr_stmt);
		
		NodeInfo stmt = new NodeInfo("LABEL,LABEL",null,null,label2+","+label3);
		setValue(ctx.getChild(9), stmt);
		
		NodeInfo for_stmt = new NodeInfo("LABEL",null,"FOR",label3);
		setValue(ctx, for_stmt);
		
		if(ctx.getChild(8)==null || ctx.getChild(8).getText() == "")
			return;
		String[] global_vars = ctx.getChild(8).getText().split(";");
		for (int i = 0; i < global_vars.length; i++) {
			String curr_val = global_vars[i];
			addtype(curr_val, table,"local");
		}
		
	}
	@Override public void exitFor_stmt(MicroParser.For_stmtContext ctx) {
//	System.out.println("exitFor_stmt");
		CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
		codeGenerater.addIncrToIR();
		codeGenerater.dropIncrArray();
		
		NodeInfo incr_stmt = getValue(ctx.getChild(6));
		String opCode_incr = incr_stmt.getOpCode();
		String label_incr = incr_stmt.getBranch();
		IRNode node_incr = new IRNode(opCode_incr,null,null,label_incr);
		codeGenerater.addIRNode(node_incr);
				
		String opCode = getValue(ctx).getOpCode();
		String label = getValue(ctx).getBranch();
		IRNode node = new IRNode(opCode,null,null,label);
		codeGenerater.addIRNode(node);
		tree.exitscope();
		
	}
	
	@Override public void enterAug_if_stmt(MicroParser.Aug_if_stmtContext ctx) {
//	System.out.println("enterAug_if_stmt");
	
		SymbolTable table = new SymbolTable(getBlkName());
		tree.currscope.addChild(table);
		tree.enterscope();
		
		String label1 = getLabel();
		String label2 = getLabel();
		NodeInfo cond = new NodeInfo(null,null,null,label1);
		setValue(ctx.getChild(2),cond);
		NodeInfo stmt = new NodeInfo("JUMP,LABEL",null,null,label2+","+label1);
		setValue(ctx.getChild(5),stmt);
		NodeInfo else_part = new NodeInfo("JUMP",null,null,label2);
		setValue(ctx.getChild(6),else_part);
		NodeInfo if_stmt = new NodeInfo("LABEL",null,null,label2);
		setValue(ctx, if_stmt);
		
		if(ctx.getChild(4)==null || ctx.getChild(4).getText() == "")
			return;
		String[] global_vars = ctx.getChild(4).getText().split(";");
		for (int i = 0; i < global_vars.length; i++) {
			String curr_val = global_vars[i];
			addtype(curr_val, table,"local");
		}		
	
	}
	
	@Override public void exitAug_if_stmt(MicroParser.Aug_if_stmtContext ctx) { 
//	System.out.println("exitAug_if_stmt");
	
		tree.exitscope();
		CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();		
		NodeInfo if_stmt = getValue(ctx);
		String opCode = if_stmt.getOpCode();
		String label = if_stmt.getBranch();
		IRNode node = new IRNode(opCode,null,null,label);
		codeGenerater.addIRNode(node);
	}
	
	@Override public void enterAug_else_part(MicroParser.Aug_else_partContext ctx) {
//	System.out.println("enterAug_else_part");
	
		if(ctx.getChild(0) == null)
			return;
		SymbolTable table = new SymbolTable(getBlkName());
		tree.currscope.addChild(table);
		tree.enterscope();
		if(ctx.getChild(1) == null ||ctx.getChild(1).getText() == "")
			return;
		String[] global_vars = ctx.getChild(0).getText().split(";");
		for (int i = 0; i < global_vars.length; i++) {
			String curr_val = global_vars[i];
			addtype(curr_val, table,"local");
		}		
	}
	
	@Override public void exitAug_else_part(MicroParser.Aug_else_partContext ctx) { 
//	System.out.println("exitAug_else_part");
		
		if(ctx.getChild(0) == null)
			return;
		CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
		NodeInfo else_part = getValue(ctx);
		String opCode = else_part.getOpCode();
		String label = else_part.getBranch();
		IRNode node = new IRNode(opCode,null,null,label);
		codeGenerater.addIRNode(node);
		
		tree.exitscope();
	}
	
	@Override public void exitStmt_list(MicroParser.Stmt_listContext ctx) {
//	System.out.println("exitStmt_list");
			if(getValue(ctx) == null)
				return;
			CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
			String opCodeArray = getValue(ctx).getOpCode();
			String labelArray = getValue(ctx).getBranch();
			String[] opCodes = opCodeArray.split(",");
			String[] labels = labelArray.split(",");
			if(opCodes[0].equals("JUMP")){
				for(int i =0; i<opCodes.length;i++){
					IRNode node = new IRNode(opCodes[i],null,null,labels[i]);
					codeGenerater.addIRNode(node);
				}
			}
			else{
				IRNode node = new IRNode(opCodes[0],null,null,labels[0]);
				codeGenerater.addIRNode(node);
			}
	 }
	 
	@Override public void exitAug_stmt(MicroParser.Aug_stmtContext ctx) {
//	System.out.println("exitAug_stmt");
			CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
			if(ctx.getText().equals("CONTINUE;")){
				String label = getValue(getNearestFor(ctx).getChild(6)).getBranch();
				IRNode node = new IRNode("JUMP",null,null,label);
				codeGenerater.addIRNode(node);
			}
			else if (ctx.getText().equals("BREAK;")){
				String label = getValue(getNearestFor(ctx)).getBranch();
				IRNode node = new IRNode("JUMP",null,null,label);
				codeGenerater.addIRNode(node);
			}

	 }
	

	
	@Override public void exitAug_stmt_list(MicroParser.Aug_stmt_listContext ctx) {
//	System.out.println("exitAug_stmt_list");
			
			if(getValue(ctx) == null)
				return;
			CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
			String opCodeArray = getValue(ctx).getOpCode();
			String labelArray = getValue(ctx).getBranch();
			String[] opCodes = opCodeArray.split(",");
			String[] labels = labelArray.split(",");
			if(opCodes[0].equals("JUMP")){
				for(int i =0; i<opCodes.length;i++){
					IRNode node = new IRNode(opCodes[i],null,null,labels[i]);
					codeGenerater.addIRNode(node);
				}
			}
			else{
				IRNode node = new IRNode(opCodes[0],null,null,labels[0]);
				codeGenerater.addIRNode(node);
			}
	 }
	
	@Override public void enterIncr_stmt(MicroParser.Incr_stmtContext ctx) {
// System.out.println("enterIncr_stmt");
			CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
 			codeGenerater.createIncrArray();
 			int num = ctx.getChildCount();
 			for(int i=0;i<num;i++){
				annotateTree(ctx.getChild(i),"Incr_stmt");
			}
	}
	
	
	@Override public void exitInit_stmt(MicroParser.Init_stmtContext ctx) {
//	System.out.println("exitInit_stmt");
			CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
			NodeInfo init_stmt = getValue(ctx);
			String opCode = init_stmt.getOpCode();
			String label = init_stmt.getBranch();
			IRNode node = new IRNode(opCode,null,null,label);
			codeGenerater.addIRNode(node);
	 }
	
	@Override public void exitCompop(MicroParser.CompopContext ctx) {
//	System.out.println("exitCompop");
	
			String operator = ctx.getText();
			NodeInfo compOp = new NodeInfo(operator,null,null,null);
			setValue(ctx,compOp);
	}
	
	@Override public void exitCond(MicroParser.CondContext ctx) {
//	System.out.println("exitCond");
			CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
			String oprand1 = getValue(ctx.getChild(0)).getTemp();
			String oprand2 = getValue(ctx.getChild(2)).getTemp();
			String compOp = lookupCompCode(getValue(ctx.getChild(1)).getOpCode());
			String label = getValue(ctx).getBranch();
			IRNode node = new IRNode(compOp, oprand1,oprand2,label);
			codeGenerater.addIRNode(node);
	}

	@Override public void exitRead_stmt(MicroParser.Read_stmtContext ctx){
//	System.out.println("exitRead_stmt");
	
			if(ctx.getChild(2)==null || ctx.getChild(2).getText()=="")
				return;
			CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
			String[] ids = ctx.getChild(2).getText().split(",");
			for(int i=0; i<ids.length; i++){
				String type = tree.checkType(ids[i]);
				if(type == null){
					System.out.println("ERROR ID");
					return;
				}
				if (type.equals("INT")){
					IRNode node = new IRNode("READI",null,null,ids[i]);
					codeGenerater.addIRNode(node);
				}
				else if (type.equals("FLOAT")){
					IRNode node = new IRNode("READF",null,null,ids[i]);
					codeGenerater.addIRNode(node);
				}
			}
			
	}
	
	
	@Override public void exitWrite_stmt(MicroParser.Write_stmtContext ctx){
//	System.out.println("exitWrite_stmt");
	
			if(ctx.getChild(2) == null || ctx.getChild(2).getText()=="")
				return;
			CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
			String[] ids = ctx.getChild(2).getText().split("\\s*,\\s*");
			for(int i=0; i<ids.length; i++){
				String type = tree.checkType(ids[i]);
				if(type == null){
					System.out.println("ERROR ID");
					return;
				}
				if (type.equals("INT")){
					IRNode node = new IRNode("WRITEI",null,null,ids[i]);
					codeGenerater.addIRNode(node);
				}
				else if (type.equals("FLOAT")){
					IRNode node = new IRNode("WRITEF",null,null,ids[i]);
					codeGenerater.addIRNode(node);
				}
				else {
					IRNode node = new IRNode("WRITES",null,null,ids[i]);
					codeGenerater.addIRNode(node);
				}	
			}
	}
	
			
	@Override public void exitAssign_expr(MicroParser.Assign_exprContext ctx) {
//	System.out.println("exitAssign_expr");
			CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
			String result = ctx.getChild(0).getText();
			String type = tree.checkType(result);

			NodeInfo expr = getValue(ctx.getChild(2));
			String exprText = expr.getTemp();
		
			String opname = "";
			

			if(type == null){
					System.out.println("ERROR ID");
					return;
				}
			if (type.equals("INT")){

				opname = "STOREI";
			}

			else if (type.equals("FLOAT")){

				opname = "STOREF";
			}
			else {
				System.out.println("ERROR NAME");
				return;
			}
			
			IRNode node = new IRNode(opname,exprText,null,result);
			if(getValue(ctx) == null || getValue(ctx).getNote()!="Incr_stmt")
						codeGenerater.addIRNode(node);
			else
						codeGenerater.addIncrNode(node);

	}
		
	
	@Override public void exitExpr(MicroParser.ExprContext ctx){
//	System.out.println("exitExpr");
		
		NodeInfo expr_prefix = getValue(ctx.getChild(0));
	    NodeInfo factor = getValue(ctx.getChild(1));
		String factorType = factor.getType();
		String factorText = factor.getTemp();
		CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
		 
		if(expr_prefix != null ){
		
		   String registerName = getFunction(ctx).getRegister();
		   String temp = expr_prefix.getTemp();
		   String addop = expr_prefix.getOpCode();		  
		   String opCode = lookupOpCode(addop,factorType);
		   IRNode node = new IRNode(opCode,temp, factorText,registerName);
		   if(getValue(ctx) == null || getValue(ctx).getNote()!="Incr_stmt")
				codeGenerater.addIRNode(node);
		   else
				codeGenerater.addIncrNode(node);
		   NodeInfo expr = new NodeInfo(null,registerName,factorType,null);
		   setValue(ctx,expr);
		}
		else{
		   NodeInfo expr = new NodeInfo(null,factorText,factorType,null);
		   setValue(ctx,expr);
		}
		
		
		  
	}
		
	@Override public void exitExpr_prefix(MicroParser.Expr_prefixContext ctx) {
//	System.out.println("exitExpr_prefix");
		if(ctx.getText() == ""){
			return;
		}	
			CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
			NodeInfo expr_prefix = getValue(ctx.getChild(0));
			NodeInfo factor = getValue(ctx.getChild(1));
			String factorType = factor.getType();
			String factorText = factor.getTemp();
			String addop = ctx.getChild(2).getText();
			
			if(expr_prefix == null){
				
		        NodeInfo expr_prefix_new = new NodeInfo(addop,factorText,factorType,null);    
				setValue(ctx,expr_prefix_new);
			}
			
			else{
				
				String registerName = getFunction(ctx).getRegister();
				String temp = expr_prefix.getTemp();
		        String mathOp = expr_prefix.getOpCode();		  
		        String opCode = lookupOpCode(mathOp,factorType);
		        IRNode node = new IRNode(opCode,temp, factorText,registerName); 
				if(getValue(ctx) == null || getValue(ctx).getNote()!="Incr_stmt")
						codeGenerater.addIRNode(node);
				else
						codeGenerater.addIncrNode(node);
		        NodeInfo expr_prefix_new = new NodeInfo(addop,registerName,factorType,null);
		        setValue(ctx,expr_prefix_new);
			
			}
			
						
	}
		
	@Override public void exitFactor(MicroParser.FactorContext ctx) {
//	System.out.println("exitFactor");			
			NodeInfo factor_prefix = getValue(ctx.getChild(0));
			NodeInfo postfix_expr = getValue(ctx.getChild(1));
			
			String postfixType = postfix_expr.getType();
			String postfixText = postfix_expr.getTemp();
			CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
			if(factor_prefix == null ){
				NodeInfo factor = new NodeInfo(null,postfixText,postfixType,null);
				setValue(ctx,factor);
			}
			else{
				String registerName = getFunction(ctx).getRegister();
				String temp = factor_prefix.getTemp();
				String mathOp = factor_prefix.getOpCode();
				String opCode = lookupOpCode(mathOp,postfixType);
				IRNode node = new IRNode(opCode,temp,postfixText,registerName);
				if(getValue(ctx) == null || getValue(ctx).getNote()!="Incr_stmt")
						codeGenerater.addIRNode(node);
				else
						codeGenerater.addIncrNode(node);
				NodeInfo factor = new NodeInfo(null,registerName,postfixType,null);
				setValue(ctx,factor);
			}
			
	}
		
	@Override public void exitFactor_prefix(MicroParser.Factor_prefixContext ctx) {
//	System.out.println("exitFactor_prefix");
		if(ctx.getText() == ""){
			return;
		}	 
			CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
			NodeInfo factor_prefix = getValue(ctx.getChild(0));
			NodeInfo postfix_expr = getValue(ctx.getChild(1));
			String postfixType = postfix_expr.getType();
			String postfixText = postfix_expr.getTemp();
			String mulop = ctx.getChild(2).getText();
			
			if(factor_prefix == null ){
		 
		        NodeInfo factor_prefix_new = new NodeInfo(mulop,postfixText,postfixType,null); 
		        setValue(ctx,factor_prefix_new);   
				
			}
			
			else{
				
				String registerName = getFunction(ctx).getRegister();
				String temp = factor_prefix.getTemp();
		        String mathOp = factor_prefix.getOpCode();		  
		        String opCode = lookupOpCode(mathOp,postfixType);
		        IRNode node = new IRNode(opCode,temp, postfixText,registerName);
				if(getValue(ctx) == null || getValue(ctx).getNote()!="Incr_stmt")
					codeGenerater.addIRNode(node);
				else
					codeGenerater.addIncrNode(node);
		        NodeInfo factor_prefix_new = new NodeInfo(mulop,temp,postfixType,null);
		        setValue(ctx,factor_prefix_new);
		        	
			}
			
	}
		
	@Override public void exitPostfix_expr(MicroParser.Postfix_exprContext ctx) {
//	System.out.println("exit Postfix_expr");
			NodeInfo postfix_expr = getValue(ctx.getChild(0));
			setValue(ctx,postfix_expr);
	}
	
	@Override public void exitPrimary(MicroParser.PrimaryContext ctx){
//	System.out.println("exitPrimary");
			SymbolTable table = getFunction(ctx).getTable();
			CodeGenerater codeGenerater = getFunction(ctx).getCodeGenerater();
			NodeInfo expr = getValue(ctx.getChild(1));
			if(expr != null ){
				setValue(ctx,expr);
			}
			else{
				String primary = ctx.getChild(0).getText();
			    String type = tree.checkType(primary);
			    if(!primary.matches("[a-zA-Z]+")){
			    	String registerName = getFunction(ctx).getRegister();
			    	String opCode = lookupStoreCode(type);
			    	IRNode node = new IRNode(opCode,primary,null,registerName);
			    	if(getValue(ctx) == null || getValue(ctx).getNote()!="Incr_stmt")
						codeGenerater.addIRNode(node);
					else
						codeGenerater.addIncrNode(node);
					NodeInfo value = new NodeInfo(null,registerName,type,null);
					setValue(ctx,value);
				}
				else{
					NodeInfo value = getValue(ctx.getChild(0));
					setValue(ctx,value);
				}
			}
	}	
	
	@Override public void exitId(MicroParser.IdContext ctx) {
//	System.out.println("exitId");
			String type = tree.checkType(ctx.getText());
			NodeInfo id = new NodeInfo(null,ctx.getText(),type,null);
			setValue(ctx,id);
	}
}	
	
