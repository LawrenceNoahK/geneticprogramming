/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geneticprogramming;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import org.jfree.ui.RefineryUtilities;
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
        
        // TODO code application logic here
        GPEnvironment myEnvironment = new GPEnvironment();
        
        //load settings
        myEnvironment.loadSettings();
       
        //variables obtained from GP environment
        populationSize = myEnvironment.getPopulationSize();
        maxTreeHeight =  myEnvironment.getMaxTreeHeight();
        percentRegeneration = myEnvironment.getPercentRegeneration();
        int numberOfNewPrograms = (int) (populationSize * percentRegeneration);

        TreeNode root;
        ArrayList<Tree> arrayOfTrees = new ArrayList<Tree>(populationSize);
        ArrayList<Tree> newProgramArray = new ArrayList<Tree>(numberOfNewPrograms);
        
        ArrayList<TreeNode> list = new ArrayList<TreeNode>(populationSize);
        List<Tree> nextGeneration = new ArrayList<Tree>();   
        TrainingData td = new TrainingData();
              
        //build initial generation
        arrayOfTrees = myEnvironment.buildInitialGeneration();
        
        //assign freeChart
        JFreeChartDemo demo = new JFreeChartDemo("Genetic Programming");
        
        //temporary threshold
        double threshold = 5.0;
        
        int count = 0;
        while(arrayOfTrees.get(0).fitnessValue > threshold){
            myEnvironment.selection(arrayOfTrees);
            myEnvironment.addNewPrograms(arrayOfTrees);
            System.out.println("Mutating...");
            myEnvironment.mutate(arrayOfTrees.get(0));
            demo.createDataset(count, arrayOfTrees.get(0).fitnessValue);
            System.out.println("The best fitness value is: " + arrayOfTrees.get(0).fitnessValue);
            count++;
        }
        System.out.println("The number of generations is: " + count);
         
        
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
    
}
