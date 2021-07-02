package model;

import model.businesses.*;
import model.reviews.*;
import model.stats.*;
import model.users.*;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GestReviewsModel implements IGestReviewsModel, Serializable {
    private IBusinessesCat businessesCat;
    private IUsersCat usersCat;
    private IReviewsCat reviewsCat;
    private IStats stats;



    public GestReviewsModel() {
        this.businessesCat = new BusinessesCat();
        this.usersCat = new UserCat();
        this.reviewsCat = new ReviewsCat();
        this.stats = new Stats();
    }

    /**
     * Método que consulta a variável de instância InvalidReviews ao invocar o método homónimo da classe stats
     * @return variável de instancia invalidReviews
     */
    public int getInvalidReviews(){
        return this.stats.getInvalidReviews();
    }


    /**
     * Método que consulta a variável de instância bizTotal ao invocar o método homónimo da classe stats
     * @return variável de instancia bizTotal
     */
    public int getBizTotal(){
        return this.stats.getbizTotal();
    }


    /**
     * Método que consulta a variável de instância bizReviewed ao invocar o método homónimo da classe stats
     * @return variável de instancia bizReviewed
     */
    public int getBizReviewed(){
        return this.stats.getBizReviewed();
    }


    /**
     * Método que consulta a variável de instância bizNotReviewd ao invocar o método homónimo da classe stats
     * @return variável de instancia bizNotReviewd
     */
    public int getBizNotReviewed(){
        return this.stats.getBizNotReviewed();
    }


    /**
     * Método que consulta a variável de instância numberOfUsers ao invocar o método homónimo da classe stats
     * @return variável de instancia numberOfUsers
     */
    public int getNumberOfUsers(){
        return this.stats.getNumberOfUsers();
    }


    /**
     * Método que consulta a variável de instância UsersReviews ao invocar o método homónimo da classe stats
     * @return variável de instancia UsersReviews
     */
    public int getUsersReviews(){
        return this.stats.getUsersReviews();
    }


    /**
     * Método que consulta a variável de instância UsersNoReviews ao invocar o método homónimo da classe stats
     * @return variável de instancia UsersNoReviews
     */
    public int getUsersNoReviews(){
        return this.stats.getUsersNoReviews();
    }


    /**
     * Método que consulta a variável de instância AverageReview ao invocar o método homónimo da classe stats
     * @return variável de instancia AverageReview
     */
    public double getAverageReview(){
        return this.stats.getAverageReview();
    }


    /**
     * Método que consulta a variável de instância ReviewsNotImpactful ao invocar o método homónimo da classe stats
     * @return variável de instancia ReviewsNotImpactful
     */
    public int getReviewsNotImpactful(){
        return this.stats.getReviewsNotImpactful();
    }


    /**
     * Método que consulta a variável de instância ReviewsMonthly ao invocar o método homónimo da classe stats
     * @return variável de instancia ReviewsMonthly
     */
    public Map<String,Integer> getReviewsMonthly(){
        return this.stats.getReviewsMonthly();
    }


    /**
     * Método que consulta a variável de instância averageMonthly ao invocar o método homónimo da classe stats
     * @return variável de instancia averageMonthly
     */
    public Map<String,Float> getAverageMonthly(){
        return this.stats.getAverageMonthly();
    }


    /**
     * Método que consulta a variável de instância difUsersMonthly ao invocar o método homónimo da classe stats
     * @return variável de instancia difUsersMonthly
     */
    public Map<String, Integer> getDifUsersMonthly(){
        return this.stats.getDifUsersMonthly();
    }


    /**
     * Método que recebe os dados de uma review e formata a string data de forma a se adequar ao modelo de LocalDateTime e invoca o método addReview
     * para adicionar a Review ao catálogo de Reviews
     * @param review_id
     * @param user_id
     * @param business_id
     * @param stars
     * @param useful
     * @param funny
     * @param cool
     * @param date
     */
    public void addReviewModel(String review_id, String user_id, String business_id,
                               String stars, String useful, String funny, String cool, String date) {
        //Set<String> textAsList = new HashSet<>();
        //String[] splittedText = text.split("[, .'!?]+");
        //11/10/2014  03:34:02
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.reviewsCat.addReview(review_id, user_id, business_id, Float.parseFloat(stars), Integer.parseInt(useful),
                Integer.parseInt(funny), Integer.parseInt(cool), LocalDateTime.parse(date, formatter));

    }

    /**
     * Método análogo ao anterior mas que, inves de formatar a data, formata a string de todas as categorias, transformando-a numa lista de Strings, antes de invocar o método addBusiness
     * para adicionar o business ao catálogo de business
     * @param business_id
     * @param name
     * @param city
     * @param state
     * @param categories
     */
    public void addBusinessModel(String business_id, String name, String city, String state, String categories) {
        String[] s = categories.split(",");
        List<String> categoriesList;
        categoriesList = new ArrayList<>(Arrays.asList(s));
        this.businessesCat.addBusiness(business_id, name, city, state, categoriesList);
    }

    public void addUserModel(String user_id, String name) {
        this.usersCat.addUser(user_id, name);
    }


    /**
     * Método que calcula o tamanho do catálogo de Reviews
     * @return tamanho do catálogo
     */
    public int getSizeReviews(){
        return this.reviewsCat.getSizeCat();
    }

    /**
     * Método que calcula o tamanho do catálogo de businesses
     * @return tamanho do catálogo
     */
    public int getSizeBiz(){
        return this.businessesCat.getSizeCat();
    }


    /**
     * Método de consulta da variável de instância businessesCat
     * @return variável de instancia businessesCat
     */
    public Map<String, IBusiness> getBusinessCat() {
        return this.businessesCat.getCat();
    }

    /**
     * Método de consulta da variável de instância usersCat
     * @return variável de instancia usersCat
     */
    public Map<String, IUser> getUserCat() {
        return this.usersCat.getUsersCat();
    }

    /**
     * Método de consulta da variável de instância reviewsCat
     * @return variável de instancia reviewsCat
     */
    public Map<String, IReviews> getReviewsCat() {
        return this.reviewsCat.getCat();
    }

    /**
     * Método que define o valor de reviews inválidas nas stats
     * @param n_invalid
     */
    public void setInvalidReviews(int n_invalid){
        this.stats.setInvalidReviews(n_invalid);
    }

    /**
     * Método que define o valor de business totais nas stats
     * @param n
     */
    public void setBizTotal(int n){
        this.stats.setbizTotal(n);
    }


    /**
     * Método de representação do catálogo de Reviews de uma String
     * @return variável de instancia reviewsCat
     */
    public String ReviewsCatToString() {
        return this.reviewsCat.toString();
    }


    /**
     * Método que valida uma Review. Para tal, verifica a existência do business e do utilizador cujos id's estão mencionados na Review utilizando os métodos apropriados definidos nas respectivas classes
     * @param user_id id do utilizador a validar
     * @param business_id id do business a validar
     * @return
     */
    public boolean validateReview(String user_id, String business_id){
        return (this.businessesCat.validateBizID(business_id) && this.usersCat.validateUserID(user_id));
    }

    /**
     * Método que calcula o número de businesses distintos que foram avaliados. para tal, é usada uma stream dos valores do catálogo de reviews
     * e aplicado método distinct() e count() para chegar ao resultado final
     */
    public void distinctBizReviewed(){
        int distinct = (int) this.reviewsCat.getCat().values().stream().map(r -> r.getBusiness_id()).distinct().count();
        this.stats.setbizReviewed(distinct);
    }

    /**
     * Método que calcula o número total de Business não avaliados, para tal é calculado o número total de negócios e o número de negócios avaliados e depois é usada uma simples subtração
     */
    public void bizNotReviewed(){
        int total = this.stats.getbizTotal();
        int reviewed = this.stats.getBizReviewed();
        this.stats.setbizNotReviewed(total - reviewed);
    }

    /**
     * Método que calcula e define nas stats o número de users
     */
    public void usersTotal(){
        this.stats.setNumberOfUsers(this.usersCat.getSizeCat());
    }

    /**
     * Método que calcula o número de utilizadores distintos que fizeram reviews e define esse valor nas stats
     */
    public void usersReviews(){
        int distinct = (int) this.reviewsCat.getCat().values().stream().map(u -> u.getUser_id()).distinct().count();
        this.stats.setUsersReviews(distinct);
    }

    /**
     * Método que calcula e define nas stats o número de reviews sem qualquer impacto (para tal são filtradas todas as reviews cujo total da soma do atributo
     * Useful Cool e Funny é 0)
     */
    public void reviewsNotImpactful(){
        int zeroImpact = (int) this.reviewsCat.getCat().values().stream().filter(r -> r.getCool() == 0 && r.getFunny() == 0 && r.getUseful() == 0).count();
        this.stats.setReviewNotImpactful(zeroImpact);
    }

    /**
     * Método que calcula e define nas stats o número de users que nao fizeram qualquer avaliação
     */
    public void usersNoReviews(){
        this.stats.setUsersNoReviews(this.usersCat.getSizeCat() - this.stats.getUsersReviews());
    }

    /**
     * Método que calcula e define nas stats o Map de número de reviews feitas numa dada combinação Mês/Ano. Para tal é percorrido o catálogo de reviews e,
     * a cada review, é verificado a combinação mês/ano e (caso não exista nenhuma key no mapa com essa combinação) adiciona a key ao map ou incrementa o valor de uma key já existente
     */
    public void reviewsMonthly(){
        Map<String,Integer> rMonthly = new TreeMap<>();
        Map<String,IReviews> rCat = this.reviewsCat.getCat();
        for(IReviews r : rCat.values()){
            String month = r.getDate().getMonth() + "/" + r.getDate().getYear();
            if(rMonthly.containsKey(month)){
                rMonthly.put(month, rMonthly.get(month) + 1);
            }else {
                rMonthly.put(month,1);
            }
        }
        this.stats.setReviewsMonthly(rMonthly);
    }

    /**
     * Método que calcula e define o map de médias de pontuações de reviews feitas em cada mês e a média total. Para tal, é inicialmente criado um map que calcula a pontuação
     * cumulativa de reviews realizadas nesse mês ao percorrer o map de catálogo de reviews. O segundo passo realizado é percorrer o Map acabado de construir no passo anterior e,
     * recorrendo aos valores armazenados no map de número de reviews em cada mês, alterar os valores do mapa original e , inves de estes serem o valor cumulativo de reviews no mês, passar a ser a média
     *
     */
    public void averageMonthly(){
        Map<String,Float> avgMonthly = new TreeMap<>();
        Map<String,IReviews> rCat = this.reviewsCat.getCat();
        Map<String,Integer> rMonthly = this.stats.getReviewsMonthly();
        float avgTotal = 0;
        int sumTotal = 0;
        for(IReviews r : rCat.values()){
            String month = r.getDate().getMonth() + "/" + r.getDate().getYear();
            if(avgMonthly.containsKey(month)){
                avgMonthly.put(month,avgMonthly.get(month) + r.getStars());
            }else{
                avgMonthly.put(month,r.getStars());
            }
        }
        for(Map.Entry<String,Float> e : avgMonthly.entrySet()){
            Float avg = e.getValue() / rMonthly.get(e.getKey());
            avgTotal += avg;
            sumTotal++;
            avgMonthly.put(e.getKey(),avg);
        }
        this.stats.setAverageMonthly(avgMonthly);
        this.stats.setAverageReview(avgTotal / sumTotal);
    }

    /**
     * Método que calcula e define nas stats o map de número de users distintos que fizeram uma review em cada mês. Para tal é percorrido, de forma semelhante aos métodos anteriores,
     * o catálogo de reviews. Cada user é armazenado numa lista associada a uma key num map auxiliar no primeiro ciclo for. O segundo ciclo for tem o objetivo de preencher o map difUser com
     * o número de users distintos na lista (utilizando uma stream)
     */
    public void difUsersMonthly(){
        Map<String,IReviews> rCat = this.reviewsCat.getCat();
        Map<String,List<IReviews>> rMonthly = new TreeMap<>();
        Map<String,Integer> difUser = new TreeMap<>();
        for(IReviews r : rCat.values()){
            String month = r.getDate().getMonth() + "/" + r.getDate().getYear();
            if(rMonthly.containsKey(month)){
                List<IReviews> aux = rMonthly.get(month);
                aux.add(r);
            }else{
                List<IReviews> emptyAux = new ArrayList<>();
                emptyAux.add(r);
                rMonthly.put(month,emptyAux);
            }
        }
        for(Map.Entry<String,List<IReviews>> e : rMonthly.entrySet()){
            int total = (int) e.getValue().stream().map(r -> r.getUser_id()).distinct().count();
            difUser.put(e.getKey(),total);
        }
        this.stats.setDifUsersMonthly(difUser);
    }



    /*----------------------------------- QUERYS ------------------------------------*/

    /**
     * Método que ordena alfabeticamente os identificadores de negócios nunca avaliados com o auxílio do catálogo de reviews e do catálogo de negócios
     * @return lista de id's de negócios
     */
    public List<String> query1() {
        TreeSet<String> resultado = this.businessesCat.getCat().values().stream().map(b -> b.getBusiness_id()).collect(Collectors.toCollection(TreeSet::new));
        Map<String, IReviews> aux = this.reviewsCat.getCat();
        Iterator<Map.Entry<String, IReviews>> it = aux.entrySet().iterator();

        while (it.hasNext()) {
            IReviews review = it.next().getValue();
            resultado.remove(review.getBusiness_id());
        }
        List<String> res = new ArrayList<>(resultado);
        return res;
    }


    /**
     * Método que determina o nº total de reviews realizadas e o número total de users distintos que realizaram reviews num dado mês
     * @param monthYear
     * @return par (numberReviews, numberUsers) sob a forma de Lista
     */
    public List<Integer> query2(String monthYear) {
        List<IReviews> listAux = new ArrayList<>();
        Map<String, IReviews> aux = this.reviewsCat.getCat();
        Iterator<Map.Entry<String, IReviews>> it = aux.entrySet().iterator();

        while (it.hasNext()) {
            IReviews review = it.next().getValue();
            String dateAux = review.getDate().getMonthValue() + "/" + review.getDate().getYear();
            if (dateAux.equals(monthYear)) {
                listAux.add(review);
            }
        }

        int numberReviews = listAux.size();
        int numberUsers = (int) listAux.stream().map(IReviews::getUser_id).distinct().count(); //distinct dá return de uma stream de todos os users distintos
        //Map.Entry<Integer, Integer> result = new AbstractMap.SimpleEntry<>(numberReviews, numberUsers);
        List<Integer> result = new ArrayList<>();
        result.add(numberReviews);
        result.add(numberUsers);
        return result;
    }


    /**
     * Método que, dado um código de utilizador, determina para cada mês quantas reviews fez, quantos negócios avaliou e que nota media atribuiu. Percorre o catalogo de reviews para popular
     * Um Map cujas keys sao os meses e os valores sao listas de Reviews. O segundo ciclo percorre esse Map e cria outro HashMap - result - cujas Keys sao as mesmas que o map anterior mas os valores
     * sao um Map.Entry com um segundo Map.Entry como valor para fornecer os dados pedidos para cada mês - nº de reviews distintas, nº de reviews total e média.
     * @param userIdFornecido código de utilizador
     * @return Map da informação organizada
     */
    public Map<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> query3(String userIdFornecido) {
        Map<String, List<IReviews>> reviewsUser = new HashMap<>();       //estrutura de dados que vai conter todas as reviews do user organizadas por mês
        Map<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> result = new HashMap<>();
        Map<String, IReviews> aux = this.reviewsCat.getCat();
        Iterator<Map.Entry<String, IReviews>> it = aux.entrySet().iterator();

        while (it.hasNext()) {
            IReviews review = it.next().getValue();
            if (review.getUser_id().equals(userIdFornecido)) {
                String dateAux = review.getDate().getMonthValue() + "/" + review.getDate().getYear();
                if (reviewsUser.containsKey(dateAux)) {
                    List<IReviews> reviewsList = reviewsUser.get(dateAux);
                    reviewsList.add(review);
                    reviewsUser.put(dateAux, reviewsList);
                } else {
                    List<IReviews> reviewsList = new ArrayList<>();
                    reviewsList.add(review);
                    reviewsUser.put(dateAux, reviewsList);
                }
            }
        }

        Iterator<Map.Entry<String, List<IReviews>>> it2 = reviewsUser.entrySet().iterator();
        int n_reviews = 0;
        double mean = 0;
        while (it2.hasNext()) {
            n_reviews = 0;
            mean = 0;
            Map.Entry<String, List<IReviews>> entry = it2.next();
            List<IReviews> reviews = entry.getValue();
            n_reviews = reviews.size();
            double starsCount = reviews.stream().map(IReviews::getStars).mapToDouble(Double::valueOf).sum();
            mean = starsCount / n_reviews;
            int revsDistinct = (int) reviews.stream().map(IReviews::getBusiness_id).distinct().count(); //distinct dá return de uma stream de todos os business distintos
            Map.Entry<Integer, Double> revMean = new AbstractMap.SimpleEntry<>(n_reviews, mean);
            Map.Entry<Integer, Map.Entry<Integer, Double>> resultMonth = new AbstractMap.SimpleEntry<>(revsDistinct, revMean);
            result.put(entry.getKey(), resultMonth);
        }
        return result;
    }


    /**
     * Método que, dado um Business ID , determina para cada mês quantas vezes foi avaliado. O primeiro ciclo percorre o catálogo de reviews e armazena em reviewsBusiness a lista de reviews feitas
     * em cada mês para o Business fornecido. Depois, tal como na query anterior, um segundo ciclo percorre o Map construído, calculando a informação pertinente para cada mês. Essa informação fica guardada
     * num Map em que o value é um triplo users distintos/nº de reviews/média
     * @param businessIdFornecido
     * @return Map resultante
     */
    public Map<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> query4(String businessIdFornecido) {
        Map<String, List<IReviews>> reviewsBusiness = new HashMap<>();
        Map<String, Map.Entry<Integer, Map.Entry<Integer, Double>>> result = new HashMap<>();
        Map<String, IReviews> aux = this.reviewsCat.getCat();
        Iterator<Map.Entry<String, IReviews>> it = aux.entrySet().iterator();

        while (it.hasNext()) {
            IReviews review = it.next().getValue();
            if (review.getBusiness_id().equals(businessIdFornecido)) {
                String dateAux = review.getDate().getMonthValue() + "/" + review.getDate().getYear();
                if (reviewsBusiness.containsKey(dateAux)) {
                    List<IReviews> reviewsList = reviewsBusiness.get(dateAux);
                    reviewsList.add(review);
                    reviewsBusiness.put(dateAux, reviewsList);
                } else {
                    List<IReviews> reviewsList = new ArrayList<>();
                    reviewsList.add(review);
                    reviewsBusiness.put(dateAux, reviewsList);
                }
            }
        }

        Iterator<Map.Entry<String, List<IReviews>>> it2 = reviewsBusiness.entrySet().iterator();
        int n_reviews = 0;
        double mean = 0;
        while (it2.hasNext()) {
            n_reviews = 0;
            mean = 0;
            Map.Entry<String, List<IReviews>> entry = it2.next();
            List<IReviews> reviews = entry.getValue();
            n_reviews = reviews.size();
            double starsCount = reviews.stream().map(IReviews::getStars).mapToDouble(Double::valueOf).sum();
            mean = starsCount / n_reviews;
            int usersDistinct = (int) reviews.stream().map(IReviews::getUser_id).distinct().count(); //distinct dá return de uma stream de todos os business distintos
            Map.Entry<Integer, Double> revMean = new AbstractMap.SimpleEntry<>(n_reviews, mean);
            Map.Entry<Integer, Map.Entry<Integer, Double>> resultMonth = new AbstractMap.SimpleEntry<>(usersDistinct, revMean);
            result.put(entry.getKey(), resultMonth);
        }
        return result;
    }




    /**
     * Método que, dado um user ID, determina a lista dos nomes de negócios que mais avaliou, ordenada por ordem decrescente de quantidade e por ordem alfabetica
     * caso a quantidade seja igual. Para tal, é utilizado um ciclo para percorrer o catálogo de Reviews, gerando um Map em que a Key é o Business e o valor o nº de vezes que avaliou
     * Por fim, é criada uma lista que organiza os dados do map por ordem decrescente de quantidade recorrendo ao comparatorQuery5
     * @param userID
     * @return Lista de Strings dos nomes dos negócios
     */
    public List<String> query5 (String userID){
        Map<IBusiness,Integer> result = new HashMap<>();
        Map<String, IReviews> aux = this.reviewsCat.getCat();
        Iterator<Map.Entry<String, IReviews>> it = aux.entrySet().iterator();

        while (it.hasNext()) {
            IReviews review = it.next().getValue();
            if (review.getUser_id().equals(userID)) {
                IBusiness b = this.businessesCat.getCat().get(review.getBusiness_id());
                if(result.containsKey(b.getName())){
                    Integer oldval = result.get(review.getBusiness_id());
                    Integer newval = oldval++;
                    result.put(b, newval);
                }
                else{
                    result.put(b,1);
                }
            }
        }
        return result.entrySet().stream().sorted(new ComparatorQuery5()).map(e -> e.getKey().getName()).collect(Collectors.toList());
    }


    /**
     * Método que determina o conjunto de X negócios mais avaliados em cada ano e indica o numero total users distintos que os avaliaram. O primeiro ciclo começa por percorrer as reviews
     * construindo dois maps auxiliares, o quantityPerYear e o userDistinctBiz. No segundo ciclo, os dados desses dois maps sao combinados, criando assim listas ordenadas de pares Negócio/distinctUsers
     * sendo essas listas adicionadas a um map resultado em que a key é o ano.
     * @param x nº de negócios a listar por ano
     * @return
     */
    public Map<String, List<Map.Entry<IBusiness, Integer>>> query6(int x){
        Map<String, List<Map.Entry<IBusiness, Integer>>> res = new HashMap<>();
        Map<String, Map<String, Integer>> quantityPerYear = new HashMap<>();
        Map<String, IReviews> rCat = this.reviewsCat.getCat();
        Map<String, IBusiness> bCat = this.businessesCat.getCat();
        String bus_id, year;
        IReviews aux;
        Map<String, Integer> auxMap;
        Map<String, Map<String, Integer>> userDistinctPerBiz = new HashMap<>(); //map que vai guardar o numero de users distintos da forma (key -> bus_id, value -> map de users distintos)
        Map<String, Integer> userIds;                                           //map a ser inserido em userDistinctPerBiz
        int newVal;
        for (Map.Entry<String, IReviews> r : rCat.entrySet()){ //percorrer as reviews
            aux = r.getValue();
            year = "" + aux.getDate().getYear();
            bus_id = aux.getBusiness_id();
            if (!userDistinctPerBiz.containsKey(bus_id)){ //se o business não existir no map userDistinctPerBiz, adicionar uma entrada para ele e criar uma lista com o user_id
                userIds = new HashMap<>();
                userIds.put(aux.getUser_id(), 1);
                userDistinctPerBiz.put(bus_id, userIds);
            }
            else{                                         // caso contrário, se para este bus_id, ainda não existir o user_id na lista, insere-o
                if (!userDistinctPerBiz.get(bus_id).containsKey(aux.getUser_id())){
                    userDistinctPerBiz.get(bus_id).put(aux.getUser_id(), 1);
                }
            }
            if (quantityPerYear.containsKey(year)){            //se o ano já existir em quantityPerYear, testar a existencia do business
                auxMap = quantityPerYear.get(year);
                if (auxMap.containsKey(bus_id)){               //caso exista, incrementa o numero de Reviews a ele associado
                    newVal = auxMap.get(bus_id);
                    newVal++;
                    auxMap.put(bus_id, newVal);
                }
                else{                                          //caso contrario, cria uma nova entrada para o business e começa o seu contador de reviews a 1
                    auxMap.put(bus_id, 1);
                }
            }
            else{                                              // se o ano não existir em quantityPerYear, é preciso criar uma nova entrada bem como inciar o Map de negócios
                auxMap = new HashMap<>();                      // e das suas quantidades e adicionar lá o negócio em questão
                auxMap.put(bus_id, 1);
                quantityPerYear.put(year, auxMap);
            }
        }

        //transformar os maps em listas ordenadas e recolher os primeiros x
        int counter, distinctUsers;
        List<Map.Entry<String, Integer>> sortedList;
        Iterator<Map.Entry<String, Integer>> it;
        Map.Entry<String, Integer> entryBus;
        List<Map.Entry<IBusiness, Integer>> auxList; //lista dos negocios organizados num par de negocio, users distintos que o avaliaram
        for (Map.Entry<String, Map<String, Integer>> entry : quantityPerYear.entrySet()){ //percorrer o map quantityPerYear (Ano, (bus_id, nReviews))
            year = entry.getKey();
            sortedList = entry.getValue().entrySet().stream().sorted(new ComparatorByQuantity()).collect(Collectors.toList()); //lista ordenada de Map.Entry<bus_id, nReviews> por quantidade de reviews feitas
            counter = 0;
            distinctUsers = 0;
            it = sortedList.iterator();
            auxList = new ArrayList<>(); //organizar os businesses numa lista de pares (business, users distintos)
            while (it.hasNext() && counter < x){ //percorrer a lista enquanto não tenhamos tirado x elementos ou enquanto tem elementos
                entryBus = it.next();
                distinctUsers = userDistinctPerBiz.get(entryBus.getKey()).size();  //faz uso do map userDistinctPerBiz para saber quantos users distintos existem para este negocio
                auxList.add(new AbstractMap.SimpleEntry<>(bCat.get(entryBus.getKey()), distinctUsers)); // inserir no par (o negocio, numero de users distintos)
                counter++;
            }              // numero de users distintos que fizeram reviews a um negocio
            res.put(year, auxList); //inserir no map resultado
        }

        return res;

    }


    /**
     * Método que determina os 3 negócios mais famosos de cada cidade. Para isso começa por percorrer o catálogo das reviews, armazenando para cada cidade o número de reviews em cada business.
     * No final, são retirados para cada cidade os 3 businesses mais famosos , e o resultado final será um Map em que as keys correspondem às cidades , e os values correspondem a listas com os 3 business mais famosos
     * @return Map<String, List<IBusiness>> onde, para cada cidade, está a lista dos 3 negócios mais famosos em termos de número de reviews
     */
    public Map<String, List<IBusiness>> query7 (){
        Map<String, List<IBusiness>> res = new HashMap<>();
        Map<String, IReviews> rCat = this.reviewsCat.getCat();
        Map<String, IBusiness> bCat = this.businessesCat.getCat();
        Map<String, Map<String, Integer>>  quantityPerCity = new HashMap<>();
        IReviews aux;
        String cityAux, bus_id;
        Map<String, Integer> entry;
        int newValue;
        for (Map.Entry<String, IReviews> e : rCat.entrySet()){
            aux = e.getValue();
            bus_id = aux.getBusiness_id();
            cityAux = bCat.get(bus_id).getCity();
            if (quantityPerCity.containsKey(cityAux)){ //se a cidade já existir
                entry = quantityPerCity.get(cityAux);
                if (entry.containsKey(bus_id)){        //verifica se o business já existe
                    newValue = entry.get(bus_id);      //se existir incrementa o contador de reviews
                    newValue++;
                    entry.put(bus_id, newValue);
                }
                else{                                  //se não existir, cria uma entrada com o contador a 1
                    entry.put(bus_id, 1);
                }
            }
            else{                                      //se a cidade não existir, cria uma nova entrada e um novo map de cidades e quantidades e adiciona
                entry = new HashMap<>();               //lá o business_id em questão
                entry.put(bus_id, 1);
                quantityPerCity.put(cityAux, entry);
            }
        }
        List<Map.Entry<String, Integer>> sortedList;
        List<IBusiness> bizResult;
        Iterator<Map.Entry<String, Integer>> it;
        int counter;
        for (Map.Entry<String, Map<String, Integer>> e : quantityPerCity.entrySet()){   // percorrer o map (cidade, (bus_id, quantidade de reviews)) para formar uma lista ordenada por maior quantidade de reviews
            sortedList = e.getValue().entrySet().stream().sorted(new ComparatorByQuantity()).collect(Collectors.toList()); //lista ordenada pela quantidade de reviews
            it = sortedList.iterator();
            counter = 0;
            bizResult = new ArrayList<>();
            while (it.hasNext() && counter < 3){       //iterar a lista ordenada e tirar apenas os 3 primeiros para cada cidade e inserir no map res (cidade, IBusiness)
                bizResult.add(bCat.get(it.next().getKey())); // nao é feito clone uma vez que os businesses que constam em bCat já são cópias
                counter++;
            }
            res.put(e.getKey(), bizResult);
        }

        return res;

    }

    /**
     * Método que determina os códigos dos X utilizadores (sendo X dado pelo utilizador) que avaliaram mais negócios diferentes. Para tal recorre a dois ciclos, o primeiro
     * cria um map auxiliar em que a id é a key e o valor é um map de negocios que o user avaliou. O segundo ciclo serve para simplificar esse map para um map Res.
     * @param x
     * @return List de pares dos primeiros X users da lista resultante de ordenar os Map.Entry
     */
    public List<Map.Entry<String, Integer>> query8(int x){
        Map<String, Integer> res = new HashMap<>();
        Map<String, Map<String, Integer>> aux_users = new HashMap<>();
        Map<String, Integer> aux_bus;
        Map<String, IReviews> rCat = this.reviewsCat.getCat();
        int ignore = 0;
        String user_id, bus_id;
        IReviews rev;
        for (Map.Entry<String, IReviews> e : rCat.entrySet()){
            rev = e.getValue();
            user_id = rev.getUser_id();
            bus_id = rev.getBusiness_id();
            if (aux_users.containsKey(user_id)){
                aux_bus = aux_users.get(user_id);
                aux_bus.put(bus_id, ignore);
            }
            else{
                aux_bus = new HashMap<>();
                aux_bus.put(bus_id, ignore);
                aux_users.put(user_id, aux_bus);
            }
        }

        for (Map.Entry<String, Map<String, Integer>> e : aux_users.entrySet()){
            res.put(e.getKey(), e.getValue().size());
        }

        return res.entrySet().stream().sorted(new ComparatorQuery8()).limit(x).collect(Collectors.toList());
    }


    /**
     * Método que, dado o código de um negócio, determinar o conjunto dos X users que mais o avaliaram e, para cada um, qual o valor médio de classificação. O primeiro ciclo organiza num Map os
     * dados importantes para esta query relativos a cada User. O segundo map simplifica aux_users, retirando o total cumulativo do triplo, criando resMap.
     * O resultado do método é a lista ordenada dos primeiros x Map.Entry's do mapa simplificado resMap.
     * @param bus_id ID do business fornecido pelo utilizador
     * @param x tamanho máximo da lista final.
     * @return
     */
    public List<Map.Entry<IUser, Map.Entry<Float, Integer>>> query9(String bus_id, int x){
        //  user_id,         mean,             total, occurences
        Map<IUser, Map.Entry<Float, Map.Entry<Float, Integer>>> aux_users = new HashMap<>();
        Map<IUser, Map.Entry<Float, Integer>> resMap = new HashMap<>();
        Map<String, IReviews> rCat = this.reviewsCat.getCat();
        Map<String, IUser> uCat = this.usersCat.getUsersCat();
        List<IUser> teste = new ArrayList<>();
        IReviews aux_rev;
        IUser aux_user;
        Map.Entry<Float, Map.Entry<Float, Integer>> aux_entry;
        float total, mean;
        int occurences;
        for (Map.Entry<String, IReviews> e : rCat.entrySet()){
            aux_rev = e.getValue();
            aux_user = uCat.get(aux_rev.getUser_id());
            if (aux_rev.getBusiness_id().equals(bus_id)){
                teste.add(aux_user);
                if (aux_users.containsKey(aux_user)){
                    aux_entry = aux_users.get(aux_user);
                    occurences = aux_entry.getValue().getValue();
                    total = aux_entry.getValue().getKey();
                    mean = (total + aux_rev.getStars())/(occurences + 1);
                    aux_users.put(aux_user, new AbstractMap.SimpleEntry<>(mean, new AbstractMap.SimpleEntry<>(total + aux_rev.getStars(), occurences + 1)));
                }
                else{
                    aux_users.put(aux_user, new AbstractMap.SimpleEntry<>(aux_rev.getStars(), new AbstractMap.SimpleEntry<>(aux_rev.getStars(), 1)));
                }
            }
        }

        for (Map.Entry<IUser, Map.Entry<Float, Map.Entry<Float, Integer>>> e : aux_users.entrySet()){
            resMap.put(e.getKey(), new AbstractMap.SimpleEntry<>(e.getValue().getKey(), e.getValue().getValue().getValue()));
        }

        return resMap.entrySet().stream().sorted(new ComparatorQuery9()).limit(x).collect(Collectors.toList());
    }


    // Map<String, Map<String, List<Float>>>
    //     (estado ->     (cidade -> (business -> (média, (total cumulativo, nReviews)))))

    /**
     * Para executar esta query, é utilizada como estrutura intermédia um Map complexo em que as chaves é o estado e o valor é um outro map em que as chaves são as cidades
     * e os valores são um outro map em que as chaves são os businesses e um triplo, do tipo (Média, (Total Comulativo, Número de reviews)). Assim, será possível à medida
     * que é feita a travessia das reviews, no sítio correto, caso já exista é atualizado o seu valor de média e caso não exista, é criado um espaço para o mesmo. O mesmo
     * método é usado para adicionar as cidades caso não existam e os estados.
     * @return Map<String, Map<String, Map<IBusiness, Float>>> um map em que para cada cidade tem como valor um map em que para cada cidade tem um último map em que as chaves
     * são os businesses e os valores são as suas médias das pontuações de todas as reviews a ele feitas
     */
    public Map<String, Map<String, Map<IBusiness, Float>>> query10(){
        Map<String, Map<String, Map<IBusiness, Map.Entry<Float, Map.Entry<Integer, Integer>>>>> res = new HashMap<>();
        Map<String, Map<String,Map<IBusiness, Float>>> res_final = new HashMap<>();
        Map<String, IReviews> rCat = this.reviewsCat.getCat();
        Map<String, IBusiness> bCat = this.businessesCat.getCat();
        int total, nReviews;
        float mean;
        String state, city;
        IBusiness auxBiz;
        IReviews auxRev;
        Map<IBusiness, Map.Entry<Float, Map.Entry<Integer, Integer>>> entry;
        Map<String, Map<IBusiness, Map.Entry<Float, Map.Entry<Integer, Integer>>>> auxMap;
        Map<IBusiness, Map.Entry<Float, Map.Entry<Integer, Integer>>> auxMap2;
        for (Map.Entry<String, IReviews> r : rCat.entrySet()){
            auxRev = r.getValue();
            auxBiz = bCat.get(auxRev.getBusiness_id());
            state = auxBiz.getState();
            city = auxBiz.getCity();
            if (res.containsKey(state)){
                auxMap = res.get(state);
                if (auxMap.containsKey(city)){
                    auxMap2 = auxMap.get(city);
                    if (auxMap2.containsKey(auxBiz)){
                        total = auxMap2.get(auxBiz).getValue().getKey();
                        nReviews = auxMap2.get(auxBiz).getValue().getValue();
                        total += auxRev.getStars();
                        nReviews++;
                        mean= ((float) total)/nReviews;
                        auxMap2.put(auxBiz, new AbstractMap.SimpleEntry<>(mean, new AbstractMap.SimpleEntry<>(total, nReviews)));
                    }
                    else{
                        auxMap2.put(auxBiz, new AbstractMap.SimpleEntry<>(auxRev.getStars(), new AbstractMap.SimpleEntry<>((int) auxRev.getStars(), 1)));
                    }
                    auxMap.put(city, auxMap2);
                    res.put(state, auxMap);
                }
                else{
                    entry = new HashMap<>();
                    entry.put(auxBiz, new AbstractMap.SimpleEntry<>(auxRev.getStars(), new AbstractMap.SimpleEntry<>((int) auxRev.getStars(), 1)));
                    auxMap.put(city, entry);
                    res.put(state, auxMap);
                }
            }
            else{
                entry = new HashMap<>();
                entry.put(auxBiz, new AbstractMap.SimpleEntry<>(auxRev.getStars(), new AbstractMap.SimpleEntry<>((int) auxRev.getStars(), 1)));
                auxMap = new HashMap<>();
                auxMap.put(city, entry);
                res.put(state, auxMap);
            }
        }


        Map<IBusiness, Float> aux_insert1;
        Map<String, Map<IBusiness, Float>> aux_insert2;
        for (Map.Entry<String, Map<String, Map<IBusiness, Map.Entry<Float, Map.Entry<Integer, Integer>>>>> e : res.entrySet()){
            state = e.getKey();
            aux_insert2 = new HashMap<>();
            for (Map.Entry<String, Map<IBusiness, Map.Entry<Float, Map.Entry<Integer, Integer>>>> e2 : res.get(state).entrySet()){
                city = e2.getKey();
                aux_insert1 = new HashMap<>();
                for (Map.Entry<IBusiness, Map.Entry<Float, Map.Entry<Integer, Integer>>> e3 : res.get(state).get(city).entrySet()){
                    aux_insert1.put(e3.getKey(), e3.getValue().getKey());
                }
                aux_insert2.put(city, aux_insert1);

            }
            res_final.put(state, aux_insert2);
        }
        return res_final;
    }

    
    public void saveToObjFile(String filename) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public static GestReviewsModel fromObjFile(String filename) throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        GestReviewsModel grm = (GestReviewsModel) ois.readObject();
        ois.close();
        return grm;
    }


}

