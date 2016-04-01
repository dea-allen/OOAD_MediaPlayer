package mediaplayer;

import model.*;
import view.*;
import controller.*;

public class MediaPlayer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GuiModel model = new ConcreteGuiModel();
        GuiView view = new GuiView(model);
    }
    
}
