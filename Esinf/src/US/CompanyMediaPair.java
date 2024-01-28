package US;

import Data_info.Localization;

/**
 * The type Companie media pair.
 */
public class CompanyMediaPair implements Comparable<CompanyMediaPair> {
    private Localization localization;

    private Double distance;

    /**
     * Instantiates a new Companie media pair.
     *
     * @param localization the localization
     * @param distance    the distancia
     */
    public CompanyMediaPair(Localization localization, Double distance) {
        this.localization = localization;
        this.distance = distance;
    }

    /**
     * Gets localization.
     *
     * @return the localization
     */
    public Localization getLocalization() {
        return localization;
    }

    /**
     * Sets localization.
     *
     * @param localization the localization
     */
    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    /**
     * Gets distancia.
     *
     * @return the distancia
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * Sets distancia.
     *
     * @param distance the distancia
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(CompanyMediaPair o) {
        return this.distance.compareTo(o.distance);
    }
}
