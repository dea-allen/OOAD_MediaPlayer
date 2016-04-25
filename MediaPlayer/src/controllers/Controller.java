/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.swing.JFileChooser;
import view.GuiView;

/**
 *
 * @author Ernie
 */
public class Controller 
{
    private static final String DATA_DIR = "./src/data/";

    public File getFile()
    {
        File file = null;
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(GuiView.getView(null).getGuiModel().frame);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            file = chooser.getSelectedFile();
        }
        return file;
    }
    
    public void addToJson(String key, String value, String filename)
    {
        RandomAccessFile file = null;
        try 
        {
            String jsonStr = Json.createObjectBuilder()
                    .add(key, value)
                    .build()
                    .toString();
            // adapted from: http://stackoverflow.com/questions/26250009/append-json-element-to-json-array-in-file-using-java
            file = new RandomAccessFile(filename, "rw");
            long pos = file.length();
            while (file.length() > 0)
            {
                pos--;
                file.seek(pos);
                if (file.readByte() == ']')
                {
                    file.seek(pos);
                    break;
                }
            }
            file.writeBytes("," + jsonStr + "]");
            file.close();
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

}
