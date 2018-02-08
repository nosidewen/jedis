package redis.clients.jedis;

import java.util.Map;

public class StreamMessage {

    private final String stream;
    private final String id;
    private final Map<String, String> data;

    public StreamMessage(String stream, String id, Map<String, String> data) {
        this.stream = stream;
        this.id = id;
        this.data = data;
    }

    public String getStream() {
        return stream;
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getData() {
        return data;
    }


}
