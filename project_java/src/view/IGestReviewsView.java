package view;

import model.businesses.IBusiness;
import model.users.IUser;

import java.util.List;
import java.util.Map;

public interface IGestReviewsView {

    Map.Entry<String, Map.Entry<String, String>> getFilenames();
    String getInputFile();
    void displayInvalidReviews(int n_invalid);
    void displayBizTotal(int bizTotal);
    void displayBizReviewed(int bizReviewed);
    void displayBizNotReviewed(int bizNotReviewed);
    void displayNumberOfUsers(int numberOfUsers);
    void displayUsersReviews(int usersReviews);
    void displayUsersNoReviews(int usersNoReviews);
    void displayReviewsNotImpactful(int reviewNotImpactful);
    void displayAverageReview(double averageReview);
    void displayReviewsMonthly(Map<String, Integer> reviewsMonthly);
    void displayAverageMonthly(Map<String,Float> averageMonthly);
    void displayDifUsersMonthly(Map<String,Integer> difUsersMonthly);
    void displayExceptions(String message);
    String getArgsQuery2();
    String getArgQuery3();
    String getArgQuery4();
    int getArgQuery6();
    int getArgQuery8();
    void displayResultQuery1(List<String> biz_ids, int numberOfBiz);
    void displayResultQuery2(List<Integer> result);
    void displayResultQuery3(Map<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> entry);
    void displayQuery4(Map<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> entry);
    void displayQuery5(List<String> entry);
    void displayQuery6(Map<String, List<Map.Entry<IBusiness, Integer>>> entry);
    void displayQuery7(Map<String, List<IBusiness>> entry);
    void displayQuery8(List<Map.Entry<String, Integer>> entry);
    void displayQuery9(List<Map.Entry<IUser, Map.Entry<Float, Integer>>> entry);
    void displayQuery10(Map<String, Map<String, Map<IBusiness, Float>>> entry);
}
