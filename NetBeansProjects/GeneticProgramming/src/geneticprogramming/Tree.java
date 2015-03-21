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
		if (start > end){
			return null;
                }
		int mid = (start + end)/2;
		
                TreeNode myRoot;
                
                myRoot = new TreeNode(num[mid],'x');
		
                myRoot.left = buildTree(num, start, mid-1);
		
                myRoot.right = buildTree(num, mid+1, end);
 
		return myRoot;
	}
    
   
    public void traverseTree(TreeNode pointerNode){
        if(pointerNode != null){
            traverseTree(pointerNode.left);
            
            if((pointerNode.left == null) && (pointerNode.right == null)){
                pointerNode.setNodeValue(generateRandomValueNode());
            }
            else{
                 //System.out.println("gets in multiply");
                pointerNode.setNodeValue(generateRandomRootNode(4));
            }
            System.out.print(pointerNode.getNodeValue());
            
            traverseTree(pointerNode.right);
            
        }
        
    }
    public char generateRandomValueNode(){
        char value;
        Random rNumber = new Random();
        int myAnswer = rNumber.nextInt(11);
        if(myAnswer == 0){
            value = '0';
        }
        else if(myAnswer == 1 ){
            value = '1';
        }
        else if(myAnswer == 2 ){
            value = '2';
        }
        else if(myAnswer == 3 ){
            value = '3';
        }
        else if(myAnswer == 4 ){
            value = '4';
        }
        else if(myAnswer == 5 ){
            value = '5';
        }
        else if(myAnswer == 6 ){
            value = '6';
        }
        else if(myAnswer == 7 ){
            value = '7';
        }
        else if(myAnswer == 8 ){
            value = '8';
        }
        else if(myAnswer == 9 ){
            value = '9';
        }
        else{
            value = 'x';
        }
        return value;
    }
    
     public char generateRandomRootNode(int myNumber){
        char operand;
        Random rNumber = new Random();
        int myAnswer = rNumber.nextInt(myNumber) + 1;
        if(myAnswer == 1){
            operand = '*';
        }
        else if(myAnswer == 2 ){
            operand = '/';
        }
        else if(myAnswer ==3 ){
            operand = '+';
        }
        else{
            operand = '-';
        }
        return operand;
    }
     
   public double evaluate(TreeNode rootNode, double valueOfX){
  
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
             return evaluate(rootNode.left, valueOfX) + evaluate(rootNode.right, valueOfX);
         case '-': 
             return evaluate(rootNode.left, valueOfX) - evaluate(rootNode.right, valueOfX);
         case '*': 
             return evaluate(rootNode.left, valueOfX) * evaluate(rootNode.right, valueOfX);
         case '/': 
             return evaluate(rootNode.left, valueOfX) / evaluate(rootNode.right, valueOfX);
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