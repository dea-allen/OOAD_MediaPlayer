package controllers;

import java.awt.event.WindowEvent;
import java.io.File;
import models.*;
import view.GuiView;

import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.*;

public class PlayerController extends Controller
{

    private void updateCurrentMediaList(GuiModel gui)
    {
        MediaMeta meta = gui.audioMediaPlayerComponent.getMediaPlayer().getMediaMeta();
        gui.currentMediaModel.clear();
        gui.currentMediaModel.addElement("Now Playing");
        gui.currentMediaModel.addElement("Track: " + meta.getTitle());
        gui.currentMediaModel.addElement("Artist: " + meta.getArtist());
        gui.currentMediaModel.addElement("Album: " + meta.getAlbum());
        gui.currentMediaModel.addElement("Length: " + meta.getLength());
    }
    private void setSliderBasedPosition(GuiModel gui) 
    {
        if(!gui.audioMediaPlayerComponent.getMediaPlayer().isSeekable()) 
        {
            return;
        }
        float positionValue = gui.seekSlider.getValue() / 1000.0f;
        if(positionValue > 0.99f) 
        {
            positionValue = 0.99f;
        }
        gui.audioMediaPlayerComponent.getMediaPlayer().setPosition(positionValue);
    }  
    private void updateUIState(GuiModel gui) 
    {
        if(!gui.audioMediaPlayerComponent.getMediaPlayer().isPlaying()) 
        {
            gui.audioMediaPlayerComponent.getMediaPlayer().play();
            if(!gui.mousePressedPlaying) 
            {
                try 
                {
                    Thread.sleep(500);
                }
                catch(InterruptedException e) { }
                gui.audioMediaPlayerComponent.getMediaPlayer().pause();
            }
        }
        int position = (int)(gui.audioMediaPlayerComponent.getMediaPlayer().getPosition() * 1000.0f);
        setDefaultSliderPosition(position, gui);
        gui.worker.cancel(true);
        gui.worker = new UpdateWorker(gui.audioMediaPlayerComponent.getMediaPlayer().getLength(), gui, position);
        gui.worker.execute();
    }
    private void setDefaultSliderPosition(int position, GuiModel gui) 
    {
        gui.seekSlider.setValue(position);
    }   
    private void setDefaultSliderPosition(GuiModel gui) {
        gui.seekSlider.setMinimum(0);
        gui.seekSlider.setMaximum(1000);
        gui.seekSlider.setValue(0);
    }
    
    public void play()
    {
        GuiModel gui = GuiView.getView(null).getGuiModel();
        if (gui.audioMediaPlayerComponent == null) 
        {
            new NativeDiscovery().discover();
            gui.audioMediaPlayerComponent = new AudioMediaPlayerComponent();        
        }
<<<<<<< Updated upstream
        gui.audioMediaPlayerComponent.getMediaPlayer().playMedia("../../../../../../" + gui.selectedMedia);
=======
        gui.audioMediaPlayerComponent.getMediaPlayer().playMedia(gui.selectedMedia);
>>>>>>> Stashed changes
        if (gui.worker != null)
        {
            gui.worker.cancel(true);
        }
        gui.worker = new UpdateWorker(gui.audioMediaPlayerComponent.getMediaPlayer().getLength(), gui, 1);
        gui.worker.execute();  
        updateCurrentMediaList(gui);
    }
    public void stop()
    {
        GuiModel gui = GuiView.getView(null).getGuiModel();

        if (gui.audioMediaPlayerComponent.getMediaPlayer().isPlaying() == true) 
        {
                gui.audioMediaPlayerComponent.getMediaPlayer().stop();
                gui.audioMediaPlayerComponent.getMediaPlayer().release();
                gui.audioMediaPlayerComponent = null;
                setDefaultSliderPosition(gui);
        }
    }
    public void seekMousePressed()
    {
        GuiModel gui = GuiView.getView(null).getGuiModel();

        if(gui.audioMediaPlayerComponent.getMediaPlayer().isPlaying()) 
        {
            gui.mousePressedPlaying = true;
            gui.audioMediaPlayerComponent.getMediaPlayer().pause();
        }
        else 
        {
            gui.mousePressedPlaying = false;
        }
        setSliderBasedPosition(gui);
    }
    public void seekMouseReleased()
    {
        GuiModel gui = GuiView.getView(null).getGuiModel();

        setSliderBasedPosition(gui);
        updateUIState(gui);
    }
    public void exit()
    {
        GuiModel gui = GuiView.getView(null).getGuiModel();

        gui.frame.dispatchEvent(new WindowEvent(gui.frame, WindowEvent.WINDOW_CLOSING));
    }
    public void openFile()
    {
        GuiModel gui = GuiView.getView(null).getGuiModel();

        File file = getFile();
        if (file != null) 
        {
            gui.selectedMedia = file.getPath();
            play();
        }
    }
    
}
