package US;

import Data_info.ComparatorProductDay;
import Data_info.Localization;
import Data_info.ProductOrder;
import Tree.AVL;

import java.util.*;

public class US311 {

    public static List<BasketStatistics> getExpeditionStatisticsPerBasket(List<Expedition> expeditions){

        byte feedBack;
        int totalProductsCompleted, totalProductsNotComplete, totalProductsEmpty, totalOfProducers;
        double successPercentage;
        List<BasketStatistics> basketStatisticsList = new ArrayList<>();
        Set<ProductOrder> productOrderList;

        for (Expedition expedition : expeditions){

            totalProductsCompleted = 0;
            totalProductsNotComplete = 0;
            totalProductsEmpty = 0;
            productOrderList = expedition.getListOfProductsOrder();

            for (ProductOrder productOrder : productOrderList){

                feedBack = productOrder.getFeedBack();
                if (feedBack == 0){
                    totalProductsEmpty ++;
                }else if (feedBack == 1){
                    totalProductsNotComplete ++;
                }else if (feedBack == 2){
                    totalProductsCompleted ++;
                }

            }

            totalOfProducers = expedition.getListOfProductsDispatched().keySet().size();
            successPercentage = totalProductsCompleted / (double) (productOrderList.size()) * 100;
            basketStatisticsList.add(new BasketStatistics(expedition.getClient(), totalProductsCompleted, totalProductsNotComplete,
                    totalProductsEmpty, successPercentage, totalOfProducers));

        }

        return basketStatisticsList;

    }

    public static List<HubStatistics> getExpeditionStatisticsPerHub(List<Localization> hubs, List<Expedition> expeditions){

        if (hubs == null || hubs.isEmpty()){
            return null;
        }

        int totalClients, totalProducers;
        Localization hubKey, client;
        List<HubStatistics> statistics = new ArrayList<>();
        Map<Localization, Set<Localization>> mapHubPerUser = new HashMap<>();

        for (Localization hub : hubs) {

            mapHubPerUser.put(hub, new HashSet<>());

        }

        for (Expedition expedition : expeditions){

            client = expedition.getClient();
            hubKey = client.getUser().getHub();
            mapHubPerUser.get(hubKey).add(client);
            mapHubPerUser.get(hubKey).addAll(expedition.getListOfProductsDispatched().keySet());

        }

        for (Localization hub : mapHubPerUser.keySet()){

            totalClients = 0;
            totalProducers = 0;

            for (Localization user : mapHubPerUser.get(hub)){

                switch (user.getUser().getUserCode()) {
                    case 'P', 'p' -> totalProducers++;
                    case 'C', 'c', 'E', 'e' -> totalClients++;
                    default -> System.out.println("Wrong Code!!!");
                }

            }

            statistics.add(new HubStatistics(hub ,totalClients, totalProducers));

        }

        return statistics;

    }

    public static List<ProducerStatistics> getExpeditionStatisticsPerProducer(List<Localization> producers, List<Expedition> expeditions){

        byte feedBack;
        int totalBasketsCompleted, totalBasketsNotCompleted, totalClientsCompleted, totalProductsOutOfStock, totalHubsCompleted, totalProductsNotCompleted;
        List<ProducerStatistics> producerStatisticsList = new ArrayList<>();
        Set<ProductOrder> productOrderSet;
        List<Localization> distinctClients = new ArrayList<>();

        for (Localization producer : producers){

            totalProductsOutOfStock = 0;
            totalBasketsNotCompleted = 0;
            totalBasketsCompleted = 0;
            totalHubsCompleted = 0;
            totalProductsNotCompleted = 0;

            for (Expedition expedition : expeditions){

                productOrderSet = expedition.getListOfProductsDispatched().get(producer);

                if (!distinctClients.contains(expedition.getClient())) distinctClients.add(expedition.getClient());

                for (ProductOrder productOrder : productOrderSet) {
                    if (productOrder.getProductQuantity() == 0) totalProductsOutOfStock++;

                    feedBack = productOrder.getFeedBack();
                    if (feedBack == 1 || feedBack == 0) totalProductsNotCompleted ++;
                }

                if (totalProductsNotCompleted == 0) totalBasketsCompleted++;
                else                                totalBasketsNotCompleted++;
            }

            totalClientsCompleted = distinctClients.size();

            producerStatisticsList.add(new ProducerStatistics(producer, totalBasketsCompleted, totalBasketsNotCompleted, totalClientsCompleted, totalProductsOutOfStock, totalHubsCompleted));

        }
        return producerStatisticsList;
    }

    public static List<ClientStatistics> getExpeditionStatisticsPerClient(List<Expedition> expeditions){

        byte feedBack;
        int totalBasketsCompleted, totalBasketsNotCompleted, totalDistinctSuppliersCompleted;
        List<ClientStatistics> clientStatisticsList = new ArrayList<>();
        Set<ProductOrder> productOrderSet;
        List<Localization> distinctSuppliers = new ArrayList<>();

        for (Expedition expedition : expeditions){

            totalBasketsNotCompleted = 0;
            totalBasketsCompleted = 0;
            totalDistinctSuppliersCompleted = 0;

            productOrderSet = expedition.getListOfProductsOrder();

            for (ProductOrder productOrder : productOrderSet){

                feedBack = productOrder.getFeedBack();
                if (feedBack == 1 || feedBack == 0) totalBasketsNotCompleted ++;
                else                                totalBasketsCompleted++;

            }
            totalDistinctSuppliersCompleted = expedition.getListOfProductsDispatched().keySet().size();;

            clientStatisticsList.add(new ClientStatistics(expedition.getClient(), totalBasketsCompleted, totalBasketsNotCompleted, totalDistinctSuppliersCompleted));

        }
        return clientStatisticsList;
    }
}
