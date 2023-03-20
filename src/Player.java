public class Player {
    private double currentPosition;
    private final double velocityPerFrame;
    private final Arrow arrow;
    public Player(double startPosition) {
        this.currentPosition = startPosition;
        this.velocityPerFrame = Environment.scaleX * Environment.PAUSE_DURATION / Environment.PERIOD_OF_PLAYER;
        this.arrow = new Arrow();
    }

    public boolean doesIntersect(double pointX, double pointY){
        if(pointX < currentPosition - Environment.getPlayerWidth() / 2) return false;
        if(pointX > currentPosition + Environment.getPlayerWidth() / 2) return false;
        return (pointY <= Environment.getPlayerHeight());
    }

    public void drawFrame(){
        arrow.drawFrame();
        StdDraw.picture(currentPosition, Environment.getPlayerHeight()/2, "images/player_back.png", Environment.getPlayerWidth(), Environment.getPlayerHeight());
    }

    public void moveRight(){
        if(isInCanvas(currentPosition + velocityPerFrame)){
            currentPosition += velocityPerFrame;
        }
    }

    public void moveLeft(){
        if(isInCanvas(currentPosition - velocityPerFrame)){
            currentPosition -= velocityPerFrame;
        }
    }

    private boolean isInCanvas(double x){
        if(0 > x - Environment.getPlayerWidth()/2) return false;
        if(Environment.scaleX < x + Environment.getPlayerWidth()/2) return false;
        return true;
    }

    public void shootArrow(){
        arrow.arrowMoveInitializer(currentPosition);
    }

    public double getArrowEndingPointX(){
        return arrow.getStartingPlace();
    }
    public double getArrowEndingPointY(){
        return arrow.getHeightOfArrow();
    }

    public boolean getActiveStatusOfArrow(){
        return arrow.isArrowActive();
    }

    public void setArrowInactive(){
        arrow.setInactive();
    }
}
