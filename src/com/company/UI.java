package com.company;

import com.company.Functions.*;

import javax.swing.*;
import java.awt.*;

public class UI extends JFrame{
    private int counter = 1;
    private JPanel paintPanel;
    private JPanel main;
    private JPanel sidePanel;
    private DrawPanel dp;

    public UI(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setVisible(true);

        this.setContentPane(main);

        JComboBox<AllFunction> comboBox = new JComboBox<>(AllFunction.values());
        comboBox.setSelectedIndex(0);
        JButton addFunc = new JButton("Добавить функцию");


        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(BorderLayout.WEST, comboBox);
        container.add(BorderLayout.EAST, addFunc);

        sidePanel.add(container);
        sidePanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        addFunc.addActionListener((e) -> {
            constraints.gridx = 0;
            AbstractFunction function = new FunctionFactory().getFunction((AllFunction) comboBox.getSelectedItem());
            dp.addFunc(function);
            FunctionPanel fp = new FunctionPanel(dp, function);
            fp.create();
            constraints.gridy = counter++;
            sidePanel.add(fp, constraints);
            sidePanel.repaint();
            sidePanel.updateUI();
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        paintPanel = dp = new DrawPanel(1280, 720);

    }
}
