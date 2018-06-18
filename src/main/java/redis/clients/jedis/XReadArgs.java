package redis.clients.jedis;

import redis.clients.jedis.exceptions.JedisDataException;

import java.util.*;

public class XReadArgs {

    public final List<String> streamsAndOffsets;
    public final int count;
    public final boolean block;
    public final long blockDuration;

    /**
     * Creates simple XReadArgs object.
     * @param streamsAndOffsets List of stream names and offsets... stream names first, offsets second.
     */
    public XReadArgs(List<String> streamsAndOffsets) {
        this.streamsAndOffsets = streamsAndOffsets;
        this.count = 0;
        this.block = false;
        this.blockDuration = 0;
    }

    private XReadArgs(List<String> streamsAndOffsets, int count, boolean block, long blockDuration) {
        this.streamsAndOffsets = streamsAndOffsets;
        this.count = count;
        this.block = block;
        this.blockDuration = blockDuration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final List<String> streams = new ArrayList<>();
        private final List<String> offsets = new ArrayList<>();
        private int count;
        private boolean block;
        private long blockDuration;

        private Builder() {
        }

        public Builder clearStreams() {
            streams.clear();
            offsets.clear();
            return this;
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

            List<String> streamsAndOffsets = new ArrayList<>(streams.size() + offsets.size());
            streamsAndOffsets.addAll(streams);
            streamsAndOffsets.addAll(offsets);
            return new XReadArgs(streamsAndOffsets, count, block, blockDuration);
        }
    }
}
