package model;

import model.businesses.IBusiness;
import model.reviews.IReviews;
import model.users.IUser;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String args[]){
        IGestReviewsModel model = new GestReviewsModel();

        try{
            IDataLoader loader = new DataLoader();
            boolean businessloaded = loader.loadBusinesses(model, "business_full.csv");
            boolean usersloaded = loader.loadUsers(model, "users_full.csv");
            boolean reviewsloaded = loader.loadReviews(model, "reviews_1M.csv");

            if (!businessloaded){
                System.out.println("Error parsing business");
            }
            if (!usersloaded){
                System.out.println("Error parsing business");
            }
            if (!reviewsloaded){
                System.out.println("Error parsing business");
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }



        System.out.println("Number Of Invalid Reviews: " + model.getInvalidReviews());

        //Map<String, IBusiness> businessCatalog = model.getBusinessCat();
        //System.out.println(businessCatalog.toString());
        //Map<String, IUser> userCatalog = model.getUserCat();
        //System.out.println(userCatalog.toString());
        //Map<String, IReviews> reviewsCatalog = model.getReviewsCat();
        //System.out.println(reviewsCatalog.toString());
        System.out.println("load done");
        //System.out.println(Arrays.toString(model.query1().toArray()));
        System.out.println(model.query2("4/2018").toString());
        //System.out.println(model.query3("d10Yt44D_-ZMtil_ICn5Xg").toString()) ;
        //System.out.println(model.query4("YZm_7uCMX3Y79s5bWGLGgA").toString()) ;
        //System.out.println(model.query5("uXCRPuB4oH1O4xVM7LqGQQ"));
        model.reviewsMonthly();
        model.averageMonthly();
        model.difUsersMonthly();

        //System.out.println(model.query7());

        //System.out.println(model.query6(5));
        //model.query10();
        //System.out.println(model.query8(15));
        //System.out.println(model.query9("NXHW9lTvBYNuf-mWsu8eTg", 30));



    }
}
