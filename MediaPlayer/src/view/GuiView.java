package view;
import models.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
        
public class GuiView 
{
    private final EventMapper _eventMapper = new EventMapper();
    private final GuiModel _gui;
    private static GuiView _view = null;
    
    private GuiView() 
    {
        _gui = new ConcreteGuiModel();
    }
    
    private GuiView(GuiModel gui)
    {
        _gui = gui.drawGui();
        _eventMapper.addAllActionListeners(_gui);     
    }
    
    public static synchronized GuiView getView(GuiModel model)
    {
        if (_view == null)
           _view = (model == null) ? new GuiView() : new GuiView(model);
        return _view;
    }
    
    public GuiModel getGuiModel()
    {
        return _gui;
    }
}
