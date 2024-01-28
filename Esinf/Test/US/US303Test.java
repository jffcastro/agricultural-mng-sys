package US;

import Data_info.GraphStore;
import Data_info.Localization;
import Data_info.User;
import Read.Read;
import graph.map.MapGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class US303Test {

    static Read store = new Read();
    public static MapGraph<Localization, Float> mapGraph = GraphStore.mapGraph;

    @org.junit.jupiter.api.BeforeEach
    public void setUp(){
        String clientesProdutoresCSV1 = "Esinf/Files/grafos/Small/clientes-produtores_small.csv";
        String distanciasCSV1= "Esinf/Files/grafos/Small/distancias_small.csv";

        store.readFromFileLocalizationsToTree(clientesProdutoresCSV1);
        store.readFromFileToMatrixGraph(distanciasCSV1);
    }

    @org.junit.jupiter.api.Test
    void us303() {


        Localization localization1 = new Localization("CT14",(float)38.5243,(float)-8.8926,new User('E',"E1"));
        Localization localization2 = new Localization("CT11",(float)39.3167,(float)-7.4167,new User('E',"E2"));
        Localization localization3 = new Localization("CT5",(float)39.823,(float)-7.4931,new User('E',"E3"));
        Localization localization4 = new Localization("CT9",(float)40.5364,(float)-7.2683,new User('E',"E4"));
        Localization localization5 = new Localization("CT4",(float)41.8,(float)-6.75,new User('E',"E5"));

        CompanyMediaPair companyMediaPair1 = new CompanyMediaPair(localization1,678619.0);
        CompanyMediaPair companyMediaPair2 = new CompanyMediaPair(localization2,435493.6666666667);
        CompanyMediaPair companyMediaPair3 = new CompanyMediaPair(localization3,209188.75);
        CompanyMediaPair companyMediaPair4 = new CompanyMediaPair(localization4,897061.0833333334);
        CompanyMediaPair companyMediaPair5 = new CompanyMediaPair(localization5,1204249.0833333333);

        List<CompanyMediaPair> expected = new ArrayList<>();
        expected.add(companyMediaPair1);
        expected.add(companyMediaPair2);
        expected.add(companyMediaPair3);
        expected.add(companyMediaPair4);
        expected.add(companyMediaPair5);

        Collections.sort(expected);

        List<CompanyMediaPair> result = US303.US303(5);

        for(int x = 0 ; x < expected.size() ; x++) {
            System.out.println(expected.get(x).getLocalization().getUser().getUserID() + " " + expected.get(x).getDistance());
        }
        System.out.println();
        for(int x = 0 ; x < result.size() ; x++) {
            System.out.println(result.get(x).getLocalization().getUser().getUserID() + " " + result.get(x).getDistance());
        }

        for(int x = 0 ; x < result.size() ; x++) {
            assertEquals(expected.get(x).getLocalization().getUser().getUserID(), result.get(x).getLocalization().getUser().getUserID());
            assertEquals(expected.get(x).getDistance(), result.get(x).getDistance());

        }
    }

}
