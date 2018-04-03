package com.harium.etyl.core.graphics;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AWTGraphicsHelperTest {

    private AWTGraphicsHelper helper;

    @Before
    public void setUp() {
        helper = new AWTGraphicsHelper();
    }

    @Test
    public void testGetBytesBGR() {
        BufferedImage image = new BufferedImage(3, 1, BufferedImage.TYPE_3BYTE_BGR);
        draw(image);

        byte[] expected = {-1, 0, 0, 0, -1, 0, 0, 0, -1};
        Assert.assertArrayEquals(expected, AWTGraphicsHelper.getBytes(image));
    }

    @Test
    public void testGetBytesIntRGB() {
        BufferedImage image = new BufferedImage(3, 1, BufferedImage.TYPE_INT_RGB);
        draw(image);

        byte[] expected = {-1, 0, 0, 0, -1, 0, 0, 0, -1};
        Assert.assertArrayEquals(expected, AWTGraphicsHelper.getBytes(image));
    }

    private void draw(BufferedImage image) {
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.RED);
        graphics.fillRect(0, 0, 1, 1);
        graphics.setColor(Color.GREEN);
        graphics.fillRect(1, 0, 1, 1);
        graphics.setColor(Color.BLUE);
        graphics.fillRect(2, 0, 1, 1);
    }

}
