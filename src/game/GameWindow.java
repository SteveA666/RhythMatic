package game;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.story.CreditsPanel;
import game.story.DialogueLine;
import game.story.Scene;
import game.story.Story;
import game.story.StoryPanel;
import game.menus.Menu;
import game.menus.SongSelection;

public class GameWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private CardLayout layout;
    private JPanel container;

    public Menu menuPanel;
    public SongSelection songSelectPanel;
    public StoryPanel storyPanel;
    public CreditsPanel creditsPanel;

    private Story story;

    public GameWindow() {

        super("RhythMatic");

        layout = new CardLayout();
        container = new JPanel(layout);

        SongDatabase.loadSongs(); 

        // Create all screens
        menuPanel = new Menu(this);
        songSelectPanel = new SongSelection(this);

        songSelectPanel.reloadSongList();

        // ---- Create StoryPanel + attach listener to engine ----
        storyPanel = new StoryPanel(new StoryPanel.StoryAdvanceListener() {
            @Override
            public void advanceRequested() {
                if (story != null) {
                    story.next();
                }
            }

            @Override
            public void skipRequested() {
                if (story != null) {
                    story.skip();
                }
            }
        });

        // Create Story engine
        story = new Story(storyPanel);
        story.loadScenes(makeTestScenes());     // temporary until loader is added
        
        // Create Panel for Credits page
        creditsPanel = new CreditsPanel(() -> showScreen("menu"));

        // Add screens to container
        container.add(menuPanel, "menu");
        container.add(songSelectPanel, "song_select");
        container.add(storyPanel, "story");
        container.add(creditsPanel, "credits");

        add(container);
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        showScreen("menu");
    }

    public void showScreen(String name) {
        layout.show(container, name);
    }

    // -------------------------------------------------------------------
    // TEMP TEST SCENE (replace with real loader later)
    // -------------------------------------------------------------------
    private java.util.List<Scene> makeTestScenes() {

        java.util.List<Scene> scenes = new java.util.ArrayList<>();

        Scene s = new Scene();
        s.id = 0;
        s.lines = new java.util.ArrayList<>();

        DialogueLine l1 = new DialogueLine();
        l1.speaker = "Narrator";
        l1.text =
        		"In the neon-lit streets of Maplesville,"
        		+"the remnants of a once-thriving city lay scattered like the bones of a long-dead giant,"
        		+ "a haunting testament to a civilization's downfall.";
        l1.background = null;
        l1.portrait = null;
        s.lines.add(l1);

        DialogueLine l2 = new DialogueLine();
        l2.speaker = "Narrator";
        l2.text =
        		" Skyscrapers, once proud glass-and-steel titans, "+
        		"now stood as skeletal silhouettes against the bruised night sky,"+
        		" their hollow windows staring blankly into the void.\n"+
        		"The wind carried a mournful whistle through cracked pavement and debris-strewn alleys,\n" + 
        		"weaving between rusted streetlamps that flickered in uneven pulses, like a failing heartbeat.";
        s.lines.add(l2);

        DialogueLine l3 = new DialogueLine();
        l3.speaker = "Narrator";
        l3.text =
                "Their glass facades, which had once captured the morning sun and reflected the ambitions of millions,\n"+
                " were now reduced to jagged shards that jutted like broken teeth,\n" + 
                " glinting in the dim light as if mocking the dreams they once sheltered. ";
        s.lines.add(l3);

        DialogueLine l4 = new DialogueLine();
        l4.speaker = "Narrator";
        l4.text =
        		"The streets were a graveyard of memory."+
        		"Rusted cars, their license plates curling with corrosion," + 
        		"sat frozen in place where their owners had abandoned them, some with doors still ajar, " + 
        		"as though the driver might return at any moment."+
        		"Overturned trash bins spilled faded photographs, cracked toys,"+ 
        		"and the brittle shells of once-important documents onto the asphalt." + 
        		"Tattered banners advertising festivals and concerts long past flapped weakly in the stale wind," + 
        		"their slogans barely legible, whispering to no one." ;
        s.lines.add(l4);

        DialogueLine l5 = new DialogueLine();
        l5.speaker = "14505";
        l5.text = "Statrep, no contact with enemy. Over.";
        l5.triggersBattle = false;
        s.lines.add(l5);
        
        DialogueLine l6 = new DialogueLine();
        l6.speaker = "Narrator";
        l6.text =
                "Tattered banners hung in slow agony from bent poles and cracked facades,  " +
                "their edges frayed into curling strands that danced reluctantly in the bitter wind. ";
        l6.triggersBattle = false;
        s.lines.add(l6);

        DialogueLine l7 = new DialogueLine();
        l7.speaker = "Narrator";
        l7.text =
                " Graffiti sprawled across once-pristine corporate surfaces in jagged, furious letters, " +
                "each word etched like a wound into the bones of the city. ";
        l7.triggersBattle = false;
        s.lines.add(l7);

        DialogueLine l8 = new DialogueLine();
        l8.speaker = "Narrator";
        l8.text =
                "The graffiti caught her eye as she moved past another crumbling wall, its vivid colors jarring against the drab grays and browns of decay. " +
                " \"DEATH TO THE CHANCELLOR,\" it screamed in jagged red letters, the paint still fresh enough to glisten faintly in the failing light.";
        l8.triggersBattle = false;
        s.lines.add(l8);

        DialogueLine l9 = new DialogueLine();
        l9.speaker = "Narrator";
        l9.text =
                "14505 looked up, her gaze sweeping across the remnants of what was once a vibrant city. " +
                "The road signs, their green paint faded and peeling, still clung stubbornly to the rusting poles";
        l9.triggersBattle = false;
        s.lines.add(l9);

        DialogueLine l10 = new DialogueLine();
        l10.speaker = "14505";
        l10.text =
                "Queen Street West. " +
                "University Avenue.";
        l10.triggersBattle = false;
        s.lines.add(l10);
        
        DialogueLine l11 = new DialogueLine();
        l11.speaker = "";
        l11.text = "Its name was Toronto, and the word echoed in her mind like a half-remembered song." +
        			" People had meandered down the wide streets, laughter spilling into the summer air,"+
        			" mingling with the rhythmic ding of streetcar bells and the hiss of their brakes as they slid into stations.";
        l11.background = null;
        l11.portrait = null;
        l11.triggersBattle = false;
        s.lines.add(l11);

        DialogueLine l12 = new DialogueLine();
        l12.speaker = "";
        l12.text = "";
        l12.background = null;
        l12.portrait = null;
        l12.triggersBattle = false;
        s.lines.add(l12);

        DialogueLine l13 = new DialogueLine();
        l13.speaker = "";
        l13.text = "";
        l13.background = null;
        l13.portrait = null;
        l13.triggersBattle = false;
        s.lines.add(l13);

        DialogueLine l14 = new DialogueLine();
        l14.speaker = "";
        l14.text = "";
        l14.background = null;
        l14.portrait = null;
        l14.triggersBattle = false;
        s.lines.add(l14);

        DialogueLine l15 = new DialogueLine();
        l15.speaker = "";
        l15.text = "";
        l15.background = null;
        l15.portrait = null;
        l15.triggersBattle = false;
        s.lines.add(l15);

        DialogueLine l16 = new DialogueLine();
        l16.speaker = "";
        l16.text = "";
        l16.background = null;
        l16.portrait = null;
        l16.triggersBattle = false;
        s.lines.add(l16);

        DialogueLine l17 = new DialogueLine();
        l17.speaker = "";
        l17.text = "";
        l17.background = null;
        l17.portrait = null;
        l17.triggersBattle = false;
        s.lines.add(l17);

        DialogueLine l18 = new DialogueLine();
        l18.speaker = "";
        l18.text = "";
        l18.background = null;
        l18.portrait = null;
        l18.triggersBattle = false;
        s.lines.add(l18);

        DialogueLine l19 = new DialogueLine();
        l19.speaker = "";
        l19.text = "";
        l19.background = null;
        l19.portrait = null;
        l19.triggersBattle = false;
        s.lines.add(l19);

        DialogueLine l20 = new DialogueLine();
        l20.speaker = "1";
        l20.text = "1";
        l20.background = null;
        l20.portrait = null;
        l20.triggersBattle = true;
        s.lines.add(l20);



        scenes.add(s);
        return scenes;
    }

    // Provide access from menu
    public void startStoryMode() {
        story.start();
        showScreen("story");
    }
}