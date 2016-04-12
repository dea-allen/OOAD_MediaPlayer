package controllers;

import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardCopyOption.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import view.*;

public class ModuleController 
{
    private static final String MODULE_DIR = "/Users/SeshaSailendra/Documents/GitHub/OOAD_MediaPlayer/MediaPlayer/src/modules/";
    private static final String MODULES_JSON = "modules.json";
    
    public ModuleController() {};
    
    public List<String> getGuiModelModules()
    {
        File moduleFile = new File(MODULE_DIR + MODULES_JSON);
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
        addToDirectory(file);
        addToJson(file.getName());
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
    private void addToDirectory(File file)
    {
        try 
        {
            Path moduleDirPath = Paths.get(MODULE_DIR + file.getName());
            Path source = Paths.get(file.getPath());
            Files.move(source, moduleDirPath, REPLACE_EXISTING);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void addToJson(String moduleClassName)
    {
        RandomAccessFile file = null;
        try 
        {
            String jsonStr = Json.createObjectBuilder()
                    .add("Modules", moduleClassName)
                    .build()
                    .toString();
            // adapted from: http://stackoverflow.com/questions/26250009/append-json-element-to-json-array-in-file-using-java
            file = new RandomAccessFile(MODULE_DIR + MODULES_JSON, "rw");
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
