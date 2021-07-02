package model.users;

import java.util.Map;

public interface IUsersCat {
    public void addUser(String user_id, String name);
    public Map<String, IUser> getUsersCat();
    public boolean validateUserID(String user_id);
    public int getSizeCat();
}
