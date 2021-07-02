package model.stats;

import java.util.HashMap;
import java.util.Map;

public interface IStats {
    int getbizTotal();
    int getBizReviewed();
    int getBizNotReviewed();
    int getUsersReviews();
    int getUsersNoReviews();
    double getAverageReview();
    Map<String,Integer> getReviewsMonthly();
    Map<String,Float> getAverageMonthly();
    Map<String, Integer> getDifUsersMonthly();
    int getInvalidReviews();
    int getNumberOfUsers();
    int getReviewsNotImpactful();




    void setInvalidReviews(int n);
    void setbizTotal(int bizTotal);
    void setbizReviewed(int bizReviewed);
    void setbizNotReviewed(int bizNotReviewed);
    void setNumberOfUsers (int n_users);
    void setUsersReviews (int usersReviews);
    void setUsersNoReviews (int usersNoReviews);
    void setReviewNotImpactful(int notImpactful);
    void setAverageReview (double averageReview);
    void setReviewsMonthly (Map<String,Integer> reviewsMonthly);
    void setAverageMonthly(Map<String,Float> averageMonthly);
    void setDifUsersMonthly (Map<String, Integer> difUsersMonthly);

}
