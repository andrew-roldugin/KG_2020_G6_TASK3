package com.company;

import com.company.Functions.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.StringTokenizer;

public class FunctionPanel extends JPanel {
    private final DrawPanel dp;
    private final AbstractFunction function;


    /*public void setFunction(AbstractFunction function) {
        this.function = function;
    }
     */
 /* public AbstractFunction getFunction() {
        return function;
    }

    */

    // private ChartPainter chartPainter;


    public FunctionPanel(DrawPanel dp, AbstractFunction function) {
        this.dp = dp;
        //chartPainter = dp.getChartPainter();
        this.function = function;
    }

    public void create() {
        Font standardFont = new Font("TimesRoman", Font.BOLD | Font.ITALIC, 16);
        //MigLayout layout = new MigLayout();
        String str = null;
        try {
            str = function.getFuncBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringTokenizer st = new StringTokenizer(str, ": , ");
        while (st.hasMoreTokens()) {
            String key = st.nextToken();
            String value = st.nextToken();
            switch (key) {
                case "start":
                case "op":
                case "symbol":
                case "arg":
                case "func":
                case "num":
                    JLabel label = new JLabel(value);
                    label.setFont(standardFont);
                    this.add(label);
                    break;
                case "rest":
                    JTextField tf1 = new JTextField(((ComplexFunction) function).getRest() + "");
                    tf1.getDocument().addDocumentListener(new DocumentListener() {

                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            onUpdate();
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            onUpdate();
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {

                        }

                        protected void onUpdate() {
                            String text = tf1.getText();
                            assert text != null;
                            if (text.length() == 0) {
                                return;
                            }
                            ((ComplexFunction) function).setRest(Double.parseDouble(text));
                        }
                    });
                    this.add(tf1);
                    break;
                case "param":
                    JTextField tf = new JTextField(function.getParams().get(value).toString());
                    tf.getDocument().addDocumentListener(new DocumentListener() {

                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            onUpdate();
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            onUpdate();
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {

                        }

                        protected void onUpdate() {
                            String text = tf.getText();
                            assert text != null;
                            if (text.length() == 0) {
                                return;
                            }
                            double num = 0;
                            try{
                                num = Double.parseDouble(text);
                            }catch (NumberFormatException e){
                              //  e.printStackTrace();
                            }
                            function.setNewValue(value, num);
                        }
                    });
                    this.add(tf);
                    break;
            }
        }
        JButton build = new JButton("Построить");
        build.addActionListener((e) -> {
            ((JButton) e.getSource()).setText("Перестроить");
            dp.drawOneChart(function);
            dp.repaint();
        });
        JButton remove = new JButton("Удалить");
        remove.addActionListener((e) -> {
            dp.removeFunc(this.function);
            Container parent = this.getParent();
            parent.remove(this);
            parent.repaint();
            parent.revalidate();
            dp.repaint();
        });
        this.add(build);
        this.add(remove);
    }
}




        /*JLabel xLabel = new JLabel("x");
        JLabel yLabel = new JLabel("y");
        JLabel leftBr = new JLabel("(");

        JLabel plus = new JLabel(" + ");
        JLabel div = new JLabel(" / ");
        JLabel rightBr = new JLabel(")");
        JLabel powLabel = new JLabel("^");
        Font stFont = new Font("TimesRoman", Font.BOLD | Font.ITALIC, 16);
        xLabel.setFont(stFont);
        yLabel.setFont(stFont);
        leftBr.setFont(stFont);
        rightBr.setFont(stFont);
        plus.setFont(stFont);
        div.setFont(stFont);
        powLabel.setFont(stFont);
        JLabel start = function.getType() == AllFunction.INV_FUNCTION ? new JLabel("x = ") : new JLabel("y = ");
        start.setFont(stFont);
        this.add(start);
        if(function.getType() == AllFunction.SINUS) {
            this.add(new JTextField(function.getParams().get('A').toString()));
            JLabel sinLabel = new JLabel("sin");
            sinLabel.setFont(stFont);
            this.add(sinLabel);
            this.add(leftBr);
            this.add(new JTextField(function.getParams().get('W').toString()));
            this.add(xLabel);
            this.add(plus);
            this.add(new JTextField(function.getParams().get('F').toString()));
            this.add(rightBr);
            this.add(plus);
            this.add(new JTextField(function.getParams().get('C').toString()));
        }else if(function.getType() == AllFunction.SIN_COS){
            this.add(new JTextField(((ComplexFunction) function).getFunction1().getParams().get('A').toString()));
            JLabel sinLabel = new JLabel("sin");
            sinLabel.setFont(stFont);
            this.add(sinLabel);
            this.add(leftBr);
            this.add(new JTextField(((ComplexFunction) function).getFunction1().getParams().get('W').toString()));
            this.add(xLabel);
            this.add(plus);
            this.add(new JTextField(((ComplexFunction) function).getFunction1().getParams().get('F').toString()));
            this.add(rightBr);
            this.add(plus);
            this.add(new JTextField(((ComplexFunction) function).getFunction1().getParams().get('C').toString()));


            this.add(new JTextField(((ComplexFunction) function).getOperator() + ""));

            this.add(new JTextField(((ComplexFunction) function).getFunction2().getParams().get('A').toString()));
            JLabel cosLabel = new JLabel("cos");
            cosLabel.setFont(stFont);
            this.add(cosLabel);
            this.add(leftBr);
            this.add(new JTextField(((ComplexFunction) function).getFunction2().getParams().get('W').toString()));
            this.add(xLabel);
            this.add(plus);
            this.add(new JTextField(((ComplexFunction) function).getFunction2().getParams().get('F').toString()));
            this.add(rightBr);
            this.add(plus);
            this.add(new JTextField(((ComplexFunction) function).getFunction2().getParams().get('C').toString()));
        }else if (function.getType() == AllFunction.SIN_EXP){
            this.add(new JTextField(((ComplexFunction) function).getFunction1().getParams().get('A').toString()));
            JLabel sinLabel = new JLabel("sin");
            sinLabel.setFont(stFont);
            this.add(sinLabel);
            this.add(leftBr);
            this.add(new JTextField(((ComplexFunction) function).getFunction1().getParams().get('W').toString()));
            this.add(xLabel);
            this.add(plus);
            this.add(new JTextField(((ComplexFunction) function).getFunction1().getParams().get('F').toString()));
            this.add(rightBr);
            /*this.add(plus);
            this.add(new JTextField(((ComplexFunction) function).getFunction1().getParams().get('C').toString()));
            this.add(rightBr);



            this.add(new JLabel(((ComplexFunction) function).getOperator() + ""));


            JLabel expLabel = new JLabel("e");
            expLabel.setFont(stFont);
            this.add(expLabel);


            this.add(powLabel);
            this.add(leftBr);
            this.add(xLabel.);
            this.add(div);
            this.add(new JTextField(((ComplexFunction) function).getFunction2().getParams().get('T').toString()));
            this.add(rightBr);
            /*GridBagConstraints constraints = new GridBagConstraints();
            // По умолчанию натуральная высота, максимальная ширина
            constraints.fill = GridBagConstraints.VERTICAL;
            //constraints.weightx = 0.5;
            constraints.gridx  = 0  ;  // нулевая ячейка таблицы по вертикали




            JPanel panel = new JPanel(); // the panel is not visible in output

            panel.setAlignmentX(-20);
            panel.setLayout(new GridBagLayout());


            JLabel minusLabel = new JLabel("-");
            constraints.gridx = 0;
            constraints.gridy = 1;
            panel.add(minusLabel, constraints);

            constraints.gridx = 1;
            constraints.gridy = 1;
            this.add(xLabel, constraints);

            constraints.gridx = 1;
            constraints.gridy = 2;
            panel.add(new JTextField(((ComplexFunction) function).getFunction2().getParams().get('T').toString()), constraints);

            this.add(panel);

            float tempX = expLabel.getAlignmentX();
            float tempY = expLabel.getAlignmentY();

            minusLabel.setAlignmentX(tempX + 5);
            minusLabel.setAlignmentY(tempY - 5);
            xLabel.setAlignmentX(tempX + 20);
            xLabel.setAlignmentY(tempY - 150);
            this.add(xLabel);
            this.add(minusLabel);
            minusLabel.setAlignmentX(tempX + 7);
            this.add(minusLabel);

            this.add(new JTextField(((ComplexFunction) function).getFunction2().getParams().get('T').toString()));


        }
        JButton build = new JButton("Построить");
        build.addActionListener((e) -> {
            ((JButton) e.getSource()).setLabel("Перестроить");
        });
        JButton remove = new JButton("Удалить");
        remove.addActionListener((e) -> {
            Container parent = this.getParent();
            parent.remove(this);
            parent.repaint();
        });
        this.add(build);
        this.add(remove);

         */

/*
    public Component[] f(){
        components = new Component[7];
        components[0] = new TextField(((Sine) function).getA() + "");
        components[1] = new Label( "sin ( " );
        components[2] = new TextField(((Sine) function).getW() + "");
        components[3] = new Label( "x + " );
        components[4] = new TextField(((Sine) function).getF() + "");
        components[5] = new Label( " ) +  " );
        components[6] = new TextField(((Sine) function).getC() + "");
        return components;
        for (int i = 0; i < function.getParams().size(); i++) {
            components[i] = new TextField()
        }
        for (Map.Entry<Character, Double> item : function.getParams().entrySet()) {
            components[1] = new TextField();
        }
    }

 */


