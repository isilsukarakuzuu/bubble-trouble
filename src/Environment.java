import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Environment {
    public static double PLAYER_HEIGHT_SCALEY_RATE = 1.0/8.0;
    public static double PLAYER_HEIGHT_WIDTH_RATE = 37.0 / 23.0;

    public static long PERIOD_OF_PLAYER = 2300;
    public static long PERIOD_OF_ARROW = 1500;
    public static double PERIOD_OF_BALL = 15000;

    public static int PAUSE_DURATION = 15;
    public static int TOTAL_GAME_DURATION = 40000;

    public static int canvasWidth;
    public static int canvasHeight;
    public static double scaleX;
    public static double scaleY;
    public static long gameStartTime;

    private Bar bar;
    private Player player;
    private ArrayList<Ball> balls;

    public Environment(int canvasWidth, int canvasHeight, double scaleX, double scaleY) {
        Environment.canvasWidth = canvasWidth;
        Environment.canvasHeight = canvasHeight;
        Environment.scaleX = scaleX;
        Environment.scaleY = scaleY;

        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(0, scaleX);
        StdDraw.setYscale(-1, scaleY);
        StdDraw.enableDoubleBuffering();
    }

    private void buildEnvironment(){
        bar = new Bar();
        player = new Player(scaleX/2);
        this.balls = new ArrayList<>();

        this.balls.add(new Ball(scaleX/4, 0.5, 0, true));
        balls.add(new Ball(scaleX/3, 0.5, 1, false));
        this.balls.add(new Ball(scaleX/4, 0.5, 2, true));
    }

    public boolean run(){
        buildEnvironment();
        gameStartTime = getCurrentTime();
        while (balls.size() > 0) {
            StdDraw.clear(StdDraw.WHITE);
            StdDraw.picture(scaleX/2, scaleY/2, "images/background.png", scaleX, scaleY);
            bar.drawFrame();
            makeKeyEvents();
            player.drawFrame();

            if(isPlayerHit() || checkTimeOver()){
                return gameOverState();
            }

            arrowHitsBall();
            drawBalls();

            StdDraw.show();
            StdDraw.pause(PAUSE_DURATION);
        }

        return winState();
    }


    private void makeKeyEvents(){
        if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
            player.moveLeft();
        }
        if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)){
            player.moveRight();
        }
        if(StdDraw.isKeyPressed(KeyEvent.VK_SPACE)){
            player.shootArrow();
        }
    }
    private void arrowHitsBall() {
        if(!player.getActiveStatusOfArrow()) return;
        for(Ball ball : balls){
            if (ball.doesIntersect(player.getArrowEndingPointX(), player.getArrowEndingPointY())) {
                splitBall(ball);
                break;
            }
        }
    }

    private void drawBalls(){
        for(Ball ball : balls){
            ball.drawFrame();
        }
    }

    private boolean isPlayerHit(){
        for(Ball ball : balls){
            if(player.doesIntersect(ball.getPositionX(), ball.getPositionY()-ball.getRadius())){
                return true;
            }
        }
        return false;
    }
    private void splitBall(Ball ball){
        if (ball.getLevel() > 0) {
            balls.add(new Ball(ball.getPositionX(), ball.getPositionY(), ball.getLevel() - 1, false));
            balls.add(new Ball(ball.getPositionX(), ball.getPositionY(), ball.getLevel() - 1, true));
        }
        player.setArrowInactive();
        balls.remove(ball);
    }

    private boolean gameOverState(){
        drawGameControlScreen("GAME OVER!");
        return checkReplayState();
    }
    private boolean winState(){
        drawGameControlScreen("YOU WON!");
        return checkReplayState();
    }

    private void drawGameControlScreen(String message){
        StdDraw.picture(Environment.scaleX/2, Environment.scaleY/2.18, "images/game_screen.png", Environment.scaleX/3.8, Environment.scaleY/4);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont( new Font("Helvetica", Font.BOLD, 30) );
        StdDraw.text(Environment.scaleX/2, Environment.scaleY/2, message);
        StdDraw.setFont( new Font("Helvetica", Font.ITALIC, 15) );
        StdDraw.text(Environment.scaleX/2, Environment.scaleY/2.3, "To Replay Click \"Y\"");
        StdDraw.text(Environment.scaleX/2, Environment.scaleY/2.6, "To Quit Click \"N\"");

        StdDraw.show();
    }
    private boolean checkTimeOver(){
       return Environment.getTimeDifference()>TOTAL_GAME_DURATION;
    }

    private boolean checkReplayState(){
        while (true) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_Y)) {
                StdDraw.pause(10);
                return true;

            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_N)) {
                StdDraw.pause(10);
                return false;

            }
        }
    }

    public static double getPlayerHeight(){
        return scaleY * PLAYER_HEIGHT_SCALEY_RATE;
    }
    public static double getPlayerWidth(){
        return getPlayerHeight() / PLAYER_HEIGHT_WIDTH_RATE;
    }
    public static long getCurrentTime(){
        return System.currentTimeMillis();
    }
    public static long getTimeDifference(){
        return getCurrentTime() - gameStartTime;
    }
}
