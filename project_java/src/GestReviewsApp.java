import controller.GestReviewsController;
import controller.IGestReviewsController;
import model.GestReviewsModel;
import model.IGestReviewsModel;
import view.*;

public class GestReviewsApp {

    public static void main(String[] args) {
        IGestReviewsModel model = new GestReviewsModel();
        IGestReviewsView view = new GestReviewsView();
        IGestReviewsController controller = new GestReviewsController();
        //controller

        controller.setView(view);
        controller.setModel(model);

        controller.start();




    }
}
