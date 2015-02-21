/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geneticprogramming;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mark2681
 */
public class GPEnvironment {
   
    private int treeHeight = 5;
    Node selectedNode;
    Node newLeftNode;
    //GPEnvironment myEnvironment;
    TrainingData td = new TrainingData();
    
    public GPEnvironment() {
        
    }
    public void compare(){}
    public void assignScore(){}
    public void selection(ArrayList<Node> previousGeneration){
        double fitnessValue = 0;
        double highestValue = 0;
        int positionOfHighestValue = 0;
        
        ArrayList<Node> nextGeneration = new ArrayList<>(10);   
        for (int i = 0; i < previousGeneration.size(); i++) {
            double tdValue = td.getTrainingData();
            System.out.println("The " + i + " expression is: " + previousGeneration.get(i).left.getNodeValue() + " " + previousGeneration.get(i).getNodeValue() + " " + previousGeneration.get(i).right.getNodeValue());
            double myValue = this.evaluate(previousGeneration.get(i), tdValue);
            System.out.println("The " + i + " value is: " + myValue);
            System.out.println("The " + i + " fitness value is: " + myValue); 
            
            
        }
        System.out.println("Creating 2nd generation...");
        for(int k = 0; k<nextGeneration.size();k++){
            System.out.println("The " + k + " expression is: " + nextGeneration.get(k).left.getNodeValue() + " " + nextGeneration.get(k).getNodeValue() + " " + nextGeneration.get(k).right.getNodeValue());
         }
        
             
        for (int i = 0;i < previousGeneration.size();i++){
            previousGeneration.get(i).getNodeValue();
        }
        
        if((fitnessValue < highestValue) || (previousGeneration.size() < 8)){
            if(nextGeneration.isEmpty()){
                highestValue = fitnessValue;
            }            
            //nextGeneration.add();
            highestValue = 0;
        }
    }
    public void crossover(){}
    public void mutate(){}
    
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
    
      public double evaluate(Node myNode, double td){
         double leftDouble;
         double rightDouble;
          //System.out.println("The left node value is: " + (double) myNode.left.getNodeValue());
        if((myNode.left.getNodeValue() >= '0') && (myNode.left.getNodeValue() <= '9')){
           leftDouble = (double) (myNode.left.getNodeValue() - '0');
           //System.out.println("The left node value is: " + leftDouble);
        }
        else{
           //fill in training value
           leftDouble = td;
        }       
        if((myNode.right.getNodeValue() >= '0') && (myNode.right.getNodeValue() <= '9')){
           rightDouble = (double) (myNode.right.getNodeValue() - '0');
           //System.out.println("The right node value is: " + rightDouble);
        }
        else{
           rightDouble = td;
        }
        if(myNode.getNodeValue() == '*'){
            return leftDouble * rightDouble;
        }
        else if(myNode.getNodeValue() == '/'){
            return leftDouble / rightDouble;
        }
        else if(myNode.getNodeValue() == '+'){
            return leftDouble + rightDouble;
        }
        else{
            return Math.abs(leftDouble - rightDouble);
        }
    }
}