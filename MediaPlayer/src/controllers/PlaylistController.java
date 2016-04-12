package controllers;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import view.*;
import modules.*;

public class PlaylistController 
{
    private static final String DATA_DIR = "/Users/Ernie/Desktop/OOAD/OOAD_MediaPlayer/MediaPlayer/src/data/";
    private static final String DATA_JSON = "playlists.json";
    
    public void showPlaylists()
    {
        ConcreteGuiPlaylistDecorator gui = (ConcreteGuiPlaylistDecorator) GuiView.getView(null).getGuiModel();
        gui.openPlaylistPanel.setVisible(!gui.openPlaylistPanel.isVisible());
    }
    
    public void createPlaylist()
    {
        ConcreteGuiPlaylistDecorator gui = (ConcreteGuiPlaylistDecorator) GuiView.getView(null).getGuiModel();
        String name = JOptionPane.showInputDialog(gui.frame, "Enter Plyalist Name:");
        File file = getFile();
        addToJson("Playlist", name);
        addToJson(name, file.getPath());        
        gui.playlistModel.addElement(name);
    }
    
    private File getFile()
    {
        File file = null;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Java Classes", "java");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(GuiView.getView(null).getGuiModel().frame);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            file = chooser.getSelectedFile();
        }
        return file;
    }
    
    private void addToJson(String key, String value)
    {
        RandomAccessFile file = null;
        try 
        {
            String jsonStr = Json.createObjectBuilder()
                    .add(key, value)
                    .build()
                    .toString();
            // adapted from: http://stackoverflow.com/questions/26250009/append-json-element-to-json-array-in-file-using-java
            file = new RandomAccessFile(DATA_DIR + DATA_JSON, "rw");
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
