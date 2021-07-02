package view;

import java.util.ArrayList;
import java.util.List;

public class Table implements ITable{
    private List<String> output;
    private int n_lines;
    private int current_line;
    private int total_lines;
    private int total_pages;
    private int current_page;

    public Table(List<String> out_table){
        this.output = new ArrayList<>(out_table);
        this.n_lines = 25;
        this.current_line = 0;
        this.total_lines = this.output.size();
        this.total_pages = Math.round(((float) this.total_lines)/25);
        this.current_page = 0;
    }

    /**
     * Metodo de paginaçao do output
     *
     */
    public void displayTable(){
        String[] s = {"**** Paginação ****", "Prev", "Next"};
        Menu table = new Menu(s);
        int option = -1;
        int endOfPage = 0;
        int beginOfPage = -this.n_lines;
        while(option != 0){
            table.execute();
            option = table.getOption();
            switch(option){
                case 1:
                    beginOfPage -= this.n_lines;
                    if (beginOfPage < 0 && endOfPage == 0){
                        beginOfPage = -this.n_lines;
                        current_page++;
                    }
                    else if(beginOfPage < 0){
                        beginOfPage = -this.n_lines;
                    }

                    endOfPage = beginOfPage + this.n_lines;
                    if (endOfPage > this.output.size()) endOfPage = this.output.size();
                    for(int i = beginOfPage; beginOfPage >= 0 && i < endOfPage ; i++)
                        System.out.println(output.get(i));
                    current_page--;
                    break;
                case 2:
                    beginOfPage += this.n_lines;
                    if(beginOfPage > this.output.size()){
                        beginOfPage = this.output.size();
                        endOfPage = beginOfPage;
                        current_page--;
                    }
                    else if (endOfPage + this.n_lines > this.output.size()){
                        endOfPage = this.output.size();
                    }
                    else endOfPage += this.n_lines;
                    for (int i = beginOfPage; i < endOfPage; i++)
                        System.out.println(output.get(i));
                    current_page++;
                    break;
            }
            System.out.println("Total of lines: " + this.total_lines);
            System.out.println("Current page: " + this.current_page);
            System.out.println("Total of pages: " + this.total_pages);
        }
    }
}