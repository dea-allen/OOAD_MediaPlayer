package controllers;

import view.*;
import modules.*;

public class PlaylistController {
    
    public void showPlaylists()
    {
        ConcreteGuiPlaylistDecorator gui = (ConcreteGuiPlaylistDecorator) GuiView.getView(null).getGuiModel();
        gui.openPlaylistPanel.setVisible(!gui.openPlaylistPanel.isVisible());
    }
}
