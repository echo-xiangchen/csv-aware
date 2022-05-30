package expr.antlrExtend;

// Generated from PCparser.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PCparserLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, TRUE=3, FALSE=4, NOT=5, AND=6, OR=7, EQUAL=8, NOTEQUAL=9, 
		GREATERTHAN=10, LESSTHAN=11, GREATEROREQUAL=12, LESSOREQUAL=13, SHIFTLEFT=14, 
		VAR=15, ID=16, WS=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "TRUE", "FALSE", "NOT", "AND", "OR", "EQUAL", "NOTEQUAL", 
			"GREATERTHAN", "LESSTHAN", "GREATEROREQUAL", "LESSOREQUAL", "SHIFTLEFT", 
			"VAR", "ID", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", null, null, "'!'", "'&&'", "'||'", "'=='", "'!='", 
			"'>'", "'<'", "'>='", "'<='", "'<<'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "TRUE", "FALSE", "NOT", "AND", "OR", "EQUAL", "NOTEQUAL", 
			"GREATERTHAN", "LESSTHAN", "GREATEROREQUAL", "LESSOREQUAL", "SHIFTLEFT", 
			"VAR", "ID", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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


	public PCparserLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "PCparser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23\177\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\66"+
		"\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5G"+
		"\n\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13"+
		"\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20r\n\20\3\21\6\21"+
		"u\n\21\r\21\16\21v\3\22\6\22z\n\22\r\22\16\22{\3\22\3\22\2\2\23\3\3\5"+
		"\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23\3\2\4\7\2//\61;C\\aac|\4\2\13\f\"\"\2\u0087\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5\'"+
		"\3\2\2\2\7\65\3\2\2\2\tF\3\2\2\2\13H\3\2\2\2\rJ\3\2\2\2\17M\3\2\2\2\21"+
		"P\3\2\2\2\23S\3\2\2\2\25V\3\2\2\2\27X\3\2\2\2\31Z\3\2\2\2\33]\3\2\2\2"+
		"\35`\3\2\2\2\37q\3\2\2\2!t\3\2\2\2#y\3\2\2\2%&\7*\2\2&\4\3\2\2\2\'(\7"+
		"+\2\2(\6\3\2\2\2)*\7v\2\2*+\7t\2\2+,\7w\2\2,\66\7g\2\2-.\7V\2\2./\7T\2"+
		"\2/\60\7W\2\2\60\66\7G\2\2\61\62\7V\2\2\62\63\7t\2\2\63\64\7w\2\2\64\66"+
		"\7g\2\2\65)\3\2\2\2\65-\3\2\2\2\65\61\3\2\2\2\66\b\3\2\2\2\678\7h\2\2"+
		"89\7c\2\29:\7n\2\2:;\7u\2\2;G\7g\2\2<=\7H\2\2=>\7C\2\2>?\7N\2\2?@\7U\2"+
		"\2@G\7G\2\2AB\7H\2\2BC\7c\2\2CD\7n\2\2DE\7u\2\2EG\7g\2\2F\67\3\2\2\2F"+
		"<\3\2\2\2FA\3\2\2\2G\n\3\2\2\2HI\7#\2\2I\f\3\2\2\2JK\7(\2\2KL\7(\2\2L"+
		"\16\3\2\2\2MN\7~\2\2NO\7~\2\2O\20\3\2\2\2PQ\7?\2\2QR\7?\2\2R\22\3\2\2"+
		"\2ST\7#\2\2TU\7?\2\2U\24\3\2\2\2VW\7@\2\2W\26\3\2\2\2XY\7>\2\2Y\30\3\2"+
		"\2\2Z[\7@\2\2[\\\7?\2\2\\\32\3\2\2\2]^\7>\2\2^_\7?\2\2_\34\3\2\2\2`a\7"+
		">\2\2ab\7>\2\2b\36\3\2\2\2cr\5!\21\2de\5!\21\2ef\7\60\2\2fg\5!\21\2gr"+
		"\3\2\2\2hi\5!\21\2ij\7*\2\2jk\7+\2\2kr\3\2\2\2lm\5!\21\2mn\7*\2\2no\5"+
		"!\21\2op\7+\2\2pr\3\2\2\2qc\3\2\2\2qd\3\2\2\2qh\3\2\2\2ql\3\2\2\2r \3"+
		"\2\2\2su\t\2\2\2ts\3\2\2\2uv\3\2\2\2vt\3\2\2\2vw\3\2\2\2w\"\3\2\2\2xz"+
		"\t\3\2\2yx\3\2\2\2z{\3\2\2\2{y\3\2\2\2{|\3\2\2\2|}\3\2\2\2}~\b\22\2\2"+
		"~$\3\2\2\2\b\2\65Fqv{\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}