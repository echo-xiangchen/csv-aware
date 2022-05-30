package expr.root;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CountFeatureVarValue {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: "
					+ "Input: a .csv file that contains feature variable type and value.\n"
					+ "Output: number of possible values.\n"
					+ "Warning: You do not include any .csv files.");
		} else {
			BufferedReader reader;
			
			List<Integer> valueNum = new ArrayList<>();
			
			try {
				reader = new BufferedReader(new FileReader(args[0]));
				
				String line;
				
				while ((line = reader.readLine()) != null) {
					String[] lineSplit = line.split("<>");
					String value = lineSplit[lineSplit.length - 1];
					String[] valueSplit = value.split(",");
					valueNum.add(valueSplit.length);
					//System.out.println(line);
				}
				BigInteger prod = BigInteger.ONE;
				int total = 0;
				int max = 1;
				for (int i = 0; i < valueNum.size(); i++) {
					prod = prod.multiply(BigInteger.valueOf(valueNum.get(i)));
					total = total + valueNum.get(i);
					if (valueNum.get(i) > max) {
						max = valueNum.get(i);
					}
					System.out.println(i + "prod: " + prod);
					
				}
				
				System.out.println("max: " + max);
				System.out.println("total: " + total);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
