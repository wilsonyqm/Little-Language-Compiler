import java.util.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MicroIRListener extends MicroBaseListener{
	
	CodeGenerater codeGenerater;
	
    SymbolTableTree tree;
    
    ParseTreeProperty<NodeInfo> parseTreeValue;
    
	private int blocknum;
	
	private int registernum;
	
	
	MicroIRListener(){
		this.tree = new SymbolTableTree();
		this.codeGenerater = new CodeGenerater();
		this.parseTreeValue = new ParseTreeProperty<NodeInfo>();
		this.blocknum = 1;
		this.registernum = 0;
	}
	
	//********************helper functions************************************
	private void addtype(String curr_val, SymbolTable table) {
		if (curr_val.startsWith("STRING", 0)) {
			String[] str_val = curr_val.substring(6).split(":=");
			Symbol symbol = new Symbol(str_val[0], "STRING",str_val[1]);
			table.addEntry(symbol);
		}
		else if (curr_val.startsWith("INT", 0)) {
			String[] int_val = curr_val.substring(3).split(",");
			for (String str : int_val) {
				Symbol symbol = new Symbol(str, "INT");
				table.addEntry(symbol);
			}
		}
		else if (curr_val.startsWith("FLOAT", 0)) {
			String[] float_val = curr_val.substring(5).split(",");
			for (String str : float_val) {
				Symbol symbol = new Symbol(str, "FLOAT");
				table.addEntry(symbol);
			}
		}
	}
	
	private void func_addtype(String curr_val, SymbolTable table) {
		String[] int_val = curr_val.split(",");
		for (String str : int_val) {
		if (curr_val.startsWith("INT", 0)) {
				str = str.substring(3);
				Symbol symbol = new Symbol(str, "INT");
				table.addEntry(symbol);
		}
		else if (curr_val.startsWith("FLOAT", 0)) {
				str = str.substring(5);
				Symbol symbol = new Symbol(str, "FLOAT");
				table.addEntry(symbol);
			}
		}
	}
	
	private String getBlkName() {
		return "BLOCK " + blocknum++; 
	}
	
	private NodeInfo getValue(ParseTree ctx) {
		if(ctx == null || ctx.getText() == "")
			return null;
		return parseTreeValue.get(ctx);
	}
	
	private void setValue(ParseTree ctx, NodeInfo value){
		parseTreeValue.put(ctx,value);
	}
	
	private String getRegister(){
		registernum++;
		String registerName = new String("$T"+registernum);
		//Symbol symbol = new Symbol(registerName, type);
		//tree.currscope.addEntry(symbol);
		return registerName;
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
	
	
	//*******************************helper functions*************************************
	
	@Override public void enterPgm_body(MicroParser.Pgm_bodyContext ctx) {
	
		if (ctx.getChild(0) == null ||ctx.getChild(0).getText()=="") return;
		String[] global_vars = ctx.getChild(0).getText().split(";");
		for (int i = 0; i < global_vars.length; i++) {
			String curr_val = global_vars[i];
			addtype(curr_val, tree.root);
		}
	}
	@Override public void exitPgm_body(MicroParser.Pgm_bodyContext ctx) { 
	
		//tree.printAll(tree.root);
		codeGenerater.printIRNodes();
	}

	@Override public void enterFunc_decl(MicroParser.Func_declContext ctx) {
	
		SymbolTable table = new SymbolTable(ctx.getChild(2).getText());
		tree.currscope.addChild(table);
		tree.enterscope();
		if (ctx.getChild(4) != null) {
			func_addtype(ctx.getChild(4).getText(), table);
		}
		if (ctx.getChild(7) != null && ctx.getChild(7).getChild(0) != null) {
			String str = ctx.getChild(7).getChild(0).getText();
			if (str.length() == 0) return;
			addtype(str.substring(0, str.length() - 1), table);
		}
	}
	@Override public void exitFunc_decl(MicroParser.Func_declContext ctx) {
	
		tree.exitscope();
	}
	
	@Override public void enterIf_stmt(MicroParser.If_stmtContext ctx) {

		SymbolTable table = new SymbolTable(getBlkName());
		tree.currscope.addChild(table);
		tree.enterscope();
		if(ctx.getChild(4)==null || ctx.getChild(4).getText() == "")
			return;
		String[] global_vars = ctx.getChild(4).getText().split(";");
		for (int i = 0; i < global_vars.length; i++) {
			String curr_val = global_vars[i];
			addtype(curr_val, table);
		}
	
	}
	@Override public void exitIf_stmt(MicroParser.If_stmtContext ctx) {
	
		tree.exitscope();
	}
	
	@Override public void enterElse_part(MicroParser.Else_partContext ctx) {
	
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
			addtype(curr_val, table);
		}
		
	}
	
	@Override public void exitElse_part(MicroParser.Else_partContext ctx) {
	
		if(ctx.getChild(0) == null)
			return;
		tree.exitscope();
	}
	
	@Override public void enterFor_stmt(MicroParser.For_stmtContext ctx) { 

		SymbolTable table = new SymbolTable(getBlkName());
		tree.currscope.addChild(table);
		tree.enterscope();
		if(ctx.getChild(8)==null || ctx.getChild(8).getText() == "")
		return;
		String[] global_vars = ctx.getChild(8).getText().split(";");
		for (int i = 0; i < global_vars.length; i++) {
			String curr_val = global_vars[i];
			addtype(curr_val, table);
		}
		
	}
	@Override public void exitFor_stmt(MicroParser.For_stmtContext ctx) {
	
		tree.exitscope();
	}
	
	@Override public void enterAug_if_stmt(MicroParser.Aug_if_stmtContext ctx) {
	
		SymbolTable table = new SymbolTable(getBlkName());
		tree.currscope.addChild(table);
		tree.enterscope();
		if(ctx.getChild(4)==null || ctx.getChild(4).getText() == "")
		return;
		String[] global_vars = ctx.getChild(4).getText().split(";");
		for (int i = 0; i < global_vars.length; i++) {
			String curr_val = global_vars[i];
			addtype(curr_val, table);
		}
	
	}
	
	@Override public void exitAug_if_stmt(MicroParser.Aug_if_stmtContext ctx) { 
	
		tree.exitscope();
	}
	
	@Override public void enterAug_else_part(MicroParser.Aug_else_partContext ctx) {
	
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
			addtype(curr_val, table);
		}		
	}
	
	@Override public void exitAug_else_part(MicroParser.Aug_else_partContext ctx) { 
	
		if(ctx.getChild(0) == null)
			return;
		tree.exitscope();
	}
	
	@Override public void exitRead_stmt(MicroParser.Read_stmtContext ctx){
			if(ctx.getChild(2)==null || ctx.getChild(2).getText()=="")
				return;
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
			if(ctx.getChild(2) == null || ctx.getChild(2).getText()=="")
				return;
			String[] ids = ctx.getChild(2).getText().split(",");
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
			}
	}
	
			
	@Override public void exitAssign_expr(MicroParser.Assign_exprContext ctx) {
	
			String result = ctx.getChild(0).getText();
			String type = tree.checkType(result);

			NodeInfo expr = getValue(ctx.getChild(2));
			String exprText = expr.getTemp();
		
			String registerName = getRegister();
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
			
			codeGenerater.addIRNode(new IRNode(opname,exprText,null,registerName));
			codeGenerater.addIRNode(new IRNode(opname, registerName, null, result));

	}
		
	
	@Override public void exitExpr(MicroParser.ExprContext ctx){
		
		NodeInfo expr_prefix = getValue(ctx.getChild(0));
	    NodeInfo factor = getValue(ctx.getChild(1));
		String factorType = factor.getType();
		String factorText = factor.getTemp();
		
		 
		if(expr_prefix != null){
		
		   String registerName = getRegister();
		   String temp = expr_prefix.getTemp();
		   String addop = expr_prefix.getOpCode();		  
		   String opCode = lookupOpCode(addop,factorType);
		   IRNode node = new IRNode(opCode,temp, factorText,registerName);
		   codeGenerater.addIRNode(node);
		   NodeInfo expr = new NodeInfo(null,registerName,factorType);
		   setValue(ctx,expr);
		}
		else{
		   NodeInfo expr = new NodeInfo(null,factorText,factorType);
		   setValue(ctx,expr);
		}
		
		
		  
	}
		
	@Override public void exitExpr_prefix(MicroParser.Expr_prefixContext ctx) {
	
		if(ctx.getText() == ""){
			return;
		}	
			NodeInfo expr_prefix = getValue(ctx.getChild(0));
			NodeInfo factor = getValue(ctx.getChild(1));
			String factorType = factor.getType();
			String factorText = factor.getTemp();
			String addop = ctx.getChild(2).getText();
			
			if(expr_prefix == null){
				
		        NodeInfo expr_prefix_new = new NodeInfo(addop,factorText,factorType);    
				setValue(ctx,expr_prefix_new);
			}
			
			else{
				
				String registerName = getRegister();
				String temp = expr_prefix.getTemp();
		        String mathOp = expr_prefix.getOpCode();		  
		        String opCode = lookupOpCode(mathOp,factorType);
		        IRNode node = new IRNode(opCode,temp, factorText,registerName);
		        codeGenerater.addIRNode(node);	  
		        NodeInfo expr_prefix_new = new NodeInfo(addop,registerName,factorType);
		        setValue(ctx,expr_prefix_new);
			
			}
			
						
	}
		
	@Override public void exitFactor(MicroParser.FactorContext ctx) {
				
			NodeInfo factor_prefix = getValue(ctx.getChild(0));
			NodeInfo postfix_expr = getValue(ctx.getChild(1));
			
			String postfixType = postfix_expr.getType();
			String postfixText = postfix_expr.getTemp();
			
			if(factor_prefix == null){
				NodeInfo factor = new NodeInfo(null,postfixText,postfixType);
				setValue(ctx,factor);
			}
			else{
				String registerName = getRegister();
				String temp = factor_prefix.getTemp();
				String mathOp = factor_prefix.getOpCode();
				String opCode = lookupOpCode(mathOp,postfixType);
				IRNode node = new IRNode(opCode,temp,postfixText,registerName);
				codeGenerater.addIRNode(node);
				NodeInfo factor = new NodeInfo(null,registerName,postfixType);
				setValue(ctx,factor);
			}
			
	}
		
	@Override public void exitFactor_prefix(MicroParser.Factor_prefixContext ctx) {
	
		if(ctx.getText() == ""){
			return;
		}	 
			NodeInfo factor_prefix = getValue(ctx.getChild(0));
			NodeInfo postfix_expr = getValue(ctx.getChild(1));
			String postfixType = postfix_expr.getType();
			String postfixText = postfix_expr.getTemp();
			String mulop = ctx.getChild(2).getText();
			
			if(factor_prefix == null){
		 
		        NodeInfo factor_prefix_new = new NodeInfo(mulop,postfixText,postfixType); 
		        setValue(ctx,factor_prefix_new);   
				
			}
			
			else{
				
				String registerName = getRegister();
				String temp = factor_prefix.getTemp();
		        String mathOp = factor_prefix.getOpCode();		  
		        String opCode = lookupOpCode(mathOp,postfixType);
		        IRNode node = new IRNode(opCode,temp, postfixText,registerName);
		        codeGenerater.addIRNode(node);	  
		        NodeInfo factor_prefix_new = new NodeInfo(mulop,registerName,postfixType);
		        setValue(ctx,factor_prefix_new);
		        	
			}
			
	}
		
	@Override public void exitPostfix_expr(MicroParser.Postfix_exprContext ctx) {
	
			NodeInfo postfix_expr = getValue(ctx.getChild(0));
			setValue(ctx,postfix_expr);
	}
	
	@Override public void exitPrimary(MicroParser.PrimaryContext ctx){
	
			NodeInfo expr = getValue(ctx.getChild(1));
			if(expr != null){
				setValue(ctx,expr);
			}
			else{
				String primary = ctx.getChild(0).getText();
			    String type = tree.checkType(primary);
			    if(!primary.matches("[a-zA-Z]+")){
			    	String registerName = getRegister();
			    	String opCode = lookupStoreCode(type);
			    	IRNode node = new IRNode(opCode,primary,null,registerName);
			    	codeGenerater.addIRNode(node);
					NodeInfo value = new NodeInfo(null,registerName,type);
					setValue(ctx,value);
				}
				else{
					NodeInfo value = getValue(ctx.getChild(0));
					setValue(ctx,value);
				}
			}
	}	
	
	@Override public void exitId(MicroParser.IdContext ctx) {
			String type = tree.checkType(ctx.getText());
			NodeInfo id = new NodeInfo(null,ctx.getText(),type);
			setValue(ctx,id);
	}
	
}