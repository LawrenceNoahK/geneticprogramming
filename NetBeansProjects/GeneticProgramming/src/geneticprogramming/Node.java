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
public class Node{
    
    private char data;   
    Node left;
    Node right;
    
    //constructor
    //add height/fitness values
    public Node(char data, Node left, Node right){
        this.data = data;
        this.left = left;
        this.right = right;
    }    
   
    //set node value
    public void setNodeValue(char value){
        data = value;
    }
    
    //get node value
    public char getNodeValue(){
        return data;
    }    
}
