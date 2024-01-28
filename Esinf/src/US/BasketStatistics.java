package US;

import Data_info.Localization;

import java.util.Objects;

public class BasketStatistics {

    private Localization user;
    private int totalProductsCompleted;

    private int totalProductsNotComplete;

    private int totalProductsEmpty;

    private double successPercentage;

    private int totalOfProducers;

    public BasketStatistics(Localization user, int totalProductsCompleted, int totalProductsNotComplete, int totalProductsEmpty, double successPercentage, int totalOfProducers) {
        this.user = user;
        this.totalProductsCompleted = totalProductsCompleted;
        this.totalProductsNotComplete = totalProductsNotComplete;
        this.totalProductsEmpty = totalProductsEmpty;
        this.successPercentage = successPercentage;
        this.totalOfProducers = totalOfProducers;
    }

    public Localization getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketStatistics that = (BasketStatistics) o;
        return totalProductsCompleted == that.totalProductsCompleted
                && totalProductsNotComplete == that.totalProductsNotComplete
                && totalProductsEmpty == that.totalProductsEmpty
                && Double.compare(that.successPercentage, successPercentage) == 0
                && totalOfProducers == that.totalOfProducers
                && user.getUser().getUserID().equals(that.user.getUser().getUserID());
    }

    @Override
    public String toString() {
        return String.format("""
                Statistics of Basket %s:
                
                     Total Products Completed = %d
                     Total Products Not Completed = %d
                     Total Products Empty = %d
                     Rate of Success = %.2f%%
                     Total Producers = %d
                     """, user.getUser().getUserID(), totalProductsCompleted, totalProductsNotComplete, totalProductsEmpty, successPercentage, totalOfProducers);

    }
}
