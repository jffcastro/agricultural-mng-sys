package Data_info;
import Tree.AVL;
import US.CompanyMediaPair;
import graph.map.MapGraph;
import graph.matrix.MatrixGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * The type Graph store.
 */
public class GraphStore {

    /**
     * The constant matrixGraph.
     */
    public static MatrixGraph<Localization, Float> matrixGraph = new MatrixGraph<>(false);

    /**
     * The constant mapGraph.
     */
    public static MapGraph<Localization, Float> mapGraph = new MapGraph<>(false);

    /**
     * The constant clientsList.
     */
    public static ArrayList<Localization> clientsList = new ArrayList<>();
    /**
     * The constant producersList.
     */
    public static ArrayList<Localization> producersList = new ArrayList<>();

    /**
     * The constant companiesList.
     */
    public static ArrayList<Localization> companiesList = new ArrayList<>();

    /**
     * The constant irrigationSystem.
     */
    public static IrrigationSystem irrigationSystem;

    /**
     * The Hubs.
     */
    public static ArrayList<CompanyMediaPair> hubs;

    public static AVL<Localization, ComparatorUserID> treeUserID = new AVL<>(new ComparatorUserID());

    public static HashMap<Integer, Set<Localization>> mapDayClient = new HashMap<>();

}
