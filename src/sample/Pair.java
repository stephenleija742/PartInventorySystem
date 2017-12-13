package sample;

import java.util.Objects;

/**
 * Created by Stephen on 10/11/2017.
 */
public class Pair<J, K, V> {

    private J id;
    private K key;
    private V value;


    public Pair(J id, K key, V value){
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }

    public void setId(J id){this.id = id;}
    public void setKey(K key){this.key = key;}
    public void setValue(V value){this.value = value;}
    public K getKey(){return this.key;}
    public V getValue(){return this.value;}
    public J getId(){return this.id;}

    @Override
    public boolean equals(Object o){
        if(o == this){return true;}
        if(o == null){return false;}
        if(getClass() != o.getClass()) {return false;}
        if(!(o instanceof Pair)){return false;}

        Pair pair = (Pair) o;
        if(this.key == null || this.value == null){
            if(pair.key != null || pair.value != null){
                return false;
            }
        }
        String keyStr = (String) this.key;
        String valStr = (String) this.value;
        boolean comparekey = keyStr.equals((String)pair.getKey());
        boolean compareValue = valStr.equals((String) pair.getValue());
        return comparekey
               && compareValue;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.key, this.value);
    }
}
