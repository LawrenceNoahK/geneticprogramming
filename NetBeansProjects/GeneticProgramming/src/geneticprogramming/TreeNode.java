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
    
    //class variables
    public char data;   
    TreeNode left;
    TreeNode right;
    int key;
    String nodeType;
    Tree tree = null;
    boolean isNodeRoot = false;
    String value = null;
    TreeNode parent = null;
    private String pointer = null;
    public static String RIGHT = "RIGHT";
    public static String LEFT = "LEFT";
    
    @Override
    public TreeNode clone(){
        TreeNode p = new TreeNode(null,null,key,data);
        p.key =  this.key;
        p.data = this.data;
        p.left = this.left;
        p.right = this.right;
        p.nodeType = this.nodeType;
        p.isNodeRoot = this.isNodeRoot;
        //p.d = this.d.clone();
        //...
        return p;
    }
    
    //constructor
    public TreeNode(TreeNode parent,String newPointer,int key,char data){
        this.key = key;
        this.data = data;
        this.left = left;
        this.right = right;
        this.nodeType = nodeType;
        
        //fill in parent node
        if (parent == null && newPointer == null) {
            this.isNodeRoot = true;
        } 
        //fill in newPointer left or right
        else if (newPointer.equals(TreeNode.LEFT) || newPointer.equals(TreeNode.RIGHT)) {
            this.pointer = newPointer;
            this.parent = parent;
            this.isNodeRoot = false;       
        } 
    }   
    
    //set node value
    public void setNodeValue(char value){
        data = value;
    }
    
    //get node value
    public char getNodeValue(){
        return data;
    }    
    
    //set key value
    public void setKeyValue(int keyValue){
        key = keyValue;
    }
    
    //get key value
    public int getKeyValue(){
        return key;
    }  
    
    //set NodeType value
    public void setNodeTypeValue(String myNode){
        nodeType = myNode;
    }
    
    //get NodeType value 
    public String getNodeTypeValue(){
        return nodeType;
    }    
    
    //sets Tree from TreeNode
    public void setTree(Tree newTree) {
         this.tree = newTree;
    }
    
    //gets Tree from TreeNode
    public Tree getTree() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //sets left TreeNode
    public void setLeft(TreeNode newLeft){
        this.left = newLeft;
    }
    
    //sets right TreeNode
    public void setRight(TreeNode newRight){
        this.right = newRight;
    }
    
    //returns boolean based on if TreeNode is root
    public boolean isRoot() {
        return this.isNodeRoot;
    }

    //returns String value of TreeNode
    public String getValue() {
        return this.value;
    }
    
    //sets parent of TreeNode
    public void setParent(TreeNode myParent){        
        this.parent = myParent;
    }
    
    //returns parent of TreeNode
    public TreeNode getParent() {
        return this.parent;
    }
           
    //gets pointer of TreeNode
    public String getPointer() {       
        return this.pointer;
        }
    
    //checks if node has left or right node
    public boolean isLeaf() {
        return (left == null) && (right == null);
    }
    
}  
