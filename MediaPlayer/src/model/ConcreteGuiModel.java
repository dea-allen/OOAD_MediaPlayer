package model;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class ConcreteGuiModel extends GuiModel {
    private static GuiModel _guiInstance;
    private ConcreteGuiModel() {}
    private JFrame _jGuiFrame;
    private JMenuBar _jMenuBar;
    
    public static GuiModel getInstance() {
        if(_guiInstance == null)
            _guiInstance = new ConcreteGuiModel() {};
        return _guiInstance;
    }
}
