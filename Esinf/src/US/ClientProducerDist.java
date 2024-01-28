package US;

import Data_info.User;

import java.util.Objects;

public class ClientProducerDist {

    private User client;

    private User producer;

    private int dist;

    public ClientProducerDist(User client, User producer, int dist) {
        this.client = client;
        this.producer = producer;
        this.dist = dist;
    }

    public User getClient() {
        return client;
    }

    public User getProducer() {
        return producer;
    }

    public int getDist() {
        return dist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientProducerDist that = (ClientProducerDist) o;
        return dist == that.dist && (client.equals(that.client)) && (producer.equals(that.producer));
    }
}
