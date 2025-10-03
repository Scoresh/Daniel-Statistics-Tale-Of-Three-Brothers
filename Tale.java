/*
   @author: Daniel Sabalakov
   @date: 10/3/2025
   @purpose: To automate processes used in "A tale of three brothers"
*/

import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.io.FileNotFoundException;
import java.util.ArrayList;

//Class Tale defines the Class
public class Tale{
   //Default Main, throws FileNotFound because I do not want to implement a Try Catch statement
   public static void main(String[] args) throws FileNotFoundException{
      //load map from a .csv file (comma seperated values). This matches a number with a word.
      HashMap<Integer,String> map = loadFromFile(new File("TaleNONStratified.csv"));
      
      //initialize total Words, total Random Generations, and an array to hold all the generated integers.
		int totalWords = countMap(map);
		int totalGeneration = 24;
		int[] generated = genRandomArray(totalGeneration);
      
      //print the array
      printArray(generated);
      
      //seperator between problems
      printSeperator();
      
      
      //print out hashmap (p2)
      for (int i : generated){
         System.out.printf("[Position: %3d, Word: %-10s, Length: %2d]\n",i,map.get(i),map.get(i).length()); 
      }
      
      //seperator between problems
      printSeperator();
      
      //Create a frequency map, and then print it out. 
      HashMap<Integer,Integer> frequencyMap = generateFrequencyMap(map,generated);
      for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
         Integer key = entry.getKey();
         Integer value = entry.getValue();
         System.out.println("Word Length: " + key + ", N: " + value);
      }
	  
     
     //seperator between problems
     printSeperator();
     
     
     //Proportion n length or lower, use custom method to print it out.
     System.out.println("Proportion N or Lower: " + sampleProportion_N_OrLower(4,frequencyMap));
     
     
     //seperator between problems
     printSeperator();
     
     //While I know that I can find strata counts myself, I still would like to seperate the csv via code.
     HashMap<Integer,String>[] mapArray = new HashMap[4];
     mapArray[0] = loadFromFile(new File("Daniel12.csv"));
     mapArray[1] = loadFromFile(new File("Daniel34.csv"));
     mapArray[2] = loadFromFile(new File("Daniel56.csv"));
     mapArray[3] = loadFromFile(new File("Daniel6P.csv"));
     
     double[] counts = new double[4];
     
     for (int i = 0; i < mapArray.length; i++){
         HashMap<Integer,String> z = mapArray[i];
         //should hopefully match up to original prediction. 
         int count = countMap(z);
         //temporarily set percents to count
         counts[i] = count;
         System.out.println("MAP LENGTHS: " + countMap(z));
     }
     System.out.println("\n");
     for (int i = 0; i < counts.length; i++){
         counts[i] = Math.round((double)counts[i] / totalWords * totalGeneration);
     }
     
     printArray(counts);
     
     //seperator between problems
     printSeperator();
     
     
     
	}
   public static int[] generateRandomArray(int totalGeneration, int totalWords){
      
      //Generate all random numbers, while also ensuring there are no duplicates.
		for (int i = 0; i < totalGeneration; i++){
			int z = (int)(Math.random() * totalWords) + 1;
			if (notInArray(generated,z)){generated[i] = z;}
			else {i-=1;}
		}
   }
   
   public static int countMap(HashMap<Integer,String> wordMap){
      Integer key = 0;
      for (Map.Entry<Integer, String> entry : wordMap.entrySet()) {
         key = entry.getKey();
         String value = entry.getValue();
      }
      return key;
   }
   
   //Load from file, generates Hashmap
   public static HashMap<Integer,String> loadFromFile(File f) throws FileNotFoundException{
      HashMap returnMap = new HashMap<Integer,String>();
      Scanner c = new Scanner(f);
      while (c.hasNextLine()){
         String n = c.nextLine();
         String fp = n.substring(0,n.indexOf(","));
         String ep = n.substring(n.indexOf(",")+1);
         returnMap.put(Integer.valueOf(fp),ep);
      }
      return returnMap;
   }
   
   //generates frequency table checks for "existing" key, and if it cannot find it, it creates one and updates it.
   public static HashMap<Integer,Integer> generateFrequencyMap(HashMap<Integer,String> wordMap, int[] generated){
      HashMap<Integer,Integer> frequencyMap = new HashMap<Integer,Integer>();
      //loop through and find
      for (int i : generated){
         String word = wordMap.get(i);
         int wordLength = word.length();
         if (frequencyMap.containsKey(wordLength)){
            frequencyMap.put(wordLength,frequencyMap.get(wordLength)+1);
            
         }
         else {
            frequencyMap.put(wordLength,1);
         }
      }
      return frequencyMap;
      
   }
   
   //prints array of integers
	public static void printArray(int[] array){
		for (int i : array){
			System.out.println("Number: " + i);
		}
	}
   //prints array of integers
	public static void printArray(double[] array){
		for (double i : array){
			System.out.println("Number: " + i);
		}
	}
   
   //checks if an integer i is NOT in array comp
	public static boolean notInArray(int[] comp, int n){
		for (int i : comp){
			if (i == n) {return false;}
			if (i == 0) {return true;}
		}
		return true;
	}

  
  
  
  
   //Calculuates Sample Population Integer N or lower.
   public static double sampleProportion_N_OrLower(int n, HashMap<Integer,Integer> frequencyMap){
      int total = 0;
      int nOrLower = 0;
      for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
         Integer key = entry.getKey();
         Integer value = entry.getValue();
         if (key <= n){
            nOrLower+=value;
         }
         total+=value;
     }
     return (double)nOrLower / total;
   }
   
   //print seperator
   public static void printSeperator(){
      System.out.println("\n\n-----------------------------------------------\n\n");
   }
}