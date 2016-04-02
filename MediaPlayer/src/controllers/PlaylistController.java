package controllers;

import view.*;
import modules.*;

public class PlaylistController {
    private GuiView _view;
    
    public void showPlaylists()
    {
        ConcreteGuiPlaylistDecorator gui = (ConcreteGuiPlaylistDecorator) _view.getView(null).getGuiModel();
        gui.openPlaylistPanel.setVisible(!gui.openPlaylistPanel.isVisible());
    }
}
