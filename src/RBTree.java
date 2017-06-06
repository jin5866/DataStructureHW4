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

    void InsertFix(Node z)
    {
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


            }
        }
    }
}
