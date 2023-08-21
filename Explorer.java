/**
 * A program that plays a maze game with the user, using the Maze.java class.
 * 
 * @author Isaac Nagle
 * @version 11222022
 *
 */

import java.util.Scanner;

public class Explorer {
   
   /**
    * A program that plays a maze game with the user, using the Maze.java class.
    */
   public static void main(String[] args) {
      Scanner scnr = new Scanner(System.in); //create scanner object
      System.out.print("Enter a map file name: "); //prompt user for file name
      String fileName = scnr.nextLine(); //gets name of file from user
      Maze mazeMap = new Maze(fileName); //creates new maze object using the file name
      mazeMap.locationAndExits(scnr); //executes game
   }
}
