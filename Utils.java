import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("CallToPrintStackTrace")
public class Utils {
    private Utils() {
    }

    public static int getFileSize(String fileName) throws IOException {
        int size = 0;
        try (Scanner input = new Scanner(new FileReader(fileName))) {
            while (input.hasNextLine()) {
                size++;
                input.nextLine();
            }
        }
        return size;
    }

    public static MyHashTable<Employee> fileToHashTable(String fileName) throws IOException {
        int size = getFileSize(fileName);
        MyHashTable<Employee> hashTable = new MyHashTable<>();
        String[] parts;
        try (Scanner input = new Scanner(new FileReader(fileName))) {
            for (int i = 0; i < size && input.hasNextLine(); i++) {
                String line = input.nextLine();
                parts = line.split(" ");
                hashTable.put(new Employee(Long.parseLong(parts[0].trim()), parts[1].trim()));
            }
        }
        return hashTable;
    }

    public static Employee[] fileToArray(String fileName) throws IOException {
        int size = getFileSize(fileName);
        Employee[] arr = new Employee[size];
        String[] parts;
        try (Scanner input = new Scanner(new FileReader(fileName))) {
            for (int i = 0; i < size && input.hasNextLine(); i++) {
                String line = input.nextLine();
                parts = line.split(" ");
                arr[i] = new Employee(Long.parseLong(parts[0].trim()), parts[1].trim());
            }
        }
        return arr;
    }

    public static List<String> writeLookupTimesForDesmos(String fileName, List<LookupResult<Employee>> lookupResults) {
        return writeLookupTimesForDesmos(fileName, lookupResults, 10000);
    }

    public static List<String> writeLookupTimesForDesmos(String fileName, List<LookupResult<Employee>> lookupResults,
            int maxEntriesPerFile) {
        List<String> outputFiles = new ArrayList<>();
        if (lookupResults.isEmpty()) {
            return outputFiles;
        }
        int dotIndex = fileName.lastIndexOf('.');
        String baseName = dotIndex >= 0 ? fileName.substring(0, dotIndex) : fileName;
        String extension = dotIndex >= 0 ? fileName.substring(dotIndex) : ".txt";
        int partNumber = 1;
        for (int startIndex = 0; startIndex < lookupResults.size(); startIndex += maxEntriesPerFile) {
            int endIndex = Math.min(startIndex + maxEntriesPerFile, lookupResults.size());
            String outputFile = String.format("%s-part-%03d%s", baseName, partNumber, extension);
            try (PrintWriter output = new PrintWriter(outputFile)) {
                for (int i = startIndex; i < endIndex; i++) {
                    output.printf("%d\t%d%n", i + 1, lookupResults.get(i).getElapsedNanos());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputFiles.add(outputFile);
            partNumber++;
        }
        return outputFiles;
    }
}
