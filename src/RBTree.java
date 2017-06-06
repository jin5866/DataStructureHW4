/**
 * Created by user on 2017-06-06.
 */
import java.io.*;

public class RBTree {

    RBTree()
    {
        nil.right =nil;
        nil.left = nil;
        nil.parent = nil;
        nil.red = true;
    }

    String ol = System.getProperty("line.separator");


    static public Node nil = new Node(0);
    public Node root = nil;

    void LeftRotate(Node a)
    {
        Node b = a.right;
        a.right = b.left;

        if(b.left != nil)
        {
            b.left.parent = a;
        }

        b.parent = a.parent;

        if(a.parent == nil)
        {
            root = b;
        }
        else if(a == a.parent.left)
        {
            a.parent.left = b;
        }
        else
        {
            a.parent.right = b;
        }

        b.left = a;
        a.parent = b;


    }

    void RightRotate(Node a)
    {
        Node b = a.left;
        a.left = b.right;

        if(b.right != nil)
        {
            b.right.parent = a;
        }


        if(a.parent == nil)
        {
            root = b;
        }
        else if(a.parent.left == a)
        {
            a.parent.left = b;
        }
        else
        {
            a.parent.right = b;
        }

        b.parent = a.parent;
        a.parent = b;

    }

    public void InsertFix(Node z)
    {
        if(z.parent == nil)
            return;
        if(z.parent.parent == nil)
            return;

        //빨강 - 빨강 일때
        while(z.parent.red  == true)
        {

            if(z.parent == z.parent.parent.left)
            {
                // 부모가 왼쪽 서브트리 일떄
                Node uncle = z.parent.parent.right;

                if(uncle != nil && uncle.red == true)
                {
                    //case 1
                    z.parent.red =false;
                    uncle.red = false;
                    z.parent.parent.red = true;
                }
                else
                {
                    if(z == z.parent.right)
                    {
                        //case 2
                        z = z.parent;
                        LeftRotate(z);
                    }
                    //case 3
                    z.parent.red = false;
                    z.parent.parent.red = true;
                    RightRotate(z.parent.parent);
                }

            }
            else
            {
                //오른쪽 서브트리 일떄
                Node uncle = z.parent.parent.left;

                if(uncle != nil && uncle.red == true)
                {
                    //case 1
                    z.parent.red =false;
                    uncle.red = false;
                    z.parent.parent.red = true;
                }
                else
                {
                    if(z == z.parent.left)
                    {
                        //case 2
                        z = z.parent;
                        RightRotate(z);
                    }
                    //case 3
                    z.parent.red = false;
                    z.parent.parent.red = true;
                    LeftRotate(z.parent.parent);
                }
            }
        }

        root.red = false;
    }

    public void Insert(Node n)
    {
        Node y = nil;
        Node x = root;

        while(x != nil)
        {
            y = x;
            if(n.val < x.val)
            {
                x = x.left;
            }
            else
            {
                x = x.right;
            }
        }

        n.parent = y;

        if(y == nil)
        {
            root = n;
        }
        else if(n.val < y.val)
        {
            y.left = n;
        }
        else
        {
            y.right = n;
        }

        n.red = true;

        InsertFix(n);
    }

    public  boolean Delete(int val)
    {
        Node x = root;
        while (x != nil && x.val != val)
        {
            if(val<x.val)
            {
                x = x.left;
            }
            else
            {
                x = x.right;
            }
        }

        if(x != nil)
        {
            if(x.val == val)
            {
                RB_delete(x);
                return true;
            }
            else
            {
                return  false;
            }
        }
        else
        {
            return  false;
        }
    }


    private void RB_delete(Node z)
    {
        Node x = nil;
        Node y = z;
        boolean y_origin_red = y.red;
        if(z.left == nil)
        {
            x= z.right;
            trasplant(this,z,z.right);
        }
        else if(z.right == nil)
        {
            x = z.left;
            trasplant(this,z,z.left);
        }
        else
        {
            y = tree_minimum(z.right);
            y_origin_red = y.red;
            x = y.right;
            if(y.parent == z)
            {
                if(x != nil)
                {
                    x.parent = y;
                }
            }
            else
            {
                trasplant(this,y,y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            trasplant(this,z,y);
            y.left = z.left;
            y.left.parent = y;
            y.red = z.red;
        }

        if(y_origin_red == false)
        {
            RBDeleteFixup(x);
        }
    }

    void RBDeleteFixup(Node x)
    {
        Node w;
        while(x!=root && x.red == false )
        {
            if(x == x.parent.left)
            {
                w = x.parent.right;
                if(w.red == true)
                {
                    w.red = false;
                    x.parent.red =true;
                    LeftRotate(x.parent);
                    w = x.parent.right;
                }
                if(w.left.red == false && w.right.red == false)
                {
                    w.red = true;
                    x = x.parent;
                }
                else
                {
                    if(w.right.red == false)
                    {
                        w.left.red = false;
                        w.red = true;
                        RightRotate(w);
                        w=  x.parent.right;
                    }
                    w.red = x.parent.red;
                    x.parent.red = false;
                    w.right.red = false;
                    LeftRotate(x.parent);
                    x = root;
                }
            }
            else
            {
                w = x.parent.left;
                if(w.red == true)
                {
                    w.red = false;
                    x.parent.red =true;
                    RightRotate(x.parent);
                    w = x.parent.left;
                }
                if(w.right.red == false && w.left.red == false)
                {
                    w.red = true;
                    x = x.parent;
                }
                else
                {
                    if(w.left.red == false)
                    {
                        w.right.red = false;
                        w.red = true;
                        LeftRotate(w);
                        w=  x.parent.left;
                    }
                    w.red = x.parent.red;
                    x.parent.red = false;
                    w.left.red = false;
                    RightRotate(x.parent);
                    x = root;
                }
            }

            x.red = false;
        }
    }



    public static void trasplant(RBTree tree, Node u,Node v)
    {
        if(u.parent == nil)
        {
            tree.root = v;
        }
        else if(u == u.parent.left)
        {
            u.parent.left = v;
        }
        else
        {
            u.parent.right = v;
        }

        if(v != nil)
        {
            v.parent = u.parent;
        }
    }

    public static Node tree_minimum(Node tree)
    {
        while(tree.left != nil)
            tree = tree.left;
        return tree;
    }

    public int GetTotal()
    {
        return GetTotal(root);
    }


    private int GetTotal(Node a)
    {
        if(a == nil)
        {
            return 0;
        }
        else
        {
            return GetTotal(a.left) + 1 +GetTotal(a.right);
        }
    }

    public int GetBlackNode()
    {
        return GetBlackNode(root);
    }

    private int GetBlackNode(Node a)
    {
        if(a == nil)
        {
            return 0;
        }
        else
        {
            int tmp;
            tmp = a.red || a.isLeaf() ? 0 : 1;
            return GetBlackNode(a.left)+GetBlackNode(a.right) + tmp;
        }
    }

    public int GetBlackHeight()
    {
        Node x = root;
        int counter = 0;
        while(x != nil)
        {
            if(!x.red)
            {
                counter ++;
            }
            x = x.left;
        }

        return counter;
    }

    public void InorderTraversal(BufferedWriter bw)
    {
        InorderTraversal(bw,root);
    }

    private  void InorderTraversal(BufferedWriter bw , Node a)
    {

        if(a == nil)
        {
            return;
        }
        else
        {
            InorderTraversal(bw , a.left);
            try
            {
                bw.write( a.val + ol);
            }
            catch (Exception e)
            {
                System.out.println(a.val);
            }
            InorderTraversal(bw , a.right);
        }
    }


}

