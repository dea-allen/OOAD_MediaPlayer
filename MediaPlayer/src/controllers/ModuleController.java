package controllers;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import view.*;

public class ModuleController 
{
    public ModuleController() {};
    
    public List<String> getGuiModelModules()
    {
        File moduleFile = new File("/Users/Ernie/Desktop/OOAD/OOAD_MediaPlayer/MediaPlayer/src/modules/modules.json");
        JsonReader reader;
        List<String> list = new ArrayList<String>();
        try 
        {
            reader = Json.createReader(new FileInputStream(moduleFile));
            JsonArray modules = reader.readArray();
            reader.close();
            for(int i = 0; i < modules.size(); i++)
            {
                list.add(modules.getJsonObject(i).getString("Module"));
            }
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public void addModule()
    {
        File file = getFile();
        addToModulesDirectory(file);
        addToModulesJson(file.getName());
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
    private void addToModulesDirectory(File file)
    {
        
        /*        JsonObject model = Json.createObjectBuilder()
                .add("GuiModules", Json.createArrayBuilder())
                .build();
        */
    }
    private void addToModulesJson(String moduleClassName)
    {
        
    }
}
