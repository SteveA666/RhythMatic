package game.menus;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import game.Chart;
import game.GameWindow;
import game.Song;
import game.SongDatabase;
import game.rhythm.FxBootstrap;
import game.rhythm.Rhythm;

public class SongSelection extends JPanel {

    private static final long serialVersionUID = 1L;

    private GameWindow window;

    private JList<String> songList;
    private DefaultListModel<String> listModel;

    private JLabel header;
    private JLabel lblTitle, lblArtist, lblBPM, lblLength;

    private JPanel difficultyPanel;

    private JButton btnPlay, btnBack;

    private Song selectedSong;
    private Chart selectedChart;

    public SongSelection(GameWindow window) {
        this.window = window;

        setLayout(new BorderLayout());

        // ===== HEADER =====
        header = new JLabel("Select Song", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 48));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // ===== SONG LIST =====
        listModel = new DefaultListModel<>();
        reloadSongList();

        songList = new JList<>(listModel);
        songList.setFont(new Font("Arial", Font.PLAIN, 28));

        JScrollPane scroll = new JScrollPane(songList);
        scroll.setPreferredSize(new Dimension(500, 1080));
        add(scroll, BorderLayout.WEST);

        // ===== INFO PANEL =====
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        lblTitle  = new JLabel("Title: ");
        lblArtist = new JLabel("Artist: ");
        lblBPM    = new JLabel("BPM: ");
        lblLength = new JLabel("Length: ");

        lblTitle.setFont(new Font("Arial", Font.PLAIN, 32));
        lblArtist.setFont(new Font("Arial", Font.PLAIN, 32));
        lblBPM.setFont(new Font("Arial", Font.PLAIN, 32));
        lblLength.setFont(new Font("Arial", Font.PLAIN, 32));

        infoPanel.add(lblTitle);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(lblArtist);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(lblBPM);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(lblLength);

        // Difficulty panel inside info panel
        difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.X_AXIS));
        difficultyPanel.setBorder(BorderFactory.createTitledBorder("Difficulty"));
        difficultyPanel.add(Box.createHorizontalStrut(10));

        infoPanel.add(Box.createVerticalStrut(40));
        infoPanel.add(difficultyPanel);

        add(infoPanel, BorderLayout.CENTER);

        // ===== BOTTOM BUTTONS =====
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Arial", Font.PLAIN, 32));

        btnPlay = new JButton("Play");
        btnPlay.setFont(new Font("Arial", Font.PLAIN, 32));
        btnPlay.setEnabled(false); // disabled until a chart is selected

        bottomPanel.add(btnBack);
        bottomPanel.add(Box.createHorizontalStrut(40));
        bottomPanel.add(btnPlay);

        add(bottomPanel, BorderLayout.SOUTH);

        // ===== EVENT HANDLERS =====

        // Song selection listener
        songList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int index = songList.getSelectedIndex();

                    if (index < 0 || SongDatabase.songs.size() == 0) {
                        btnPlay.setEnabled(false);
                        return;
                    }

                    selectedSong = SongDatabase.songs.get(index);
                    selectedChart = null;
                    updateSongInfo();
                    updateDifficultyButtons();
                }
            }
        });

        // Back button
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.showScreen("menu");
            }
        });

        // Play button (will later start rhythm mode)
        btnPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedChart != null) {
                	
                	FxBootstrap.init();
                    new Rhythm(selectedChart, false);
                }
            }
        });
    }

    // Update Song Info
    private void updateSongInfo() {
        lblTitle.setText("Title: " + selectedSong.title);
        lblArtist.setText("Artist: " + selectedSong.artist);
        lblBPM.setText("BPM: " + selectedSong.bpm);
        lblLength.setText("Length: " + selectedSong.length + " sec");
    }

    // Reload the Song List
    public void reloadSongList() {
        listModel.clear();

        if (SongDatabase.songs.size() == 0) {
            listModel.addElement("No Songs Loaded");
            return;
        }

        for (Song s : SongDatabase.songs) {
            listModel.addElement(s.title);
        }
    }

    // Update Difficulty buttons
    private void updateDifficultyButtons() {
        difficultyPanel.removeAll();

        if (selectedSong.charts.size() == 0) {
            JLabel none = new JLabel("No charts available");
            none.setFont(new Font("Arial", Font.PLAIN, 28));
            difficultyPanel.add(none);
            refreshDifficultyPanel();
            return;
        }

        for (Chart chart : selectedSong.charts) {
            JButton diffButton = new JButton(
                chart.difficultyLevel + " Lv." + chart.displayedDifficulty
            );
            diffButton.setFont(new Font("Arial", Font.PLAIN, 24));

            diffButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selectedChart = chart;
                    highlightSelectedDifficulty(diffButton);
                    btnPlay.setEnabled(true);
                }
            });

            difficultyPanel.add(diffButton);
            difficultyPanel.add(Box.createHorizontalStrut(20));
        }

        refreshDifficultyPanel();
    }

    // Highlight selected difficulty visually
    private void highlightSelectedDifficulty(JButton selectedButton) {
        for (int i = 0; i < difficultyPanel.getComponentCount(); i++) {
            if (difficultyPanel.getComponent(i) instanceof JButton) {
                selectedButton.setFont(new Font("Arial", Font.PLAIN, 24));
            }
        }
        selectedButton.setFont(new Font("Arial", Font.BOLD, 28));
    }

    private void refreshDifficultyPanel() {
        difficultyPanel.revalidate();
        difficultyPanel.repaint();
    }
}
