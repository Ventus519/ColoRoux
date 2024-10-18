import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*+----------------------------------------------------------------------
 ||
 ||  Class CubeGUI
 ||
 ||         Author:  Mercado, Benjamin
 ||
 ||        Purpose:  Create a GUI to display information related to CMLL Algs
 ||
 ||  Inherits From:  JFrame
 ||
 ||     Interfaces:  None
 ||
 ||
 ||   Constructors:  CubeGUI()
 ||
 ||  Class Methods:  None
 ||
 ||  Inst. Methods:  public void init()
 ||                  public void appear()
 ++-----------------------------------------------------------------------*/

public class CubeGUI extends JFrame
{
    CMLL algs;
    ArrayList<URL> locations = new ArrayList<URL>();
    public CubeGUI()
    {
        super();
        setResizable(false);
        setFocusable(true);
        init();
    }


    //Prompt user for input of D face and L face on startup
/*---------------------------------------------------------------------
        |  Method: init()
        |
        |  Purpose:  initialize CMLL algs with properly defined colors as the base and left side.
        |            The colors are selected using a option dialogue, which returns ints representing the option selected.
        |
        |  Pre-condition:  None
        |
        |  Post-condition: The option dialogues have been properly clicked through, a valid color scheme has been made with a correctly initialized CMLL object algs
        |
        |  Parameters: None
        |
        |
        |  Returns: null
        *-------------------------------------------------------------------*/
    public void init()
    {
        String[] options = {"White", "Green", "Red", "Yellow", "Blue", "Orange"};
        ArrayList<String> opt = new ArrayList<String>(List.of(options));
        int base = JOptionPane.showOptionDialog(this, "What color do you use for your cross/blocks?", "D Face Color Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opt.toArray(), 0);
        opt.remove(options[base]); //removes chosen color from options to ensure that an invalid color scheme is not selected
        opt.remove(options[(base+3)%6]); //removes opposite color (yellow is opposite to white, green is opposite to blue, red is opposite to orange)
        int side = JOptionPane.showOptionDialog(this, "Select a color on the left side", "L Face Color Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opt.toArray(), 0);
        algs = new CMLL(options[base], opt.get(side));
    }
    //Show everything based on what user said

    /*---------------------------------------------------------------------
        |  Method: void appear()
        |
        |  Purpose:  Makes the JFrame object and displays images and information for every case in "src/CMLL.txt"
        |
        |  Pre-condition:  algs has been properly initialized.
        |
        |  Post-condition: A frame showing all CMLLS and Algs will be shown until the user closes out of the window.
        |
        |  Parameters: None
        |
        |  Returns:  null
        *-------------------------------------------------------------------*/
    public void appear()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JFrame frame = new JFrame("ColoRoux");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 12));
        frame.setSize(2000, 1000);
        locations = algs.getImages();
        for (URL x: locations)
        {
            BufferedImage image = null;
            try
            {

                image = ImageIO.read(x);
                JLabel lblimage = new JLabel(new ImageIcon(image));
                panel.add(lblimage);
                //
                int i = locations.indexOf(x);

                JLabel l = new JLabel("<html>"+ algs.getCMLLs().get(i).getType() + "<br>" + algs.getCMLLs().get(i).getShape()  + "<br>" + algs.getCMLLs().get(i).getCompressed() +"<br>" + algs.getCMLLs().get(i).getNotes() + "</html");
                panel.add(l);

                System.out.println("imaghe found at" + x.toString());
            }
            catch (Exception e)
            {
                System.out.println("imaghe fake, failed at: " + x.toString());
                continue;
            }
        }
        frame.add(panel);
        frame.setBackground(Color.BLACK); //this didn't even do anything ;-;
        frame.setVisible(true);

    }

}
