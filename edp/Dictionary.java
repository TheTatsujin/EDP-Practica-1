package edp;

import java.lang.reflect.Array;

public class Dictionary{
    private Object[] keys;
    private Object[] values;

    private final static int MIN_SIZE = 10;
    private int size;

    public Dictionary(){
        keys = new Object[MIN_SIZE];
        values = new Object[MIN_SIZE];
        size = 0;
    }

    public void put(Object key, Object value){
        int index = this.keyHash(key) & this.size;
        while (keys[index] != null){
            // Collision -> Open Addressing

        }
        keys[index] = key;
        values[index] = value;
        size++;



    }

    public Object get(Object key){
        int pseudoKey = this.keyHash(key) & this.size;
        return this.values[pseudoKey];
    }


    private int keyHash(Object key){
        if (key instanceof Integer intKey){
            return integerHash(intKey);
        }
        if (key instanceof String stringKey){
            return stringHash(stringKey);
        }
        return 0;
    }
    private static int stringHash(String key){
        return 0;
    }

    private static int integerHash(int key){
        return 0;
    }
}
