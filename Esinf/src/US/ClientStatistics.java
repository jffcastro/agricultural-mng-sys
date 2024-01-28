package US;

import Data_info.Localization;

import java.util.Objects;

public class ClientStatistics {

    private Localization client;

    private final int totalBasketsCompleted;
    private final int totalBasketsNotCompleted;
    private final int totalDistinctSuppliersCompleted;

    public ClientStatistics(Localization client, int totalBasketsCompleted, int totalBasketsNotCompleted, int totalDistinctSuppliersCompleted) {
        this.client = client;
        this.totalBasketsCompleted = totalBasketsCompleted;
        this.totalBasketsNotCompleted = totalBasketsNotCompleted;
        this.totalDistinctSuppliersCompleted = totalDistinctSuppliersCompleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientStatistics that = (ClientStatistics) o;
        return totalBasketsCompleted == that.totalBasketsCompleted
                && totalBasketsNotCompleted == that.totalBasketsNotCompleted
                && totalDistinctSuppliersCompleted == that.totalDistinctSuppliersCompleted
                && client.getUser().getUserID().equals(that.client.getUser().getUserID());
    }

    @Override
    public String toString() {
        return String.format("""
                Statistics of Client %s
                    Total Baskets Completed= %d
                    Total Baskets Not Completed= %d
                    Total Distinct Suppliers Completed= %d
                """, client.getUser().getUserID(), totalBasketsCompleted, totalBasketsNotCompleted, totalDistinctSuppliersCompleted);
    }
}
