package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
import javax.swing.text.Document;


import model.MyTableModel;
import viewmodel.ViewModel;

public class AddProjekatDialog extends JDialog implements PropertyChangeListener{
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JDialog instance = null;
	private DefaultComboBoxModel<String> depBoxModel = new DefaultComboBoxModel(new String[0]);
	private JLabel lbInvalidInput = new JLabel("");
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	private ViewModel viewModel;
	
	private HashMap<Integer, Object> depIdMap = new HashMap();
	//input fields
	private String projName = null;
	private Object departman = null;
	
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
	
	
	public void setDepManagerModel(DefaultComboBoxModel<String> dcbm) {
		depBoxModel = dcbm;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddProjekatDialog dialog = new AddProjekatDialog(new ViewModel());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddProjekatDialog(ViewModel vm) {
		super(new DialogFrame("Add a new Departman"));
		try {
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			instance = this;
			instance.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosed(WindowEvent e) {
	            	((DialogFrame)instance.getParent()).dispose();
	            }
	        });
			instance.setTitle("Add departman");
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
			//setDefaultBoxModel(new DefaultComboBoxModel());
			
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
				        	if(uploadNewProjekat()) {
				        		lbInvalidInput.setForeground(Color.GREEN);
				        		lbInvalidInput.setText("Department was succesfully added");
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

	private void contentPanelSetUp() {
		//name
		JLabel lbDepName = new JLabel("Projekat name:");
		JTextField tfDepName = inputFieldConstructor(lbDepName.getText(), "str");
		
		
		//departman
		JLabel lbDepaprtman = new JLabel("Departman:");
		JComboBox<String> cbDepartman = new JComboBox(depBoxModel);
		cbDepartman.setPreferredSize(new Dimension(100, 30));
		cbDepartman.setInputVerifier(new BoxInputVerifier());
		cbDepartman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	if(cbDepartman.getSelectedItem() != null) {
            		MyTableModel temp = ((MyTableModel) depIdMap.get(cbDepartman.getSelectedIndex()));
                	selectedDepId = temp.getObjectId();
                	cbDepartman.getInputVerifier().verify(cbDepartman);

            	}
            }
        });
		
		
		//labels
		
		fillGrid(lbDepName, 0, 0, 0.3, 10);
		

		fillGrid(lbDepaprtman, 0, 2, 0.3, 10);

		
		
		//inputs
		
		fillGrid(tfDepName, 0, 1, 0.0, 0);

		fillGrid(cbDepartman, 0, 3, 0.0, 0);

		
	}
	
	private void fillGrid(JComponent comp ,int posX, int posY, double weightX, int padY) {
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		cons.gridx = posX;
		cons.gridy = posY;
		cons.weightx = weightX;
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
	

	private boolean uploadNewProjekat() {
		if(projName != null && departman != null)
			return viewModel.uploadNewProjekat(projName, departman);
		else {
			lbInvalidInput.setForeground(Color.RED);
			lbInvalidInput.setText("Not all field were filled!");
			return false;
		}
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
                //cancelButton.setEnabled(false);
                return false;
            } else {
            	lbInvalidInput.setText("");
                textField.setBackground(Color.WHITE);
                okButton.setEnabled(true);
                if(textField.getName().equals("Projekat name:"))
                	projName = text.trim();
                return true;
            }
        }

        @Override
        public boolean shouldYieldFocus(JComponent input) {
            return verify(input); // Prevent focus loss if input is invalid
        }
    }
	
	
	private class BoxInputVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
        	JComboBox box = (JComboBox) input;
            Object selected = box.getSelectedItem();
            
            if(selected != null) {
            	box.setBackground(Color.WHITE);

            	okButton.setEnabled(true);
            	departman = depIdMap.get(box.getSelectedIndex());
            	return true;
            }
            else {
            	box.setBackground(Color.PINK);
            	

            	okButton.setEnabled(false);
            	return false;
            }
        }
        @Override
        public boolean shouldYieldFocus(JComponent input) {
            return verify(input); // Prevent focus loss if input is invalid
        }
    }
}

