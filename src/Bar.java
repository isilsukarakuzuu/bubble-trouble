import java.awt.*;

public class Bar {
    public void drawFrame(){
        StdDraw.picture(Environment.scaleX/2, -0.5, "images/bar.png", Environment.scaleX, 1.0);
        StdDraw.setPenColor(new Color(1, Math.max(1 - (float)Environment.getTimeDifference()/40000, 0),0));
        StdDraw.filledRectangle(Environment.scaleX/2 - Environment.scaleX/2 *Environment.getTimeDifference()/40000, -0.5, Math.max(0,Environment.scaleX/2 - Environment.scaleX*Environment.getTimeDifference()/40000/2), 0.25);
    }
}

