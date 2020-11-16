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
   // private ChartPainter chartPainter = new ChartPainter(pd, ld, sd, sc);

    public UI(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setVisible(true);

        /*main.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        main.setLayout(new GridLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // По умолчанию натуральная высота, максимальная ширина
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridy   = 0  ;  // нулевая ячейка таблицы по вертикали


        constraints.gridx = 0;
        sidePanel.setPreferredSize(new Dimension(20, 1000));
        main.add(sidePanel, constraints);


        constraints.gridx = 1;
        main.add(paintPanel, constraints);



        //sidePanel.setPreferredSize(new Dimension(20, 1000));


         */

        this.setContentPane(main);


        // По умолчанию натуральная высота, максимальная ширина
        //constraints.fill = GridBagConstraints.VERTICAL;
        //constraints.weightx = 0.5;
        //  ;  // нулевая ячейка таблицы по вертикали


        JComboBox<AllFunction> comboBox = new JComboBox<>(AllFunction.values());
        comboBox.setSelectedIndex(0);
        JButton addFunc = new JButton("Добавить функцию");


        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(BorderLayout.WEST, comboBox);
        container.add(BorderLayout.EAST, addFunc);

        sidePanel.add(container);
        sidePanel.setLayout(new GridBagLayout());



        //sidePanel.add(comboBox);
       // sidePanel.add(addFunc);
        GridBagConstraints constraints = new GridBagConstraints();
       // constraints.fill = GridBagConstraints.NONE;
       // constraints.gridx = 0;
      //  constraints.gridy = 0;

        //sidePanel.add(container, constraints);

        //constraints.gridx = 1;
        //sidePanel.add(addFunc, constraints);
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

        //sidePanel.add(BorderLayout.NORTH, panel);
        //sidePanel.add(BorderLayout.SOUTH, panel1);

        //fp.setFunction(new ComplexFunction(new Sine().setA(3).setW(4), new InvExp().setT(2), '*'));




        //sidePanel.add(BorderLayout.NORTH, tf);
        //sidePanel.add(BorderLayout.CENTER, ta);
     //   sidePanel.add(BorderLayout.SOUTH, new JSeparator());
        /*for (Component c: a) {
            constraints.gridx = i++;
            sidePanel.add(c, constraints);
        }

         */
        //revalidate();
        //paintPanel.setLayout(new GridLayout());
        //DrawPanel dp = new DrawPanel();
        //paintPanel.add(dp);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        paintPanel = dp = new DrawPanel(1280, 720);
        //sidePanel = new JPanel();

        //sidePanel.setPreferredSize(new Dimension((int) (this.getWidth() * 0.02), this.getHeight()));

    }
}
