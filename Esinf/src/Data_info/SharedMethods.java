package Data_info;

import Tree.AVL;
import Tree.BST;
import graph.Graph;

import java.util.ArrayList;

public class SharedMethods {

    public static int todayAndLast2Days(int day){
        if(day == 1){
            return day;
        } else if(day == 2){
            return 12;
        } else {
            String auxiliar = String.format("%d%d%d",day - 2,day-1,day);
            return Integer.parseInt(auxiliar);
        }
    }

    //metodo usado para alterar a quantiodade de stock que um determinado producer tem de um determinado produto
    public static void alterProducerStock(ArrayList<AVL.Node<ProductOrder>> list, double quantity){
        double quantitySoFar;

        for (AVL.Node<ProductOrder> productOrderNode : list){
            quantitySoFar = productOrderNode.getElement().getProductQuantity();
            if (productOrderNode.getElement().getProductQuantity() <= quantity){
                quantity -= quantitySoFar;
                productOrderNode.getElement().setProductQuantity(0);
            }else {
                productOrderNode.getElement().setProductQuantity(quantitySoFar - quantity);
            }

        }
    }

    public static void showGraph(Graph<Localization, Float> graph){

        System.out.println("Graph:\n");

        for (Localization vertice : graph.vertices()){

            System.out.printf("%15s  ->", vertice.getUser().getUserID());
            for (Localization vertice2 : graph.adjVertices(vertice)){
                System.out.printf("%9s ", vertice2.getUser().getUserID());
                System.out.printf("(%-8.2f m)", graph.edge(vertice, vertice2).getWeight());
            }
            System.out.println("\n");
        }

    }

    public static double totalQuantity(ArrayList<AVL.Node<ProductOrder>> list) {

        double total = 0;

        if (list.size() > 0) {
            for (AVL.Node<ProductOrder> productOrderNode : list) {
                total += productOrderNode.getElement().getProductQuantity();
            }
        }

        return total;

    }

}
