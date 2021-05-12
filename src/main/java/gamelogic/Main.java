package gamelogic;

import fieldSetup.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;


public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static int numberOfFields;
    private static int numberOfMines;
    private static Field[][] field;

    public static void main(String[] args){
        logger.trace("Please type in the difficulty you want. easy or normal or hard");
        selectDifficulty();

        field = new Field[numberOfFields][numberOfFields];

        for(int i = 0; i < numberOfFields; i++){
            for( int j = 0; j < numberOfFields; j++){
                field[i][j] = new Field();
            }
        }

        randomizeMines(field);
        setNeighbours(field);

        print();

    }

    private static void selectDifficulty(){
        Scanner sc = new Scanner(System.in);
        String difficulty = sc.nextLine();

        if( difficulty.equalsIgnoreCase("easy") || difficulty.equalsIgnoreCase("normal") || difficulty.equalsIgnoreCase("hard") ){
            switch(difficulty.toLowerCase()) {
                case "easy":
                    numberOfFields = 9;
                    numberOfMines = 10;
                    break;
                case "normal":
                    numberOfFields = 16;
                    numberOfMines = 40;
                    break;
                case "hard":
                    numberOfFields = 24;
                    numberOfMines = 99;
            }
        }
        else{
            logger.info("I don't understand. Try again:");
            selectDifficulty();
        }
    }

    private static void print(){
        int rownum = 0;
        int colnum = 0;

        System.out.print("  ");

        for( int i = 0; i < numberOfFields; i++){
            if(colnum <= 10) {
                System.out.print(" " + colnum);
            }
            else{
                System.out.print(colnum);
            }
            colnum++;
        }

        System.out.print("\n  ");

        for( int i = 0; i < numberOfFields*2; i++){
            System.out.print("_");
        }
        System.out.println("");

        for(int i = 0; i< numberOfFields; i++){
            if(rownum < 10){
                System.out.print(rownum+ " |");
            }
            else {
                System.out.print(rownum+"|");
            }
            rownum++;

            for( int j = 0; j< numberOfFields; j++){
                if(field[i][j].isMine()) {
                    System.out.print("M ");
                }
                else{
                    System.out.print(field[i][j].getNeighborMines()+" ");
                }
            }
            System.out.println("");
        }
    }

    private static void correctPrint(){
        int rownum = 0;
        int colnum = 0;

        System.out.print("  ");

        for( int i = 0; i < numberOfFields; i++){
            if(colnum <= 10) {
                System.out.print(" " + colnum);
            }
            else{
                System.out.print(colnum);
            }
            colnum++;
        }

        System.out.print("\n  ");

        for( int i = 0; i < numberOfFields*2; i++){
            System.out.print("_");
        }
        System.out.println("");

        for(int i = 0; i< numberOfFields; i++){
            if(rownum < 10){
                System.out.print(rownum+ " |");
            }
            else {
                System.out.print(rownum+"|");
            }
            rownum++;

            for( int j = 0; j< numberOfFields; j++){
                if(field[i][j].isFlagged()){
                    System.out.print("L ");
                }
                else if(field[i][j].isClicked()){
                    System.out.print(field[i][j].getNeighborMines());
                }
                else{
                    System.out.print("X ");
                }
            }
            System.out.println("");
        }
    }

    private static void randomizeMines(Field[][] field){
        int randomx;
        int randomy;

        for( int i = 1; i < numberOfMines; i++){
            randomx = (int)(Math.random() * numberOfFields);
            randomy = (int)(Math.random() * numberOfFields);

            if( field[randomx][randomy].isMine() ){
                i--;
            }
            else{
                field[randomx][randomy].setMine(true);
            }
        }
    }

    private static void setNeighbours(Field[][] field){

        for( int i = 0; i < field.length; i++){
            for( int j = 0; j< field.length; j++){
                if(field[i][j].isMine()){

                    if( i == 0 && j == 0){  //happens if the mine is in the first column of the first row
                        field[i+1][j].setNeighborMines( field[i+1][j].getNeighborMines()+1 );
                        field[i][j+1].setNeighborMines( field[i][j+1].getNeighborMines()+1 );
                        field[i+1][j+1].setNeighborMines( field[i+1][j+1].getNeighborMines()+1 );
                    }
                    else if( i == field.length-1 && j == 0){ //happens if the mine is in the first column of the last row
                        field[i-1][j].setNeighborMines( field[i-1][j].getNeighborMines()+1 );
                        field[i][j-1].setNeighborMines( field[i][j-1].getNeighborMines()+1 );
                        field[i+1][j-1].setNeighborMines( field[i+1][j-1].getNeighborMines()+1 );
                    }
                    else if( i == 0 && j == field.length-1){ //happens if the mine is in the last column of the first row
                        field[i+1][j].setNeighborMines( field[i+1][j].getNeighborMines()+1 );
                        field[i][j-1].setNeighborMines( field[i][j+1].getNeighborMines()+1 );
                        field[i+1][j-1].setNeighborMines( field[i+1][j+1].getNeighborMines()+1 );
                    }
                    else if( i == field.length-1 && j == field.length-1){ //happens if the mine is in the last column of the last row
                        field[i-1][j].setNeighborMines( field[i-1][j].getNeighborMines()+1 );
                        field[i][j-1].setNeighborMines( field[i][j-1].getNeighborMines()+1 );
                        field[i-1][j-1].setNeighborMines( field[i-1][j-1].getNeighborMines()+1 );
                    }
                    else if( i == 0 ){ //happens if the mine is in the first row, but not on the edges
                        field[i][j-1].setNeighborMines( field[i][j-1].getNeighborMines()+1 );
                        field[i+1][j-1].setNeighborMines( field[i+1][j-1].getNeighborMines()+1 );
                        field[i+1][j].setNeighborMines( field[i+1][j].getNeighborMines()+1 );
                        field[i+1][j+1].setNeighborMines( field[i+1][j+1].getNeighborMines()+1 );
                        field[i][j+1].setNeighborMines( field[i][j+1].getNeighborMines()+1 );
                    }
                    else if( i == field.length-1 ){ //happens if the mine is in the last row, but not on the edges
                        field[i][j-1].setNeighborMines( field[i][j-1].getNeighborMines()+1 );
                        field[i-1][j-1].setNeighborMines( field[i-1][j-1].getNeighborMines()+1 );
                        field[i-1][j].setNeighborMines( field[i-1][j].getNeighborMines()+1 );
                        field[i-1][j+1].setNeighborMines( field[i-1][j+1].getNeighborMines()+1 );
                        field[i][j+1].setNeighborMines( field[i][j+1].getNeighborMines()+1 );
                    }
                    else if( j == 0 ){ //happens if the mine is in the first column but not on the edges
                        field[i-1][j].setNeighborMines( field[i-1][j].getNeighborMines()+1 );
                        field[i-1][j+1].setNeighborMines( field[i-1][j+1].getNeighborMines()+1 );
                        field[i][j+1].setNeighborMines( field[i][j+1].getNeighborMines()+1 );
                        field[i+1][j+1].setNeighborMines( field[i+1][j+1].getNeighborMines()+1 );
                        field[i+1][j].setNeighborMines( field[i+1][j].getNeighborMines()+1 );
                    }
                    else if( j == field.length-1 ){ //happens if the mine is in the last column but not on the edges
                        field[i-1][j].setNeighborMines( field[i-1][j].getNeighborMines()+1 );
                        field[i-1][j-1].setNeighborMines( field[i-1][j-1].getNeighborMines()+1 );
                        field[i][j-1].setNeighborMines( field[i][j-1].getNeighborMines()+1 );
                        field[i+1][j-1].setNeighborMines( field[i+1][j-1].getNeighborMines()+1 );
                        field[i+1][j].setNeighborMines( field[i+1][j].getNeighborMines()+1 );
                    }
                    else{ //Happens if the mine is not on any of the edges, so it has 8 neighbours.
                        field[i-1][j-1].setNeighborMines( field[i-1][j-1].getNeighborMines()+1 );
                        field[i-1][j].setNeighborMines( field[i-1][j].getNeighborMines()+1 );
                        field[i-1][j+1].setNeighborMines( field[i-1][j+1].getNeighborMines()+1 );
                        field[i][j+1].setNeighborMines( field[i][j+1].getNeighborMines()+1 );
                        field[i+1][j+1].setNeighborMines( field[i+1][j+1].getNeighborMines()+1 );
                        field[i+1][j].setNeighborMines( field[i+1][j].getNeighborMines()+1 );
                        field[i+1][j-1].setNeighborMines( field[i+1][j-1].getNeighborMines()+1 );
                        field[i][j-1].setNeighborMines( field[i][j-1].getNeighborMines()+1 );
                    }
                }
            }
        }
    }
}
