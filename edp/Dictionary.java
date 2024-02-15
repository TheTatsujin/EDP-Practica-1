package edp;

public class Dictionary{
    private Object[] keys;
    private Object[] values;

    private final static int PERTURB_SHIFT = 5;
    private final static int MIN_SIZE = 8;
    private int used;

    private int capacity;

    public Dictionary(){
        keys = new Object[MIN_SIZE];
        values = new Object[MIN_SIZE];
        used = 0;
        capacity = MIN_SIZE;
    }

    public void put(Object key, Object value) throws IndexOutOfBoundsException{
        int index = this.generateIndex(key, false);
        if (index == -1){
            throw new IndexOutOfBoundsException();
        }
        keys[index] = key;
        values[index] = value;
        used++;
        if (used >= LOAD_FACTOR(used)){
            this.resize();
        }
    }

    public Object get(Object key){
        int index = this.generateIndex(key, true);
        if (index == -1){
            return null;
        }
        return this.values[index];
    }

    public boolean remove(Object key){
        int index = this.generateIndex(key, true);
        return index == 0;
    }

    public int size(){
        return this.used;
    }


    private void resize(){
        //
    }
    private static int LOAD_FACTOR(int used){
        return (used << 1) / 3;
    }

    private static int GROWTH_RATE(int capacity){
        return (capacity << 1);
    }

    private boolean contains(Object key){
        int perturb = key.hashCode();
        int index = perturb % this.capacity;
        if (this.keys[index].equals(key)){
            return true;
        }
        while (!this.keys[index].equals(key)){
            int j = index;
            j = 5*j + 1 + perturb;
            perturb >>= PERTURB_SHIFT;
            index = j % this.capacity;
            if (this.keys[index].equals(key)){
                return true;
            }
        }
        return false;

    }
    public int generateIndex(Object key, boolean isSearching){
        Object searchingKey = null;
        if (isSearching){
            searchingKey = key;
        }
        int perturb = key.hashCode();
        int index = perturb % this.capacity;

        if (!this.contains(key)){
            return -1;
        }

        while (this.keys[index] != searchingKey){
            System.out.println("Collision!");
            int j = index;
            j = 5*j + 1 + perturb;
            perturb >>= PERTURB_SHIFT;
            index = j % this.capacity;
        }
        return index;
    }


}
