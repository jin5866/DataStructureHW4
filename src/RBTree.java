/**
 * Created by user on 2017-06-06.
 */
public class RBTree {

    public Node root = null;

    void LeftRotate(Node a)
    {
        Node b = a.right;
        a.right = b.left;

        if(b.left != null)
        {
            b.left.parent = a;
        }

        b.parent = a.parent;

        if(a.parent == null)
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

        if(b.right != null)
        {
            b.right.parent = a;
        }


        if(a.parent == null)
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
        if(z.parent == null)
            return;
        if(z.parent.parent == null)
            return;

        //빨강 - 빨강 일때
        while(z.parent.red  == true)
        {

            if(z.parent == z.parent.parent.left)
            {
                // 부모가 왼쪽 서브트리 일떄
                Node uncle = z.parent.parent.right;

                if(uncle.red == true)
                {
                    //case 1
                    z.parent.red =false;
                    uncle.red = false;
                    z.parent.parent.red = true;
                }
                else if(z == z.parent.right)
                {
                    //case 2
                    z = z.parent;
                    LeftRotate(z);
                }
                else
                {
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

                if(uncle.red == true)
                {
                    //case 1
                    z.parent.red =false;
                    uncle.red = false;
                    z.parent.parent.red = true;
                }
                else if(z == z.parent.left)
                {
                    //case 2
                    z = z.parent;
                    RightRotate(z);
                }
                else
                {
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
        Node y = null;
        Node x = root;

        while(x != null)
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

        if(y == null)
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

    public  boolean delete(int val)
    {
        Node x = root;
        while (x.val != val && x!=null )
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

        if(x != null)
        {
            RB_delete(x);
            return true;
        }
        else
        {
            return  false;
        }
    }


    private void RB_delete(Node z)
    {
        Node x = null;
        Node y = z;
        boolean y_origin_red = y.red;
        if(z.left == null)
        {
            x= z.right;
            trasplant(this,z,z.right);
        }
        else if(z.right == null)
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
                x.parent = y;
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
        if(u.parent == null)
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

        if(v != null)
        {
            v.parent = u.parent;
        }
    }

    public static Node tree_minimum(Node tree)
    {
        while(tree.left != null)
            tree = tree.left;
        return tree;
    }
}

