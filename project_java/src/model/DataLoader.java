package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DataLoader implements IDataLoader {

    public boolean loadReviews(IGestReviewsModel model, String filename) throws IOException{

        Predicate<String[]> fieldsAmount =
                fields -> Stream.of(fields).noneMatch(f -> f.equals("")) && fields.length >= 9;

        Stream<String> lines1 = Files.lines(Paths.get("input_files/" + filename));
        Stream<String> lines3 = Files.lines(Paths.get("input_files/" + filename));
        int n_lines = (int) lines1.skip(1).count();
        lines3.skip(1).map(str->str.split(";"))
                     .filter(fieldsAmount)
                     .filter(s -> model.validateReview(s[1],s[2]))
                     .forEach(fields -> model.addReviewModel(fields[0],fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7]));
        model.setInvalidReviews(n_lines - model.getSizeReviews());

        return true;
    }

    public boolean loadUsers(IGestReviewsModel model, String filename) throws IOException{
        Predicate<String[]> fieldsAmount =
                fields -> Stream.of(fields).noneMatch(f -> f.equals("")) && fields.length == 3;


        Stream<String> lines1 = Files.lines(Paths.get("input_files/" + filename));
        Stream<String> lines3 = Files.lines(Paths.get("input_files/" + filename));
        int lines = (int) lines1.skip(1).count();
        lines3
                .skip(1)
                .map(str->str.split(";"))
                .filter(fieldsAmount)
                .forEach(fields -> model.addUserModel(fields[0],fields[1]));


        return true;
    }

    public boolean loadBusinesses(IGestReviewsModel model, String filename) throws IOException{
        Predicate<String[]> fieldsAmount =
                fields -> Stream.of(fields).noneMatch(f -> f.equals("")) && (fields.length == 4 || fields.length == 5);


        Stream<String> lines1 = Files.lines(Paths.get("input_files/" + filename));
        Stream<String> lines3 = Files.lines(Paths.get("input_files/" + filename));
        int lines = (int) lines1.skip(1).count();
        lines3
                .skip(1)
                .map(str->str.split(";"))
                .filter(fieldsAmount)
                .forEach(fields -> { if (fields.length == 4) model.addBusinessModel(fields[0],fields[1], fields[2].toLowerCase(Locale.ROOT),fields[3], "");
                                     else model.addBusinessModel(fields[0],fields[1], fields[2].toLowerCase(Locale.ROOT),fields[3], fields[4]);});
        model.setBizTotal(model.getSizeBiz());


        return true;
    }

}
