/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import java.awt.BorderLayout;
import javax.swing.*;
import models.GuiDecorator;
import models.GuiModel;

/**
 *
 * @author Ernie
 */
public class ConcreteGuiPlaylistDecorator extends GuiDecorator 
{
    private JFrame _frame;
    
    private JPanel playlistPanel;
    private JButton showPlaylistButton;

    public ConcreteGuiPlaylistDecorator(GuiModel base) {
        super(base);
        _frame = base.frame;
    }
    
    @Override
    public void drawGui()
    {
        super.drawGui();
        playlistPanel = new JPanel();
        showPlaylistButton = new JButton();
        playlistPanel.add(showPlaylistButton);
        _frame.add(playlistPanel, BorderLayout.EAST);
        _frame.setVisible(true);
    }
    
}
