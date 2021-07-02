package model.reviews;

import model.businesses.Business;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reviews implements IReviews, Serializable {

    private String review_id;
    private String user_id;
    private String business_id;
    private float stars;
    private int useful;
    private int funny;
    private int cool;
    private LocalDateTime date;
    private String text;

    public Reviews(){
        this.review_id   = "";
        this.user_id     = "";
        this.business_id = "";
        this.stars       = 0;
        this.useful      = 0;
        this.funny       = 0;
        this.cool        = 0;
        this.date        = LocalDateTime.now();
        this.text        = "";
    }

    public Reviews(String review_id, String user_id, String business_id, float stars,
                   int useful, int funny, int cool, LocalDateTime date){
        this.review_id   = review_id;
        this.user_id     = user_id;
        this.business_id = business_id;
        this.stars       = stars;
        this.useful      = useful;
        this.funny       = funny;
        this.cool        = cool;
        this.date        = date;
        this.text        = "";
    }

    public Reviews (Reviews r){
        this.review_id   = r.getReview_id();
        this.user_id     = r.getUser_id();
        this.business_id = r.getBusiness_id();
        this.stars       = r.getStars();
        this.useful      = r.getUseful();
        this.funny       = r.getFunny();
        this.cool        = r.getCool();
        this.date        = r.getDate();
        this.text        = r.getText();
    }

    /**
     * Método de consulta da variável de instância Review_id
     * @return variável de instancia review_id
     */
    public String getReview_id(){
        return this.review_id;
    }

    /**
     * Método de consulta da variável de instância user_id
     * @return variável de instancia user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * Método de consulta da variável de instância business_id
     * @return variável de instancia business_id
     */
    public String getBusiness_id() {
        return business_id;
    }

    /**
     * Método de consulta da variável de instância stars
     * @return variável de instancia stars
     */
    public float getStars() {
        return stars;
    }

    /**
     * Método de consulta da variável de instância Useful
     * @return variável de instancia useful
     */
    public int getUseful() {
        return useful;
    }

    /**
     * Método de consulta da variável de instância Funny
     * @return variável de instancia Funny
     */
    public int getFunny() {
        return funny;
    }

    /**
     * Método de consulta da variável de instância Cool
     * @return variável de instancia cool
     */
    public int getCool(){
        return this.cool;
    }

    /**
     * Método de consulta da variável de instância Date
     * @return variável de instancia date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Método de consulta da variável de instância text
     * @return variável de instancia text
     */
    public String getText() {
        return text;
    }


    /**
     *  Método copia de um objeto Reviews
     * @return Copia do objeto Reviews
     **/
    public Reviews clone(){
        return new Reviews(this);
    }


    /**
     *  Método de comparação de um Reviews
     * @param o Objeto a comparar com o Reviews
     * @return resultado da igualdade
     **/
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Reviews r = (Reviews) o;
        return (this.review_id.equals(r.getReview_id())     &&
                this.user_id.equals(r.getUser_id())         &&
                this.business_id.equals(r.getBusiness_id()) &&
                this.stars  == r.getStars()                 &&
                this.useful == r.getUseful()                &&
                this.funny  == r.getFunny()                 &&
                this.date.equals(r.getDate())               &&
                this.text.equals(r.getText()));
    }

    /**
     * Método de representação da classe Reviews sob a forma de uma String
     * @return String com a representação da classe Reviews
     **/
    public String toString(){
        return "[ " + this.review_id + ";"
                + this.user_id + ";"
                + this.business_id + ";"
                + this.stars + ";"
                + this.useful + ";" +
                + this.funny + ";"
                + this.date.toString() + ";"
                + this.text + " ]";
    }

}
