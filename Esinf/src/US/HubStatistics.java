package US;

import Data_info.Localization;

import java.util.Objects;

public class HubStatistics {

    private final Localization hub;
    private final int totalOfClients;
    private final int totalOfProducers;

    public HubStatistics(Localization hub, int totalOfClients, int totalOfProducers) {
        this.hub = hub;
        this.totalOfClients = totalOfClients;
        this.totalOfProducers = totalOfProducers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HubStatistics that = (HubStatistics) o;
        return totalOfClients == that.totalOfClients
                && totalOfProducers == that.totalOfProducers
                && hub.getUser().getUserID().equals(that.hub.getUser().getUserID());
    }

    @Override
    public String toString() {
        return String.format("""
                Statistics of Hub %s:
                
                    Total of Clients = %d
                    Total of Producers = %d
                    """, hub.getUser().getUserID(), totalOfClients, totalOfProducers);
    }
}
