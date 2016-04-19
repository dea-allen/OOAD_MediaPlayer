package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.stream.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import view.*;
import modules.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class PlaylistController 
{
    private static final String DATA_DIR = "/Users/SeshaSailendra/Documents/GitHub/OOAD_MediaPlayer/MediaPlayer/src/data/";
    private static final String JSON_PATH = "/Users/SeshaSailendra/Documents/GitHub/OOAD_MediaPlayer/MediaPlayer/src/data/";
    private static final String DATA_JSON = "playlists.json";
    private static final String JSON = ".json";
    
    public void showPlaylists()
    {
        ConcreteGuiPlaylistDecorator gui = (ConcreteGuiPlaylistDecorator) GuiView.getView(null).getGuiModel();
        gui.openPlaylistPanel.setVisible(!gui.openPlaylistPanel.isVisible());
    }
    
    public void createPlaylist()
    {
        ConcreteGuiPlaylistDecorator gui = (ConcreteGuiPlaylistDecorator) GuiView.getView(null).getGuiModel();
        String name = JOptionPane.showInputDialog(gui.frame, "Enter Plyalist Name:");
        addToJson("Playlist", name);
        gui.playlistModel.addElement(name);
        
        try 
        {
            PrintWriter writer = new PrintWriter(JSON_PATH + name + JSON, "UTF-8");
            writer.println("[");
            writer.println("]");
            writer.close();
        }
        catch (FileNotFoundException | UnsupportedEncodingException ex) 
        {
            Logger.getLogger(PlaylistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deletePlaylist()
    {
        ConcreteGuiPlaylistDecorator gui = (ConcreteGuiPlaylistDecorator) GuiView.getView(null).getGuiModel();
        Object name = gui.playlists.getSelectedValue();
        
        //Delete json file
        File toDelete = new File(JSON_PATH + name + JSON); 
        toDelete.delete();
        //Delete from GUI
        gui.playlistModel.removeElement(name);
        //Delete from json list
        removeFromJson(DATA_DIR + DATA_JSON, name.toString());
    }
    
    public void addToPlaylist()
    {
        ConcreteGuiPlaylistDecorator gui = (ConcreteGuiPlaylistDecorator) GuiView.getView(null).getGuiModel();
        Object name = gui.playlists.getSelectedValue();
        
        File file = getFile();
        addToJson(file.getPath(), name.toString(), JSON_PATH + name + JSON);
    }
    
    private File getFile()
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
    
    private void addToJson(String key, String value)
    {
        String filename = DATA_DIR + DATA_JSON;
        addToJson(key, value, filename);
    }
    
    private void addToJson(String key, String value, String filename)
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
    
    private void removeFromJson(String path, String playlistToRemove)
    {
        try
        {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(path));
            JSONArray arr = (JSONArray)obj;

            for (int i=0; i<arr.size(); i++)
            {
                JSONObject item = (JSONObject) arr.get(i);
                String remove = item.values().toString();
                if (playlistToRemove.equals(remove.substring(1,remove.length()-1)))
                {
                    arr.remove(item);
                }
            }
            
            PrintWriter writer = new PrintWriter(path);
            writer.println("[");
            writer.println("{\"Playlist\":\"DefaultPlayList\"}");
            writer.println("]");
            writer.close();
            for (int i=1; i<arr.size(); i++)
            {
                JSONObject item = (JSONObject) arr.get(i);
                String key = item.keySet().toString();
                String val = item.values().toString();
                addToJson(key.substring(1,key.length()-1), val.substring(1,val.length()-1), path);
            }
        }
        catch (Exception ex) 
        {
            Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    JList generateCurrentList()
    {
        DefaultListModel currentMediaModel = new DefaultListModel();
        currentMediaModel.addElement("Now Playing");
        return new JList(currentMediaModel);
    }
}
