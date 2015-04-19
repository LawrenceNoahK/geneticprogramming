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
import org.jfree.ui.RefineryUtilities;
/**
 *
 * @author mark2681
 */
public class GeneticProgramming {

    /**
     * @param args the command line arguments
     */
    //main driver class
    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");  
        
        //class variables
        int populationSize = 0; 
        int maxTreeHeight = 0;
        double percentRegeneration = 0;
        double percentMutation = 0;
        
        GPEnvironment myEnvironment = new GPEnvironment(populationSize);
        
        //load settings
        myEnvironment.loadSettings();
       
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
        
        //assign freeChart
        GPInterface demo = new GPInterface("Genetic Programming");
        
        //temporary threshold
        double threshold = 1.0;
        
        int count = 0;
        while(arrayOfTrees.get(0).fitnessValue > threshold){
           if(count> 10000){
               break;
           }
            
            myEnvironment.selection(arrayOfTrees);
            myEnvironment.addNewPrograms(arrayOfTrees);
            ArrayList<Tree> crossoverArray = new ArrayList<Tree>();
            ArrayList<Tree> mutateArray = new ArrayList<Tree>();
            
            System.out.println("Crossing over trees...");
            crossoverArray = myEnvironment.crossoverTrees(arrayOfTrees,maxTreeHeight,td);
            System.out.println("Crossover over complete, adding to existing array...");
            arrayOfTrees.addAll(crossoverArray);
            Collections.sort(arrayOfTrees);
            
            for (int m=0;m<arrayOfTrees.size();m++){
                System.out.println("After crossover: " +   m + " has value: "  + arrayOfTrees.get(m).fitnessValue);
            }
            
            mutateArray = myEnvironment.mutateTrees(arrayOfTrees, percentMutation, td);
            arrayOfTrees.addAll(arrayOfTrees.size(),mutateArray);
            Collections.sort(arrayOfTrees);
             for (int m=0;m<arrayOfTrees.size();m++){
                System.out.println("After mutation: " +   m + " has value: "  + arrayOfTrees.get(m).fitnessValue );
            }
            
            
             for (int m=0;m<arrayOfTrees.size();m++){
                System.out.println("After sort: " +   m + " has value: "  + arrayOfTrees.get(m).fitnessValue );
            }
            mutateArray.clear();
            crossoverArray.clear();
            demo.createDataset(count, arrayOfTrees.get(0).fitnessValue);
            System.out.println("The best fitness value is: " + arrayOfTrees.get(0).fitnessValue);
            System.out.println("The best fitness equation is: " + arrayOfTrees.get(0).getNodes());
            System.out.println("The generation number is: "+ count);
            count++;
        }
        System.out.println("The number of generations is: " + count);
         
        
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
    
}
