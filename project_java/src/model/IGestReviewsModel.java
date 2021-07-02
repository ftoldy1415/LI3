package model;

import model.businesses.IBusiness;
import model.reviews.IReviews;
import model.users.IUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IGestReviewsModel {
    void addReviewModel(String review_id, String user_id, String business_id, String stars,
                          String useful, String funny, String cool, String date);
    void addUserModel(String user_id, String name);
    void addBusinessModel(String business_id, String name, String city, String state, String categories);
    Map<String, IBusiness> getBusinessCat();
    Map<String, IUser> getUserCat();
    Map<String, IReviews> getReviewsCat();
    String ReviewsCatToString();
    void setInvalidReviews(int n_invalid);
    void setBizTotal(int n );
    int getSizeReviews();
    int getSizeBiz();
    int getInvalidReviews();
    int getBizTotal();
    int getBizReviewed();
    void distinctBizReviewed();
    void bizNotReviewed();
    void usersTotal();
    void usersReviews();
    void reviewsNotImpactful();
    void usersNoReviews();
    int getBizNotReviewed();
    double getAverageReview();
    int getNumberOfUsers();
    int getUsersReviews();
    int getUsersNoReviews();
    int getReviewsNotImpactful();
    Map<String,Integer> getReviewsMonthly();
    Map<String,Float> getAverageMonthly();
    Map<String, Integer> getDifUsersMonthly();
    boolean validateReview(String user_id, String business_id);
    List<String> query1();
    List<Integer> query2(String monthYear);
    Map<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> query3(String userIdFornecido);
    Map<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> query4(String businessIdFornecido);
    List<String> query5 (String userID);
    Map<String, List<Map.Entry<IBusiness, Integer>>> query6(int x);
    Map<String, List<IBusiness>> query7();
    List<Map.Entry<String, Integer>> query8(int x);
    List<Map.Entry<IUser, Map.Entry<Float, Integer>>> query9(String bus_id, int x);
    Map<String, Map<String, Map<IBusiness, Float>>> query10();
    void reviewsMonthly();
    void averageMonthly();
    void difUsersMonthly();
    void saveToObjFile(String filename) throws FileNotFoundException, IOException;
}
