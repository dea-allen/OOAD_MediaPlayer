package view;
import controllers.EventMapper;
import models.*;
        
public class GuiView 
{
    private static GuiModel _gui = null;
    private static GuiView _view = null;
    
    private GuiView(GuiModel gui)
    {
        EventMapper _eventMapper = new EventMapper();
        if (gui == null)
            gui = new ConcreteGuiModel();
        _gui = gui.drawGui();
        _eventMapper.addAllActionListeners(_gui);     
    }
    
    public static synchronized GuiView getView(GuiModel model)
    {
        if (_view == null)
           _view = new GuiView(model);
        return _view;
    }
    
    public GuiModel getGuiModel()
    {
        return _gui;
    }
}
