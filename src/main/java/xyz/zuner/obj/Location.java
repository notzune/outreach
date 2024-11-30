//package xyz.zuner.obj;
//
///**
// * <p>21:198:335:02 Data Structures & Algorithms</p>
// * <p>Data Structures Final Project</p>
// * <p>Rutgers ID: 199009651</p>
// * <br>
// *
// * Represents a geographical location specified by latitude and longitude.
// *
// * @author Zeyad "zmr15" Rashed
// * @mailto zmr15@scarletmail.rutgers.edu
// * @created 16 Nov 2024
// */
//public final class Location {
//
//    private final double latitude;  // latitude ranges from -90 to +90 degrees
//    private final double longitude; // longitude ranges from -180 to +180 degrees
//
//    /**
//     * Constructs a Location object with specified latitude and longitude.
//     *
//     * @param latitude  the latitude in degrees, between -90 and +90
//     * @param longitude the longitude in degrees, between -180 and +180
//     * @throws IllegalArgumentException if latitude or longitude are out of valid ranges
//     */
//    public Location(double latitude, double longitude) {
//        if (latitude < -90.0 || latitude > 90.0) {
//            throw new IllegalArgumentException("Latitude must be between -90 and +90 degrees.");
//        }
//        if (longitude < -180.0 || longitude > 180.0) {
//            throw new IllegalArgumentException("Longitude must be between -180 and +180 degrees.");
//        }
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }
//
//    /**
//     * Gets the latitude of this location.
//     *
//     * @return the latitude in degrees
//     */
//    public double getLatitude() {
//        return latitude;
//    }
//
//    /**
//     * Gets the longitude of this location.
//     *
//     * @return the longitude in degrees
//     */
//    public double getLongitude() {
//        return longitude;
//    }
//
//    /**
//     * Returns a string representation of this location in the format "(latitude, longitude)".
//     *
//     * @return a string representing this location
//     */
//    @Override
//    public String toString() {
//        return "(" + latitude + ", " + longitude + ")";
//    }
//
//    /**
//     * Parses a string in the format "(latitude, longitude)" and returns a new Location object.
//     *
//     * @param str the string to parse, expected in the format "(latitude, longitude)"
//     * @return a new Location object with the parsed latitude and longitude values
//     * @throws IllegalArgumentException if the input string is not in the correct format
//     */
//    public static Location parseString(String str) {
//        // validate input string matches expected pattern
//        if (str == null || !str.matches("\\(\\s*-?\\d+(\\.\\d+)?\\s*,\\s*-?\\d+(\\.\\d+)?\\s*\\)")) {
//            throw new IllegalArgumentException("Invalid format. Expected format is '(latitude, longitude)'");
//        }
//
//        // remove parentheses and split by the comma
//        String[] coordinates = str.substring(1, str.length() - 1).split("\\s*,\\s*");
//
//        // parse the latitude and longitude values
//        double latitude = Double.parseDouble(coordinates[0]);
//        double longitude = Double.parseDouble(coordinates[1]);
//
//        // return new location object
//        return new Location(latitude, longitude);
//    }
//
//    /**
//     * Checks if this location is equal to another object.
//     *
//     * @param obj the object to compare with
//     * @return true if the other object is a Location with the same latitude and longitude
//     */
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj instanceof Location) {
//            Location other = (Location) obj;
//            return Double.compare(this.latitude, other.latitude) == 0 &&
//                    Double.compare(this.longitude, other.longitude) == 0;
//        }
//        return false;
//    }
//
//    /**
//     * Computes a hash code for this location.
//     *
//     * @return a hash code value for this object
//     */
//    @Override
//    public int hashCode() {
//        return java.util.Objects.hash(latitude, longitude);
//    }
//}