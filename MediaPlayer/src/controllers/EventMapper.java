package controllers;

import models.*;
import modules.*;

import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Method;
import java.util.logging.*;
import javax.swing.*;

public class EventMapper implements ActionListener 
{
    public void addAllActionListeners(GuiModel gui)
    {
        java.util.List<Component> items = getAllComponents(gui.frame);
        for (Component c: items)
        {
            //TODO: refactor smelly
            if (c.getClass() == JButton.class)
            {
                JButton b = (JButton) c;
                b.addActionListener(this);
            }
            if (c.getClass() == JMenu.class)
            {
                JMenu m = (JMenu) c;
                for (int i = 0; i < m.getItemCount(); i++) 
                {
                    m.getItem(i).addActionListener(this);
                }
            }
        }
    }
    // adpated this from: http://stackoverflow.com/questions/6495769/how-to-get-all-elements-inside-a-jframe
    public java.util.List<Component> getAllComponents(final Container c) 
    {
        Component[] components = c.getComponents();
        java.util.ArrayList<Component> compList = new java.util.ArrayList<Component>();
        for (Component comp: components)
        {
            compList.add(comp);
            if (comp instanceof Container)
                compList.addAll(getAllComponents((Container) comp));
        }
        return compList;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        try 
        {
            String com = e.getActionCommand();
            String[] command = com.split("\\.");
            Class controller = Class.forName("controllers." + command[0]);
            Method method = controller.getMethod(command[1]);
            Object t = controller.newInstance();
            method.invoke(t);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(EventMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
}
