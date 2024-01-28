package US;

import Data_info.GraphStore;
import Data_info.Localization;
import graph.Algorithms;
import graph.Edge;
import graph.map.MapGraph;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The type Us 302.
 */
public class US302 {

    private static MapGraph<Localization, Float> storeGraph = GraphStore.mapGraph;
    private static ArrayList<Localization> clientsList = GraphStore.clientsList;

    private static boolean verifyConnectivity() {

        Localization user = storeGraph.vertices().get(0);
        LinkedList<Localization> reachableVertex = Algorithms.DepthFirstSearch(storeGraph, user);
        return storeGraph.vertices().size() == reachableVertex.size();
    }

    /**
     * Shortest paths unweighted array list.
     *
     * @return the array list
     */
    public static ArrayList<ClientProducerDist> shortestPathsUnweighted() {
        if (verifyConnectivity()) {
            ArrayList<LinkedList<Localization>> paths = new ArrayList<>();
            ArrayList<Integer> dists = new ArrayList<>();
            ArrayList<ClientProducerDist> clientProducerDists = new ArrayList<>();

            for (Localization client : clientsList) {
                if (Algorithms.shortestPathsUnweighted(storeGraph, client, paths, dists)) {
                    for (int producer = 0; producer < paths.size(); producer++) {
                        if ((paths.get(producer).getLast().getUser().getUserCode() == 'P')) {
                            clientProducerDists.add(new ClientProducerDist(client.getUser(), paths.get(producer).getLast().getUser(), dists.get(producer)));
                        }
                    }
                } else {
                    System.out.println("invalid Vertex");
                }
            }
            System.out.println("Graph Connected");
            return clientProducerDists;
        }else {
            System.out.println("Graph not connected");
        }
        return null;
    }
}