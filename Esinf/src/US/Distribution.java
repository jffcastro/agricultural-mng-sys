package US;

import Data_info.Localization;
import graph.Edge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Distribution {


    private HashMap<Localization, Set<Expedition>> basketsPerHub;
    private HashMap<Localization, ArrayList<Edge<Localization, Double>>> distanceBetweenEveryCrossingPoint;
    private double totalDistance;

    public Distribution(HashMap<Localization, Set<Expedition>> basketsPerHub, HashMap<Localization, ArrayList<Edge<Localization, Double>>> distanceBetweenEveryCrossingPoint, double totalDistance) {
        this.basketsPerHub = basketsPerHub;
        this.distanceBetweenEveryCrossingPoint = distanceBetweenEveryCrossingPoint;
        this.totalDistance = totalDistance;
    }

    public HashMap<Localization, Set<Expedition>> getBasketsPerHub() {
        return basketsPerHub;
    }

    public HashMap<Localization, ArrayList<Edge<Localization, Double>>> getDistanceBetweenEveryCrossingPoint() {
        return distanceBetweenEveryCrossingPoint;
    }

    public double getTotalDistance() {
        return totalDistance;
    }
}
