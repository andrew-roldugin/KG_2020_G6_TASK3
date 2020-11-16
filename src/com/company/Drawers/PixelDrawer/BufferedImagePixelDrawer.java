package com.company.Drawers.PixelDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImagePixelDrawer implements PixelDrawer {
    private BufferedImage bf;

    public BufferedImagePixelDrawer(BufferedImage bf) {
        this.bf = bf;
    }

    @Override
    public void drawPixel(int x, int y, Color color) {
        if(x >= 0 && y >= 0 && x < bf.getWidth() && y < bf.getHeight())
            bf.setRGB(x, y, color.getRGB());
    }
}
