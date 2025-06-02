package utils;

import java.io.*;
import java.util.*;

public class FileCRUDHandler {
    private String filePath;
    
    public FileCRUDHandler(String filePath) {
        this.filePath = filePath;
        createFileIfNotExists();
    }
    
    private void createFileIfNotExists() {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        
        try {
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }
    
    // Read all lines from file
    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }
    
    // Write all lines to file
    public boolean writeAll(List<String> lines) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.println(line);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }
    
    // Append a line to file
    public boolean append(String line) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(line);
            return true;
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
            return false;
        }
    }
    
    // Find lines containing specific text
    public List<String> search(String searchTerm) {
        List<String> results = new ArrayList<>();
        List<String> allLines = readAll();
        
        for (String line : allLines) {
            if (line.toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(line);
            }
        }
        return results;
    }
    
    // Update a line based on first field (ID)
    public boolean update(String id, String newLine) {
        List<String> lines = readAll();
        boolean found = false;
        
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split("##");
            if (parts.length > 0 && parts[0].equals(id)) {
                lines.set(i, newLine);
                found = true;
                break;
            }
        }
        
        if (found) {
            return writeAll(lines);
        }
        return false;
    }
    
    // Delete a line based on first field (ID)
    public boolean delete(String id) {
        List<String> lines = readAll();
        boolean found = false;
        
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split("##");
            if (parts.length > 0 && parts[0].equals(id)) {
                lines.remove(i);
                found = true;
                break;
            }
        }
        
        if (found) {
            return writeAll(lines);
        }
        return false;
    }
    
    // Find a specific record by ID
    public String findById(String id) {
        List<String> lines = readAll();
        for (String line : lines) {
            String[] parts = line.split("##");
            if (parts.length > 0 && parts[0].equals(id)) {
                return line;
            }
        }
        return null;
    }
    
    // Generate next ID based on existing records
    public String generateNextId(String prefix) {
        List<String> lines = readAll();
        int maxId = 0;
        
        for (String line : lines) {
            String[] parts = line.split("##");
            if (parts.length > 0 && parts[0].startsWith(prefix)) {
                try {
                    String numStr = parts[0].substring(prefix.length());
                    int num = Integer.parseInt(numStr);
                    maxId = Math.max(maxId, num);
                } catch (NumberFormatException e) {
                    // Skip invalid IDs
                }
            }
        }
        
        return prefix + String.format("%03d", maxId + 1);
    }
}
