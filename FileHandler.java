package utils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandler {
    private static final Logger logger = Logger.getLogger(FileHandler.class.getName());

    // 🔹 Write content to a file (overwrites existing content)
    public static boolean writeToFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            logger.info("Successfully wrote to file: " + filePath);
            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing to file: " + filePath, e);
            return false;
        }
    }

    // 🔹 Append content to a file (preserves existing content)
    public static boolean appendToFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
            writer.newLine();
            logger.info("Successfully appended to file: " + filePath);
            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error appending to file: " + filePath, e);
            return false;
        }
    }

    // 🔹 Read content from a file
    public static String readFromFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            logger.info("Successfully read from file: " + filePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading from file: " + filePath, e);
            return null;
        }
        return content.toString();
    }

    // 🔹 Delete a file
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                logger.info("Successfully deleted file: " + filePath);
                return true;
            } else {
                logger.warning("Failed to delete file: " + filePath);
                return false;
            }
        } else {
            logger.warning("File not found: " + filePath);
            return false;
        }
    }
}