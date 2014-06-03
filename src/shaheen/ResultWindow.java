/**
 * 
 */
package shaheen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import org.apache.bcel.classfile.Method;
/**
 * @author {Muhammad Rabee SHAHEEN}
 *
 * 20 mai 09
 */
public class ResultWindow extends JFrame implements KeyListener{
	
	//private final int  METRICSCOUNT =  9; // number of metrics to be displayed
	
	//private ResultWindow resultWind;
	
	private Vector<String> classesNames;
	private Vector<MetricsValues> metricsValues; 
	private PairVector CCs;
	private Trial t;
	
	private String selectedClassName;
	private JTable metricsGrid;
	private DefaultTableModel detailModel;
	
	/**
	 *
	 *@param t represent the analyse result that will be displayed
	 */
	public ResultWindow(Trial t)
	{		
		this.t = t;
		
		//resultWind = new ResultWindow();
		setTitle("Metrics Calculator Results");
		setLayout(new BorderLayout(10,10));
		
		
		/***           Metrics View of all Classes              ***/
		String[] metricsColName = {"Class Name", "DIT", "DIT_A", "WMC", "WMH", "WMH_A", "NOM", "NOH", "NOH_A", "RFC", "NOC"};
		
		DefaultTableModel model = new DefaultTableModel(metricsColName,0);
		fillData(model);
		
		metricsGrid = new JTable(model);
		metricsGrid.setGridColor(Color.BLUE);
		metricsGrid.addKeyListener(this);
		
		JScrollPane scrollPane = new JScrollPane(metricsGrid);				
		//metricsGrid.setFillsViewportHeight(true);
		
		JPanel gridPanel = new JPanel(new BorderLayout());
		gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		gridPanel.add(scrollPane,BorderLayout.CENTER);
		
		add(gridPanel,BorderLayout.CENTER);
		
		
		/***           Detailed View of Selected Class              ***/
		String[] detailMethodsCol = {"Method Name", "Cyclomatic Complexity"};
		
		detailModel = new DefaultTableModel(detailMethodsCol,0);
				
		
		JTable metricsMethodGrid = new JTable(detailModel);
		
		
		JScrollPane detailScrollPane = new JScrollPane(metricsMethodGrid);				
//		metricsMethodGrid.setFillsViewportHeight(true);
		detailScrollPane.setPreferredSize(new Dimension(800, 300));
		
		JPanel detailGridPanel = new JPanel(new BorderLayout());
		detailGridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		detailGridPanel.add(detailScrollPane,BorderLayout.CENTER);
		
		add(detailGridPanel,BorderLayout.SOUTH);
		
		
		/***          Defining Events               ***/
		
		metricsGrid.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				//selectedClassName
				selectedClassName = metricsGrid.getValueAt(metricsGrid.getSelectedRow(), 0).toString();
				
				fillMethodMetricsData(detailModel);
			}
		});
		
	
		setSize(800, 600);
		FrameCenter.centerFrame(this);
		setVisible(true);		
	}
	
	
	private void fillData(DefaultTableModel model)
	{		
		classesNames = t.getClassesNames();
		metricsValues = t.getMetricsValues();
		
		int rowsCount = classesNames.size();		
		
		int index = 0;
		
		//model.setRowCount(rowsCount);
		
		for(String classname : classesNames)
		{
			Vector<String> row = new Vector<String>();			
			row.add(classname);
			
			MetricsValues mv = metricsValues.get(index);
			row.add(String.valueOf(mv.DIT));
			row.add(String.valueOf(mv.DIT_A));
			row.add(String.valueOf(mv.WMC));
			row.add(String.valueOf(mv.WMH));
			row.add(String.valueOf(mv.WMH_A));
			row.add(String.valueOf(mv.NOM));
			row.add(String.valueOf(mv.NOH));
			row.add(String.valueOf(mv.NOH_A));
			row.add(String.valueOf(mv.RFC));		
			row.add(String.valueOf(mv.NOC));
			
			model.addRow(row);
			++index;
		}
	}

	private void fillMethodMetricsData(DefaultTableModel model)
	{
		CCs = t.getMethodCCs(selectedClassName);
		model.getDataVector().removeAllElements();
		
		for(int i =0; i< CCs.size();i++)
		 {
			
			Method method = (Method)CCs.getKey(i);				 
			Integer cc = (Integer)CCs.getValue(i);
						
			Vector<String> row = new Vector<String>();			
									
			row.add(method.getName());
			row.add(cc.toString());
					
			model.addRow(row);			
		}
	}	


	public void keyReleased(KeyEvent e) 
	{
		if(e.getSource()==this.metricsGrid)
		{
			selectedClassName = metricsGrid.getValueAt(metricsGrid.getSelectedRow(), 0).toString();			
			fillMethodMetricsData(detailModel);
		}	
		
	}


	public void keyPressed(KeyEvent e) 
	{
		//nothing to do						
	}

	public void keyTyped(KeyEvent arg0) 
	{
		// nothing to do 		
	}
}

