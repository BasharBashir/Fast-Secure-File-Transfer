package common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class AESDecryption {
    public static String Decrypt(String FileName,String key,int i) throws Exception {
        // Step 1: Generate or retrieve the secret key for decryption
        // Note that the same key that was used for encryption must be used for decryption
        Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
System.out.println("the fils that coming to dec"+FileName);
        // Step 2: Create a Cipher object and initialize it with the decryption mode, the secret key, and the AES algorithm
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        int lastDotIndex = FileName.lastIndexOf('.');
		String fileType = FileName.substring(lastDotIndex + 1).toLowerCase();
        String  Decrypted = FileName.substring(0,FileName.indexOf(".")) + "_Decrypted_"+i+"."+fileType;

        // Step 3: Create an input stream to read the encrypted file and an output stream to write the decrypted data to the file
        InputStream in = new FileInputStream(FileName);
        OutputStream out = new FileOutputStream(Decrypted);

        // Step 4: Read the encrypted file in chunks and decrypt each chunk using the Cipher object's update() method and write the decrypted data to the output stream
    
        byte[] input = new byte[128];
        int bytesRead;
        while ((bytesRead = in.read(input)) != -1) {
            byte[] output = cipher.update(input, 0, bytesRead);
            if (output != null) {
                out.write(output);
            }
        }

        // Step 5: Use the Cipher object's doFinal() method to decrypt any remaining data in the input stream and write it to the output stream
      
        in.close();
        out.flush();
        out.close();
        return Decrypted;
    }public static void main(String[] args) {
		
	}
}
