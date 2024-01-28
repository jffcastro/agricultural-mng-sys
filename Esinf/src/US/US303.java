package US;

import Data_info.GraphStore;
import Data_info.Localization;
import graph.Algorithms;
import graph.map.MapGraph;

import java.util.*;

/**
 * The type Us 303.
 */
public class US303 {
    /**
     * The Companies list.
     */
    static ArrayList<Localization> companiesList = GraphStore.companiesList;
    /**
     * The Map graph.
     */
    static MapGraph<Localization, Float> mapGraph = GraphStore.mapGraph;
    /**
     * The Paths.
     */
    static ArrayList<LinkedList<Localization>> paths = new ArrayList<>();
    /**
     * The Dists.
     */
    static ArrayList<Double> dists = new ArrayList<>();

    /**
     * Us 303 list.
     *
     * @param n the n
     * @return the list
     */
    public static List<CompanyMediaPair> US303(int n) {
        ArrayList<CompanyMediaPair> companyMediaPairs = new ArrayList<>();
        CompanyMediaPair companyMediaPair;
        double acumulador = 0;
        int contador;
        for (Localization l : companiesList) {
            contador = 0;
            Algorithms.shortestPathsWeighted(mapGraph, l, paths, dists);
            for (int x = 0; x < paths.size(); x++) {
                if (paths.get(x).getLast().getUser().getUserCode() != 'E') {
                    acumulador += dists.get(x);
                    contador++;
                }
            }
            companyMediaPair = new CompanyMediaPair(l, acumulador / contador);
            companyMediaPairs.add(companyMediaPair);
        }

        Collections.sort(companyMediaPairs);
        List<CompanyMediaPair> newList;
        if (n < companyMediaPairs.size()) {
            newList = companyMediaPairs.subList(0, n);
        } else {
            newList = companyMediaPairs;
        }

        GraphStore.hubs = new ArrayList<>(newList);
        return newList;
    }

}
