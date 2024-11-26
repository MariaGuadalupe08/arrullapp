package com.example.arrullapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PlacesResponse {

    @SerializedName("results")
    private List<PlaceResult> results;

    public List<PlaceResult> getResults() {
        return results;
    }

    public static class PlaceResult {
        @SerializedName("name")
        private String name;

        @SerializedName("geometry")
        private Geometry geometry;

        public String getName() {
            return name;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public static class Geometry {
            @SerializedName("location")
            private Location location;

            public Location getLocation() {
                return location;
            }

            public static class Location {
                @SerializedName("lat")
                private double lat;

                @SerializedName("lng")
                private double lng;

                public double getLat() {
                    return lat;
                }

                public double getLng() {
                    return lng;
                }
            }
        }
    }
}
