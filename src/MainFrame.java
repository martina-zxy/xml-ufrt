import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainFrame {

	private JFrame frame;
	private JTextField txtSelectFile;
	final JFileChooser fc = new JFileChooser();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
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
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Choose File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnval = fc.showOpenDialog(frame);
				System.out.println(returnval);
				
				if (returnval == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            
		            Parser parser = new Parser();
		            parser.parse(file);
		            //This is where a real application would open the file.
		            System.out.println("Opening: " + file.getParent() + ".\n");
		            
		        } else {
		        	System.out.println("Open command cancelled by user.\n");
		        }
			}
		});
		btnNewButton.setBounds(10, 11, 89, 23);
		btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		frame.getContentPane().add(btnNewButton);
		
		txtSelectFile = new JTextField();
		txtSelectFile.setHorizontalAlignment(SwingConstants.CENTER);
		txtSelectFile.setText("Select an OWL-S file");
		txtSelectFile.setEditable(false);
		txtSelectFile.setBounds(109, 13, 315, 20);
		frame.getContentPane().add(txtSelectFile);
		txtSelectFile.setColumns(10);
	}
}
