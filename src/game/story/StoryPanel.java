package game.story;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class StoryPanel extends JPanel{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// UI Components
    private JLabel backgroundLabel;
    private JLabel portraitLabel;
    private JTextArea textArea;
    private JLabel continueArrow;
    private JButton autoButton;
    private JButton skipButton;
    private JLabel speakerLabel;
    private JPanel textBox;


    // Typewriter Timer
    private Timer typeTimer;
    private String fullText = "";
    private int charIndex = 0;
    private boolean isTyping = false;
    private boolean autoMode = false;

    // Listener to notify StoryEngine
    private StoryAdvanceListener advanceListener;

    public StoryPanel(StoryAdvanceListener listener) {
        this.advanceListener = listener;
        initUI();
        initKeyBindings();
    }

    private void initUI() {
        setLayout(null);
        setBackground(Color.BLACK);

        // Background image
        backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 1280, 720);
        backgroundLabel.setOpaque(true);
        backgroundLabel.setBackground(Color.BLACK);
        add(backgroundLabel);

        // Character portrait
        portraitLabel = new JLabel();
        portraitLabel.setBounds(50, 150, 400, 500);
        add(portraitLabel);

        // Text box background
        textBox = new JPanel();
        textBox.setLayout(null);
        textBox.setBounds(0, 520, 1280, 200);
        textBox.setBackground(new Color(20, 20, 20, 180));
        textBox.setOpaque(true);
        add(textBox);
        
        //Speaker label
        speakerLabel = new JLabel();
        speakerLabel.setFont(new Font("Georgia", Font.BOLD, 22)); // Change font here
        speakerLabel.setForeground(new Color(220, 220, 220));
        speakerLabel.setBounds(40, 520 - 36, 400, 28); 
        speakerLabel.setVisible(false);
        add(speakerLabel);



        // Text display
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Georgia", Font.PLAIN, 24)); // Change text display font here
        textArea.setForeground(Color.WHITE);
        textArea.setBounds(40, 40, 1100, 120);
        textArea.setOpaque(false);
        textBox.add(textArea);

        // Continue arrow 
        continueArrow = new JLabel("V");
        continueArrow.setFont(new Font("SansSerif", Font.BOLD, 32));
        continueArrow.setForeground(Color.WHITE);
        continueArrow.setBounds(1180, 150, 40, 40);
        continueArrow.setVisible(false);
        textBox.add(continueArrow);

        // Auto mode toggle
        autoButton = new JButton("Auto");
        autoButton.setBounds(1800, 20, 100, 30);
        autoButton.addActionListener(e -> toggleAutoMode());
        add(autoButton);

        // Skip button
        skipButton = new JButton("Skip");
        skipButton.setBounds(1800, 60, 100, 30);
        skipButton.addActionListener(e -> attemptSkip());
        add(skipButton);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleClickAnywhere();
            }
        });
        
        setComponentZOrder(speakerLabel, 0);

    }
    
    private void handleClickAnywhere() {
        if (isTyping) {
            finishTypingImmediately();
        } else {
            continueArrow.setVisible(false);
            advanceListener.advanceRequested();
        }
    }

    
    public void showBackground(Image bg) {
        backgroundLabel.setIcon(new ImageIcon(bg));
    }

    public void showPortrait(Image img) {
        portraitLabel.setIcon(new ImageIcon(img));
    }

    public void clearPortrait() {
        portraitLabel.setIcon(null);
    }

    public void displayText(String text) {
        if (typeTimer != null) {
            typeTimer.stop();
        }

        fullText = text;
        charIndex = 0;
        isTyping = true;
        
        adjustTextBoxHeight(text);

        // Reset all text
        textArea.setText("");
        textArea.setCaretPosition(0);
        textArea.revalidate();
        textArea.repaint();

        continueArrow.setVisible(false);

        typeTimer = new Timer(18, e -> typewriterStep());
        typeTimer.start();
    }


    private void typewriterStep() {
        if (charIndex >= fullText.length()) {
            typeTimer.stop();
            isTyping = false;
            continueArrow.setVisible(true);

            if (autoMode) {
                Timer delay = new Timer(1000, e -> advanceListener.advanceRequested());
                delay.setRepeats(false);
                delay.start();
            }
            return;
        }

        charIndex++;
        textArea.setText(fullText.substring(0, charIndex));
    }
    
    public void setSpeaker(String speaker) {
        if (speaker == null || speaker.isEmpty() || speaker.equalsIgnoreCase("Narrator")) {
            speakerLabel.setVisible(false);
        } else {
            speakerLabel.setText(speaker);
            speakerLabel.setVisible(true);
        }
    }
    
    private void adjustTextBoxHeight(String text) {
        Font font = textArea.getFont();
        FontMetrics fm = textArea.getFontMetrics(font);

        int maxWidth = textArea.getWidth();
        int lineHeight = fm.getHeight();

        int lines = 1;
        int lineWidth = 0;

        for (String word : text.split(" ")) {
            int w = fm.stringWidth(word + " ");
            if (lineWidth + w > maxWidth) {
                lines++;
                lineWidth = w;
            } else {
                lineWidth += w;
            }
        }

        int neededHeight = lines * lineHeight + 60;
        neededHeight = Math.min(neededHeight, 320);

        textBox.setBounds(0, 720 - neededHeight, 1280, neededHeight);
        speakerLabel.setLocation(40, textBox.getY() - 36);
        textArea.setBounds(40, 40, 1100, neededHeight - 60);
        continueArrow.setBounds(1180, neededHeight - 45, 40, 40);
    }




    

    // Pop out a dialogue box if user wants to skip
    private void attemptSkip() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to skip?",
                "Skip Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            advanceListener.skipRequested();
        }
    }

    private void toggleAutoMode() {
        autoMode = !autoMode;
        autoButton.setText(autoMode ? "Auto: ON" : "Auto: OFF");
    }

    private void finishTypingImmediately() {
        if (isTyping) {
            typeTimer.stop();
            textArea.setText(fullText);
            isTyping = false;
            continueArrow.setVisible(true);
        }
    }

   

    // Bind Space, Enter and Page Down to Next Dialogue
    private void initKeyBindings() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        String[] keys = {"SPACE", "ENTER", "PAGEDOWN"};
        for (String key : keys) {
            im.put(KeyStroke.getKeyStroke(key), "ADVANCE");
        }

        am.put("ADVANCE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAdvanceKey();
            }
        });
    }

    private void handleAdvanceKey() {
        if (isTyping) {
            finishTypingImmediately();
        } else {
            continueArrow.setVisible(false);
            advanceListener.advanceRequested();
        }
    }

    // ------------------------------
    //   CALLBACK INTERFACE FOR ENGINE
    // ------------------------------

    public interface StoryAdvanceListener {
        void advanceRequested();
        void skipRequested();
    }

}
