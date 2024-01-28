package Data_info;

import Data_info.Localization;
import Tree.AVL;
import Tree.BST;

import java.util.ArrayList;
import java.util.Comparator;

public class ComparatorProductDay implements Comparator<ProductOrder> {

    @Override
    public int compare(ProductOrder po1, ProductOrder po2) {
        if (po1.equals(po2)){
            return 0;
        }else if (!po1.getProductName().equals(po2.getProductName())){
            return po1.getProductName().compareTo(po2.getProductName());
        }else {
            return Integer.compare(po1.getDay(), po2.getDay());
        }
    }

    public void find(AVL.Node<ProductOrder> node, String description, ArrayList<AVL.Node<ProductOrder>> list) {
        if (!(node == null || list.size() >= 3)) {
            if (node.getElement().getProductName().equalsIgnoreCase(description)) {
                list.add(node);
                find(node.getLeft(), description, list);
                find(node.getRight(), description, list);
            } else if ((node.getElement().getProductName().compareTo(description)) > 0) {
                find(node.getLeft(), description, list);
            } else {
                find(node.getRight(), description, list);
            }
        }
    }


}
