package model.businesses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Business implements IBusiness, Serializable {
    private String business_id;
    private String name;
    private String city;
    private String state;
    List<String> categories;

    public Business(String business_id, String name, String city, String state, List<String> categories){
        this.business_id = business_id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.categories = new ArrayList<>();
        this.categories.addAll(categories);
    }

    public Business(Business b){
        this.business_id = b.getBusiness_id();
        this.name = b.getName();
        this.city = b.getCity();
        this.state = b.getState();
        this.categories = b.getCategories();

    }

    /**
     * Método de consulta da variável de instância business_id
     * @return variável de instancia business_id
     */
    public String getBusiness_id() {
        return this.business_id;
    }

    /**
     * Método de consulta da variável de instância name
     * @return variável de instancia name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Método de consulta da variável de instância city
     * @return variável de instancia city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Método de consulta da variável de instância state
     * @return variável de instancia state
     */
    public String getState() {
        return this.state;
    }

    /**
     * Método de consulta da variável de instância categories
     * @return variável de instancia categories
     */
    public List<String> getCategories() {
        return new ArrayList<>(this.categories);
    }

    /**
     *  Método de alteração da variável de instância business_id
     * @param business_id novo valor da variável de instância business_id
     **/
    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    /**
     *  Método de alteração da variável de instância Name
     * @param name novo valor da variável de instância Name
     **/
    public void setName(String name) {
        this.name = name;
    }

    /**
     *  Método de alteração da variável de instância City
     * @param city novo valor da variável de instância City
     **/
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *  Método de alteração da variável de instância State
     * @param state novo valor da variável de instância State
     **/
    public void setState(String state) {
        this.state = state;
    }

    /**
     *  Método de alteração da variável de instância Categories
     * @param categories novo valor da variável de instância Categories
     **/
    public void setCategories(List<String> categories) {
        this.categories = new ArrayList<>(categories);
    }

    /**
     *  Método copia de um Business
     * @return Copia do Business
     **/
    public Business clone(){
        return new Business(this);
    }


    /**
     *  Método de comparação de um Business
     * @param o Objeto a comparar com o Business
     * @return resultado da igualdade
     **/
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Business b = (Business) o;
        return (this.business_id.equals(b.getBusiness_id()) && this.name.equals(b.getName())
                                                            && this.city.equals(b.city)
                                                            && this.state.equals(b.getState())
                                                            && this.categories.equals(b.getCategories()));
    }

    /**
     * Método de representação da classe Business sob a forma de uma String
     * @return String com a representação da classe Business
     **/
    public String toString(){
        return this.business_id + ";"
                + this.name  + ";"
                + this.city + ";"
                + this.state + ";"
                + this.categories.toString();
    }
}
