package data;

/** A pixel on the grid. */
public final class Pixel {
    private final String hexColor;
    private final int xAxis;
    private final int yAxis;

    public Pixel(String hexColor, int xAxis, int yAxis) {
        this.hexColor = hexColor;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }
}