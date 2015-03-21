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
   
    //GPEnvironment myEnvironment;
    TrainingData td = new TrainingData();
    
    public GPEnvironment() {
        
    }
    public void compare(){}
    public void assignScore(TrainingData td){
        double score = 0.0;
        
    }
    public void selection(ArrayList<TreeNode> previousGeneration){
        double fitnessValue = 0;
        double highestValue = 0;
        
        ArrayList<TreeNode> nextGeneration = new ArrayList<>(10);   
        for (int i = 0; i < previousGeneration.size(); i++) {
            double tdValue = td.getTrainingData();
            System.out.println("The " + i + " expression is: " + previousGeneration.get(i).left.getNodeValue() + " " + previousGeneration.get(i).getNodeValue() + " " + previousGeneration.get(i).right.getNodeValue());              
        }
        
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
            highestValue = 0;
        }
    }
    public void crossover(Tree t1, Tree t2){
        
        //select random node for 
        //t1.selectSubtree = temp.subTree
        //t2.selectSubtree = t1.selectSubtree
        //t1.selectSubtree = t2.selectSubtree
    }
    public void mutate(Tree t1){
    
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
    
}
