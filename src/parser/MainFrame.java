package parser;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import pddl_datatype.Action;
import pddl_datatype.Domain;
import pddl_datatype.Param;
import pddl_datatype.Predicate;
import pddl_datatype.Problem;

public class MainFrame {

	private JFrame frmOwlsTranslator;
	private JTextField txtSelectFile;
	final JFileChooser fc = new JFileChooser();	
	private JTabbedPane tabbedPane;
	private JPanel pnlConvertOwls;
	private JScrollPane scrollPane;
	private JTextArea log;
	private JButton btnContinue;
	private JButton btnAddAction;
	private Domain domain = new Domain();
	private Action currAction;
	private JLabel lblActionCounter;
	private JScrollPane scrollPane_1;
	private JTextArea txtDomain;
	private JButton btnGenProblem;
	private JPanel pnlSelectIO;
	private JScrollPane inputOptionScrollPane;
	private JScrollPane inputSelectedScrollPane;
	private JLabel lblInput;
	private JLabel lblOutput;
	private JButton btnCreateProblemFile;
	private JList listInputOption;
	private JButton btnInputInsert;
	private JButton btnInputRemove;
	private JList listInputSelected;
	private JScrollPane outputOptionScrollPane;
	private JList listOutputOption;
	private JScrollPane outputSelectedScrollPane;
	private JList listOutputSelected;
	private JButton btnOutputInsert;
	private JButton btnOutputRemove;
	private DefaultListModel inputOptions;
	private DefaultListModel outputOptions;
	private DefaultListModel inputSelected;
	private DefaultListModel outputSelected;	
	private Set paramSet = new HashSet();
	private JPanel pnlViewProblem;
	private Problem problem;
	private JScrollPane scrollPane_2;
	private JTextArea txtProblem;
	private JButton btnReset;
	private JButton btnStrips;
	private JPanel pnlStrips;
	private JScrollPane scrollPane_3;
	private JTextArea txtStrips;
	private JLabel lblNewLabel;
	private List<Action> listAction = new ArrayList<Action>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frmOwlsTranslator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "serial", "unchecked" })
	private void initialize() {
		
		frmOwlsTranslator = new JFrame();
		frmOwlsTranslator.setTitle("OWLS Translator");
		frmOwlsTranslator.setBounds(100, 100, 769, 574);
		frmOwlsTranslator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOwlsTranslator.getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 9, 733, 514);
		frmOwlsTranslator.getContentPane().add(tabbedPane);
		
		// 1. OWLS2Action Tab
		createOWLS2ActionPanel();
		
		// 2. Domain File Tab
		createDomainFilePanel();
		
		// 3. Select Input & Output Tab
		createSelectIOPanel();
		
		// 4. Problem File Tab
		createProblemFileTab();
		
		// 5. Strips Planning Tab
		createStripsPlanningTab();
		
	}
	
	/**
	 * 1. OWLS2Action Tab
	 */
	
	public void createOWLS2ActionPanel() {
		// 1. OWLS2Action
		pnlConvertOwls = new JPanel();
		tabbedPane.addTab("1. OWLS2Action", null, pnlConvertOwls, null);
		GridBagLayout gbl_pnlConvertOwls = new GridBagLayout();
		gbl_pnlConvertOwls.columnWidths = new int[]{77, 47, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pnlConvertOwls.rowHeights = new int[]{0, 0, 0, 0};
		gbl_pnlConvertOwls.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlConvertOwls.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		pnlConvertOwls.setLayout(gbl_pnlConvertOwls);
		
		JButton btnNewButton = new JButton("Choose File");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		pnlConvertOwls.add(btnNewButton, gbc_btnNewButton);
		
		fc.setMultiSelectionEnabled(true);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnval = fc.showOpenDialog(frmOwlsTranslator);
				
				if (returnval == JFileChooser.APPROVE_OPTION) {
					log.setText(null);
					
		            File[] files = fc.getSelectedFiles();
		            
		            StringBuilder sb = new StringBuilder();
		            for (File file : files) {
		            	
		            	// Check extension - skip non owls
		            	String extension = "";
		            	int i = file.toString().lastIndexOf('.');
		            	if (i > 0) {
		            	    extension = file.toString().substring(i+1);
		            	}
		            	if (!extension.equals("owls")) continue;
		            	
						sb.append(file.toString());
					
						// Parse action
						Parser parser = new Parser();
			            parser.parse(file);		            
			            currAction = parser.getAction();
			            listAction.add(parser.getAction());
			            log.append("\n");
			            log.append(currAction.getPddl());
					}
		            
		            txtSelectFile.setText(sb.toString());
				}
			}
		});
		btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
		
		txtSelectFile = new JTextField();
		GridBagConstraints gbc_txtSelectFile = new GridBagConstraints();
		gbc_txtSelectFile.insets = new Insets(0, 0, 5, 0);
		gbc_txtSelectFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSelectFile.gridwidth = 10;
		gbc_txtSelectFile.gridx = 1;
		gbc_txtSelectFile.gridy = 0;
		pnlConvertOwls.add(txtSelectFile, gbc_txtSelectFile);
		txtSelectFile.setHorizontalAlignment(SwingConstants.CENTER);
		txtSelectFile.setText("Select an OWL-S file");
		txtSelectFile.setEditable(false);
		txtSelectFile.setColumns(10);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 11;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		pnlConvertOwls.add(scrollPane, gbc_scrollPane);
		
		log = new JTextArea();
		log.setEditable(false);
		log.setTabSize(2);
		scrollPane.setViewportView(log);
		
		btnAddAction = new JButton("Add to Domain File");
		btnAddAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (domain == null) domain = new Domain();
				addActionToDomain();
			}
		});
		
		// Reset variables
		btnReset = new JButton("Reset!");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				domain = new Domain();
				updateLabelCounter();
				log.setText("");
				txtDomain.setText("");
				txtProblem.setText("");
				inputOptions.removeAllElements();
				outputOptions.removeAllElements();
				inputSelected.removeAllElements();
				outputSelected.removeAllElements();
				paramSet.clear();
			}
		});
		
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnReset.insets = new Insets(0, 0, 0, 5);
		gbc_btnReset.gridx = 0;
		gbc_btnReset.gridy = 2;
		pnlConvertOwls.add(btnReset, gbc_btnReset);
		
		lblActionCounter = new JLabel();
		lblActionCounter.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblActionCounter = new GridBagConstraints();
		gbc_lblActionCounter.gridwidth = 8;
		gbc_lblActionCounter.insets = new Insets(0, 0, 0, 5);
		gbc_lblActionCounter.gridx = 1;
		gbc_lblActionCounter.gridy = 2;
		pnlConvertOwls.add(lblActionCounter, gbc_lblActionCounter);
		updateLabelCounter();
		
		GridBagConstraints gbc_btnAddAction = new GridBagConstraints();
		gbc_btnAddAction.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddAction.gridx = 9;
		gbc_btnAddAction.gridy = 2;
		pnlConvertOwls.add(btnAddAction, gbc_btnAddAction);
		
		btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (domain.listAction.size() > 0) {
					tabbedPane.setSelectedIndex(1);
				}

			}
		});
		GridBagConstraints gbc_btnContinue = new GridBagConstraints();
		gbc_btnContinue.gridx = 10;
		gbc_btnContinue.gridy = 2;
		pnlConvertOwls.add(btnContinue, gbc_btnContinue);
	}
	
	/**
	 * 2. Domain File Tab
	 */
	private void createDomainFilePanel() {

		JPanel pnlViewDomain = new JPanel();
		tabbedPane.addTab("2. Domain File", null, pnlViewDomain, null);

		GridBagLayout gbl_pnlViewDomain = new GridBagLayout();
		gbl_pnlViewDomain.columnWidths = new int[]{546, 0, 0};
		gbl_pnlViewDomain.rowHeights = new int[]{0, 0, 0};
		gbl_pnlViewDomain.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlViewDomain.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		
		pnlViewDomain.setLayout(gbl_pnlViewDomain);
		
		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		pnlViewDomain.add(scrollPane_1, gbc_scrollPane_1);
		
		txtDomain = new JTextArea();
		txtDomain.setTabSize(2);
		txtDomain.setEditable(false);
		scrollPane_1.setViewportView(txtDomain);
		
		btnGenProblem = new JButton("Generate Problem File");
		btnGenProblem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
			}
		});
		GridBagConstraints gbc_btnCreateProblemFile = new GridBagConstraints();
		gbc_btnCreateProblemFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCreateProblemFile.gridx = 1;
		gbc_btnCreateProblemFile.gridy = 1;
		pnlViewDomain.add(btnGenProblem, gbc_btnCreateProblemFile);
		
	}

	/**
	 * 3. Select IO Tab
	 */
	private void createSelectIOPanel() {
		pnlSelectIO = new JPanel();
		tabbedPane.addTab("3. Select Input & Output", null, pnlSelectIO, null);
		GridBagLayout gbl_pnlSelectIO = new GridBagLayout();
		gbl_pnlSelectIO.columnWidths = new int[]{250, 0, 250, 0};
		gbl_pnlSelectIO.rowHeights = new int[]{0, 75, 0, 0, 75, 0, 75, 0, 0, 75, 0, 0};
		gbl_pnlSelectIO.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlSelectIO.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlSelectIO.setLayout(gbl_pnlSelectIO);
		
		lblInput = new JLabel("INPUT");
		GridBagConstraints gbc_lblInput = new GridBagConstraints();
		gbc_lblInput.gridwidth = 3;
		gbc_lblInput.insets = new Insets(0, 0, 5, 0);
		gbc_lblInput.gridx = 0;
		gbc_lblInput.gridy = 0;
		pnlSelectIO.add(lblInput, gbc_lblInput);
		
		inputSelected = new DefaultListModel();
		
		listInputSelected = new JList(inputSelected);
		listInputSelected.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel) {
                    ((JLabel) renderer).setText(value.toString());
                }
                return renderer;
            }
        });
		
		inputSelectedScrollPane = new JScrollPane(listInputSelected);
		GridBagConstraints gbc_inputSelectedScrollPane = new GridBagConstraints();
		gbc_inputSelectedScrollPane.gridheight = 4;
		gbc_inputSelectedScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_inputSelectedScrollPane.fill = GridBagConstraints.BOTH;
		gbc_inputSelectedScrollPane.gridx = 2;
		gbc_inputSelectedScrollPane.gridy = 1;
		pnlSelectIO.add(inputSelectedScrollPane, gbc_inputSelectedScrollPane);
		
		inputSelectedScrollPane.setViewportView(listInputSelected);
		
		// Button choose input
		btnInputInsert = new JButton(">");
		btnInputInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listInputOption.getSelectedValue() != null) {
					inputSelected.addElement(listInputOption.getSelectedValue());
					inputOptions.removeElement(listInputOption.getSelectedValue());
					listInputOption.clearSelection();
				}
			}
		});
		
		GridBagConstraints gbc_btnInputInsert = new GridBagConstraints();
		gbc_btnInputInsert.insets = new Insets(0, 0, 5, 5);
		gbc_btnInputInsert.gridx = 1;
		gbc_btnInputInsert.gridy = 2;
		pnlSelectIO.add(btnInputInsert, gbc_btnInputInsert);
		
		btnInputRemove = new JButton("<");
		btnInputRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listInputSelected.getSelectedValue() != null) {
					inputOptions.addElement(listInputSelected.getSelectedValue());
					inputSelected.removeElement(listInputSelected.getSelectedValue());
					listInputSelected.clearSelection();
				}
			}
		});
		
		GridBagConstraints gbc_btnInputRemove = new GridBagConstraints();
		gbc_btnInputRemove.insets = new Insets(0, 0, 5, 5);
		gbc_btnInputRemove.gridx = 1;
		gbc_btnInputRemove.gridy = 3;
		pnlSelectIO.add(btnInputRemove, gbc_btnInputRemove);
		
		lblOutput = new JLabel("OUTPUT");
		GridBagConstraints gbc_lblOutput = new GridBagConstraints();
		gbc_lblOutput.gridwidth = 3;
		gbc_lblOutput.insets = new Insets(0, 0, 5, 0);
		gbc_lblOutput.gridx = 0;
		gbc_lblOutput.gridy = 5;
		pnlSelectIO.add(lblOutput, gbc_lblOutput);
		
		inputOptions = new DefaultListModel<>();
		listInputOption = new JList(inputOptions);
		listInputOption.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel) {
                    ((JLabel) renderer).setText(value.toString());
                }
                return renderer;
            }
        });
		
		inputOptionScrollPane = new JScrollPane(listInputOption);
		GridBagConstraints gbc_inputOptionScrollPane = new GridBagConstraints();
		gbc_inputOptionScrollPane.gridheight = 4;
		gbc_inputOptionScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_inputOptionScrollPane.fill = GridBagConstraints.BOTH;
		gbc_inputOptionScrollPane.gridx = 0;
		gbc_inputOptionScrollPane.gridy = 1;
		pnlSelectIO.add(inputOptionScrollPane, gbc_inputOptionScrollPane);
		
		outputOptions = new DefaultListModel();
		listOutputOption = new JList(outputOptions);
		listOutputOption.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel) {
                    ((JLabel) renderer).setText(value.toString());
                }
                return renderer;
            }
        });
		
		outputOptionScrollPane = new JScrollPane();
		GridBagConstraints gbc_outputOptionScrollPane = new GridBagConstraints();
		gbc_outputOptionScrollPane.gridheight = 4;
		gbc_outputOptionScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_outputOptionScrollPane.fill = GridBagConstraints.BOTH;
		gbc_outputOptionScrollPane.gridx = 0;
		gbc_outputOptionScrollPane.gridy = 6;
		pnlSelectIO.add(outputOptionScrollPane, gbc_outputOptionScrollPane);
		
		outputOptionScrollPane.setViewportView(listOutputOption);
		
		outputSelected = new DefaultListModel();
		
		listOutputSelected = new JList(outputSelected);
		listOutputSelected.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel) {
                    ((JLabel) renderer).setText(value.toString());
                }
                return renderer;
            }
        });
		
		outputSelectedScrollPane = new JScrollPane();
		GridBagConstraints gbc_outputSelectedScrollPane = new GridBagConstraints();
		gbc_outputSelectedScrollPane.gridheight = 4;
		gbc_outputSelectedScrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_outputSelectedScrollPane.fill = GridBagConstraints.BOTH;
		gbc_outputSelectedScrollPane.gridx = 2;
		gbc_outputSelectedScrollPane.gridy = 6;
		pnlSelectIO.add(outputSelectedScrollPane, gbc_outputSelectedScrollPane);
		
		outputSelectedScrollPane.setViewportView(listOutputSelected);
		
		// Button choose output
		btnOutputInsert = new JButton(">");
		btnOutputInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listOutputOption.getSelectedValue() != null) {
					outputSelected.addElement(listOutputOption.getSelectedValue());
					outputOptions.removeElement(listOutputOption.getSelectedValue());
					listOutputOption.clearSelection();
				}
			}
		});
		GridBagConstraints gbc_btnOutputInsert = new GridBagConstraints();
		gbc_btnOutputInsert.insets = new Insets(0, 0, 5, 5);
		gbc_btnOutputInsert.gridx = 1;
		gbc_btnOutputInsert.gridy = 7;
		pnlSelectIO.add(btnOutputInsert, gbc_btnOutputInsert);
		
		btnOutputRemove = new JButton("<");
		btnOutputRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listOutputSelected.getSelectedValue() != null) {
					outputOptions.addElement(listOutputSelected.getSelectedValue());
					outputSelected.removeElement(listOutputSelected.getSelectedValue());
					listOutputSelected.clearSelection();
				}
			}
		});
		GridBagConstraints gbc_btnOutputRemove = new GridBagConstraints();
		gbc_btnOutputRemove.insets = new Insets(0, 0, 5, 5);
		gbc_btnOutputRemove.gridx = 1;
		gbc_btnOutputRemove.gridy = 8;
		pnlSelectIO.add(btnOutputRemove, gbc_btnOutputRemove);
		
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 3;
		
		
		btnCreateProblemFile = new JButton("Generate Problem File");
		btnCreateProblemFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				problem = new Problem();
				problem.init.addAll(Arrays.asList(inputSelected.toArray()));
				problem.goal.addAll(Arrays.asList(outputSelected.toArray()));
				txtProblem.setText(problem.getPddl());
				tabbedPane.setSelectedIndex(3);
				
			}
		});
		
		btnCreateProblemFile.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_btnGenerateProblemFile = new GridBagConstraints();
		gbc_btnGenerateProblemFile.anchor = GridBagConstraints.EAST;
		gbc_btnGenerateProblemFile.gridx = 2;
		gbc_btnGenerateProblemFile.gridy = 10;
		pnlSelectIO.add(btnCreateProblemFile, gbc_btnGenerateProblemFile);
	}
	
	/**
	 * 4. Problem File Tab
	 */
	private void createProblemFileTab() {
		pnlViewProblem = new JPanel();
		tabbedPane.addTab("4. Problem File", null, pnlViewProblem, null);
		GridBagLayout gbl_pnlViewProblem = new GridBagLayout();
		gbl_pnlViewProblem.columnWidths = new int[]{0, 0};
		gbl_pnlViewProblem.rowHeights = new int[]{0, 0, 0};
		gbl_pnlViewProblem.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnlViewProblem.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		pnlViewProblem.setLayout(gbl_pnlViewProblem);
		
		scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 0;
		pnlViewProblem.add(scrollPane_2, gbc_scrollPane_2);
		
		txtProblem = new JTextArea();
		txtProblem.setTabSize(2);
		scrollPane_2.setViewportView(txtProblem);
		
		btnStrips = new JButton("Solve!");
		btnStrips.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (domain != null && problem != null) {
					
					StripsPlanner planner = new StripsPlanner(domain, problem);
					planner.plan();

					txtStrips.setText(planner.getPlanStr());
					tabbedPane.setSelectedIndex(4);
				}
			}
		});
		
		GridBagConstraints gbc_btnStrips = new GridBagConstraints();
		gbc_btnStrips.anchor = GridBagConstraints.EAST;
		gbc_btnStrips.gridx = 0;
		gbc_btnStrips.gridy = 1;
		pnlViewProblem.add(btnStrips, gbc_btnStrips);
	}
	
	/**
	 * 5. Planning Tab
	 */
	private void createStripsPlanningTab() {
		pnlStrips = new JPanel();
		tabbedPane.addTab("5. Planning", null, pnlStrips, null);
		GridBagLayout gbl_pnlStrips = new GridBagLayout();
		gbl_pnlStrips.columnWidths = new int[]{0, 0};
		gbl_pnlStrips.rowHeights = new int[]{0, 0, 0};
		gbl_pnlStrips.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnlStrips.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pnlStrips.setLayout(gbl_pnlStrips);
		
		lblNewLabel = new JLabel("=====PLAN=====");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		pnlStrips.add(lblNewLabel, gbc_lblNewLabel);
		
		scrollPane_3 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.gridx = 0;
		gbc_scrollPane_3.gridy = 1;
		pnlStrips.add(scrollPane_3, gbc_scrollPane_3);
		
		txtStrips = new JTextArea();
		txtStrips.setEditable(false);
		txtStrips.setTabSize(2);
		scrollPane_3.setViewportView(txtStrips);
	}

	/**
	 * Reset the view
	 */
	public void reset() {
		log.setText("");
		txtSelectFile.setText("Select an OWL-S file");
	}
	
	/**
	 * Update the action counter label
	 */
	public void updateLabelCounter() {
		lblActionCounter.setText("Number of Action : " + domain.listAction.size());
	}
	
	/**
	 * Add all inserted action (in the panel) to the domain file
	 */
	public void addActionToDomain() {

		if (listAction.size() > 0) {
			for (Action currAction : listAction) {
				
				// Check if action exists
				boolean isActionExist = false;
				for (Action act : domain.listAction) {
					
					if (currAction.name.equals(act.name)) {
						JOptionPane.showMessageDialog(frmOwlsTranslator, "Duplicate Action:" + currAction.name, "Info", JOptionPane.INFORMATION_MESSAGE);
						isActionExist = true;
						break;
	
					}
				}
				// End check if action exists
				
				if (isActionExist) continue;
				domain.addAction(currAction);
				
				// View action and reset view
				txtDomain.setText(domain.getPddl());
				updateLabelCounter();
				
				// Add paramSet
				for (int i = 0; i < currAction.paramPredicateList.size(); i++) {
					Object p = currAction.paramPredicateList.get(i);
					
					if(!paramSet.contains(p)) {
						if (p instanceof Param) {

							paramSet.add((Param)p);
							inputOptions.addElement((Param)p);
							outputOptions.addElement((Param)p);
						} else if (p instanceof Predicate) {

							paramSet.add((Predicate)p);
							inputOptions.addElement((Predicate)p);
							outputOptions.addElement((Predicate)p);
						}
					}
	
				}
			}
			String show = String.valueOf(listAction.size());
			if (listAction.size() > 1) {
				show += " actions have been successfully added";
			}
			else {
				show += " action has been successfully added";
			}
			
			JOptionPane.showMessageDialog(frmOwlsTranslator, show, "Success", JOptionPane.INFORMATION_MESSAGE);
			reset();
			listAction.clear();

		} else {
			 JOptionPane.showMessageDialog(frmOwlsTranslator, "Load an OWL-S file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
