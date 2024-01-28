package US;

import Data_info.GraphStore;
import Data_info.Localization;
import Data_info.User;
import Read.Read;
import graph.map.MapGraph;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class US304Test {

    static Read store = new Read();
    public static MapGraph<Localization, Float> mapGraph = GraphStore.mapGraph;

    @org.junit.jupiter.api.BeforeEach
    public void setUp(){
        String clientesProdutoresCSV = "Esinf/Test/clientes-produtores_small.csv";
        String distanciasCSV = "Esinf/Test/distancias_small.csv";

        store.readFromFileLocalizationsToTree(clientesProdutoresCSV);
        store.readFromFileToMatrixGraph(distanciasCSV);
    }

    @org.junit.jupiter.api.Test
    void us304() {

        List<CompanyMediaPair> hubsTest = new ArrayList<>();


        Localization vertex8 = new Localization("CT14", 38.5243f, -8.8926f, new User('E', "E1"));
        Localization vertex9 = new Localization("CT11", 39.3167f, -7.4167f, new User('E', "E2"));

        Localization vertex001 = null;
        Localization vertex002 = null;

        for (int i = 0; i < mapGraph.vertices().size(); i++) {
            if (mapGraph.vertices().get(i).equals(vertex8)){
                vertex001 = mapGraph.vertices().get(i);
            } else if (mapGraph.vertices().get(i).equals(vertex9)){
                vertex002 = mapGraph.vertices().get(i);
            }
        }

        CompanyMediaPair cmp1 = new CompanyMediaPair(vertex001,386552.71428571426);
        CompanyMediaPair cmp2 = new CompanyMediaPair(vertex002,169664.85714285713);

        hubsTest.add(cmp1);
        hubsTest.add(cmp2);

        List<String> actual = US304.ClosestHub(hubsTest);
        List<String> expected = new ArrayList<>();
        List<String> expected1 = new ArrayList<>();

        expected.add("Client/Company: C1 | Closest Hub: E2 | Distance: 62655.0m");
        expected.add("Client/Company: C3 | Closest Hub: E2 | Distance: 142470.0m");
        expected.add("Client/Company: C2 | Closest Hub: E1 | Distance: 114913.0m");
        expected.add("Client/Company: C5 | Closest Hub: E1 | Distance: 197909.0m");
        expected.add("Client/Company: C4 | Closest Hub: E2 | Distance: 186068.0m");

        assertEquals(actual,expected);
        assertNotEquals(actual,expected1);

    }
}
