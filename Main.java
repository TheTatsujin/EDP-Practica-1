import edp.Dictionary;

public class Main {
    public static void main(String[] args) {
        Dictionary pyDict = new Dictionary();
        Object key;
        key = "aaa";
        pyDict.put(key, 1);
        pyDict.put(key + "!ac", 2);
        Object value = pyDict.get(key);
        System.out.println(value);

        value = pyDict.get(key + "!ac");
        System.out.println(value);
        System.out.println(pyDict);

    }
}