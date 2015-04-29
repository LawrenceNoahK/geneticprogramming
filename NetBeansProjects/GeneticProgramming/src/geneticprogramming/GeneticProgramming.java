/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geneticprogramming;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.jfree.ui.RefineryUtilities;
/**
 *
 * @author mark2681
 */
public class GeneticProgramming {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.lang.CloneNotSupportedException
     */
    //main driver class
    public static void main(String[] args) throws FileNotFoundException, IOException, CloneNotSupportedException {
        
        //start time and system properties
        long startTime = System.nanoTime();
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");  
        GPInterface demo = new GPInterface("Genetic Programming");
        
        //class variables
        int populationSize = 0; 
        int maxTreeHeight = 0;
        double percentRegeneration = 0;
        double percentMutation = 0;
        double threshold = 0.0;
        int count = 0;
        long duration = 0;
        double minutes = 0.0;
        
        //create new GP Environment
        GPEnvironment myEnvironment = new GPEnvironment();
        
        //load settings
        //myEnvironment.loadSettings();
       
        //variables obtained from GP environment
        populationSize = myEnvironment.getPopulationSize();
        maxTreeHeight =  myEnvironment.getMaxTreeHeight();
        percentRegeneration = myEnvironment.getPercentRegeneration();
        percentMutation = myEnvironment.getPercentMutation();
        
        //class variables based on settings
        ArrayList<Tree> arrayOfTrees = new ArrayList<Tree>(populationSize);
        TrainingData td = new TrainingData();
              
        //build initial generation
        arrayOfTrees = myEnvironment.buildInitialGeneration();
        
        threshold = 1E-10;
        //while the top fitness value is greater than zero
        while(arrayOfTrees.get(0).fitnessValue > threshold){
            
            //if the count is too large, quit the while loop
            if(duration> 900000){
               System.out.println("The duration is: " + duration);
                break;
           }
            
            //select the fittest programs
            myEnvironment.selection(arrayOfTrees);
            
            //add in the new programs
            myEnvironment.addNewPrograms(arrayOfTrees);
            
            //create new crossover and mutate arrays
            ArrayList<Tree> crossoverArray = new ArrayList<Tree>();
            ArrayList<Tree> mutateArray = new ArrayList<Tree>();
            
            //crossover the trees
            crossoverArray = myEnvironment.crossoverTrees(arrayOfTrees,maxTreeHeight,td);
            arrayOfTrees.addAll(crossoverArray);
            Collections.sort(arrayOfTrees);
                        
            //mutate the array
            mutateArray= myEnvironment.mutateTrees(arrayOfTrees, percentMutation, td);
            arrayOfTrees.addAll(arrayOfTrees.size(),mutateArray);
            Collections.sort(arrayOfTrees);
                      
            for(int i=0;i<arrayOfTrees.size()-1;i++){                    
              
                if(Double.compare(arrayOfTrees.get(i).fitnessValue, arrayOfTrees.get(i+1).fitnessValue) ==0 ){                   
                    arrayOfTrees.remove(i);
                }
                
            }            
            //clear the array and plot the point
            mutateArray.clear();
            crossoverArray.clear();
            demo.createDataset(count, arrayOfTrees.get(0).fitnessValue);
            
            //print out current fitness value and generation number
            System.out.print("The best equation is: ");  
            myEnvironment.printTree(arrayOfTrees.get(0).getRoot());
            System.out.println();
            System.out.println("The best fitness value is: " + arrayOfTrees.get(0).fitnessValue);
            System.out.println("The generation number is: "+ count);
           
            count++;
            
            //current duration
            long endTime = System.nanoTime();
            duration = (endTime - startTime)/1000000;
            minutes = ((double)TimeUnit.MILLISECONDS.toSeconds(duration))/60;
            System.out.println("The duration is: " + minutes + " minutes");
        
        }
        
        
        System.out.print("The final equation is: ");  
        myEnvironment.printTree(arrayOfTrees.get(0).getRoot());
        System.out.println();
        //print out final results
        System.out.println("The final number of generations is: " + count);
        System.out.println("The final duration is: " + minutes + " minutes");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
    
}
