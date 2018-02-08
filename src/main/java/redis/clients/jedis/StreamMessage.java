package redis.clients.jedis;

import java.util.Map;

public class StreamMessage {

    private final String stream;
    private final String offset;
    private final Map<String, String> data;

    StreamMessage(String stream, String offset, Map<String, String> data) {
        this.stream = stream;
        this.offset = offset;
        this.data = data;
    }

    public String getStream() {
        return stream;
    }

    public String getOffset() {
        return offset;
    }

    public Map<String, String> getData() {
        return data;
    }


}
