/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geneticprogramming;

/**
 *
 * @author mark2681
 */

        
public class TrainingData {
   
    private int[] trainingData; 
    private String myEquation = "(Math.pow(x,2)-1)/2";
    private double myScore;
    
    public TrainingData(){
        
    }
    public double getTrainingData(){
        return 1.0;
    }
    public double read(int i){
        trainingData = new int[7];
        trainingData[0] = -3;
        trainingData[1] = -2;
        trainingData[2] = -1;
        trainingData[3] = 0;
        trainingData[4] = 1;
        trainingData[5] = 2;
        trainingData[6] = 3;
        
        myScore = (Math.pow(trainingData[i],2)-1)/2;
        return myScore;
    }
    public double getScore(){
        return myScore;
    }
    
}
