package modules;

import java.awt.*;
import javax.swing.*;
import models.*;

public class ConcreteGuiPlaylistDecorator extends GuiDecorator 
{
    private final JFrame _frame;
    
    private JPanel playlistPanel;
    private JButton showPlaylistButton;
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
        playlistPanel = new JPanel(new GridLayout(1,1));
        showPlaylistButton = new JButton("Show");
        playlistPanel.add(showPlaylistButton);
        
        _frame.add(playlistPanel, BorderLayout.EAST);
        _frame.setVisible(true);
    }
    
}
