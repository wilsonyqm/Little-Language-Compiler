// Generated from Microlexer.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Microlexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		KEYWORD=1, IDENTIFIER=2, INTLITERAL=3, FLOATLITERAL=4, STRINGLITERAL=5, 
		COMMENT=6, OPERATOR=7, WS=8;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"KEYWORD", "IDENTIFIER", "INTLITERAL", "FLOATLITERAL", "STRINGLITERAL", 
		"COMMENT", "OPERATOR", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "KEYWORD", "IDENTIFIER", "INTLITERAL", "FLOATLITERAL", "STRINGLITERAL", 
		"COMMENT", "OPERATOR", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public Microlexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Microlexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\n\u00a9\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2g\n\2\3\3\3\3\7\3k\n\3\f\3"+
		"\16\3n\13\3\3\4\6\4q\n\4\r\4\16\4r\3\5\7\5v\n\5\f\5\16\5y\13\5\3\5\3\5"+
		"\6\5}\n\5\r\5\16\5~\3\6\3\6\7\6\u0083\n\6\f\6\16\6\u0086\13\6\3\6\3\6"+
		"\3\7\3\7\3\7\3\7\7\7\u008e\n\7\f\7\16\7\u0091\13\7\3\7\3\7\3\7\3\7\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u00a1\n\b\3\t\6\t\u00a4\n\t\r"+
		"\t\16\t\u00a5\3\t\3\t\2\2\n\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\3\2\n\5"+
		"\2C\\aac|\6\2\62;C\\aac|\3\2\62;\3\2$$\3\2\f\f\6\2,-//\61\61??\6\2*+."+
		".=>@@\5\2\13\f\16\17\"\"\u00c5\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\3f\3\2\2\2"+
		"\5h\3\2\2\2\7p\3\2\2\2\tw\3\2\2\2\13\u0080\3\2\2\2\r\u0089\3\2\2\2\17"+
		"\u00a0\3\2\2\2\21\u00a3\3\2\2\2\23\24\7R\2\2\24\25\7T\2\2\25\26\7Q\2\2"+
		"\26\27\7I\2\2\27\30\7T\2\2\30\31\7C\2\2\31g\7O\2\2\32\33\7D\2\2\33\34"+
		"\7G\2\2\34\35\7I\2\2\35\36\7K\2\2\36g\7P\2\2\37 \7G\2\2 !\7P\2\2!g\7F"+
		"\2\2\"#\7H\2\2#$\7W\2\2$%\7P\2\2%&\7E\2\2&\'\7V\2\2\'(\7K\2\2()\7Q\2\2"+
		")g\7P\2\2*+\7T\2\2+,\7G\2\2,-\7C\2\2-g\7F\2\2./\7Y\2\2/\60\7T\2\2\60\61"+
		"\7K\2\2\61\62\7V\2\2\62g\7G\2\2\63\64\7K\2\2\64g\7H\2\2\65\66\7G\2\2\66"+
		"\67\7N\2\2\678\7U\2\28g\7G\2\29:\7H\2\2:g\7K\2\2;<\7H\2\2<=\7Q\2\2=g\7"+
		"T\2\2>?\7T\2\2?@\7Q\2\2@g\7H\2\2AB\7E\2\2BC\7Q\2\2CD\7P\2\2DE\7V\2\2E"+
		"F\7K\2\2FG\7P\2\2GH\7W\2\2Hg\7G\2\2IJ\7D\2\2JK\7T\2\2KL\7G\2\2LM\7C\2"+
		"\2Mg\7M\2\2NO\7T\2\2OP\7G\2\2PQ\7V\2\2QR\7W\2\2RS\7T\2\2Sg\7P\2\2TU\7"+
		"K\2\2UV\7P\2\2Vg\7V\2\2WX\7X\2\2XY\7Q\2\2YZ\7K\2\2Zg\7F\2\2[\\\7U\2\2"+
		"\\]\7V\2\2]^\7T\2\2^_\7K\2\2_`\7P\2\2`g\7I\2\2ab\7H\2\2bc\7N\2\2cd\7Q"+
		"\2\2de\7C\2\2eg\7V\2\2f\23\3\2\2\2f\32\3\2\2\2f\37\3\2\2\2f\"\3\2\2\2"+
		"f*\3\2\2\2f.\3\2\2\2f\63\3\2\2\2f\65\3\2\2\2f9\3\2\2\2f;\3\2\2\2f>\3\2"+
		"\2\2fA\3\2\2\2fI\3\2\2\2fN\3\2\2\2fT\3\2\2\2fW\3\2\2\2f[\3\2\2\2fa\3\2"+
		"\2\2g\4\3\2\2\2hl\t\2\2\2ik\t\3\2\2ji\3\2\2\2kn\3\2\2\2lj\3\2\2\2lm\3"+
		"\2\2\2m\6\3\2\2\2nl\3\2\2\2oq\t\4\2\2po\3\2\2\2qr\3\2\2\2rp\3\2\2\2rs"+
		"\3\2\2\2s\b\3\2\2\2tv\t\4\2\2ut\3\2\2\2vy\3\2\2\2wu\3\2\2\2wx\3\2\2\2"+
		"xz\3\2\2\2yw\3\2\2\2z|\7\60\2\2{}\t\4\2\2|{\3\2\2\2}~\3\2\2\2~|\3\2\2"+
		"\2~\177\3\2\2\2\177\n\3\2\2\2\u0080\u0084\7$\2\2\u0081\u0083\n\5\2\2\u0082"+
		"\u0081\3\2\2\2\u0083\u0086\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2"+
		"\2\2\u0085\u0087\3\2\2\2\u0086\u0084\3\2\2\2\u0087\u0088\7$\2\2\u0088"+
		"\f\3\2\2\2\u0089\u008a\7/\2\2\u008a\u008b\7/\2\2\u008b\u008f\3\2\2\2\u008c"+
		"\u008e\n\6\2\2\u008d\u008c\3\2\2\2\u008e\u0091\3\2\2\2\u008f\u008d\3\2"+
		"\2\2\u008f\u0090\3\2\2\2\u0090\u0092\3\2\2\2\u0091\u008f\3\2\2\2\u0092"+
		"\u0093\7\f\2\2\u0093\u0094\3\2\2\2\u0094\u0095\b\7\2\2\u0095\16\3\2\2"+
		"\2\u0096\u0097\7<\2\2\u0097\u00a1\7?\2\2\u0098\u00a1\t\7\2\2\u0099\u009a"+
		"\7#\2\2\u009a\u00a1\7?\2\2\u009b\u00a1\t\b\2\2\u009c\u009d\7>\2\2\u009d"+
		"\u00a1\7?\2\2\u009e\u009f\7@\2\2\u009f\u00a1\7?\2\2\u00a0\u0096\3\2\2"+
		"\2\u00a0\u0098\3\2\2\2\u00a0\u0099\3\2\2\2\u00a0\u009b\3\2\2\2\u00a0\u009c"+
		"\3\2\2\2\u00a0\u009e\3\2\2\2\u00a1\20\3\2\2\2\u00a2\u00a4\t\t\2\2\u00a3"+
		"\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a5\u00a6\3\2"+
		"\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a8\b\t\2\2\u00a8\22\3\2\2\2\f\2flrw"+
		"~\u0084\u008f\u00a0\u00a5\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}