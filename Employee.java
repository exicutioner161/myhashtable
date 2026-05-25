import java.util.Random;

public class Employee implements Comparable<Employee> {
    private final long id;
    private final String name;
    private static final Random random = new Random();
    private static final char[] vowels = { 'a', 'e', 'i', 'o', 'u' };
    private static final char[] consonants = { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r',
            's',
            't', 'v', 'w', 'x', 'y', 'z' };

    public Employee(long id) {
        this.id = id;
        this.name = generateRandomName();
    }

    public Employee(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Employee(String name) {
        this.id = 1;
        this.name = name;
    }

    private static String generateRandomName() {
        StringBuilder sb = new StringBuilder();
        int rand = random.nextInt(3, 9);
        for (int i = 0; i < rand; i++) {
            if (i % 2 == 0) {
                sb.append(consonants[random.nextInt(0, consonants.length)]);
            } else {
                sb.append(vowels[random.nextInt(0, vowels.length)]);
            }
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    @Override
    public int compareTo(Employee e) {
        return this.name.compareTo(e.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Employee employee && this.name.equals(employee.getName());
    }

    @Override
    public String toString() {
        return id + " " + name;
    }

    @Override
    public int hashCode() {
        int code = 0;
        for (int i = 0; i < name.length(); i++) {
            code += name.charAt(i) * (i + 1);
        }
        return code;
    }
}
