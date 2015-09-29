import java.util.*;
import org.antlr.v4.runtime.*;

public class MicroSymbolListener extends MicroBaseListener{
	

	public SymbolTableTree tree;
	private int blocknum;
	public MicroSymbolListener(){
		this.tree = new SymbolTableTree();
		this.blocknum = 1;
	}
	public void addtype(String curr_val, SymbolTable table) {
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
	public void func_addtype(String curr_val, SymbolTable table) {
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
	
	
	
	public String getBlkName() {
		return "BLOCK " + blocknum++; 
	}
	@Override public void enterPgm_body(MicroParser.Pgm_bodyContext ctx) {
	//System.out.println("enter Pgm_body");
		if (ctx.getChild(0) == null ||ctx.getChild(0).getText()=="") return;
		String[] global_vars = ctx.getChild(0).getText().split(";");
		for (int i = 0; i < global_vars.length; i++) {
			String curr_val = global_vars[i];
			addtype(curr_val, tree.root);
		}
	}
	@Override public void exitPgm_body(MicroParser.Pgm_bodyContext ctx) { 
	//System.out.println("exit Pgm_body");
		tree.printAll(tree.root);
	}

	@Override public void enterFunc_decl(MicroParser.Func_declContext ctx) {
	//System.out.println("enter Func_decl");
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
	//System.out.println("exit Func_decl");
		tree.exitscope();
	}
	@Override public void enterIf_stmt(MicroParser.If_stmtContext ctx) {
	//System.out.println("enter If_stmt");
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
	//System.out.println("exit If_stmt");
		tree.exitscope();
	}
	
	@Override public void enterElse_part(MicroParser.Else_partContext ctx) {
	//System.out.println("enter Else_part");
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
	//System.out.println("exit Else_part");
		if(ctx.getChild(0) == null)
			return;
		tree.exitscope();
	}
	
	@Override public void enterFor_stmt(MicroParser.For_stmtContext ctx) { 
	//System.out.println("enter for_stmt");
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
	//System.out.println("exit for_stmt");
		tree.exitscope();
	}
	
	@Override public void enterAug_if_stmt(MicroParser.Aug_if_stmtContext ctx) {
	//System.out.println("enter aug_if_stmt");
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
	//System.out.println("exit aug_if_stmt");
		tree.exitscope();
	}
	@Override public void enterAug_else_part(MicroParser.Aug_else_partContext ctx) {
	//System.out.println("enter aug_else_part");
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
	//System.out.println("exit aug_else_part");
		if(ctx.getChild(0) == null)
			return;
		tree.exitscope();
	}
}