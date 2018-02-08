package redis.clients.jedis;

import redis.clients.jedis.exceptions.JedisDataException;

import java.util.LinkedList;
import java.util.List;

public class XReadArgs {

    final List<String> streams;
    final List<String> offsets;
    final int count;
    final boolean block;
    final long blockDuration;

    private XReadArgs(List<String> streams, List<String> offsets, int count, boolean block, long blockDuration) {
        this.streams = streams;
        this.offsets = offsets;
        this.count = count;
        this.block = block;
        this.blockDuration = blockDuration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final List<String> streams = new LinkedList<>();
        private final List<String> offsets = new LinkedList<>();
        private int count;
        private boolean block;
        private long blockDuration;

        private Builder() {
        }

        public Builder add(String stream, String offset) {
            streams.add(stream);
            offsets.add(offset);
            return this;
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder setBlock(boolean block, long blockDuration) {
            this.block = block;
            this.blockDuration = blockDuration;
            return this;
        }

        public String getKey() {
            StringBuilder stringBuilder = new StringBuilder();

            for (String s : streams) {
                stringBuilder.append(s);
            }

            return stringBuilder.toString();
        }

        public XReadArgs build() {

            if (streams.isEmpty()) {
                throw new JedisDataException("Must at least 1 stream!");
            }

            return new XReadArgs(streams, offsets, count, block, blockDuration);
        }
    }
}
