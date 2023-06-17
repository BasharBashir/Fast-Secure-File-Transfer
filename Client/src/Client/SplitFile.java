package Client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import common.AESEncryption;
import common.Encoder;

public class SplitFile {
	public static int split(String filename,int id,int numparts) throws Exception, IOException
    {
		String filePath = filename;
        int numParts = numparts; // Number of parts to divide the file into
        byte[] buffer = new byte[(int) Math.ceil(new File(filePath).length() / (double) numParts)]; 
        
        try {
            File file = new File(filename);
            FileInputStream fileInputStream = new FileInputStream(file);
             String  FileName=file.getName();
            int dotIndex = FileName.lastIndexOf(".");
            String fileExtension = (dotIndex == -1) ? "" : FileName.substring(dotIndex + 1);
            int i = 1;
            int bytesRead = 0;
    		 
    		 int index = FileName.indexOf('.');
    	     String beforeDot = FileName.substring(0, index);
    		 String newpath="C:\\"+"Client_Id_"+id+"\\"+beforeDot+"_Chunks";
             File f = new File(newpath);
     		f.mkdir();
     		
     		 while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                 String partFilePath = newpath+"\\"+beforeDot +"."+fileExtension ;
                 FileOutputStream fileOutputStream = new FileOutputStream(partFilePath);
                 fileOutputStream.write(buffer, 0, bytesRead);
                 fileOutputStream.close();
                 String s= Encoder.Encode_string(partFilePath);
                 String s11=  AESEncryption.Encrypt(s, ChatClient.DH_Key,i);
                 File f1=new File(s);
                 i++;
                 f1.delete();
                 if (i > numParts) {
                     break;
                 }
                 
             }
     		
             fileInputStream.close();
             System.out.println("File divided into " + numParts + " parts.");
         } catch (IOException e) {
             System.out.println("An error occurred while dividing the file into parts.");
             e.printStackTrace();
         }
             
        
           
            
            return numParts;
            
    }

    static void readWrite(RandomAccessFile raf, BufferedOutputStream bw, long numBytes) throws IOException {
        byte[] buf = new byte[(int) numBytes];
        int val = raf.read(buf);
        if(val != -1) {
            bw.write(buf);
        }
    }
    public static void main(String[] args) throws IOException, Exception {
    	int num= split("C:\\Users\\moham\\OneDrive\\שולחן העבודה\\test\\test1_.txt",5,6);
	}
}

