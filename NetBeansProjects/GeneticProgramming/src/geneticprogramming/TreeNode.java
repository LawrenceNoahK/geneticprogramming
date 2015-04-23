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
    private int level = 0;
    private String pointer = null;
    public static String RIGHT = "RIGHT";
    public static String LEFT = "LEFT";
    
    
    //constructor
    //add height/fitness values
    public TreeNode(TreeNode parent,String newPointer,int key,char data){
        this.key = key;
        this.data = data;
        this.left = left;
        this.right = right;
        this.nodeType = nodeType;
        
        //fill in parent node
        if (parent == null && newPointer == null) {
            this.isNodeRoot = true;
            this.level = 0;
        } 
        //fill in newPointer left or right
        else if (newPointer.equals(TreeNode.LEFT) || newPointer.equals(TreeNode.RIGHT)) {
            this.pointer = newPointer;
            this.parent = parent;
            this.isNodeRoot = false;
            parent.setChild(this);
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
    
    //returns String for TreeNode output
    public String toString(){
        return "key " + key + " has data value of: " + data;
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
    
    //sets level of TreeNode
    public void setLevel(int myLevel) {
        this.level = myLevel;
    }
    
    //return level of TreeNode
    public int getLevel() {
        return this.level;
    }
    
    //sets child of TreeNode
    public void setChild(final TreeNode myNode){
                        
        if (myNode.getPointer().equals(TreeNode.LEFT)) {
            setLeft(myNode);
        } 
        else if (myNode.getPointer().equals(TreeNode.RIGHT)) {
                setRight(myNode);
        } 
    }
    
    //sets pointer of TreeNode
    public void setPointer(final String myPointer){
        if (myPointer.equals(TreeNode.LEFT) || myPointer.equals(TreeNode.RIGHT)) {
                this.pointer = myPointer;
        } 
    }
    
    //gets pointer of TreeNode
    public String getPointer() {       
        return this.pointer;
        }
     public boolean isLeaf() {
        return (left == null) && (right == null);
    }

}  
