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
public class TreeNode{
    
    private char data;   
    TreeNode left;
    TreeNode right;
    int key;
    
    //constructor
    //add height/fitness values
    public TreeNode(int key,char data){
        this.key = key;
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
    public void setKeyValue(int keyValue){
        key = keyValue;
    }
        //get node value
    public int getKeyValue(){
        return key;
    }    
    public void mutate(){
        
    }
    public String toString(){
        return "key " + key + " has data value of: " + data;
    }
    
}
