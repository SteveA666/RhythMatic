package game.rhythm;

import javax.swing.SwingUtilities;

import javafx.embed.swing.JFXPanel;

@SuppressWarnings("restriction")
public final class FxBootstrap {

    private static boolean initialized = false;

    private FxBootstrap() { }

    public static void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        // Ensure JavaFX toolkit is initialized.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFXPanel();
            }
        });
    }
}
