package common;


/**
	-----Decoder Java File-------
@author Karthikeyan Thorali Krishnmaurthy Ragunath
@version 1.0
@student ID 800936747

**/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

public class Decoder {
	
	
	private static String File_Input = null;
	private static double MAX_TABLE_SIZE; //Max Table size is based on the bit length input.
	private static String LZWfilename;
	

	
	/* Decodes the the compressed file to a decoded input file. 
	 * @param file_Input2 //The name of compressed file.
	 * @param bit_Length  //Provided as user input.
	 * @throws IOException */


	public static String Decode_String(String file_Input2) throws IOException {
		
		File_Input=file_Input2;
		MAX_TABLE_SIZE = Math.pow(2, 16);
		
		System.out.println("im here "+file_Input2);
		List<Integer> get_compress_values = new ArrayList<Integer>();
		int table_Size = 255;
		
		
		BufferedReader br = null;
		InputStream inputStream  = new FileInputStream(file_Input2);
		Reader inputStreamReader = new InputStreamReader(inputStream, "UTF-16BE"); // The Charset UTF-16BE is used to read the 16-bit compressed file.
	
		br = new BufferedReader(inputStreamReader);
		  
		double value=0;
		
         // reads to the end of the stream 
         while((value = br.read()) != -1)
         {
        	 get_compress_values.add((int) value);
         }
         	
         br.close();
         			
		Map<Integer, String> TABLE = new HashMap<Integer, String>();
		for (int i = 0; i < 255; i++)
			TABLE.put(i, "" + (char) i);

		String Encode_values = "" + (char) (int) get_compress_values.remove(0);
		
		StringBuffer decoded_values = new StringBuffer(Encode_values);
		
		String get_value_from_table = null;
		for (int check_key : get_compress_values) {
			
			if (TABLE.containsKey(check_key))
				get_value_from_table = TABLE.get(check_key);
			else if (check_key == table_Size)
				get_value_from_table = Encode_values + Encode_values.charAt(0);
			
			decoded_values.append(get_value_from_table);
			
			if(table_Size < MAX_TABLE_SIZE )
				TABLE.put(table_Size++, Encode_values + get_value_from_table.charAt(0));

			Encode_values = get_value_from_table;
		}
	
		int dotIndex = File_Input.lastIndexOf(".");
        String fileExtension = (dotIndex == -1) ? "" :  File_Input.substring(dotIndex + 1);
	return Create_decoded_file(decoded_values.toString(),fileExtension);
	
	
	
	}

/*
@param String , This hold the decoded text.
@throws IOException

*/

	private static String Create_decoded_file(String decoded_values,String type) throws IOException {
        
		
		LZWfilename = File_Input.substring(0,File_Input.indexOf(".")) + "_decoded."+type;
		
		 FileWriter writer = new FileWriter(LZWfilename, true);
		 BufferedWriter bufferedWriter = new BufferedWriter(writer);
		
	
		try {
			
			bufferedWriter.write(decoded_values);
		
			}
		 catch (IOException e) {
			e.printStackTrace(); 
		}
		bufferedWriter.flush();
		
		bufferedWriter.close();
		return LZWfilename;	
	}

	public static void main(String[] args) throws IOException {
		
		File_Input = "C:\\Users\\moham\\OneDrive\\שולחן העבודה\\test\\test1_.txt";
		int Bit_Length = 65335;
		try {
			
			Decode_String(File_Input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		
	}
}