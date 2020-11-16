package com.company.Drawers.LineDrawer;

import com.company.common.Geometry.ScreenPoint;

import java.awt.*;

public interface LineDrawer {
    void drawLine(ScreenPoint p1, ScreenPoint p2);
    void setColor(Color color);
}
