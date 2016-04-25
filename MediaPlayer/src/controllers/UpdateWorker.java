/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.swing.SwingWorker;
import models.*;


public class UpdateWorker extends SwingWorker<Void, Integer> 
{

    private int duration;
    private GuiModel cgi;
    private int i;

    public UpdateWorker(long duration, GuiModel cgi, int i) 
    {
        this.duration = (int) duration;
        this.cgi = cgi;
        this.i = i;
    }

    @Override
    protected Void doInBackground() throws Exception 
    {
        for (int j = this.i; j <= duration; j++) {
            Thread.sleep(1000);
            cgi.seekSlider.setValue((j/1000)+(j));
            publish(j);
        }
        return null;
    }

    protected void process(int i) { }
}