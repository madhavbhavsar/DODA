package com.mad.doda.model;

import android.content.Intent;

public class Drawing {
    String title;
    String id;
    String path;
    String markers;
    String addtime;

    public Drawing() {
    }

    public Drawing(String title, String id, String path,String markers, String addtime) {
        this.title = title;
        this.id = id;
        this.path = path;
        this.addtime = addtime;
        this.markers = markers;
    }

    public String getMarkers() {
        return markers;
    }

    public void setMarkers(String markers) {
        this.markers = markers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
