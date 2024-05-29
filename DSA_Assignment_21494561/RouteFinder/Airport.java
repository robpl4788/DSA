package DSA_Assignment_21494561.RouteFinder;


// Class to store info about a specific airport
public class Airport {
    private String code;
    private String name;
    private double lattitude;
    private double longitude;
    private final double degreesToKm = 111.31949;

    public Airport(String code, String name, double lattitude, double longitude) {
        this.code = code;
        this.name = name;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    protected String getCode() {
        return code;
    }

    protected String getName() {
        return name;
    }

    protected double getLattitude() {
        return lattitude;
    }

    protected double getLongitude() {
        return longitude;
    }

    // Get the distance to another airport
    protected int getDistanceKm(Airport other) {
        double result;

        if (this.equals(other)) {
            result = 0;
        } else {

            //Assume pythagorean distance works fine on a sphere
            result = Math.pow(getLattitude() - other.getLattitude(), 2);
            result += Math.pow(getLongitude() - other.getLongitude(), 2);
            result = Math.sqrt(result);
            result *= degreesToKm;
        }

        return (int) result;
    }
}
