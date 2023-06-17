package Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDividerExample {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\moham\\test1.txt";
        int numParts = 3; // Number of parts to divide the file into
        byte[] buffer = new byte[(int) Math.ceil(new File(filePath).length() / (double) numParts)];

        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);

            int i = 0;
            int bytesRead = 0;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                String partFilePath = "C:\\Users\\moham\\parts\\"+file.getName() + i + ".txt";
                FileOutputStream fileOutputStream = new FileOutputStream(partFilePath);
                fileOutputStream.write(buffer, 0, bytesRead);
                fileOutputStream.close();
                i++;
                if (i >= numParts) {
                    break;
                }
            }

            fileInputStream.close();
            System.out.println("File divided into " + numParts + " parts.");
        } catch (IOException e) {
            System.out.println("An error occurred while dividing the file into parts.");
            e.printStackTrace();
        }
    }
}
