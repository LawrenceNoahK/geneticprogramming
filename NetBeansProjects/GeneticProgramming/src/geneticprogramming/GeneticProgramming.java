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
/**
 *
 * @author mark2681
 */
public class GeneticProgramming {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        GPEnvironment myEnvironment = new GPEnvironment();
        
        Properties prop = new Properties();
        String fileName = "src/geneticprogramming/settings.config";
        
        InputStream is = new FileInputStream(fileName);
        prop.load(is);
        
        int populationSize, maxTreeHeight = 0;
        double percentRegeneration = 0;
        populationSize = Integer.parseInt(prop.getProperty("populationSize"));
        maxTreeHeight = Integer.parseInt(prop.getProperty("maxTreeHeight"));
        percentRegeneration = Integer.parseInt(prop.getProperty("percentRegeneration"));
        TreeNode root;
        
        
        ArrayList<TreeNode> list = new ArrayList<TreeNode>(populationSize);
        List<Tree> nextGeneration = new ArrayList<Tree>();   
        TrainingData td = new TrainingData();
              
        myEnvironment.selection(list);
       
        int totalNodes = (int) Math.pow(2,maxTreeHeight);
        int nodeArray[] = new int[(int)Math.pow(2,maxTreeHeight)];
        
        for(int i=0;i<totalNodes;i++){
            nodeArray[i] = i;
        }
             
        TreeNode myRoot = null;
        ArrayList<Tree> arrayOfTrees = new ArrayList<Tree>(populationSize);
        double fitnessScore = 0.0;
        double highestValue=0.0;
        for(int j=0;j<populationSize;j++){
            Tree newTree = new Tree(3,0.0);
            myRoot = newTree.buildTree(nodeArray,1, totalNodes-1);
            arrayOfTrees.add(newTree);
            //Collections.sort(arrayOfTrees);
            newTree.traverseTree(myRoot);
            System.out.println();
            System.out.println(newTree.evaluate(myRoot,1));  
        
            
            for(int k=0;k<7;k++){
                fitnessScore = fitnessScore + Math.abs(td.read(k) - arrayOfTrees.get(j).evaluate(myRoot, k));    
            }
            
   
            arrayOfTrees.get(j).fitnessValue = fitnessScore;
            
            for(int k= 0; k< arrayOfTrees.size();k++){
                System.out.println("The fitness score for " + k + " is: " + arrayOfTrees.get(k).fitnessValue);
            }
            
            double percentage = (double) percentRegeneration;
            int nextGenerationSize = (int) Math.round(populationSize * percentage/100);
            System.out.println("The next generation size is: " + nextGenerationSize);
            if((fitnessScore<highestValue) || (nextGeneration.size() < nextGenerationSize)){
                nextGeneration.add(arrayOfTrees.get(j));
                nextGeneration.get(nextGeneration.size()-1).fitnessValue = fitnessScore;
                Collections.sort(nextGeneration);
                System.out.println("The highest value is now: " + highestValue);    
                    
                if(fitnessScore > highestValue){
                    highestValue = fitnessScore;
                    System.out.println("The highest score is now: " + nextGeneration.get(nextGeneration.size()-1).fitnessValue);            
                }    
                if(nextGeneration.size()>nextGenerationSize){
                         System.out.println("The fitness score is: " + nextGeneration.get(nextGeneration.size()-1).fitnessValue + " removing...");              
                         nextGeneration.remove(nextGeneration.size()-1);
                         fitnessScore = 0.0;                    
                 }
                 else{
                    fitnessScore = 0.0;
                 }
                  fitnessScore = 0.0; 
            }
            else{
                Collections.sort(nextGeneration);
                System.out.println("The fitness score is: " + nextGeneration.get(nextGeneration.size()-1).fitnessValue + " removing...");              
                nextGeneration.remove(nextGeneration.size()-1);
                fitnessScore = 0.0;
                System.out.println("The new highest score is " + nextGeneration.get(nextGeneration.size()-1).fitnessValue);
            }
                    
            Collections.sort(nextGeneration); 
            for (int q = 0; q < nextGeneration.size();q++){
                System.out.println("Next generation location: " + q + " has value: "  + nextGeneration.get(q).fitnessValue );
            }
            }
        //perform selection
     
    }
    
}
