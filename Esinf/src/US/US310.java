package US;

import Data_info.GraphStore;
import Data_info.Localization;
import graph.Algorithms;
import graph.Edge;
import graph.map.MapGraph;

import java.util.*;

public class US310 {
    /**
     * The Map graph.
     */
    static MapGraph<Localization, Float> mapGraph = GraphStore.mapGraph;


    // Este método pretende calcular a distribuição mais curta para entregar as expedições (encomendas) aos clientes.
    public static Distribution shortestPath(List<Expedition> expeditionList) {
        ArrayList<LinkedList<Localization>> paths = new ArrayList<>();
        ArrayList<Double> dists = new ArrayList<>();
        ArrayList<Localization> hubsWhereProducerHasToGo;

        // mapa que relaciona cada produtor com o conjunto de hubs onde eles têm de levar produtos
        HashMap<Localization, Set<Localization>> producerHubs = new HashMap<>();
        // mapa que relaciona cada hub com o conjunto de expedições que têm de ser entregues nesse hub
        HashMap<Localization, Set<Expedition>> basketsPerHub = new HashMap<>();
        // mapa que relaciona cada produtor com uma lista de edges, onde cada edge representa uma ligação entre hub e outo hub, que tem um peso (distância entre hubs)
        HashMap<Localization, ArrayList<Edge<Localization, Double>>> distanceBetweenEveryCrossingPoint = new HashMap<>();

        Set<Localization> producersOfEachExpedition;

        double totalDistance = 0;
        // percorre uma lista de expedições e, para cada uma delas, extrai o hub de entrega e o conjunto de produtores envolvidos na expedição
        for (Expedition expedition : expeditionList) {
            producersOfEachExpedition = expedition.getListOfProductsDispatched().keySet();
            Localization hub = expedition.getClient().getUser().getHub();
            for (Localization producer : producersOfEachExpedition) {
                if (producerHubs.get(producer) != null) {
                    producerHubs.get(producer).add(hub);
                } else {
                    Set<Localization> setLocalization = new TreeSet<>(); //para o contains tem complexidade logn
                    setLocalization.add(hub);
                    producerHubs.put(producer, setLocalization);
                }
            }
            if(basketsPerHub.get(hub) != null) {
                basketsPerHub.get(hub).add(expedition);
            } else {
                Set<Expedition> setExpedition = new HashSet<>();
                setExpedition.add(expedition);
                basketsPerHub.put(hub,setExpedition);
            }
        }

        // para cada produtor, usa o shortestPathsWeighted para calcular o caminho mais curto entre o produtor e cada um dos hubs onde tem de fazer entregas
        for (Localization producer : producerHubs.keySet()) {
            Algorithms.shortestPathsWeighted(mapGraph, producer, paths, dists);
            ArrayList<Edge<Localization, Double>> edges = new ArrayList<>();
            LinkedList<Localization> path;
            Localization possibleHub, closestHub = null;
            double compare = 100000000, distanceBetween2Localization;

            // este ‘loop’ percorre todos os caminhos mais curtos calculados pelo algoritmo de Dijkstra, e guarda o caminho mais curto até um hub que esteja na lista de hubs que o produtor tem de visitar
            for(int x = 0 ; x < paths.size() ; x++) {
                path = paths.get(x);
                distanceBetween2Localization = dists.get(x);
                possibleHub = path.getLast();
                if(producerHubs.get(producer).contains(possibleHub) && distanceBetween2Localization < compare){
                    compare = distanceBetween2Localization;
                    closestHub = possibleHub;
                }
            }
            hubsWhereProducerHasToGo = new ArrayList<>(producerHubs.get(producer));
            hubsWhereProducerHasToGo.remove(closestHub);
            edges.add(new Edge<>(producer,closestHub,compare));

            totalDistance += findEdges(hubsWhereProducerHasToGo,closestHub,edges,paths,dists) + compare;
            distanceBetweenEveryCrossingPoint.put(producer,edges);
        }

        return new Distribution(basketsPerHub,distanceBetweenEveryCrossingPoint,totalDistance);
    }

    // este método é responsável por calcular o caminho mais curto entre um produtor atual e os hubs em que tem de entregar produtos
    private static double findEdges(ArrayList<Localization> hubsWhereProducerHasToGo, Localization origin, ArrayList<Edge<Localization, Double>> edges,ArrayList<LinkedList<Localization>> paths,ArrayList<Double> dists){
        // Se a lista de hubs estiver vazia, o método retorna 0
        if(hubsWhereProducerHasToGo.size() == 0){
            return 0;
        }
        // começa por calcular o caminho mais curto entre o produtor atual e todos os outros pontos usando o método shortestPathsWeighted
        Algorithms.shortestPathsWeighted(mapGraph, origin, paths, dists);
        LinkedList<Localization> path;
        Localization possibleHub, closestHub = null;
        double compare = 100000000, distanceBetween2Localization;

        for(int x = 0 ; x < paths.size() ; x++) {
            path = paths.get(x);
            distanceBetween2Localization = dists.get(x);
            possibleHub = path.getLast();

            /* para cada caminho encontrado, ele verifica se o último ponto do caminho (que é um hub) está na lista de hubs em que o produtor tem que entregar.
            Se estiver, ele compara a distância desse caminho com a distância atualmente mais curta e, se for mais curta, atualiza a distância mais curta e o hub mais próximo */
            if(hubsWhereProducerHasToGo.contains(possibleHub) && distanceBetween2Localization < compare){
                compare = distanceBetween2Localization;
                closestHub = possibleHub;
            }
        }
        // remove o hub mais próximo da lista de hubs em que o produtor tem que entregar
        hubsWhereProducerHasToGo.remove(closestHub);
        // adiciona uma aresta (representando o caminho mais curto encontrado) à lista de edges
        edges.add(new Edge<>(origin,closestHub,compare));

        // chama o método de novo, passando por parâmetros a lista de hubs atualizada e o hub mais próximo como novo ponto de origem. No fim retorna a distância total percorrer
        return findEdges(hubsWhereProducerHasToGo,closestHub,edges,paths,dists) + compare;
    }
}
