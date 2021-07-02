package controller;

import model.IGestReviewsModel;
import view.IGestReviewsView;

public interface IGestReviewsController {
        void setView(IGestReviewsView view);
        void setModel(IGestReviewsModel model);
        void start();
}
