package view;
import java.awt.*;
import javax.swing.*;
import model.*;
        
public class GuiView {
    private EventMapper _eventMapper;
    private JFrame frame;
    
    public GuiView(GuiModel gui)
    {
        frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        drawGuiToFrame(gui);
        frame.setVisible(true);
    }
    
    void drawGuiToFrame(GuiModel gui)
    {
        frame.getContentPane().add(gui.menubar, BorderLayout.NORTH);
        frame.getContentPane().add(gui.currentMediaPanel, BorderLayout.WEST);
        frame.getContentPane().add(gui.controlPanel, BorderLayout.CENTER);
    }
    
    
}
