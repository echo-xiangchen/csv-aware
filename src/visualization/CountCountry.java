package visualization;

import java.io.*;
import java.util.*;

public class CountCountry {
	public static void main(String[] args) {
//		if (args.length != 1) {
//			System.out.println("Usage: "
//					+ "Input: two files that contains countries and gender in order.\n"
//					+ "Output: a list of countries with their occurrance(only female).\n"
//					+ "Warning: You do not include any file.");
//		} else {
			Map<String, Integer> femaleCountMap = new HashMap<>();
	        Map<String, Integer> maleCountMap = new HashMap<>();
			BufferedReader gender_reader;
			BufferedReader country_reader;
			
			try {
				// read the file
				gender_reader = new BufferedReader(new FileReader("gender.txt"));
				country_reader = new BufferedReader(new FileReader("country.txt"));
				
				String gender_line, country_line;
				while ((gender_line = gender_reader.readLine()) != null && (country_line = country_reader.readLine()) != null) {
	            	// Split each line by comma and count the occurrences of each country
					// Split each line by commas
	                String[] genders = gender_line.split(",");
	                String[] countries = country_line.split(",");
	                
                    
	                // Loop through the genders and count occurrences
	                for (int i = 0; i < genders.length && i < countries.length; i++) {
	                    String country = countries[i].trim();
	                    String gender = genders[i].trim();

	                    // Count female occurrences
	                    if ("F".equals(gender)) {
	                        femaleCountMap.put(country, femaleCountMap.getOrDefault(country, 0) + 1);
	                    }

	                    // Count male occurrences
	                    if ("male".equals(gender)) {
	                        maleCountMap.put(country, maleCountMap.getOrDefault(country, 0) + 1);
	                    }
	                }
	            }
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Prepare a list to store the data along with female count
	        List<CountryGenderStats> statsList = new ArrayList<>();

	        // Process the maps to create a list of statistics objects
	        for (String country : femaleCountMap.keySet()) {
	            int femaleCount = femaleCountMap.getOrDefault(country, 0);
	            int maleCount = maleCountMap.getOrDefault(country, 0);
	            double total = femaleCount + maleCount;

	            double percentage = (total > 0) ? (double) femaleCount / total * 100 : 0;
	            statsList.add(new CountryGenderStats(country, femaleCount, maleCount, percentage));
	        }

	        // Sort the list by female count in descending order
	        statsList.sort(Comparator.comparingInt(CountryGenderStats::getFemaleCount).reversed());

	        // Print the sorted results
	        System.out.println("Country, Female Count, Male Count, Female Percentage");
	        for (CountryGenderStats stats : statsList) {
	            System.out.printf("%s, %d, %d, %.2f%%\n", stats.getCountry(), stats.getFemaleCount(), stats.getMaleCount(), stats.getFemalePercentage());
	        }
	    }

	    // Helper class to store the statistics for each country
	    static class CountryGenderStats {
	        private final String country;
	        private final int femaleCount;
	        private final int maleCount;
	        private final double femalePercentage;

	        public CountryGenderStats(String country, int femaleCount, int maleCount, double femalePercentage) {
	            this.country = country;
	            this.femaleCount = femaleCount;
	            this.maleCount = maleCount;
	            this.femalePercentage = femalePercentage;
	        }

	        public String getCountry() {
	            return country;
	        }

	        public int getFemaleCount() {
	            return femaleCount;
	        }

	        public int getMaleCount() {
	            return maleCount;
	        }

	        public double getFemalePercentage() {
	            return femalePercentage;
	        }
	}
//	}
}
