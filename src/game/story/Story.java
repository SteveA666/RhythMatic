package game.story;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import game.GameWindow;

public class Story {

    private SaveFile save = null; // TODO implement story saving later
    private List<Scene> scenes;
    private StoryPanel panel;

    // Cursor for tracking position
    private int currentSceneIndex = 0;
    private int currentLineIndex = 0;

    public Story(StoryPanel panel) {
        this.panel = panel;
        scenes = new ArrayList<>();
    }

    // Public Control Methods

    public void start() {
        if (scenes.isEmpty()) {
            System.err.println("[Story] No scenes loaded!");
            return;
        }
        currentSceneIndex = 0;
        currentLineIndex = 0;
        showCurrentLine();
    }

    public void next() {
        Scene scene = scenes.get(currentSceneIndex);

        // Move to next line
        currentLineIndex++;

        // End of scene?
        if (currentLineIndex >= scene.lines.size()) {
            goToNextScene();
            return;
        }
        
        

        showCurrentLine();
    }

    public void skip() {
        // For now, skip to the next scene.
        // Later, skip to next battle or choice.
        goToNextScene();
    }

    // Internal Logic for Story Mode

    private void goToNextScene() {
        currentSceneIndex++;
        currentLineIndex = 0;

        if (currentSceneIndex >= scenes.size()) {
            endStory();
            return;
        }

        showCurrentLine();
    }

    private void endStory() {
        System.out.println("[Story] Story finished.");
        panel.setVisible(false);
        GameWindow gw = (GameWindow) SwingUtilities.getWindowAncestor(panel);
        gw.showScreen("credits");
        gw.creditsPanel.startCredits();
        // return to credits
        // GameWindow.showMenu();
    }

    private void showCurrentLine() {
        Scene scene = scenes.get(currentSceneIndex);
        DialogueLine line = scene.lines.get(currentLineIndex);

        // Show background
        if (line.background != null) {
            panel.showBackground(StoryAssets.loadImage(line.background));
        }

        // Show portrait
        if (line.portrait != null) {
            panel.showPortrait(StoryAssets.loadImage(line.portrait));
        } else {
            panel.clearPortrait();
        }

        // If this line triggers a rhythm mode
        if (line.triggersBattle) {
            triggerBattle(line);
            return;
        }

        // Display text with typewriter effect
     // Set speaker (StoryPanel decides whether to show it)
        panel.setSpeaker(line.speaker);

        // Display text only (NO speaker prefix)
        panel.displayText(line.text);

    }

    
    // ------------------------------
    //  RHYTHM TRIGGER (PLACEHOLDER)
    // ------------------------------

    private void triggerBattle(DialogueLine line) {
        System.out.println("[Story] Triggering battle...");

        // Hide story panel while rhythm plays
        //panel.setVisible(false);

        // TODO hook into your rhythm launcher
        // RhythmLauncher.startGame(line.battleChart, result -> {
        //     panel.setVisible(true);
        //     next(); // continue story
        // });
    }

    // ------------------------------
    //  LOADING SCENES
    // ------------------------------

    public void addScene(Scene scene) {
        scenes.add(scene);
    }

    public void loadScenes(List<Scene> loadedScenes) {
        scenes.clear();
        scenes.addAll(loadedScenes);
    }
}

