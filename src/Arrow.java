public class Arrow {
    private long lastTime = -1000000009;
    double startingPlace;

    public Arrow() {
    }

    public void arrowMoveInitializer(double startingPlace){
        if(isArrowActive()) return;
        lastTime = Environment.getCurrentTime();
        this.startingPlace = startingPlace;
    }

    public void drawFrame(){
        if(!isArrowActive()) return;
        StdDraw.picture(startingPlace, getHeightOfArrow()/2, "arrow.png", 0.01*Environment.scaleX, getHeightOfArrow());
    }
    public boolean isArrowActive(){
        return getTimeDifference() < Environment.PERIOD_OF_ARROW;
    }


    private long getTimeDifference(){
        return Environment.getCurrentTime() - lastTime;
    }

    public double getHeightOfArrow(){
        if(!isArrowActive()) return -1000000009;
        return Environment.scaleY * (double)getTimeDifference() / (double)Environment.PERIOD_OF_ARROW;
    }

    public void setInactive(){
        lastTime = -1000000009;
    }
}
