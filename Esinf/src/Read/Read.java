package Read;

import Data_info.*;
import Tree.AVL;
import Tree.ComparatorLocalization;
import graph.matrix.MatrixGraph;

import graph.map.MapGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Read.
 */
public class Read {

    /**
     * The Matrix graph.
     */
    public static MatrixGraph<Localization, Float> matrixGraph = GraphStore.matrixGraph;

    /**
     * The Map graph.
     */
    public static MapGraph<Localization, Float> mapGraph = GraphStore.mapGraph;

    /**
     * The Clients list.
     */
    public static ArrayList<Localization> clientsList = GraphStore.clientsList;
    /**
     * The Producers list.
     */
    public static ArrayList<Localization> producersList = GraphStore.producersList;
    /**
     * The Companies list.
     */
    public static ArrayList<Localization> companiesList = GraphStore.companiesList;

    /**
     * The Tree.
     */
    public  AVL<Localization, ComparatorLocalization> tree = new AVL<>(new ComparatorLocalization());

    public static AVL<Localization, ComparatorUserID> treeUserID = GraphStore.treeUserID;

    public static HashMap<Integer, Set<Localization>> mapDayClient = GraphStore.mapDayClient;

    //Ler coordenadas, distancias, e por fim os cabazes

    //se quiser criar um mapa para guardar os cabazes
    //TreeSet<Production> productionOfTheCountry = new TreeSet<>(new ComparatorKeyCountryQuantity());
    //no set onde estao os cabazes de um produtor tenho de meter um comparator para ficar ordenado;
    //se calhar usar um mapa em que o dia é a key e o resto fica num set

    /**
     * Read from file localizations to tree.
     *
     * @param csvFile the csv file
     */
    public void readFromFileLocalizationsToTree(String csvFile) {

        try {
            FileReader file = new FileReader(csvFile);
            BufferedReader readFromFile = new BufferedReader(file);
            String line;
            String[] lineDivided;
            readFromFile.readLine();

            while ((line = readFromFile.readLine()) != null) {
                lineDivided = line.split(",");
                Localization localization = new Localization(lineDivided[0], Float.parseFloat(lineDivided[1]), Float.parseFloat(lineDivided[2]), new User(lineDivided[3].charAt(0), lineDivided[3]));
                tree.insert(localization);
                treeUserID.insert(localization);
            }
        } catch (IOException e) {
            System.out.println("You typed the wrong file path!");
        }
    }

    /**
     * Read from file to matrix graph.
     *
     * @param csvFile the csv file
     */
    public void readFromFileToMatrixGraph(String csvFile) {

        try {
            FileReader file = new FileReader(csvFile);
            BufferedReader readFromFile = new BufferedReader(file);
            String line;
            String[] lineDivided;
            readFromFile.readLine();

            while ((line = readFromFile.readLine()) != null) {
                line = line.replaceAll("\"\"", "");
                lineDivided = line.split(",");
                Localization localization1 = ComparatorLocalization.find(tree.root, lineDivided[0]).getElement();
                Localization localization2 = ComparatorLocalization.find(tree.root, lineDivided[1]).getElement();
                float weight = Float.parseFloat(lineDivided[2]);

                if (!matrixGraph.validVertex(localization1)) {
                    char code1 = localization1.getUser().getUserCode();
                    switch (code1) {
                        case 'C', 'c' -> clientsList.add(localization1);
                        case 'E', 'e' -> companiesList.add(localization1);
                        case 'P', 'p' -> producersList.add(localization1);
                        default -> System.out.println("Wrong Code");
                    }
                }

                if (!matrixGraph.validVertex(localization2)) {
                    char code2 = localization2.getUser().getUserCode();
                    switch (code2) {
                        case 'C', 'c' -> clientsList.add(localization2);
                        case 'E', 'e' -> companiesList.add(localization2);
                        case 'P', 'p' -> producersList.add(localization2);
                        default -> System.out.println("Wrong Code");
                    }
                }

                matrixGraph.addEdge(localization1, localization2, weight);
                mapGraph.addEdge(localization1, localization2, weight);
            }
        } catch (IOException e) {
            System.out.println("You typed the wrong file path!");
        }
    }

    /**
     * Read user and localization info from file.
     *
     * @param csvFile the csv file
     */
    public void readUserAndLocalizationInfoFromFile(String csvFile) {

        try {
            FileReader file = new FileReader(csvFile);
            BufferedReader readFromFile = new BufferedReader(file);
            String line;
            String[] lineDivided;
            readFromFile.readLine();

            while ((line = readFromFile.readLine()) != null) {
                line = line.replaceAll("\"\"", "");
                lineDivided = line.split(",");
                Localization localization1 = new Localization(lineDivided[0]);

            }
        } catch (IOException e) {
            System.out.println("You typed the wrong file path!");
        }
    }

    /**
     * Read irrigation system from file.
     *
     * @param csvFile the csv file
     */
    public void readIrrigationSystemFromFile(String csvFile) {
        Time[] horas;
        String[] auxiliar;
        try {
            FileReader file = new FileReader(csvFile);
            BufferedReader readFromFile = new BufferedReader(file);

            String line = readFromFile.readLine();
            auxiliar = line.split(",");
            horas = new Time[auxiliar.length];
            GraphStore.irrigationSystem = new IrrigationSystem(horas);

            for (int x = 0; x < auxiliar.length; x++) {
                horas[x] = new Time(Integer.parseInt(auxiliar[x].split(":")[0]), Integer.parseInt(auxiliar[x].split(":")[1]), 0);
            }

            while ((line = readFromFile.readLine()) != null) {
                auxiliar = line.split(",");
                GraphStore.irrigationSystem.getIrrigationObjectList().add(new IrrigationObject(auxiliar[0], Integer.parseInt(auxiliar[1]), auxiliar[2]));
            }

        } catch (
                IOException e) {
            System.out.println("You typed the wrong file path!");
        }
    }

    public void readOrdersFromFile(String csvFile) {
        try {

            FileReader file = new FileReader(csvFile);
            BufferedReader readFromFile = new BufferedReader(file);
            String header, line, userId;
            String[] headerDivided, lineDivided;
            int headerSize, counter = 0, day, threeDay1, threeDay2, threeDay3;
            double quantity;
            boolean zero = true;

            header = readFromFile.readLine();
            header = header.replaceAll("\"", "");
            headerDivided = header.split(",");
            headerSize = headerDivided.length;
            List<String> productsName = new ArrayList<>(Arrays.asList(headerDivided).subList(2, headerSize));

            while ((line = readFromFile.readLine()) != null) {

                line = line.replaceAll("\"", "");
                lineDivided = line.split(",");

                userId = lineDivided[0];
                day = Integer.parseInt(lineDivided[1]);

                AVL.Node<Localization> userNode = ComparatorUserID.find(treeUserID.root, userId);

                if (userNode != null) {
                    Localization user = userNode.getElement();
                    switch (user.getUser().getUserCode()) {
                        case 'C', 'c', 'E', 'e' -> {

                            HashMap<Integer, Set<ProductOrder>> order = user.getUser().getOrder();
                            for (int i = 0; i < headerSize - 2; i ++) {
                                quantity = Double.parseDouble(lineDivided[i + 2]);
                                if (quantity > 0) {
                                    ProductOrder productOrder = new ProductOrder(productsName.get(i), quantity, day);
                                    if (order.containsKey(day)) {
                                        order.get(day).add(productOrder);
                                    } else {
                                        Set<ProductOrder> productOrderSet = new HashSet<>();
                                        productOrderSet.add(productOrder);
                                        order.put(day, productOrderSet);
                                    }
                                    zero = false;
                                }
                            }
                            if (!zero) {
                                if (mapDayClient.containsKey(day)) {
                                    mapDayClient.get(day).add(user);
                                } else {
                                    Set<Localization> userSet = new HashSet<>();
                                    userSet.add(user);
                                    mapDayClient.put(day, userSet);
                                }
                            }
                            zero = true;

                        }
                        case 'P', 'p' -> {

                            HashMap<Integer, AVL<ProductOrder, ComparatorProductDay>> stock = user.getUser().getProducerStock();
                            for (int i = 0; i < headerSize - 2; i ++) {
                                quantity = Double.parseDouble(lineDivided[i + 2]);
                                threeDay1 = todayAndLast2Days(day);
                                threeDay2 = todayAndLast2Days(day + 1);
                                threeDay3 = todayAndLast2Days(day + 2);
                                if (quantity > 0) {
                                    ProductOrder productOrder = new ProductOrder(productsName.get(i), quantity, day);
                                    insertIntoStock(stock, threeDay1, productOrder);
                                    insertIntoStock(stock, threeDay2, productOrder);
                                    insertIntoStock(stock, threeDay3, productOrder);
                                }
                            }

                        }
                        default -> System.out.println("Wrong Code");
                    }

                } else {
                    counter++;
                }
            }

            if (counter == 0) {
                System.out.println("\nThe csv file that contains information about the baskets was imported successfully.");
            } else {
                System.out.println("\n" + counter + " lines were ignored because the user was not found in the system.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    private int todayAndLast2Days(int day){
        if(day == 1){
            return day;
        } else if(day == 2){
            return 12;
        } else {
            String auxiliar = String.format("%d%d%d",day - 2,day-1,day);
            return Integer.parseInt(auxiliar);
        }
    }

    private void insertIntoStock(HashMap<Integer, AVL<ProductOrder, ComparatorProductDay>> stock, int threeDay, ProductOrder productOrder){
        if (stock.containsKey(threeDay)) {
            stock.get(threeDay).insert(productOrder);
        } else {
            AVL<ProductOrder, ComparatorProductDay> products = new AVL<>(new ComparatorProductDay());
            products.insert(productOrder);
            stock.put(threeDay, products);
        }
    }

}
