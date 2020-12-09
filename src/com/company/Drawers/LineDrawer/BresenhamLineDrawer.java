package com.company.Drawers.LineDrawer;

import com.company.Drawers.PixelDrawer.PixelDrawer;
import com.company.common.Geometry.ScreenPoint;

import java.awt.*;

import static java.lang.StrictMath.abs;

public class BresenhamLineDrawer implements LineDrawer {

    private PixelDrawer pixelDrawer;
    private Color color = Color.BLACK;

    public BresenhamLineDrawer(PixelDrawer pixelDrawer, Color color) {
        this.pixelDrawer = pixelDrawer;
        this.color = color;
    }

    public BresenhamLineDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        drawBresenhamLine(x1, y1, x2, y2);
    }

    private void drawBresenhamLine(int x1, int y1, int x2, int y2) {
        if(x1 == x2 && y1 == y2) {
            pixelDrawer.drawPixel(x1, y1, color);
            return;
        }
        int x, y, error;
        boolean swap = false;

        int dx = x2 - x1;
        int dy = y2 - y1;
        int signX = signum(dx);
        int signY = signum(dy);
        dx = dx < 0 ? -dx : dx;
        dy = dy < 0 ? -dy : dy;
        x = x1;
        y = y1;

        if( dy > dx){
            int temp = dx; dx = dy; dy = temp;
            swap = true;
        }



        error = 2 * dy - dx;
        pixelDrawer.drawPixel(x, y, color);
        for(int i = 1; i < dx; i++){

            pixelDrawer.drawPixel(x, y, color);

            while (error >= 0){
                if(swap)
                    x += signX;
                else
                    y += signY;
                error -= 2 * dx;
            }
            if(swap)
                y += signY;
            else
                x += signX;
            error += 2 * dy;



        }
    }

    private int signum(int x) {
        return x < 0 ? -1 : x > 0 ? 1 : 0;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
