package controllers;

import models.*;
import view.*;
import modules.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class PlaylistController extends Controller
{
    private static final String DATA_DIR = "./src/data/";
    private static final String DATA_JSON = "playlists.json";
    private static final String JSON = ".json";
    
    public void showPlaylists()
    {
        ConcreteGuiPlaylistDecorator gui = (ConcreteGuiPlaylistDecorator) GuiView.getView(null).getGuiModel();
        gui.openPanel.setVisible(!gui.openPanel.isVisible());
    }
    
    public void showMedia()
    {
        ConcreteGuiPlaylistDecorator gui = (ConcreteGuiPlaylistDecorator) GuiView.getView(null).getGuiModel();
        gui.selectedPlaylistPanel.setVisible(!gui.selectedPlaylistPanel.isVisible());
        if (gui.hideMediaPanel.getText() == ">>")
            gui.hideMediaPanel.setText("<<");
        else
            gui.hideMediaPanel.setText(">>");
    }
    
    public void createPlaylist()
    {
        ConcreteGuiPlaylistDecorator gui = (ConcreteGuiPlaylistDecorator) GuiView.getView(null).getGuiModel();
        String name = JOptionPane.showInputDialog(gui.frame, "Enter Plyalist Name:");
        addToJson("Playlist", name, DATA_DIR + DATA_JSON);
        gui.playlistModel.addElement(name);
        
        try 
        {
            PrintWriter writer = new PrintWriter(DATA_DIR + name + JSON, "UTF-8");
            writer.println("[{\""+ name + "\":\"\"}");
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
        File toDelete = new File(DATA_DIR + name + JSON); 
        toDelete.delete();
        //Delete from GUI
        gui.playlistModel.removeElement(name);
        //Delete from json list
        removeFromJson(DATA_DIR + DATA_JSON, name.toString());
        gui.mediaModel.removeAllElements();
    }
    
    public void deleteMedia()
    {
        ConcreteGuiPlaylistDecorator gui = (ConcreteGuiPlaylistDecorator) GuiView.getView(null).getGuiModel();
        Object name = gui.medias.getSelectedValue();
    }
    
    public void addToPlaylist()
    {
        ConcreteGuiPlaylistDecorator gui = (ConcreteGuiPlaylistDecorator) GuiView.getView(null).getGuiModel();
        Object name = gui.playlists.getSelectedValue();
        
        File file = getFile();
        addToJson(name.toString(), file.getPath(), DATA_DIR + name + JSON);
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
}
