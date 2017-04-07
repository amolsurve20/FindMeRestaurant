package amols.com.filterlist;

/**
 * Created by amolsurve on 10/21/16.
 */

public class Restaurants
{
    public Restaurants(int id, String name, String team, int picture){
        this.id = id;
        this.name = name;
        this.location = team;
        this.picture = picture;
    }

    private int id;
    private String name;
    private String location;
    private int picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String team) {
        this.location = location;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public int getId() {
        return id;
    }
}


