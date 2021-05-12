package main;

import minefieldSetup.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gameplayLogic.Printer;
import gameplayLogic.Moves;

import java.util.Scanner;


public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static int numberOfFields;
    private static int numberOfMines;
    private static Field[][] field;

    public static void main(String[] args){
        logger.trace("Please type in the difficulty you want. easy or normal or hard");
        Setup.selectDifficulty();
        int numberOfFields = Setup.getNumberOfFields();
        int numberOfMines = Setup.getNumberOfMines();

        Field[][] field = new Field[numberOfFields][numberOfFields];

        for(int i = 0; i < numberOfFields; i++){
            for(int j = 0; j < numberOfFields; j++){
                field[i][j] = new Field();
            }
        }

        Setup.randomizeMines(field);
        Setup.setNeighbours(field);

        Printer.duringGamePrint(field, numberOfFields);

        while(true){
            int numberOfNotClicked = 0;
            Moves.userMove(field, numberOfFields);

            for(int i = 0; i < numberOfFields; i++){
                for(int j = 0; j < numberOfFields; j++){
                    if( !field[i][j].isClicked() ){
                        numberOfNotClicked++;
                    }
                }
            }
            if( numberOfNotClicked == numberOfMines ){
                Printer.endgamePrint(field, numberOfFields);
                System.out.println("You won!");
                break;
            }
        }
        System.exit(0);
    }

}
