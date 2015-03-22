/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geneticprogramming;

import java.util.Random;

/**
 *
 * @author mark2681
 */
public class Tree implements Comparable<Tree>{
    double fitnessValue;
    int height;
    boolean specialNode;
    TreeNode left;
    TreeNode right;
    TreeNode rootNode = null;    
    TreeNode root;
    String nodeLocation;
    TreeNode myRoot;
    
    public Tree(int height, double fitnessValue){
        this.fitnessValue = fitnessValue;
        this.height = height;   
       
    }  
    
    public TreeNode buildTree(int[] num, int start, int end) {
        
        TreeNode myRoot;
        int mid = (start + end)/2;
        
        if (start > end){
            return null;
        }
        
        myRoot = new TreeNode(num[mid],'x');		
        myRoot.left = buildTree(num, start, mid-1);
        myRoot.right = buildTree(num, mid+1, end);

        return myRoot;
    }
    
   
    public void traverseTree(TreeNode pointerNode){
        if(pointerNode != null){
            traverseTree(pointerNode.left);
            
            if((pointerNode.left == null) && (pointerNode.right == null)){
                pointerNode.setNodeValue(generateRandomOperandNode());
                pointerNode.setNodeTypeValue("OPERAND");
            }
            else{
                pointerNode.setNodeValue(generateRandomOperatorNode());
                pointerNode.setNodeTypeValue("OPERATOR");
            }
            System.out.print(pointerNode.getNodeValue());
           
            traverseTree(pointerNode.right);
            
        }
        
    }
    public char generateRandomOperandNode(){
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
    
     public char generateRandomOperatorNode(){
        
        Random rNumber = new Random();
        int myAnswer = rNumber.nextInt(4) + 1;
        switch(myAnswer){
            case 1: 
                return '*';
            case 2: 
                return '/';
            case 3:
                return '+';
            default:
                return '-';
        }
    }
     
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

}