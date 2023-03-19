public class Bar {
    public void drawFrame(){
        if(Environment.getTimeDifference()<12000)
            StdDraw.setPenColor(StdDraw.YELLOW);
        else if(Environment.getTimeDifference()<22000)
            StdDraw.setPenColor(StdDraw.ORANGE);
        else {
            StdDraw.setPenColor(StdDraw.RED);
        }
        StdDraw.filledRectangle(0.0, -1.0, Environment.scaleX*Environment.getTimeDifference()/40000, 1.0);
    }
}

