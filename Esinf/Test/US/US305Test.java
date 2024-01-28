package US;

import Data_info.GraphStore;
import Data_info.Localization;
import Data_info.User;
import Read.Read;
import graph.Edge;
import graph.map.MapGraph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class US305Test {

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
    void us305() {

        MapGraph<Localization, Float> espected = new MapGraph<>(false);
        MapGraph<Localization, Float> actual = (MapGraph<Localization, Float>) US305.Us305(mapGraph);

        Localization vertex1 = new Localization("CT1", 40.6389f, -8.6553f, new User('C', "C1"));
        Localization vertex2 = new Localization("CT2", 38.0333f, -7.8833f, new User('C', "C2"));
        Localization vertex3 = new Localization("CT3", 41.5333f, -8.4167f, new User('C', "C3"));
        Localization vertex4 = new Localization("CT15", 41.7f, -8.8333f, new User('C', "C4"));
        Localization vertex5 = new Localization("CT16", 41.3002f, -7.7398f, new User('C', "C5"));
        Localization vertex6 = new Localization("CT17", 40.6667f, -7.9167f, new User('P', "P1"));
        Localization vertex7 = new Localization("CT6", 40.2111f, -8.4291f, new User('P', "P2"));
        espected.addVertex(vertex1);
        espected.addVertex(vertex2);
        espected.addVertex(vertex3);
        espected.addVertex(vertex4);
        espected.addVertex(vertex5);
        espected.addVertex(vertex6);
        espected.addVertex(vertex7);

        espected.addEdge(vertex5, vertex3, 68957f);// //
        espected.addEdge(vertex5, vertex6, 79560f); // //
        espected.addEdge(vertex4, vertex3, 43598f); // //
        espected.addEdge(vertex6, vertex1, 69282f); // //
        espected.addEdge(vertex2, vertex6, 65574f); // //
        espected.addEdge(vertex2, vertex7, 125105f); // //
        espected.addEdge(vertex3, vertex5, 68957f);// //
        espected.addEdge(vertex6, vertex5, 79560f); // //
        espected.addEdge(vertex3, vertex4, 43598f); // //
        espected.addEdge(vertex1, vertex6, 69282f); // //
        espected.addEdge(vertex6, vertex2, 65574f); // //
        espected.addEdge(vertex7, vertex2, 125105f); // //


        int j;
        for (int i = 0; i < actual.vertices().size(); i++) {
            for (j = 0; j < espected.vertices().size(); j++) {
                if (actual.vertex(i).equals(espected.vertex(j))){
                    break;
                }
            }
            assertEquals(actual.vertex(i), espected.vertex(j));
        }

        boolean equa = false;
        for (Edge<Localization, Float> exp : espected.edges()) {
            for (Edge<Localization, Float> act : actual.edges()) {
                if (exp.equals(act)){
                    equa = true;
                    break;
                }
            }
            assertTrue(equa);
        }
    }
}
