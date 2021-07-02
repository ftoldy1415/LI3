package controller;

import model.*;
import model.businesses.IBusiness;
import model.stats.IStats;
import model.users.IUser;
import view.GestReviewsView;
import view.IGestReviewsView;
import view.Menu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GestReviewsController implements IGestReviewsController{
    private IGestReviewsView view;
    private IGestReviewsModel model;

    public void setView(IGestReviewsView view){
        this.view = view;
    }
    public void setModel(IGestReviewsModel model){
        this.model = model;
    }

    public void start() {
        String[] s = {"**** GestReviews ****", "Read File", "Load stats", "Display Stats", "Display Queries", "Gravar dados em ficheiro", "Carregar dados de ficheiro"};
        Menu start = new Menu(s);
        Map.Entry<String, Map.Entry<String, String>> filenames;
        String filename_businesses;
        String filename_reviews;
        String filename_users;
        String filename;
        boolean stats_loaded = false, files_loaded = false;
        int option = -1;
        while (option != 0) {
            start.execute();
            option = start.getOption();
            switch (option) {
                case 1:
                    filenames = view.getFilenames();
                    filename_businesses = filenames.getKey();
                    filename_reviews = filenames.getValue().getKey();
                    filename_users = filenames.getValue().getValue();
                    load_files(filename_businesses, filename_reviews, filename_users);
                    files_loaded = true;
                    break;
                case 2:
                    if (!files_loaded) view.displayExceptions("Files not yet loaded! To load the stats, load the files first!");
                    else{
                        initStats(model);
                        stats_loaded = true;
                    }
                    break;
                case 3:
                    if (!stats_loaded) view.displayExceptions("Stats not yet loaded! To display, load them first!");
                    else{
                        String[] string_stats = {"**** Stats ****", "Invalid Reviews", "Total Number of Businesses", "Distinct Businesses Reviewed", "Businesses Not Reviewed",
                                "Number of Users", "Number of Users With Reviews", "Number of Users Without Reviews (Inactive)", "Number of Not Inpactful Reviews",
                                "Average Review", "Reviews by Month", "Average Reviews by Month", "Number of Distinct Users by Month"};
                        Menu statsMenu = new Menu(string_stats);
                        int statsOption = -1;
                        while (statsOption != 0) {
                            statsMenu.execute();
                            statsOption = statsMenu.getOption();
                            switch (statsOption) {
                                case 1:
                                    view.displayInvalidReviews(model.getInvalidReviews());
                                    break;
                                case 2:
                                    view.displayBizTotal(model.getBizTotal());
                                    break;
                                case 3:
                                    view.displayBizReviewed(model.getBizReviewed());
                                    break;
                                case 4:
                                    view.displayBizNotReviewed(model.getBizNotReviewed());
                                    break;
                                case 5:
                                    view.displayNumberOfUsers(model.getNumberOfUsers());
                                    break;
                                case 6:
                                    view.displayUsersReviews(model.getUsersReviews());
                                    break;
                                case 7:
                                    view.displayUsersNoReviews(model.getUsersNoReviews());
                                    break;
                                case 8:
                                    view.displayReviewsNotImpactful(model.getReviewsNotImpactful());
                                    break;
                                case 9:
                                    view.displayAverageReview(model.getAverageReview());
                                    break;
                                case 10:
                                    view.displayReviewsMonthly(model.getReviewsMonthly());
                                    break;
                                case 11:
                                    view.displayAverageMonthly(model.getAverageMonthly());
                                    break;
                                case 12:
                                    view.displayDifUsersMonthly(model.getDifUsersMonthly());
                                    break;
                            }
                        }
                    }

                    break;
                case 4:
                    if (!files_loaded) view.displayExceptions("Files not yet loaded! To execute the queries, load the files first!");
                    else{
                        String[] string_querys = {"**** Querys ****", "query 1" , "query 2" , "query 3", "query 4","query 5","query 6","query 7",
                                "query 8","query 9","query 10"};
                        Menu querysMenu = new Menu(string_querys);
                        int querysOption = -1;
                        while (querysOption != 0) {
                            querysMenu.execute();
                            querysOption = querysMenu.getOption();
                            switch (querysOption) {
                                case 1:
                                    List<String> result_q1;
                                    result_q1 = model.query1();
                                    view.displayResultQuery1(result_q1,this.model.getBizNotReviewed());
                                    break;
                                case 2:
                                    List<Integer> result_q2;
                                    String monthYear = view.getArgsQuery2();
                                    result_q2 = model.query2(monthYear); //fazer exceções para ano invalido
                                    view.displayResultQuery2(result_q2);
                                    break;
                                case 3:
                                    Map<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> result_q3;
                                    String userIdFornecido = view.getArgQuery3();
                                    result_q3 = model.query3(userIdFornecido);
                                    view.displayResultQuery3(result_q3);
                                    break;
                                case 4:
                                    Map<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> result_q4;
                                    String businessIdFornecido = view.getArgQuery4();
                                    result_q4 = model.query4(businessIdFornecido);
                                    view.displayQuery4(result_q4);
                                    break;
                                case 5:
                                    List<String> result_q5;
                                    String userID = view.getArgQuery3();
                                    result_q5 = model.query5(userID);
                                    view.displayQuery5(result_q5);
                                    break;
                                case 6:
                                    Map<String, List<Map.Entry<IBusiness, Integer>>> result_q6;
                                    int x = view.getArgQuery6();
                                    result_q6 = model.query6(x);
                                    view.displayQuery6(result_q6);
                                    break;
                                case 7:
                                    Map<String, List<IBusiness>> result_q7;
                                    result_q7 = model.query7();
                                    view.displayQuery7(result_q7);
                                    break;
                                case 8:
                                    List<Map.Entry<String,Integer>> result_q8;
                                    int input_q8 = view.getArgQuery8();
                                    result_q8 = model.query8(input_q8);
                                    view.displayQuery8(result_q8);
                                    break;
                                case 9:
                                    List<Map.Entry<IUser, Map.Entry<Float, Integer>>> result_q9;
                                    String bus_id = view.getArgQuery4();
                                    int input_q9 = view.getArgQuery8();
                                    result_q9 = model.query9(bus_id,input_q9);
                                    view.displayQuery9(result_q9);
                                    break;
                                case 10:
                                    Map<String, Map<String, Map<IBusiness, Float>>> result_q10;
                                    result_q10 = model.query10();
                                    view.displayQuery10(result_q10);
                                    break;
                            }
                        }
                    }

                    break;
                case 5:
                    filename = view.getInputFile();
                    try{
                        model.saveToObjFile(filename);
                    }
                    catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    filename = view.getInputFile();
                    try{
                        this.model = GestReviewsModel.fromObjFile(filename);
                    }
                    catch(ClassNotFoundException | IOException e){
                        System.out.println(e.getMessage());
                    }
                    break;
            }
        }
    }

    private void load_files(String filename_businesses, String filename_reviews, String filename_users){
        try{
            IDataLoader loader = new DataLoader();
            loader.loadBusinesses(this.model, filename_businesses);
            loader.loadUsers(this.model, filename_users);
            loader.loadReviews(this.model, filename_reviews);
        }
        catch (IOException e){
            view.displayExceptions("File " + e.getMessage() + " does not exist!");
        }

    }

    private void initStats(IGestReviewsModel model){
        model.distinctBizReviewed();
        model.bizNotReviewed();
        model.usersTotal();
        model.usersReviews();
        model.reviewsNotImpactful();
        model.usersNoReviews(); //verificar users invalidos
        model.reviewsMonthly();
        model.averageMonthly();
        model.difUsersMonthly();
    }

}
