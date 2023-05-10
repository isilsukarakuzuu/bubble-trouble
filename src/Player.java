public class Player {
    private double currentPosition;
    private final double velocityPerFrame;
    private final Arrow arrow;
    public Player(double startPosition) {
        this.currentPosition = startPosition;
        this.velocityPerFrame = Environment.scaleX * Environment.PAUSE_DURATION / Environment.PERIOD_OF_PLAYER;
        this.arrow = new Arrow();
    }

    public boolean doesIntersect(double pointX, double pointY, double ballRadius){
        //(x – h)^2 + (y – k)^2 = r^2  left -> (x - w_start)^2 + (y - h_end^2) = ballRadius^2, right -> (x - w_end)^2 + (y - h_end^2) = ballRadius^2
        double w_start = currentPosition - Environment.getPlayerWidth() / 2;
        double w_end = currentPosition + Environment.getPlayerWidth() / 2;
        double h_end = Environment.getPlayerHeight();

        if(pointY <= h_end && pointX >= w_start - ballRadius && pointX <= w_end + ballRadius){
            return true;
        }
        if(pointY <= h_end + ballRadius && pointX <= w_end && pointX >= w_start){
            return true;
        }
        if (pointX >= w_start - ballRadius && pointY > h_end &&
                (pointX - w_start) * (pointX - w_start) + (pointY - h_end) * (pointY - h_end) <= ballRadius * ballRadius) {
            return true;
        }
        if (pointX <= w_end + ballRadius && pointY > h_end &&
                (pointX - w_end) * (pointX - w_end) + (pointY - h_end) * (pointY - h_end) <= ballRadius * ballRadius) {
            return true;
        }
        return false;
    }

    public void drawFrame(){
        arrow.drawFrame();
        // Students who would like to examine the circle-rectangle intersection can uncomment the following line.
        // StdDraw.rectangle(currentPosition, Environment.getPlayerHeight()/2, Environment.getPlayerWidth()/2, Environment.getPlayerHeight()/2); //
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
