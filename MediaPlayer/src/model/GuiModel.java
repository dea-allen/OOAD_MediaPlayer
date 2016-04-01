package model;

import javax.swing.*;

public abstract class GuiModel 
{    
    public JMenuBar menubar;
    public JMenu fileMenu;
    public JMenuItem openFileMenuItem;
    public JMenu moduleMenu;
    public JMenuItem addModuleMenuItem;
    public JMenuItem exitMenuItem;
    
    public JPanel currentMediaPanel;
    public JScrollPane currentMediaScrollPane;
    public JList currentMedia;
    
    public JPanel controlPanel;
    public JButton playButton;
    public JButton stopButton;
    public JSlider seekSlider;
    
   // public JButton showPlaylistButton;

    
}
