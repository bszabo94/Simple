// Generated from Simple.g4 by ANTLR 4.5.3

	package simple.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SimpleParser}.
 */
public interface SimpleListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SimpleParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(SimpleParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(SimpleParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(SimpleParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(SimpleParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#vardeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVardeclaration(SimpleParser.VardeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#vardeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVardeclaration(SimpleParser.VardeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(SimpleParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(SimpleParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#functiondeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctiondeclaration(SimpleParser.FunctiondeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#functiondeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctiondeclaration(SimpleParser.FunctiondeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(SimpleParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(SimpleParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(SimpleParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(SimpleParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(SimpleParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(SimpleParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(SimpleParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(SimpleParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(SimpleParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(SimpleParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NthChar}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void enterNthChar(SimpleParser.NthCharContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NthChar}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void exitNthChar(SimpleParser.NthCharContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToNthChar}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void enterToNthChar(SimpleParser.ToNthCharContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToNthChar}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void exitToNthChar(SimpleParser.ToNthCharContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FromNthChar}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void enterFromNthChar(SimpleParser.FromNthCharContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FromNthChar}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void exitFromNthChar(SimpleParser.FromNthCharContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FromNthToMthChar}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void enterFromNthToMthChar(SimpleParser.FromNthToMthCharContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FromNthToMthChar}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void exitFromNthToMthChar(SimpleParser.FromNthToMthCharContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FirstIndexOf}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void enterFirstIndexOf(SimpleParser.FirstIndexOfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FirstIndexOf}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void exitFirstIndexOf(SimpleParser.FirstIndexOfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LastIndexOF}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void enterLastIndexOF(SimpleParser.LastIndexOFContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LastIndexOF}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void exitLastIndexOF(SimpleParser.LastIndexOFContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FrontTrim}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void enterFrontTrim(SimpleParser.FrontTrimContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FrontTrim}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void exitFrontTrim(SimpleParser.FrontTrimContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BackTrim}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void enterBackTrim(SimpleParser.BackTrimContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BackTrim}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 */
	void exitBackTrim(SimpleParser.BackTrimContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(SimpleParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(SimpleParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#functioncall}.
	 * @param ctx the parse tree
	 */
	void enterFunctioncall(SimpleParser.FunctioncallContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#functioncall}.
	 * @param ctx the parse tree
	 */
	void exitFunctioncall(SimpleParser.FunctioncallContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#callargs}.
	 * @param ctx the parse tree
	 */
	void enterCallargs(SimpleParser.CallargsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#callargs}.
	 * @param ctx the parse tree
	 */
	void exitCallargs(SimpleParser.CallargsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(SimpleParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(SimpleParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#mexp}.
	 * @param ctx the parse tree
	 */
	void enterMexp(SimpleParser.MexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#mexp}.
	 * @param ctx the parse tree
	 */
	void exitMexp(SimpleParser.MexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#pexp}.
	 * @param ctx the parse tree
	 */
	void enterPexp(SimpleParser.PexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#pexp}.
	 * @param ctx the parse tree
	 */
	void exitPexp(SimpleParser.PexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(SimpleParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(SimpleParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#ifblock}.
	 * @param ctx the parse tree
	 */
	void enterIfblock(SimpleParser.IfblockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#ifblock}.
	 * @param ctx the parse tree
	 */
	void exitIfblock(SimpleParser.IfblockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#whileblock}.
	 * @param ctx the parse tree
	 */
	void enterWhileblock(SimpleParser.WhileblockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#whileblock}.
	 * @param ctx the parse tree
	 */
	void exitWhileblock(SimpleParser.WhileblockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#forblock}.
	 * @param ctx the parse tree
	 */
	void enterForblock(SimpleParser.ForblockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#forblock}.
	 * @param ctx the parse tree
	 */
	void exitForblock(SimpleParser.ForblockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#returnstatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnstatement(SimpleParser.ReturnstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#returnstatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnstatement(SimpleParser.ReturnstatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleParser#compareop}.
	 * @param ctx the parse tree
	 */
	void enterCompareop(SimpleParser.CompareopContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleParser#compareop}.
	 * @param ctx the parse tree
	 */
	void exitCompareop(SimpleParser.CompareopContext ctx);
}