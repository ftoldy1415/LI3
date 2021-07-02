package model.businesses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BusinessesCat implements IBusinessesCat, Serializable {
    private Map<String,IBusiness> cat;

    public BusinessesCat(){
        this.cat = new HashMap<>();
    }

    /**
     * Método cujo propósito é adicionar um Business ao catálogo de Business cat
     * @param business_id identificação do Business
     * @param name nome do Business
     * @param city cidade do Business
     * @param state estado do Business
     * @param categories categorias do Business
     */
    public void addBusiness(String business_id, String name, String city, String state, List<String> categories) {
        IBusiness b = new Business(business_id, name,  city, state, new ArrayList<>(categories));
        this.cat.put(business_id, b);
    }

    /**
     *  Método de consulta da variável Cat
     * @return valor da variável de instância cat
     **/
    public Map<String, IBusiness> getCat(){
        return this.cat.values().stream().collect(Collectors.toMap(IBusiness::getBusiness_id, IBusiness::clone));
    }

    /**
     * Método para validar se um determinado Business_id corresponde a um Business no catálogo
     * @param business_id id do Business a verificar
     * @return true se existir
     */
    public boolean validateBizID(String business_id){
        return this.cat.containsKey(business_id);
    }

    /**
     * Método de consulta do valor do tamanho do catálogo
     * @return tamanho do catálogo
     */
    public int getSizeCat(){return this.cat.size();}

    /**
     * Método de consulta de um Business pertencente ao catálogo dado o seu ID
     * @param business_id
     * @return IBusiness
     */
    public IBusiness getBizById(String business_id){
        return this.cat.get(business_id);
    }

}

