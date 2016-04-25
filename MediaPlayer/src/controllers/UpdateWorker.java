/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import javax.swing.SwingWorker;
import models.*;
import view.GuiView;


public class UpdateWorker extends SwingWorker<Void, Integer> 
{

    private int duration;
    private GuiModel cgi;
    private int i;

    public UpdateWorker(long duration, int i) 
    {
        this.duration = (int) duration;
        this.i = i;
    }

    @Override
    protected Void doInBackground() throws Exception 
    {
        GuiModel gui = GuiView.getView(null).getGuiModel();

        for (int j = this.i; j <= duration; j++) {
            Thread.sleep(1000);
            gui.seekSlider.setValue((j/1000)+(j));
            publish(j);
        }
        return null;
    }
    @Override
    protected void process(List<Integer> i) { }
}