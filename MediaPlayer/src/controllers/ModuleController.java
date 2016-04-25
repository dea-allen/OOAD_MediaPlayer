package controllers;

import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardCopyOption.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.*;

public class ModuleController extends Controller
{
    private static final String MODULE_DIR = "./src/modules/";
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
        addToJson("Modules", file.getName(), MODULE_DIR + MODULES_JSON);
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
}
