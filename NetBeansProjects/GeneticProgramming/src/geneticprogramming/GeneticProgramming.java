/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package geneticprogramming;

import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author mark2681
 */
public class GeneticProgramming {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Creating GP Environment...");
        GPEnvironment myEnvironment = new GPEnvironment();
        
        //create root node
        System.out.println("Creating Root Node...");
        
        //Node left = new Node ('x', null, null);
        //Node right = new Node ('2', null, null);        
        //Node root = new Node ('*', left, right);
        
        ArrayList<Node> list = new ArrayList<Node>(10);
        TrainingData td = new TrainingData();
        System.out.println("Creating expression trees array...");
        for(int i = 0;i<=10;i++){
            
            Node left = new Node ('x', null, null);
            Node right = new Node ('2', null, null);        
            Node root = new Node ('*', left, right);
            
            Random myNumber;
            myNumber = new Random();
            
            int myRootAnswer = myNumber.nextInt(4) + 1;
                    
            char randomLeft = myEnvironment.generateRandomValueNode();
            char randomRight = myEnvironment.generateRandomValueNode();
            char randomRoot = myEnvironment.generateRandomRootNode(myRootAnswer);
                       
            //create Array list of nodes
            
            list.add(i,root);
            list.get(i).left.setNodeValue(randomLeft);
            list.get(i).right.setNodeValue(randomRight);
            list.get(i).setNodeValue(randomRoot);
                        
        }
        
        //for (int i = 0; i < list.size(); i++) {
        //    double tdValue = td.getTrainingData();
        //    System.out.println("The " + i + " expression is: " + list.get(i).left.getNodeValue() + " " + list.get(i).getNodeValue() + " " + list.get(i).right.getNodeValue());
        //    double myValue = myEnvironment.evaluate(list.get(i), tdValue);
        //    System.out.println("The " + i + " value is: " + myValue);
        //    System.out.println("The " + i + " fitness value is: " + myValue);                      
        //}
        System.out.println("Selecting Trees for Next Generation...");         
        myEnvironment.selection(list);
       
    }
  
    
}
