package game.story;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class CreditsPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

    private Image background;
    private JTextArea creditsArea;
    private Timer scrollTimer;
    private int yOffset = 1080;   // start offscreen (below)

    // Callback to return to menu
    private Runnable exitCallback;

    public CreditsPanel(Runnable onExit) {
        this.exitCallback = onExit;
        initUI();
        initKeyAndMouse();
    }

    private void initUI() {
        setLayout(null);

        // Load optional background
        // You can change this to an actual file path later
        background = null; // StoryAssets.loadImage("credits_bg.png");

        // Credits text
        creditsArea = new JTextArea();
        creditsArea.setEditable(false);
        creditsArea.setOpaque(false);
        creditsArea.setForeground(Color.BLACK);
        creditsArea.setFont(new Font("Serif", Font.PLAIN, 32));
        creditsArea.setText(getCreditsText());

        creditsArea.setBounds(200, yOffset, 1500, 2000); // very tall to allow scrolling
        add(creditsArea);

        // Scrolling animation (smooth)
        scrollTimer = new Timer(20, e -> {
            yOffset -= 1; // scroll speed
            creditsArea.setLocation(200, yOffset);

            // Once credits finish scrolling off the top
            if (yOffset < -1800) {
                scrollTimer.stop();
                exitToMenu();
            }
        });
    }

    private void initKeyAndMouse() {
        // Click anywhere to exit
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                exitToMenu();
            }
        });

        // ESC or ENTER or SPACE also exit
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke("ESCAPE"), "EXIT");
        im.put(KeyStroke.getKeyStroke("ENTER"), "EXIT");
        im.put(KeyStroke.getKeyStroke("SPACE"), "EXIT");

        am.put("EXIT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitToMenu();
            }
        });
    }

    /** Called by Story engine when story finishes */
    public void startCredits() {
        yOffset = 1080; // reset scroll position
        creditsArea.setLocation(200, yOffset);
        scrollTimer.start();
    }

    private void exitToMenu() {
        scrollTimer.stop();
        if (exitCallback != null)
            exitCallback.run();
    }
    
    private String getCreditsText() {
        return 
            "RhythMatic\n\n" +
            "A Rhythm Game Project by Waterjet Studios\n\n" +
            "Developed by: SteveA666\n" +
            "Special Thanks:\n" +
            "  - Inspired by rhythm games\n" +
            "  - Music community\n\n" +
            "This game was developed entirely in Java \n"+ 
            "using Swing and JavaFX via Eclipse\n\n" +
            "Thank you for playing!\n" +
            "See you next time.\n";
    }

}
