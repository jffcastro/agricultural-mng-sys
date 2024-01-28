package US;

import Data_info.GraphStore;
import Data_info.Localization;
import Data_info.User;
import Read.Read;
import graph.map.MapGraph;
import org.junit.Before;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class US302Test {

    static Read store = new Read();
    public static MapGraph<Localization, Float> mapGraph = GraphStore.mapGraph;


    @org.junit.jupiter.api.BeforeEach
    @Before
    public void setUp(){
        String clientesProdutoresCSV = "Esinf/Test/clientes-produtores_small.csv";
        String distanciasCSV = "Esinf/Test/distancias_small.csv";

        store.readFromFileLocalizationsToTree(clientesProdutoresCSV);
        store.readFromFileToMatrixGraph(distanciasCSV);
    }

    @org.junit.jupiter.api.Test
    void US302(){

        ArrayList<ClientProducerDist> expected = new ArrayList<>();
        ArrayList<ClientProducerDist> actual = US302.shortestPathsUnweighted();

        Localization vertex1 = new Localization("CT1", 40.6389f, -8.6553f, new User('C', "C1"));
        Localization vertex2 = new Localization("CT2", 38.0333f, -7.8833f, new User('C', "C2"));
        Localization vertex3 = new Localization("CT3", 41.5333f, -8.4167f, new User('C', "C3"));
        Localization vertex4 = new Localization("CT15", 41.7f, -8.8333f, new User('C', "C4"));
        Localization vertex5 = new Localization("CT16", 41.3002f, -7.7398f, new User('C', "C5"));
        Localization vertex6 = new Localization("CT17", 40.6667f, -7.9167f, new User('P', "P1"));
        Localization vertex7 = new Localization("CT6", 40.2111f, -8.4291f, new User('P', "P2"));
        expected.add(new ClientProducerDist(vertex1.getUser(), vertex6.getUser(), 1));
        expected.add(new ClientProducerDist(vertex3.getUser(), vertex6.getUser(), 1));
        expected.add(new ClientProducerDist(vertex2.getUser(), vertex6.getUser(), 1));
        expected.add(new ClientProducerDist(vertex2.getUser(), vertex7.getUser(), 1));
        expected.add(new ClientProducerDist(vertex5.getUser(), vertex6.getUser(), 1));
        expected.add(new ClientProducerDist(vertex5.getUser(), vertex7.getUser(), 2));
        expected.add(new ClientProducerDist(vertex4.getUser(), vertex6.getUser(), 2));
        expected.add(new ClientProducerDist(vertex1.getUser(), vertex7.getUser(), 3));
        expected.add(new ClientProducerDist(vertex3.getUser(), vertex7.getUser(), 3));
        expected.add(new ClientProducerDist(vertex4.getUser(), vertex7.getUser(), 4));

        boolean equa = false;
        for (ClientProducerDist clientProducerDist : expected){
            for (ClientProducerDist clientProducerDist1 : actual){
                if (clientProducerDist1.equals(clientProducerDist)){
                    equa = true;
                    break;
                }
            }
            assertTrue(equa);
        }

    }

}
