package US;
import Data_info.*;
import Tree.AVL;
import java.util.*;

public class US308 {
    private static final HashMap<Integer, Set<Localization>> mapDayClient = GraphStore.mapDayClient;
    private static final ArrayList<Localization> producersList = GraphStore.producersList;

    private static final ComparatorProductDay comparatorProductDay = new ComparatorProductDay();

    public static List<Expedition> expeditionListOfACertainDay(int day, List<Localization> hubs) {
        //da um set de users de um determinado dia
        Set<Localization> users = mapDayClient.get(day);
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

        //vai percorrer todos os users existentes no set dos users do dia passado por parametro
        for (Localization user : users) {

            //vai ver se o user nao ta na lista dos hubs pois se tiver nao conta como cliente
            if (!hubs.contains(user)) {

                mapProducerProducts = new HashMap<>();
                //vai buscar a order de um user
                productOrderList = user.getUser().getOrder().get(day);

                //agora vai percorrer essa order toda para encontrar produtores para os produtos
                for (ProductOrder productOrder : productOrderList) {

                    found = false;
                    biggerProducer = null;
                    biggerQuantity = 0;
                    productName = productOrder.getProductName();

                    //vai percorrer todos os producers existentes
                    for (Localization producer : producersList) {

                        //vai buscar todos os produtos que o producer tem disponiveis para vender (ja contando com os dois dias)
                        avlProductOrder = producer.getUser().getProducerStock().get(day3);

                        //verifica se ele tem pelo menos um produto
                        if (avlProductOrder != null) {

                            list = new ArrayList<>();
                            //mete na "list" os produtos que sao do tipo do produto que o cliente quer
                            comparatorProductDay.find(avlProductOrder.root, productName, list);
                            //ve a quantidade total que o producer tem do produto que estamos a procura agora
                            totalQuantity = SharedMethods.totalQuantity(list);
                            //ve a quantidade de produto que o cliente quer
                            totalQuantityOrder = productOrder.getProductQuantity();

                            //se a quantidade que o producer tem for maior que a que o cliente quer
                            if (totalQuantity >= totalQuantityOrder) {
                                //é tudo dado ao cliente e da-se ja break do loop por motivos de eficiencia
                                biggerQuantity = totalQuantityOrder;
                                biggerQuantityList = list;
                                biggerProducer = producer;
                                found = true;
                                break;

                                //este else é usado sempre para registar a maior quantidade existente
                                //de um determinado produto pois se nao se satisfizer a condiçao de cima
                                //é entregue ao cliente a maior quantidade de produto possível
                            } else if (totalQuantity > biggerQuantity) {
                                biggerQuantity = totalQuantity;
                                biggerQuantityList = list;
                                biggerProducer = producer;
                            }
                        }
                    }

                    //se foi encontrada alguma quantidade de um determinado produto
                    if (biggerProducer != null) {

                        //se esta quantidade nao for a total pedida pelo cliente
                        if (!found) {
                            //se for zero
                            if (biggerQuantity == 0) {
                                //da-mos um feedback de 0 ou seja o cliente nao recebeu absolutamente nada
                                feedback = 0;
                                //se for maior que zero mas nao for a total que o cliente queria
                            } else {
                                //com este metodo alteramos a quantidade de produto que o producer tem
                                //pois vai ser dado ao cliente
                                SharedMethods.alterProducerStock(biggerQuantityList, biggerQuantity);
                                //da-mos um feedback de 1 ou seja o cliente recebeu alguma coisa mas nao foi a total que ele queria
                                feedback = 1;
                            }
                            //se o cliente recebeu a quantidade total de produto que ele queria
                        } else {
                            //com este metodo alteramos a quantidade de produto que o producer tem
                            //pois vai ser dado ao cliente
                            SharedMethods.alterProducerStock(biggerQuantityList, biggerQuantity);
                            //da-mos um feedback de 1 ou seja o cliente recebeu o produto na totalidade
                            feedback = 2;
                        }

                        //da-mos o feedback de um produto na order do cliente
                        productOrder.setFeedBack(feedback);
                        //cria-mos um objecto do tipo product order para registar o produto que vai ser dado ao cliente
                        //e retirado ao produtor
                        productOrderDispatched = new ProductOrder(productName, biggerQuantity, productOrder.getDay());

                        //este mapa é utilizado para ver o que um producer deu de um produto a um certo cliente
                        if (mapProducerProducts.containsKey(biggerProducer)) {
                            mapProducerProducts.get(biggerProducer).add(productOrderDispatched);
                        } else {
                            Set<ProductOrder> productOrderSet = new HashSet<>();
                            productOrderSet.add(productOrderDispatched);
                            mapProducerProducts.put(biggerProducer, productOrderSet);
                        }

                    }
                }

                //por fim adiciono a lista de expediçao o clinete
                //a lista dos produtos que ele pediu com um determinado feedback (para ver se recebeu na totalidade o produto ou nao)
                //e um mapa que tem como key produtores e depois tem um set que tem o que eles deram ao cliente
                expeditionList.add(new Expedition(user, productOrderList, mapProducerProducts));

            }
        }

        return expeditionList;

    }

}

