package common;
import java.io.*;
import java.util.*;

import java.io.*;
import java.util.*;
public class LZWCompressor {
    
    private static final int MAX_DICT_SIZE = 65536;
    
    public static String compressFile(String inputFileName) throws IOException {
        FileInputStream inputFile = new FileInputStream(inputFileName);
        
        int lastDotIndex = inputFileName.lastIndexOf('.');
		String fileType = inputFileName.substring(lastDotIndex + 1).toLowerCase();
        DataOutputStream outputFile = new DataOutputStream(new FileOutputStream(inputFileName.substring(0,inputFileName.indexOf(".")) + "_encoded."+fileType));
        
        Map<String, Integer> dictionary = new HashMap<String, Integer>();
        for (int i = 0; i < 256; i++) {
            dictionary.put("" + (char)i, i);
        }
        
        int dictionarySize = 256;
        String current = "";
        while (inputFile.available() > 0) {
            char nextChar = (char)inputFile.read();
            String combined = current + nextChar;
            if (dictionary.containsKey(combined)) {
                current = combined;
            } else {
                outputFile.writeInt(dictionary.get(current));
                if (dictionarySize < MAX_DICT_SIZE) {
                    dictionary.put(combined, dictionarySize++);
                }
                current = "" + nextChar;
            }
        }
        
        if (!current.equals("")) {
            outputFile.writeInt(dictionary.get(current));
        }
        
        inputFile.close();
        outputFile.close();
        
        
        
        String t= inputFileName.substring(0,inputFileName.indexOf(".")) + "_encoded."+fileType;
        return t;
        		 
    }
    
    
    public static void decompressFile(String inputFileName, String outputFileName) throws IOException {
        DataInputStream inputFile = new DataInputStream(new FileInputStream(inputFileName));
        PrintWriter outputFile = new PrintWriter(new FileWriter(outputFileName));

        
        List<String> dictionary = new ArrayList<String>();
        for (int i = 0; i < 256; i++) {
            dictionary.add("" + (char)i);
        }
        
        int dictionarySize = 256;
        int s=inputFile.readInt();
        String current = "" + (char)inputFile.readInt();
        System.out.println("the current: "+current+" the value : "+s);
        outputFile.write(current);
        String nextString;
        while (inputFile.available() > 0) {
            int nextCode = inputFile.readInt();
            System.out.println("nextCode: "+nextCode);
            
            if (nextCode >= dictionarySize) {
                nextString = current + current.charAt(0);
            } else {
                nextString = dictionary.get(nextCode);
            }
            outputFile.write(nextString);
            if (dictionarySize < MAX_DICT_SIZE) {
                dictionary.add(current + nextString.charAt(0));
                dictionarySize++;
            }
            current = nextString;
        }
        
        inputFile.close();
        outputFile.close();
    }
    public static void main(String[] args) throws IOException {
  // String file= LZWCompressor.compressFile("C:\\\\Users\\\\moham\\\\OneDrive\\\\שולחן העבודה\\\\test\\sssssss.txt");
  
	try {	
		//String ss=AESEncryption.Encrypt(file,"dcadb8687cce597c");

	//String qq=	AESDecryption.Decrypt("C:\\Client_Id_1\\mohamed_encoded_Encrypted.txt_1.txt","dcadb8687cce597c" );
		// LZWCompressor.decompressFile( qq, "C:\\\\Users\\\\moham\\\\OneDrive\\\\שולחן העבודה\\\\test\\tadddddddaa.txt");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  
   
    
    }
}


