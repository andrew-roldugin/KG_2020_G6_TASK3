package com.company;

import com.company.Drawers.LineDrawer.BresenhamLineDrawer;
import com.company.Drawers.LineDrawer.DDALineDrawer;
import com.company.Drawers.LineDrawer.LineDrawer;
import com.company.Drawers.LineDrawer.WuLineDrawer;
import com.company.Drawers.PixelDrawer.BufferedImagePixelDrawer;
import com.company.Drawers.PixelDrawer.PixelDrawer;
import com.company.Drawers.StringDrawer.BufferedImageStringDrawer;
import com.company.Drawers.StringDrawer.StringDrawer;
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
    private Line xAxis = new Line(-1000, 0, 1000, 0),
            yAxis = new Line(0, -1000, 0, 1000);

    private List<Line> allLines = new ArrayList();
    private ScreenConverter sc = new ScreenConverter(-25, 25, 50, 50, 1280, 720);
    private ScreenPoint prevPosition;
    private Line currentNewLine;

    private BufferedImage bf;
    private ChartPainter chartPainter;
    private List<AbstractFunction> allFunc = new ArrayList();

    private Actions lastAction;

    public DrawPanel(int width, int height) {
        setSize(width, height);
        /*sc.setScreenW(getWidth()); sc.setScreenH(getHeight());
        bf = new BufferedImage(
                getWidth(), getHeight(),
                BufferedImage.TYPE_INT_RGB);
        BufferedImagePixelDrawer pd = new BufferedImagePixelDrawer(bf);
        DDALineDrawer ld = new DDALineDrawer(pd);
        BufferedImageStringDrawer sd = new BufferedImageStringDrawer(bf);
        this.chartPainter = new ChartPainter(pd, ld, sd, sc);

         */

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
        this.chartPainter = new ChartPainter(pd, ld, sd, sc);
        chartPainter.setXAxis(xAxis);
        chartPainter.setYAxis(yAxis);
        Graphics2D graphics2D = bf.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        graphics2D.dispose();

        drawAll(g);
        g.drawImage(bf, 0, 0, null);
    }

    private void drawAll(/*LineDrawer ld, StringDrawer sd, PixelDrawer pd,*/ Graphics g){
        chartPainter.drawGrid(false);

        chartPainter.drawAxis(g);

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



       /* for (double i = xAxis.getP1().getX(); i < xAxis.getP2().getX(); i += deltaMajorX) {

            //засечки на оси X
            ld.drawLine(sc.r2s(new RealPoint(i , -0.05 * deltaMajorY)), sc.r2s(new RealPoint(i, 0.05 * deltaMajorY)));

            //засечки на оси Y
            ld.drawLine(sc.r2s(new RealPoint(-0.05 * deltaMajorX, i)), sc.r2s(new RealPoint( 0.05 * deltaMajorX, i)));


            String num = String.valueOf(i);
            if(num.endsWith(".0"))
                num = num.substring(0, num.length() - 2);
            if(num.length() > 8)
                num = String.format("%.2e", i);


            FontMetrics fm = g.getFontMetrics();
            int width = fm.stringWidth(num);

            //цифры на оси X
            ScreenPoint temp = sc.r2s(new RealPoint(i, -0.5 * deltaMajorY));
            sd.drawString(num, temp.getX() - width / 2, temp.getY());



          if(i == 0) continue;
            ScreenPoint tmp = sc.r2s(new RealPoint(-0.5 * deltaMajorY, i));
            sd.drawString(num, tmp.getX() - width, tmp.getY() + 5);

        }

        */

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

        //temp = (sc.getScreenW() / (sc.getRealW() * sc.getRealW()));
        //delta *= clicks > 0 ? abs(clicks) * 2 : abs(clicks) / 2;

        /*System.out.println("delta" + delta);
        System.out.println(computeBestSteps(1.9537838031769912,
                new int[]{5, 4, 5},
                new int[]{1, 2, 5})[0]);
         */
       // delta = (Double.compare(scale, 1) > 0) ? delta * 2 : delta / 2;
       /* if (mouseWheelEvent.getWheelRotation() > 0){
            this.delta *= 2;
            sc.setRealH(sc.getRealH() * 2);
            sc.setRealW(sc.getRealW() * 2);
        }else{
            this.delta /= 2;
            sc.setRealH(sc.getRealH() / 2);
            sc.setRealW(sc.getRealW() / 2);
        }

        */
        //автоцентрирование
        //TODO сделать через параметр
        sc.setyR(sc.getRealH() / 2);
        sc.setxR(-sc.getRealW() / 2);
        xAxis.setP1(new RealPoint(xAxis.getP1().getX() * scale, 0));
        xAxis.setP2(new RealPoint(xAxis.getP2().getX() * scale, 0));
        yAxis.setP1(new RealPoint(0, yAxis.getP1().getY() * scale));
        yAxis.setP2(new RealPoint(0, yAxis.getP2().getY() * scale));

        chartPainter.setConverter(sc);
        chartPainter.setXAxis(xAxis);
        chartPainter.setYAxis(yAxis);

        /*xAxis.setP2(new RealPoint(sc.getRealW(), 0));
        yAxis.setP1(new RealPoint(0, -sc.getRealH()));
        yAxis.setP2(new RealPoint(0, sc.getRealH()));

         */
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
            /*RealPoint vector = new RealPoint(
                    deltaReal.getX() - sc.getxR(),
                    deltaReal.getY() - sc.getyR());
             */
            sc.setxR(deltaReal.getX());
            sc.setyR(deltaReal.getY());
            chartPainter.setConverter(sc);
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

   /* @Override
    public void repaint() {
        /*super.repaint();
        if(chartPainter == null)
            return;
        //drawAll(this.getGraphics());
        if(allFunc != null && !allFunc.isEmpty())
            chartPainter.drawChart(allFunc.get(allFunc.size() - 1));




        /*if(sc == null)
            return;
        sc.setScreenW(getWidth()); sc.setScreenH(getHeight());
        bf = new BufferedImage(
                getWidth(), getHeight(),
                BufferedImage.TYPE_INT_RGB);


        if(chartPainter == null)
            return;
        Graphics g = this.getGraphics();
        BufferedImagePixelDrawer pd = new BufferedImagePixelDrawer(bf);
        DDALineDrawer ld = new DDALineDrawer(pd);
        BufferedImageStringDrawer sd = new BufferedImageStringDrawer(bf);
        /*this.chartPainter = new ChartPainter(pd, ld, sd, sc);
        chartPainter.setXAxis(xAxis);
        chartPainter.setYAxis(yAxis);


        Graphics2D graphics2D = bf.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        graphics2D.dispose();

        allFunc.forEach(this::drawOneChart);
        drawAll(g);
        //chartPainter.drawChart(new ComplexFunction(new Sine().setA(2), new Cosine().setW(5), '*', 0));
        g.drawImage(bf, 0, 0, null);
    }
    */


    public void addFunc(AbstractFunction function) {
        allFunc.add(function);
        lastAction = Actions.ADD;
       // drawOneChart(function);
    }

    public void drawOneChart(AbstractFunction function) {
        //addFunc(function);

        chartPainter.drawChart(function);
        //repaint();
        //repaintCharts();
       //repaint();
    }

    /*private void repaintCharts() {
        paint(this.getGraphics());
    }

     */

    public void removeFunc(AbstractFunction f) {
        allFunc.remove(f);
        lastAction = Actions.REMOVE;
        //allFunc.forEach(this::drawOneChart);
    }

   /* public ChartPainter getChartPainter() {
        return chartPainter;
    }

    */
    enum Actions{
        ADD,
       REMOVE
   }
}
