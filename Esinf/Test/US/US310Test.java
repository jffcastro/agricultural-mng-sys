package US;

import Data_info.Localization;
import Data_info.ProductOrder;
import Data_info.User;
import Read.Read;
import graph.Edge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class US310Test {
    private static Read store = new Read();
    private String clientesProdutoresCSV, distanciasCSV, basketCSV;
    private List<Localization> hubs = new ArrayList<>();
    private List<Expedition> expeditionsList;
    private List<CompanyMediaPair> companieMediaPairs;

    @BeforeEach
    void setUp() {
        clientesProdutoresCSV = "Esinf/Test/clientes-produtores_small.csv";
        distanciasCSV = "Esinf/Test/distancias_small.csv";
        basketCSV = "Esinf/Test/cabazes_small.csv";

        store.readFromFileLocalizationsToTree(clientesProdutoresCSV);
        store.readFromFileToMatrixGraph(distanciasCSV);
        store.readOrdersFromFile(basketCSV);

        companieMediaPairs = US303.US303(1);
        for (CompanyMediaPair companyMediaPair : companieMediaPairs) {
            hubs.add(companyMediaPair.getLocalization());
        }
        US304.ClosestHub(companieMediaPairs);
        expeditionsList = US309.expeditionListOfACertainDay(2, 1, hubs);
    }

    @Test
    void shortestPath() {
        ArrayList<Localization> producersExpected = new ArrayList<>();
        ArrayList<Edge<Localization,Double>> edgesExpected = new ArrayList<>();
        Edge<Localization,Double> edgeExpected;
        Localization p2 = new Localization("CT6", (float) 40.2111, (float) -8.4291, new User('P', "P2"));
        Localization p1 = new Localization("CT17", (float) 40.6667, (float) -7.9167, new User('P', "P1"));
        Localization e2 = new Localization("CT11", (float) 39.3167, (float) -7.4167, new User('E', "E2"));
        int counter = 0, verCounter = 0;
        double totalDistanceExpected = 421038.0;

        producersExpected.add(p1);
        producersExpected.add(p2);

        edgesExpected.add(new Edge<>(p1,e2,131937.0));
        edgesExpected.add(new Edge<>(p2,e2,289101.0));

        Distribution dist = US310.shortestPath(expeditionsList);
        assertEquals(dist.getTotalDistance(), totalDistanceExpected);

        for (Localization localization : dist.getDistanceBetweenEveryCrossingPoint().keySet()) {
            assertEquals(localization,producersExpected.get(counter));

            for(Edge<Localization,Double> edge : dist.getDistanceBetweenEveryCrossingPoint().get(localization)){
                edgeExpected = edgesExpected.get(counter);
                assertEquals(edge.getVOrig(), edgeExpected.getVOrig());
                assertEquals(edge.getVDest(), edgeExpected.getVDest());
                assertEquals(edge.getWeight(), edgeExpected.getWeight());
            }

            counter++;
        }
    }
}