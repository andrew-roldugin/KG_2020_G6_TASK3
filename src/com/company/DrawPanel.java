package com.company;

import com.company.Drawers.LineDrawer.DDALineDrawer;
import com.company.Drawers.LineDrawer.LineDrawer;
import com.company.Drawers.PixelDrawer.BufferedImagePixelDrawer;
import com.company.Drawers.StringDrawer.BufferedImageStringDrawer;
import com.company.Functions.*;
import com.company.common.Converter.ScreenConverter;
import com.company.common.Geometry.Line;
import com.company.common.Geometry.RealPoint;
import com.company.common.Geometry.ScreenPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.*;

public class DrawPanel extends JPanel
        implements MouseWheelListener, MouseMotionListener, MouseListener {
    private final Line xAxis = new Line(-1000, 0, 1000, 0);
    private final Line yAxis = new Line(0, -1000, 0, 1000);

    private final List<Line> allLines = new ArrayList<>();
    private final ScreenConverter sc = new ScreenConverter(-25, 25, 50, 50, 1280, 720);
    private ScreenPoint prevPosition;
    private Line currentNewLine;

    private BufferedImage bf;
    private ChartDrawer chartDrawer;
    private final List<AbstractFunction> allFunc = new ArrayList<>();


    public DrawPanel(int width, int height) {
        setSize(width, height);

        this.addMouseWheelListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void paint(Graphics g) {
        if(sc == null)
            return;
        sc.setScreenW(getWidth()); sc.setScreenH(getHeight());
        bf = new BufferedImage(
                getWidth(), getHeight(),
                BufferedImage.TYPE_INT_RGB);
        BufferedImagePixelDrawer pd = new BufferedImagePixelDrawer(bf);
        LineDrawer ld = new DDALineDrawer(pd);
        BufferedImageStringDrawer sd = new BufferedImageStringDrawer(bf);
        this.chartDrawer = new ChartDrawer(pd, ld, sd, sc);
        chartDrawer.setXAxis(xAxis);
        chartDrawer.setYAxis(yAxis);
        Graphics2D graphics2D = bf.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        graphics2D.dispose();

        drawAll(g);
        g.drawImage(bf, 0, 0, null);
    }

    private void drawAll(Graphics g){
        chartDrawer.drawGrid(false);

        chartDrawer.drawAxis(g);

        allFunc.forEach(this::drawOneChart);



       // chartPainter.drawChart(new Sine().setF(1)); //OK
        //drawChart(ld, new ComplexFunction(new Sine().setA(3).setW(4), new InvExp().setT(2), '*')); //OK
        //drawChart(ld, new Hyperbola()); //OK
        //drawChart(ld, new Hyperbola().setA(2).setB(3).setD(1).setC(6)); //OK
        //drawChart(ld, new Polynomial().setA(1).setB(2).setC(1).setD(6)); //OK
        //drawChart(ld, new ComplexExp().setA(2).setC(4)); //OK
        //drawChart(ld, new ComplexFunction(new Sine().setA(2), new Cosine().setW(5), '*')); //OK

        //chartPainter.drawChart(ld, function);
        //drawChart(ld, new InvFunction().setA(4).setB(3).setC(2).setD(1)); //OK
        /*for (Line l : allLines) {
            drawOneLine(ld, l);
        }
        if(currentNewLine != null){
            drawOneLine(ld, currentNewLine);
        }*/
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        int clicks = mouseWheelEvent.getWheelRotation();
        double scale = 1;
        double step = clicks < 0 ? 0.9 : 1.1;
        for (int i = abs(clicks); i > 0 ; i--) {
            scale *= step;
        }

        sc.setRealH(sc.getRealH() * scale);
        sc.setRealW(sc.getRealW() * scale);

        //автоцентрирование
        //TODO сделать через параметр
        sc.setyR(sc.getRealH() / 2);
        sc.setxR(-sc.getRealW() / 2);
        xAxis.setP1(new RealPoint(xAxis.getP1().getX() * scale, 0));
        xAxis.setP2(new RealPoint(xAxis.getP2().getX() * scale, 0));
        yAxis.setP1(new RealPoint(0, yAxis.getP1().getY() * scale));
        yAxis.setP2(new RealPoint(0, yAxis.getP2().getY() * scale));

        chartDrawer.setConverter(sc);
        chartDrawer.setXAxis(xAxis);
        chartDrawer.setYAxis(yAxis);

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON3)
            prevPosition = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
        else if(mouseEvent.getButton() == MouseEvent.BUTTON1){
            ScreenPoint current = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
            currentNewLine = new Line(sc.screen2Real(current), sc.screen2Real(current));
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON3)
            prevPosition = null;
        else if(mouseEvent.getButton() == MouseEvent.BUTTON1){
            if(currentNewLine != null)
                allLines.add(currentNewLine);
            currentNewLine = null;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        boolean flag = false;
        ScreenPoint currentPosition = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
        if(prevPosition != null){
            ScreenPoint delta = new ScreenPoint(
                    -currentPosition.getX() + prevPosition.getX(),
                    -currentPosition.getY() + prevPosition.getY());
            RealPoint deltaReal = sc.screen2Real(delta);
            sc.setxR(deltaReal.getX());
            sc.setyR(deltaReal.getY());
            chartDrawer.setConverter(sc);
            if(prevPosition != currentPosition) {
                prevPosition = currentPosition;
                flag = true;
            }
        }

        if(currentNewLine != null){
            currentNewLine.setP2(sc.screen2Real(currentPosition));
        }
        if (flag)
            repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    public void addFunc(AbstractFunction function) {
        allFunc.add(function);
    }

    public void drawOneChart(AbstractFunction function) {
        chartDrawer.drawChart(function);
    }

    public void removeFunc(AbstractFunction f) {
        allFunc.remove(f);
    }
}
