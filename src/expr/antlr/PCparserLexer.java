// Generated from PCparser.g4 by ANTLR 4.9.2

package expr.antlr;

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
		T__0=1, T__1=2, TRUE=3, FALSE=4, NOT=5, AND=6, OR=7, COMMENT=8, WS=9, 
		ID=10;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "TRUE", "FALSE", "NOT", "AND", "OR", "COMMENT", "WS", 
			"ID"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "TRUE", "FALSE", "NOT", "AND", "OR", "COMMENT", "WS", 
			"ID"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\fh\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5"+
		"\4(\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5"+
		"\59\n\5\3\6\3\6\3\6\3\6\5\6?\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7H\n\7"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\5\bP\n\b\3\t\3\t\3\t\3\t\7\tV\n\t\f\t\16\tY\13"+
		"\t\3\t\3\t\3\n\6\n^\n\n\r\n\16\n_\3\n\3\n\3\13\6\13e\n\13\r\13\16\13f"+
		"\2\2\f\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\3\2\5\4\2\f\f\17"+
		"\17\4\2\13\f\"\"\6\2\62;C\\aac|\2s\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"+
		"\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3"+
		"\2\2\2\2\25\3\2\2\2\3\27\3\2\2\2\5\31\3\2\2\2\7\'\3\2\2\2\t8\3\2\2\2\13"+
		">\3\2\2\2\rG\3\2\2\2\17O\3\2\2\2\21Q\3\2\2\2\23]\3\2\2\2\25d\3\2\2\2\27"+
		"\30\7*\2\2\30\4\3\2\2\2\31\32\7+\2\2\32\6\3\2\2\2\33\34\7v\2\2\34\35\7"+
		"t\2\2\35\36\7w\2\2\36(\7g\2\2\37 \7V\2\2 !\7T\2\2!\"\7W\2\2\"(\7G\2\2"+
		"#$\7V\2\2$%\7t\2\2%&\7w\2\2&(\7g\2\2\'\33\3\2\2\2\'\37\3\2\2\2\'#\3\2"+
		"\2\2(\b\3\2\2\2)*\7h\2\2*+\7c\2\2+,\7n\2\2,-\7u\2\2-9\7g\2\2./\7H\2\2"+
		"/\60\7C\2\2\60\61\7N\2\2\61\62\7U\2\2\629\7G\2\2\63\64\7H\2\2\64\65\7"+
		"c\2\2\65\66\7n\2\2\66\67\7u\2\2\679\7g\2\28)\3\2\2\28.\3\2\2\28\63\3\2"+
		"\2\29\n\3\2\2\2:?\7#\2\2;<\7p\2\2<=\7q\2\2=?\7v\2\2>:\3\2\2\2>;\3\2\2"+
		"\2?\f\3\2\2\2@A\7(\2\2AH\7(\2\2BC\7c\2\2CD\7p\2\2DH\7f\2\2EF\7\61\2\2"+
		"FH\7^\2\2G@\3\2\2\2GB\3\2\2\2GE\3\2\2\2H\16\3\2\2\2IJ\7~\2\2JP\7~\2\2"+
		"KL\7q\2\2LP\7t\2\2MN\7^\2\2NP\7\61\2\2OI\3\2\2\2OK\3\2\2\2OM\3\2\2\2P"+
		"\20\3\2\2\2QR\7/\2\2RS\7/\2\2SW\3\2\2\2TV\n\2\2\2UT\3\2\2\2VY\3\2\2\2"+
		"WU\3\2\2\2WX\3\2\2\2XZ\3\2\2\2YW\3\2\2\2Z[\b\t\2\2[\22\3\2\2\2\\^\t\3"+
		"\2\2]\\\3\2\2\2^_\3\2\2\2_]\3\2\2\2_`\3\2\2\2`a\3\2\2\2ab\b\n\2\2b\24"+
		"\3\2\2\2ce\t\4\2\2dc\3\2\2\2ef\3\2\2\2fd\3\2\2\2fg\3\2\2\2g\26\3\2\2\2"+
		"\13\2\'8>GOW_f\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}