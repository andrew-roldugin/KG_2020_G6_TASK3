package com.company.Drawers.LineDrawer;

import com.company.Drawers.PixelDrawer.PixelDrawer;
import com.company.common.Geometry.ScreenPoint;

import java.awt.*;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.max;

public class DDALineDrawer implements LineDrawer {
    private PixelDrawer pixelDrawer;
    private Color color;

    public DDALineDrawer(PixelDrawer pixelDrawer, Color c1) {
        this.pixelDrawer = pixelDrawer;
        this.color = c1;
    }

    public DDALineDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
        this.color = Color.BLACK;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        int x1 = p1.getX(), y1 = p1.getY();
        int x2 = p2.getX(), y2 = p2.getY();

        if(x1 == x2 && y1 == y2) {
            pixelDrawer.drawPixel(x1, y1, color);
            return;
        }
        int dx = x2 - x1;
        int dy = y2 - y1;

        if(Math.abs(dx) > Math.abs(dy)){
            double k = 1. * dy / dx;
            if(x1 > x2){
                int tmp = x1; x1 = x2; x2 = tmp;
                tmp = y1; y1 = y2; y2 = tmp;
            }

            for (int x = x1; x <= x2; x++) {
                double y = k * (x - x1) + y1;
                pixelDrawer.drawPixel(x, (int) y, color);
            }
        }else{
            double kInv = 1. * dx / dy;
            if(y1 > y2){
                int tmp = x1; x1 = x2; x2 = tmp;
                tmp = y1; y1 = y2; y2 = tmp;
            }
            for (int y = y1; y <= y2; y++) {
                double x = (y - y1) * kInv + x1;
                pixelDrawer.drawPixel((int) x, y, color);
            }
        }
    }

    private int signum(int x) {
        return x < 0 ? -1 : x > 0 ? 1 : 0;
    }
}
