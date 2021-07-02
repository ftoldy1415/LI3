package tests;

import model.DataLoader;
import model.GestReviewsModel;
import model.IDataLoader;
import model.IGestReviewsModel;

import java.io.IOException;
import java.util.Scanner;

public class PerformanceTests {

    public static void main(String[] args){
        String filename_businesses = null;
        String filename_reviews = null;
        String filename_users = null;
        Scanner sc = new Scanner(System.in);
        GestReviewsModel model = new GestReviewsModel();
        IDataLoader loader = new DataLoader();
        boolean files_loaded = true;
        long beforeUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();

        while (filename_businesses == null && filename_reviews == null && filename_users == null){
            System.out.println("What files do you wish to load?");
            System.out.println("What's the businesses file name?");
            filename_businesses = sc.nextLine();
            System.out.println("What's the reviews file name?");
            filename_reviews = sc.nextLine();
            System.out.println("What's the users file name?");
            filename_users = sc.nextLine();
            if (filename_businesses != null && filename_reviews != null && filename_users != null){
                Crono.start();
                try{
                    loader.loadBusinesses(model, filename_businesses);
                    loader.loadUsers(model, filename_users);
                    loader.loadReviews(model, filename_reviews);
                }
                catch (IOException e){
                    System.out.println("File " + e.getMessage() + " does not exist!");
                    files_loaded = false;
                }

                if (files_loaded){
                    System.out.println("Files loaded in " + Crono.stop() + " seconds");

                    Crono.start();

                    model.distinctBizReviewed();
                    model.bizNotReviewed();
                    model.usersTotal();
                    model.reviewsNotImpactful();
                    model.usersReviews();
                    model.usersNoReviews();
                    model.reviewsMonthly();
                    model.averageMonthly();
                    model.difUsersMonthly();

                    System.out.println("Stats loaded in " + Crono.stop() + " seconds");
                }


            }

            if (files_loaded){
                System.out.println("Query 1: ");
                Crono.start();
                model.query1();
                System.out.println("Query 1 done in " + Crono.stop() + " seconds");

                System.out.println("Query 2: ");
                Crono.start();
                model.query2("4/2018");
                System.out.println("Query 2 done in " + Crono.stop() + " seconds");

                System.out.println("Query 3: ");
                Crono.start();
                model.query3("d10Yt44D_-ZMtil_ICn5Xg");
                System.out.println("Query 3 done in " + Crono.stop() + " seconds");

                System.out.println("Query 4: ");
                Crono.start();
                model.query4("YZm_7uCMX3Y79s5bWGLGgA");
                System.out.println("Query 4 done in " + Crono.stop() + " seconds");

                System.out.println("Query 5: ");
                Crono.start();
                model.query5("uXCRPuB4oH1O4xVM7LqGQQ");
                System.out.println("Query 5 done in " + Crono.stop() + " seconds");

                System.out.println("Query 6: ");
                Crono.start();
                model.query6(500);
                System.out.println("Query 6 done in " + Crono.stop() + " seconds");

                System.out.println("Query 7: ");
                Crono.start();
                model.query7();
                System.out.println("Query 7 done in " + Crono.stop() + " seconds");

                System.out.println("Query 8: ");
                Crono.start();
                model.query8(500);
                System.out.println("Query 8 done in " + Crono.stop() + " seconds");

                System.out.println("Query 9: ");
                Crono.start();
                model.query9("NXHW9lTvBYNuf-mWsu8eTg", 500);
                System.out.println("Query 9 done in " + Crono.stop() + " seconds");

                System.out.println("Query 10: ");
                Crono.start();
                model.query10();
                System.out.println("Query 10 done in " + Crono.stop() + " seconds");

                long afterUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();

                long actualMemUsed = afterUsedMem-beforeUsedMem;

                System.out.println(actualMemUsed);

                Crono.start();
                try{
                    model.saveToObjFile("binFileTest");
                }
                catch (IOException e){
                    System.out.println(e.getMessage());
                }

                System.out.println("Data saved to file in " + Crono.stop() + " seconds");

                Crono.start();
                try{
                    GestReviewsModel.fromObjFile("binFileTest");
                }
                catch (ClassNotFoundException | IOException e){
                    System.out.println(e.getMessage());
                }
                System.out.println("Data retrieved from file in " + Crono.stop() + " seconds");

            }


        }
    }

}
