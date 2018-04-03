# etyl-gdx-awt
Helper classes to integrate AWT and libGDX

## Snippet
```java
AWTGraphicsHelper awtGraphicsHelper = new AWTGraphicsHelper();
BufferedImage buffer;

public void draw(Graphics g) {
    Graphics2D graphics = buffer.createGraphics();
    graphics.setColor(Color.RED);
    graphics.fillRect(0, 0, 10, 10);
    awtGraphicsHelper.setGraphics((GDXGraphics) g);
    awtGraphicsHelper.drawImage(buffer, 0, 0);
}
```

## Maven
```xml
<dependency>
    <groupId>com.harium.etyl</groupId>
    <artifactId>etyl-gdx-awt</artifactId>
    <version>1.0.0</version>
</dependency>
```
