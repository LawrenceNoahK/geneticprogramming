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
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 *
 * @author mark2681
 */
public class GPEnvironment {
   
    //initial variables
    TrainingData td = new TrainingData();
    int populationSize = 0; 
    public int maxTreeHeight = 0;
    double percentRegeneration = 0.0;
    
    //constructor
    public GPEnvironment() {
        
    }
   
    //load settings from settings.config file
    public void loadSettings() throws FileNotFoundException, IOException{
        Properties prop = new Properties();
        String fileName = "src/geneticprogramming/settings.config";
        
        InputStream is = new FileInputStream(fileName);
        prop.load(is);
    
        populationSize = Integer.parseInt(prop.getProperty("populationSize"));
        maxTreeHeight = Integer.parseInt(prop.getProperty("maxTreeHeight"));
        percentRegeneration = Double.parseDouble(prop.getProperty("percentRegeneration"));
        
    }
    
    public ArrayList<Tree> buildInitialGeneration() throws FileNotFoundException{
        
        int totalNodes = (int) Math.pow(2,maxTreeHeight);
        int nodeArray[] = new int[(int)Math.pow(2,maxTreeHeight)];
        
        for(int i=0;i<totalNodes;i++){
            nodeArray[i] = i;
        }
               
        TreeNode myRoot = null;
        ArrayList<Tree> arrayOfTrees = new ArrayList<Tree>(populationSize);
        ArrayList<Tree> nextGeneration = new ArrayList<Tree>();
        
        double fitnessScore = 0.0;
        double highestValue=0.0;
        
        for(int j=0;j<populationSize;j++){
            
            
            Tree newTree = new Tree(maxTreeHeight,0.0);
            myRoot = newTree.buildTree(nodeArray,1, totalNodes-1);
            arrayOfTrees.add(newTree);
            
            newTree.traverseTree(myRoot);
            System.out.println();
            System.out.println(newTree.evaluate(myRoot,1));  
        
            
            for(int k=0;k<td.getLength();k++){
                fitnessScore = fitnessScore + Math.abs(td.getTrainingDataScore(k) - arrayOfTrees.get(j).evaluate(myRoot,  td.getIndex(k)));    
                System.out.println("Training score is: " + td.getTrainingDataScore(k));
                System.out.println("Array score is: " + arrayOfTrees.get(j).evaluate(myRoot, td.getIndex(k)));
                System.out.println("k score is: " + td.getIndex(k));
                System.out.println("The fitness score is: "+ fitnessScore);
            }
            
            //add score to array of trees
            arrayOfTrees.get(j).fitnessValue = fitnessScore;
            
            Double myFitnessValue = arrayOfTrees.get(j).fitnessValue;
            
            if(myFitnessValue.isInfinite() || myFitnessValue.isNaN()){
                 arrayOfTrees.get(j).fitnessValue = Double.POSITIVE_INFINITY;
            }
            //reset the score
            fitnessScore = 0.0;
            
            for(int k= 0; k< arrayOfTrees.size();k++){
                System.out.println("The fitness score for " + k + " is: " + arrayOfTrees.get(k).fitnessValue);
            }
            }
            return arrayOfTrees;    
    }
    
    public ArrayList<Tree> selection(ArrayList<Tree> arrayOfTrees) throws IOException{
           
        Collections.sort(arrayOfTrees); 
        
        int numberOfNewPrograms = (int) (populationSize * percentRegeneration);
        
        //display array before removal
        for (int q = 0; q < arrayOfTrees.size();q++){
            System.out.println("Before removal: " + q + " has value: "  + arrayOfTrees.get(q).fitnessValue );
            
        }
        
        
        System.out.println("Number of new programs: " + numberOfNewPrograms);
        
        //remove the worst fit trees
        System.out.println("Removing the worst fit trees...");
        for(int j=0;j<numberOfNewPrograms;j++){
            arrayOfTrees.remove(arrayOfTrees.size()-1);
            Double myFitnessValue = arrayOfTrees.get(j).fitnessValue;
            
            //remove trees if they are infinite or NaN
            if(myFitnessValue.isInfinite() || myFitnessValue.isNaN()){
                arrayOfTrees.get(j).fitnessValue = Double.POSITIVE_INFINITY;
            }
        }
        
        return arrayOfTrees;
        
    }      
    
     public ArrayList<Tree> addNewPrograms(ArrayList<Tree> arrayOfTrees) throws FileNotFoundException{
        
        //initial variables 
        double fitnessScore = 0.0;
        TreeNode myRoot = null;

        //building array of trees variables
        int totalNodes = (int) Math.pow(2,maxTreeHeight);
        int nodeArray[] = new int[(int)Math.pow(2,maxTreeHeight)];
        int numberOfNewPrograms = (int) (populationSize * percentRegeneration);       
        ArrayList<Tree> newProgramArray = new ArrayList<Tree>(numberOfNewPrograms);     
        totalNodes = (int) Math.pow(2,maxTreeHeight);
        nodeArray = new int[(int)Math.pow(2,maxTreeHeight)];
        
        
        for(int i=0;i<totalNodes;i++){
            nodeArray[i] = i;
        }
        
        //assign nodes for new program trees
        Tree newTree;
        for(int j=arrayOfTrees.size();j<populationSize;j++){
            newTree = new Tree(maxTreeHeight,0.0);    
            newProgramArray.add(newTree);            
        }       
                 
        for (int m=0;m<newProgramArray.size();m++){
            newTree = new Tree(maxTreeHeight,0.0);          
            myRoot = newTree.buildTree(nodeArray,1, totalNodes-1);
            newTree.traverseTree(myRoot);
              //calculate fitness value score
            for(int k=0;k<td.getLength();k++){
                //System.out.println("The length is: " + td.getLength());
                fitnessScore = fitnessScore + Math.abs(td.getTrainingDataScore(k) - newProgramArray.get(m).evaluate(myRoot,  td.getIndex(k)));    
                //System.out.println("Training score is: " + td.getTrainingDataScore(k));
                System.out.println("Array score is: " + newProgramArray.get(m).evaluate(myRoot, td.getIndex(k)));
                System.out.println("The fitness score is: "+ fitnessScore);
            }
              
            //assign fitness values to new program trees
            newProgramArray.get(m).fitnessValue = fitnessScore;
            System.out.println("New program array at " + m + " is: " + newProgramArray.get(m).fitnessValue);
            fitnessScore = 0.0;
        }
        
        System.out.println("Adding new programs to existing array...");
        arrayOfTrees.addAll(newProgramArray);
        
        Collections.sort(arrayOfTrees);
        
         for (int q = 0; q < arrayOfTrees.size();q++){
            System.out.println("Next generation location1: " + q + " has value: "  + arrayOfTrees.get(q).fitnessValue );
        }
        return arrayOfTrees;
    }
   
     public void mutate(Tree t1){
        
        //select random node for mutation
        ArrayList<TreeNode> nodes = t1.getNodes();
        //System.out.println("Nodes are: " + t1.getNodes().get(0).getNodeTypeValue());
             
        int size = nodes.size();
        if(size ==0){
            return;
        }
        
        Random randomGenerator = new Random();
        
        int random = randomGenerator.nextInt(size);
   
        TreeNode randomNode = nodes.get(random);
        String type = randomNode.getNodeTypeValue();
        
        if(type.equals("OPERAND")) {
            randomNode.setNodeValue(t1.generateRandomOperandNode());
        } 
        else if (type.equals("OPERATOR")) {
            randomNode.setNodeValue(t1.generateRandomOperatorNode(4));
        }
        
    }

    //public void crossover(){}
    //public void mutate(){}
   
     public int getPopulationSize(){
        return populationSize;
    }
    public int getMaxTreeHeight(){
        return maxTreeHeight;
    }
    public double getPercentRegeneration(){
        return percentRegeneration;
    }
}
