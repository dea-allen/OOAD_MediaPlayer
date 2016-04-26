/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static java.time.Clock.system;
import java.util.List;
import javax.swing.SwingWorker;
import models.*;
import view.GuiView;


public class UpdateWorker extends SwingWorker<Void, Integer> 
{

    private int duration;
    private float position;

    public UpdateWorker(long duration, int i) 
    {
        GuiModel gui = GuiView.getView(null).getGuiModel();

        this.duration = (int) gui.audioMediaPlayerComponent.getMediaPlayer().getLength();
        gui.seekSlider.setMinimum(0);
        gui.seekSlider.setMaximum(this.duration);
    }

    @Override
    protected Void doInBackground() throws Exception 
    {
        while(position < .99)
        {
            GuiModel gui = GuiView.getView(null).getGuiModel();
            position = gui.audioMediaPlayerComponent.getMediaPlayer().getPosition();
            duration = (int) gui.audioMediaPlayerComponent.getMediaPlayer().getLength();
            Thread.sleep(1000);
            GuiView.getView(null).getGuiModel().seekSlider.setValue((int)(position*duration));
        }
        return null;
    }
}