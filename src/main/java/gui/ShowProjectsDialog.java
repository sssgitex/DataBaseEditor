package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.MyTableModel;
import viewmodel.ViewModel;

public class ShowProjectsDialog extends JDialog implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JDialog instance = null;
	private DefaultComboBoxModel<String> depBoxModel = new DefaultComboBoxModel(new String[0]);
	private JLabel lbInvalidInput = new JLabel("");
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	private String[] header = {"Sifra Projekta", "Naziv Projekta", "Departman MBR", "Naziv Departmana"};
	private DefaultTableModel dftm = new DefaultTableModel(header, 0);
	private ViewModel viewModel;
	
	private HashMap<Integer, Object> depIdMap = new HashMap();
	
	public int selectedDepId = -1;
	
	 @Override
     public void propertyChange(PropertyChangeEvent evt) {
         if ("dataDepartman".equals(evt.getPropertyName())) {
        	 updateBoxModel(evt);
         }
     }
	 
	 private <T extends MyTableModel> void updateBoxModel(PropertyChangeEvent evt) {
		 ArrayList<MyTableModel> al = (ArrayList<MyTableModel>) evt.getNewValue();
		 ArrayList<String> res = new ArrayList();
		 for (MyTableModel man : al) {
			 String mbr = "MBR : ";
			 String name = "NAZIV : ";
			 String type = "TIP : ";
			 String date = "DATUM : ";
			 String[] inp = man.getAsArray();
			 res.add(mbr + inp[0] + " | " + name  + inp[1] + " | " + type  + inp[2] + " |  " + date  + inp[3]);
			 depIdMap.put(res.size()-1, man);
		 }
		 depBoxModel.removeAllElements();
		 depBoxModel.addAll(res);
	 }
	 
	
	private void updateTableModel(ArrayList<MyTableModel> projList) {
		dftm.setRowCount(0);
		dftm = updateTableInsert(projList, dftm, selectedDepId);
	}
	
	
	public <T extends MyTableModel> DefaultTableModel updateTableInsert(ArrayList<T> temp, DefaultTableModel currDftm, int filterId) {
		for (T rowData : temp) {
			currDftm.addRow(rowData.getAsArray());
		}
		return currDftm;
	}
	
	
	public void setDepManagerModel(DefaultComboBoxModel<String> dcbm) {
		depBoxModel = dcbm;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ShowProjectsDialog dialog = new ShowProjectsDialog(new ViewModel());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		    //yourJDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ShowProjectsDialog(ViewModel vm) {
		super(new DialogFrame("Show projects"));
		try {
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			instance = this;
			this.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosed(WindowEvent e) {
	
	            	((Window) instance.getParent()).dispose();
	
	            }
	        });
			instance.setTitle("Projects");
			instance.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/plusicon.png")));
			setBounds(100, 100, 450, 300);
			instance.setMinimumSize(new Dimension(600, 400));
			instance.setPreferredSize(new Dimension(600, 500));
			
			viewModel = vm;
			viewModel.addPropertyChangeListener(this);
			viewModel.updateDataDepartman();
			
			lbInvalidInput.setForeground(Color.RED);
			
	
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setLayout(new GridBagLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			contentPanelSetUp();
	
			
			getContentPane().add(contentPanel, BorderLayout.CENTER);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void contentPanelSetUp() {

		//manager
		JLabel lbDepManager = new JLabel("Departman:");
		JComboBox<String> cbDepartman = new JComboBox(depBoxModel);
		cbDepartman.setPreferredSize(new Dimension(100, 30));

		cbDepartman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	if(cbDepartman.getSelectedItem() != null) {
            		MyTableModel tempDep = ((MyTableModel) depIdMap.get(cbDepartman.getSelectedIndex()));
                	selectedDepId = tempDep.getObjectId();
                	//viewModel.updateDataProjekat();
                	ArrayList projList = viewModel.getProjekatsOfDepartman(tempDep);
                	if(projList != null)
                		updateTableModel(projList);
            	}
            }
        });
		
		JLabel lbProjects = new JLabel("Projects:");
		JTable tProjects = new JTable(dftm);
		tProjects.setPreferredSize(new Dimension(600, 600));
		JScrollPane scrollWrap = new JScrollPane(tProjects);
		scrollWrap.setPreferredSize(new Dimension(500, 500));
		
		
		
		//labels


		fillGrid(lbDepManager, 0, 0, 0.3, 0.0, 10);

		fillGrid(lbProjects, 0, 2, 0.3, 0.0, 10);
		
		
		//inputs

		fillGrid(cbDepartman, 0, 1, 0.0, 0.0, 0);
		
		fillGrid(scrollWrap, 0, 3, 0, 0.9, 500);

		
	}
	
	private void fillGrid(JComponent comp ,int posX, int posY, double weightX, double weightY, int padY) {
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		cons.gridx = posX;
		cons.gridy = posY;
		cons.weightx = weightX;
		cons.weighty = weightY;
		cons.ipady = padY;
		
		contentPanel.add(comp, cons);
	}
}

