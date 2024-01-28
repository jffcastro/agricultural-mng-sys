package Tree;

import java.util.*;

public class BST<E, T extends Comparator<E>> implements BSTInterface<E> {


    /** Nested static class for a binary search tree node. */

    public static class Node<E> {
        private E element;          // an element stored at this node
        private Node<E> left;       // a reference to the left child (if any)
        private Node<E> right;      // a reference to the right child (if any)

        /**
         * Constructs a node with the given element and neighbors.
         *
         * @param e  the element to be stored
         * @param leftChild   reference to a left child node
         * @param rightChild  reference to a right child node
         */
        public Node(E e, Node<E> leftChild, Node<E> rightChild) {
            element = e;
            left = leftChild;
            right = rightChild;
        }

        // accessor methods
        public E getElement() { return element; }
        public Node<E> getLeft() { return left; }
        public Node<E> getRight() { return right; }

        // update methods
        public void setElement(E e) { element = e; }
        public void setLeft(Node<E> leftChild) { left = leftChild; }
        public void setRight(Node<E> rightChild) { right = rightChild; }
    }

    //----------- end of nested Node class -----------

    public Node<E> root = null;     // root of the tree
    protected Comparator<E> comparator;

    /* Constructs an empty binary search tree. */
    public BST(T t) {
        root = null;
        comparator = t;

    }

    /*
     * @return root Node of the tree (or null if tree is empty)
     */
    protected Node<E> root() {
        return root;
    }

    /*
     * Verifies if the tree is empty
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty(){
        return root==null;
    }

    /**
     * Returns the Node containing a specific Element, or null otherwise.
     *
     * @param element    the element to find
     * @return the Node that contains the Element, or null otherwise
     *
     * This method despite not being essential is very useful.
     * It is written here in order to be used by this class and its
     * subclasses avoiding recoding.
     * So its access level is protected
     */
    /*protected Node<E> find(Node<E> node, E element) {
        if (node == null) return null;
        else if (node.getElement() == element) return node;
        else if (comparator.compare(node.getElement(), element) > 0) return find(node.getLeft(), element);
        else return find(node.getRight(), element);
    }

     */

    /*
     * Inserts an element in the tree.
     */
    public void insert(E element){
        if(root == null){
            root = new Node<>(element,null,null);
        }
        if(root.getElement() == null){
            root.setElement(element);
        }else if(comparator.compare(root.getElement(), element) > 0){
            if(root.getLeft() == null){
                root.setLeft(new Node<>(element,null,null));
            }else{
                insert(element, root.getLeft());
            }
        }else if(comparator.compare(root.getElement(), element) < 0){
            if(root.getRight() == null){
                root.setRight(new Node<>(element, null, null));
            }else{
                insert(element, root.getRight());
            }
        }
    }

    private Node<E> insert(E element, Node<E> node){
        if (node == null) return new Node<>(element, null, null);
        if (comparator.compare(node.getElement(), element) > 0) node.setLeft(insert(element, node.getLeft()));
        else if (comparator.compare(node.getElement(), element) < 0) node.setRight(insert(element, node.getRight()));
        return node;
    }


    /**
     * Removes an element from the tree maintaining its consistency as a Binary Search Tree.
     */
    public void remove(E element){
        root = remove(element, root());
    }

    private Node<E> remove(E element, Node<E> node) {

        if (node == null) {
            return null;    //throw new IllegalArgumentException("Element does not exist");
        }
        if (comparator.compare(element, node.getElement()) == 0) {
            // node is the Node to be removed
            if (node.getLeft() == null && node.getRight() == null) { //node is a leaf (has no childs)
                return null;
            }
            if (node.getLeft() == null) {   //has only right child
                return node.getRight();
            }
            if (node.getRight() == null) {  //has only left child
                return node.getLeft();
            }
            E min = smallestElement(node.getRight());
            node.setElement(min);
            node.setRight(remove(min, node.getRight()));
        }
        else if (comparator.compare(element, node.getElement()) < 0)
            node.setLeft( remove(element, node.getLeft()) );
        else
            node.setRight( remove(element, node.getRight()) );

        return node;
    }

    /*
     * Returns the number of nodes in the tree.
     * @return number of nodes in the tree
     */
    public boolean search(E elem){
        return search(root,elem);
    }


    private boolean search(Node<E> node, E elem){
        if (node == null)
            return false;
        if (node.getElement() == elem)
            return true;
        if (comparator.compare(node.getElement(),elem)>0)
            return search(node.getLeft(),elem);
        else
            return search(node.getRight(),elem);
    }
    public int size(){
        return size(root);
    }

    private int size(Node<E> node){
        if (node == null){
            return 0;
        }else{
            return 1+ (size(node.left) + size(node.right));
        }
    }

    /*
     * Returns the height of the tree
     * @return height
     */
    public int height(){

        return (root==null)? -1: height(root);

    }

    /**

     * Returns the height of the subtree rooted at Node node.
     *
     * @param node A valid Node within the tree
     * @return height

     */

    protected int height(Node<E> node){

        if (node == null) return -1;

        int hl = height(node.getLeft());
        int hr = height(node.getRight());

        return 1 + (Math.max(hl, hr));

    }


    /**
     * Returns the smallest element within the tree.
     * @return the smallest element within the tree
     */
    public E smallestElement(){
        return smallestElement(root);
    }

    protected E smallestElement(Node<E> node){
        if (node == null){
            return null;
        }
        if (node.getLeft() == null){
            return node.element;
        }
        return smallestElement(node.getLeft());
    }

    public E biggestElement(){
        return biggestElement(root);
    }

    protected E biggestElement(Node<E> node){
        if (node == null){
            return null;
        }
        if (node.getRight() == null){
            return node.element;
        }
        return biggestElement(node.getRight());
    }


    /*
     * Returns an iterable collection of elements of the tree, reported in in-order.
     * @return iterable collection of the tree's elements reported in in-order
     */
    public Iterable<E> inOrder(){
        List<E> snapshot = new ArrayList<>();
        if (root!=null)
            inOrderSubtree(root, snapshot);   // fill the snapshot recursively
        return snapshot;
    }

    public boolean contains( E key )
    {
        return containsHelper(this.root, key);
    }

    private boolean containsHelper(Node<E> root, E key)
    {
        @SuppressWarnings("unchecked")
        int comp = ((Comparable)key).compareTo(root.getElement());
        if(comp == 0)
        {
            return true;
        }
        if(comp < 0 && root.getLeft() !=null)
        {
            containsHelper(root.getLeft(), key); // here, you forgot to save the result of containsHelper
        }
        if(comp > 0 && root.getRight() != null)
        {
            containsHelper(root.getRight(), key); // here, you forgot to save the result of containsHelper
        }
        return false;
    }
    /**
     * Adds elements of the subtree rooted at Node node to the given
     * snapshot using an in-order traversal
     * @param node       Node serving as the root of a subtree
     * @param snapshot  a list to which results are appended
     */
    private void inOrderSubtree(Node<E> node, List<E> snapshot) {
        if (node == null)
            return;
        inOrderSubtree(node.getLeft(), snapshot);
        snapshot.add(node.getElement());
        inOrderSubtree(node.getRight(), snapshot);
    }
    /**
     * Returns an iterable collection of elements of the tree, reported in pre-order.
     * @return iterable collection of the tree's elements reported in pre-order
     */
    public Iterable<E> preOrder(){

        List<E> snapshot = new ArrayList<>();
        if (root == null){
            return null;
        }
        snapshot.add(root.element);
        preOrderSubtree(root.left, snapshot);
        preOrderSubtree(root.right, snapshot);

        return snapshot;

    }
    /**
     * Adds elements of the subtree rooted at Node to the given
     * snapshot using an pre-order traversal
     * @param node       Node serving as the root of a subtree
     * @param snapshot  a list to which results are appended
     */
    private void preOrderSubtree(Node<E> node, List<E> snapshot) {

        if (node == null){
            return;
        }

        snapshot.add(node.element);
        preOrderSubtree(node.left, snapshot);
        preOrderSubtree(node.right, snapshot);

    }
    /**
     * Returns an iterable collection of elements of the tree, reported in post-order.
     * @return iterable collection of the tree's elements reported in post-order
     */
    public Iterable<E> posOrder(){
        List<E> snapshot = new ArrayList<>();
        if (root == null){
            return null;
        }

        posOrderSubtree(root.left, snapshot);
        posOrderSubtree(root.right, snapshot);
        snapshot.add(root.getElement());
        return snapshot;

    }
    /**
     * Adds positions of the subtree rooted at Node node to the given
     * snapshot using an post-order traversal
     * @param node       Node serving as the root of a subtree
     * @param snapshot  a list to which results are appended
     */
    private void posOrderSubtree(Node<E> node, List<E> snapshot) {
        if (node == null){
            return;
        }

        posOrderSubtree(node.left, snapshot);
        posOrderSubtree(node.right, snapshot);
        snapshot.add(node.element);
    }

    /*
     * Returns a map with a list of nodes by each tree level.
     * @return a map with a list of nodes by each tree level
     */
    public Map<Integer,List<E>> nodesByLevel(){
        if (root != null) {
            HashMap<Integer, List<E>> map = new HashMap<>();
            processBstByLevel(root, map, 0);
            return map;
        }

        return null;

    }

    private void processBstByLevel(Node<E> node, Map<Integer,List<E>> result, int level){
        if (node == null){
            return;
        }

        if (result.get(level) != null) {
            result.get(level).add(node.element);
        }else {
            List<E> list = new ArrayList<>();
            list.add(node.element);
            result.put(level, list);
        }

        level ++;
        processBstByLevel(node.left, result, level);

        processBstByLevel(node.right, result, level);


    }



//#########################################################################

    /**
     * Returns a string representation of the tree.
     * Draw the tree horizontally
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        toStringRec(root, 0, sb);
        return sb.toString();
    }

    private void toStringRec(Node<E> root, int level, StringBuilder sb){
        if(root==null)
            return;
        toStringRec(root.getRight(), level+1, sb);
        if (level!=0){
            sb.append("|\t".repeat(Math.max(0, level - 1)));
            sb.append("|-------").append(root.getElement()).append("\n");
        }
        else
            sb.append(root.getElement()).append("\n");
        toStringRec(root.getLeft(), level+1, sb);
    }

} //----------- end of BST class -----------
