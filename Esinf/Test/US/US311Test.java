package US;

import Data_info.GraphStore;
import Data_info.Localization;
import Data_info.ProductOrder;
import Data_info.User;
import Read.Read;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class US311Test {

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
        for (CompanyMediaPair companyMediaPair : companieMediaPairs){
            hubs.add(companyMediaPair.getLocalization());
        }
        US304.ClosestHub(companieMediaPairs);
    }

    @Test
    void getExpeditionStatisticsPerBasket() {

        expeditionsList = US308.expeditionListOfACertainDay(1, hubs);
        List<BasketStatistics> basketStatisticsListExpected = US311.getExpeditionStatisticsPerBasket(expeditionsList);
        List<BasketStatistics> basketStatisticsListActual = new ArrayList<>();

        basketStatisticsListActual.add(new BasketStatistics(new Localization("", 0f, 0f, new User('C', "C1")) ,
                2, 1, 0, 2 / (double) 3 * 100, 2));

        basketStatisticsListActual.add(new BasketStatistics(new Localization("", 0f, 0f, new User('C', "C2")) ,
                5, 0, 1, 5 / (double) 6 * 100, 2));

        basketStatisticsListActual.add(new BasketStatistics(new Localization("", 0f, 0f, new User('C', "C3")) ,
                0, 4, 0, 0 / (double) 4 * 100, 2));

        boolean found;

        for (BasketStatistics basketStatisticsActual : basketStatisticsListActual){

            found = false;

            for (BasketStatistics basketStatisticsExpected : basketStatisticsListExpected){
                if (basketStatisticsActual.equals(basketStatisticsExpected)) {
                    found = true;
                    break;
                }
            }

            assertTrue(found);
        }

    }

    @Test
    void getExpeditionStatisticsPerBasketPerHub() {

        expeditionsList = US308.expeditionListOfACertainDay(1, hubs);
        List<HubStatistics> hubStatisticsListExpected = US311.getExpeditionStatisticsPerHub(hubs, expeditionsList);
        HubStatistics hubStatisticsActual = new HubStatistics(new Localization("", 0f, 0f ,new User('E', "E2")), 3, 2);

        boolean found = false;

        for (HubStatistics hubStatisticsExpected : hubStatisticsListExpected) {
            if (hubStatisticsExpected.equals(hubStatisticsActual)){
                found = true;
                break;
            }
        }
        assertTrue(found);


        expeditionsList = US308.expeditionListOfACertainDay(2, hubs);
        List<HubStatistics> hubStatisticsListExpected2 = US311.getExpeditionStatisticsPerHub(hubs, expeditionsList);
        HubStatistics hubStatisticsActual2 = new HubStatistics(new Localization("", 0f, 0f ,new User('E', "E2")), 5, 2);

        found = false;

        for (HubStatistics hubStatisticsExpected : hubStatisticsListExpected2) {
            if (hubStatisticsExpected.equals(hubStatisticsActual2)){
                found = true;
                break;
            }
        }
        assertTrue(found);

    }

    @Test
    void getExpeditionStatisticsPerProducer() {

        Localization producer1 = new Localization("", 0f, 0f, new User('P', "P1"));
        Localization producer2 = new Localization("", 0f, 0f, new User('P', "P2"));

        Localization client1 = new Localization("", 0f, 0f, new User('C', "C1"));
        Localization client2 = new Localization("", 0f, 0f, new User('C', "C2"));

        ProductOrder productOrder1 = new ProductOrder("Product 1", 10, 1);
        ProductOrder productOrder2 = new ProductOrder("Product 2", 0, 1);
        ProductOrder productOrder3 = new ProductOrder("Product 3", 5, 0);
        ProductOrder productOrder4 = new ProductOrder("Product 4", 7, 1);

        Set<ProductOrder> productOrders1 = new HashSet<>();
        Set<ProductOrder> productOrders2 = new HashSet<>();

        productOrders1.add(productOrder1);
        productOrders1.add(productOrder2);
        productOrders2.add(productOrder3);
        productOrders2.add(productOrder4);

        HashMap<Localization, Set<ProductOrder>> listOfProductsDispatched = new HashMap<>();

        listOfProductsDispatched.put(producer1, productOrders1);
        listOfProductsDispatched.put(producer2, productOrders2);

        Expedition expedition1 = new Expedition(client1, productOrders1, listOfProductsDispatched);
        Expedition expedition2 = new Expedition(client2, productOrders2, listOfProductsDispatched);

        List<Expedition> expeditions = new ArrayList<>();
        expeditions.add(expedition1);
        expeditions.add(expedition2);

        List<Localization> producers = List.of(producer1, producer2);

        List<ProducerStatistics> producerStatistics = US311.getExpeditionStatisticsPerProducer(producers, expeditions);


        assertEquals(2, producerStatistics.size());

    }

    @Test
    void getExpeditionStatisticsPerClient() {

        List<Expedition> expeditions = new ArrayList<>();
        Localization client1 = new Localization("", 0f, 0f, new User('C', "C1"));
        Localization client2 = new Localization("", 0f, 0f, new User('C', "C2"));
        Localization client3 = new Localization("", 0f, 0f, new User('C', "C3"));

        Localization producer1 = new Localization("", 0f, 0f, new User('P', "P1"));
        Localization producer2 = new Localization("", 0f, 0f, new User('P', "P2"));
        Localization producer3 = new Localization("", 0f, 0f, new User('P', "P3"));

        ProductOrder product1 = new ProductOrder("Product 1", 10, 1);
        ProductOrder product2 = new ProductOrder("Product 2", 20, 1);
        ProductOrder product3 = new ProductOrder("Product 3", 0, 2);
        ProductOrder product4 = new ProductOrder("Product 4", 30, 0);
        ProductOrder product5 = new ProductOrder("Product 5", 40, 1);
        ProductOrder product6 = new ProductOrder("Product 6", 50, 2);

        HashMap<Localization, Set<ProductOrder>> listOfProductsDispatched = new HashMap<>();

        Set<ProductOrder> products1 = new HashSet<>();
        products1.add(product1);
        products1.add(product2);
        listOfProductsDispatched.put(producer1, products1);

        Set<ProductOrder> products2 = new HashSet<>();
        products2.add(product3);
        products2.add(product4);
        listOfProductsDispatched.put(producer2, products2);

        Set<ProductOrder> products3 = new HashSet<>();
        products3.add(product5);
        products3.add(product6);
        listOfProductsDispatched.put(producer3, products3);

        Expedition expedition1 = new Expedition(client1, products1, listOfProductsDispatched);
        Expedition expedition2 = new Expedition(client2, products2, listOfProductsDispatched);
        Expedition expedition3 = new Expedition(client3, products3, listOfProductsDispatched);

        expeditions.add(expedition1);
        expeditions.add(expedition2);
        expeditions.add(expedition3);

        List<ClientStatistics> clientStatisticsList = US311.getExpeditionStatisticsPerClient(expeditions);


        assertEquals(3, clientStatisticsList.size());
    }
}