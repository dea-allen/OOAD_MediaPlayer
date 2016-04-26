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
        gui.audioMediaPlayerComponent.getMediaPlayer().parseMedia();
        MediaMeta meta = gui.audioMediaPlayerComponent.getMediaPlayer().getMediaMeta();
        gui.currentMediaModel.clear();
        gui.currentMediaModel.addElement("Now Playing");
        gui.currentMediaModel.addElement("Track: " + meta.getTitle());
        gui.currentMediaModel.addElement("Artist: " + meta.getArtist());
        gui.currentMediaModel.addElement("Album: " + meta.getAlbum());
        gui.currentMediaModel.addElement("Length: " + meta.getLength());
    }
    private void setSliderBasedPosition()
    {
        GuiModel gui = GuiView.getView(null).getGuiModel();

        if(!gui.audioMediaPlayerComponent.getMediaPlayer().isSeekable()) 
        {
            return;
        }
        float duration = GuiView.getView(null).getGuiModel().audioMediaPlayerComponent.getMediaPlayer().getLength();
        int sliderValue = GuiView.getView(null).getGuiModel().seekSlider.getValue();
        float positionValue = sliderValue / duration;

        GuiView.getView(null).getGuiModel().audioMediaPlayerComponent.getMediaPlayer().setPosition(positionValue);
    }  
    private void updateUIState() 
    {
        GuiModel gui = GuiView.getView(null).getGuiModel();
        if(!gui.audioMediaPlayerComponent.getMediaPlayer().isPlaying() 
                & gui.mousePressedPlaying) 
        {
            gui.audioMediaPlayerComponent.getMediaPlayer().play();
        }
        gui.audioMediaPlayerComponent.getMediaPlayer().parseMedia();
        float pos = GuiView.getView(null).getGuiModel().audioMediaPlayerComponent.getMediaPlayer().getPosition();
        float duration = GuiView.getView(null).getGuiModel().audioMediaPlayerComponent.getMediaPlayer().getLength();
        int position = (int) (pos * duration);

        setDefaultSliderPosition(position);
        gui.worker.cancel(true);
        gui.worker = new UpdateWorker(gui.audioMediaPlayerComponent.getMediaPlayer().getLength(), position);
        gui.worker.execute();
    }
    private void setDefaultSliderPosition(int position) 
    {
        GuiView.getView(null).getGuiModel().seekSlider.setValue(position);
    }   
    private void setDefaultSliderPosition(GuiModel gui) 
    {
        gui.seekSlider.setMinimum(0);
        gui.seekSlider.setMaximum(1000);
        gui.seekSlider.setValue(0);
    }
    
    public void play()
    {
        GuiModel gui = GuiView.getView(null).getGuiModel();
        setDefaultSliderPosition(gui);
        if (gui.audioMediaPlayerComponent == null) 
        {
            new NativeDiscovery().discover();
            gui.audioMediaPlayerComponent = new AudioMediaPlayerComponent();        
        }
        gui.audioMediaPlayerComponent.getMediaPlayer().playMedia("../../../../../../" + gui.selectedMedia);
        gui.audioMediaPlayerComponent.getMediaPlayer().parseMedia();
        if (gui.worker != null)
        {
            gui.worker.cancel(true);
        }
        gui.worker = new UpdateWorker(gui.audioMediaPlayerComponent.getMediaPlayer().getLength(), 1);
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
        setSliderBasedPosition();
    }
    public void seekMouseReleased() 
    {
        setSliderBasedPosition();
        updateUIState();
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
