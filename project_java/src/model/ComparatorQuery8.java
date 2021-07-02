package model;


import java.util.Comparator;
import java.util.Map;

public class ComparatorQuery8 implements Comparator<Map.Entry<String,Integer>> {

    public int compare(Map.Entry<String,Integer> a, Map.Entry<String,Integer> b){
        return b.getValue()-a.getValue();
    }
}