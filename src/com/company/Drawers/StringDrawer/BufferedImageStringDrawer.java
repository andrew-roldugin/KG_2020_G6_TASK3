package com.company.Drawers.StringDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImageStringDrawer implements StringDrawer {
    private BufferedImage bufferedImage;

    public BufferedImageStringDrawer(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void drawString(String str, int x, int y) {
        Graphics2D g = bufferedImage.createGraphics();
        g.setPaint(Color.BLACK);
        g.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        g.drawString(str, x, y);
        g.dispose();
    }
}
