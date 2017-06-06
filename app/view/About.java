package view;

import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JList;
import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class About extends JDialog {
	private JTextField authorsTextField;

	public About() {
		setBounds(500, 200, 250, 280);
		getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("Organizer");
		titleLabel.setBounds(10, 0, 214, 30);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(titleLabel);
		
		JLabel subTitleLabel = new JLabel("2017, FTIMS");
		subTitleLabel.setForeground(Color.DARK_GRAY);
		subTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		subTitleLabel.setBounds(10, 30, 214, 14);
		getContentPane().add(subTitleLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 52, 234, 2);
		getContentPane().add(separator);
		
		JLabel authorsLabel = new JLabel("Tw\u00F3rcy");
		authorsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		authorsLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		authorsLabel.setBounds(10, 65, 214, 14);
		getContentPane().add(authorsLabel);
		
		authorsTextField = new JTextField();
		authorsTextField.setHorizontalAlignment(SwingConstants.CENTER);
		authorsTextField.setEditable(false);
		authorsTextField.setText("Pawe\u0142 Znamiec, Pawe\u0142 Cichocki");
		authorsTextField.setBorder(null);
		authorsTextField.setBounds(10, 82, 214, 20);
		getContentPane().add(authorsTextField);
		authorsTextField.setColumns(10);
		
		JLabel libsLabel = new JLabel("U\u017Cyte biblioteki");
		libsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		libsLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		libsLabel.setBounds(10, 142, 214, 14);
		getContentPane().add(libsLabel);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(10, 167, 214, 63);
		editorPane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
		editorPane.setEditable(false);
		editorPane.setBackground(null);
		editorPane.setText("<a href=\"https://github.com/ical4j\">iCal4j 2.0.0</a><br/>" +
				"<a href=\"https://bitbucket.org/xerial/sqlite-jdbc\">SQLite JDBC 3.18.0</a><br/>" +
				"<a href=\"http://x-stream.github.io\">XStream 1.4.10</a>");
		getContentPane().add(editorPane);
		
		editorPane.addHyperlinkListener(new HyperlinkListener() {
		    public void hyperlinkUpdate(HyperlinkEvent e) {
		        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		        	
		        	if(Desktop.isDesktopSupported()) {
		        	    try {
							Desktop.getDesktop().browse(e.getURL().toURI());
							
						} catch (IOException except) {
							except.printStackTrace();
						} catch (URISyntaxException except) {
							except.printStackTrace();
						}
		        	}
		        }
		    }
		});
		
		
	}
}
