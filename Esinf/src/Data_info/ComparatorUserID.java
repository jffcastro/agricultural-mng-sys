package Data_info;

import Tree.AVL;

import java.util.Comparator;

public class ComparatorUserID implements Comparator<Localization> {

    public int compare(Localization o1, Localization o2) {

        return o1.getUser().getUserID().compareTo(o2.getUser().getUserID());
    }

    public static AVL.Node<Localization> find(AVL.Node<Localization> node, String userID) {
        if (node == null) return null;
        else if (node.getElement().getUser().getUserID().equalsIgnoreCase(userID)) return node;
        else if ((node.getElement().getUser().getUserID().compareTo(userID))>0) return find(node.getLeft(), userID);
        else return find(node.getRight(), userID);
    }
}

