// Generated from Micro.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MicroParser}.
 */
public interface MicroListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MicroParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MicroParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MicroParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#id}.
	 * @param ctx the parse tree
	 */
	void enterId(MicroParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#id}.
	 * @param ctx the parse tree
	 */
	void exitId(MicroParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#pgm_body}.
	 * @param ctx the parse tree
	 */
	void enterPgm_body(MicroParser.Pgm_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#pgm_body}.
	 * @param ctx the parse tree
	 */
	void exitPgm_body(MicroParser.Pgm_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(MicroParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(MicroParser.DeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#string_decl}.
	 * @param ctx the parse tree
	 */
	void enterString_decl(MicroParser.String_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#string_decl}.
	 * @param ctx the parse tree
	 */
	void exitString_decl(MicroParser.String_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#str}.
	 * @param ctx the parse tree
	 */
	void enterStr(MicroParser.StrContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#str}.
	 * @param ctx the parse tree
	 */
	void exitStr(MicroParser.StrContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void enterVar_decl(MicroParser.Var_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void exitVar_decl(MicroParser.Var_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#var_type}.
	 * @param ctx the parse tree
	 */
	void enterVar_type(MicroParser.Var_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#var_type}.
	 * @param ctx the parse tree
	 */
	void exitVar_type(MicroParser.Var_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#any_type}.
	 * @param ctx the parse tree
	 */
	void enterAny_type(MicroParser.Any_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#any_type}.
	 * @param ctx the parse tree
	 */
	void exitAny_type(MicroParser.Any_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#id_list}.
	 * @param ctx the parse tree
	 */
	void enterId_list(MicroParser.Id_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#id_list}.
	 * @param ctx the parse tree
	 */
	void exitId_list(MicroParser.Id_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#id_tail}.
	 * @param ctx the parse tree
	 */
	void enterId_tail(MicroParser.Id_tailContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#id_tail}.
	 * @param ctx the parse tree
	 */
	void exitId_tail(MicroParser.Id_tailContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#param_decl_list}.
	 * @param ctx the parse tree
	 */
	void enterParam_decl_list(MicroParser.Param_decl_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#param_decl_list}.
	 * @param ctx the parse tree
	 */
	void exitParam_decl_list(MicroParser.Param_decl_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#param_decl}.
	 * @param ctx the parse tree
	 */
	void enterParam_decl(MicroParser.Param_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#param_decl}.
	 * @param ctx the parse tree
	 */
	void exitParam_decl(MicroParser.Param_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#param_decl_tail}.
	 * @param ctx the parse tree
	 */
	void enterParam_decl_tail(MicroParser.Param_decl_tailContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#param_decl_tail}.
	 * @param ctx the parse tree
	 */
	void exitParam_decl_tail(MicroParser.Param_decl_tailContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#func_declarations}.
	 * @param ctx the parse tree
	 */
	void enterFunc_declarations(MicroParser.Func_declarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#func_declarations}.
	 * @param ctx the parse tree
	 */
	void exitFunc_declarations(MicroParser.Func_declarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#func_decl}.
	 * @param ctx the parse tree
	 */
	void enterFunc_decl(MicroParser.Func_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#func_decl}.
	 * @param ctx the parse tree
	 */
	void exitFunc_decl(MicroParser.Func_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#func_body}.
	 * @param ctx the parse tree
	 */
	void enterFunc_body(MicroParser.Func_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#func_body}.
	 * @param ctx the parse tree
	 */
	void exitFunc_body(MicroParser.Func_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#stmt_list}.
	 * @param ctx the parse tree
	 */
	void enterStmt_list(MicroParser.Stmt_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#stmt_list}.
	 * @param ctx the parse tree
	 */
	void exitStmt_list(MicroParser.Stmt_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(MicroParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(MicroParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#base_stmt}.
	 * @param ctx the parse tree
	 */
	void enterBase_stmt(MicroParser.Base_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#base_stmt}.
	 * @param ctx the parse tree
	 */
	void exitBase_stmt(MicroParser.Base_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#assign_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssign_stmt(MicroParser.Assign_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#assign_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssign_stmt(MicroParser.Assign_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#assign_expr}.
	 * @param ctx the parse tree
	 */
	void enterAssign_expr(MicroParser.Assign_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#assign_expr}.
	 * @param ctx the parse tree
	 */
	void exitAssign_expr(MicroParser.Assign_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#read_stmt}.
	 * @param ctx the parse tree
	 */
	void enterRead_stmt(MicroParser.Read_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#read_stmt}.
	 * @param ctx the parse tree
	 */
	void exitRead_stmt(MicroParser.Read_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#write_stmt}.
	 * @param ctx the parse tree
	 */
	void enterWrite_stmt(MicroParser.Write_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#write_stmt}.
	 * @param ctx the parse tree
	 */
	void exitWrite_stmt(MicroParser.Write_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturn_stmt(MicroParser.Return_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturn_stmt(MicroParser.Return_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MicroParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MicroParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#expr_prefix}.
	 * @param ctx the parse tree
	 */
	void enterExpr_prefix(MicroParser.Expr_prefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#expr_prefix}.
	 * @param ctx the parse tree
	 */
	void exitExpr_prefix(MicroParser.Expr_prefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(MicroParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(MicroParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#factor_prefix}.
	 * @param ctx the parse tree
	 */
	void enterFactor_prefix(MicroParser.Factor_prefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#factor_prefix}.
	 * @param ctx the parse tree
	 */
	void exitFactor_prefix(MicroParser.Factor_prefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#postfix_expr}.
	 * @param ctx the parse tree
	 */
	void enterPostfix_expr(MicroParser.Postfix_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#postfix_expr}.
	 * @param ctx the parse tree
	 */
	void exitPostfix_expr(MicroParser.Postfix_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#call_expr}.
	 * @param ctx the parse tree
	 */
	void enterCall_expr(MicroParser.Call_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#call_expr}.
	 * @param ctx the parse tree
	 */
	void exitCall_expr(MicroParser.Call_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#expr_list}.
	 * @param ctx the parse tree
	 */
	void enterExpr_list(MicroParser.Expr_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#expr_list}.
	 * @param ctx the parse tree
	 */
	void exitExpr_list(MicroParser.Expr_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#expr_list_tail}.
	 * @param ctx the parse tree
	 */
	void enterExpr_list_tail(MicroParser.Expr_list_tailContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#expr_list_tail}.
	 * @param ctx the parse tree
	 */
	void exitExpr_list_tail(MicroParser.Expr_list_tailContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(MicroParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(MicroParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#addop}.
	 * @param ctx the parse tree
	 */
	void enterAddop(MicroParser.AddopContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#addop}.
	 * @param ctx the parse tree
	 */
	void exitAddop(MicroParser.AddopContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#mulop}.
	 * @param ctx the parse tree
	 */
	void enterMulop(MicroParser.MulopContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#mulop}.
	 * @param ctx the parse tree
	 */
	void exitMulop(MicroParser.MulopContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void enterIf_stmt(MicroParser.If_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void exitIf_stmt(MicroParser.If_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#else_part}.
	 * @param ctx the parse tree
	 */
	void enterElse_part(MicroParser.Else_partContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#else_part}.
	 * @param ctx the parse tree
	 */
	void exitElse_part(MicroParser.Else_partContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCond(MicroParser.CondContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCond(MicroParser.CondContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#compop}.
	 * @param ctx the parse tree
	 */
	void enterCompop(MicroParser.CompopContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#compop}.
	 * @param ctx the parse tree
	 */
	void exitCompop(MicroParser.CompopContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#init_stmt}.
	 * @param ctx the parse tree
	 */
	void enterInit_stmt(MicroParser.Init_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#init_stmt}.
	 * @param ctx the parse tree
	 */
	void exitInit_stmt(MicroParser.Init_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#incr_stmt}.
	 * @param ctx the parse tree
	 */
	void enterIncr_stmt(MicroParser.Incr_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#incr_stmt}.
	 * @param ctx the parse tree
	 */
	void exitIncr_stmt(MicroParser.Incr_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFor_stmt(MicroParser.For_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFor_stmt(MicroParser.For_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#aug_stmt_list}.
	 * @param ctx the parse tree
	 */
	void enterAug_stmt_list(MicroParser.Aug_stmt_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#aug_stmt_list}.
	 * @param ctx the parse tree
	 */
	void exitAug_stmt_list(MicroParser.Aug_stmt_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#aug_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAug_stmt(MicroParser.Aug_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#aug_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAug_stmt(MicroParser.Aug_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#aug_if_stmt}.
	 * @param ctx the parse tree
	 */
	void enterAug_if_stmt(MicroParser.Aug_if_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#aug_if_stmt}.
	 * @param ctx the parse tree
	 */
	void exitAug_if_stmt(MicroParser.Aug_if_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroParser#aug_else_part}.
	 * @param ctx the parse tree
	 */
	void enterAug_else_part(MicroParser.Aug_else_partContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroParser#aug_else_part}.
	 * @param ctx the parse tree
	 */
	void exitAug_else_part(MicroParser.Aug_else_partContext ctx);
}