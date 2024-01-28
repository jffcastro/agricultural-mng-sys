package US;

import Data_info.Localization;
import Data_info.ProductOrder;
import Data_info.User;

import java.util.HashMap;
import java.util.Set;

public class Expedition {

    private Localization client;

    private Set<ProductOrder> listOfProductsOrder;
    private HashMap<Localization, Set<ProductOrder>> listOfProductsDispatched;

    public Expedition(Localization client, Set<ProductOrder> listOfProductsOrder, HashMap<Localization, Set<ProductOrder>> listOfProductsDispatched) {
        this.client = client;
        this.listOfProductsOrder = listOfProductsOrder;
        this.listOfProductsDispatched = listOfProductsDispatched;
    }

    public Localization getClient() {
        return client;
    }

    public Set<ProductOrder> getListOfProductsOrder() {
        return listOfProductsOrder;
    }

    public HashMap<Localization, Set<ProductOrder>> getListOfProductsDispatched() {
        return listOfProductsDispatched;
    }
}
