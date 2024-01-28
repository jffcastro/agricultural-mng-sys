package Tree;

import Data_info.Localization;

import java.util.Comparator;

/**
 * The type Comparator localization.
 */
public class ComparatorLocalization implements Comparator<Localization> {


    public int compare(Localization o1, Localization o2) {
        return o1.getLocId().compareTo(o2.getLocId());
    }


    /**
     * Find bst . node.
     *
     * @param node        the node
     * @param description the description
     * @return the bst . node
     */
    public static BST.Node<Localization> find(BST.Node<Localization> node, String description) {
        if (node == null) return null;
        else if (node.getElement().getLocId().equalsIgnoreCase(description)) return node;
        else if ((node.getElement().getLocId().compareTo(description))>0) return find(node.getLeft(), description);
        else return find(node.getRight(), description);
    }
}


