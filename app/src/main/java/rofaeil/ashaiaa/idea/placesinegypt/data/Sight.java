package rofaeil.ashaiaa.idea.placesinegypt.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * defines columns for Sight table in the database schema
 * also, defines sight in get sights response
 *
 * @author Rofaeil Ashaiaa
 *         Created on 19/10/17.
 */

@Entity(tableName = "sight")
public class Sight {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @ColumnInfo(name = "placeDescription")
    @SerializedName("placeDescription")
    @Expose
    private String placeDescription;

    @Embedded
    @SerializedName("image")
    @Expose
    private Image image;

    @ColumnInfo(name = "price")
    @SerializedName("price")
    @Expose
    private int price;

    @ColumnInfo(name = "isFeatured")
    private int isFeatured;

    public Sight(int id, String placeDescription, Image image, int price) {
        this.id = id;
        this.placeDescription = placeDescription;
        this.image = image;
        this.price = price;
    }

    public Sight() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(int isFeatured) {
        this.isFeatured = isFeatured;
    }
}