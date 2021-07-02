package model.reviews;

import java.time.LocalDateTime;
import java.util.Map;

public interface IReviewsCat {
    public void addReview(String review_id, String user_id, String business_id, float stars,
                          int useful, int funny, int cool, LocalDateTime date);
    public Map<String, IReviews> getCat();
    public int getSizeCat();
}
