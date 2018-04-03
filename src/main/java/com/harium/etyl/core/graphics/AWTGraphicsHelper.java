package com.harium.etyl.core.graphics;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Helper class to render awt images using libdgx
 */
public class AWTGraphicsHelper {

    private GDXGraphics graphics;

    /**
     * Reference: http://badlogicgames.com/forum/viewtopic.php?t=1000
     */
    public void drawImage(BufferedImage image, int x, int y) {
        int h = image.getHeight();

        Texture texture = buildTexture(image);

        graphics.beginBatch();
        graphics.getBatch().draw(texture, x, graphics.getHeight() - h + y);
        graphics.endBatch();

        texture.dispose();
    }

    public void drawBytes(byte[] bytes, int w, int h, int x, int y) {
        Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGB888);

        pixmap.getPixels().put(bytes);
        // Do not remove or you will get IllegalArgumentException
        pixmap.getPixels().flip();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        graphics.beginBatch();
        graphics.getBatch().draw(texture, x, graphics.getHeight() - h + y);
        graphics.endBatch();

        texture.dispose();
    }

    public static Texture buildTexture(BufferedImage image) {
        final byte[] bytes = getBytes(image);
        if (bytes == null) {
            return null;
        }

        return buildTexture(bytes, image.getWidth(), image.getHeight());
    }

    public static Texture buildTexture(byte[] bytes, int w, int h) {
        Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGB888);

        pixmap.getPixels().put(bytes);
        // Do not remove or you will get IllegalArgumentException
        pixmap.getPixels().flip();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return texture;
    }

    public static byte[] getBytes(BufferedImage image) {
        byte[] bytes;

        if (image.getType() == BufferedImage.TYPE_INT_RGB) {
            final int[] pixels = ((DataBufferInt) image.getData().getDataBuffer()).getData();
            bytes = toByteArray(pixels);
        } else if (image.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            final byte[] pixels = ((DataBufferByte) image.getData().getDataBuffer()).getData();
            bytes = BGRtoRGBArray(pixels);
        } else if (image.getType() == BufferedImage.TYPE_CUSTOM) {
            final byte[] pixels = ((DataBufferByte) image.getData().getDataBuffer()).getData();
            return pixels;
        } else {
            return null;
        }
        return bytes;
    }

    private static byte[] toByteArray(int[] pixels) {
        return toByteArray(pixels, 3);
    }

    private static byte[] toByteArray(int[] pixels, int size) {
        final ByteBuffer buf = ByteBuffer.allocate(pixels.length * size)
                .order(ByteOrder.BIG_ENDIAN);

        for (int i = 0; i < pixels.length; i++) {
            int rgb = pixels[i];

            byte r = (byte) ((rgb >> 16) & 0xFF);
            byte g = (byte) ((rgb >> 8 ) & 0xFF);
            byte b = (byte) ((rgb) & 0xFF);

            buf.put(r);
            buf.put(g);
            buf.put(b);
        }

        return buf.array();
    }

    private static byte[] BGRtoRGBArray(byte[] pixels) {
        final ByteBuffer buf = ByteBuffer.allocate(pixels.length);

        int[] incs = {2, 0, -2};
        int index;

        for (int i = 0; i < pixels.length; i++) {
            index = i + incs[i % 3];
            buf.put(pixels[index]);
        }

        return buf.array();
    }

    public GDXGraphics getGraphics() {
        return graphics;
    }

    public void setGraphics(GDXGraphics graphics) {
        this.graphics = graphics;
    }
}
