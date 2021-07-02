package model.users;

import model.businesses.Business;

import java.util.List;

public interface IUser {

    public String getUser_id();
    public String getName();
    public User clone();
    public void setUser_id(String user_id);
    public void setName(String name);
}
