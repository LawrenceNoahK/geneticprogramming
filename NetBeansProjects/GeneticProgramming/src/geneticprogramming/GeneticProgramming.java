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

/**
 *
 * @author mark2681
 */
public class GeneticProgramming {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
         
        int populationSize = 0; 
        int maxTreeHeight = 0;
        double percentRegeneration = 0;
        
        ArrayList<Tree> nextGeneration = new ArrayList<Tree>();   
         
        //load new environment
        GPEnvironment myEnvironment = new GPEnvironment();
        
        //load settings
        myEnvironment.loadSettings();
       
        //variables obtained from GP environment
        populationSize = myEnvironment.getPopulationSize();
        ArrayList<Tree> arrayOfTrees = new ArrayList<Tree>(populationSize);
        
        maxTreeHeight =  myEnvironment.getMaxTreeHeight();
        percentRegeneration = myEnvironment.getPercentRegeneration();
        
        //build initial generation
        arrayOfTrees = myEnvironment.buildInitialGeneration();
        
        //System.out.println("Array of Trees: " + arrayOfTrees.get(0).fitnessValue);
        //while(fitnessValue > threshold){
            //select trees for next generation
        for(int i = 0;i<3;i++){
            System.out.println("Selecting best fit trees...");
            myEnvironment.selection(arrayOfTrees);
            System.out.println("Adding new programs...");
            myEnvironment.addNewPrograms(arrayOfTrees);
            //arrayOfTrees.clear();
            //arrayOfTrees = myEnvironment.buildInitialGeneration();
        }
            //myEnvironment.crossover()
            //myEnvironment.mutate()
            //System.out.println("The current fitness value is: " + fitnessValue);
        //}
        //myEnvironment.sendToConsole
    }
    
}
