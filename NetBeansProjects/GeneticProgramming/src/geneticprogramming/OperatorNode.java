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
public class OperatorNode extends TreeNode {

    public OperatorNode(int key,char data, TreeNode left, TreeNode right, int height) {
        super(key,data);
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
}
