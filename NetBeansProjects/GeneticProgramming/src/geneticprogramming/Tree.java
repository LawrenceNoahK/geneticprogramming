/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geneticprogramming;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mark2681
 */
public class Tree implements Comparable<Tree>,Cloneable{
   
   //class variables
   double fitnessValue;
   int height;
   TreeNode left;
   TreeNode right;
   TreeNode rootNode = null;    
   TreeNode root;
   TreeNode myRoot;
   private ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
   ArrayList<TreeNode> operators = new ArrayList<TreeNode>();
   ArrayList<TreeNode> operands = new ArrayList<TreeNode>();
   public TrainingData td = new TrainingData();

   //constructor
   public Tree(TreeNode newRoot,int height, double fitnessValue) {
        this.fitnessValue = fitnessValue;
        this.height = height;     
        root = newRoot;
        getOperators().add(newRoot);
    }  
     
   //returns TreeNode with empty set of nodes 
   public TreeNode buildTree(int[] num, int start, int end) {
		
        if (start > end){
                return null;
        }
        int mid = (start + end)/2;

        TreeNode myRoot;
        TreeNode parent = null;
        String pointer = "";
        myRoot = root;
        
        myRoot = new TreeNode(parent,pointer,num[mid],'x');
        
        myRoot.left = buildTree(num, start, mid-1);

        myRoot.right = buildTree(num, mid+1, end);

        return myRoot;
    }
    
   //traverse tree and assign random values to TreeNodes
   public void traverseTree(TreeNode pointerNode){
        if(pointerNode != null){
            traverseTree(pointerNode.left);
            
            if((pointerNode.left == null) && (pointerNode.right == null)){
                pointerNode.setNodeValue(generateRandomOperandTreeNode());
                pointerNode.setNodeTypeValue("OPERAND");
                addNode(pointerNode);
            }
            else{
                pointerNode.setNodeValue(generateRandomOperatorTreeNode());
                pointerNode.setNodeTypeValue("OPERATOR");
                addNode(pointerNode);
            }
                       
            traverseTree(pointerNode.right);
            
        }
        
    }
   
   //returns treeNode array with filled values
   public TreeNode fillTree(ArrayList<TreeNode> myNodes, int start, int end) {    
        
       TreeNode myRoot;  
       TreeNode parent = null;  
       String pointer = "";
       myRoot = root;
   
       if (start > end){   
           return null;
       }
      
       int mid = (start+end) / 2;  
       myRoot = new TreeNode(parent,pointer,mid,myNodes.get(mid).data);  
       myRoot.left = fillTree(myNodes, start, mid - 1);
       myRoot.right = fillTree(myNodes, mid + 1, end);
    
       return myRoot;
}
   
   //generates a random operand TreeNode
   public char generateRandomOperandTreeNode(){
        
        Random rNumber = new Random();
        int myAnswer = rNumber.nextInt(11);
        switch(myAnswer){
            case 0:
                return '0';
            case 1:
                return '1';
            case 2:
                return '2';
            case 3:
                return '3';
            case 4:
                return '4';
            case 5:
                return '5';
            case 6:
                return '6';
            case 7:
                return '7';
            case 8:
                return '8';
            case 9:
                return '9';
            default:
                return 'x';
        }    
    }
    
   //generates a random operator TreeNode
   public char generateRandomOperatorTreeNode(){
        
        Random rNumber = new Random();
        int myAnswer = rNumber.nextInt(4) + 1;
        
        switch(myAnswer){
            case 1:
                return '*';
            case 2:
                return '/';
            case 3:
                return '+';
            case 4:
                return '-';
            default:
                return '*';
        }        
    }
     
   //returns evaluated TreeNode value
   public double evaluateTree(TreeNode rootNode, double valueOfX){
       
       switch (rootNode.getNodeValue()){
          
         case 'x':      
             return valueOfX;     
         case '1': 
             return (double)rootNode.getNodeValue()-'0';
         case '2': 
             return (double)rootNode.getNodeValue()-'0';
         case '3': 
             return (double)rootNode.getNodeValue()-'0';
         case '4': 
             return (double)rootNode.getNodeValue()-'0';
         case '5': 
             return (double)rootNode.getNodeValue()-'0';
         case '6': 
             return (double)rootNode.getNodeValue()-'0';
         case '7': 
             return (double)rootNode.getNodeValue()-'0';
         case '8': 
             return (double)rootNode.getNodeValue()-'0';
         case '9': 
             return (double)rootNode.getNodeValue()-'0';
         case '0': 
             return (double)rootNode.getNodeValue()-'0';
         case '+': 
             return evaluateTree(rootNode.left, valueOfX) + evaluateTree(rootNode.right, valueOfX);
         case '-': 
             return evaluateTree(rootNode.left, valueOfX) - evaluateTree(rootNode.right, valueOfX);
         case '*': 
             return evaluateTree(rootNode.left, valueOfX) * evaluateTree(rootNode.right, valueOfX);
         case '/': 
             return evaluateTree(rootNode.left, valueOfX) / evaluateTree(rootNode.right, valueOfX);
         default : 
             return 0.0;

    }
   
   }
     
   //override compareTo method, is this still needed?
   @Override
   public int compareTo(Tree compareTree) {
         if (this.fitnessValue < compareTree.fitnessValue) {
            return -1;
        } else if (this.fitnessValue > compareTree.fitnessValue) {
            return 1;
        } else {
            return 0;
        }
    }
    
   //adds TreeNode to Tree 
   public void addNode(TreeNode newNode){
                        
        getNodes().add(newNode);
        
        newNode.setTree(this);
        
        if (newNode.getNodeTypeValue().equals("OPERAND")) {
                getOperands().add(newNode);
        } 
        else if (newNode.getNodeTypeValue().equals("OPERATOR")) {
                getOperators().add(newNode);
        } 

}

   //returns fitness value of Tree
   public double calculateScore(Tree myTree,TreeNode myRoot) throws FileNotFoundException{
       
       
       double fitnessScore = 0.0; 
         
       for(int k=0;k<td.getLength();k++){ 
            fitnessScore = fitnessScore + Math.abs(td.getTrainingDataScore(k) - evaluateTree(myRoot,  td.getIndex(k)));    
       }
       
       Double myFitnessScore = fitnessScore;
       
       if(myFitnessScore.isInfinite() || myFitnessScore.isNaN()){
            fitnessScore = Double.POSITIVE_INFINITY;
       }
       
       myTree.fitnessValue = fitnessScore;
       
       return fitnessScore;
   }

   //returns random operator TreeNode
   public TreeNode getRandomOperatorNode(){
       
        TreeNode myNode = null;

        Random rNumber = new Random();
          
        int randomInt = rNumber.nextInt(getOperators().size());
          
        myNode = getOperators().get(randomInt);
          
        
        if (myNode.getParent() == null ||myNode.isRoot()) {
            myNode = getRandomOperatorNode();
        }
        
        return myNode;
   } 
        
   //returns ArrayList of operators
   public ArrayList<TreeNode> getOperators() {
                return this.operators;
   }
   
   //returns ArrayList of operands
   public ArrayList<TreeNode> getOperands() {
                return this.operands;
    }
   
   //sets root of Tree
   public void setRoot(TreeNode newRoot){
            this.root = newRoot;
    }
   
   //returns TreeNode root
   public TreeNode getRoot() {
        return this.root;
    }
   
   public void setNodes(ArrayList<TreeNode> myNodes) {
                this.nodes = myNodes;
        }
   
   //returns ArrayList of TreeNodes
   public ArrayList<TreeNode> getNodes() {
        return this.nodes;
    }
   
   //clones a tree
   public Tree clone(){
        Tree myTree = new Tree(myRoot,height,fitnessValue);
        myTree.nodes = (ArrayList<TreeNode>) this.nodes.clone();
        return myTree;
    }
}