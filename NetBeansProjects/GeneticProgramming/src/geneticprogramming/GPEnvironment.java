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
import java.util.Properties;
import java.util.Random;

/**
 *
 * @author mark2681
 */
public class GPEnvironment {
   
    //initial variables
    private int populationSize = 0; 
    private int maxTreeHeight = 0;
    private double percentRegeneration = 0;
    private TreeNode myRoot = null;
    private int totalNodes = 0;
    private int nodeArray[];
    private ArrayList<Tree> arrayOfTrees = new ArrayList<Tree>(populationSize);
    private Tree newTree;
    TrainingData td = new TrainingData(); 
        
    
    //constructor
    public GPEnvironment() {
        
    }    
    
    //load settings from settings.config
    public void loadSettings() throws FileNotFoundException, IOException{
        Properties prop = new Properties();
        String fileName = "src/geneticprogramming/settings.config";
        
        InputStream is = new FileInputStream(fileName);
        prop.load(is);
    
        populationSize = Integer.parseInt(prop.getProperty("populationSize"));
        maxTreeHeight = Integer.parseInt(prop.getProperty("maxTreeHeight"));
        percentRegeneration = Double.parseDouble(prop.getProperty("percentRegeneration"));
        
    }
    
    //building initial generation
    public ArrayList<Tree> buildInitialGeneration() throws FileNotFoundException{
        
        double fitnessScore = 0.0;
        
        totalNodes = (int) Math.pow(2,maxTreeHeight);
        nodeArray = new int[(int)Math.pow(2,maxTreeHeight)];
        
        for(int i=0;i<totalNodes;i++){
            nodeArray[i] = i;
        }
        
        for(int j=0;j<populationSize;j++){
            
            //fix this
            newTree = new Tree(3,0.0);
            
            myRoot = newTree.buildTree(nodeArray,1, totalNodes-1);
            arrayOfTrees.add(newTree);
            newTree.traverseTree(myRoot);
            System.out.println();
            System.out.println(newTree.evaluateTree(myRoot,1));  
        
            //Need to fix training data length
            for(int k=0;k<td.getLength();k++){
                fitnessScore = fitnessScore + Math.abs(td.getTrainingDataScore(k) - arrayOfTrees.get(j).evaluateTree(myRoot, k));    
            }
            for(int k= 0; k< arrayOfTrees.size();k++){
                //System.out.println("The fitness score for " + k + " is: " + arrayOfTrees.get(k).fitnessValue);
            }  
            arrayOfTrees.get(j).fitnessValue = fitnessScore;
        }
        return arrayOfTrees;
    }
    
    //selection of fittest programs
    public ArrayList<Tree> selection(ArrayList<Tree> arrayOfTrees) throws IOException{
           
        //td = new TrainingData(); 
        //ArrayList<Tree> nextGeneration = new ArrayList<Tree>();
        TreeNode myRoot = null;
        
        //fix this
        int treeRemoval = (int) (populationSize / 2);
          
        //add to the next generation
        //for(int j=0;j<populationSize;j++){
            //nextGeneration.add(arrayOfTrees.get(j));
            Collections.sort(arrayOfTrees); 
        //}
        
        System.out.println("The percent regeneration is: " + treeRemoval);
        
        //remove the worst fit trees
        System.out.println("Removing the worst fit trees...");
        for(int j=0;j<3;j++){
            arrayOfTrees.remove(arrayOfTrees.size()-1);
        }
        
        //print out the next generation
        for (int q = 0; q < arrayOfTrees.size();q++){
            System.out.println("Next generation location: " + q + " has value: "  + arrayOfTrees.get(q).fitnessValue );
        }
        return arrayOfTrees;
        
    }
    
    //add new programs after unfit programs have been removed
    public ArrayList<Tree> addNewPrograms(ArrayList<Tree> arrayOfTrees) throws FileNotFoundException{
        
        double fitnessScore = 0.0;
        //TrainingData td = new TrainingData(); 
        
        //fix this
        int currentArraySize = arrayOfTrees.size() - 5;
        ArrayList<Tree> additionalTrees;
        
        totalNodes = (int) Math.pow(2,maxTreeHeight);
        nodeArray = new int[(int)Math.pow(2,maxTreeHeight)];
        
        for(int i=0;i<totalNodes;i++){
            nodeArray[i] = i;
        }
        /*
        for(int j=arrayOfTrees.size();j<arrayOfTrees.size()+2;j++){
            
            
            newTree = new Tree(maxTreeHeight,0.0);
            
            myRoot = newTree.buildTree(nodeArray,1, totalNodes-1);
            
        
            //Need to fix training data length
            for(int k=0;k<td.getLength();k++){
                fitnessScore = fitnessScore + Math.abs(td.getTrainingDataScore(k) - arrayOfTrees.get(j).evaluateTree(myRoot, k));    
            }
              
            arrayOfTrees.get(j).fitnessValue = fitnessScore;
        }*/
        return arrayOfTrees;
    }
    /*
    public void mutate(Tree t1){
        //select random node for mutation
        //ArrayList<TreeNode> nodes = t1.(int) 
        int size = (int) Math.pow(2,maxTreeHeight);
        
        Random randomGenerator = new Random();
        
        int random = randomGenerator.nextInt(size);
        TreeNode randomNode = nodes.get(random);
        String type = randomNode.getNodeTypeValue();
        
        if (type.equals("OPERAND")) {
            randomNode.setNodeValue(t1.generateRandomValueNode());
        } 
        else if (type.equals("OPERATOR")) {
            randomNode.setNodeValue(t1.generateRandomRootNode());
        }
        
    }
    */
    public int getPopulationSize(){
        return populationSize;
    }
    public int getMaxTreeHeight(){
        return maxTreeHeight;
    }
    public double getPercentRegeneration(){
        return percentRegeneration;
    }
    public void buildNodes(){
        
    }
   
}
