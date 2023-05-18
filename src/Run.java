package src;

public class Run {

    public static void main(String[] args) {
        switch (args[0]) {
            case "text":
                Text.main(args);
                break;
            case "engine":
                Engine.main(args);
                break;
            default:
                System.out.println("Invalid run arg.");
                break;
        }
    }

}
