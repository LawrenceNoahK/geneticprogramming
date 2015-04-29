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
public class GPEnvironment{
   
    //initial variables
    public TrainingData td = new TrainingData();
    int populationSize = 0; 
    public int maxTreeHeight = 0;
    double percentRegeneration = 0.0;
    double percentMutation = 0.0;
    double percentCrossover = 0.0;     
    TreeNode myRoot =null;
    
    //constructor, add initial variables here?
    public GPEnvironment() throws FileNotFoundException, IOException {
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
                Double myEvalScore = arrayOfTrees.get(j).evaluateTree(myRoot,  td.getIndex(k));
                if(myEvalScore.isInfinite() || myEvalScore.isNaN()){
                    fitnessScore = Double.POSITIVE_INFINITY; 
                }
                else{
                    fitnessScore = fitnessScore + Math.abs(td.getTrainingDataScore(k) - arrayOfTrees.get(j).evaluateTree(myRoot,  td.getIndex(k)));    
                }
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
            
            return arrayOfTrees;    
    }
    
    //select fittest trees
    public ArrayList<Tree> selection(ArrayList<Tree> arrayOfTrees) throws IOException{
           
        //sort the array
        Collections.sort(arrayOfTrees); 
        
        //variables for selection
        int numberOfNewPrograms = (int) (populationSize * percentRegeneration);
        int cutdownSize = (int) (arrayOfTrees.size() - populationSize);
        
        //remove worst programs
        for(int j=0;j<numberOfNewPrograms;j++){
            arrayOfTrees.remove(arrayOfTrees.size()-1);
            
            //recalculate fitness value
            Double myFitnessValue = arrayOfTrees.get(j).fitnessValue;
            
            //remove trees if they are infinite or NaN
            if(myFitnessValue.isInfinite() || myFitnessValue.isNaN()){
                arrayOfTrees.get(j).fitnessValue = Double.POSITIVE_INFINITY;
            }
        }
        //cutdown population size further if we have 
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
                Double myEvalScore = newProgramArray.get(newProgramArray.size()-1).evaluateTree(myRoot,  td.getIndex(k));
                if(myEvalScore.isInfinite() || myEvalScore.isNaN()){
                    fitnessScore = Double.POSITIVE_INFINITY; 
                }
                else{
                    fitnessScore = fitnessScore + Math.abs(td.getTrainingDataScore(k) - newProgramArray.get(newProgramArray.size()-1).evaluateTree(myRoot,  td.getIndex(k)));    
                }
            }
            newProgramArray.get(newProgramArray.size()-1).fitnessValue = fitnessScore;
            fitnessScore = 0.0;
        }       
        
         for(int j=0;j<newProgramArray.size();j++){
            Double myFitnessValue = newProgramArray.get(j).fitnessValue;
            
            //remove trees if they are infinite or NaN
            if(myFitnessValue.isInfinite() || myFitnessValue.isNaN()){
                newProgramArray.get(j).fitnessValue = Double.POSITIVE_INFINITY;
            }
        }
        
        arrayOfTrees.addAll(newProgramArray);
        
        //sort array of trees
        Collections.sort(arrayOfTrees);
        
        return arrayOfTrees;
    }
   
    //mutate tree
    public Tree mutate(Tree myTree) throws FileNotFoundException, CloneNotSupportedException{
        
        //select random node for mutation
        int totalNodes = (int) Math.pow(2,maxTreeHeight)-1;
        ArrayList<TreeNode> nodes = myTree.getNodes();
        Tree myTree1 = (Tree) myTree.clone();
        ArrayList<TreeNode> myClone = new ArrayList<TreeNode>();
        
        for(TreeNode p : nodes){
            myClone.add((p.clone()));
       }
        myTree1.getNodes().clear();
        for(int i=0;i<myClone.size();i++){
            myTree1.getNodes().add(myClone.get(i));
        }
        
        int size = nodes.size();
        
        if(size ==0){
            return myTree;
        }
        
        Random randomGenerator = new Random();
        
        int random = randomGenerator.nextInt(size);
   
        TreeNode randomNode = myClone.get(random);
        String type = randomNode.getNodeTypeValue();
       
        myRoot = myTree1.fillTree(myClone,0, totalNodes-1);
        myTree1.setRoot(myRoot);
        myTree1.myRoot = myRoot;
        if(type.equals("OPERAND")) {
            
            char myOperand = myTree1.generateRandomOperandTreeNode();
            while(randomNode.data == myOperand){
                myOperand = myTree1.generateRandomOperandTreeNode();
            }
            myTree1.getNodes().get(random).setNodeValue(myOperand);
            myTree1.getNodes().get(random).data = myOperand;
            
        } 
        else if (type.equals("OPERATOR")) {
            
            char myOperator = myTree1.generateRandomOperatorTreeNode();
            while(randomNode.data == myOperator){ 
                myOperator = myTree1.generateRandomOperatorTreeNode();
            }
            
            myTree1.getNodes().get(random).setNodeValue(myOperator);       
            myTree1.getNodes().get(random).data = myOperator;
        }
     
        //calculate myroot
        myRoot = myTree1.fillTree(myTree1.getNodes(),0, totalNodes-1);
         
        //adding myRoot to existing tree
        myTree1.setRoot(myRoot);        
        myTree1.fitnessValue = myTree1.calculateScore(myTree1,myRoot);
        
        //calculate new score
        Double myFitnessValue = myTree1.fitnessValue;

        if(myFitnessValue.isInfinite() || myFitnessValue.isNaN()){
            myTree1.fitnessValue = Double.POSITIVE_INFINITY;
        }
     
        return myTree1;
    }
        
    //mutate percentage of population
    public ArrayList<Tree> mutateTrees(ArrayList<Tree> arrayOfTrees, double percentMutation,TrainingData td) throws FileNotFoundException, CloneNotSupportedException{
         
        //initialize random number
        Random rNumber = new Random();
              
        //variables for mutation
        int size = arrayOfTrees.size();
        int numberToMutate = (int) (percentMutation * size);
        
        ArrayList<Tree> mutateArray = new ArrayList<Tree>(numberToMutate);
        
        
        for(int i=0;i<numberToMutate;i++){
            size = arrayOfTrees.size();
            numberToMutate = (int) (percentMutation * size);
            
            int randomInt = rNumber.nextInt(size);
            
            Tree tree = arrayOfTrees.get(randomInt);
            Tree myTree1 = (Tree) tree.clone();
   
            Tree myTree2 = new Tree(myRoot,3,100);
    
            myTree2 = mutate(myTree1);
            
            int totalNodes = (int) Math.pow(2,maxTreeHeight)-1;
            
            myRoot = myTree2.fillTree(myTree2.getNodes(),0, totalNodes-1);
            
            myTree2.myRoot = myRoot;          
            
            myTree2.fitnessValue = myTree2.calculateScore(myTree2,myRoot);
                       
            //calculate new score
            Double myFitnessValue = myTree2.fitnessValue;

            if(myFitnessValue.isInfinite() || myFitnessValue.isNaN()){
                myTree2.fitnessValue = Double.POSITIVE_INFINITY;
            }
            
                if((randomInt != 0) && (randomInt !=1)){
                    arrayOfTrees.remove(randomInt);
                }
               
                mutateArray.add(myTree2);
            
        }
        
        return mutateArray;       
    }
      
    //crossover two trees
    public Tree crossover(Tree tree1, Tree tree2) throws FileNotFoundException, CloneNotSupportedException {
        
        //initial variables
        TreeNode myRoot = null;
        int totalNodes = (int) Math.pow(2,maxTreeHeight)-1;
        Tree newTree = new Tree(myRoot,maxTreeHeight,0.0); 
        
        //cloning trees
        Tree myTree1 = (Tree) tree1.clone();
        Tree myTree2 = (Tree) tree2.clone();  
           
        ArrayList<TreeNode> myClone = new ArrayList<TreeNode>();
        ArrayList<TreeNode> myClone2 = new ArrayList<TreeNode>();
        
        ArrayList<TreeNode> nodes = myTree1.getNodes();
        ArrayList<TreeNode> nodes2 = myTree2.getNodes();
        
        for(TreeNode p : nodes){
            myClone.add((p.clone()));
       }
        for(TreeNode p : nodes2){
            myClone2.add((p.clone()));
       }
        myTree1.getNodes().clear();
        myTree2.getNodes().clear();
        for(int i=0;i<myClone.size();i++){
            myTree1.getNodes().add(myClone.get(i));
        }
        for(int i=0;i<myClone2.size();i++){
            myTree2.getNodes().add(myClone2.get(i));
        }
        //set crossover point       
        int crossoverNode = (int) (Math.random()*myTree1.getNodes().size());
                
        //don't allow top value to be crossed over
        while(crossoverNode ==0){
            crossoverNode = (int) (Math.random()*myTree1.getNodes().size());
        }
            
        //for loop for nodes
        for (int i=0;i<myTree1.getNodes().size();i++){           
            
            
            if (i < crossoverNode){                          
                newTree.getNodes().add(myTree1.getNodes().get(i));                                
            }           
            else{
                newTree.getNodes().add(myTree2.getNodes().get(i));                    
            }                  
        }
          
        //fill in new root for new tree
        myRoot = newTree.fillTree(newTree.getNodes(),0, totalNodes-1);
                    
        //adding myRoot to existing tree           
        newTree.setRoot(myRoot);
                
        //calculate new value of fitness tree
        newTree.fitnessValue = newTree.calculateScore(newTree,myRoot);
          
             
        //calculate new score            
        Double myFitnessValue = newTree.fitnessValue;
               
        //check for infinity or NaN
        if(!isValidNumber(myFitnessValue)){
            newTree.fitnessValue = Double.POSITIVE_INFINITY; 
        }
        
        //return new tree
        return newTree;
    }
    
    //crossover selected percentage of trees
    public ArrayList<Tree> crossoverTrees(ArrayList<Tree> newTrees, int newMaxTreeHeight, TrainingData td) throws FileNotFoundException, CloneNotSupportedException{
              
        ArrayList<Tree> crossoverArray = new ArrayList<Tree>();
        int size = newTrees.size();
       
        // calculate the number of crossovers to perform
        int crossoverSize = (int) (percentCrossover * size);
       
        for (int i=0;i<crossoverSize;i++) {
            int rNumber = (int) (Math.random()*newTrees.size());
            Tree crossoverTree = crossover(newTrees.get(rNumber),newTrees.get(i));
            Tree crossoverTree2 = crossover(newTrees.get(i),newTrees.get(rNumber));
          
            while ((rNumber == i) || newTrees.get(rNumber).fitnessValue == newTrees.get(i).fitnessValue
                    ||crossoverTree.fitnessValue == crossoverTree2.fitnessValue){
                rNumber = (int) (Math.random()*newTrees.size());
                crossoverTree = crossover(newTrees.get(rNumber),newTrees.get(i));
                crossoverTree2 = crossover(newTrees.get(i),newTrees.get(rNumber));
            }
            
            if((crossoverTree.fitnessValue != crossoverTree2.fitnessValue)){
                crossoverArray.add(crossoverTree);  
                crossoverArray.add(crossoverTree2);
            }
            
        }
        return crossoverArray;
}

    //print out expression result
    public void printTree(TreeNode myRoot) {
        
        if (myRoot.isLeaf()) {
            System.out.print(myRoot.data);

        } 
        else {
            System.out.print("(");
            printTree(myRoot.left);
            System.out.print(" " + myRoot.data + " ");
            printTree(myRoot.right);
            System.out.print(")");
        } 
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
    
    //checks to see if result is valid number
    public boolean isValidNumber(Double myValue){
        if(myValue.isInfinite()||myValue.isNaN()){
            return false;
        }
       else{
            return true;
        }
   }
   

}
