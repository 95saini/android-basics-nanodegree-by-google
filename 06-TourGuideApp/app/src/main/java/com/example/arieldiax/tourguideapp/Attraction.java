package com.example.arieldiax.tourguideapp;

public class Attraction {

    /**
     * @var mAttractionName Name of the attraction.
     */
    private String mAttractionName;

    /**
     * @var mAttractionDescription Description of the attraction.
     */
    private String mAttractionDescription;

    /**
     * @var mAttractionImageResourceId Image resource ID of the attraction.
     */
    private int mAttractionImageResourceId;

    /**
     * @var mAttractionLatitude Latitude of the attraction.
     */
    private double mAttractionLatitude;

    /**
     * @var mAttractionLongitude Longitude of the attraction.
     */
    private double mAttractionLongitude;

    /**
     * Creates a new Attraction object.
     *
     * @param attractionName            Name of the attraction.
     * @param attractionDescription     Description of the attraction.
     * @param attractionImageResourceId Image resource ID of the attraction.
     * @param attractionLatitude        Latitude of the attraction.
     * @param attractionLongitude       Longitude of the attraction.
     */
    public Attraction(String attractionName, String attractionDescription, int attractionImageResourceId, double attractionLatitude, double attractionLongitude) {
        mAttractionName = attractionName;
        mAttractionDescription = attractionDescription;
        mAttractionImageResourceId = (attractionImageResourceId > -1) ? attractionImageResourceId : 0;
        mAttractionLatitude = attractionLatitude;
        mAttractionLongitude = attractionLongitude;
    }

    /**
     * Gets the name of the attraction.
     *
     * @return The name of the attraction.
     */
    public String getAttractionName() {
        return mAttractionName;
    }

    /**
     * Gets the description of the attraction.
     *
     * @return The description of the attraction.
     */
    public String getAttractionDescription() {
        return mAttractionDescription;
    }

    /**
     * Gets the image resource ID of the attraction.
     *
     * @return The image resource ID of the attraction.
     */
    public int getAttractionImageResourceId() {
        return mAttractionImageResourceId;
    }

    /**
     * Gets the latitude of the attraction.
     *
     * @return The latitude of the attraction.
     */
    public double getAttractionLatitude() {
        return mAttractionLatitude;
    }

    /**
     * Gets the longitude of the attraction.
     *
     * @return The longitude of the attraction.
     */
    public double getAttractionLongitude() {
        return mAttractionLongitude;
    }
}
