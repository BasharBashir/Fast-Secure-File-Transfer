package common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryption {
	private static String Encryptedfile;
    public static String Encrypt(String FileName,String key,int i) throws Exception {
        // Step 1: Generate a secret key for encryption
    	Key secretKey = new SecretKeySpec(key.getBytes(), "AES");

        // Step 2: Create a Cipher object and initialize it with the encryption mode, the generated secret key, and the AES algorithm
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        int lastDotIndex = FileName.lastIndexOf('.');
		String fileType = FileName.substring(lastDotIndex + 1).toLowerCase();
        // Step 3: Create an input stream to read the plain text file and an output stream to write the encrypted data to the file
        InputStream in = new FileInputStream(FileName);
        Encryptedfile = FileName.substring(0,FileName.indexOf(".")) + "_Encrypted_"+i+"."+fileType;
        OutputStream out = new FileOutputStream(Encryptedfile);

        // Step 4: Read the plain text file in chunks and encrypt each chunk using the Cipher object's update() method and write the encrypted data to the output stream
        byte[] input = new byte[64];
        int bytesRead;
        while ((bytesRead = in.read(input)) != -1) {
            byte[] output = cipher.update(input, 0, bytesRead);
            if (output != null) {
                out.write(output);
            }
        }

        // Step 5: Use the Cipher object's doFinal() method to encrypt any remaining data in the input stream and write it to the output stream
        byte[] output = cipher.doFinal();
        if (output != null) {
            out.write(output);
        }

        // Step 6: Close the input and output streams
        in.close();
        out.flush();
        out.close();
        return Encryptedfile;
    }
    public static void main(String[] args) throws Exception {
    	//Encrypt("C:\\Users\\moham\\OneDrive\\שולחן העבודה\\test\\test1.txt","f31cbc0dccfeeb2a");
	}
}
