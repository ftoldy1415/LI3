package model;

import model.businesses.IBusiness;

import java.util.Comparator;
import java.util.Map;

public class ComparatorQuery5 implements Comparator<Map.Entry<IBusiness,Integer>> {

    public int compare(Map.Entry<IBusiness,Integer> a, Map.Entry<IBusiness,Integer> b){
        if(a.getValue() == b.getValue()){
            return a.getKey().getName().compareTo(b.getKey().getName());
        }
        else return b.getValue()-a.getValue();
    }
}
