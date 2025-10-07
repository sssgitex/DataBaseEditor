package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import gui.MainWindow.TableManager.Entities;
import model.MyTableModel;
import viewmodel.ViewModel;

import javax.swing.JToolBar;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class MainWindow implements PropertyChangeListener{

	private JFrame mainFrame;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private ViewModel viewModel;
	
	TableManager myTableManager = new TableManager();
	TabManager myTabManager = new TabManager();
	
	
	//int tabCounter = -1;
	ArrayList<Component> tabCounter = new ArrayList<>();
	
	HashMap<String, Component> dialogController = new HashMap<>();

    //DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0); // 0 rows initially
    
    //String[] tableBoxMenuModel;
    DefaultComboBoxModel<String> tableBoxMenuModel = new DefaultComboBoxModel<String>(); 
	
	 @Override
     public void propertyChange(PropertyChangeEvent evt) {
         if ("dataZaposleni".equals(evt.getPropertyName())) {
        	 if(myTableManager.tableModelMap.containsKey(TableManager.Entities.ZAPOSLENI))
        		 updateTable(TableManager.Entities.ZAPOSLENI, evt);
         }
         if ("dataManager".equals(evt.getPropertyName()) && myTableManager.tableModelMap.containsKey(TableManager.Entities.MANAGER)) {
        	// table exists
        	 updateTable(TableManager.Entities.MANAGER, evt);
         }
         if ("dataRadnik".equals(evt.getPropertyName()) && myTableManager.tableModelMap.containsKey(TableManager.Entities.RADNIK)) {
        	 updateTable(TableManager.Entities.RADNIK, evt);
         }
         if ("dataDepartman".equals(evt.getPropertyName()) && myTableManager.tableModelMap.containsKey(TableManager.Entities.DEPARTMAN)) {
        	 updateTable(TableManager.Entities.DEPARTMAN, evt);
        	 //System.out.println("PROPERTY CHANGED!");
         }
         if ("dataProjekat".equals(evt.getPropertyName()) && myTableManager.tableModelMap.containsKey(TableManager.Entities.PROJEKAT)) {
        	 updateTable(TableManager.Entities.PROJEKAT, evt);
        	 //System.out.println("PROPERTY CHANGED!");
         }
         if ("dataRadiNa".equals(evt.getPropertyName()) && myTableManager.tableModelMap.containsKey(TableManager.Entities.RADINA)) {
        	 updateTable(TableManager.Entities.RADINA, evt);
        	 System.out.println("PROPERTY CHANGED!");
         }
         if ("tablesNames".equals(evt.getPropertyName())) {
        	 updateTableBoxMenu(evt.getNewValue());
//    	 	try {
//				viewModel.updateDataTables();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        	 //System.out.println(evt.getNewValue());
         }
     }


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		viewModel = new ViewModel();
		viewModel.addPropertyChangeListener(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setBounds(100, 100, 650, 600);
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/dbicon.png")));
		mainFrame.setTitle("EDB administration");
		mainFrame.setMinimumSize(new Dimension(500, 500));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initializeInputs();
	}
	
	private void initializeInputs() {
		
		//init
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 255, 241));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setRollover(true);
		toolBar.setFloatable(false);
		toolBar.setBackground(new Color(243, 243, 243));
		
		JButton btnDataBaseEditor = new JButton("Data Base Preview");
		btnDataBaseEditor.setBackground(new Color(243, 243, 243));
		btnDataBaseEditor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDataBaseEditor.setFocusable(false);
		btnDataBaseEditor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		btnDataBaseEditor.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	    		//tabCounter++;
	        	MyTab myTab = new MyTab();
	        	Component c = addTab("Preview", myTab.createTableTab(TableManager.Entities.EMPTY));
				tabCounter.add(c);
				myTableManager.tables.getLast().setTabIdx(c);
				myTableManager.tableIndexMap.put(c, myTableManager.tables.getLast());
				myTabManager.tabIdxMap.put(c, myTab);
			
				
	        	//viewModel.updateDataZaposleni();
	        	try {
					viewModel.updateDataTables();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	
	        	
	        }
	    });
		
		JButton btnAddDepartman = new JButton("Add a Departman");
		btnAddDepartman.setBackground(new Color(243, 243, 243));
		btnAddDepartman.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAddDepartman.setFocusable(false);
		btnAddDepartman.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		btnAddDepartman.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if(!dialogController.containsKey("addDep")) {
	        		AddDepartmanDialog dialog = new AddDepartmanDialog(viewModel);
		        	dialog.setVisible(true);
		        	dialogController.put("addDep", dialog);
		        	dialog.addWindowListener(new WindowAdapter() {

			            @Override
			            public void windowClosing(WindowEvent e) {
			            }

			            @Override
			            public void windowClosed(WindowEvent e) {
			            	dialogController.remove("addDep");
			            }

			        });
	        	}
	        	else {
	        		dialogController.get("addDep").requestFocus();
	        	}
	        }
		});
		
		JButton btnAddProject = new JButton("Add a Project");
		btnAddProject.setBackground(new Color(243, 243, 243));
		btnAddProject.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAddProject.setFocusable(false);
		btnAddProject.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		btnAddProject.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if(!dialogController.containsKey("addProj")) {
		        	AddProjekatDialog dialog = new AddProjekatDialog(viewModel);
		        	dialog.setVisible(true);
		        	dialogController.put("addProj", dialog);
		        	dialog.addWindowListener(new WindowAdapter() {

			            @Override
			            public void windowClosing(WindowEvent e) {
			            }

			            @Override
			            public void windowClosed(WindowEvent e) {
			            	dialogController.remove("addProj");
			            }

			        });
	        	}
	        	else {
	        		dialogController.get("addProj").requestFocus();
	        	}
	        }
		});
		
		JButton btnRemoveDepartman = new JButton("Remove a Departman");
		btnRemoveDepartman.setBackground(new Color(243, 243, 243));
		btnRemoveDepartman.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRemoveDepartman.setFocusable(false);
		btnRemoveDepartman.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		btnRemoveDepartman.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if(!dialogController.containsKey("delDep")) {
	        		DeleteDepartmanDialog dialog = new DeleteDepartmanDialog(viewModel);
		        	dialog.setVisible(true);
		        	dialogController.put("delDep", dialog);
		        	dialog.addWindowListener(new WindowAdapter() {

			            @Override
			            public void windowClosing(WindowEvent e) {
			            }

			            @Override
			            public void windowClosed(WindowEvent e) {
			            	dialogController.remove("delDep");
			            }

			        });
	        	}
	        	else {
	        		dialogController.get("delDep").requestFocus();
	        	}
	        }
		});
		
		JButton btnShowProjects = new JButton("Projects");
		btnShowProjects.setBackground(new Color(243, 243, 243));
		btnShowProjects.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnShowProjects.setFocusable(false);
		btnShowProjects.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		btnShowProjects.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	        	if(!dialogController.containsKey("showProj")) {
	        		ShowProjectsDialog dialog = new ShowProjectsDialog(viewModel);
		        	dialog.setVisible(true);
		        	dialogController.put("showProj", dialog);
		        	dialog.addWindowListener(new WindowAdapter() {

			            @Override
			            public void windowClosing(WindowEvent e) {
			            }

			            @Override
			            public void windowClosed(WindowEvent e) {
			            	dialogController.remove("showProj");
			            }

			        });
	        	}
	        	else {
	        		dialogController.get("showProj").requestFocus();
	        	}
	        }
		});
		
		JButton btnShowManual = new JButton("CRUD op`s");
		btnShowManual.setBackground(new Color(243, 243, 243));
		btnShowManual.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnShowManual.setFocusable(false);
		btnShowManual.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		btnShowManual.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
				ManualCRUDDialog dialog;
				try {
					if(!dialogController.containsKey("manual")) {
						dialog = new ManualCRUDDialog(viewModel);
						dialog.setVisible(true);
						dialogController.put("manual", dialog);
						dialog.addWindowListener(new WindowAdapter() {

				            @Override
				            public void windowClosing(WindowEvent e) {
				            }

				            @Override
				            public void windowClosed(WindowEvent e) {
				            	dialogController.remove("manual");
				            }

				        });
					}
					else {
		        		dialogController.get("manual").requestFocus();
		        	}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	}
		});
		
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(199, 199, 199));
		separator.setBackground(new Color(243, 243, 243));
		separator.setOrientation(SwingConstants.VERTICAL);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(199, 199, 199));
		separator_1.setBackground(new Color(243, 243, 243));
		separator_1.setOrientation(SwingConstants.VERTICAL);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(new Color(199, 199, 199));
		separator_2.setBackground(new Color(243, 243, 243));
		separator_2.setOrientation(SwingConstants.VERTICAL);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(new Color(199, 199, 199));
		separator_3.setBackground(new Color(243, 243, 243));
		separator_3.setOrientation(SwingConstants.VERTICAL);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setForeground(new Color(199, 199, 199));
		separator_4.setBackground(new Color(243, 243, 243));
		separator_4.setOrientation(SwingConstants.VERTICAL);
		
		JScrollPane scrollPane = new JScrollPane(toolBar);
		scrollPane.setPreferredSize(new Dimension(999, 55));
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
        
		
		//setup
		mainFrame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		toolBar.add(btnDataBaseEditor);

		toolBar.add(separator);

		toolBar.add(btnAddDepartman);

		toolBar.add(separator_1);

		toolBar.add(btnAddProject);
		
		toolBar.add(separator_2);
		
		toolBar.add(btnRemoveDepartman);
		
		toolBar.add(separator_3);
		
		toolBar.add(btnShowProjects);
		
		toolBar.add(separator_4);
		
		toolBar.add(btnShowManual);
		
		panel.add(scrollPane, BorderLayout.NORTH);
		
		panel.add(tabbedPane, BorderLayout.CENTER);

	}

	
	public void updateTable(TableManager.Entities tableCode, PropertyChangeEvent evt) {
		DefaultTableModel currDftm = myTableManager.tableModelMap.get(tableCode).getModel();
		currDftm.setRowCount(0);
		
		currDftm = updateTableInsert((ArrayList) evt.getNewValue(), currDftm);

		
		myTableManager.tableModelMap.get(tableCode).getBody().setModel(currDftm);
        myTableManager.tableModelMap.get(tableCode).setModel(currDftm);
        tabCounter.get(tabbedPane.getSelectedIndex()).revalidate();
        tabCounter.get(tabbedPane.getSelectedIndex()).repaint();

	}
	
	public <T extends MyTableModel> DefaultTableModel updateTableInsert(ArrayList<T> temp, DefaultTableModel currDftm) {
		for (T rowData : temp) {
        	currDftm.addRow(rowData.getAsArray());
        }
		return currDftm;
	}
	

	private void updateTableBoxMenu(Object model) {

		tableBoxMenuModel.addAll((ArrayList<String>) model);
	}
	
	
	public Component addTab(String name, Component comp) {
		tabbedPane.addTab(name, null, comp, null);
		System.out.println("Counter: " + tabCounter);
	
		

		return tabbedPane.getComponentAt(tabbedPane.getTabCount() - 1);
	}
	
	public class TabManager {
		
		public HashMap<Component, MyTab> tabIdxMap = new HashMap<>();
		
		enum OperationType{
			CHANGE_TABLE_SOURCE,
			OPEN_NEW_TAB
		}
		
		public void handleTabEvent(OperationType type, JComboBox<?> tableBoxMenu, JPanel cont, JScrollPane scroll) {
			switch (type) {
				case CHANGE_TABLE_SOURCE:

					String selectedString = null;
		        	TableManager.Entities tableType = null;
		        	
		        	if(tableBoxMenu.getSelectedItem() != null) {
		        		selectedString = tableBoxMenu.getSelectedItem().toString();
		        		tableType = myTableManager.stringToEntity(selectedString);
		        		System.out.println("IDX: " + selectedString);
		        		System.out.println("IDX: " + tabbedPane.getSelectedIndex());
		        		System.out.println("Type: " + tableType.toString());
		        		System.out.println("Contatin: " + myTableManager.tableModelMap.containsKey(tableType));
		        	}

		        	if(tableType != null) {
		        		System.out.println(true);
		        		if(!myTableManager.tableModelMap.containsKey(tableType)) {
		        			
		        			Component activeTabIdx = tabbedPane.getSelectedComponent();
		        			
		        			MyTable removeTable = myTableManager.tableIndexMap.get(activeTabIdx);
		        			myTableManager.tables.remove(removeTable);
		        			myTableManager.tableIndexMap.remove(activeTabIdx);
		        			myTableManager.tableModelMap.remove(removeTable.getTableType());
		        			
	        				System.out.println("Tables rem: " + myTableManager.tables.size());
	        				MyTable newTable = myTableManager.createTable(tableType);
	        				
	        				newTable.setTabIdx(activeTabIdx);
	        				myTableManager.tableIndexMap.put(activeTabIdx, newTable);
	        				
	        				System.out.println("Tables add: " + myTableManager.tables.size());
	        				System.out.println(cont.getComponentCount());

							for (Component component : cont.getComponents()) {
							        if (component instanceof JScrollPane) {
							            scroll = (JScrollPane) component;
							      }
							}
		        			cont.remove(scroll);
		        			System.out.println("DELETED SCROLL");
		        			System.out.println(cont.getComponentCount());

		        			scroll = new JScrollPane(newTable.getBody());
			        		cont.add(scroll, BorderLayout.CENTER);
			        		cont.revalidate();
			        		cont.repaint();
			        		tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), selectedString);
			        		
			        		tabIdxMap.get(activeTabIdx).getLbTableName().setText(selectedString);
			        		
	        			}
	        		}
		        	break;

				case OPEN_NEW_TAB:
					
					selectedString = null;
		        	tableType = null;
					
					if(tableBoxMenu.getSelectedItem() != null) {
		        		selectedString = tableBoxMenu.getSelectedItem().toString();
		        		tableType = myTableManager.stringToEntity(selectedString);
		        		System.out.println("IDX: " + selectedString);
		        		System.out.println("IDX: " + tabbedPane.getSelectedIndex());
		        		System.out.println("Type: " + tableType.toString());
		        		System.out.println("Contatin: " + myTableManager.tableModelMap.containsKey(tableType));
		        	}
					else {
						return;
					}
					
					boolean found = false;
					int cmpIdx = -1;
					
					for(Component cmp: tabbedPane.getComponents()) {
						if(selectedString.equals(tabbedPane.getTitleAt(tabbedPane.indexOfComponent(cmp)))) {
							found = true;
							cmpIdx = tabbedPane.indexOfComponent(cmp);
						}
					}

						
					if(found) {
						tabbedPane.setSelectedIndex(cmpIdx);
					}
					else {
						Component activeTabIdx = tabbedPane.getSelectedComponent();
						MyTable newTable = myTableManager.createTable(tableType);
						
						MyTab myTab = new MyTab();
						Component c = addTab(selectedString, myTab.createTableTab(tableType));
						tabCounter.add(c);
						myTableManager.tables.getLast().setTabIdx(c);
						myTableManager.tableIndexMap.put(c, myTableManager.tables.getLast());
						myTabManager.tabIdxMap.put(c, myTab);
						
						tabIdxMap.get(c).getLbTableName().setText(selectedString);
					}
					
					break;
			}
		}
	}
	
	public boolean deleteTable() {
		Component removeTabIdx = tabbedPane.getSelectedComponent();
    	MyTable removeTable = myTableManager.tableIndexMap.get(removeTabIdx);
    	
    	System.out.println("idx: " + tabbedPane.indexOfComponent(removeTabIdx));
    	System.out.println("IDXMAP: " + myTableManager.tableIndexMap.get(removeTabIdx));
    	System.out.println("MODELMAP: " + myTableManager.tableModelMap.get(removeTable.getTableType()));
    	System.out.println("TABLES: " + myTableManager.tables.getLast().toString());
    	
    	if(removeTable != null) {
    		myTableManager.tableModelMap.remove(removeTable.getTableType());
        	myTableManager.tableIndexMap.remove(removeTabIdx);
        	System.out.println("ALL: " + myTableManager.tables.size());
        	myTableManager.tables.remove(removeTable);
        	System.out.println("DELETED: " + myTableManager.tables.size());
    	}
    	tabbedPane.remove(tabbedPane.getSelectedComponent());
    	tabCounter.remove(removeTabIdx);
    	return true;
	}
	
	public class TableManager{
		
		public HashMap<Entities, String[]> tableHeaders = new HashMap<>();
		public String[] ZaposleniColumns = {"MBR", "Name", "Sername"};
		public String[] ManagerColumns = {"MBR", "Plata"};
		public String[] RadnikColumns = {"MBR", "Staz"};
		public String[] DepartmanColumns = {"Manager MBR", "Department Name", "Department type", "Datum postavka"};
		public String[] ProjekatColumns = {"Sifra Projekta","Naziv Projekta", "Departman MBR", "Naziv Departmana"};
		public String[] RadiNaColumns = {"Radnik MBR", "Sifra Projekta", "Broj casova"};
		
		
		public HashMap<Component, MyTable> tableIndexMap = new HashMap<Component, MyTable>();
		public HashMap<Entities, MyTable> tableModelMap = new HashMap<>();
		public ArrayList<MyTable> tables = new ArrayList<>(); 
		
		public enum Entities{
			ZAPOSLENI,
			MANAGER,
			RADNIK,
			DEPARTMAN,
			PROJEKAT,
			RADINA,
			EMPTY
		}
		
		public TableManager() {
			tableHeaders.put(Entities.ZAPOSLENI, ZaposleniColumns);
			tableHeaders.put(Entities.MANAGER, ManagerColumns);
			tableHeaders.put(Entities.RADNIK, RadnikColumns);
			tableHeaders.put(Entities.DEPARTMAN, DepartmanColumns);
			tableHeaders.put(Entities.PROJEKAT, ProjekatColumns);
			tableHeaders.put(Entities.RADINA, RadiNaColumns);
			tableHeaders.put(Entities.EMPTY, new String[0]);
		}
		
		public MyTable createTable(Entities type) {
			DefaultTableModel dftm = new DefaultTableModel(tableHeaders.get(type), 0);
			JTable table = new JTable(dftm);
	    	table.setPreferredSize(new Dimension(1000, 1000));
	    	table.getTableHeader().setVisible(true);
	    	
	    	
	    	
	    	MyTable mtable = new MyTable(null, type, table, dftm);
	    	tableModelMap.put(type, mtable);
	    	tables.add(mtable);
	    	
	    	updateTableModel(type);
	    	System.out.println(tables.size());
	    	
	    	return mtable;
		}
		
		public Entities stringToEntity(String input) {
			if(input.contains("zaposleni"))
				return Entities.ZAPOSLENI;
			if(input.contains("manager"))
				return Entities.MANAGER; 
			if(input.contains("radnik"))
				return Entities.RADNIK; 
			if(input.contains("departman"))
				return Entities.DEPARTMAN; 
			if(input.contains("projekat"))
				return Entities.PROJEKAT; 
			if(input.contains("radi_na"))
				return Entities.RADINA;
			return Entities.EMPTY;
		}
		
		public void updateTableModel(Entities type) {
			switch (type) {
				case Entities.ZAPOSLENI:
					viewModel.updateDataZaposleni();
					break;
				case Entities.RADNIK:
					viewModel.updateDataRadnik();
					break;
				case Entities.MANAGER:
					viewModel.updateDataManager();
					break;
				case Entities.DEPARTMAN:
					viewModel.updateDataDepartman();
					break;
				case Entities.PROJEKAT:
					viewModel.updateDataProjekat();
					break;
				case Entities.RADINA:
					viewModel.updateDataRadiNa();
					break;
			}
			
		}
	}
	
	public class MyTable{
		
		private Component tabIdx = null;
		private TableManager.Entities tableType = null;
		private JTable body = null;
		private DefaultTableModel model = null;
		
		public Component getTabIdx() {
			return tabIdx;
		}
		public void setTabIdx(Component tabIdx) {
			this.tabIdx = tabIdx;
		}
		public TableManager.Entities getTableType() {
			return tableType;
		}
		public void setTableType(TableManager.Entities tableType) {
			this.tableType = tableType;
		}
		public JTable getBody() {
			return body;
		}
		public void setBody(JTable body) {
			this.body = body;
		}
		public DefaultTableModel getModel() {
			return model;
		}
		public void setModel(DefaultTableModel model) {
			this.model = model;
		}
		
		public MyTable() {
			
		}
		
		public MyTable(Component tabIdx, Entities tableType, JTable body, DefaultTableModel model) {
			this.tabIdx = tabIdx;
			this.tableType = tableType;
			this.body = body;
			this.model = model;
		}
		
		@Override
		public String toString() {
			return "MyTable [tabIdx=" + tabIdx + ", tableType=" + tableType + ", body=" + body + ", model=" + model
					+ "]";
		}
		
	}
	
	public class MyTab{
		
		private String tableName;
		private JLabel lbTableName;
		
		

		public JLabel getLbTableName() {
			return lbTableName;
		}


		public void setLbTableName(JLabel lbTableName) {
			this.lbTableName = lbTableName;
		}

		public String getTableName() {
			return tableName;
		}


		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		
		
		public JScrollPane createTableTab(TableManager.Entities type) {
			
			JPanel cont = new JPanel();
			JTable table = null;
			JLabel tableName = new JLabel();
			
			//tablePane
			if(type == TableManager.Entities.EMPTY)
				table = myTableManager.createTable(TableManager.Entities.EMPTY).getBody();
			else {
				table = myTableManager.createTable(type).getBody();
			}
			
			JScrollPane scroll = new JScrollPane(table);
	    	
	    	JButton closeButton = new JButton("Close");
			closeButton.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	deleteTable();
		        }
		    });
			
	    	
	    	//drop menu pane
	    	JComboBox<?> tableBoxMenu = new JComboBox(tableBoxMenuModel);
	    	
	    	JButton btnOpenInCurrTab = new JButton("Open in current tab");
	    	btnOpenInCurrTab.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	myTabManager.handleTabEvent(TabManager.OperationType.CHANGE_TABLE_SOURCE,tableBoxMenu, cont, scroll);
	        	}
		    });
	    	
	    	JButton btnOpenInNewTab = new JButton("Open in new tab");
	    	btnOpenInNewTab.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	myTabManager.handleTabEvent(TabManager.OperationType.OPEN_NEW_TAB,tableBoxMenu, cont, scroll);
		        }
		    });
	    	
	    	
	    	JPanel contMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    	contMenu.add(new JLabel("DB Table"));
	    	contMenu.add(tableBoxMenu);
	    	contMenu.add(btnOpenInCurrTab);
	    	contMenu.add(btnOpenInNewTab);

	    	
	    	tableName.setText("Empty");
	    	JLabel sep = new JLabel(":");
			sep.setFont(new Font("Arial", Font.BOLD, 20));
	    	contMenu.add(sep);
	    	contMenu.add(tableName);
	    	
	    	
	    	cont.setLayout(new BorderLayout());
	    	cont.setPreferredSize(new Dimension(600, 400));

	    	cont.add(contMenu, BorderLayout.NORTH);
	    	
	    	cont.add(scroll, BorderLayout.CENTER);

	    	cont.add(closeButton, BorderLayout.SOUTH);
	    	
	    	JScrollPane scrollPane = new JScrollPane(cont);
	    	
	    	setLbTableName(tableName);
	        return scrollPane;
		}



	}

}
