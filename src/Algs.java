import java.util.ArrayList;
/*+----------------------------------------------------------------------
 ||
 ||  Class Algs
 ||
 ||         Author:  Mercado, Benjamin
 ||
 ||        Purpose:  Store valuable information for algs used to solve a Rubik's Cube
 ||
 ||  Inherits From:  None
 ||
 ||     Interfaces:  None
 ||
 ||
 ||   Constructors:  Algs(String alg, String note, String type, String shape)
 ||
 ||  Class Methods:  public static String analyzeAlg(String compressed)
 ||
 ||  Inst. Methods:  public String getNotes()
 ||                  public String getShape()
 ||                  public void setCompressed(String alg)
 ||                  public String getCompressed()
 ||                  public String getAlg()
 ||                  public void editAlg(String n)
 ||                  public String toString()
 ++-----------------------------------------------------------------------*/

public class Algs {
    private String Alg = "";
    private String notes = "";

    private String type = "";
    private String shape = "";
    private String compressed = "";
    public Algs(String alg, String note, String type, String shape)
    {
        this.shape = shape;
        this.type = type;
        notes = note;

        Alg = alg;
    }
    /* ACCESSOR AND MUTATOR METHODS!!!!
    *
    *   Accessors:
    *               String getNotes() - gets the notes provided for the alg
    *               String getShape() - gets the general shape of the case (for CMLLS: O, H, Pi, U, T, etc)
    *               String getCompressed() - gets the condensed form of the alg RUR'U'RUR'U' -> (R U R' U')2
    *               String getAlg() - gets the raw alg itself
    *               String getType() - gets the type of case (For all Pi shapes: Row, Column, Columns, etc)
    *
    *   Mutators:
    *               void setCompressed(String alg) - sets the compressed version to the desired value
    *               void editAlg(String n) - edits the raw alg
     */

    public String getNotes()
    {
        return notes;
    }
    public String getShape()
    {
        return shape;
    }
    public void setCompressed(String alg)
    {
        compressed = alg;
    }
    public String getCompressed()
    {
        return compressed;
    }
    public String getAlg()
    {
        return Alg;
    }
    public String getType()
    {
        return type;
    }

    public void editAlg(String n)
    {
        n = Algs.analyzeAlg(n);
        Alg = n;
    }

    /*---------------------------------------------------------------------
            |  Method: static String analyzeAlg(String compressed)
            |
            |  Purpose:  Expands a compressed alg to allow for the URL not to show the wrong case
            |
            |  Pre-condition:  compressed is a valid Rubik's Cube algorithm (does not contain "A", "B", "C", may contain "(", ")")
            |
            |  Post-condition: The value of compressed is edited to reflect the new, expanded version of the alg
            |
            |  Parameters:
            |      String compressed - Stores the final entry of each line in "src/CMLL.txt". The purpose of this method removes parentheses, which may cause issues with forming the URL for the desired image
            |
            |  Returns:  returns a String which represents the expanded alg, which is needed to properly form the URL
            *-------------------------------------------------------------------*/
    public static String analyzeAlg(String compressed)
    {

        ArrayList<String> sections = new ArrayList<String>();
        int updated = 0;
        while (compressed.contains("("))
        {
            int s = compressed.indexOf("(") + 1;
            int f = compressed.indexOf(")");
            int amount = 1;
            try
            {
                amount = Integer.parseInt(compressed.substring(f+1, f+2));
            }
            catch (Exception e)
            {
                ;;
            }
            finally
            {
                String segment = compressed.substring(s, f);
                sections.add(segment);
                if (amount == 1)
                {
                    compressed = compressed.replaceFirst("\\(" + segment+ "\\)", "A" + updated + "B" + amount + "C");
                }
                else
                {
                    compressed = compressed.replaceFirst("\\(" + segment+ "\\)" + amount, "A" + updated + "B" + amount + "C");
                }

                updated++;
                System.out.println("Changed to: " + compressed);
            }
        }
        while (compressed.contains("A"))
        {
            int s = compressed.indexOf("A") + 1;
            int mid = compressed.indexOf("B");
            int f = compressed.indexOf("C");
            int location = Integer.parseInt(compressed.substring(s, mid));
            int amount = Integer.parseInt(compressed.substring(mid+1, f));
            String temp = "";
            for (int i = 0; i<amount; i++)
            {
                temp += sections.get(location);
            }
            compressed = compressed.replaceFirst("A" + location + "B" + amount + "C", temp);
        }
        compressed = compressed.replaceAll(" ", "");
        return compressed;
    }

    public String toString()
    {
        /*
        https://cube.rider.biz/visualcube.png?fmt=svg&size=150&pzl=3&alg=RUR'URU2R'U2&view=plan&sch=bwogyr&stage=cmll

useful constraints:
alg - runs the alg
case - runs the inverse
sch - color scheme (order: U R F D L B)
view - plan makes it only U face and other needed pieces
stage - hides some pieces to help recognition (use cmll for cmll)
         */
        return "https://cube.rider.biz/visualcube.png?fmt=svg&size=150&pzl=3&case=" + getAlg() + "&stage=cmll"+ "&view=plan&sch="; //
    }
}
