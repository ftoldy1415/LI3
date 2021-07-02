package view;

import model.GestReviewsModel;
import model.IGestReviewsModel;
import model.businesses.IBusiness;
import model.users.IUser;

import javax.swing.table.AbstractTableModel;
import java.util.*;

public class GestReviewsView implements IGestReviewsView{

    /**
     * Método que pede ao utilizador os ficheiros que este pretende carregar para a aplicação
     * @return triplo com os nomes dos ficheiros sobre o formato string.
     */
    public Map.Entry<String, Map.Entry<String, String>> getFilenames(){
        Scanner sc = new Scanner(System.in);
        String filename_businesses;
        String filename_reviews;
        String filename_users;
        System.out.println("Businesses file name?");
        filename_businesses = sc.nextLine();
        System.out.println("Reviews file name?");
        filename_reviews = sc.nextLine();
        System.out.println("Users file name?");
        filename_users = sc.nextLine();
        return new AbstractMap.SimpleEntry<>(filename_businesses, new AbstractMap.SimpleEntry<>(filename_reviews, filename_users));
    }

    /**
     * Metodo que pede ao utilizador que ficheiro pretende usar para a ação de carregar dados de/para um ficheiro
     * @return String com o nome do ficheiro especificado pelo utilizador
     */

    public String getInputFile(){
        Scanner sc = new Scanner(System.in);
        System.out.println("What's the file name?");
        return sc.nextLine();
    }


    /**
     * Metodo de impressão do número de reviews inválidas
     * @param n_invalid nº de reviews inválidas
     */
    public void displayInvalidReviews(int n_invalid){
        System.out.println("Number of Invalid Reviews: " + n_invalid);
    }

    /**
     * Metodo de impressão do número de negócios total
     * @param bizTotal nº de negócios total
     */
    public void displayBizTotal(int bizTotal){
        System.out.println("Total number of Businesses: " + bizTotal);
    }

    /**
     * Metodo de impressão do número de negócios avaliados
     * @param bizReviewed nº de negócios avaliados
     */
    public void displayBizReviewed(int bizReviewed){
        System.out.println("Total number of Businesses reviewed: " + bizReviewed );
    }

    /**
     * Metodo de impressão do número de negócios não avaliados
     * @param bizNotReviewed nº de Bussinesses nao avaliados
     */
    public void displayBizNotReviewed(int bizNotReviewed){
        System.out.println("Total number of Businesses not reviewed: " + bizNotReviewed);
    }

    /**
     * Metodo de impressão do número de utilizadores
     * @param numberOfUsers nº de utilizadores
     */
    public void displayNumberOfUsers(int numberOfUsers){
        System.out.println("Total number of Users: " + numberOfUsers);
    }

    /**
     * Metodo de impressão do número de utilizadores que fizeram avaliações
     * @param usersReviews nº de users com reviews feitas
     */
    public void displayUsersReviews(int usersReviews){
        System.out.println("Total of users who made reviews: " + usersReviews);
    }

    /**
     * Metodo de impressao do numero de users que nao avaliaram negocios
     * @param usersNoReviews
     */
    public void displayUsersNoReviews(int usersNoReviews){
        System.out.println("Total of users that didn't make any reviews: " + usersNoReviews);
    }

    /**
     * Meotdo de impressao do numero de reviews sem qualquer impacto
     * @param reviewNotImpactful
     */
    public void displayReviewsNotImpactful(int reviewNotImpactful){
        System.out.println("Number of reviews that don't have any impact " + reviewNotImpactful);
    }

    /**
     * Metodo de impressao da pontuaçao media
     * @param averageReview
     */
    public void displayAverageReview(double averageReview){
        System.out.println("Average of all the reviews made: " + averageReview);
    }

    /**
     * Metodo de impressao do map de numero de reviews por mes
     * @param reviewsMonthly
     */
    public void displayReviewsMonthly(Map<String, Integer> reviewsMonthly){
        List<String> output = new ArrayList<>();
        for (Map.Entry<String, Integer> e : reviewsMonthly.entrySet()){
            output.add("Date: " + e.getKey() + " | Number of Reviews: " + e.getValue());
        }
        ITable t_stats1 = new Table(output);
        t_stats1.displayTable();
    }

    /**
     * Metodo de impressao do map de pontuaçao media por mes
     * @param averageMonthly
     */
    public void displayAverageMonthly(Map<String,Float> averageMonthly){
        List<String> output = new ArrayList<>();
        for (Map.Entry<String, Float> e : averageMonthly.entrySet()){
            output.add("Date: " + e.getKey() + " | Mean: " + e.getValue());
        }
        ITable t_stats2 = new Table(output);
        t_stats2.displayTable();
    }

    /**
     * Metodo de impressao do map do nº de users distintos em cada mes
     * @param difUsersMonthly
     */
    public void displayDifUsersMonthly(Map<String,Integer> difUsersMonthly){
        List<String> output = new ArrayList<>();
        for (Map.Entry<String, Integer> e : difUsersMonthly.entrySet()){
            output.add("Date: " + e.getKey() + " | Different Users Monthly: " + e.getValue());
        }
        ITable t_stats3 = new Table(output);
        t_stats3.displayTable();
    }

    /**
     * Metodo que imprime mensagens de excepçoes
     * @param message
     */
    public void displayExceptions(String message){
        System.out.println(message);
    }


    /**
     * Método que pede os argumentos para a Query 2
     * @return string formata de mes/ano
     */
    public String getArgsQuery2(){
        Scanner sc = new Scanner(System.in);
        String month;
        String year;
        System.out.println("Month to search: ");
        month = sc.nextLine();
        System.out.println("Year: ");
        year = sc.nextLine();
        return month + "/" + year;
    }

    /**
     * Método que pede o argumento para a Query 3
     * @return string do user ID
     */
    public String getArgQuery3(){
        Scanner sc = new Scanner(System.in);
        String userID;
        System.out.println("User ID : ");
        userID = sc.nextLine();
        return userID;
    }

    /**
     * Método que pede o argumento para a Query 4
     * @return string do business ID
     */
    public String getArgQuery4(){
        Scanner sc = new Scanner(System.in);
        String businessID;
        System.out.println("Business ID : ");
        businessID = sc.nextLine();
        return businessID;
    }

    /**
     * Método que pede o argumento para a Query 6
     * @return número de businesses
     */
    public int getArgQuery6(){
        Scanner sc = new Scanner(System.in);
        int x;
        System.out.println("How many businesses? : ");
        x = Integer.parseInt(sc.nextLine());
        return x;
    }

    /**
     * Método que pede o argumento para a Query 8
     * @return  número de utilizadores
     */
    public int getArgQuery8(){
        Scanner sc = new Scanner(System.in);
        int x;
        System.out.println("How many users? : ");
        x = Integer.parseInt(sc.nextLine());
        return x;
    }


    /**
     * Método de impressão do resultado da query 1
     * @param biz_ids Lista de ids de businesses
     * @param numberOfBiz nºde businesses nao avaliados
     */
    public void displayResultQuery1(List<String> biz_ids, int numberOfBiz){
        ITable t1 = new Table(biz_ids);
        t1.displayTable();
        System.out.println("Total of unreviewed " + numberOfBiz);
    }


    /**
     * Método de impressão dos resultados da query 2
     * @param result Resultados sobre forma de List
     */
    public void displayResultQuery2(List<Integer> result){
        System.out.println("Number Of Reviews: " + result.remove(0));
        System.out.println("Number Of Users: " + result.remove(0));
    }


    /**
     * Método de impressão dos resultados da query 3
     * @param entry resultado da query 3
     */
    public void displayResultQuery3(Map<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> entry){
        List<String> output = new ArrayList<>();
        for (Map.Entry<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> e : entry.entrySet()){
            output.add("Month: " + e.getKey() + " -> " + "Número de Reviews distintas: " + e.getValue().getKey() + " | Número total de reviews: " +
                        e.getValue().getValue().getKey() + " | Média: " + e.getValue().getValue().getValue());
        }
        ITable t3 = new Table(output);
        t3.displayTable();
    }

    /**
     * Método de impressão do resultado da query 4
     * @param entry resultado da query 4 sob o formato de um mapa cujo valor é um triplo users/nº de reviews/média
     */
    public void displayQuery4(Map<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> entry){
        /*
        é um triplo users distintos/nº de reviews/média
         */
        List<String> output = new ArrayList<>();
        for (Map.Entry<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> e : entry.entrySet()){
            output.add("Month: " + e.getKey() + " -> " + " Número de Users distintas: " + e.getValue().getKey() + " | Número total de reviews: " +
                        e.getValue().getValue().getKey() + " | Média: " + e.getValue().getValue().getValue());
        }
        ITable t4 = new Table(output);
        t4.displayTable();
    }


    /**
     * Método de impressão do resultado da query 5
     * @param entry resultado da query 5 com formato de list de strings
     */
    public void displayQuery5(List<String> entry){
        //Para um user, lista de negocios que mais avaliou, quantos
        entry.add("Número de negocios avaliados: " + entry.size());
        ITable t5 = new Table(entry);
        t5.displayTable();
    }


    /**
     * Método de impressão do resultado da query 6
     * @param entry resultado da query 6 com formato de map cujos valores sao um par
     */
    public void displayQuery6(Map<String, List<Map.Entry<IBusiness, Integer>>> entry){
        // ano -> [(bus, number distinct users)]
        List<String> output = new ArrayList<>();
        List<Map.Entry<IBusiness, Integer>> aux;
        for (Map.Entry<String, List<Map.Entry<IBusiness, Integer>>> e : entry.entrySet()){
            output.add("Ano: " + e.getKey());
            for (Map.Entry<IBusiness, Integer> b : e.getValue()){
                output.add("Business: " + b.getKey() + " | Number of distinct users: " + b.getValue());
            }
        }
        ITable t6 = new Table(output);
        t6.displayTable();
    }


    /**
     * Método de impressão do resultado da query 7
     * @param entry resultado da query 7 com o formato de um Map em que o resultado é uma list de negócios
     */
    //public Map<String, List<IBusiness>> query7 ()
    public void displayQuery7(Map<String, List<IBusiness>> entry){
        List<String> output = new ArrayList<>();

        for (Map.Entry<String, List<IBusiness>> e : entry.entrySet()){
            output.add("City: " + e.getKey());
            output.add("3 Most Famous: ");
            for (IBusiness b : e.getValue()){
                output.add(b.toString());
            }
        }

        ITable t7 = new Table(output);
        t7.displayTable();
    }

    /**
     * Método de impressão dos resultados da query 8
     * @param entry resultado da query 8 com formato de Map <String, integer>
     */
    public void displayQuery8(List<Map.Entry<String, Integer>> entry){

        List<String> output = new ArrayList<>();
        Map.Entry<String, Integer> aux_entry;
        for (Map.Entry<String, Integer> e : entry){
            output.add("UserID: " + e.getKey() + " | Number of distinct businesses reviewed: " + e.getValue());
        }
        ITable t8 = new Table(output);
        t8.displayTable();
    }


    /**
     * Método para impressão dos resultados da query 9
     * @param entry resultado da query 9 com formato List de pares User/Média
     */
    public void displayQuery9(List<Map.Entry<IUser, Map.Entry<Float, Integer>>> entry){
        List<String> output = new ArrayList<>();
        for(Map.Entry<IUser, Map.Entry<Float, Integer>> e : entry)
            output.add("User: " + e.getKey().toString() + " | Mean: " + e.getValue().getKey());
        ITable t9 = new Table(output);
        t9.displayTable();
    }


    /**
     * Método para impressão dos resultados da query 10
     * @param entry resultado da query 10
     */
    public void displayQuery10(Map<String, Map<String, Map<IBusiness, Float>>> entry){
        List<String> output = new ArrayList<>();
        for (Map.Entry<String, Map<String, Map<IBusiness, Float>>> e : entry.entrySet()){
            output.add("State: " + e.getKey());
            for(Map.Entry<String, Map<IBusiness, Float>> e2 : e.getValue().entrySet()){
                output.add("Cidade: " + e2.getKey());
                for (Map.Entry<IBusiness, Float> e3 : e2.getValue().entrySet()){
                    output.add("Business: " + e3.getKey() + " | Média: " + e3.getValue());
                }
            }
        }
        ITable t10 = new Table(output);
        t10.displayTable();
    }



}
