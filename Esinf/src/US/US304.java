package US;

import Data_info.GraphStore;
import Data_info.Localization;
import graph.Algorithms;
import graph.map.MapGraph;
import java.util.*;

/**
 * The type Us 304.
 */
public class US304 {

    /**
     * The Map graph.
     */
    static MapGraph<Localization, Float> mapGraph = GraphStore.mapGraph;

    /**
     * Closest hub list.
     *
     * @param hubsList the hubs list
     * @return the list
     */
    public static List<String> ClosestHub(List<CompanyMediaPair> hubsList) {

        if (hubsList.isEmpty()){
            return null;
        }

        List<String> lstClosestHub = new ArrayList<>();
        List<String> lstLocalization = new ArrayList<>();
        List<String> lstHubs = new ArrayList<>();
        List<Double> lstDistance = new ArrayList<>();
        List<String> allHubs = new ArrayList<>();

        for (CompanyMediaPair companyMediaPair : hubsList) {
            allHubs.add(companyMediaPair.getLocalization().getUser().getUserID());
        }

        for (int i = 0; i < mapGraph.vertices().size(); i++) {

            if (mapGraph.vertices().get(i).getUser().getUserCode()!='P' && !allHubs.contains(mapGraph.vertices().get(i).getUser().getUserID())){

                LinkedList<Localization> shortPath = new LinkedList<>();
                Localization vOrig = mapGraph.vertices().get(i);

                for (int j = 0; j < hubsList.size(); j++) {

                    double returnAlgo = Algorithms.shortestPathWeighted(mapGraph, vOrig, hubsList.get(j).getLocalization(), shortPath);

                    if (returnAlgo != 0) {

                        String localUserID = vOrig.getUser().getUserID();
                        String hubUserId = hubsList.get(j).getLocalization().getUser().getUserID();

                        lstLocalization.add(localUserID);
                        lstHubs.add(hubUserId);
                        lstDistance.add(returnAlgo);
                    }
                }

                Double minDist = Collections.min(lstDistance);
                int minDistPos = lstDistance.indexOf(minDist);
                mapGraph.vertices().get(i).getUser().setHub(hubsList.get(minDistPos).getLocalization());
                lstClosestHub.add("Client/Company: " + lstLocalization.get(minDistPos) + " | " + "Closest Hub: " + lstHubs.get(minDistPos) + " | " + "Distance: " + lstDistance.get(minDistPos) + "m");

                lstLocalization.clear();
                lstHubs.clear();
                lstDistance.clear();
                }
            }

        return  lstClosestHub;
    }
}
