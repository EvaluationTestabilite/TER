/**
 * 
 */
package shaheen;

import java.awt.Dimension;
import java.awt.Window;


/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * 25 mai 09
 */
public class FrameCenter 
{
	  /** Put frame at center of screen. **/
	/**
	 * Put window at the screen center. In order to get the 
	 * correct location, it MUST be called after the calling of 
	 * pack() method or setSize() method
	 */
	  static void centerFrame (Window f) {
	    // Need the toolkit to get info on system.
	    java.awt.Toolkit tk =  java.awt.Toolkit.getDefaultToolkit ();

	    // Get the screen dimensions.
	    Dimension screen = tk.getScreenSize ();

	    // get screen center
	    int scx =  (int )screen.getWidth ()/2;
	    int scy =  (int )screen.getHeight()/2;
	    
	    //get JFrame center
	    int h = f.getHeight() /2;
	    int w = f.getWidth() /2;
	        
	    // And place it in center of screen.
	    int x =  scx - w;
	    int y =  scy - h;
	    f.setLocation (x,y);
	  } // centerFrame

}
