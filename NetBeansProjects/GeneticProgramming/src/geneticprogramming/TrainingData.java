/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geneticprogramming;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author mark2681
 */

        
public class TrainingData {
   
    //class variables
    public int[] trainingData; 
    private double myScore;
    private int length;
    
    //constructor
    public TrainingData(){
        int[] td = trainingData;
    }
    
    //return value of training data equation
    public double getTrainingDataScore(int j) throws FileNotFoundException{
        
        Scanner s = new Scanner(new File("src/geneticprogramming/TrainingData.config"));
        
        trainingData = new int[s.nextInt()];
        for (int i = 0; i < trainingData.length; i++){
            trainingData[i] = s.nextInt();          
        }
        
        myScore = (Math.pow(trainingData[j],2)-1)/2;
        s.close();
        return myScore;
    }
    
    //return index of training data file
    public int getIndex(int j) throws FileNotFoundException{
        
        Scanner s = new Scanner(new File("src/geneticprogramming/TrainingData.config"));
        int indexValue = 0;
        
        int[] trainingData = new int[s.nextInt()];
        for (int i = 0; i < trainingData.length; i++){
            trainingData[i] = s.nextInt();          
        }
        
        indexValue = trainingData[j];
        s.close();
        return indexValue;
    }
    
    //return length of training data file
    public int getLength() throws FileNotFoundException{
        Scanner s = new Scanner(new File("src/geneticprogramming/TrainingData.config"));
        length = s.nextInt();
        return length; 
    }
    
}
