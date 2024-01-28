package US;

import Data_info.Localization;

public class ProducerStatistics {

    private Localization producer;

    private final int totalBasketsCompleted;
    private final int totalBasketsNotCompleted;
    private final int totalClientsCompleted;
    private final int totalProductsOutOfStock;
    private final int totalHubsCompleted;

    public ProducerStatistics(Localization producer, int totalBasketsCompleted, int totalBasketsNotCompleted, int totalClientsCompleted, int totalProductsOutOfStock, int totalHubsCompleted) {
        this.producer = producer;
        this.totalBasketsCompleted = totalBasketsCompleted;
        this.totalBasketsNotCompleted = totalBasketsNotCompleted;
        this.totalClientsCompleted = totalClientsCompleted;
        this.totalProductsOutOfStock = totalProductsOutOfStock;
        this.totalHubsCompleted = totalHubsCompleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProducerStatistics that = (ProducerStatistics) o;
        return totalBasketsCompleted == that.totalBasketsCompleted
                && totalBasketsNotCompleted == that.totalBasketsNotCompleted
                && totalClientsCompleted == that.totalClientsCompleted
                && totalProductsOutOfStock == that.totalProductsOutOfStock
                && totalHubsCompleted == that.totalHubsCompleted
                && producer.getUser().getUserID().equals(that.producer.getUser().getUserID());
    }

    @Override
    public String toString() {
        return String.format("""
                Statistics of Producer %s
                    Total Baskets Completed= %d
                    Total Baskets Not Completed= %d
                    Total Clients Completed= %d
                    Total Products Out Of Stock= %d
                    Total Hubs Completed= %d
                """, producer.getUser().getUserID(), totalBasketsCompleted, totalBasketsNotCompleted, totalClientsCompleted, totalProductsOutOfStock, totalHubsCompleted);
    }
}
