/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geneticprogramming;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
     TreeNode myRoot =null;
    
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
        //for (int q = 0; q < arrayOfTrees.size();q++){
        //    System.out.println("Selection: " + q + " has value: "  + arrayOfTrees.get(q).fitnessValue );
            
        //}
               
        //System.out.println("Number of new programs: " + numberOfNewPrograms);
        
        //remove the worst fit trees
        //System.out.println("Removing the worst fit trees...");
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
        //for (int m=0;m<newProgramArray.size();m++){
        //    System.out.println("New program array at " + m + " is: " + newProgramArray.get(m).fitnessValue);
        //}
        
        arrayOfTrees.addAll(newProgramArray);
        
        //sort array of trees
        Collections.sort(arrayOfTrees);
        
         //for (int q = 0; q < arrayOfTrees.size();q++){
         //   System.out.println("Next generation location1: " + q + " has value: "  + arrayOfTrees.get(q).fitnessValue );
        //}
        return arrayOfTrees;
    }
   
    //mutate tree
    public Tree mutate(Tree myTree) throws FileNotFoundException, CloneNotSupportedException{
        
        //select random node for mutation
        int totalNodes = (int) Math.pow(2,maxTreeHeight)-1;
        ArrayList<TreeNode> nodes = myTree.getNodes();
        Tree myTree1 = (Tree) myTree.clone();
        ArrayList<TreeNode> nodes1 = myTree1.getNodes();
        //TreeNode myRoot =null;     
        int size = nodes1.size();
        
        if(size ==0){
            return myTree;
        }
        
        Random randomGenerator = new Random();
        
        int random = randomGenerator.nextInt(size);
   
        TreeNode randomNode = nodes1.get(random);
        String type = randomNode.getNodeTypeValue();
        //System.out.println("Before mutation for node: " + random + " is " + myTree1.getNodes());
        //System.out.println("Before mutation score for node: "  + random + " is " + myTree1.fitnessValue);
        myRoot = myTree1.fillTree(myTree1.getNodes(),0, totalNodes-1);
        //printExpression(myRoot);
        //System.out.println();
        if(type.equals("OPERAND")) {
            char myOperand = myTree1.generateRandomOperandTreeNode();
            myTree1.getNodes().get(random).setNodeValue(myOperand);
            myTree1.getNodes().get(random).data = myOperand;
            
             //myTree.getNodes().set(random, myTree.getNodes().get(random));
        } 
        else if (type.equals("OPERATOR")) {
            char myOperator = myTree1.generateRandomOperatorTreeNode();
            myTree1.getNodes().get(random).setNodeValue(myOperator);       
            myTree1.getNodes().get(random).data = myOperator;
            //myTree.getNodes().set(random, myTree.getNodes().get(random));
        }
        //System.out.println("get nodes before mutate: " + myTree1.getNodes());
        //calculate myroot
        myRoot = myTree1.fillTree(myTree1.getNodes(),0, totalNodes-1);
        //printExpression(myRoot);
        //System.out.println();
        for(int i=0;i<myTree.getNodes().size();i++){
            myTree1.getNodes().get(i).data = myTree.getNodes().get(i).data;
        }
        
       // myFitnessScore = myFitnessScore + Math.abs(td.getTrainingDataScore(k) - arrayOfTrees.get(0).evaluateTree(myRoot,  td.getIndex(k)));
        //myRoot = arrayOfTrees.get(0).fillTree(arrayOfTrees.get(0).getNodes(),0, totalNodes-1);
        //adding myRoot to existing tree
        myTree1.setRoot(myRoot);        
        myTree1.fitnessValue = myTree.calculateScore(myTree,myRoot,maxTreeHeight);
        //System.out.println("Mutate: The fitness score is: " + myTree1.fitnessValue);
        
        //calculate new score
        Double myFitnessValue = myTree1.fitnessValue;

        if(myFitnessValue.isInfinite() || myFitnessValue.isNaN()){
            myTree1.fitnessValue = Double.POSITIVE_INFINITY;
        }
        
        //System.out.println("After mutation: " + myTree.getNodes());
        //System.out.println("After mutation score: " + myTree.fitnessValue);
        //System.out.println("get nodes after mutate: " + myTree1.getNodes());  
        //printExpression(myRoot);
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
        //ArrayList<Tree> copy = new ArrayList<Tree>(arrayOfTrees);
        
        
        for(int i=0;i<numberToMutate;i++){
            size = arrayOfTrees.size();
            numberToMutate = (int) (percentMutation * size);
            
            int randomInt = rNumber.nextInt(size);
            
            //while (randomInt == 0){
            //    randomInt = rNumber.nextInt(size);
            //}
            //System.out.println("mutating: " + randomInt);
            Tree tree = arrayOfTrees.get(randomInt);
            Tree myTree1 = (Tree) tree.clone();
            //Tree tree = copy.get(randomInt);
            System.out.println("The fitness value 1 is: " + myTree1.fitnessValue);
            //print out selected tree
            //Tree newTree = mutate(tree);      
            //arrayOfTrees.remove(randomInt);
            //System.out.println("Removing tree number: " +randomInt);
            double fitnessValue1 =  myTree1.fitnessValue;
            double fitnessValue2 = Double.POSITIVE_INFINITY;
            Tree myTree2 = new Tree (myRoot,3,100);
            //while(fitnessValue2 >= fitnessValue1){
                
                //randomInt = rNumber.nextInt(size);
                //tree = arrayOfTrees.get(randomInt);
                //myTree1 = (Tree) tree.clone();
                myTree2 = mutate(tree);
                int totalNodes = (int) Math.pow(2,maxTreeHeight)-1;
                myRoot = myTree2.fillTree(myTree2.getNodes(),0, totalNodes-1);
                myTree2.myRoot = myRoot;          
                myTree2.fitnessValue = myTree2.calculateScore(myTree2,myRoot,maxTreeHeight);
                fitnessValue2 = myTree2.fitnessValue;
                //System.out.println("The fitness value 1 is: " + fitnessValue1);
                System.out.println("The fitness value 2 is: " + fitnessValue2);
            //}
            //}
            //calculate new score
            Double myFitnessValue = myTree2.fitnessValue;

            if(myFitnessValue.isInfinite() || myFitnessValue.isNaN()){
                myTree2.fitnessValue = Double.POSITIVE_INFINITY;
            }
            arrayOfTrees.remove(randomInt);
            mutateArray.add(myTree2);
            
            
        }
        
        return mutateArray;       
    }
    public Tree crossover2(Tree tree1, Tree tree2) throws FileNotFoundException, CloneNotSupportedException {
        
        TreeNode myRoot = null;
        int totalNodes = (int) Math.pow(2,maxTreeHeight)-1;
        Tree newTree = new Tree(myRoot,maxTreeHeight,0.0); 
        
        Tree myTree1 = (Tree) tree1.clone();
        Tree myTree2 = (Tree) tree2.clone();  
          
        //set crossover point       
        int crossoverPoint = (int) (Math.random()*tree1.getNodes().size());
        while(crossoverPoint ==0){
            crossoverPoint = (int) (Math.random()*tree1.getNodes().size());
        }
        //System.out.println("The crosspoint is: " + crossoverPoint);
            for (int i=0;i<myTree1.getNodes().size();i++){  
                //newTree.getNodes().set(i, myRoot)
               
                 if (i < crossoverPoint){
                    
                    newTree.getNodes().add(myTree1.getNodes().get(i));                  
                 }
                else{
                    newTree.getNodes().add(myTree2.getNodes().get(i));               
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
    
    //crossover two trees
    public Tree crossover(Tree tree1, Tree tree2) throws FileNotFoundException, CloneNotSupportedException {
        
        TreeNode myRoot = null;
        int totalNodes = (int) Math.pow(2,maxTreeHeight)-1;
        Tree newTree = new Tree(myRoot,maxTreeHeight,0.0); 
        
        Tree myTree1 = (Tree) tree1.clone();
        Tree myTree2 = (Tree) tree2.clone();  
          
        
        //set crossover point       
        int crossoverPoint = (int) (Math.random()*tree1.getNodes().size());
        while(crossoverPoint ==0){
            crossoverPoint = (int) (Math.random()*tree1.getNodes().size());
        }
        //System.out.println("The crosspoint is: " + crossoverPoint);
            for (int i=0;i<myTree1.getNodes().size();i++){           
                 if (i < crossoverPoint){
                    
                    newTree.getNodes().add(myTree1.getNodes().get(i));                  
                 }
                else{
                    newTree.getNodes().add(myTree2.getNodes().get(i));               
                 }            
            }
            //System.out.println("Tree 1 is: " + myTree1.getNodes());
            //System.out.println("Tree 2 is: " + myTree2.getNodes());
            //System.out.println("The New Tree is: " + newTree.getNodes());
            
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
    public ArrayList<Tree> crossoverTrees(ArrayList<Tree> newTrees, int newMaxTreeHeight, TrainingData td) throws FileNotFoundException, CloneNotSupportedException{
              
        ArrayList<Tree> crossoverArray = new ArrayList<Tree>();
        int size = newTrees.size();
       
        // calculate the number of crossovers to perform
        int crossoverSize = (int) (percentCrossover * size);
       
        for (int i=0;i<crossoverSize;i++) {
            int rNumber = (int) (Math.random()*newTrees.size());
            int rNumber2 = (int) (Math.random()*newTrees.size());
            
            while(rNumber == rNumber2){
                rNumber2 = (int) (Math.random()*newTrees.size());
            }
           
            Tree crossoverTree = crossover(newTrees.get(rNumber),newTrees.get(rNumber2));
            while((crossoverTree.fitnessValue >= newTrees.get(rNumber).fitnessValue) &&
                    (crossoverTree.fitnessValue >=newTrees.get(rNumber2).fitnessValue)){
                rNumber = (int) (Math.random()*newTrees.size());
                rNumber2 = (int) (Math.random()*newTrees.size());
                crossoverTree = crossover(newTrees.get(rNumber),newTrees.get(rNumber2));
            }
            //else{
                crossoverArray.add(crossoverTree);
            
            //System.out.println("The crossover tree score is: " + crossoverTree.fitnessValue);
            //System.out.println("The random1 tree score is: " + newTrees.get(rNumber).fitnessValue);
            //System.out.println("The random2 tree score is: " + newTrees.get(rNumber2).fitnessValue);

            
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
   
    public void printExpression() {
        TreeNode myRoot = null;
        
        // Call a recursive helper method to perform the actual printing.
        printExpression(myRoot);
        System.out.println();
    }

    public void printExpression(TreeNode root) {
        
        if (root.isLeaf()) {
            System.out.print(root.data);

        } 
        else {
            System.out.print("(");
            printExpression(root.left);
            System.out.print(" " + root.data + " ");
            printExpression(root.right);
            System.out.print(")");
        } 
    }   
}
