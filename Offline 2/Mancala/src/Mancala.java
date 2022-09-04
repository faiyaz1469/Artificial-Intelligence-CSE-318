import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


class PlayAndBuildMancalaGame
{
    int currentMancalaPlayer;
    GameState boardState;
    AI artificial = new AI();
    MancalaPlayersProfile[] playersListByName;

    public static void main(String[] args) {
        System.out.println("1. Play Mancala - Human vs Human" );
        System.out.println("2. Play Mancala - Human vs AI");
        System.out.println("3. Play Mancala - AI vs AI");

        System.out.println("Enter Option: ");
        Scanner p = new Scanner(System.in);
        int ch = p.nextInt();

        if (ch == 1) {
            System.out.println("Enter First Human Player Name: ");
            String name1 = p.next();
            System.out.println("Enter Second Human Player Name: ");
            String name2 = p.next();
            PlayAndBuildMancalaGame playGameMancals = new PlayAndBuildMancalaGame(name1, name2);
            playGameMancals.playMancala(0,ch);
        }
        else if (ch == 2){

            System.out.println("Enter heuristics: ");
            int choice = p.nextInt();

            System.out.println("Enter Human Player Name: ");
            String name = p.next();

            PlayAndBuildMancalaGame playGameMancals = new PlayAndBuildMancalaGame(name, null);
            playGameMancals.playMancala (choice,ch);
        }
        else if (ch == 3) {

            System.out.println("Enter heuristics: ");
            int choice = p.nextInt();

            PlayAndBuildMancalaGame playGameMancals = new PlayAndBuildMancalaGame(null, null);
            playGameMancals.playMancala(choice,ch);
        }
        else{
            System.out.println("Wrong Input!!");
        }
    }

    PlayAndBuildMancalaGame(String firstPlayerName, String secondPlayerName)
    {
        boardState = new GameState();
        playersListByName = new MancalaPlayersProfile[2];

        playersListByName[0] = new MancalaPlayersProfile(firstPlayerName, 0);
        playersListByName[1] = new MancalaPlayersProfile(secondPlayerName, 1);
        currentMancalaPlayer = 0;
    }


    public void playMancala(int heuristics,int choice)
    {
        if(choice == 1){

            displayMancalaBoardConsole();
            while (!boardState.gameOver()){

                String playerNamefromArray;
                if (currentMancalaPlayer == 0) {
                    playerNamefromArray = playersListByName[0].getName();
                } else {
                    playerNamefromArray = playersListByName[1].getName();
                }

                int pitNum;
                int sufflePitNumber;
                int p = 0;
                if (currentMancalaPlayer == 0) {

                    try {
                        System.out.println(" ______________________________________________________________________________");
                        System.out.println(" Recent Updated Board has been printed above along with last user turn.");
                        System.out.println(" ______________________________________________________________________________");
                        System.out.println(" ---Check Below to see whose turn is now!---\n");
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("<<<<<"+ playerNamefromArray + ">>>>>  PLEASE ENTER A PIT TO MOVE FROM :->   ");
                        while (scanner.hasNext()) {
                            if (scanner.hasNextInt()) {
                                p = scanner.nextInt();
                                if ((p <= 6 && p >= 1)) {
                                    if(boardState.stoneCount(p-1) != 0)
                                        break;
                                    else{
                                        System.out.println("**********************************************************************************************************************************************");
                                        System.out.println("Warning -> "+ playerNamefromArray +" has selected Pits without any stones in it (Pits=0),So Player has to Go Again and choose Pits from Console.");
                                        System.out.println("**********************************************************************************************************************************************");
                                    }
                                } else {
                                    System.out.println("******************************************************************************");
                                    System.out.println( playerNamefromArray + " HAS ENTERED A WRONG PIT... YOUR SELECTION SHOULD BE IN BETWEEN ( 1 TO 6 ), Please Provide correct Input Below");
                                    System.out.println("******************************************************************************");
                                    System.out.print("<<<<<" + playerNamefromArray + ">>>>>  PLEASE ENTER A PIT TO MOVE FROM :->   "); // Prompt User Input need to be provided
                                }
                            } else {
                                System.out.println("******************************************************************************");
                                System.out.println("ERROR: Invalid Input...Information is not in the correct format. YOUR SELECTION SHOULD BE IN BETWEEN ( 1 TO 6 ), Please Provide correct Input Below ");
                                System.out.println("******************************************************************************");
                                System.out.print("<<<<<" + playerNamefromArray + ">>>>>  PLEASE ENTER A PIT TO MOVE FROM :->   "); // Prompt User Input need to be provided
                                scanner.next();
                            }
                        }

                    } catch (NumberFormatException ex) {
                        System.err.println("Could not convert input string " + ex.getMessage()+"\n");
                    }

                    sufflePitNumber = p-1;
                } else {

                    try {
                        System.out.println(" ______________________________________________________________________________");
                        System.out.println(" Recent Updated Board has been printed above along with last user turn.");
                        System.out.println(" ______________________________________________________________________________");
                        System.out.println(" ---Check Below to see whose turn is now!---\n");
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("<<<<<"+ playerNamefromArray + ">>>>>  PLEASE ENTER A PIT TO MOVE FROM :->   ");
                        while (scanner.hasNext()) {
                            if (scanner.hasNextInt()) {
                                p = scanner.nextInt();
                                if ((p <= 6 && p >= 1)) {
                                    if(boardState.stoneCount(p+6) != 0)
                                        break;
                                    else{
                                        System.out.println("**********************************************************************************************************************************************");
                                        System.out.println("Warning -> "+ playerNamefromArray +" has selected Pits without any stones in it (Pits=0),So Player has to Go Again and choose Pits from Console.");
                                        System.out.println("**********************************************************************************************************************************************");
                                    }
                                } else {
                                    System.out.println("******************************************************************************");
                                    System.out.println( playerNamefromArray + " HAS ENTERED A WRONG PIT... YOUR SELECTION SHOULD BE IN BETWEEN ( 1 TO 6 ), Please Provide correct Input Below");
                                    System.out.println("******************************************************************************");
                                    System.out.print("<<<<<" + playerNamefromArray + ">>>>>  PLEASE ENTER A PIT TO MOVE FROM :->   "); // Prompt User Input need to be provided
                                }
                            } else {
                                System.out.println("******************************************************************************");
                                System.out.println("ERROR: Invalid Input...Information is not in the correct format. YOUR SELECTION SHOULD BE IN BETWEEN ( 1 TO 6 ), Please Provide correct Input Below ");
                                System.out.println("******************************************************************************");
                                System.out.print("<<<<<" + playerNamefromArray + ">>>>>  PLEASE ENTER A PIT TO MOVE FROM :->   "); // Prompt User Input need to be provided
                                scanner.next();
                            }
                        }

                    } catch (NumberFormatException ex) {
                        System.err.println("Could not convert input string " + ex.getMessage()+"\n");
                    }

                    sufflePitNumber = p+6;
                }

                pitNum = sufflePitNumber;


                System.out.println("Player [ " + playerNamefromArray + " ] moved from " + doReshuffle(sufflePitNumber));

                boolean checkgoAgainCall = boardState.applyMove(pitNum);

                displayMancalaBoardConsole();

                if (!checkgoAgainCall) {
                    if (currentMancalaPlayer == 0)
                        currentMancalaPlayer = 1;
                    else
                        currentMancalaPlayer = 0;
                } else {

                    System.out.println("Player [ " + playerNamefromArray + " ] goes again");
                }

            }

            boardState.stonesToMancalas();
            displayMancalaBoardConsole();

            if (boardState.stoneCount(6) > boardState.stoneCount(13)) {
                System.out.println("************* Congratulations..! *************");
                System.out.println(playersListByName[0].getName() + " wins");
                System.out.println("*******************************************");
            } else if (boardState.stoneCount(6) < boardState.stoneCount(13)) {
                System.out.println("************* Congratulations..! *************");
                System.out.println(playersListByName[1].getName() + " wins");
                System.out.println("*******************************************");
            } else {
                System.out.println("************* Scores of both players are equal..! *************");
                System.out.println("Tie!");
                System.out.println("*******************************************");
            }
        }

        if(choice == 2){

            displayMancalaBoardConsole();
            while (!boardState.gameOver()) {

                String playerNamefromArray;
                if (currentMancalaPlayer == 0) {
                    playerNamefromArray = playersListByName[0].getName();
                } else {
                    playerNamefromArray = playersListByName[1].getName();
                }


                int pitNum;
                int sufflePitNumber;
                int p = 0;
                if (currentMancalaPlayer == 0) {
                    try {
                        System.out.println(" ______________________________________________________________________________");
                        System.out.println(" Recent Updated Board has been printed above along with last user turn.");
                        System.out.println(" ______________________________________________________________________________");
                        System.out.println(" ---Check Below to see whose turn is now!---\n");
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("<<<<<"+ playerNamefromArray + ">>>>>  PLEASE ENTER A PIT TO MOVE FROM :->   ");
                        while (scanner.hasNext()) {
                            if (scanner.hasNextInt()) {
                                p = scanner.nextInt();
                                if ((p <= 6 && p >= 1)) {
                                    if(boardState.stoneCount(p-1) != 0)
                                        break;
                                    else{
                                        System.out.println("**********************************************************************************************************************************************");
                                        System.out.println("Warning -> "+ playerNamefromArray +" has selected Pits without any stones in it (Pits=0),So Player has to Go Again and choose Pits from Console.");
                                        System.out.println("**********************************************************************************************************************************************");
                                    }
                                } else {
                                    System.out.println("******************************************************************************");
                                    System.out.println( playerNamefromArray + " HAS ENTERED A WRONG PIT... YOUR SELECTION SHOULD BE IN BETWEEN ( 1 TO 6 ), Please Provide correct Input Below");
                                    System.out.println("******************************************************************************");
                                    System.out.print("<<<<<" + playerNamefromArray + ">>>>>  PLEASE ENTER A PIT TO MOVE FROM :->   "); // Prompt User Input need to be provided
                                }
                            } else {
                                System.out.println("******************************************************************************");
                                System.out.println("ERROR: Invalid Input...Information is not in the correct format. YOUR SELECTION SHOULD BE IN BETWEEN ( 1 TO 6 ), Please Provide correct Input Below ");
                                System.out.println("******************************************************************************");
                                System.out.print("<<<<<" + playerNamefromArray + ">>>>>  PLEASE ENTER A PIT TO MOVE FROM :->   "); // Prompt User Input need to be provided
                                scanner.next();
                            }
                        }

                    } catch (NumberFormatException ex) {
                        System.err.println("Could not convert input string " + ex.getMessage()+"\n");
                    }

                    sufflePitNumber = p-1;
                } else {
                    sufflePitNumber = artificial.moveMin(boardState,heuristics);
                }

                pitNum = sufflePitNumber;

                System.out.println("Player [ " + playerNamefromArray + " ] moved from " + doReshuffle(sufflePitNumber));

                boolean checkgoAgainCall = boardState.applyMove(pitNum);
                displayMancalaBoardConsole();

                if (!checkgoAgainCall) {
                    // If the current player does not go again,switch to the other player
                    if (currentMancalaPlayer == 0)
                        currentMancalaPlayer = 1;
                    else
                        currentMancalaPlayer = 0;
                } else {
                    System.out.println("Player [ " + playerNamefromArray + " ] goes again");
                }

            }

            boardState.stonesToMancalas();
            displayMancalaBoardConsole();

            if (boardState.stoneCount(6) > boardState.stoneCount(13)) {
                System.out.println("************* Congratulations..! *************");
                System.out.println(playersListByName[0].getName() + " wins");
                System.out.println("*******************************************");
            } else if (boardState.stoneCount(6) < boardState.stoneCount(13)) {
                System.out.println("************* Congratulations..! *************");
                System.out.println(playersListByName[1].getName() + " wins");
                System.out.println("*******************************************");
            } else {
                System.out.println("************* Scores of both players are equal..! **************");
                System.out.println("Tie!");
                System.out.println("*******************************************");
            }
        }

        if(choice == 3) {
            displayMancalaBoardConsole();
            while (!boardState.gameOver()) {
                String playerNamefromArray;
                if (currentMancalaPlayer == 0) {
                    playerNamefromArray = playersListByName[0].getName();
                } else {
                    playerNamefromArray = playersListByName[1].getName();
                }

                int pitNum;
                int sufflePitNumber;
                if (currentMancalaPlayer == 0) {
                    sufflePitNumber = artificial.moveMax(boardState,heuristics);
                } else {
                    sufflePitNumber = artificial.moveMin(boardState,heuristics);
                }

                pitNum = sufflePitNumber;
                System.out.println("Player [ " + playerNamefromArray + " ] moved from " + doReshuffle(sufflePitNumber));

                boolean checkgoAgainCall = boardState.applyMove(pitNum);
                displayMancalaBoardConsole();

                if (!checkgoAgainCall) {
                    if (currentMancalaPlayer == 0)
                        currentMancalaPlayer = 1;
                    else
                        currentMancalaPlayer = 0;
                } else {
                    System.out.println("Player [ " + playerNamefromArray + " ] goes again");
                }

            }

            boardState.stonesToMancalas();
            //displayMancalaBoardConsole();

            if (boardState.stoneCount(6) > boardState.stoneCount(13)) {
                System.out.println("************* Congratulations..! *************");
                System.out.println(playersListByName[0].getName() + " wins");
                System.out.println("*******************************************");
            } else if (boardState.stoneCount(6) < boardState.stoneCount(13)) {
                System.out.println("************* Congratulations..! *************");
                System.out.println(playersListByName[1].getName() + " wins");
                System.out.println("*******************************************");
            } else {
                System.out.println("************* Scores of both players are equal..! ************");
                System.out.println("Tie!");
                System.out.println("*******************************************");
            }
        }

    }


    public int doReshuffle (int userInputasPit)
    {
        int newPits = 0;

            if(userInputasPit==0 || userInputasPit==7)
                newPits= 1;
            else if(userInputasPit==1 || userInputasPit==8)
                newPits= 2;
            else if (userInputasPit==2 || userInputasPit==9)
                newPits= 3;
            else if (userInputasPit==3 || userInputasPit==10)
                newPits= 4;
            else if (userInputasPit==4 || userInputasPit==11)
                newPits= 5;
            else if (userInputasPit==5 || userInputasPit==12)
                newPits= 6;

        return newPits;
    }

    private void displayMancalaBoardConsole()
    {

        /*try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        System.out.println("\n");
        StringBuilder sepraterMancalaLineFiller = new StringBuilder();
        System.out.println("*************************************");
        System.out.print("      ");

        //Display Player 2 Pits
        for (int i = 12; i >= 7; i--)
        {
            System.out.print(boardState.stoneCount(i) + "    ");
            sepraterMancalaLineFiller.append("     ");
        }

        //Player 2 Information
        displayPlayer(1);

        //Display Mancala information Both side
        System.out.print(boardState.stoneCount(13) + "    ");
        System.out.print(sepraterMancalaLineFiller);
        System.out.println(boardState.stoneCount(6));

        System.out.print("      ");

        //Display Player 1 Pits
        for (int i = 0; i <= 5; i++)
            System.out.print(boardState.stoneCount(i) + "    ");

        //Player 1 Information
        displayPlayer(0);
        System.out.println("*************************************");
    }

    private void displayPlayer(int mancalaPlayerNum)
    {

        // Check If it this player's turn,
        if (currentMancalaPlayer == mancalaPlayerNum){
            System.out.print("            -->> ");        // --> Sign mean User Turn, Active user, Same can be visble in Console screen
        }else {
            System.out.print("                 ");       // User is not active , user loose his/her turn, Same can be visble in Console screen
        }


        int playerCounter;
        if(mancalaPlayerNum==0) {
            playerCounter=1;
        }else {
            playerCounter=2;
        }

        System.out.println("Player " + playerCounter + " ( " + playersListByName[mancalaPlayerNum].getName() + ")");
    }
}