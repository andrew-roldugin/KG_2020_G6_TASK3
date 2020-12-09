package com.company;

import com.company.Functions.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.StringTokenizer;

public class FunctionPanel extends JPanel {
    private final DrawPanel dp;
    private final AbstractFunction function;

    public FunctionPanel(DrawPanel dp, AbstractFunction function) {
        this.dp = dp;
        this.function = function;
    }

    public void create() {
        Font standardFont = new Font("TimesRoman", Font.BOLD | Font.ITALIC, 16);
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
                            try {
                                num = Double.parseDouble(text);
                            } catch (NumberFormatException e) {
                                // TODO ИГНОРИРУЕМ ВОЗМОЖНЫЕ ОШИБКИ
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