package model.users;
import model.businesses.IBusiness;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserCat implements IUsersCat, Serializable {
    private Map<String, IUser> usersCat;

    public UserCat(){
        this.usersCat = new HashMap<>();
    }

    /**
     * Método para adicionar um objeto User ao catálogo de users
     * @param user_id id do utilizador
     * @param name nome do utilizador
     */
    public void addUser(String user_id, String name) {
        IUser u = new User(user_id, name);
        this.usersCat.put(user_id, u);
    }

    /**
     *  Método de consulta da variável Cat
     * @return valor da variável de instância cat
     **/
    public Map<String, IUser> getUsersCat(){
        return this.usersCat.values().stream().collect(Collectors.toMap(IUser::getUser_id, IUser::clone));
    }

    /**
     * Método para validar se um determinado User_id corresponde a um User no catálogo
     * @param user_id id do Business a verificar
     * @return true se existir
     */
    public boolean validateUserID(String user_id){
        return this.usersCat.containsKey(user_id);
    }

    /**
     * Método para calculo do tamanho do catálogo de utilizadores
     * @return tamanho do catálogo
     */
    public int getSizeCat(){
        return this.usersCat.size();
    }
}
