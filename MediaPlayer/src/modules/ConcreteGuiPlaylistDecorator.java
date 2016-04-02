package modules;

import java.awt.*;
import javax.swing.*;
import models.*;

public class ConcreteGuiPlaylistDecorator extends GuiDecorator 
{
    // variables to modify base model
    private final JFrame _frame;
    
    private JPanel playlistPanel;
    private JPanel showPlaylistPanel;
    private JButton showPlaylistButton;
    private JPanel openPlaylistPanel;
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
        _frame = base.frame;
    }
    
    @Override
    public void drawGui()
    {
        super.drawGui();
        playlistPanel = new JPanel(new BorderLayout());
        showPlaylistPanel = new JPanel(new GridLayout(1,1));
        showPlaylistButton = new JButton("Show");
        showPlaylistPanel.add(showPlaylistButton);
        
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
        
        selectedPlaylistPanel = new JPanel(new BorderLayout(1,1));
        
        
        playlistPanel.add(showPlaylistPanel, BorderLayout.WEST);
        playlistPanel.add(openPlaylistPanel, BorderLayout.CENTER);

        _frame.add(playlistPanel, BorderLayout.EAST);
        _frame.setSize(500, 300);   
        _frame.setVisible(true);
    }
    
}
