package controllers;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.*;
import javax.json.stream.JsonParser;

import models.GuiModel;

public class ModuleController 
{
    public ModuleController() {};
    
    public List<String> getGuiModelModules()
    {
        File moduleFile = new File("/Users/Ernie/Desktop/OOAD/OOAD_MediaPlayer/MediaPlayer/src/modules/modules.json");
        JsonReader reader;
        List<String> list = new ArrayList<String>();
        try {
            reader = Json.createReader(new FileInputStream(moduleFile));
            JsonArray modules = reader.readArray();
            reader.close();
            for(int i = 0; i < modules.size(); i++)
            {
                list.add(modules.getJsonObject(i).getString("GuiModel"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ModuleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public void addModule()
    {
/*        JsonObject model = Json.createObjectBuilder()
                .add("GuiModules", Json.createArrayBuilder())
                .build();
*/
    }

}
