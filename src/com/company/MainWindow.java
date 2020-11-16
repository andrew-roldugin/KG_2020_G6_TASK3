package com.company;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private DrawPanel drawPanel;
    private JPanel sidePanel;

    public MainWindow(){
        //drawPanel = new DrawPanel();
        sidePanel = new JPanel();
      //  sidePanel.setSize();
        //sidePanel.setBorder();
        this.setLayout(new FlowLayout());
        this.add(drawPanel, CENTER_ALIGNMENT);
        this.add(sidePanel, BorderLayout.WEST);
    }
}
