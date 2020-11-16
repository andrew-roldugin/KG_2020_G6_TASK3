package com.company;

import com.company.Drawers.LineDrawer.DDALineDrawer;
import com.company.Drawers.LineDrawer.LineDrawer;
import com.company.Drawers.PixelDrawer.BufferedImagePixelDrawer;
import com.company.Drawers.PixelDrawer.PixelDrawer;
import com.company.Drawers.StringDrawer.BufferedImageStringDrawer;
import com.company.Drawers.StringDrawer.StringDrawer;
import com.company.Functions.AbstractFunction;
import com.company.Functions.Hyperbola;
import com.company.Functions.InvFunction;
import com.company.common.Converter.ScreenConverter;
import com.company.common.Geometry.Line;
import com.company.common.Geometry.RealPoint;
import com.company.common.Geometry.ScreenPoint;

import java.awt.*;
import java.math.BigDecimal;

import static java.lang.StrictMath.*;

public class ChartPainter {

    private final int[] minor = new int[] {5, 4, 5};
    private final int[] major = new int[] {1, 2, 5};
    private double density = .05;
    private double deltaMajorX, deltaMajorY;

    private Line xAxis, yAxis;
    private BufferedImagePixelDrawer pd;
    private LineDrawer ld;
    private BufferedImageStringDrawer sd;
    private ScreenConverter sc;

    public ChartPainter(BufferedImagePixelDrawer pd, LineDrawer ld, BufferedImageStringDrawer sd, ScreenConverter sc) {
        this.pd = pd;
        this.ld = ld;
        this.sd = sd;
        this.sc = sc;
    }

    private void drawOneLine(LineDrawer ld, Line l){
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }

    private void drawNumber(Graphics g, StringDrawer sd, double delta1, double delta2, double t, boolean flag) {
        //for (BigDecimal i = BigDecimal.valueOf(delta1); i.compareTo(BigDecimal.valueOf(t)) < 0; i = i.add(BigDecimal.valueOf(delta1))) {
        for (double i = delta1; i < t; i += delta1) {
            String num = String.valueOf(i);
            if (num.endsWith(".0"))
                num = num.substring(0, num.length() - 2);
            if (num.length() > 8)
                num = String.format("%.1e", i);

            FontMetrics fm = g.getFontMetrics();
            int width = fm.stringWidth(num);

            if (flag) {
                //цифры на оси X
                ScreenPoint temp = sc.r2s(new RealPoint(0, -0.5 * delta2));
                sd.drawString(String.valueOf(0), temp.getX(), temp.getY());
                ScreenPoint temp1 = sc.r2s(new RealPoint(i, -0.5 * delta2));
                sd.drawString(num, temp1.getX() - width / 2, temp1.getY());
                ScreenPoint temp2 = sc.r2s(new RealPoint(-i, -0.5 * delta2));
                sd.drawString("-" + num, temp2.getX() - width / 2, temp2.getY());
            } else {
                //цифры на оси Y
                ScreenPoint temp1 = sc.r2s(new RealPoint(-0.5 * delta2, i));
                sd.drawString(num, temp1.getX() - width, temp1.getY() + 5);
                ScreenPoint temp2 = sc.r2s(new RealPoint(-0.5 * delta2, -i));
                sd.drawString("-" + num, temp2.getX() - width, temp2.getY() + 5);
            }
        }
    }

    public void drawAxis(/*LineDrawer ld, StringDrawer sd,*/ Graphics g) {
        //отрисовка двух осей
        drawOneLine(ld, xAxis);
        drawOneLine(ld, yAxis);

        deltaMajorX = computeBestSteps(estimateMajors()[0], minor, major)[0];

        deltaMajorY = computeBestSteps(estimateMajors()[1], minor, major)[0];

        drawNumber(g, sd, deltaMajorX, deltaMajorY, xAxis.getP2().getX(), true);
        drawNumber(g, sd, deltaMajorY, deltaMajorX, yAxis.getP2().getY(), false);

    }
    public void drawGrid(/*PixelDrawer pd, LineDrawer ld,*/ boolean flag) {
        ld.setColor(Color.GRAY.brighter());
        double[] t = computeBestSteps(estimateMajors()[0], minor, major);
        double[] t1 = computeBestSteps(estimateMajors()[1], minor, major);
        double deltaMajorX = t[0];
        double deltaMajorY = t1[0];
        double deltaMinorX = t[1];
        double deltaMinorY = t1[1];

        double minW = sc.getxR() - deltaMajorX, minH = sc.getyR() - sc.getRealH() - deltaMajorY;
        double maxW = minW + sc.getRealW() + 2 * deltaMajorX, maxH = sc.getyR() + deltaMajorY;

        //вертикальные линии
        drawOneLine(ld, yAxis);
        for (double i = deltaMajorX, j = deltaMinorX; i <= max(maxW, abs((minW))); i += deltaMajorX, j += deltaMinorX) {
            drawOneLine(ld, new Line(i, minH, i, maxH));
            drawOneLine(ld, new Line(-i, minH, -i, maxH));
            if (flag){
                //мелкая сетка
                drawOneLine(ld, new Line(j, minH, j, maxH));
                drawOneLine(ld, new Line(-j, minH, -j, maxH));
               /* drawOneLine(ld, new Line(j, yAxis.getP1().getY(), j, maxH));
                drawOneLine(ld, new Line(-j, yAxis.getP1().getY(), -j, maxH));

                */
            }
        }

        //горизонтальные линии
        drawOneLine(ld, xAxis);
       // for (double i = deltaMajorY, j = deltaMinorY; i <= yAxis.getP2().getY(); i += deltaMajorY, j += deltaMinorY) {
         for (double i = deltaMajorY, j = deltaMinorY; i <= max(maxH, abs((minH))); i += deltaMajorY, j += deltaMinorY) {
           /* drawOneLine(ld, new Line(xAxis.getP1().getX(), i, maxW, i));
            drawOneLine(ld, new Line(xAxis.getP1().getX(), -i, maxW, -i));

            */
             drawOneLine(ld, new Line(minW, i, maxW, i));
             drawOneLine(ld, new Line(minW, -i, maxW, -i));
            if (flag){
                //мелкая сетка
                drawOneLine(ld, new Line(minW, j, maxW, j));
                drawOneLine(ld, new Line(minW, -j, maxW, -j));
            }
        }
    }
    private double[] estimateMajors() {
        int e = sc.getScreenW(),
                i = sc.getScreenH();
        double r = sc.getRealW(),
                o = sc.getRealH(),
                n = 50;
        return new double[]{
                n / e * r,
                n / i * o
        };
    }
    private double[] computeBestSteps(double t, int[] minor, int[] major) {
        double a = Double.MAX_VALUE;
        double n, r = 0;
        for (int s = 0; s < major.length; s++) {
            double i = major[s],
                    pow = Math.ceil(Math.log10(t / i));
            if((n = i * Math.pow(10, pow)) < a)
                r = (a = n) / minor[s];
        }
        return new double[]{a, r};
    }

    public void drawChart(AbstractFunction function) {
        drawChart(ld, function);
    }

    private void drawChart(LineDrawer ld, AbstractFunction function) {
        ld.setColor(Color.RED);
        double a, b, c, d, e;
       /*boolean swap = false;
       if (function instanceof InvFunction) {
           /*a = ( b = sc.getyR() ) - sc.getRealH();
           c = abs((e = sc.getxR()) + sc.getRealW());
           d = -2 * deltaMajorX;


           b = ( a = sc.getxR() ) + sc.getRealW();
           c = abs((e = sc.getyR()) - sc.getRealH());
           d = 2 * deltaMajorX;

           //a = yAxis.getP1().getY();
           //b = yAxis.getP2().getY();
           swap = true;
       } else {

         //  a = xAxis.getP1().getX();
         //  b = xAxis.getP2().getX();
       }

        */
        b = ( a = sc.getxR() ) + sc.getRealW();
        c = (e = sc.getyR()) - sc.getRealH();
        d = 2 * deltaMajorY;

        double density = (e + abs(c)) / sc.getScreenW();

        if (function instanceof Hyperbola) {
            density = max( density * density, 1e-4);
            double k = -((Hyperbola) function).getD() / ((Hyperbola) function).getB();
            double i = a + density, j;
            double t = Double.NEGATIVE_INFINITY, p = function.compute(i);
            for (j = i + density; j < k - density; j += density) {
                t = function.compute(j);
                Line l1 = new Line(i, p, j, t);
                drawOneLine(ld, l1);
                i = j;
                p = t;
            }

            if(t != Double.NEGATIVE_INFINITY && t > c) {
                drawOneLine(ld, new Line(i,  function.compute(i), k - density / 5, sc.getyR() - sc.getRealH() - d));
            }

            i = k + density / 5;
            t = k + density;
            p = function.compute(i);
            if(p < e) {
                drawOneLine(ld, new Line(i, e + d, t, function.compute(t)));
            }
            for (i = t, j = t + density; j < b + density; j += density) {
                t = function.compute(j);
                Line l = new Line(i, p, j, t);
                drawOneLine(ld, l);
                i = j;
                p = t;
            }
        }else if(function instanceof InvFunction){
            a = ( b = sc.getyR() ) - sc.getRealH();
            c = abs((e = sc.getxR()) + sc.getRealW());
            d = 10 * deltaMajorX;

            double i = a, p = function.compute(i);
            for (double j = i + density; j < b; j += density) {
                double t = function.compute(j);
                if(t >= e - d && t <= c + d) {
                    Line l = new Line(i, p, j, t).reverse();
                    drawOneLine(ld, l);
                }
                p = t;
                i = j;
            }
        }else {
            double i = a, p = function.compute(i);
            for (double j = i + density; j < b; j += density) {
                double t = function.compute(j);
                double viewportY1 = (p > 0 ? e + d : c - d);
                double viewportY2 = (t > 0 ? e + d : c - d);
                Line l = new Line(i,
                        viewportY1 < 0 ? max(p, viewportY1) : min(p, viewportY1),
                        j,
                        viewportY2 < 0 ? max(t, viewportY2) : min(t, viewportY2));
                drawOneLine(ld, l);
                p = t;
                i = j;
            }
        }
    }

    public void setXAxis(Line xAxis) {
        this.xAxis = xAxis;
    }

    public void setYAxis(Line yAxis) {
        this.yAxis = yAxis;
    }

    public void setConverter(ScreenConverter sc) {
        this.sc = sc;
    }
}

