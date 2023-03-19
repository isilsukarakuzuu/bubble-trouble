public class Main {

    public static void main(String[] args) {
        Environment env = new Environment(800, 500, 16, 9);
        while(env.run());
        System.exit(0);
    }

}
