import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;

/*+----------------------------------------------------------------------
 ||
 ||  Class CMLL
 ||
 ||         Author:  Mercado, Benjamin
 ||
 ||        Purpose:  Store valuable information for algs used to solve a Rubik's Cube
 ||
 ||  Inherits From:  JFrame
 ||
 ||     Interfaces:  None
 ||
 ||
 ||   Constructors:  CMLL(String b, String s)
 ||
 ||  Class Methods:  None
 ||
 ||  Inst. Methods:   public void init() throws FileNotFoundException, MalformedURLException
 ||                   public ArrayList<URL> getImages()
 ||                   public ArrayList<Algs> getCMLLs()
 ++-----------------------------------------------------------------------*/
public class CMLL {
    ArrayList<Algs> d = new ArrayList<Algs>();
    ArrayList<Algs> personalized = new ArrayList<Algs>();
    ArrayList<URL> images = new ArrayList<URL>();
    String base = ""; //block base (bottom face of cube)
    String start = ""; //left face
    String sch = "";

    String[] orient = { "wrgyob", "wbrygo", "wobyrg", "wgoybr",
                        "brwgoy", "byrgwo", "boygrw", "bwogyr",
                        "yrbwog", "ygrwbo", "yogwrb", "ybowgr",
                        "grybow", "gwrbyo", "gowbry", "gyobwr",
                        "rgwoby", "rygowb", "rbyogw", "rwboyg",
                        "ogyrbw", "owgryb", "obwrgy", "oybrwg"
    };
    public CMLL(String b, String s)
    {

        base = b.substring(0, 1).toLowerCase(); //D layer
        start = s.substring(0, 1).toLowerCase(); //L layer
        //time to make an array of all the orientations (order : U R F D L B)
        System.out.println(base+ start);
        for (String rotate : orient)
        {
            if (rotate.substring(3, 5).equals(base + start))
            {
                sch = rotate;
                break;
            }
        }
        try
        {
            init();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("FILE LOCATION BAD!!!");
        }
        catch (MalformedURLException f)
        {
            System.out.println("MISSING ALGS!!!");
        }


    }

    /*---------------------------------------------------------------------
        |  Method: init() throws throws FileNotFoundException, MalformedURLException
        |
        |  Purpose:  Initialize the values of default ArrayList d and personalized through iteration over entries in a text file, read by a Scanner
        |
        |  Pre-condition:  The file "src/CMLL.txt" exists, each line contains exactly 3 commas with valid entries
        |
        |  Post-condition: ArrayList images will contain all URL objects (where the images are located) determined based on the entries in the file
        |
        |  Parameters: None
        |
        |  Returns:  null, throws exceptions should something go wrong in the current step (FileNotFoundException if the file is not found, MalformedURLException if the URL is malformed)
        *-------------------------------------------------------------------*/
    public void init() throws FileNotFoundException, MalformedURLException {
        File path = new File("src/CMLL.txt");
        Scanner read = new Scanner(path);
        while(read.hasNextLine())
        {
            String data = read.nextLine();
            System.out.println(data);
            int index = 0;
            String[] info = {"", "", "", ""}; //format: type, case, default notes, alg
            String temp = "";
            for (int i = 0; i<3; i++)
            {
                info[i] = data.substring(0, data.indexOf(","));
                data = data.substring(data.indexOf(",") + 1);
            }
            info[3] = data;
            info[3] = Algs.analyzeAlg(info[3]);
            System.out.println(info[3]);
            Algs a = new Algs(info[3], info[2], info[0], info[1]);
            a.setCompressed(data);
            d.add(a);
            personalized = d; //for storage purposes
            images.add(new URL(a.toString() + sch));
        }
    }
    /* ACCESSOR AND MUTATOR METHODS!!!!
     *
     *   Accessors:
     *               ArrayList<URL> getImages() - gets the URL locations of each image for each CMLL
     *               ArrayList<Algs> getCMLLs() - gets the Alg objects referenced by each CMLL
     */
    public ArrayList<URL> getImages()
    {
        return images;
    }
    public ArrayList<Algs> getCMLLs()
    {
        return d;
    }


}
