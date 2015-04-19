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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;
/**
 *
 * @author mark2681
 */
public class GPEnvironment{
   
    //initial variables
    public TrainingData td = new TrainingData();
    int populationSize = 0; 
    public int maxTreeHeight = 0;
    double percentRegeneration = 0.0;
    double percentMutation = 0.0;
    double percentCrossover = 0.0;
    private ArrayList<Tree> arrayOfTreesClone;
    
    //constructor, add initial variables here?
    public GPEnvironment(int population) {
        population = populationSize;
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
        percentMutation = Double.parseDouble(prop.getProperty("percentMutation"));
        percentCrossover = Double.parseDouble(prop.getProperty("percentCrossover"));
    }
    
    //build first generation of trees
    public ArrayList<Tree> buildInitialGeneration() throws FileNotFoundException{
        
        int totalNodes = (int) Math.pow(2,maxTreeHeight);
        int nodeArray[] = new int[(int)Math.pow(2,maxTreeHeight)];
        TreeNode myRoot = null;
        ArrayList<Tree> arrayOfTrees = new ArrayList<Tree>(populationSize);      
        double fitnessScore = 0.0;
        
        
        //fill in node array
        for(int i=0;i<totalNodes;i++){
            nodeArray[i] = i;
        }
               
        for(int j=0;j<populationSize;j++){
            
            Tree newTree = new Tree(myRoot,maxTreeHeight,0.0);
            myRoot = newTree.buildTree(nodeArray,1, totalNodes-1);
            newTree.traverseTree(myRoot);
            arrayOfTrees.add(newTree);
            
            //assign fitness score
            for(int k=0;k<td.getLength();k++){
                fitnessScore = fitnessScore + Math.abs(td.getTrainingDataScore(k) - arrayOfTrees.get(j).evaluateTree(myRoot,  td.getIndex(k)));    
            }
            
            //add score to array of trees
            arrayOfTrees.get(j).fitnessValue = fitnessScore;
            arrayOfTrees.get(j).setRoot(myRoot);
            Double myFitnessValue = arrayOfTrees.get(j).fitnessValue;
            
            //check for infinity or NaN
            if(myFitnessValue.isInfinite() || myFitnessValue.isNaN()){
                 arrayOfTrees.get(j).fitnessValue = Double.POSITIVE_INFINITY;
            }
            
            //reset the score
            fitnessScore = 0.0;
        }
           for(int k= 0; k< arrayOfTrees.size();k++){
                System.out.println("The fitness score for " + k + " is: " + arrayOfTrees.get(k).fitnessValue);
               
                for (int m = 0;m < arrayOfTrees.get(k).getNodes().size();m++){
                    System.out.print(arrayOfTrees.get(k).getNodes().get(m).getNodeValue());
                }
                System.out.println();
           }
        
            return arrayOfTrees;    
    }
    
    //select fittest trees
    public ArrayList<Tree> selection(ArrayList<Tree> arrayOfTrees) throws IOException{
           
        Collections.sort(arrayOfTrees); 
        
        int numberOfNewPrograms = (int) (populationSize * percentRegeneration);
        int cutdownSize = (int) (arrayOfTrees.size() - populationSize);
        
        //display array before removal
        for (int q = 0; q < arrayOfTrees.size();q++){
            System.out.println("Selection: " + q + " has value: "  + arrayOfTrees.get(q).fitnessValue );
            
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
        if(cutdownSize>0){
            for(int j=0;j<cutdownSize;j++){
                arrayOfTrees.remove(arrayOfTrees.size()-1);
            }
        }
        
        return arrayOfTrees;
        
    }      
    
    //add new programs after selection
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
            newTree = new Tree(myRoot,maxTreeHeight,0.0);    
            myRoot = newTree.buildTree(nodeArray,1, totalNodes-1);
            newTree.traverseTree(myRoot);
            newProgramArray.add(newTree);       
            newProgramArray.get(newProgramArray.size()-1).setRoot(myRoot);
            
            for(int k=0;k<td.getLength();k++){
                fitnessScore = fitnessScore + Math.abs(td.getTrainingDataScore(k) - newProgramArray.get(newProgramArray.size()-1).evaluateTree(myRoot,  td.getIndex(k)));    
            }
            newProgramArray.get(newProgramArray.size()-1).fitnessValue = fitnessScore;
            fitnessScore = 0.0;
        }       
        
        for (int m=0;m<newProgramArray.size();m++){
            System.out.println("New program array at " + m + " is: " + newProgramArray.get(m).fitnessValue);
        }
        
        arrayOfTrees.addAll(newProgramArray);
        
        //sort array of trees
        Collections.sort(arrayOfTrees);
        
         for (int q = 0; q < arrayOfTrees.size();q++){
            System.out.println("Next generation location1: " + q + " has value: "  + arrayOfTrees.get(q).fitnessValue );
        }
        return arrayOfTrees;
    }
   
    //mutate tree
    public Tree mutate(Tree myTree) throws FileNotFoundException{
        
        //select random node for mutation
        int totalNodes = (int) Math.pow(2,maxTreeHeight)-1;
        ArrayList<TreeNode> nodes = myTree.getNodes();
        TreeNode myRoot =null;     
        int size = nodes.size();
        
        if(size ==0){
            return myTree;
        }
        
        Random randomGenerator = new Random();
        
        int random = randomGenerator.nextInt(size);
   
        TreeNode randomNode = nodes.get(random);
        String type = randomNode.getNodeTypeValue();
        
        if(type.equals("OPERAND")) {
            myTree.getNodes().get(random).setNodeValue(myTree.generateRandomOperandTreeNode());
        } 
        else if (type.equals("OPERATOR")) {
            myTree.getNodes().get(random).setNodeValue(myTree.generateRandomOperatorTreeNode());       
        }
              
        //calculate myroot
        myRoot = myTree.fillTree(myTree.getNodes(),0, totalNodes-1);
        
        //adding myRoot to existing tree
        myTree.setRoot(myRoot);
        
        //calculate new fitness value
        myTree.fitnessValue = myTree.calculateScore(myTree,myRoot,maxTreeHeight);
        
        //calculate new score
        Double myFitnessValue = myTree.fitnessValue;

        if(myFitnessValue.isInfinite() || myFitnessValue.isNaN()){
            myTree.fitnessValue = Double.POSITIVE_INFINITY;
        }
        return myTree;
    }
        
    //mutate percentage of population
    public ArrayList<Tree> mutateTrees(ArrayList<Tree> arrayOfTrees, double percentMutation,TrainingData td) throws FileNotFoundException{
         
        //initialize random number
        Random rNumber = new Random();
              
        //variables for mutation
        int size = arrayOfTrees.size();
        int numberToMutate = (int) (percentMutation * size);
        
        ArrayList<Tree> mutateArray = new ArrayList<Tree>(numberToMutate);
        ArrayList<Tree> copy = new ArrayList<Tree>(arrayOfTrees);
        
        
        for(int i=0;i<numberToMutate;i++){
            int randomInt = rNumber.nextInt(size);
            
            Tree tree = arrayOfTrees.get(randomInt);
            //Tree tree = copy.get(randomInt);
            
            //print out selected tree
            //Tree newTree = mutate(tree);
            mutate(tree);
            //add new tree to mutateArray
            //mutateArray.add(newTree);
            
        }
        
        return mutateArray;       
    }
    //crossover two trees
    public Tree crossover(Tree tree1, Tree tree2) throws FileNotFoundException {
        
        TreeNode myRoot = null;
        int totalNodes = (int) Math.pow(2,maxTreeHeight)-1;
        Tree newTree = new Tree(myRoot,maxTreeHeight,0.0); 
               
        //set crossover point       
        int crossoverPoint = (int) (Math.random()*tree1.getNodes().size());
        System.out.println("The crosspoint is: " + crossoverPoint);
            for (int i=0;i<tree1.getNodes().size();i++){           
                 if (i < crossoverPoint){
                    newTree.getNodes().add(tree1.getNodes().get(i));                  
                 }
                else{
                    newTree.getNodes().add(tree2.getNodes().get(i));               
                 }            
            }
            
            myRoot = newTree.fillTree(newTree.getNodes(),0, totalNodes-1);
        
            //adding myRoot to existing tree
            newTree.setRoot(myRoot);
               
            newTree.fitnessValue = newTree.calculateScore(newTree,myRoot,maxTreeHeight);
          
             //calculate new score
            Double myFitnessValue = newTree.fitnessValue;
            
            if(myFitnessValue.isInfinite() || myFitnessValue.isNaN()){          
                newTree.fitnessValue = Double.POSITIVE_INFINITY;
            }
        
            return newTree;
    }
    
    //crossover selected percentage of trees
    public ArrayList<Tree> crossoverTrees(ArrayList<Tree> newTrees, int newMaxTreeHeight, TrainingData td) throws FileNotFoundException{
        
        
        ArrayList<Tree> crossoverArray = new ArrayList<Tree>();
        int size = newTrees.size();
       
        // calculate the number of crossovers to perform
        int crossoverSize = (int) (percentCrossover * size);
       
        for (int i=0;i<crossoverSize;i++) {
            
            Tree crossoverTree = crossover(newTrees.get(i),newTrees.get(i+1));
            crossoverArray.add(crossoverTree);
        }
        return crossoverArray;
}

    //get population size
    public int getPopulationSize(){
        return populationSize;
    }
    
    //get max tree height
    public int getMaxTreeHeight(){
        return maxTreeHeight;
    }
    
    //get percentage of regenerated trees
    public double getPercentRegeneration(){
        return percentRegeneration;
    }
    
    //get percentage of mutated trees
    public double getPercentMutation(){
        return percentMutation;
    }
    
    //get percentage of crossover trees
    public double getPercentCrossover(){
        return percentCrossover;
    }
    
}
