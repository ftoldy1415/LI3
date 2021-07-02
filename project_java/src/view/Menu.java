package view;


import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private List<String> options ;
    private int op;

    public Menu(String[] options){
        this.options = Arrays.asList(options);
        this.op = 0;
    }

    /**
     * Metodo de execuçao do Menu
     */
    public void execute(){
        do{
            showMenu();
            this.op = readOption();
        }while(this.op == -1);
    }


    /**
     * Metodo de impressao do Menu
     */
    public void showMenu(){
        System.out.println(this.options.get(0));
        for(int i = 1 ; i < this.options.size() ; i++){
            System.out.print(i);
            System.out.print(" - ");
            System.out.println(this.options.get(i));
        }
        System.out.println("0 - Exit");
    }

    /**
     * Metodo de leitura do valor da opçao selecionada
     * @return valor correspondente a opçao escolhida pelo utilizador
     */
    private int readOption(){
        int op;
        Scanner is = new Scanner(System.in);
        System.out.print("Option: ");
        try{
            op = is.nextInt();
        }catch(InputMismatchException e){
            op = -1;
        }
        if(op < 0 || op > this.options.size()){
            System.out.println("ERROR::Invalid Option!");
        }
        return op;
    }

    /**
     * Método de consulta da variável de instância Op
     * @return variável de instancia op
     */
    public int getOption(){
        return this.op;
    }

}
