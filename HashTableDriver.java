import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@SuppressWarnings("CallToPrintStackTrace")
public class HashTableDriver {
    private static final String DESMOS_EXPORT_FILE = "lookup-times.txt";
    private static final String EMPLOYEE_DATA_FILE = "data.txt";
    private static final int DESMOS_FILE_LIMIT = 10000;
    private static final Set<String> EXIT_COMMANDS = Set.of("-1", "q", "quit");
    private static final double NANOS_IN_MILLIS = 1000000.0;

    private static LookupResult<Employee> getTimedLookup(MyHashTable<Employee> hashTable, Employee item) {
        long startTime = System.nanoTime();
        Employee employee = hashTable.get(item);
        long elapsedNanos = System.nanoTime() - startTime;
        return new LookupResult<>(employee, elapsedNanos);
    }

    private static long timedLookupPrint(MyHashTable<Employee> hashTable, Employee temp) {
        LookupResult<Employee> result = getTimedLookup(hashTable, temp);
        String value = result.getValue() == null ? "null" : result.getValue().toString();
        long nanos = result.getElapsedNanos();
        System.out.printf("Value: %s%nLookup time: %d nanoseconds, %f milliseconds%n", value, nanos,
                nanos / NANOS_IN_MILLIS);
        return nanos;
    }

    private static void printResults(List<LookupResult<Employee>> lookupResults, int exportFilesSize) {
        LookupResult<Employee> shortestLookupEmployee = lookupResults.get(0);
        LookupResult<Employee> longestLookupEmployee = lookupResults.get(lookupResults.size() - 1);
        long shortestLookupTime = shortestLookupEmployee.getElapsedNanos();
        long longestLookupTime = longestLookupEmployee.getElapsedNanos();
        long avg = getAvg(lookupResults);
        System.out.printf(
                "Shortest lookup time: (%s) %d nanoseconds, %f milliseconds%nLongest lookup time: (%s) %d nanoseconds, %f milliseconds%nAverage lookup time: %d nanoseconds, %f milliseconds%nDesmos export written to %d files of up to %d entries each%n",
                shortestLookupEmployee.getValue().toString(), shortestLookupTime, shortestLookupTime / NANOS_IN_MILLIS,
                longestLookupEmployee.getValue().toString(), longestLookupTime,
                longestLookupTime / NANOS_IN_MILLIS, avg, avg / NANOS_IN_MILLIS, exportFilesSize, DESMOS_FILE_LIMIT);
    }

    private static long getAvg(List<LookupResult<Employee>> lookupResults) {
        long sum = 0;
        for (LookupResult<Employee> result : lookupResults) {
            sum += result.getElapsedNanos();
        }
        return sum / lookupResults.size();
    }

    private static void autoTest(Employee[] arr, MyHashTable<Employee> hashTable) {
        System.out.println("Running auto test:");
        List<LookupResult<Employee>> lookupResults = new ArrayList<>();
        for (Employee employee : arr) {
            lookupResults.add(getTimedLookup(hashTable, employee));
        }
        if (lookupResults.isEmpty()) {
            System.out.println("No lookup times recorded!");
            return;
        }
        List<String> exportFiles = Utils.writeLookupTimesForDesmos(DESMOS_EXPORT_FILE, lookupResults,
                DESMOS_FILE_LIMIT);
        Collections.sort(lookupResults);
        printResults(lookupResults, exportFiles.size());
    }

    private static void manualLookup(Scanner input, MyHashTable<Employee> hashTable) {
        System.out.println("Manual lookup:");
        while (true) {
            System.out.print("Enter a name to look up or -1/q/quit to exit: ");
            String in = input.nextLine().trim().toLowerCase();
            if (EXIT_COMMANDS.contains(in)) {
                System.out.println("Quitting!");
                return;
            }
            if (in.equals("null")) {
                System.out.println("Input cannot be null!");
                continue;
            }
            timedLookupPrint(hashTable, new Employee(in));
        }
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            MyHashTable<Employee> hashTable = new MyHashTable<>();
            Employee[] arr = Utils.fileToArray(EMPLOYEE_DATA_FILE);
            for (Employee e : arr) {
                hashTable.put(e);
            }
            System.out.println();
            autoTest(arr, hashTable);
            System.out.println();
            manualLookup(input, hashTable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
