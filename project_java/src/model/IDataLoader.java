package model;

import java.io.IOException;

public interface IDataLoader {
    boolean loadUsers(IGestReviewsModel model, String filename) throws IOException;
    boolean loadReviews(IGestReviewsModel model, String filename) throws IOException;
    boolean loadBusinesses(IGestReviewsModel model, String filename) throws IOException;
}
