package com.company.Drawers.LineDrawer;

import com.company.Drawers.PixelDrawer.PixelDrawer;
import com.company.common.Geometry.ScreenPoint;

import java.awt.*;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.round;

/**
 *
 * Реализация алгоритма Ву для рисования отрезков с применением сглаживания
 */
public class WuLineDrawer implements LineDrawer {
    private PixelDrawer pixelDrawer;

    public WuLineDrawer(PixelDrawer pixelDrawer, Color color) {
        this.pixelDrawer = pixelDrawer;
        this.color = color;
    }

    private Color color = Color.BLACK;

    public WuLineDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    /**
     * Публичный метод для рисования линии по алгоритму Ву
     * @param x1 координата x начала отрезка
     * @param y1 координата y начала отрезка
     * @param x2 координата x конца отрезка
     * @param y2 координата y конца отрезка
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        drawXiaolinWuLine(x1, y1, x2, y2);
    }

    private void drawXiaolinWuLine(int x1, int y1, int x2, int y2) {

        boolean swap = false;
        //"переворачиваем" координаты(x <-> y)
        if(abs(x2 - x1) < abs(y2 - y1)){
            int temp = x1; x1 = y1; y1 = temp;
            temp = x2; x2 = y2; y2 = temp;
            swap = true;
        }

        if (x2 < x1){
            int temp = x1; x1 = x2; x2 = temp;
            temp = y1; y1 = y2; y2 = temp;
        }

        float dx = x2 - x1;
        float dy = y2 - y1;

        // горизонтальные и вертикальные линии рисуются отдельно
        if (dx == 0) {
            drawVHLine(x1, y1, x2, y2);
            return;
        }

        // первый пиксель
        if(swap)
            pixelDrawer.drawPixel(y1, x1, color);
        else
            pixelDrawer.drawPixel(x1, y1, color);

        float k = dy / dx;
        float newCoord = y1 + k;

        for (int i = x1 + 1; i < x2; i++) {
            if (swap) {
                pixelDrawer.drawPixel((int) newCoord, i, new Color(0, 0, 0, (int) (255 * (1 - fractionalPart(newCoord)))));
                pixelDrawer.drawPixel((int) newCoord + 1, i, new Color(0, 0, 0, (int) (255 * fractionalPart(newCoord))));
            } else {
                pixelDrawer.drawPixel(i, (int) newCoord, new Color(0, 0, 0, (int) (255 * (1 - fractionalPart(newCoord)))));
                pixelDrawer.drawPixel(i, (int) newCoord + 1, new Color(0, 0, 0, (int) (255 * fractionalPart(newCoord))));
            }
            newCoord += k;
        }

        //последний пиксель
        if(swap)
            pixelDrawer.drawPixel(y2, x2, color);
        else
            pixelDrawer.drawPixel(x2, y2, color);
    }

    private void drawVHLine(int x1, int y1, int x2, int y2) {
        if(x1 == x2){
            for (int y = y1; y < y2; y++) {
                pixelDrawer.drawPixel(x1, y, color);
            }
        }else{
            for (int x = x1; x < x2; x++) {
                pixelDrawer.drawPixel(x, y1, color);
            }
        }
    }

    private float fractionalPart(float x) {
        return x - (int) x;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
