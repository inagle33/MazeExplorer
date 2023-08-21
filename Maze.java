/**
 * A class that implements a file of a map. The user is tasked with finding the treasure before falling 
 * into a pit or being killed by a monster. There are many methods in this class that are used to make
 * the game run.
 *
 * @author Isaac Nagle
 * @version 11222022
 *
 */
 
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Maze {
   
   private int[][] location;     //private variable declarations
   private int row;
   private int col;
   
   /**
    * Constructor for map.
    */ 
   public Maze(String mapFile) {
      openMap(mapFile);
      this.row = 0;
      this.col = 0;
   }
   /**
    * Method that checks the user's current location using the rows and columns compared to the 2-D array
    * that contains the map's coordinates.
    * 
    * @return int value that is the coordinate of the 2-D array.
    */
    public int checkMap() {
       int coordinate = this.location[this.row][this.col];
       return coordinate;
    }
    
   /**
    * Opens up the file with the map that will be used for the game. 
    * 
    * @param mapFile
    *             The name of the file containing the map.
    * @return 2-D array with coordinate plane of the map.
    */
   private void openMap(String mapFile) {
      String area = "";          //variable declarations
      String contents = "";
      int i = 0;
      int j = 0;
      int size = 0;
      this.location = new int[size][size];
      try {
         File map = new File(mapFile); //open file
         Scanner mapScan = new Scanner(map); //create scanner object for file
         area = mapScan.nextLine(); //assigns area to first line of file which is dimensions of the map
         size = Integer.parseInt(area); //assigns size to int value of dimensions of the map
         this.location = new int[size][size]; //makes the 2-D array the size of the dimensions of the map
         while (mapScan.hasNext()) { //assign 2-D array to map coordinates
            contents = mapScan.nextLine(); //assigns contents with the next line of the file
            String[] numbers = contents.split(","); //create an array with values of each line w/o commas
            for (i = 0; i < numbers.length; i++) { //loop to assign each number in file to each coordinate
               this.location[j][i] = Integer.parseInt(numbers[i]);
            }
            j++;
         }
         // for (int k = 0; k < size; k++) {    //tests that location prints out correct values from file
         //    for (int f = 0; f < size; f++) {
         //       System.out.print(location[k][f] + " ");
         //    }
         //    System.out.println();
         // }
         mapScan.close(); //close scanner object
      } catch (FileNotFoundException e) { //error message if file cannot be opened
         System.out.println("ERROR - Cannot load file " + mapFile);
      }
   }
   
   /**
    * Get the user's decision for which direction they wish to move in.
    * 
    * @param scnr
    *             Scanner to take input from keyboard.
    * @return String that is user's choice of direction as a lowercase letter.
    */
   public String direction(Scanner scnr) {
      String move = NESW().toString(); //string with the directions that the user can move in
      String choice = scnr.nextLine(); //takes user's choice of direction
      choice = choice.toUpperCase().substring(0,1); //gets first character from input in uppercase 
      while (!move.contains(choice)) { //while loop to ensure user enters valid direction
         System.out.println("You can't go that way.");
         System.out.print("Which way do you want to move? ");
         choice = scnr.nextLine();
         choice = choice.toUpperCase().substring(0,1);
      }
      choice = choice.toLowerCase(); //makes the choice a lowercase letter
      return choice;
   }
   
   /**
    * This method will tell the user which directions they can move in based on their current location.
    * 
    * @param upDown
    *             int that holds the value of the rows of the 2-D array map.
    * @param leftRight
    *             int that holds the value of the columns of the 2-D array map.
    * @return moveTo
    *             StringBuilder that has the directions the user can choose to move in.
    */
   public StringBuilder NESW() {
      StringBuilder moveTo = new StringBuilder();
      if (this.row != 0) {                 //if statements to determine choices of NESW
         moveTo.append("N");
      }
      if (this.col != this.location.length-1) {
         moveTo.append("E");
      }
      if (this.row != this.location.length-1) {
         moveTo.append("S");
      }
      if (this.col != 0) {
         moveTo.append("W");
      }
      return moveTo;
   }
   
   /**
    * This method will update the values of the rows and columns where the user is currently located
    * in the map.
    * 
    * @param letter
    *             char that holds the user's choice of direction they wish to go in
    * @param upDown
    *             int that holds the value of the rows of the 2-D array map.
    * @param leftRight
    *             int that holds the value of the columns of the 2-D array map.
    */
   public void updateCurrentLocation(String letter) {
      if (letter.equals("n")) {        //updates which row and column user is in based on which direction
         this.row--;                   //user chose to move in
      }
      else if (letter.equals("e")) {
         this.col++;
      }
      else if (letter.equals("s")) {
         this.row++;
      }
      else if (letter.equals("w")) {
         this.col--;
      }
   }
   
   /**
    * Method that gives the user a hint if they are in a room next to a pit or the ravenous Bugblatter 
    * Beast. This is done by comparing the user's current location to the map.
    */
   public void hints() {
      String options = NESW().toString(); //string with the directions that the user can move in
      if (options.contains("N")) {     //statements that determine which hint should ne displayed based
         int newRow = this.row - 1;    //on user's current location
         int value = this.location[newRow][this.col];
         pitOrMonster(value); //method call to display appropriate hint
      }
      if (options.contains("E")) {
         int newCol = this.col + 1;
         int value = this.location[this.row][newCol];
         pitOrMonster(value);
      }
      if (options.contains("S")) {
         int newRow = this.row + 1;
         int value = this.location[newRow][this.col];
         pitOrMonster(value);
      }
      if (options.contains("W")) {
         int newCol = this.col - 1;
         int value = this.location[this.row][newCol];
         pitOrMonster(value);
      }
   }
   
   /**
    * Method that prints out message to tell user that they are near a pit or a monster.
    */
    public void pitOrMonster(int decider) {
       if (decider == 2) { //hint for if user is near a pit
          System.out.println("You feel a breeze.");
       }
       if (decider == 1) { //hint for if user is near a the monster
          System.out.println("You hear a growling noise.");
       }
    }
    
   /**
    * Tell the user their current location and the list of direction that they can go in. The direction
    * method will then be called to receive the user's choice, and using that choice, this method will
    * move the user. Then, it will once again tell the user their current location and the list of 
    * direction they can move in.
    * 
    * @param location
    *             2-D array with coordinate plane of the map.
    * @return Updated 2-D array with the location of user.
    */
   public int[][] locationAndExits(Scanner scnr) {
      this.row = 0;
      this.col = 0;
      System.out.println("You are in location row:" + row + " col:" + col); //tell user current location
      System.out.println("There are exits to the: " + NESW().toString()); //tell user possible directions
      hints(); //method call to give user hint
      System.out.print("Which way do you want to move? "); //prompt user to move
      String choice = direction(scnr); //method call to get the user's choice of direction
      updateCurrentLocation(choice); //method call that updates where the user is on the map
      winOrDie(); //method call to see if user has won or lost
      while (checkMap() == 0) { //loop that keeps game running as long as they haven't won or died
         System.out.println();
         System.out.println("You are in location row:" + row + " col:" + col);
         System.out.println("There are exits to the: " + NESW().toString());
         hints();
         System.out.print("Which way do you want to move? ");
         choice = direction(scnr);
         updateCurrentLocation(choice);
         winOrDie();
      }
      return this.location;
   }
   
   /**
    * Method that determines if user has won due to finding gold, or died due to falling in pit or 
    * encountering ravenous Bugblatter Beast. This is done by comparing the user's current location to
    * the map.
    */
   public void winOrDie() {
      if (checkMap() == 3) { //program ending condition for if user wins
         System.out.println("You have found the gold!");
         System.out.println();
         System.out.println("You have won!  Congratulations!");
      }
      else if (checkMap() == 2) { //program ending condition for if user dies from pit
         System.out.println("AAAARGH! You have fallen into a pit!");
         System.out.println();
         System.out.println("You have died!  Game over!");
      }
      else if (checkMap() == 1) { //program ending condition for if user dies from monster
         System.out.println("Oh no! You have run into a ravenous Bugblatter Beast!");
         System.out.println();
         System.out.println("You have died!  Game over!");
      }
   }
   
}
