package Tree;
    /*class Node {

        Area key;

        Item link;
        int height;
        Node left, right;

        Node(Area d, Item x) {
            key = d;
            link = x;
            height = 1;
        }
    }

     */

import java.util.Comparator;

    /**
     * Class to create an AVL Tree
     *
     * @param <E> the type parameter
     * @param <T> the type parameter
     */
    public class AVL<E, T extends Comparator<E> > extends BST<E, T> {

        /**
         * Instantiates a new Avl.
         *
         * @param t the t
         */
        public AVL(T t) {
        super(t);
    }

        /**
         * Checks the difference in height between the right and left subtree
         *
         * @param node
         * @return the height difference
         */
    private int balanceFactor(Node<E> node) {

        return height(node.getRight()) - height(node.getLeft());
    }

        /**
         * Rotates the tree to the right
         *
         * @param node
         * @return the top node
         */
    private Node<E> rightRotation(Node<E> node) {

        Node<E> leftSon = node.getLeft();
        node.setLeft(leftSon.getRight());
        leftSon.setRight(node);

        node = leftSon;
        return node;


    }

        /**
         * Rotates the tree to the left
         *
         * @param node
         * @return the top node
         */
    private Node<E> leftRotation(Node<E> node) {

        Node<E> rightSon = node.getRight();
        node.setRight(rightSon.getLeft());
        rightSon.setLeft(node);

        node = rightSon;
        return node;
    }

        /**
         * Rotates the tree in different directions based on the balance factor
         *
         * @param node
         * @return the top node
         */
    private Node<E> twoRotations(Node<E> node) {

        if (balanceFactor(node) < 0){
            node.setLeft(leftRotation(node.getLeft()));
            node = rightRotation(node);
        }else {
            node.setRight(rightRotation(node.getRight()));
            node = leftRotation(node);
        }
        return node;
    }

        /**
         * Calls the right rotation depending on the balance factor of a node.
         *
         * @param node, a node of the tree
         * @return the top node
         */
    private Node<E> balanceNode(Node<E> node) {

        if (balanceFactor(node) > 1){
            if (balanceFactor(node.getRight()) > 0){
                return leftRotation(node);
            }else {
                return twoRotations(node);
            }
        }else if (balanceFactor(node) < -1){
            if (balanceFactor(node.getLeft()) < 0){
                return rightRotation(node);
            }else {
                return twoRotations(node);
            }
        }else {
            return node;
        }
    }

        /**
         * Inserts an element into a tree, comparing it to the root
         *
         * @param element, the element to insert
         */
    @Override
    public void insert(E element) {
        root = insert(element, root);
    }

        /**
         * Inserts an element into a tree, comparing it to the root
         *
         * @param element, the element to insert
         * @param node, the root
         * @return
         */
    private Node<E> insert(E element, Node<E> node) {
        if (node == null){
            return new Node<>(element, null, null);
        }

        if (comparator.compare(node.getElement(),element) == 0){
            return node;
        }else {
            if (comparator.compare(node.getElement(), element) > 0){
                node.setLeft(insert(element ,node.getLeft()));
                node = balanceNode(node);
            }else {
                node.setRight(insert(element ,node.getRight()));
                node = balanceNode(node);
            }
        }
        return node;
    }

        /**
         * Compares two objects
         *
         * @param otherObj, object to compare
         * @return a boolean
         */
    public boolean equals(Object otherObj) {

        if (this == otherObj)
            return true;

        if (otherObj == null || this.getClass() != otherObj.getClass())
            return false;

        AVL<E,T> second = (AVL<E,T>) otherObj;
        return equals(root, second.root);
    }

        /**
         * Compares the elements of two nodes
         *
         * @param root1 element of node 1
         * @param root2 element of node 2
         * @return the boolean
         */
        public boolean equals(Node<E> root1, Node<E> root2) {
        if (root1 == null && root2 == null)
            return true;
        else if (root1 != null && root2 != null) {
            if (comparator.compare(root1.getElement(), root2.getElement()) == 0) {
                return equals(root1.getLeft(), root2.getLeft())
                        && equals(root1.getRight(), root2.getRight());
            } else
                return false;
        } else return false;
    }
}


