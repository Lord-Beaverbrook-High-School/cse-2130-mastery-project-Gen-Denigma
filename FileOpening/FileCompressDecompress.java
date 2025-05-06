// Testing and debugging

/*package com.DataFlair.FileOpening;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReadFile {
    public static void main(String[] args) {
        String filePath1 = "C:\\Users\\1100124890\\Downloads\\data_raw.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath1))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        String filePath2 = "C:\\Users\\1100124890\\Downloads\\data_compressed.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath2))){
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println();
                System.out.println(line);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 */




/*package com.DataFlair.FileOpening;

import java.io.*;

public class ReadFile {
    public static void main(String[] args) {
        String filePath1 = "C:\\Users\\1100124890\\Downloads\\data_raw.txt";
        String filePath2 = "C:\\Users\\1100124890\\Downloads\\data_compressed.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath1));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath2))) {

            String line;
            StringBuilder compressedContent = new StringBuilder();

            // Read and compress the data
            while ((line = reader.readLine()) != null) {
                // Example compression: remove extra spaces and append
                compressedContent.append(line.trim().replaceAll("\\s+", " ")).append("\n");
            }

            // Write compressed content to filePath2
            writer.write(compressedContent.toString());
            System.out.println("Data from " + filePath1 + " has been compressed and written to " + filePath2);
            System.out.println("Compressed data: " + compressedContent.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 */
package com.DataFlair.FileOpening;

import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class FileCompressDecompress {
    public static void main(String[] args) {
        String filePath1 = "C:\\Users\\1100124890\\Downloads\\data_raw.txt"; // Raw data file
        String filePath2 = "C:\\Users\\1100124890\\Downloads\\data_compressed.txt"; // Compressed data file
        String filePathDecompressed = "C:\\Users\\1100124890\\Downloads\\data_decompressed.txt"; // Decompressed data file

        // Step 1: Read and Compress Data
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath1));
             FileOutputStream compressedFileOut = new FileOutputStream(filePath2)) {

            StringBuilder rawData = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                // Build raw data string (you can process this further if needed).
                rawData.append(line).append("\n");
            }

            // Compress the raw data
            byte[] compressedData = compressData(rawData.toString());
            compressedFileOut.write(compressedData);

            System.out.println("Raw data compressed and written to " + filePath2);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Step 2: Read and Decompress Data
        try (FileInputStream compressedFileIn = new FileInputStream(filePath2);
             FileOutputStream decompressedFileOut = new FileOutputStream(filePathDecompressed)) {

            // Read compressed data from file
            byte[] compressedData = compressedFileIn.readAllBytes();

            // Decompress the data
            String decompressedData = decompressData(compressedData);
            decompressedFileOut.write(decompressedData.getBytes());

            System.out.println("Compressed data decompressed and written to " + filePathDecompressed);
            System.out.println("Decompressed Data:\n" + decompressedData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to compress a string
    public static byte[] compressData(String data) {
        try {
            byte[] input = data.getBytes("UTF-8");

            // Create the compressor
            Deflater deflater = new Deflater();
            deflater.setInput(input);
            deflater.finish();

            // Compress the data
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(input.length);
            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Helper method to decompress data
    public static String decompressData(byte[] data) {
        try {
            // Create the decompressor
            Inflater inflater = new Inflater();
            inflater.setInput(data);

            // Decompress the data
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
            return outputStream.toString("UTF-8");
        } catch (IOException | java.util.zip.DataFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}



