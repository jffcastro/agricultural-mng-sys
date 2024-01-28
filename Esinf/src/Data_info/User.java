package Data_info;

import Tree.AVL;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * The type User.
 */
public class User implements Comparable<User>{
    //é por exemplo no caso de um cliente "c"
    private char userCode;
    //é, por exemplo no caso de um cliente "c14"
    private String userID;

    Localization hub;

    private HashMap<Integer, Set<ProductOrder>> order;
    private HashMap<Integer, AVL<ProductOrder, ComparatorProductDay>> producerStock;
    /**
     * Instantiates a new User.
     *
     * @param userCode the user code
     * @param userID   the user id
     */
    public User(Character userCode, String userID) {
        if (userCode == 'P' || userCode == 'p'){
            producerStock = new HashMap<>();
        }else {
            order = new HashMap<>();
        }
        this.userCode = userCode;
        this.userID = userID;
    }

    /**
     * Gets user code.
     *
     * @return the user code
     */
    public char getUserCode() {
        return userCode;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserID() {
        return userID;
    }

    public HashMap<Integer, Set<ProductOrder>> getOrder() {
        return order;
    }

    public HashMap<Integer, AVL<ProductOrder, ComparatorProductDay>> getProducerStock() {
        return producerStock;
    }

    public Localization getHub() {
        return hub;
    }

    /**
     * Sets user code.
     *
     * @param userCode the user code
     */
    public void setUserCode(Character userCode) {
        this.userCode = userCode;
    }

    /**
     * Sets user id.
     *
     * @param userID the user id
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setHub(Localization hub) {
        if (this.userCode != 'P' && this.userCode != 'p') {
            this.hub = hub;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userID, user.userID);
    }

    @Override
    public String toString() {
        return "User{" +
                "userCode=" + userCode +
                ", userID='" + userID + '\'' +
                '}';
    }

    @Override
    public int compareTo(User o) {
        return this.userID.compareTo(o.userID);
    }
}
