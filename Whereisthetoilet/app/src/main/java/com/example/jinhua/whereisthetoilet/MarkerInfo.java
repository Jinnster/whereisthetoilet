package com.example.jinhua.whereisthetoilet;

import org.w3c.dom.Text;

/**
 * Created by Jinhua on 24-5-2015.
 */
public class MarkerInfo {
    private String mLabel;
    private String mIcon;
    private Double mLat;
    private Double mLng;
    private String mDescription;

    public MarkerInfo (String label, String icon, Double lat, Double lng, String description){
        this.mLabel = label;
        this.mIcon = icon;
        this.mLat = lat;
        this.mLng = lng;
        this.mDescription = description;
    }

    public String getmLabel()
    {
        return mLabel;
    }

    public void setmLabel(String mLabel){
        this.mLabel = mLabel;
    }

    public String getmIcon(){
        return mIcon;
    }

    public void setmIcon(String mIcon){
        this.mIcon = mIcon;
    }

    public Double getmLat(){
        return mLat;
    }

    public void setmLat(Double mLat){
        this.mLat = mLat;
    }

    public Double getmLng(){
        return mLng;
    }

    public void setmLng(Double mLng){
        this.mLng = mLng;
    }

    public String getmDescription(){
        return mDescription;
    }

    public void setmDescription(String mDescription){
        this.mDescription = mDescription;
    }


}

