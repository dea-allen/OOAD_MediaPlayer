package controllers;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import models.GuiModel;

import view.GuiView;

public class PlayerController
{
    private Media mediaObj;
    private MediaPlayer mediaPlayer;
    private GuiModel gui;
    
    public void PlayerController()
    {
        gui = GuiView.getView(null).getGuiModel();
    }

    public void play()
    {
        try {
            // translate local relative path to an URI
            mediaObj = new Media(new File("./mediaSamples/allegro.mp3").toURI().toURL().toExternalForm());
        }
        catch (Exception ex) {
           Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mediaPlayer = new MediaPlayer(mediaObj);
        mediaPlayer.play();
    }
    public void stop()
    {
        mediaPlayer.stop();
    }
}
