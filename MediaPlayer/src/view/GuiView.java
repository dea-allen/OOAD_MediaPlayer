package view;
import models.GuiModel;
import java.awt.*;
import javax.swing.*;
        
public class GuiView 
{
    private EventMapper _eventMapper;
    
    public GuiView(GuiModel gui)
    {
        gui.drawGui();
    }
    

    
    
}
