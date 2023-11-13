package visualization;

import java.io.*;
import java.util.*;

public class GenCountryList {
	public static void main(String[] args) {
//		if (args.length != 2) {
//			System.out.println("Usage: "
//					+ "Input: 1. a file that contains lines of author full name+country, 2. a file contains list of author names .\n"
//					+ "Output: a list of countries, with the same order of the second file's authors name.\n"
//					+ "Warning: You do not include any file.");
//		} else {
			Map<String, String> nameCountryMap = new HashMap<>();
			Map<String, String> nameGenderMap = new HashMap<>();
			
			try {
				// Get the name-country map first
				BufferedReader author_country_reader = new BufferedReader(new FileReader("author-country.csv"));
				BufferedReader author_gender_reader = new BufferedReader(new FileReader("author-gender.csv"));
				BufferedWriter country_writer = new BufferedWriter(new FileWriter("countryList.txt"));
				BufferedWriter gender_writer = new BufferedWriter(new FileWriter("genderList.txt"));
				
				String line;
				
				// save the name-country pair into the map
				while ((line = author_country_reader.readLine()) != null) {
					// Split the line into keywords using a comma as the delimiter
	                String[] name_country_pair = line.split(",");
	                
	                if (name_country_pair.length > 1) {
	                	nameCountryMap.put(name_country_pair[0].trim(), name_country_pair[1].trim());
					} else {
						//nameCountryMap.put(name_country_pair[0].trim(), "unknown");
						System.out.println(line);
					}
				}
				
				// save the author-gender map
				while ((line = author_gender_reader.readLine()) != null) {
					// Split the line into keywords using a comma as the delimiter
	                String[] name_gender_pair = line.split(",");
	                
	                if (name_gender_pair.length > 1) {
	                	nameGenderMap.put(name_gender_pair[0].trim(), name_gender_pair[1].trim());
					} else {
						//nameCountryMap.put(name_country_pair[0].trim(), "unknown");
						System.out.println(line);
					}
				}
				
				// Get the author name list
				BufferedReader author_reader = new BufferedReader(new FileReader("author.txt"));
				
				// generate the country list and gender list
				// save to file
				while ((line = author_reader.readLine()) != null) {
					String[] authors = line.split(",");
					
					// create a string to store country list
					String country = "";
					String gender = "";
					for (String name : authors) {
						name = name.trim();
						if (nameCountryMap.containsKey(name)) {
							country = country + "," + nameCountryMap.get(name);
						}
						
						if (nameGenderMap.containsKey(name)) {
							gender = gender + "," + nameGenderMap.get(name);
						}
					}
					// remove the first ","
					if (country.length() > 0) {
						country = country.substring(1);
					} else {
						System.out.println(country);
					}
					
					if (gender.length() > 0) {
						gender = gender.substring(1);
					} else {
						System.out.println(gender);
					}
					
					
					
					country_writer.write(country + "\n");
					gender_writer.write(gender + "\n");
					
				}
				country_writer.close();
				System.out.println("writing countryList.txt finished.");
				gender_writer.close();
				System.out.println("writing genderList.txt finished.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//	}
}
