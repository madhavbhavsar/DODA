package com.mad.doda.model;

public class Markers {

    String markerId;
    String markerTitle;
    String markerDescription;
    String drawingId;

    public Markers() {
    }

    public Markers(String markerId, String markerTitle, String markerDescription, String drawingId) {
        this.markerId = markerId;
        this.markerTitle = markerTitle;
        this.markerDescription = markerDescription;
        this.drawingId = drawingId;
    }

    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(String markerId) {
        this.markerId = markerId;
    }

    public String getMarkerTitle() {
        return markerTitle;
    }

    public void setMarkerTitle(String markerTitle) {
        this.markerTitle = markerTitle;
    }

    public String getMarkerDescription() {
        return markerDescription;
    }

    public void setMarkerDescription(String markerDescription) {
        this.markerDescription = markerDescription;
    }

    public String getDrawingId() {
        return drawingId;
    }

    public void setDrawingId(String drawingId) {
        this.drawingId = drawingId;
    }
}
