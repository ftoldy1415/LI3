package model.reviews;

import java.time.LocalDateTime;

public interface IReviews {

    public String getReview_id();
    public String getUser_id();
    public String getBusiness_id();
    public float getStars();
    public int getUseful();
    public int getFunny();
    public int getCool();
    public LocalDateTime getDate();
    public String getText();
    public Reviews clone();

}
