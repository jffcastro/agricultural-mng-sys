package graph;

import graph.map.MapGraph;
import graph.matrix.MatrixGraph;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.BinaryOperator;

/**
 * The type Algorithms.
 *
 * @author DEI -ISEP
 */
public class Algorithms {

    /**
     * Kruskall graph.
     *
     * @param <V> the type parameter
     * @param <E> the type parameter
     * @param g   the g
     * @return the graph
     */
    public static <V,E> Graph<V,E> kruskall(Graph<V,E> g){
        Graph<V,E> mst = new MapGraph<>(false);
        for (V vertex : g.vertices()){
            mst.addVertex(vertex);
        }
        ArrayList<Edge<V, E>> lstEdges = new ArrayList<>(g.edges());
        Collections.sort(lstEdges);
        for (Edge<V,E> edge : lstEdges){
            LinkedList<V> connectedVerts = DepthFirstSearch(mst, edge.getVOrig());
            if (!connectedVerts.contains(edge.getVDest())){
                mst.addEdge(edge.getVOrig(), edge.getVDest(), edge.getWeight());
            }
        }
        return mst;
    }

    /**
     * Performs breadth-first search of a Graph starting in a vertex
     *
     * @param <V>  the type parameter
     * @param <E>  the type parameter
     * @param g    Graph instance
     * @param vert vertex that will be the source of the search
     * @return a LinkedList with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {
        if (g == null || !g.validVertex(vert)){
            return null;
        }

        LinkedList<V> qbfs = new LinkedList<>();
        LinkedList<V> qaux = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];
        qbfs.add(vert);
        qaux.add(vert);
        visited[g.key(vert)] = true;
        while (!qaux.isEmpty()){
            vert = qaux.remove(0);
            for (V vAdj : g.adjVertices(vert)){
                if (!visited[g.key(vAdj)]){
                    qbfs.add(vAdj);
                    qaux.add(vAdj);
                    visited[g.key(vAdj)] = true;
                }
            }
        }
        return qbfs;

    }

    /** Performs depth-first search starting in a vertex
     *
     * @param g Graph instance
     * @param vOrig vertex of graph g that will be the source of the search
     * @param visited set of previously visited vertices
     * @param qdfs return LinkedList with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {

        if (!g.validVertex(vOrig) || visited[g.key(vOrig)]){
            return;
        }

        qdfs.add(vOrig);
        visited[g.key(vOrig)] = true;

       for (V vAdj : g.adjVertices(vOrig)) {
           DepthFirstSearch(g, vAdj, visited, qdfs);
       }
    }

    /**
     * Performs depth-first search starting in a vertex
     *
     * @param <V>  the type parameter
     * @param <E>  the type parameter
     * @param g    Graph instance
     * @param vert vertex of graph g that will be the source of the search
     * @return a LinkedList with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {
        LinkedList<V> qdfs = new LinkedList<>();
        if (g != null && g.numVertices() > 0) {
            boolean[] visited = new boolean[g.numVertices()];
            DepthFirstSearch(g, vert, visited, qdfs);
            return qdfs;
        }
        return null;
    }

    /** Returns all paths from vOrig to vDest
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param vDest   Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path    stack with vertices of the current path (the path is in reverse order)
     * @param paths   ArrayList with all the paths (in correct order)
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                        LinkedList<V> path, ArrayList<LinkedList<V>> paths) {

        path.add(vOrig);
        visited[g.key(vOrig)] = true;
        for (V vAdj : g.adjVertices(vOrig)){
            if (vAdj == vDest) {
                path.add(vDest);
                paths.add(path);
                path.remove(path.size() - 1);
            }else {
                if (!visited[g.key(vAdj)]){
                    allPaths(g, vAdj, vDest, visited, path, paths);
                }
            }
        }
        path.remove(path.size() - 1);

    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param <V>   the type parameter
     * @param <E>   the type parameter
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from vOrig to vDest
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {

        boolean[] visited = new boolean[g.numVertices()];
        LinkedList<V> path = new LinkedList<>();
        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        allPaths(g, vOrig, vDest, visited, path, paths);
        return paths;


    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with non-negative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param visited  set of previously visited vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */
    private static <V, E> void shortestPathDijkstraWeighted(Graph<V, E> g, V vOrig, boolean[] visited, int [] pathKeys, double [] dist) {

        Edge<V, E> edge;
        for (V vertex : g.vertices()) {

            dist[g.key(vertex)] = Double.MAX_VALUE;
            pathKeys[g.key(vertex)] = -1;
            visited[g.key(vertex)] = false;
        }

        dist[g.key(vOrig)] = 0;
        while (g.key(vOrig) != -1) {
            visited[g.key(vOrig)] = true;
            for (V vAdj : g.adjVertices(vOrig)) {
                edge = g.edge(vOrig, vAdj);
                if (!visited[g.key(vAdj)] && dist[g.key(vAdj)] > (dist[g.key(vOrig)] + (Double.parseDouble(edge.getWeight().toString())))) {
                    dist[g.key(vAdj)] = (dist[g.key(vOrig)] + (Double.parseDouble(edge.getWeight().toString())));
                    pathKeys[g.key(vAdj)] = g.key(vOrig);
                }
            }
            vOrig = g.vertex(getVertMinDistance(dist, visited));
        }

    }

    private static <V, E> void shortestPathDijkstraUnweighted(Graph<V, E> g, V vOrig, int [] pathKeys, int [] dist) {

        ArrayList<V> queue_aux = new ArrayList<>();
        for (V vertex : g.vertices()) {

            dist[g.key(vertex)] = Integer.MAX_VALUE;
            pathKeys[g.key(vertex)] = -1;
        }
        queue_aux.add(vOrig);
        dist[g.key(vOrig)] = 0;
        while (!queue_aux.isEmpty()) {
            vOrig = queue_aux.remove(0);
            for (V vAdj : g.adjVertices(vOrig)) {
                if (dist[g.key(vAdj)] == Integer.MAX_VALUE) {
                    dist[g.key(vAdj)] = dist[g.key(vOrig)] + 1;
                    pathKeys[g.key(vAdj)] = g.key(vOrig);
                    queue_aux.add(vAdj);
                }
            }
        }
    }

    private static int getVertMinDistance(double[]dist, boolean[]visited){
        double min = Double.POSITIVE_INFINITY;
        int v, min_index = -1;
        for(v = 0; v < visited.length; v++){
            if(!visited[v] && dist[v] < min){
                min = dist[v];
                min_index = v;
            }
        }
        return min_index;
    }

    /**
     * Shortest-path between two vertices
     *
     * @param <V>       the type parameter
     * @param <E>       the type parameter
     * @param g         graph
     * @param vOrig     origin vertex
     * @param vDest     destination vertex
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> double shortestPathWeighted(Graph<V, E> g, V vOrig, V vDest, LinkedList<V> shortPath) {

        shortPath.clear();
        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return 0;
        }

        if (vOrig.equals(vDest)) {
            shortPath.add(vDest);
            return 0;
        }

        int numVertices = g.numVertices();
        boolean[] visited = new boolean[numVertices]; //default value: false
        int[] pathKeys = new int[numVertices];
        double[] dist = new double[numVertices];

        for (int i = 0; i < numVertices; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = -1;
        }
        shortestPathDijkstraWeighted(g, vOrig, visited, pathKeys, dist);
        if (pathKeys[g.key(vDest)] == -1) {
            return 0;
        }
        getPath(g, vOrig, vDest, pathKeys, shortPath);
        return dist[g.key(vDest)];
    }

    /**
     * Shortest-path between two vertices
     *
     * @param <V>       the type parameter
     * @param <E>       the type parameter
     * @param g         graph
     * @param vOrig     origin vertex
     * @param vDest     destination vertex
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false otherwise
     */
    public static <V, E> double shortestPathUnweighted(Graph<V, E> g, V vOrig, V vDest, LinkedList<V> shortPath) {

        shortPath.clear();
        if (!g.validVertex(vOrig) || !g.validVertex(vDest)) {
            return 0;
        }

        if (vOrig.equals(vDest)) {
            shortPath.add(vDest);
            return 0;
        }

        int numVertices = g.numVertices();
        int[] pathKeys = new int[numVertices];
        int[] dist = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            dist[i] = Integer.MAX_VALUE;
            pathKeys[i] = -1;
        }
        shortestPathDijkstraUnweighted(g, vOrig, pathKeys, dist);
        if (pathKeys[g.key(vDest)] == -1) {
            return 0;
        }
        getPath(g, vOrig, vDest, pathKeys, shortPath);
        return dist[g.key(vDest)];
    }

    /**
     * Shortest-path between a vertex and all other vertices
     *
     * @param <V>   the type parameter
     * @param <E>   the type parameter
     * @param g     graph
     * @param vOrig start vertex
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static <V, E> boolean shortestPathsWeighted(Graph<V, E> g, V vOrig, ArrayList<LinkedList<V>> paths, ArrayList<Double> dists) {

        paths.clear();
        dists.clear();

        if (!g.validVertex(vOrig)) {
            return false;
        }

        int numVertices = g.numVertices();
        boolean[] visited = new boolean[numVertices]; //default value: false
        int[] pathKeys = new int[numVertices];
        double[] dist = new double[numVertices];

        for (int i = 0; i < numVertices; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = -1;
        }

        shortestPathDijkstraWeighted(g,vOrig, visited, pathKeys, dist);

        for (int i = 0; i < numVertices; i++) {
            paths.add(null);
            dists.add(null);
        }
        for (int i = 0; i < numVertices; i++) {
            LinkedList<V> shortPath = new LinkedList<>();

            if (Double.compare(dist[i], Double.MAX_VALUE) != 0) {
                getPath(g, vOrig, g.vertices().get(i), pathKeys, shortPath);
            }
            paths.set(i, shortPath);
            dists.set(i, dist[i]);
        }
        return true;

    }

    /**
     * Shortest-path between a vertex and all other vertices
     *
     * @param <V>   the type parameter
     * @param <E>   the type parameter
     * @param g     graph
     * @param vOrig start vertex
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static <V, E> boolean shortestPathsUnweighted(Graph<V, E> g, V vOrig, ArrayList<LinkedList<V>> paths, ArrayList<Integer> dists) {

        paths.clear();
        dists.clear();

        if (!g.validVertex(vOrig)) {
            return false;
        }

        int numVertices = g.numVertices();
        int[] pathKeys = new int[numVertices];
        int[] dist = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            dist[i] = Integer.MAX_VALUE;
            pathKeys[i] = -1;
        }

        shortestPathDijkstraUnweighted(g,vOrig, pathKeys, dist);

        for (int i = 0; i < numVertices; i++) {
            paths.add(null);
            dists.add(null);
        }
        for (int i = 0; i < numVertices; i++) {
            LinkedList<V> shortPath = new LinkedList<>();
            if (dist[i] != Integer.MAX_VALUE) {
                getPath(g, vOrig, g.vertices().get(i), pathKeys, shortPath);
            }
            paths.set(i, shortPath);
            dists.set(i, dist[i]);
        }
        return true;

    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest,
                                       int [] pathKeys, LinkedList<V> path) {

        path.push(vDest);
        int vKey = pathKeys[g.key(vDest)];
        if (vKey != -1){
            vDest = g.vertices().get(vKey);
            getPath(g, vOrig, vDest, pathKeys, path);
        }
    }

    /**
     * Calculates the minimum distance graph using Floyd-Warshall
     *
     * @param <V> the type parameter
     * @param <E> the type parameter
     * @param g   initial graph
     * @param ce  comparator between elements of type E
     * @param sum sum two elements of type E
     * @return the minimum distance graph
     */
    public static <V,E> MatrixGraph <V,E> minDistGraph(Graph <V,E> g, Comparator<E> ce, BinaryOperator<E> sum) {
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

}