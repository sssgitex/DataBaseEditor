package gui;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class DialogFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DialogFrame(String title) {
        super(title);
        setUndecorated(true);
        setVisible(true);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/plusicon.png")));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
