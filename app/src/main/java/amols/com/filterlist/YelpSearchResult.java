package amols.com.filterlist;

import java.util.List;

/**
 * Created by amolsurve on 11/2/16.
 */

public class YelpSearchResult {
    private Region region;
    private int total;
    private List<Business> businesses;
    private List<Location> locations;
    public Region getRegion() {
        return region;
    }
    public void setRegion(Region region) {
        this.region = region;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public List<Business> getBusinesses() {
        return businesses;
    }
    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

    public List<Location> getLocations() {
        return locations;
    }
    public void setLocations(List<Location> Locations) {
        this.locations = locations;
    }

}