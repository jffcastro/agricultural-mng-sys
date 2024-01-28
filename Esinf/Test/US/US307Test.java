package US;

import Data_info.ComparatorProductDay;

import Data_info.GraphStore;
import Data_info.Localization;
import Data_info.ProductOrder;
import Read.Read;
import Tree.AVL;
import graph.map.MapGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

class US307Test {

    static Read store = new Read();
    private String clientesProdutoresCSV, distanciasCSV, basketCSV;
    private List<Localization> hubs;
    private List<CompanyMediaPair> companyMediaPairs;

    @BeforeEach
    void setUp() {
        clientesProdutoresCSV = "Esinf/Test/clientes-produtores_small.csv";
        distanciasCSV = "Esinf/Test/distancias_small.csv";
        basketCSV = "Esinf/Test/cabazes_small.csv";
        String csvFile = "Esinf/Test/cabazes_small.csv";

        store.readFromFileLocalizationsToTree(clientesProdutoresCSV);
        store.readFromFileToMatrixGraph(distanciasCSV);
        store.readOrdersFromFile(basketCSV);
        hubs = new ArrayList<>();
        companyMediaPairs = US303.US303(1);
        US304.ClosestHub(companyMediaPairs);
        for (CompanyMediaPair companieMediaPair : companyMediaPairs){
            hubs.add(companieMediaPair.getLocalization());
        }
        store.readOrdersFromFile(csvFile);
    }

    @Test
    void readOrdersFromFile() {

            ArrayList<ProductOrder> poAL = new ArrayList<>();
            ArrayList<ProductOrder> poAL1 = new ArrayList<>();

            assertEquals(9, Read.treeUserID.size());

            Localization user = GraphStore.clientsList.get(0);
            Localization company = GraphStore.companiesList.get(1);

            ProductOrder po1 = new ProductOrder("Prod5",5.0,1);
            ProductOrder po2 = new ProductOrder("Prod6",2.0,1);
            ProductOrder po3 = new ProductOrder("Prod11",2.5,1);

            ProductOrder po4 = new ProductOrder("Prod4",4.5,2);
            ProductOrder po5 = new ProductOrder("Prod8",3,2);
            ProductOrder po6 = new ProductOrder("Prod10",9.5,2);
            ProductOrder po7 = new ProductOrder("Prod11",2.5,2);
            ProductOrder po8 = new ProductOrder("Prod12",3,2);


            poAL.add(po1);
            poAL.add(po2);
            poAL.add(po3);

            poAL1.add(po4);
            poAL1.add(po5);
            poAL1.add(po6);
            poAL1.add(po7);
            poAL1.add(po8);

            int counter = 0;
            int counterj = 0;

            for (int i = 0; i < poAL.size(); i++) {
                for (ProductOrder po: user.getUser().getOrder().get(1)) {
                    if (po.equals(poAL.get(i)) && po.getProductQuantity()==(poAL.get(i).getProductQuantity())){
                       counter++;
                    }
                }
            }

            assertEquals(poAL.size(),counter);



            for (int i = 0; i < poAL1.size(); i++) {
                for (ProductOrder poa: company.getUser().getOrder().get(2)) {
                    if (poa.equals(poAL1.get(i)) && poa.getProductQuantity()==(poAL1.get(i).getProductQuantity())){
                        counterj++;
                 }
                }
            }

            assertEquals(poAL1.size(),counterj);
        }
}