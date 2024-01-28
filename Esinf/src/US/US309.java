package US;

import Data_info.*;
import Tree.AVL;
import graph.Algorithms;
import graph.map.MapGraph;

import java.util.*;

public class US309 {

    /**
     * The Map graph.
     */
    static MapGraph<Localization, Float> mapGraph = GraphStore.mapGraph;

    private static final HashMap<Integer, Set<Localization>> mapDayClient = GraphStore.mapDayClient;
    private static final ComparatorProductDay comparatorProductDay = new ComparatorProductDay();

    public static List<Expedition> expeditionListOfACertainDay(int nClosest, int day, List<Localization> hubs) {

        Set<Localization> users = mapDayClient.get(day);
        Map<Localization, ArrayList<Localization>> closestProducersPerClient = nClosestProducers(nClosest, users);
        ArrayList<AVL.Node<ProductOrder>> list, biggerQuantityList = new ArrayList<>();
        Localization biggerProducer;
        ProductOrder productOrderDispatched;
        double totalQuantity, totalQuantityOrder, biggerQuantity;
        byte feedback;
        boolean found;
        int day3;
        String productName;
        HashMap<Localization, Set<ProductOrder>> mapProducerProducts;
        List<Expedition> expeditionList = new ArrayList<>();
        Set<ProductOrder> productOrderList;
        AVL<ProductOrder, ComparatorProductDay> avlProductOrder;

        day3 = SharedMethods.todayAndLast2Days(day);

        for (Localization user : users) {

            if (!hubs.contains(user)) {

                mapProducerProducts = new HashMap<>();
                productOrderList = user.getUser().getOrder().get(day);

                for (ProductOrder productOrder : productOrderList) {

                    found = false;
                    biggerProducer = null;
                    biggerQuantity = 0;
                    productName = productOrder.getProductName();

                    for (Localization producer : closestProducersPerClient.get(user)) {

                        avlProductOrder = producer.getUser().getProducerStock().get(day3);

                        if (avlProductOrder != null) {

                            list = new ArrayList<>();
                            comparatorProductDay.find(avlProductOrder.root, productName, list);
                            totalQuantity = SharedMethods.totalQuantity(list);
                            totalQuantityOrder = productOrder.getProductQuantity();

                            if (totalQuantity >= totalQuantityOrder) {
                                biggerQuantity = totalQuantityOrder;
                                biggerQuantityList = list;
                                biggerProducer = producer;
                                found = true;
                                break;

                            } else if (totalQuantity > biggerQuantity) {
                                biggerQuantity = totalQuantity;
                                biggerQuantityList = list;
                                biggerProducer = producer;
                            }
                        }
                    }

                    if (biggerProducer != null) {

                        if (!found) {
                            if (biggerQuantity == 0) {
                                feedback = 0;
                            } else {
                                SharedMethods.alterProducerStock(biggerQuantityList, biggerQuantity);
                                feedback = 1;
                            }
                        } else {
                            SharedMethods.alterProducerStock(biggerQuantityList, biggerQuantity);
                            feedback = 2;
                        }

                        productOrder.setFeedBack(feedback);
                        productOrderDispatched = new ProductOrder(productName, biggerQuantity, productOrder.getDay());

                        if (mapProducerProducts.containsKey(biggerProducer)) {
                            mapProducerProducts.get(biggerProducer).add(productOrderDispatched);
                        } else {
                            Set<ProductOrder> productOrderSet = new HashSet<>();
                            productOrderSet.add(productOrderDispatched);
                            mapProducerProducts.put(biggerProducer, productOrderSet);
                        }

                    }
                }

                expeditionList.add(new Expedition(user, productOrderList, mapProducerProducts));

            }
        }

        return expeditionList;

    }

    private static Map<Localization, ArrayList<Localization>> nClosestProducers(int n, Set<Localization> clients) {
        ArrayList<LinkedList<Localization>> paths;
        ArrayList<Double> dists;
        ArrayList<CompanyMediaPair> cmpList;
        ArrayList<Localization> cmpSet;

        int arraySize;
        int auxN;

        Map<Localization, ArrayList<Localization>> mapClosestProducers = new HashMap<>();

        for(Localization client : clients) {
            paths = new ArrayList<>();
            dists = new ArrayList<>();
            cmpList = new ArrayList<>();
            cmpSet = new ArrayList<>();
            Algorithms.shortestPathsWeighted(mapGraph, client.getUser().getHub(), paths, dists);
            arraySize = paths.size();
            for(int x = 0 ; x < arraySize ; x++){
                Localization producer = paths.get(x).getLast();
                if(producer.getUser().getUserCode() == 'P') {
                    cmpList.add(new CompanyMediaPair(producer,dists.get(x)));
                }
            }
            Collections.sort(cmpList);
            auxN = Math.min(n, cmpList.size());
            for(int x = 0 ; x < auxN ; x++) {
                cmpSet.add(cmpList.get(x).getLocalization());
            }
            mapClosestProducers.put(client,cmpSet);
        }
        return mapClosestProducers;
    }
}



