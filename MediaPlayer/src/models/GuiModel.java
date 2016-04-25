package models;

import controllers.UpdateWorker;
import javax.swing.*;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;

public abstract class GuiModel 
{    
    public JFrame frame;

    public JMenuBar menubar;
    public JMenu fileMenu;
    public JMenuItem openFileMenuItem;
    public JMenu moduleMenu;
    public JMenuItem addModuleMenuItem;
    public JMenuItem exitMenuItem;
    
    public JPanel currentMediaPanel;
    public JScrollPane currentMediaScrollPane;
    public JList currentMedia;
    public DefaultListModel currentMediaModel;
    
    public JPanel controlPanel;
    public JPanel controlButtonPanel;
    public JButton playButton;
    public JButton stopButton;
    public JPanel controlSliderPanel;
    public JSlider seekSlider;
    
    public AudioMediaPlayerComponent audioMediaPlayerComponent;
    public UpdateWorker worker;
    public boolean mousePressedPlaying;

    public String selectedMedia;
 
    public abstract GuiModel drawGui();
}
