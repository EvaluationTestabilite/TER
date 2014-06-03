/**
 * 
 */
package shaheen;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.bcel.classfile.Method;

/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * 23 mai 09
 */
 
public class MainWindow extends JFrame implements ActionListener//, ItemListener
{
	
	private JMenuBar menubar;
	private JMenu settingsMenu;
	private JMenu helpMenu = new JMenu("Help");
	private JMenuItem classpathItem;
	
	private ButtonGroup group = new ButtonGroup();
	private JRadioButtonMenuItem RFC_level_0 = new JRadioButtonMenuItem("RFC Level 0");
	private JRadioButtonMenuItem RFC_level_1 = new JRadioButtonMenuItem("RFC Level 1");
	private JRadioButtonMenuItem RFC_level_2 = new JRadioButtonMenuItem("RFC Level 2");
	private JRadioButtonMenuItem RFC_level_3 = new JRadioButtonMenuItem("RFC Level 3");
	
	
	private JMenuItem exitItem;
	
	private JMenuItem helpContents = new JMenuItem("Help Contents");
	private JMenuItem about = new JMenuItem("About");
	
	private JLabel appPathLbl = new JLabel("Application Path: ");
	private JTextField applicationPath = new JTextField(40);	
	private JButton analyzeBtn = new JButton("Start Analyzing");
	private JButton openBtn = new JButton("...");
	private JFileChooser fileDialog = new JFileChooser();
	private SettingsWindow settingsWindow = new SettingsWindow();
	
	private int RFC_Level = 0;
	
	public MainWindow()
	{				
		setLayout(new BorderLayout());
		setTitle("Metrics Calculator Ver. 0.1");
				
		createSettingsMenu();
		createHelpMenu();
				
		add(menubar,BorderLayout.NORTH);
			
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel,BoxLayout.X_AXIS));
		
		upperPanel.add(appPathLbl);
		upperPanel.add(Box.createRigidArea(new Dimension(5,0)));
		upperPanel.add(applicationPath);
		upperPanel.add(Box.createRigidArea(new Dimension(5,0)));
		upperPanel.add(openBtn);
				
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(upperPanel);
		panel.add(Box.createRigidArea(new Dimension(5,5))); // to make fixed space size between the upper panel and bottom one
		panel.add(analyzeBtn);

		upperPanel.setOpaque(false);
		panel.setOpaque(false);
		
		myJPanel panel1 = new myJPanel();
		panel1.setLayout(new FlowLayout());
		panel1.add(panel);
		
		//add(panel1,BorderLayout.CENTER);
		add(panel1);
				
		/***          Defining Events               ***/
		addWindowListener(new WindowAdapter(){
			//	@override
				public void windowClosing(WindowEvent e) {
					System.exit(NORMAL);
				}
			});

		openBtn.addActionListener(this);
		classpathItem.addActionListener(this);
		
		RFC_level_0.addActionListener(this);
		RFC_level_1.addActionListener(this);
		RFC_level_2.addActionListener(this);
		RFC_level_3.addActionListener(this);
		
		exitItem.addActionListener(this);
		analyzeBtn.addActionListener(this);
		about.addActionListener(this);
				
	
		//pack();
		setSize(600, 400);
		setResizable(false);
		FrameCenter.centerFrame(this);		
		setVisible(true);		
	}
	
	private void createSettingsMenu(){				
		menubar = new JMenuBar();
		settingsMenu = new JMenu("Settings");
		classpathItem = new JMenuItem("Class path...");
		
		exitItem = new JMenuItem("Exit");
		
		menubar.add(settingsMenu);
		
		settingsMenu.add(classpathItem);
		settingsMenu.addSeparator();
		settingsMenu.add(RFC_level_0);
		settingsMenu.add(RFC_level_1);
		settingsMenu.add(RFC_level_2);
		settingsMenu.add(RFC_level_3);
		settingsMenu.addSeparator();
		settingsMenu.add(exitItem);
		
		group.add(RFC_level_0);
		group.add(RFC_level_1);
		group.add(RFC_level_2);
		group.add(RFC_level_3);
		
		RFC_level_0.setSelected(true);
	}
	
	private void createHelpMenu()
	{
		this.helpMenu.add(this.helpContents);
		helpMenu.addSeparator();
		this.helpMenu.add(this.about);
		this.menubar.add(helpMenu);		
	}
	

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==openBtn)	
		{
			fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int result = fileDialog.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION)
				this.applicationPath.setText(fileDialog.getSelectedFile().getAbsolutePath());	
		}
		else if(e.getSource()==classpathItem)
		{
			this.settingsWindow.setVisible(true);
		}
		else if(e.getSource()==this.exitItem)
		{						
				System.exit(NORMAL);				
		}
		else if(e.getSource()== analyzeBtn)
		{
			if (verifyAppPath())
			{
				//System.out.println("Before SYSTEM PROPERTY: \n" + System.getProperty("java.class.path"));
				
				Configuration.setClassPath(settingsWindow.getClassesPaths());								
				
				//System.out.println("After SYSTEM PROPERTY: \n" + System.getProperty("java.class.path"));
				
				Trial t = new Trial(this.applicationPath.getText());;
				t.set(RFC_Level);
				boolean analyseOk= false;				
				try
				{						
					t.run();
					analyseOk = true;						
				}
				catch(ClassNotFoundException e1)
				{
					JOptionPane.showMessageDialog(this,"Some libraries are missing.\n Verify the class path settings","Information",JOptionPane.ERROR_MESSAGE);
					System.err.println(e1.toString());					
				}										
					
				if(analyseOk)
				{
					ResultWindow rw = new ResultWindow(t);
				}
			}				
		}
		else if(e.getSource()== this.RFC_level_0)
			this.RFC_Level = 0;
		else if(e.getSource()== this.RFC_level_1)
			this.RFC_Level = 1;
		else if(e.getSource()== this.RFC_level_2)
			this.RFC_Level = 2;
		else if(e.getSource()== this.RFC_level_3)
			this.RFC_Level = 3;	
		
		if(e.getSource() == this.about)
		{
				
			MetricsCalculatorAboutBox aboutWindow = new MetricsCalculatorAboutBox(this);			
			aboutWindow.setVisible(true);
			
				
		}
	}
	
	private boolean verifyAppPath()
	{
		boolean result 	=true;
		java.io.File  f =  new java.io.File(this.applicationPath.getText());
		if(!f.exists() || !f.isDirectory())
		{
			JOptionPane.showMessageDialog(this,"Application folder is not valid.","Information",JOptionPane.ERROR_MESSAGE);
			result = false;
		}		
		
		return result;
	}
	
	public static void main(String[] args) throws ClassNotFoundException
	{
		  try
		  {
			  UIManager.setLookAndFeel(		  
		            UIManager.getSystemLookAndFeelClassName());
		  }
		  catch(Exception e){
			  System.err.println(e.toString());
		  }

		MainWindow mw = new MainWindow();						
	}


}//End ResultWindow class


class myJPanel extends JPanel
{
	private Image backgroundImg; 
	
	public myJPanel()
	{
	    try
	    {
	    	String backgroundImgPath = "";//this.getClass().getResource("/resources/1.jpg").getFile();
	    	backgroundImg = ImageIO.read(new java.io.File(backgroundImgPath));
	    }
	    catch(java.io.IOException e)
	    {
	    	System.err.println(e.toString());
	    }		
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(this.backgroundImg, 0, 0, null);
	}	
}