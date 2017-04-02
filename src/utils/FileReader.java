package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileReader {
	public ArrayList<String> readFile(String filePath) {
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1252"));         

	        String line;
	        while ((line = br.readLine()) != null) {
	        	lines.add(line);
	        }
	        br.close();

			
		}catch(Exception e) {
			
		}
		
		return lines;
	}
	
	public static void writeFile(ArrayList<String> lines) {
		File fout = new File("out.txt");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			 
			for(String line: lines) {
				bw.write(line);
				bw.newLine();
			}
		 
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		
	}
}
