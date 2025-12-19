package game.rhythm;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@SuppressWarnings("restriction")
public class InputManager {

    private final Judgement judgement;
    private final GameClock clock;

    // The four corner nodes this input manager controls
    private Node topLeft;
    private Node topRight;
    private Node bottomLeft;
    private Node bottomRight;

    // Edge-trigger flags (true for 1 update tick after a press)
    private boolean pressTL;
    private boolean pressTR;
    private boolean pressBL;
    private boolean pressBR;

    // Pause (placeholder)
    private boolean pausePressed;

    public InputManager(Judgement j, GameClock clock) {
        this.judgement = j;
        this.clock = clock;

        pressTL = false;
        pressTR = false;
        pressBL = false;
        pressBR = false;

        pausePressed = false;
    }

    /**
     * Call this once after you create the PlayZone / Nodes.
     */
    public void bindCornerNodes(Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    /**
     * Call this once when your JavaFX Scene exists.
     */
    public void attachToScene(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyPressed(event);
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyReleased(event);
            }
        });
    }

    private void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.ESCAPE) {
            pausePressed = true;
            return;
        }

        if (code == KeyCode.W) {
            pressTL = true;
        } else if (code == KeyCode.R) {
            pressTR = true;
        } else if (code == KeyCode.Z) {
            pressBL = true;
        } else if (code == KeyCode.C) {
            pressBR = true;
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        // You can add "hold" mechanics later here.
        // For now, we only care about the press pulse.
    }

    /**
     * Called every frame by your game loop (Rhythm.update()).
     * time is typically your clock time (ns) or whatever you use.
     */
    public void update(long time) {

        // Pause placeholder
        if (pausePressed) {
            // If you later add a pause system, trigger it here.
            // Example (if Judgement later supports it): judgement.requestPause();
            pausePressed = false;
        }

        // Activation pulses
        if (pressTL) {
            activateNode(topLeft);
            pressTL = false;
        }

        if (pressTR) {
            activateNode(topRight);
            pressTR = false;
        }

        if (pressBL) {
            activateNode(bottomLeft);
            pressBL = false;
        }

        if (pressBR) {
            activateNode(bottomRight);
            pressBR = false;
        }
    }

    private void activateNode(Node node) {
        if (node == null) {
            return;
        }

        node.setState(State.PRESSED);

        // If later you want “flash for 1 frame”, you can reset elsewhere
        // (or add a small timer in Node).
    }
}
