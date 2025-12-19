package game.rhythm;

import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;

@SuppressWarnings("restriction")
public class RhythmWindow {

	private static boolean fxInitialized = false;

	private Stage stage;
	private Canvas canvas;
	private GraphicsContext gc;
	private AnimationTimer timer;

	private PlayZone pz;
	private InputManager im;
	private GameStatus status;

	private int w;
	private int h;

	public RhythmWindow(PlayZone pz, InputManager im, GameStatus status, int width, int height) {
		this.pz = pz;
		this.im = im;
		this.status = status;
		this.w = width;
		this.h = height;

		initJavaFXToolkitIfNeeded(); // IMPORTANT
	}

	private void initJavaFXToolkitIfNeeded() {
		if (fxInitialized) {
			return;
		}
		fxInitialized = true;

		// Initialize JavaFX toolkit synchronously
		if (SwingUtilities.isEventDispatchThread()) {
			new JFXPanel();
		} else {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						new JFXPanel();
					}
				});
			} catch (Exception e) {
				throw new RuntimeException("Failed to initialize JavaFX toolkit", e);
			}
		}
	}

	public void start() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				stage = new Stage();
				Pane root = new Pane();
				root.setPrefSize(w, h);

				canvas = new Canvas(w, h);
				gc = canvas.getGraphicsContext2D();

				root.getChildren().add(canvas);

				Scene scene = new Scene(root, w, h);

				stage.setTitle("RhythMatic - RhythmWindow Test");
				stage.setScene(scene);
				stage.show();
				
			}
		});
	}

	public void close() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (stage != null) {
					stage.close();
					stage = null;
				}
			}
		});
	}

	public void render() {
	    if (gc == null || pz == null) {
	        return;
	    }

	    Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            gc.setFill(Color.BLACK);
	            gc.fillRect(0, 0, w, h);

	            double offsetX = (w - pz.getSize()) / 2;
	            double offsetY = (h - pz.getSize()) / 2;

	            gc.save();
	            gc.translate(offsetX, offsetY);
	            renderPlayZone();
	            gc.restore();
	        }
	    });
	}

	private void renderPlayZone() {

		// Draw Lanes
		Lane[] lanes = pz.getLanes();
		
		for (int i = 0; i < lanes.length; i++) {
			Lane lane = lanes[i];

			gc.setFill(Color.rgb(40, 40, 40));
			gc.fillRect(lane.getX(), lane.getY(), lane.getWidth(), lane.getHeight());

			gc.setStroke(Color.WHITE);
			gc.strokeRect(lane.getX(), lane.getY(), lane.getWidth(), lane.getHeight());
		}

		// Draw Nodes
		Node[] nodes = pz.getNodes();


		for (int i = 0; i < nodes.length; i++) {
			Node node = nodes[i];

			double s = pz.getLaneThickness();
			double half = s / 2;

			gc.setFill(node.getState() == State.PRESSED ? Color.YELLOW : Color.CYAN);

			gc.fillRect(
			    node.getX(),
			    node.getY(),
			    s,
			    s
			);

			gc.setStroke(Color.WHITE);
			gc.strokeRect(
			    node.getX(),
			    node.getY(),
			    s,
			    s
			);

		}
	}

	public void setUpdateCallBack(final Rhythm rhythm) {
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				rhythm.update();
			}
		};
		timer.start();
	}

	public void showResult() {
		// TODO later
	}
	
	public void renderInfoDisplay() {

	    // ---------- Pause ----------
	    gc.setFill(Color.WHITE);
	    gc.setFont(javafx.scene.text.Font.font(20));
	    gc.fillText("II", 20, 30);

	    // ---------- Score ----------
	    gc.setFont(javafx.scene.text.Font.font(18));
	    String scoreText = String.format("%07d", status.score);
	    gc.fillText(scoreText, w - 140, 30);

	    // ---------- HP bar (only if HP != null) ----------
	    if (status.HP != null) {
	        double barX = w / 2.0 - 180;
	        double barY = 18;
	        double barW = 360;
	        double barH = 14;

	        double hpRatio = Math.max(0, Math.min(1, status.HP / 100.0));

	        gc.setFill(Color.rgb(60, 60, 60));
	        gc.fillRect(barX, barY, barW, barH);

	        gc.setFill(Color.RED);
	        gc.fillRect(barX, barY, barW * hpRatio, barH);

	        gc.setStroke(Color.WHITE);
	        gc.strokeRect(barX, barY, barW, barH);
	    }

	    // Combo
	    gc.setFill(Color.WHITE);
	    gc.setFont(javafx.scene.text.Font.font(40));
	    gc.fillText(
	        Integer.toString(status.CurrentCombo),
	        w / 2.0 - 20,
	        h / 2.0 - 10
	    );

	    // Accuracy
	    gc.setFont(javafx.scene.text.Font.font(25));
	    String accText = String.format("%.2f%%", status.accuracy);
	    gc.fillText(
	        accText,
	        w / 2.0 - 35,
	        h / 2.0 + 20
	    );

	    // ---------- Song info (right side) ----------
	    double infoX = w - 220;
	    double infoY = 80;

	    gc.setFont(javafx.scene.text.Font.font(14));
	    gc.fillText(status.currentSong.title, infoX, infoY);

	    gc.setFont(javafx.scene.text.Font.font(12));
	    gc.fillText(status.currentSong.artist, infoX, infoY + 18);

	    // ---------- Progress bar ----------
	    double progW = 180;
	    double progH = 10;
	    double progY = infoY + 30;

	    double progress01 =
	            status.currentChart.getCurrentTime() /
	            status.currentChart.parent.length;

	    progress01 = Math.max(0, Math.min(1, progress01));

	    gc.setFill(Color.rgb(60, 60, 60));
	    gc.fillRect(infoX, progY, progW, progH);

	    gc.setFill(Color.CYAN);
	    gc.fillRect(infoX, progY, progW * progress01, progH);

	    gc.setStroke(Color.WHITE);
	    gc.strokeRect(infoX, progY, progW, progH);

	    // ---------- Difficulty ----------
	    gc.fillText(
	        String.format("%d",status.currentChart.getDisplayedDifficulty()),
	        infoX,
	        progY + 26
	    );
	}

}
