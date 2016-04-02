/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Ernie
 */
public abstract class GuiDecorator extends GuiModel {
    private GuiModel baseGui;
    public GuiDecorator(GuiModel base) { baseGui = base; }
    @Override
    public void drawGui() { baseGui.drawGui(); }
}
