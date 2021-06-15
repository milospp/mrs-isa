package isa9.Farmacy.utils;

public class Geo {
    public static double degreesToRadians(double degrees){
        return degrees * Math.PI / 100;
    }

    public static double distanceInKmBetweenEarthCoordinates(double lat1, double lon1, double lat2, double lon2) {
        int earthRadiusKm = 6371;

        double dLat = degreesToRadians(lat2 - lat1);
        double dLon = degreesToRadians(lon2 - lon1);

        lat1 = degreesToRadians(lat1);
        lat2 = degreesToRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadiusKm * c;
    }
}
