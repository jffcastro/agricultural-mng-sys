package Data_info;

import java.util.Objects;

/**
 * The type Localization.
 */
public class Localization implements Comparable<Localization> {

    private String locId;
    private float lat;
    private float lng;
    private User user;

    /**
     * Instantiates a new Localization.
     *
     * @param locId the loc id
     * @param lat   the lat
     * @param lng   the lng
     * @param user  the user
     */
    public Localization(String locId, float lat, float lng, User user) {
        this.locId = locId;
        this.lat = lat;
        this.lng = lng;
        this.user = user;
    }

    /**
     * Instantiates a new Localization.
     *
     * @param locId the loc id
     */
    public Localization(String locId) {
        this.locId = locId;
    }

    /**
     * Gets loc id.
     *
     * @return the loc id
     */
    public String getLocId() {
        return locId;
    }

    /**
     * Sets loc id.
     *
     * @param locId the loc id
     */
    public void setLocId(String locId) {
        this.locId = locId;
    }

    /**
     * Gets lat.
     *
     * @return the lat
     */
    public float getLat() {
        return lat;
    }

    /**
     * Sets lat.
     *
     * @param lat the lat
     */
    public void setLat(float lat) {
        this.lat = lat;
    }

    /**
     * Gets lng.
     *
     * @return the lng
     */
    public float getLng() {
        return lng;
    }

    /**
     * Sets lng.
     *
     * @param lng the lng
     */
    public void setLng(float lng) {
        this.lng = lng;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localization that = (Localization) o;
        return (Float.compare(that.lat, lat) == 0 && Float.compare(that.lng, lng) == 0) || Objects.equals(locId, that.locId) || Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locId, user);
    }

    @Override
    public String toString() {
        return "Localization{" +
                "locId='" + locId + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", user=" + user +
                '}';
    }

    @Override
    public int compareTo(Localization o) {
        return this.user.compareTo(o.user);
    }
}
