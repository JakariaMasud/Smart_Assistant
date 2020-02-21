package com.example.smartassistant.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "location_based_table")
public class LocationBasedEvent {
    @PrimaryKey(autoGenerate = true)
    long id;
    String title;
    float radiusInMeter;
    double latitude;
    double longitude;
    String address;

    public LocationBasedEvent() {
    }
    @Ignore
    public LocationBasedEvent(final long id, final String title, final float radiusInMeter, final double latitude, final double longitude, final String address) {
        this.id = id;
        this.title = title;
        this.radiusInMeter = radiusInMeter;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }
    @Ignore
    public LocationBasedEvent(final String title, final float radiusInMeter, final double latitude, final double longitude, final String address) {
        this.title = title;
        this.radiusInMeter = radiusInMeter;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public float getRadiusInMeter() {
        return this.radiusInMeter;
    }

    public void setRadiusInMeter(final float radiusInMeter) {
        this.radiusInMeter = radiusInMeter;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }
}
