package mediaplayer;

import model.*;
import view.*;
import controller.*;
import modules.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MediaPlayer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        GuiModel model = decorateGuiModel();
        GuiView view = new GuiView(model);
    }
    
    private static GuiModel decorateGuiModel()
    {
        ModuleController moduleController = new ModuleController();
        List<String> guiModelClasses = moduleController.getGuiModelModules();
        GuiModel model = new ConcreteGuiModel();
        for (String decorator: guiModelClasses)
        {
            try 
            {
                String fullClassName = "modules." + decorator;
                Class className = Class.forName(fullClassName);
                Constructor con = className.getConstructor(GuiModel.class);
                model = (GuiModel) con.newInstance(model);
            } catch (Exception ex) 
            {
                    Logger.getLogger(MediaPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return model;
    }
}
