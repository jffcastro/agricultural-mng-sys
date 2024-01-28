package US;

import Data_info.ComparatorProductDay;
import Data_info.GraphStore;
import Data_info.Localization;
import Data_info.ProductOrder;
import Read.Read;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class US309Test {

    private static Read store = new Read();
    private String clientesProdutoresCSV, distanciasCSV, basketCSV;
    private List<Localization> hubs;
    private List<CompanyMediaPair> companieMediaPairs;

    public static ArrayList<Localization> producersList;

    @BeforeEach
    void setUp() {
        producersList = GraphStore.producersList;
        clientesProdutoresCSV = "Esinf/Test/clientes-produtores_small.csv";
        distanciasCSV = "Esinf/Test/distancias_small.csv";
        basketCSV = "Esinf/Test/cabazes_small.csv";

        store.readFromFileLocalizationsToTree(clientesProdutoresCSV);
        store.readFromFileToMatrixGraph(distanciasCSV);
        store.readOrdersFromFile(basketCSV);
        hubs = new ArrayList<>();
        companieMediaPairs = US303.US303(1);
        US304.ClosestHub(companieMediaPairs);
        for (CompanyMediaPair companyMediaPair : companieMediaPairs){
            hubs.add(companyMediaPair.getLocalization());
        }
    }

    @Test
    void expeditionListOfACertainDay() {

        List<Expedition> expeditionListExpected = US309.expeditionListOfACertainDay(1, 1, hubs);

        String userC3 = "C3";
        List<ProductOrder> productOrderList1 = new ArrayList<>();
        productOrderList1.add(new ProductOrder("Prod1", 10, 1));
        productOrderList1.add(new ProductOrder("Prod5", 9, 1));
        productOrderList1.add(new ProductOrder("Prod6", 2.5, 1));
        productOrderList1.add(new ProductOrder("Prod9", 4.5, 1));

        HashMap<Localization, Set<ProductOrder>> mapProducerProducts1 =  new HashMap<>();
        Set<ProductOrder> productOrderSet1 = new HashSet<>();
        ProductOrder po1 = new ProductOrder("Prod9",2.5 ,1);
        productOrderSet1.add(po1);
        mapProducerProducts1.put(producersList.get(0), productOrderSet1);


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
        ProductOrder po5 = new ProductOrder("Prod5",1.0 ,1);
        ProductOrder po6 = new ProductOrder("Prod10",9.0 ,1);
        ProductOrder po8 = new ProductOrder("Prod9",1.0 ,1);
        ProductOrder po9 = new ProductOrder("Prod2",5.5 ,1);
        ProductOrder po10 = new ProductOrder("Prod3",4.5 ,1);
        productOrderSet3.add(po8);
        productOrderSet3.add(po6);
        productOrderSet3.add(po9);
        productOrderSet3.add(po10);
        productOrderSet3.add(po5);
        mapProducerProducts2.put(producersList.get(0), productOrderSet3);



        String userC1 = "C1";
        List<ProductOrder> productOrderList3 = new ArrayList<>();
        productOrderList3.add(new ProductOrder("Prod5", 5, 1));
        productOrderList3.add(new ProductOrder("Prod6", 2, 1));
        productOrderList3.add(new ProductOrder("Prod11", 2.5, 1));

        HashMap<Localization, Set<ProductOrder>> mapProducerProducts3 =  new HashMap<>();
        Set<ProductOrder> productOrderSet5 = new HashSet<>();
        ProductOrder po11 = new ProductOrder("Prod5",5.0 ,1);
        ProductOrder po12 = new ProductOrder("Prod11",1.0 ,1);
        productOrderSet5.add(po11);
        productOrderSet5.add(po12);
        mapProducerProducts3.put(producersList.get(0), productOrderSet5);

        boolean found;
        boolean found1;
        HashMap<Localization, Set<ProductOrder>> mapProductsDispatched;
        for (Expedition expedition : expeditionListExpected) {

            System.out.printf("Client %s:\n\n", expedition.getClient().getUser().getUserID());
            System.out.println("     Products Ordered:\n");

            for (ProductOrder productOrder : expedition.getListOfProductsOrder()) {

                System.out.print("      " + productOrder.getProductName() + " " + productOrder.getProductQuantity());
                System.out.println();

            }

            mapProductsDispatched = expedition.getListOfProductsDispatched();
            System.out.println("\n     Products Dispatched:\n");

            for (Localization productOrder1 : mapProductsDispatched.keySet()) {

                System.out.print(" " + productOrder1.getUser().getUserID() + " - ");

                for (ProductOrder productOrder2 : mapProductsDispatched.get(productOrder1)) {

                    System.out.print(" " + productOrder2.getProductName() + " " + productOrder2.getProductQuantity() + " ");

                }

                System.out.println();
            }

            System.out.println("\n");
        }


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
                            if (productOrder1.equals(productOrder2)) {
                                found1 = true;
                                break;
                            }
                        }
                        assertTrue(found1);
                    }
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
                            if (productOrder1.equals(productOrder2)) {
                                found1 = true;
                                break;
                            }
                        }
                        assertTrue(found1);
                    }
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
                        for (ProductOrder productOrder2 : mapProducerProducts3.get(key)){
                            if (productOrder1.equals(productOrder2)) {
                                found1 = true;
                                break;
                            }
                        }
                        assertTrue(found1);
                    }
                }
            }
        }
    }
}