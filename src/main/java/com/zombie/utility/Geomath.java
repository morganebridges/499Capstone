package com.zombie.utility;

import com.zombie.models.dto.LatLng;

import java.util.Random;

/**
 * Created by morganebridges on 6/21/16.
 * This utility class is here to provide static methods to the services layer in order to make useful calculations.
 *
 */
public class Geomath {

    /**::  This routine calculates the distance between two points (given the
    /*::  latitude/longitude of those points). It is being used to calculate
    /*::
    /*::  Definitions:
    /*    South latitudes are negative, east longitudes are positive
    /*/
     public static double getDistance(double lat1, double lon1, double lat2, double lon2, String unit){
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }
        return dist;
    }

    public static double getDistance (LatLng latLng1, LatLng latLng2, String unit){
        return getDistance(latLng1.getLatitude(), latLng1.getLongitude(), latLng2.getLatitude(), latLng2.getLongitude(), unit );
    }


    /**
     * Converts degrees to radians
     * @param deg
     * @return
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
    public static double feetToMiles(double feet){ return feet * .000189;}
    public static double milesToFeet(double miles){ return (miles / .000189);}

    public static LatLng getRandomLocationWithin(double lat, double lng, double radius) {
        Random random = new Random();
        System.out.println("randomLocationWithin");
        System.out.println("Point of origin:");
        System.out.println("Lat: " + lat + " Long: " + lng );
        System.out.println("radius: " + radius);
        // Convert radius from meters to degrees
        double radiusInDegrees = metersToDegrees(radius);

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);
        System.out.println("x : " + x);
        System.out.println("y: " + y);

        // Adjust the x-coordinate for the shrinking of the east-west distances
        double new_x = x / Math.cos(lng);

        double foundLongitude = new_x + lat;

        double foundLatitude = y + lng;
        System.out.println("Longitude: " + foundLongitude + "  Latitude: " + foundLatitude );
        return new LatLng(foundLongitude, foundLatitude);
    }

    public static LatLng moveTowardsTarget(
            double sourceLat, double sourceLng, double destLat, double destLng, double meters) {
        double dx = deg2rad(destLng - sourceLng) * Math.cos(deg2rad(sourceLat)) * 6371000;
        double dy = deg2rad(destLat - sourceLat) * 6371000;
        double d = Math.sqrt(Math.abs(dx * dx + dy * dy));
        d = d < meters ? meters : d;
        double f = d == 0 ? 1 : meters / d;
        double newLat = sourceLat + (destLat - sourceLat) * f;
        double newLng = sourceLng + (destLng - sourceLng) * f;

        return new LatLng(newLat, newLng);
    }

    public static LatLng moveTowardsTarget(
            LatLng source, LatLng dest, double meters) {
        return moveTowardsTarget(source.getLatitude(), source.getLongitude(),
                dest.getLatitude(), dest.getLongitude(), meters);
    }

    public static boolean isInRange(
            double sourceLat, double sourceLng, double destLat, double destLng, double rangeInMeters) {
        double dx = deg2rad(destLng - sourceLng) * Math.cos(deg2rad(sourceLat)) * 6371000;
        double dy = deg2rad(destLat - sourceLat) * 6371000;
        double d = Math.sqrt(Math.abs(dx * dx + dy * dy));
        return d < rangeInMeters;
    }

    public static boolean isInRange(
            LatLng source, LatLng dest, double rangeInMeters) {
        return isInRange(source.getLatitude(), source.getLongitude(),
                dest.getLatitude(), dest.getLongitude(), rangeInMeters);
    }

    public static double metersToDegrees(double meters){
        return meters / 111000f;
    }

    public static double degreesToMeters(double degrees){
        return degrees * 111000f;
    }
}

