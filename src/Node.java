/**
 * Created by user on 2017-06-06.
 */
public class Node {

    public int val;

    public Node parent;
    public  Node left;
    public  Node right;
    public boolean red;

    Node(int _val)
    {
        this.val =_val;
        this.left =RBTree.nil;
        this.right = RBTree.nil;
        this.parent = RBTree.nil;
        red = true;
    }


    public boolean isLeaf()
    {
        return left == RBTree.nil && right == RBTree.nil;
    }



}
