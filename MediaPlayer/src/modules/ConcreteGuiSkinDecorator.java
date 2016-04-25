package modules;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import models.*;

public class ConcreteGuiSkinDecorator extends GuiDecorator 
{

    public ConcreteGuiSkinDecorator(GuiModel base) 
    {
        super(base);
        frame = base.frame;
        audioMediaPlayerComponent = base.audioMediaPlayerComponent;
        worker = base.worker;
        seekSlider = base.seekSlider;
        mousePressedPlaying = base.mousePressedPlaying;
        currentMediaModel = base.currentMediaModel;
        selectedMedia = base.selectedMedia;
    }
    @Override
    public GuiModel drawGui()
    {
        super.drawGui();
        
        try {
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ConcreteGuiSkinDecorator.class.getName()).log(Level.SEVERE, null, ex);
        }
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
        frame.setVisible(true);

        return this;
    }
}