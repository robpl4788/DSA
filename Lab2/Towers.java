package Lab2;

public class Towers {
    private static void moveDisk(int source, int destination, int recursionLevel, int n) {
        for (int i = 0; i < recursionLevel; i ++ ){
            System.out.print("     ");
        }
        System.out.println("Recursion Level = " + String.valueOf(1 + recursionLevel));
        for (int i = 0; i < recursionLevel; i ++ ){
            System.out.print("     ");
        }
        System.out.println("Moving disk " + String.valueOf(n) + " from " + String.valueOf(source) + " " + "to " + String.valueOf(destination));
        for (int i = 0; i < recursionLevel; i ++ ){
            System.out.print("     ");
        }
        System.out.println("n=" + String.valueOf(n) + " src=" + String.valueOf(source) + " dest=" + String.valueOf(destination));
        }

    private static void towers(int n, int source, int destination, int recursionLevel) {
        if (n == 1) {
            moveDisk(source, destination, recursionLevel, n);
        }
        else {
            int temp = 6 - source - destination;
            towers(n - 1, source, temp, recursionLevel + 1);
            moveDisk(source, destination, recursionLevel, n);
            towers(n - 1, temp, destination, recursionLevel + 1);
        }
    }

    public static void main(String[] args) {
        towers(3, 1, 3, 0);
    }
}
