package model.users;

import model.businesses.Business;
import model.reviews.Reviews;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements IUser, Serializable {
    private String user_id;
    private String name;


    public User(){
        this.user_id = "";
        this.name    = "";
    }


    public User(String user_id, String name){
        this.user_id = user_id;
        this.name    = name;
    }

    public User(User u){
        this.user_id = u.getUser_id();
        this.name    = u.getName();
    }

    /**
     * Método de consulta da variável de instância user_id
     * @return variável de instancia user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * Método de consulta da variável de instância Name
     * @return variável de instancia Name
     */
    public String getName() {
        return name;
    }

    /**
     *  Método de alteração da variável de instância user_id
     * @param user_id novo valor da variável de instância user_id
     **/
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     *  Método de alteração da variável de instância name
     * @param name novo valor da variável de instância name
     **/
    public void setName(String name) {
        this.name = name;
    }


    /**
     *  Método de comparação de um User
     * @param o Objeto a comparar com o User
     * @return resultado da igualdade
     **/
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        User u = (User) o;
        return (this.user_id.equals(u.getUser_id()) &&
                this.name.equals(u.getName()));
    }

    /**
     *  Método copia de um objeto User
     * @return Copia do objeto User
     **/
    public User clone(){
        return new User(this);
    }

    /**
     * Método de representação da classe User sob a forma de uma String
     * @return String com a representação da classe User
     **/
    public String toString(){
        return "[ " + this.user_id + ";"
                    + this.name    + " ]";
    }

}
