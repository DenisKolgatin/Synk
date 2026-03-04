import java.util.*;

public class PhoneBook {

    private final Map<String, String> nameToNumber = new HashMap<>();
    private final Map<String, String> numberToName = new HashMap<>();
    private final Set<String> names = new TreeSet<>();

    public int add(String name, String number) {
        nameToNumber.put(name, number);
        numberToName.put(number, name);
        names.add(name);
        return nameToNumber.size();
    }

    public String findByNumber(String number) {
        return numberToName.get(number);
    }

    public String findByName(String name) {
        return nameToNumber.get(name);
    }

    public List<String> printAllNames() {
        return new ArrayList<>(names);
    }
}