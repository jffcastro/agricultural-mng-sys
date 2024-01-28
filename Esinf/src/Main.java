import Data_info.*;
import Read.Read;
import US.*;
import graph.Edge;
import graph.map.MapGraph;
import graph.matrix.MatrixGraph;

import java.util.*;


public class Main {
    static Read store = new Read();
    static Scanner read = new Scanner(System.in);

    public static MatrixGraph<Localization, Float> matrixGraph = GraphStore.matrixGraph;

    public static MapGraph<Localization, Float> mapGraph = GraphStore.mapGraph;

    public static void main(String[] args) {
        /*System.out.println("####################### INSERT DATA #######################\n");
        System.out.println("Write the file path to the clients and producers file: ");
        String clientesProdutoresCSV = read.nextLine();
        System.out.println("\nWrite the file path to the distances file: ");
        String distanciasCSV = read.nextLine();
        System.out.println("\nWrite the file path to the irrigation system file: ");
        String irrigationSystemCSV = read.nextLine();
        System.out.println("\nWrite the file path to the Basket file: ");
        String basketCSV = read.nextLine();


        store.readFromFileLocalizationsToTree(clientesProdutoresCSV);
        store.readFromFileToMatrixGraph(distanciasCSV);
        store.readIrrigationSystemFromFile(irrigationSystemCSV);
        store.readOrdersFromFile(basketCSV);
*/


        store.readFromFileLocalizationsToTree("Esinf/Files/grafos/Small/clientes-produtores_small.csv");
        store.readFromFileToMatrixGraph("Esinf/Files/grafos/Small/distancias_small.csv");
        store.readIrrigationSystemFromFile("Esinf/Files/grafos/IrrigationSystem.csv");
        store.readOrdersFromFile("Esinf/Files/grafos/Small/cabazes_small.csv");



         /*

        store.readFromFileLocalizationsToTree("Esinf/Files/grafos/Big/clientes-produtores_big.csv");
        store.readFromFileToMatrixGraph("Esinf/Files/grafos/Big/distancias_big.csv");
        store.readIrrigationSystemFromFile("Esinf/Files/grafos/IrrigationSystem.csv");
        store.readOrdersFromFile("Esinf/Files/grafos/Big/cabazes_big.csv");



          */


        System.out.println();
        System.out.println("\n\n=========US301=========");
        for (Localization u : store.tree.inOrder()) {
            System.out.println(u);
        }
        System.out.println(Read.matrixGraph);
        SharedMethods.showGraph(mapGraph);


        System.out.println("\n\n=========US302=========");
        ArrayList<ClientProducerDist> clientProducerDistList = US302.shortestPathsUnweighted();
        if (clientProducerDistList != null) {
            for (ClientProducerDist clientProducerDist : clientProducerDistList) {
                System.out.printf("Client: %s | Producer: %s | Dist: %d\n", clientProducerDist.getClient().getUserID(), clientProducerDist.getProducer().getUserID(), clientProducerDist.getDist());
            }
        }


        System.out.println("\n\n=========US303=========");
        System.out.println("Total Of Hubs:");
        int n = read.nextInt();
        List<CompanyMediaPair> US303 = US.US303.US303(n);
        List<Localization> hubs = new ArrayList<>();
        for (CompanyMediaPair cmp : US303) {
            System.out.println(cmp.getLocalization().getUser().getUserID() + " => " + cmp.getDistance());
            hubs.add(cmp.getLocalization());
        }

        System.out.println("\n\n=========US304=========");
        List<String> lstClosestHub = US304.ClosestHub(US303);
        if (lstClosestHub != null) {
            for (String s : lstClosestHub) {
                System.out.println(s);
            }
        } else System.out.println("\nThere are no Hubs.");

        System.out.println("\n\n=========US305=========\n");
        MapGraph<Localization, Float> kruskal = (MapGraph<Localization, Float>) US305.Us305(mapGraph);
        SharedMethods.showGraph(kruskal);

        System.out.println("\n\n=========US306=========");
        US306.rega();


        System.out.println();
        System.out.println("\n\n=========US308=========\n");
        System.out.println("Insert the day:");
        int day = read.nextInt();
        List<Expedition> expeditions1 = US308.expeditionListOfACertainDay(day, hubs);
        HashMap<Localization, Set<ProductOrder>> mapProductsDispatched1;

        for (Expedition expedition : expeditions1) {

            System.out.printf("Client %s:\n\n", expedition.getClient().getUser().getUserID());
            System.out.println("     Products Ordered:\n");

            for (ProductOrder productOrder : expedition.getListOfProductsOrder()) {

                System.out.print("      " + productOrder.getProductName() + " " + productOrder.getProductQuantity());
                System.out.println();

            }

            mapProductsDispatched1 = expedition.getListOfProductsDispatched();
            System.out.println("\n     Products Dispatched:\n");

            for (Localization productOrder1 : mapProductsDispatched1.keySet()) {

                System.out.print(" " + productOrder1.getUser().getUserID() + " - ");

                for (ProductOrder productOrder2 : mapProductsDispatched1.get(productOrder1)) {

                    System.out.print(" " + productOrder2.getProductName() + " " + productOrder2.getProductQuantity() + " ");

                }

                System.out.println();
            }

            System.out.println("\n");
        }


        System.out.println();
        System.out.println("\n\n=========US309=========\n");
        System.out.println("Insert the day:");
        day = read.nextInt();
        System.out.println("Insert the N closest Producers to the Expedition:");
        int nClosest = read.nextInt();
        List<Expedition> expeditions = US309.expeditionListOfACertainDay(nClosest, day, hubs);
        HashMap<Localization, Set<ProductOrder>> mapProductsDispatched;

        for (Expedition expedition : expeditions) {

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


        System.out.println("\n\n=========US310=========\n");
        HashMap<Localization, Set<Localization>> producerHubs = new HashMap<>();
        Set<Localization> producersOfEachExpedition;
        for (Expedition expedition : expeditions) {
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
        }

        for (Localization producer : producerHubs.keySet()) {
            System.out.println("Hubs do produtor " + producer.getUser().getUserID());
            for (Localization hub : producerHubs.get(producer)) {
                System.out.println(hub.getUser().getUserID());
            }
            System.out.println();
        }
        System.out.println();
        Distribution dist = US310.shortestPath(expeditions);
        for (Localization localization : dist.getDistanceBetweenEveryCrossingPoint().keySet()) {
            System.out.println("Produtor = " + localization.getUser().getUserID());
            for (Edge<Localization, Double> edge : dist.getDistanceBetweenEveryCrossingPoint().get(localization)) {
                System.out.println("Vertice de Origem = " + edge.getVOrig().getUser().getUserID() + " Vertice de destino = " + edge.getVDest().getUser().getUserID() + " Distancia = " + edge.getWeight());
            }
            System.out.println();
        }
        System.out.println("\nTotal distance = " + dist.getTotalDistance());

        System.out.println("\nCabazes por Hub");
        int counter;
        for (Localization hub : dist.getBasketsPerHub().keySet()) {
            System.out.println("\n -- Hub " + hub.getUser().getUserID() + "\n");
            counter = 0;
            for (Expedition expedition : dist.getBasketsPerHub().get(hub)) {

                mapProductsDispatched = expedition.getListOfProductsDispatched();
                System.out.println(" Cabaz " + counter);

                for (Localization productOrder1 : mapProductsDispatched.keySet()) {

                    System.out.print(" " + productOrder1.getUser().getUserID() + " - ");

                    for (ProductOrder productOrder2 : mapProductsDispatched.get(productOrder1)) {

                        System.out.print(" " + productOrder2.getProductName() + " " + productOrder2.getProductQuantity() + " ");

                    }

                    System.out.println();
                }

                System.out.println("\n");
                counter++;
            }
        }

        System.out.println();
        System.out.println("\n\n=========US311 (Per Basket)=========\n");

        List<BasketStatistics> basketStatisticsList = US311.getExpeditionStatisticsPerBasket(expeditions);
        for (
                BasketStatistics basketStatistics : basketStatisticsList) {
            System.out.println(basketStatistics);
            System.out.println();
        }

        System.out.println("\n\n=========US311 (Per Hub)=========\n");

        List<HubStatistics> hubStatisticsList = US311.getExpeditionStatisticsPerHub(hubs, expeditions);
        if (hubStatisticsList == null || hubStatisticsList.isEmpty()) {
            System.out.println("No Hubs Used!!!");
        } else {
            for (HubStatistics hubStatistics : hubStatisticsList) {
                System.out.println(hubStatistics);
                System.out.println();
            }
        }

        System.out.println("\n\n=========US311 (Per Producer)=========\n");

        List<ProducerStatistics> producerStatisticsList = US311.getExpeditionStatisticsPerProducer(GraphStore.producersList, expeditions);
        if (producerStatisticsList == null || producerStatisticsList.isEmpty()) {
            System.out.println("No Hubs Used!!!");
        } else {
            for (ProducerStatistics producerStatistics : producerStatisticsList) {
                System.out.println(producerStatistics);
                System.out.println();
            }
        }

        System.out.println("\n\n=========US311 (Per Client)=========\n");

        List<ClientStatistics> clientStatisticsList = US311.getExpeditionStatisticsPerClient(expeditions);
        if (clientStatisticsList == null || clientStatisticsList.isEmpty()) {
            System.out.println("No Hubs Used!!!");
        } else {
            for (ClientStatistics clientStatistics : clientStatisticsList) {
                System.out.println(clientStatistics);
                System.out.println();
            }
        }

    }

}
