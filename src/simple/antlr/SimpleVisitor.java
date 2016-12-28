// Generated from Simple.g4 by ANTLR 4.5.3

	package simple.antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SimpleParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SimpleVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SimpleParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(SimpleParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(SimpleParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#vardeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardeclaration(SimpleParser.VardeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(SimpleParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#functiondeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctiondeclaration(SimpleParser.FunctiondeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(SimpleParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(SimpleParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(SimpleParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(SimpleParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(SimpleParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NthChar}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNthChar(SimpleParser.NthCharContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToNthChar}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToNthChar(SimpleParser.ToNthCharContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FromNthChar}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFromNthChar(SimpleParser.FromNthCharContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FromNthToMthChar}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFromNthToMthChar(SimpleParser.FromNthToMthCharContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FirstIndexOf}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFirstIndexOf(SimpleParser.FirstIndexOfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LastIndexOF}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLastIndexOF(SimpleParser.LastIndexOFContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FrontTrim}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFrontTrim(SimpleParser.FrontTrimContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BackTrim}
	 * labeled alternative in {@link SimpleParser#strop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBackTrim(SimpleParser.BackTrimContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(SimpleParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#functioncall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctioncall(SimpleParser.FunctioncallContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#callargs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallargs(SimpleParser.CallargsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(SimpleParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#mexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMexp(SimpleParser.MexpContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#pexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPexp(SimpleParser.PexpContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(SimpleParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#ifblock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfblock(SimpleParser.IfblockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#whileblock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileblock(SimpleParser.WhileblockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#forblock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForblock(SimpleParser.ForblockContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#returnstatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnstatement(SimpleParser.ReturnstatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleParser#compareop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareop(SimpleParser.CompareopContext ctx);
}