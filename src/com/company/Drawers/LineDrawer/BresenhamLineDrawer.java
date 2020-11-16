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
       /* if(abs(x2 - x1) < abs(y2 - y1)){
            int temp = x1; x1 = y1; y1 = temp;
            temp = x2; x2 = y2; y2 = temp;
            swap = true;
        }

        */
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
            /*if(!swap)
                pixelDrawer.drawPixel(x, y, color);
            else
                pixelDrawer.drawPixel(y, x, color);

            while(error >= 0){
                y += signY;
                error -= 2 * dx;
            }
            x += signX;
            error += 2 * dy;


             */
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
        /*for (int i = 0; i < dx; i++) {
            x += signX;
            if (error >= 0) {
                y += signY;
                error += 2 * (dy - dx);
            } else {
                error += 2 * dy;
            }
            if (swap)
                pixelDrawer.drawPixel(y, x, color);
            else
                pixelDrawer.drawPixel(x, y, color);
        }

         */
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
