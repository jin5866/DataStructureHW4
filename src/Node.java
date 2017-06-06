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
        this.left =null;
        this.right = null;
        this.parent = null;
        red = true;
    }


}
