package model.stats;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Stats implements IStats, Serializable {

    private int invalidReviews;
    private int bizTotal;
    private int bizReviewed;
    private int bizNotReviewed;
    private int numberOfUsers;
    private int usersReviews;
    private int usersNoReviews;
    private int reviewNotImpactful;
    private double averageReview;
    private Map<String,Integer> reviewsMonthly;
    private Map<String,Float> averageMonthly;
    private Map<String,Integer> difUsersMonthly;

    public Stats(){
        this.invalidReviews = 0;
        this.bizTotal = 0;
        this.bizReviewed = 0;
        this.bizNotReviewed = 0;
        this.numberOfUsers = 0;
        this.usersReviews = 0;
        this.usersNoReviews = 0;
        this.reviewNotImpactful = 0;
        this.averageReview = 0;
        this.reviewsMonthly = new HashMap<>();
        this.averageMonthly = new  HashMap<>();
        this.difUsersMonthly = new HashMap<>();
    }


    /**
     * Método de consulta da variável de instância invalidReviews
     * @return variável de instancia invalidReviews
     */
    public int getInvalidReviews(){
        return this.invalidReviews;
    }


    /**
     * Método de consulta da variável de instância numberOfUsers
     * @return variável de instancia numberOfUsers
     */
    public int getNumberOfUsers(){
        return this.numberOfUsers;
    }


    /**
     * Método de consulta da variável de instância ReviewsNotImpactful
     * @return variável de instancia ReviewsNotImpactful
     */
    public int getReviewsNotImpactful(){
        return this.reviewNotImpactful;
    }

    /**
     * Método de consulta da variável de instância bizTotal
     * @return variável de instancia bizTotal
     */
    public int getbizTotal(){
        return this.bizTotal;
    }

    /**
     * Método de consulta da variável de instância bizReviewed
     * @return variável de instancia bizReviewed
     */
    public int getBizReviewed(){
        return this.bizReviewed;
    }

    /**
     * Método de consulta da variável de instância bizReviewed
     * @return variável de instancia bizNotReviewed
     */
    public int getBizNotReviewed(){
        return this.bizNotReviewed;
    }

    /**
     * Método de consulta da variável de instância usersReviews
     * @return variável de instancia usersReviews
     */
    public int getUsersReviews(){
        return this.usersReviews;
    }

    public int getUsersNoReviews(){
        return this.usersNoReviews;
    }

    public double getAverageReview(){
        return this.averageReview;
    }
    /**
     * Método de consulta da variável de instância reviewsMonthly
     * @return variável de instancia reviewsMonthly
     */
    public Map<String,Integer> getReviewsMonthly(){
        return new HashMap<>(this.reviewsMonthly);
    }

    public Map<String,Float> getAverageMonthly(){ return new HashMap<>(this.averageMonthly);}

    public Map<String, Integer> getDifUsersMonthly(){
        return new HashMap<>(this.difUsersMonthly);
    }

    /**
     *  Método de alteração da variável de instância invalidReviews
     * @param n novo valor da variável de instância invalidReviews
     **/
    public void setInvalidReviews(int n){
        this.invalidReviews = n;
    }

    /**
     *  Método de alteração da variável de instância bizTotal
     * @param bizTotal novo valor da variável de instância bizTotal
     **/
    public void setbizTotal(int bizTotal){
        this.bizTotal = bizTotal;
    }

    /**
     *  Método de alteração da variável de instância bizReviews
     * @param bizReviewed novo valor da variável de instância bizReviews
     **/
    public void setbizReviewed(int bizReviewed){
        this.bizReviewed = bizReviewed;
    }

    /**
     *  Método de alteração da variável de instância bizNotReviewed
     * @param bizNotReviewed novo valor da variável de instância bizNotReviewed
     **/
    public void setbizNotReviewed(int bizNotReviewed){
        this.bizNotReviewed = bizNotReviewed;
    }

    /**
     *  Método de alteração da variável de instância numberOfUsers
     * @param n_users novo valor da variável de instância numberOfUsers
     **/
    public void setNumberOfUsers (int n_users){
        this.numberOfUsers = n_users;
    }

    /**
     *  Método de alteração da variável de instância usersReviews
     * @param usersReviews novo valor da variável de instância usersReviews
     **/
    public void setUsersReviews (int usersReviews){
        this.usersReviews = usersReviews;
    }

    /**
     *  Método de alteração da variável de instância usersNoReviews
     * @param usersNoReviews novo valor da variável de instância usersNoReviews
     **/
    public void setUsersNoReviews (int usersNoReviews){
        this.usersNoReviews = usersNoReviews;
    }

    /**
     *  Método de alteração da variável de instância reviewNotImpactful
     * @param notImpactful novo valor da variável de instância reviewNotImpactful
     **/
    public void setReviewNotImpactful(int notImpactful){
        this.reviewNotImpactful = notImpactful;
    }

    /**
     *  Método de alteração da variável de instância averageReview
     * @param averageReview novo valor da variável de instância averageReview
     **/
    public void setAverageReview (double averageReview){
        this.averageReview = averageReview;
    }

    /**
     *  Método de alteração da variável de instância reviewsMonthly
     * @param reviewsMonthly novo valor da variável de instância reviewsMonthly
     **/
    public void setReviewsMonthly (Map<String,Integer> reviewsMonthly){
        Map<String,Integer> aux = new HashMap<>();
        for (Map.Entry<String, Integer> e : reviewsMonthly.entrySet()){
            aux.put(e.getKey(), e.getValue());
        }
        this.reviewsMonthly = aux;
    }

    /**
     *  Método de alteração da variável de instância averageMonthly
     * @param averageMonthly novo valor da variável de instância averageMonthly
     **/
    public void setAverageMonthly (Map<String,Float> averageMonthly){
        Map<String,Float> aux = new HashMap<>();
        for (Map.Entry<String, Float> e : averageMonthly.entrySet()){
            aux.put(e.getKey(), e.getValue());
        }
        this.averageMonthly = aux;
    }

    /**
     *  Método de alteração da variável de instância difUsersMonthly
     * @param difUsersMonthly novo valor da variável de instância difUsersMonthly
     **/
    public void setDifUsersMonthly (Map<String, Integer> difUsersMonthly){
        Map<String,Integer> aux = new HashMap<>();
        for (Map.Entry<String, Integer> e : difUsersMonthly.entrySet()){
            aux.put(e.getKey(), e.getValue());
        }
        this.difUsersMonthly = aux;
    }


    /**
     * Método de representação da classe Stats sob a forma de uma String
     * @return String com a representação da classe Stats
     **/
    public String toString(){
        return "Invalid Reviews: " + this.invalidReviews +
                "Total businesses: " + this.bizTotal +
                "Businesses Reviewed: " + this.bizReviewed +
                "Businesses Not Reviewed: " + this.bizNotReviewed +
                "Number of users: " + this.numberOfUsers +
                "Users with reviews: " + this.usersReviews +
                "Users without reviews: " + this.usersNoReviews +
                "Reviews sem impacto: " + this.reviewNotImpactful +
                "Average review: " + this.averageReview;

    }

}
