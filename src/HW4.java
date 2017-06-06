/**
 * Created by user on 2017-06-06.
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import  java.io.BufferedReader;
import  java.io.IOException;
import java.io.*;

public class HW4 {
    public  static  void main(String args[]) throws IOException
    {
        RBTree rbTree = new RBTree();

        String path = HW4.class.getResource("").getPath();
        String inputPath = path + "input.txt";
        String outputPath = path + "output.txt";

        BufferedReader br = null;
        BufferedWriter bw = null;

        File file = new File(inputPath);
        FileReader fr = new FileReader(file);
        //int tmp = fr.read();

        File ofile = new File(outputPath);
        FileWriter fw = new FileWriter(ofile,false);
        bw = new BufferedWriter(fw);


        try {
            br = new BufferedReader(fr);
        }
        catch (Exception e)
        {

        }


        //br = new BufferedReader(new FileReader("input.txt"));
        while (true)
        {
            String g = "  ";
            String line;
            try {
                line = br.readLine();
                line = line.replaceAll(" ","");
            }
            catch (IOException e)
            {
                line = null;
            }
            if(line == null)
            {
                break;
            }

            int val = 0;

            try {
                val = Integer.parseInt(line);
            }
            catch (Exception e)
            {

            }

            if(val == 12)
            {
                System.out.println(12);
            }


            if(val > 0)
            {
                rbTree.Insert(new Node(val));
            }
            else if(val < 0)
            {
                if(rbTree.Delete(-val))
                {

                }
                else
                {

                }
            }
            else
            {
                break;
            }
        }

        br.close();

        String ol = System.getProperty("line.separator");

        int total = rbTree.GetTotal();
        int nb = rbTree.GetBlackNode();
        int bh = rbTree.GetBlackHeight();


        bw.write("total = "+total +ol);
        bw.write("nb = "+nb+ ol);
        bw.write("bh = "+bh + ol);

        rbTree.InorderTraversal(bw);


        bw.close();


    }
}
