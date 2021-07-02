package model;

import model.users.IUser;

import java.util.Comparator;
import java.util.Map;

public class ComparatorQuery9 implements Comparator<Map.Entry<IUser, Map.Entry<Float, Integer>>> {
    public int compare(Map.Entry<IUser, Map.Entry<Float, Integer>> e1, Map.Entry<IUser, Map.Entry<Float, Integer>> e2){
        if (e1.getValue().getValue() == (e2.getValue().getValue())){
            return e1.getKey().getName().compareTo(e2.getKey().getName());
        }
       return e2.getValue().getValue() - e1.getValue().getValue();
    }
}
