/**
 * Reads data from all batch files (files with "batch_" as first part of name) in the current directory.
 * Outputs data to new file "collated.txt" where each line starts with the name of a batch file and 
 * contains the space separated data from that batch file, so there will be as many lines as there
 * are "batch_..." files in the current directory.  The output is intended to be used by an Excel 
 * spreadsheet for graphing purposes.
 */
package batchfilecollator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Jon Mitzman
 *
 */
public class BatchFileCollator {

	// Same field as in MolComSim.  I was in a rush and having problems with imports.  TODO: avoid duplication. 
	private static final boolean APPEND_TO_FILE = true; // used to set the append field for FileWriter to write out to the
	// same file as other simulations during a batch run.
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File dir = new File(".");
		ArrayList<String> batchFileContent = new ArrayList<>();
		File[] files = dir.listFiles((File dir2, String name)-> name.startsWith("batch_"));
		FileWriter writer;
		try {
			writer = new FileWriter("collated.txt", APPEND_TO_FILE);
			for (File file : files) {
			    if (file.isFile()) {
					BufferedReader br = new BufferedReader(new FileReader(file.getName()));
					String line = null;
					while((line = br.readLine()) != null) {
						if(line.isEmpty()) {
							continue;
						}
						
						// strip of any end of line characters
						while(line.endsWith("\n") || line.endsWith("\r")) {
							line = line.substring(0, line.length() - 1);
						}
						batchFileContent.add(line);		
					}
					br.close();
					if(!batchFileContent.isEmpty()) {
						writer.append(file.getName() + " ");
						for(String data : batchFileContent) {
							writer.append(data + " ");
						}
						writer.append("\n");
						batchFileContent.clear();
					}
			        
			    }
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
