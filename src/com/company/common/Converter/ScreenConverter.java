package com.company.common.Converter;

import com.company.common.Geometry.RealPoint;
import com.company.common.Geometry.ScreenPoint;

public class ScreenConverter {
    private double xR, yR, realW, realH;
    private int screenW, screenH;

    /**
     *
     * @param xR  координата по X левого верхнего угла области просмотра
     * @param yR  координата по Y левого верхнего угла области просмотра
     * @param wR  ширина области просмотра
     * @param hR  высота области просмотра
     * @param wS  ширина экрана
     * @param hS  высота экрана
     */
    public ScreenConverter(double xR, double yR, double wR, double hR, int wS, int hS) {
        this.xR = xR;
        this.yR = yR;
        this.realW = wR;
        this.realH = hR;
        this.screenW = wS;
        this.screenH = hS;
    }

    public ScreenPoint r2s(RealPoint p){
        double x = (p.getX() - xR) * screenW / realW;
        double y = (yR - p.getY()) * screenH / realH;
        return new ScreenPoint((int) x, (int) y);
    }
	
	public RealPoint screen2Real(ScreenPoint p){
        double x = p.getX() * realW / screenW + xR,
                y = yR - p.getY() * realH / screenH;
        return new RealPoint(x, y);
    }
	
    public double getxR() {
        return xR;
    }

    public void setxR(double xR) {
        this.xR = xR;
    }

    public double getyR() {
        return yR;
    }

    public void setyR(double yR) {
        this.yR = yR;
    }

    public double getRealW() {
        return realW;
    }

    public void setRealW(double realW) {
        this.realW = realW;
    }

    public double getRealH() {
        return realH;
    }

    public void setRealH(double realH) {
        this.realH = realH;
    }

    public int getScreenW() {
        return screenW;
    }

    public void setScreenW(int screenW) {
        this.screenW = screenW;
    }

    public int getScreenH() {
        return screenH;
    }

    public void setScreenH(int screenH) {
        this.screenH = screenH;
    }
}
