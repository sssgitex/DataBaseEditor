package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;

import model.MyTableModel;
import viewmodel.ViewModel;

public class ManualCRUDDialog extends JDialog implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JDialog instance = null;
	private DefaultComboBoxModel<String> depBoxModel = new DefaultComboBoxModel<String>();
	private JLabel lbInvalidInput = new JLabel("");
	private JLabel lbInputHint = new JLabel("");
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");


	private ViewModel viewModel;
	private String selectedTable;
	private ArrayList<String> tablesNames = null;
	
	private HashMap<String, String> hintMap = new HashMap<String, String>();
	
	private String inputQuery = null;
	
	 @Override
     public void propertyChange(PropertyChangeEvent evt) {

         if ("tablesNames".equals(evt.getPropertyName())) {
        	 updateBoxModel(evt.getNewValue());
        	 System.out.println("OP's");
         }
     }
	
	 
	 private void updateBoxModel(Object list) {
		 depBoxModel.addAll((ArrayList<String>) list);
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
			ManualCRUDDialog dialog = new ManualCRUDDialog(new ViewModel());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		    
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 */
	public ManualCRUDDialog(ViewModel vm) throws SQLException {
		super(new DialogFrame("Manual CRUD"));
		try {
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			instance = this;
			this.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosed(WindowEvent e) {
	                
	                
	            	((Window) instance.getParent()).dispose();
	            	
	            }
	        });
			instance.setTitle("Manual CRUD");
			instance.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/plusicon.png")));
			setBounds(100, 100, 450, 300);
			instance.setMinimumSize(new Dimension(600, 400));
			instance.setPreferredSize(new Dimension(600, 500));
			
			viewModel = vm;
			ViewModel localvm = new ViewModel();
			localvm.addPropertyChangeListener(this);
			localvm.updateDataTables();
			tablesNames = localvm.getDataTables();
			
			
			lbInputHint.setForeground(Color.BLUE);
			uploadHints();
			
			lbInvalidInput.setForeground(Color.RED);
			
			
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setLayout(new GridBagLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			contentPanelSetUp();
			
			
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					buttonPane.add(lbInvalidInput);
				}
				{
					
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);
					okButton.addActionListener(new ActionListener() {
						@Override
				        public void actionPerformed(ActionEvent e) {
	
							if(uploadNewEntity()) {
								lbInvalidInput.setForeground(Color.GREEN);
				        		lbInvalidInput.setText("Entity was succesfully added");
				        		updateData();
							}
							else {
								lbInvalidInput.setForeground(Color.RED);
								lbInvalidInput.setText("Error accured, entity was not added to DB");
							}
								
				        }
					});
				}
				{
					
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
					cancelButton.addActionListener(new ActionListener() {
						@Override
				        public void actionPerformed(ActionEvent e) {
				        	instance.dispose();
				        }
					});
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void uploadHints() {
		hintMap.put("zap", "mbr,ime,prezime");
		hintMap.put("man", "mbr,plata");
		hintMap.put("rad", "mbr,staz");
		hintMap.put("dep", "There's a standalone function fow this operation (Button: \"Add a new Departman\")");
		hintMap.put("proj", "naziv,departman-mbr");
		hintMap.put("radina", "radnik-mbr,proj-mbr,brcasova");
	}

	private void contentPanelSetUp() {

		JLabel lbTableName = new JLabel("Table:");
		JComboBox<String> cbTableName = new JComboBox(depBoxModel);
		cbTableName.setPreferredSize(new Dimension(100, 30));
		
		cbTableName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	getSelectedTableName(cbTableName.getSelectedItem().toString());
            	updateInputHint();
            }
        });
		
		JLabel lbInputCrud = new JLabel("Input:");
		JTextField tfInputCrud = inputFieldConstructor(lbInputCrud.getText(), "str");
		
		JLabel lbInputForm = new JLabel("Input form:");


		fillGrid(lbTableName, 0, 0, 0.3, 0.0, 10);

		fillGrid(lbInputCrud, 0, 2, 0.3, 0.0, 10);
		
		fillGrid(lbInputForm, 0, 4, 0.3, 0.0, 10);
		
		fillGrid(lbInputHint, 0, 5, 0.3, 0.0, 10);

		fillGrid(cbTableName, 0, 1, 0.0, 0.0, 0);
		
		fillGrid(tfInputCrud, 0, 3, 0, 0.0, 0);

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
	
	private JTextField inputFieldConstructor(String name, String type) {
		JTextField tf = new JTextField();
		tf.setPreferredSize(new Dimension(100, 30));
		tf.setName(name);
		
		if(type.equals("str"))
			tf.setInputVerifier(new StringInputVerifier());


		
		Document doc = tf.getDocument();
		doc.addDocumentListener(new DocumentListener() {
	        @Override
	        public void insertUpdate(DocumentEvent e) {
	        	tf.getInputVerifier().verify(tf);
	        }

	        @Override
	        public void removeUpdate(DocumentEvent e) {
	        	tf.getInputVerifier().verify(tf);
	        }

	        @Override
	        public void changedUpdate(DocumentEvent e) {
	        }
	    });
		return tf;
	}
	

	private class StringInputVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            JTextField textField = (JTextField) input;
            String text = textField.getText();
            if (text.trim().isEmpty()) {
            	lbInvalidInput.setForeground(Color.RED);
                lbInvalidInput.setText(textField.getName().substring(0, textField.getName().indexOf(":")) + " cannot be empty.");
                textField.setBackground(Color.PINK);
                okButton.setEnabled(false);
                
                return false;
            } else {
            	lbInvalidInput.setText("");
                textField.setBackground(Color.WHITE);
                okButton.setEnabled(true);
                if(textField.getName().equals("Input:"))
                	inputQuery = text.trim();


                return true;
            }
        }

        @Override
        public boolean shouldYieldFocus(JComponent input) {
            return verify(input); 
        }
    }
	
	private void getSelectedTableName(String input) {
		if(input.contains("zaposleni"))
			selectedTable = "zap";
		if(input.contains("manager"))
			selectedTable = "man";
		if(input.contains("radnik"))
			selectedTable = "rad";
		if(input.contains("departman"))
			selectedTable = "dep";
		if(input.contains("projekat"))
			selectedTable = "proj";
		if(input.contains("radi_na"))
			selectedTable = "radina";

	}
	
	private void updateInputHint() {
		lbInputHint.setText(hintMap.get(selectedTable));
	}
	
	private boolean uploadNewEntity() {
		if(inputQuery != null) {
			return viewModel.uploadNewEntity(selectedTable, inputQuery.split(","));
		}
		else {
			lbInvalidInput.setForeground(Color.RED);
			lbInvalidInput.setText("Not all field were filled!");
			return false;
		}
	}
	
	private void updateData() {
		switch (selectedTable) {
			case "zap":
				viewModel.updateDataZaposleni();
				break;
			case "man":
				viewModel.updateDataManager();
				break;
			case "rad":
				viewModel.updateDataRadnik();
				break;
			case "dep":
				viewModel.updateDataDepartman();
				break;
			case "proj":
				viewModel.updateDataProjekat();
				break;
			case "radina":
				viewModel.updateDataRadiNa();
				break;
		}
	}
}

