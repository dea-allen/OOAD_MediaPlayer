package models;

import controllers.PlayerController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

import view.GuiView;

public class ConcreteGuiModel extends GuiModel
{
    private static final String PLAYER_CONTROLLER = "PlayerController";
    private static final String MODULE_CONTROLLER = "ModuleController";
    
    
    private AudioMediaPlayerComponent audioMediaPlayerComponent = null;
    private boolean mousePressedPlaying = false;
    private UpdateWorker worker;
    
    public ConcreteGuiModel() 
    {
        setupFrame();
        setupMenu();
        setupCurrentMediaPanel();
        setupControlPanel();
    }
    void setupFrame()
    {
        frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);   
    }
    void setupMenu()
    { 
        menubar = new JMenuBar();
        fileMenu = new JMenu("File");
        openFileMenuItem = new JMenuItem("Open File");
        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(openFileMenuItem);
        fileMenu.add(exitMenuItem);
        menubar.add(fileMenu);
        moduleMenu = new JMenu("Modules");
        addModuleMenuItem = new JMenuItem("Add Module");
        addModuleMenuItem.setActionCommand(MODULE_CONTROLLER + ".addModule");
        moduleMenu.add(addModuleMenuItem);
        menubar.add(moduleMenu); 
    }
    void setupCurrentMediaPanel()
    {   
        currentMediaPanel = new JPanel(new GridLayout(1,4,4,4));
        currentMedia = generateCurrentList();
        currentMediaScrollPane = new JScrollPane(currentMedia);
        currentMediaPanel.add(currentMediaScrollPane);      
    }   
    void setupControlPanel()
    {
        controlPanel = new JPanel(new GridLayout(2,1));
        controlButtonPanel = new JPanel(new GridLayout(1,2));
        playButton = new JButton("Play");
        stopButton = new JButton("Stop");
        controlSliderPanel = new JPanel(new GridLayout(1,1));
        seekSlider = new JSlider();
        
        controlButtonPanel.add(playButton);
        controlButtonPanel.add(stopButton);
        controlSliderPanel.add(seekSlider);
        
        setDefaultSliderPosition();
        
        playButton.addActionListener((ActionEvent e) -> {
            if (audioMediaPlayerComponent == null) {
                new NativeDiscovery().discover();
                audioMediaPlayerComponent = new AudioMediaPlayerComponent();
                audioMediaPlayerComponent.getMediaPlayer().playMedia("./mediaSamples/allegro.mp3");
                audioMediaPlayerComponent.getMediaPlayer().parseMedia();
                worker = new UpdateWorker(audioMediaPlayerComponent.getMediaPlayer().getLength(), this, 1);
                worker.execute();                
            }            
        });
        
        stopButton.addActionListener((ActionEvent e) -> {
            if (audioMediaPlayerComponent.getMediaPlayer().isPlaying() == true) {
                audioMediaPlayerComponent.getMediaPlayer().stop();
                audioMediaPlayerComponent.getMediaPlayer().release();
                audioMediaPlayerComponent = null;
                setDefaultSliderPosition();
                worker.cancel(true);
            }
        });
        
        seekSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(audioMediaPlayerComponent.getMediaPlayer().isPlaying()) {
                    mousePressedPlaying = true;
                    audioMediaPlayerComponent.getMediaPlayer().pause();
                }
                else {
                    mousePressedPlaying = false;
                }
                setSliderBasedPosition();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setSliderBasedPosition();
                updateUIState();
            }
        });
        
        controlPanel.add(controlButtonPanel);
        controlPanel.add(controlSliderPanel);   
    }
    
    private void setSliderBasedPosition() {
        if(!audioMediaPlayerComponent.getMediaPlayer().isSeekable()) {
            return;
        }
        float positionValue = seekSlider.getValue() / 1000.0f;
        if(positionValue > 0.99f) {
            positionValue = 0.99f;
        }
        audioMediaPlayerComponent.getMediaPlayer().setPosition(positionValue);
    }
    
    
    private void updateUIState() {
        if(!audioMediaPlayerComponent.getMediaPlayer().isPlaying()) {
            audioMediaPlayerComponent.getMediaPlayer().play();
            if(!mousePressedPlaying) {
                try {
                    Thread.sleep(500);
                }
                catch(InterruptedException e) { }
                audioMediaPlayerComponent.getMediaPlayer().pause();
            }
        }
        int position = (int)(audioMediaPlayerComponent.getMediaPlayer().getPosition() * 1000.0f);
        setDefaultSliderPosition(position);
        worker.cancel(true);
        worker = new UpdateWorker(audioMediaPlayerComponent.getMediaPlayer().getLength(), this, position);
        worker.execute();
    }
    
    private void setDefaultSliderPosition(int position) {
        seekSlider.setValue(position);
    }
    
    private void setDefaultSliderPosition() {
        seekSlider.setMinimum(0);
        seekSlider.setMaximum(1000);
        seekSlider.setValue(0);
    }
    
    JList generateCurrentList()
    {
        DefaultListModel currentMediaModel = new DefaultListModel();
        
        currentMediaModel.addElement("Now Playing");
        currentMediaModel.addElement("Track: ");
        currentMediaModel.addElement("Artist: ");
        currentMediaModel.addElement("Album: ");
        currentMediaModel.addElement("Length: ");
        return new JList(currentMediaModel);
    }
    
    void setFileInfo(File file)
    {
        
    }
    
    @Override
    public GuiModel drawGui()
    {
        frame.setJMenuBar(menubar);
        frame.getContentPane().add(currentMediaPanel, BorderLayout.WEST);
        frame.getContentPane().add(controlPanel, BorderLayout.CENTER);
        frame.setVisible(true); 
        
        return this;
    }
}

class UpdateWorker extends SwingWorker<Void, Integer> {

    private int duration;
    private ConcreteGuiModel cgi;
    private int i;

    public UpdateWorker(long duration, ConcreteGuiModel cgi, int i) {
        this.duration = (int) duration;
        this.cgi = cgi;
        this.i = i;
    }

    @Override
    protected Void doInBackground() throws Exception {
        for (int j = this.i; j <= duration; j++) {
            Thread.sleep(1000);
            cgi.seekSlider.setValue((j/1000)+(j));
            publish(j);
        }
        return null;
    }

    protected void process(int i) { }
}
