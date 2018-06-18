package redis.clients.jedis;

import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.util.SafeEncoder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

final class StreamMessageProcessor {

    static List<StreamMessage> process(List<Object> rawData) {

        List<StreamMessage> messages = new LinkedList<>();

        if (rawData == null || rawData.isEmpty()) {
            return messages;
        }

        for (Object o : rawData) {

            List<Object> innerData = (List<Object>) o;

            // Inner data contains stream name and list of data objects
            if (innerData.size() != 2) {
                throw new JedisDataException("Stream raw data is an unexpected size!");
            }

            String stream = SafeEncoder.encode((byte[]) innerData.get(0));

            // List of data object entries
            List<Object> entries = (List<Object>) innerData.get(1);

            // Each entry contains an id and a list of key value pairs
            for (Object entry : entries) {

                List<Object> entryData = (List<Object>) entry;

                String offset = SafeEncoder.encode((byte[]) entryData.get(0));

                List<Object> keyValueData = (List<Object>) entryData.get(1);

                if (keyValueData.size() % 2 != 0) {
                    throw new JedisDataException("Stream raw data is an unexpected size!");
                }

                Map<String, String> data = new HashMap<>();

                for (int i = 0; i < keyValueData.size(); i += 2) {
                    data.put(SafeEncoder.encode((byte[]) keyValueData.get(i)), SafeEncoder.encode((byte[]) keyValueData.get(i + 1)));
                }

                messages.add(new StreamMessage(stream, offset, data));
            }
        }

        return messages;
    }
}
