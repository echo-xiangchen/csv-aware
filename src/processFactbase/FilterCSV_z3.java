package processFactbase;

import static info.scce.addlib.cudd.Cudd.Cudd_ReadLogicZero;
import static info.scce.addlib.cudd.Cudd.Cudd_bddAnd;

import com.microsoft.z3.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.ParseTree;

import expr.antlr.PCparserLexer;
import expr.antlr.PCparserParser;
import expr.Antlr2z3;
import expr.composite.Expr;
import expr.visitor.BDDbuilder;
import expr.visitor.Z3builder;

public class FilterCSV_z3 {

	// hashmap stores the <pc, bddAddress> Map
	private static Map<String, BoolExpr> pcMap = new LinkedHashMap<String, BoolExpr>();

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Usage: " + "Input: a .csv file that contains analysis result from Neo4j.\n"
					+ "Output: a .csv file that only contains valid paths (variability-aware analysis result)"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			BufferedWriter writer;

			try {
				// Get the CSVReader instance with specifying the delimiter to be used
				reader = new BufferedReader(new FileReader(args[0]));

				// initialize antlr2Expr and bddbuilder for parsing
				final Antlr2z3 antlr2z3 = new Antlr2z3();

				// skip the first line - it's the title of Neo4j's result
				String line = reader.readLine();

				// string that waiting to be written to the output file
				String output = "";

				int linenum = 2;
				while ((line = reader.readLine()) != null) {
					// indicator for adding to the final result
					boolean writeToFile = true;

					if (line.equals("\"\"")) {
						// dont use break here! it will quit the while loop!
						// use continue.
						// break;
						writeToFile = true;
					} else {
						// get and split string
						String[] splitPC = line.split("condition\"\":\"\"");

						// parse the first pc of current row if pcMap does not contain it
						String firstPC = splitPC[1].split("\"\"")[0];

						if (firstPC.isEmpty()) {
							firstPC = "True";
						}

						if (!pcMap.containsKey(firstPC)) {
							CharStream input = CharStreams.fromString(firstPC);
							PCparserLexer lexer = new PCparserLexer(input);
							CommonTokenStream tokens = new CommonTokenStream(lexer);
							PCparserParser parser = new PCparserParser(tokens);
							parser.setBuildParseTree(true); // tell ANTLR to build a parse tree
							ParseTree tree = parser.stat(); // parse

							// generate the z3 boolean expression for initial string
							BoolExpr expr = antlr2z3.visit(tree.getChild(0));

							// generate the BDD
							// expr.accept(z3Builder);

							// store the BDD into the map
							pcMap.put(firstPC, expr);
						}

						BoolExpr PCpath = pcMap.get(firstPC);

						// perform SAT check for current row (path)
						for (int i = 2; i < splitPC.length; i++) {
							// print splitPC[i].split(",")[0]
							// System.out.println(i + ": " + splitPC[i].split(",")[0]);

							String currentPC = splitPC[i].split("\"\"")[0];
							// parse the string if pcMap does not contain it
							if (!pcMap.containsKey(currentPC)) {
								CharStream input = CharStreams.fromString(firstPC);
								PCparserLexer lexer = new PCparserLexer(input);
								CommonTokenStream tokens = new CommonTokenStream(lexer);
								PCparserParser parser = new PCparserParser(tokens);
								parser.setBuildParseTree(true); // tell ANTLR to build a parse tree
								ParseTree tree = parser.stat(); // parse

								// generate the Expr hierarchy for initial string
								BoolExpr expr = antlr2z3.visit(tree.getChild(0));

								// generate the BDD
								// expr.accept(z3Builder);

								// store the BDD into the map
								pcMap.put(currentPC, expr);
							}

							PCpath = antlr2z3.z3ctx.mkAnd(PCpath, pcMap.get(currentPC));

							Solver s = antlr2z3.z3ctx.mkSolver();
							s.add(PCpath);
							if (s.check() == Status.UNSATISFIABLE) {
								writeToFile = false;
								break;
							}
						}
					}

					if (writeToFile) {
						output = output + line + "\n";
					}

					System.out.println("finished line " + linenum);
					linenum++;
				}

				// capture input file name, and use it in output file
				String[] splitInputFileName = args[0].toString().split("/");
				String filename = splitInputFileName[splitInputFileName.length - 1].split("\\.")[0];

				System.out.println("writing " + filename + ".awareZ3.csv");

				writer = new BufferedWriter(new FileWriter(filename + ".awareZ3.csv"));

				// write to file
				writer.write(output);
				writer.close();
				System.out.println("writing " + filename + ".awareZ3.csv finished.");

//			    for (Map.Entry<String, Pair<Expr, Long>> entry : pcMap.entrySet()) {
//	    			Pair<Expr, Long> exprBDD = entry.getValue();
//	    		    String name = entry.getKey();
//	    		    System.out.println("PC: " + name + "\n"
//	    		    		+ "Expr: " + exprBDD.a + "\n"
//	    		    		+ "BDD address: " + exprBDD.b);
//	    		 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
