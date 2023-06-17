package common;

/**
       -----Encoder Java File-------
@author Karthikeyan Thorali Krishnmaurthy Ragunath
@version 1.0
@student ID 800936747

**/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Encoder {
	
	private static String File_Input = null;
	private static double MAX_TABLE_SIZE; //Max Table size is based on the bit length input.
	private static String LZWfilename;
	

	/** Compress a string to a list of output symbols and then pass it for compress file creation.
	 * @param Bit_Length //Provided as user input.
	 * @param input_string //Filename that is used for encoding.
	 * @throws IOException */
	
	public static String Encode_string(String input_string) throws IOException {
		File_Input=input_string;
           StringBuffer input_string1 = new StringBuffer();
		
		 try (BufferedReader br = Files.newBufferedReader(Paths.get(File_Input), StandardCharsets.UTF_8)) {
		    for (String line = null; (line = br.readLine()) != null;) {
		    	input_string1 = input_string1.append(line);
		    }
		}
	
		MAX_TABLE_SIZE = Math.pow(2,16);	
			
		double table_Size =  255;
		
		Map<String, Integer> TABLE = new HashMap<String, Integer>();
		
		for (int i = 0; i < 255 ; i++)
			TABLE.put("" + (char) i, i);

		String initString = "";
		
		List<Integer> encoded_values = new ArrayList<Integer>();
		
		for (char symbol : input_string1.toString().toCharArray()) {
			String Str_Symbol = initString + symbol;
			if (TABLE.containsKey(Str_Symbol))
				initString = Str_Symbol;
			else {
				encoded_values.add(TABLE.get(initString));
			
				if(table_Size < MAX_TABLE_SIZE)
					TABLE.put(Str_Symbol, (int) table_Size++);
				initString = "" + symbol;
			}
		}

		if (!initString.equals(""))
			encoded_values.add(TABLE.get(initString));
		
		
		int dotIndex = File_Input.lastIndexOf(".");
        String fileExtension = (dotIndex == -1) ? "" :  File_Input.substring(dotIndex + 1);
		return  CreateLZWfile(encoded_values,fileExtension); 
		
	}


/*
@param encoded_values , This hold the encoded text.
@throws IOException
*/

	private static String CreateLZWfile(List<Integer> encoded_values,String type) throws IOException {
		
		BufferedWriter out = null;
		
		LZWfilename = File_Input.substring(0,File_Input.indexOf(".")) +"_encoded"+ "."+type;
		
		try {
	            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LZWfilename),"UTF_16BE")); //The Charset UTF-16BE is used to write as 16-bit compressed file
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Iterator<Integer> Itr = encoded_values.iterator();
			while (Itr.hasNext()) {
				out.write(Itr.next());
			}
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
		out.flush();
		out.close();	
		return LZWfilename;
	}


	public static void main(String[] args) throws IOException {
				
		File_Input = "C:\\Users\\moham\\OneDrive\\שולחן העבודה\\test\\test1_Encrypted.txt";
		int Bit_Length =65335;
		
		
		Encode_string(File_Input);
		System.out.print(Encode_string(File_Input));
			
	}
}