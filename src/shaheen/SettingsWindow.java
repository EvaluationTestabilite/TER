package shaheen;

/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * May 23, 2009
 */
 
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
 
public class SettingsWindow extends JFrame {
		JButton addJarBtn       = new JButton("Add jar");
		JButton addJarsBtn     = new JButton("Add jars");
		JButton addFolderBtn = new JButton("Add folder");
		JButton savePathsBtn = new JButton("Save");
		JButton loadPathBtn  = new JButton("Load");
		
		JFileChooser fileDialog = new JFileChooser();
		
		JTextArea pathsArea = new JTextArea();
		
	public SettingsWindow()
	{
		this.setLayout(new BorderLayout(10,10));
		JPanel leftPanel = new JPanel(new BorderLayout(10,10));
		JPanel rightPanel = new JPanel(new GridLayout(5,1,5,5));
		
	//	rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
		
		leftPanel.setBorder(BorderFactory.createTitledBorder("Class paths"));
		
		pathsArea.setFont(new Font("Serif", Font.ITALIC, 16));
		pathsArea.setLineWrap(true);
		
		JScrollPane scroll = new JScrollPane(pathsArea);				
		leftPanel.add(scroll);
				
		rightPanel.add(addJarBtn);
		rightPanel.add(addJarsBtn);
		rightPanel.add(addFolderBtn);
		rightPanel.add(savePathsBtn);
		rightPanel.add(loadPathBtn);
		
		JPanel rPanel = new JPanel(new FlowLayout());
		rPanel.add(rightPanel);
		
		fileDialog.setFileFilter( new FileNameExtensionFilter("Jar/Zip file", "jar", "zip"));
		
		createEvents();
		
		this.add(leftPanel,BorderLayout.CENTER);
		this.add(rPanel, BorderLayout.EAST);
		
		this.setSize(500,300);
		this.setResizable(false);
		FrameCenter.centerFrame(this);
		this.setTitle("Settings");
		
		
	}
		
	private void createEvents()
	{
		addJarBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {								
				fileDialog.setMultiSelectionEnabled(false);
				fileDialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
				int result = fileDialog.showOpenDialog(SettingsWindow.this);
				if (result == JFileChooser.APPROVE_OPTION)
						pathsArea.append(fileDialog.getSelectedFile().getAbsolutePath() + ";");
			}
		});
		
		addJarsBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
			
				fileDialog.setMultiSelectionEnabled(true);
				fileDialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
				int result = fileDialog.showOpenDialog(SettingsWindow.this);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					for(File f : fileDialog.getSelectedFiles())
					{
						pathsArea.append(f.getAbsolutePath() + ";");
					}
				}						
			}
		});
		
		addFolderBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
			fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
				int result = fileDialog.showOpenDialog(SettingsWindow.this);
				if (result == JFileChooser.APPROVE_OPTION)
						pathsArea.append(fileDialog.getSelectedFile().getAbsolutePath() + ";");
			}
		});
		
		this.savePathsBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {								
								
				fileDialog.setSelectedFile(new File("classespaths.txt"));
				
				int result = fileDialog.showSaveDialog(SettingsWindow.this);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					try
					{
						if(fileDialog.getSelectedFile().exists())
						{
							
							int yesno = JOptionPane.showConfirmDialog(SettingsWindow.this, "A file with same name is already exist, do you want to replace it?", 
							           "Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
							if (yesno == JOptionPane.YES_OPTION)
							{
								FileWriter fw = new FileWriter(fileDialog.getSelectedFile().getAbsolutePath());
								fw.write(pathsArea.getText());
								fw.close();									
							}
						}
						else
						{
							FileWriter fw = new FileWriter(fileDialog.getSelectedFile().getAbsolutePath());
							fw.write(pathsArea.getText());
							fw.close();	
						}																		
					}
					catch (Exception e1)
					{
						System.err.println(e1.toString());
					}					
				}				
			}
		});
		
		loadPathBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
			
				fileDialog.setMultiSelectionEnabled(false);
				fileDialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
				int result = fileDialog.showOpenDialog(SettingsWindow.this);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					try
					{						
						FileReader fr = new FileReader(fileDialog.getSelectedFile().getAbsolutePath() );
						BufferedReader br = new BufferedReader(fr);
						String line = br.readLine();
						
						while(line!=null)
						{
							pathsArea.append(line);
							line=br.readLine();
						}						
					}
					catch(Exception e1)
					{
						System.err.println(e1.toString());
					}
				}
			}
		});
		
	   addWindowListener(new WindowAdapter(){
			//	@override
				public void windowClosing(WindowEvent e) {
					SettingsWindow.this.setVisible(false);
				}
		});

	}//End create event
	
	
	/**
	 * returns the classes paths of required libraries of the application.
	 */
	public String getClassesPaths()
	{
		return pathsArea.getText();
	}
}

