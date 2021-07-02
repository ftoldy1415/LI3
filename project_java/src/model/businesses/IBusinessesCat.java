package model.businesses;

import java.util.List;
import java.util.Map;

public interface IBusinessesCat {
    public void addBusiness(String business_id, String name, String city, String state, List<String> categories);//completar
    public Map<String, IBusiness> getCat();
    public boolean validateBizID(String business_id);
    public int getSizeCat();
    public IBusiness getBizById(String business_id);
}
