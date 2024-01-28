package US;

import Data_info.GraphStore;
import Data_info.Localization;
import Data_info.ProductOrder;
import Data_info.User;
import Read.Read;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class US308Test {

    static Read store = new Read();
    private String clientesProdutoresCSV, distanciasCSV, basketCSV;
    private List<Localization> hubs;
    private List<CompanyMediaPair> companyMediaPairs;
    public static ArrayList<Localization> producersList = GraphStore.producersList;

    @BeforeEach
    void setUp() {
        clientesProdutoresCSV = "Esinf/Test/clientes-produtores_small.csv";
        distanciasCSV = "Esinf/Test/distancias_small.csv";
        basketCSV = "Esinf/Test/cabazes_small.csv";

        store.readFromFileLocalizationsToTree(clientesProdutoresCSV);
        store.readFromFileToMatrixGraph(distanciasCSV);
        store.readOrdersFromFile(basketCSV);
        hubs = new ArrayList<>();
        companyMediaPairs = US303.US303(1);
        US304.ClosestHub(companyMediaPairs);
        for (CompanyMediaPair companieMediaPair : companyMediaPairs){
            hubs.add(companieMediaPair.getLocalization());
        }
    }

    @Test
    void US308() {
        List<Expedition> expeditionListExpected = US308.expeditionListOfACertainDay(1, hubs);

        String userC3 = "C3";
        List<ProductOrder> productOrderList1 = new ArrayList<>();
        productOrderList1.add(new ProductOrder("Prod1", 10, 1));
        productOrderList1.add(new ProductOrder("Prod5", 9, 1));
        productOrderList1.add(new ProductOrder("Prod6", 2.5, 1));
        productOrderList1.add(new ProductOrder("Prod9", 4.5, 1));

        HashMap<Localization, Set<ProductOrder>> mapProducerProducts1 =  new HashMap<>();
        Set<ProductOrder> productOrderSet1 = new HashSet<>();
        Set<ProductOrder> productOrderSet2 = new HashSet<>();
        ProductOrder po1 = new ProductOrder("Prod1",2.5 ,1);
        ProductOrder po2 = new ProductOrder("Prod5",6.0 ,1);
        ProductOrder po3 = new ProductOrder("Prod6",2.5 ,1);
        ProductOrder po4 = new ProductOrder("Prod9",3.5 ,1);
        productOrderSet2.add(po1);
        productOrderSet1.add(po2);
        productOrderSet2.add(po3);
        productOrderSet1.add(po4);
        mapProducerProducts1.put(producersList.get(0), productOrderSet1);
        mapProducerProducts1.put(producersList.get(1), productOrderSet2);



        String userC2 = "C2";
        List<ProductOrder> productOrderList2 = new ArrayList<>();
        productOrderList2.add(new ProductOrder("Prod2", 5.5, 1));
        productOrderList2.add(new ProductOrder("Prod3", 4.5, 1));
        productOrderList2.add(new ProductOrder("Prod5", 4, 1));
        productOrderList2.add(new ProductOrder("Prod9", 1, 1));
        productOrderList2.add(new ProductOrder("Prod10", 9, 1));
        productOrderList2.add(new ProductOrder("Prod11", 10, 1));

        HashMap<Localization, Set<ProductOrder>> mapProducerProducts2 =  new HashMap<>();
        Set<ProductOrder> productOrderSet3 = new HashSet<>();
        Set<ProductOrder> productOrderSet4 = new HashSet<>();
        ProductOrder po5 = new ProductOrder("Prod5",0.0 ,1);
        ProductOrder po6 = new ProductOrder("Prod10",9.0 ,1);
        ProductOrder po7 = new ProductOrder("Prod11",0.0 ,1);
        ProductOrder po8 = new ProductOrder("Prod9",1.0 ,1);
        ProductOrder po9 = new ProductOrder("Prod2",5.5 ,1);
        ProductOrder po10 = new ProductOrder("Prod3",4.5 ,1);
        productOrderSet3.add(po5);
        productOrderSet3.add(po6);
        productOrderSet3.add(po7);
        productOrderSet3.add(po9);
        productOrderSet3.add(po10);
        productOrderSet4.add(po8);
        mapProducerProducts2.put(producersList.get(0), productOrderSet3);
        mapProducerProducts2.put(producersList.get(1), productOrderSet4);



        String userC1 = "C1";
        List<ProductOrder> productOrderList3 = new ArrayList<>();
        productOrderList3.add(new ProductOrder("Prod5", 5.0, 1));
        productOrderList3.add(new ProductOrder("Prod6", 2.0, 1));
        productOrderList3.add(new ProductOrder("Prod11", 2.5, 1));
        HashMap<Localization, Set<ProductOrder>> mapProducerProducts3 =  new HashMap<>();
        Set<ProductOrder> productOrderSet5 = new HashSet<>();
        ProductOrder po11 = new ProductOrder("Prod5",0.0 ,1);
        ProductOrder po12 = new ProductOrder("Prod11",0.0 ,1);
        ProductOrder po13 = new ProductOrder("Prod6",0.0 ,1);
        productOrderSet5.add(po11);
        productOrderSet5.add(po12);
        productOrderSet5.add(po13);
        mapProducerProducts3.put(producersList.get(1), productOrderSet5);


        boolean found;
        boolean found1 = true;


        for (Expedition expedition : expeditionListExpected){

            if (expedition.getClient().getUser().getUserID().equals(userC3)) {

                for (ProductOrder productOrder : productOrderList1) {
                    found = false;
                    for (ProductOrder productOrder1 : expedition.getListOfProductsOrder()){
                        if (productOrder.equals(productOrder1) && productOrder.getProductQuantity() == productOrder1.getProductQuantity()) {
                            found = true;
                            break;
                        }
                    }
                    assertTrue(found);
                }


                for (Localization key : expedition.getListOfProductsDispatched().keySet()) {
                    Set<ProductOrder> value = expedition.getListOfProductsDispatched().get(key);

                    for (ProductOrder productOrder1 : value){
                        found1 = false;
                        for (ProductOrder productOrder2 : mapProducerProducts1.get(key)){
                            if (productOrder1.equals(productOrder2)){
                                found1 = true;
                            }
                        }
                    }
                    assertTrue(found1);
                }

            }



            if (expedition.getClient().getUser().getUserID().equals(userC2)) {

                for (ProductOrder productOrder : productOrderList2) {

                    found = false;
                    for (ProductOrder productOrder1 : expedition.getListOfProductsOrder()){
                        if (productOrder.equals(productOrder1) && productOrder.getProductQuantity() == productOrder1.getProductQuantity()) {
                            found = true;
                            break;
                        }
                    }
                    assertTrue(found);
                }



                for (Localization key : expedition.getListOfProductsDispatched().keySet()) {
                    Set<ProductOrder> value = expedition.getListOfProductsDispatched().get(key);

                    for (ProductOrder productOrder1 : value){
                        found1 = false;
                        for (ProductOrder productOrder2 : mapProducerProducts2.get(key)){
                            if (productOrder1.equals(productOrder2)){
                                found1 = true;
                            }
                        }
                    }
                    assertTrue(found1);
                }



            }

            if (expedition.getClient().getUser().getUserID().equals(userC1)) {

                for (ProductOrder productOrder : productOrderList3) {
                    found = false;
                    for (ProductOrder productOrder1 : expedition.getListOfProductsOrder()){
                        if (productOrder.equals(productOrder1) && productOrder.getProductQuantity() == productOrder1.getProductQuantity()) {
                            found = true;
                            break;
                        }
                    }
                    assertTrue(found);
                }



                for (Localization key : expedition.getListOfProductsDispatched().keySet()) {
                    Set<ProductOrder> value = expedition.getListOfProductsDispatched().get(key);

                    for (ProductOrder productOrder1 : value){
                        found1 = false;
                        //producersList.get(1) corresponde ao produtor 2
                        for (ProductOrder productOrder2 : mapProducerProducts3.get(producersList.get(1))){
                            if (productOrder1.equals(productOrder2)){
                                found1 = true;
                            }
                        }
                    }
                    assertTrue(found1);
                }
            }

        }
    }
}
