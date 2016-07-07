package com.zombie.utility;

import com.zombie.models.dto.LatLng;

/**
 * Created by morganebridges on 6/21/16.
 * This utility class is here to provide static methods to the services layer in order to make useful calculations.
 *
 */
public class Geomath {

    /**::  This routine calculates the distance between two points (given the
    /*::  latitude/longitude of those points). It is being used to calculate
    /*::  the distance between two locations using GeoDataSource (TM) prodducts
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
}
