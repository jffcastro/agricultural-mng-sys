package US;

import Data_info.Localization;
import graph.Algorithms;
import graph.Edge;
import graph.Graph;
import graph.map.MapGraph;

import java.util.ArrayList;

/**
 * The type Us 305.
 */
public class US305 {

    /**
     * Us 305 graph.
     *
     * @param g the g
     * @return the graph
     */
    public static Graph<Localization, Float> Us305(Graph<Localization, Float> g){
        Graph<Localization, Float> noCompanies = noCompanies(g);
        return Algorithms.kruskall(noCompanies);
    }

    private static Graph<Localization, Float> noCompanies(Graph<Localization, Float> g){
        ArrayList<Edge<Localization, Float>> listaEdges = new ArrayList<>(g.edges());
        ArrayList<Edge<Localization, Float>> listaNovaArestas = new ArrayList<>();
        ArrayList<Localization> listaNovaVertices = new ArrayList<>();
        Edge<Localization, Float> edgeAuxiliar;

        for (Edge<Localization, Float> edge : listaEdges){
            if (edge.getVDest().getUser().getUserCode() == 'E' && edge.getVOrig().getUser().getUserCode() != 'E'){
                for (Localization user : g.adjVertices(edge.getVDest())) {
                    edgeAuxiliar = g.edge(edge.getVDest(), user);
                    if (user.getUser().getUserCode() != 'E' && edgeAuxiliar.getVDest() != edge.getVOrig()) {
                        listaNovaArestas.add(new Edge<>(edge.getVOrig(), user, edge.getWeight() + edgeAuxiliar.getWeight()));
                        listaNovaArestas.add(new Edge<>(user, edge.getVOrig(), edge.getWeight() + edgeAuxiliar.getWeight()));
                        listaNovaVertices.add(edge.getVOrig());
                    }
                }
            }else if (!(edge.getVOrig().getUser().getUserCode() == 'E')){
                listaNovaArestas.add(edge);
                listaNovaVertices.add(edge.getVOrig());
            }
        }

        Graph<Localization, Float> graphWithNoCompanies = new MapGraph<>(false);
        for (Localization localization : listaNovaVertices){
            graphWithNoCompanies.addVertex(localization);
        }

        for (Edge<Localization, Float> edge : listaNovaArestas){
            graphWithNoCompanies.addEdge(edge.getVOrig(), edge.getVDest(), edge.getWeight());
        }

        return graphWithNoCompanies;
    }

}
