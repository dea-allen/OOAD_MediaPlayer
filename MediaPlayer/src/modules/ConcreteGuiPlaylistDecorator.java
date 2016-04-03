package modules;

import java.awt.*;
import javax.swing.*;
import models.*;

public class ConcreteGuiPlaylistDecorator extends GuiDecorator 
{
    private static final String CONTROLLER = "PlaylistController";
    
    private JPanel playlistPanel;
    private JPanel showPlaylistPanel;
    private JButton showPlaylistButton;
    
    public JPanel openPlaylistPanel;
    
    private JPanel controlPlaylistPanel;
    private JList playlists;
    private JScrollPane playlistScrollPane;
    private JButton createPlaylistButton;
    private JButton deletePlaylistButton;
    private JButton addPlaylistButton;
    private JPanel selectedPlaylistPanel;
    private JScrollPane selectPlaylistScrollPane;
    private JButton deleteMediaButton;
    private JList medias;
   
    public ConcreteGuiPlaylistDecorator(GuiModel base) 
    {
        super(base);
        frame = base.frame;
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
        addPlaylistButton = new JButton("+");
        createPlaylistButton = new JButton("Create Playlist");
        deletePlaylistButton = new JButton("X");
        controlPlaylistPanel.add(addPlaylistButton);
        controlPlaylistPanel.add(createPlaylistButton);
        controlPlaylistPanel.add(deletePlaylistButton);
        playlistScrollPane = new JScrollPane();
        openPlaylistPanel.add(controlPlaylistPanel, BorderLayout.NORTH);
        openPlaylistPanel.add(playlistScrollPane, BorderLayout.CENTER);
    }
    public void setupSelectedPlaylistPanel()
    {
        selectedPlaylistPanel = new JPanel(new BorderLayout(1,1));
        playlists = new JList();
        // get playlists from PlaylistController or decorate PlayerController with PlaylistController?
        
    }
}
