package game.menus;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.GameWindow;

public class Menu extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GameWindow window;
    private JButton btnPlay, btnStory, btnContinue, btnQuit;
	
	
	public Menu(GameWindow gameWindow) {
		
		// Create the window for the Main Menu
		this.window = gameWindow;

        setLayout(new BorderLayout());

        JLabel logo = new JLabel("RhythMatic", SwingConstants.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 64));
        logo.setBorder(BorderFactory.createEmptyBorder(60, 0, 40, 0));
        add(logo, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        btnPlay = new JButton("Play Music");
        btnStory = new JButton("Story Mode");
        btnContinue = new JButton("Continue Story");
        btnQuit = new JButton("Quit");

        styleButton(btnPlay);
        styleButton(btnStory);
        styleButton(btnContinue);
        styleButton(btnQuit);

        buttonPanel.add(btnPlay);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(btnStory);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(btnContinue);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(btnQuit);

        add(buttonPanel, BorderLayout.CENTER);
        
        
        // Add Action Listeners
        btnPlay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				window.showScreen("song_select");
			}
        	
        });
        
        btnStory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				window.startStoryMode();
				
			}
        	
        });
        
        btnContinue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Continue Story button pressed");
				//// TODO Read saved Story Mode
				
			}
        	
        });
        
        btnQuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Quit");
				System.exit(0);
				
			}
        	
        });
	    
	}


	private void styleButton(JButton btn) {
		btn.setFont(new Font("Arial", Font.PLAIN, 36));
	    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
	    btn.setMaximumSize(new Dimension(400, 80));
	    btn.setFocusPainted(false);
		
	}


	

}
