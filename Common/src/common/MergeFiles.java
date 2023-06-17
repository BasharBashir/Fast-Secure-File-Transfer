package common;

// Java program to merge all files of a directory
import java.io.*;

 public class MergeFiles {
//should give file type
	
	public static String MergeFiles(String directoryPath,String name,String key,String type) throws IOException
	{
		// create instance of directory
		File dir = new File(directoryPath);
		int last=directoryPath.lastIndexOf("\\");
        String outfile=directoryPath.substring(0,last);
         
		// create object of PrintWriter for output file
        String path =outfile+"\\"+name+type;
        String Decode_Decrypted_File = null;
		PrintWriter pw = new PrintWriter(path);
        int i=1;
		// Get list of all the files in form of String Array
		String[] fileNames = dir.list();
		String file = null;
		// loop for reading the contents of all the files
		// in the directory GeeksForGeeks
		for (String fileName : fileNames) {
			System.out.println("Reading from " + fileName);
			try {
				String filepathtodelete=directoryPath+"\\"+fileName;
				file=AESDecryption.Decrypt(filepathtodelete,key,i);
				System.out.println("decrypteing file: " + file);
				 Decode_Decrypted_File=Decoder.Decode_String(file);

				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// create instance of file from Name of
			// the file stored in string Array
			File f = new File( Decode_Decrypted_File);
            i++;
			 
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			File FileTDelete =new File(file);
			FileTDelete.delete();
			
			String line = br.readLine();
			
			while (line != null) {

			 
				pw.print(""+ line);
				line = br.readLine();
			}
			pw.flush();
			br.close();
			f.delete();
			
		}
		
		System.out.println("Reading from all files" +
		" in directory " + dir.getName() + " Completed");
		 i=1;
		return path;
	}
	 public static void main(String[] args) throws IOException 
	{
		 //MergeFiles("C:\\Client_Id_5\\test1_Chunks","fff","f31cbc0dccfeeb2a") ;
	}
}
