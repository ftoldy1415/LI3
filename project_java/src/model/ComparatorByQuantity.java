package model;

import java.util.Comparator;
import java.util.Map;

public class ComparatorByQuantity implements Comparator<Map.Entry<String, Integer>>{
    public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2){
        if (e1.getValue() == e2.getValue()){
            return e1.getKey().compareTo(e2.getKey());
        }
        else return e2.getValue() - e1.getValue();
    }
}
