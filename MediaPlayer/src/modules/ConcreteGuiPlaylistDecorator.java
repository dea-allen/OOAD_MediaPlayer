package modules;

import controllers.ModuleController;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.swing.*;
import models.*;
import view.GuiView;

public class ConcreteGuiPlaylistDecorator extends GuiDecorator 
{
    private static final String JSON_PATH = "./src/data/";
    private static final String JSON = ".json";
    
    private static final String CONTROLLER = "PlaylistController";
    private static final String MODULE_DIR = "./src/data/";
    private static final String MODULES_JSON = "playlists.json";
    
    private JPanel playlistPanel;
    private JPanel showPlaylistPanel;
    private JButton showPlaylistButton;
    
    private JPanel controlPlaylistPanel;
    private JScrollPane playlistScrollPane;
    private JButton createPlaylistButton;
    private JButton deletePlaylistButton;
    private JButton addToPlaylistButton;
    private JPanel selectedPlaylistPanel;
    private JScrollPane selectPlaylistScrollPane;
    private JButton deleteMediaButton;
    private JList medias;
    
    public JList playlists;
    public JPanel openPlaylistPanel;
    public DefaultListModel playlistModel;
    public DefaultListModel mediaModel;
    
    public ConcreteGuiPlaylistDecorator(GuiModel base) 
    {
        super(base);
        frame = base.frame;
        audioMediaPlayerComponent = base.audioMediaPlayerComponent;
        worker = base.worker;
        seekSlider = base.seekSlider;
        mousePressedPlaying = base.mousePressedPlaying;
        currentMediaModel = base.currentMediaModel;
        selectedMedia = base.selectedMedia;
    }
    
    
    @Override
    public GuiModel drawGui()
    {
        super.drawGui();
        
        setupShowPlaylistPanel();
        setupOpenPlaylistPanel();
        setupSelectedPlaylistPanel();
        
        playlistPanel.add(showPlaylistPanel, BorderLayout.WEST);
        playlistPanel.add(openPlaylistPanel, BorderLayout.CENTER);
        playlistPanel.add(selectedPlaylistPanel, BorderLayout.EAST);

        frame.add(playlistPanel, BorderLayout.EAST);
        frame.setSize(800, 300);   
        frame.setVisible(true);
        
        return this;
    }
    public void setupShowPlaylistPanel()
    {
        playlistPanel = new JPanel(new BorderLayout());
        showPlaylistPanel = new JPanel(new GridLayout(1,1));
        showPlaylistButton = new JButton("Show");
        showPlaylistButton.setActionCommand(CONTROLLER + ".showPlaylists");
        showPlaylistPanel.add(showPlaylistButton);
    }
    public void setupOpenPlaylistPanel()
    {
        openPlaylistPanel = new JPanel(new BorderLayout());
        controlPlaylistPanel = new JPanel(new GridLayout(1,3));
        addToPlaylistButton = new JButton("+");
        addToPlaylistButton.setActionCommand(CONTROLLER + ".addToPlaylist");
        createPlaylistButton = new JButton("Create Playlist");
        createPlaylistButton.setActionCommand(CONTROLLER + ".createPlaylist");
        deletePlaylistButton = new JButton("X");
        deletePlaylistButton.setActionCommand(CONTROLLER + ".deletePlaylist");
        controlPlaylistPanel.add(addToPlaylistButton);
        controlPlaylistPanel.add(createPlaylistButton);
        controlPlaylistPanel.add(deletePlaylistButton);
        
        playlistModel = new DefaultListModel();
        playlistModel = populateList();
        playlists = new JList(playlistModel);
        playlists.addMouseListener(mouseListener);
        playlistScrollPane = new JScrollPane(playlists);
        
        mediaModel = new DefaultListModel();
        medias = new JList(mediaModel);
        medias.addMouseListener(selectMediaListener);
        selectPlaylistScrollPane = new JScrollPane(medias);
        
        openPlaylistPanel.add(playlistScrollPane, BorderLayout.CENTER);
        openPlaylistPanel.add(selectPlaylistScrollPane, BorderLayout.EAST);
        openPlaylistPanel.add(controlPlaylistPanel, BorderLayout.NORTH);
    }
    public void setupSelectedPlaylistPanel()
    {
        selectedPlaylistPanel = new JPanel(new BorderLayout(1,1));   
    }
    private static DefaultListModel populateList()
    {
        DefaultListModel playlists = new DefaultListModel();
        
        File moduleFile = new File(MODULE_DIR + MODULES_JSON);
        JsonReader reader;
        try 
        {
            reader = Json.createReader(new FileInputStream(moduleFile));
            JsonArray modules = reader.readArray();
            reader.close();
            for(int i = 0; i < modules.size(); i++)
            {
                playlists.addElement(modules.getJsonObject(i).getString("Playlist"));
            }
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return playlists;
    }
    
    MouseListener mouseListener = new MouseAdapter() 
    {
        public void mouseClicked(MouseEvent e) 
        {
            if (e.getClickCount() == 2) 
            {
                mediaModel.removeAllElements();
                String selectedItem = (String) playlists.getSelectedValue();
                File file = new File(JSON_PATH + selectedItem + JSON);
                JsonReader reader;
                try
                {
                    reader = Json.createReader(new FileInputStream(file));
                    JsonArray modules = reader.readArray();
                    reader.close();
                    for(int i = 0; i < modules.size(); i++)
                    {
                        mediaModel.addElement(modules.getJsonObject(i).getString(selectedItem));
                    }
                }
                catch (Exception ex) 
                {
                    Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };
    //TODO: REFACTOR - shouldn't get gui here
    MouseListener selectMediaListener = new MouseAdapter() {
        public void mouseClicked(MouseEvent e)
        {
            GuiModel gui = GuiView.getView(null).getGuiModel();
            gui.selectedMedia = medias.getSelectedValue().toString();
        }
    };
}
