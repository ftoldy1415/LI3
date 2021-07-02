package model.reviews;

import model.users.IUser;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ReviewsCat implements IReviewsCat, Serializable {

    private Map<String,IReviews> reviews_cat;

    public ReviewsCat(){
        this.reviews_cat = new HashMap<>();
    }

    /**
     * Método cujo propósito é adicionar uma Review ao catálogo de Reviews
     * @param review_id
     * @param user_id
     * @param business_id
     * @param stars
     * @param useful
     * @param funny
     * @param cool
     * @param date
     */
    public void addReview(String review_id, String user_id, String business_id, float stars,
                          int useful, int funny, int cool, LocalDateTime date) {
        IReviews r = new Reviews(review_id, user_id, business_id, stars, useful, funny, cool, date);
        this.reviews_cat.put(review_id, r);
    }

    /**
     *  Método de consulta da variável Cat
     * @return valor da variável de instância cat
     **/
    public Map<String, IReviews> getCat(){
        return this.reviews_cat.values().stream().collect(Collectors.toMap(IReviews::getReview_id, IReviews::clone));
    }

    /**
     * Método que calcula o tamanho do catálogo de Reviews
     * @return tamanho do catálogo
     */
    public int getSizeCat() {
        return  this.reviews_cat.size();
    }
}
