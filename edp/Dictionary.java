package edp;

import java.security.KeyException;

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

    public void put(Object key, Object value){
        if (this.contains(key)){
            // Ya existe esa Clave
            return;
        }
        int index = this.generateIndex(key);
        if (index == -1){
            // FULL!
            return;
        }

        keys[index] = key;
        values[index] = value;
        used++;
        if (used >= LOAD_FACTOR(capacity)){
            //System.out.println("RESIZE!!");
            this.resize();
        }
    }

    public Object get(Object key){
        if (this.isEmpty()){
            // TODO: Exception isEmpty
            return null;
        }
        int perturb = key.hashCode();
        int index = perturb & (this.capacity - 1);
        if (this.keys[index] != null && this.keys[index].equals(key)){
            return this.values[index];
        }
        int iteration = 0;
        while (this.keys[index] != null && !this.keys[index].equals(key) && iteration < capacity){
            int j = index;
            j = 5*j + 1 + perturb;
            perturb >>= PERTURB_SHIFT;
            index = j & (this.capacity - 1);
            if (this.keys[index] != null && this.keys[index].equals(key)){
                return this.values[index];
            }
            iteration++;
        }

        // No existe
        return null;
    }

    public boolean remove(Object key){
        int index = this.generateIndex(key);
        return index == 0;
    }

    public int size(){
        return this.used;
    }


    private void resize(){

        int newSize;
        int oldSize = this.capacity;
        Object[] oldKeys = this.keys;
        Object[] oldValues = this.values;

        // Encontrar nueva capacidad
        for (newSize = MIN_SIZE;
             newSize <= used && newSize > 0;
             newSize = GROWTH_RATE(newSize));
        if (newSize <= 0){
            // No more memory error
            return;
        }
        this.capacity = GROWTH_RATE(this.capacity);
        //System.out.println("New SIZE: " + this.capacity);

        // Refill table
        this.keys = new Object[this.capacity];
        this.values = new Object[this.capacity];

        for (int j=0; j < oldSize; j++){
            if (oldKeys[j] != null){
                int newIndex = this.generateIndex(oldKeys[j]);
                this.keys[newIndex] = oldKeys[j];
                this.values[newIndex] = oldValues[j];
            }
        }
    }
    private static int LOAD_FACTOR(int capacity){
        return ((capacity << 1) / 3) + 1;
    }

    private static int GROWTH_RATE(int capacity){
        return (capacity << 1);
    }

    private boolean contains(Object key){
        int perturb = key.hashCode();
        int index = perturb & (this.capacity - 1);

        if (this.isEmpty()){
            return false;
        }

        if (this.keys[index] != null && this.keys[index].equals(key)){
            return true;
        }
        int iteration = 0;
        while (this.keys[index] != null && !this.keys[index].equals(key) && iteration < capacity){
            int j = index;
            j = 5*j + 1 + perturb;
            perturb >>= PERTURB_SHIFT;
            index = j & (this.capacity - 1);
            if (this.keys[index] != null && this.keys[index].equals(key)){
                return true;
            }
            iteration++;
        }
        return false;

    }
    private int generateIndex(Object key){
        /*
        *   Algoritmo que usa Python para manejo de colision
        *   Funciona mejor para capacity = 2**i;
        *
        * */
        int perturb = key.hashCode();
        int index = perturb & (this.capacity - 1);

        int iterations = 0;
        while (this.keys[index] != null){
            if (iterations >= capacity){
                return -1;
            }
            //System.out.println("Collision!");
            int j = index;
            j = 5*j + 1 + perturb;
            perturb >>= PERTURB_SHIFT;
            index = j & (this.capacity - 1);
            iterations++;
        }
        return index;
    }

    public boolean isEmpty(){
        return this.used == 0;
    }

    @Override
    public String toString(){
        String result = "{";
        int i = 0;
        int j = 0;
        while (used > 0 && i < used && j < capacity){

            if (keys[j] != null){
                if (i > 0){
                    result += ", ";
                }
                result += keys[j] + " : " + values[j];
                i++;
            }


            j++;
        }
        return result + "}";
    }


}