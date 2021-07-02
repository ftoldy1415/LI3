package model.businesses;

import java.util.List;

public interface IBusiness {

    public String getBusiness_id();
    public String getName();
    public String getCity();
    public String getState();
    public List<String> getCategories();
    public void setBusiness_id(String business_id);
    public void setName(String name);
    public void setCity(String city);
    public void setState(String state);
    public void setCategories(List<String> categories);
    public Business clone();

}
