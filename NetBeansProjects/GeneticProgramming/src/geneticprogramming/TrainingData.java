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
        
        //load training data config
        Scanner s = new Scanner(new File("src/geneticprogramming/TrainingData.config"));
        
        //set training data size to set file size
        trainingData = new int[s.nextInt()];
        
        //fill training data values from file
        for (int i = 0; i < trainingData.length; i++){
            trainingData[i] = s.nextInt();          
        }
        
        //set score of training data at data point
        myScore = (2*(Math.pow(trainingData[j],2))-2)/3;
        s.close();
        
        //return score of training data
        return myScore;
    }
    
    //return index of training data file
    public int getIndex(int j) throws FileNotFoundException{
        
        //open training data file
        Scanner s = new Scanner(new File("src/geneticprogramming/TrainingData.config"));
       
        //initialize variables
        int indexValue = 0;
        int[] trainingData = new int[s.nextInt()];
        
        //fill array with array of indexes
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
