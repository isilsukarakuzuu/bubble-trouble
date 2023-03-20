public class Ball {
    private final double GRAVITY = 0.000003;
    private final double HEIGHT_MULTIPLIER = 1.75;
    private final double RADIUS_MULTIPLIER = 2.0;
    private final double minPossibleHeight = Environment.getPlayerHeight() * 1.4;
    private final double minPossibleRadius = Environment.scaleY * 0.0175;
    private double maxHeight;
    private int level;
    private boolean movingRight;
    private double lastTime;
    private double lastGroundHitPositionX;
    private double lastGroundHitPositionY;

    public Ball(double startPositionX, double startPositionY, int level, boolean movingRight){
        this.level = level;
        this.movingRight = movingRight;
        initializeBall(startPositionX, startPositionY);
    }

    private void initializeBall(double startPositionX, double startPositionY){
        this.maxHeight = minPossibleHeight * Math.pow(HEIGHT_MULTIPLIER, this.level);
        this.lastTime = Environment.getCurrentTime(); // - Math.sqrt(2 * (this.maxHeight-getRadius()) / gravity);
        this.lastGroundHitPositionX = startPositionX - getVelocityX() * getTimeDifference();
        this.lastGroundHitPositionY = startPositionY;

    }

    private void bounceGround(){
        if(getPositionY() - getRadius() <= 0){
            lastGroundHitPositionX = getPositionX();
            lastGroundHitPositionY = getRadius();
            lastTime = Environment.getCurrentTime();
        }
    }
    private void bounceWall(){
        if(getPositionX() - getRadius() < 0){
            lastGroundHitPositionX -= 2*(lastGroundHitPositionX - getRadius());
            movingRight = true;
        }
        if(getPositionX() + getRadius() > Environment.scaleX){
            lastGroundHitPositionX += 2*(Environment.scaleX - getRadius() - lastGroundHitPositionX);
            movingRight = false;
        }
    }

    public boolean doesIntersect(double pointX, double pointY){
        return Math.sqrt(Math.pow(getPositionX() - pointX, 2) + Math.pow(getPositionY() - Math.min(pointY, getPositionY()), 2)) <= getRadius();
    }

    public void drawFrame(){
        bounceGround();
        bounceWall();
        StdDraw.picture(getPositionX(), getPositionY(), "images/ball.png", getRadius()*2, getRadius()*2);
    }
    public double getRadius(){
        return minPossibleRadius * Math.pow(RADIUS_MULTIPLIER, this.level);
    }

    public double getVelocityX(){
        if(movingRight) return Environment.scaleX / Environment.PERIOD_OF_BALL;
        return -Environment.scaleX / Environment.PERIOD_OF_BALL;
    }
    public double getPositionX(){
        return lastGroundHitPositionX + getVelocityX() * getTimeDifference() ;
    }
    public double getPositionY(){
        double time = getTimeDifference();
        return lastGroundHitPositionY + Math.sqrt(2.0 * GRAVITY * (this.maxHeight-getRadius())) * time - GRAVITY * Math.pow(time, 2) / 2;
    }

    private double getTimeDifference(){
        return Environment.getCurrentTime() - lastTime;
    }
    public int getLevel() {
        return level;
    }
}