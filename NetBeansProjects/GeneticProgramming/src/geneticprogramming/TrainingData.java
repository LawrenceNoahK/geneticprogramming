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
   
    private int[] trainingData; 
    private double myScore;
    private int length;
    
    public TrainingData(){
        
    }
    
    public double getTrainingDataScore(int j) throws FileNotFoundException{
        
        Scanner s = new Scanner(new File("src/geneticprogramming/TrainingData.config"));
        
        int[] trainingData = new int[s.nextInt()];
        for (int i = 0; i < trainingData.length; i++){
            trainingData[i] = s.nextInt();          
        }
        
        myScore = (Math.pow(trainingData[j],2)-1)/2;
        s.close();
        return myScore;
    }
    
    public double getScore(){
        return myScore;
    }
    public int getLength() throws FileNotFoundException{
        Scanner s = new Scanner(new File("src/geneticprogramming/TrainingData.config"));
        length = s.nextInt();
        return length; 
    }
    
}
