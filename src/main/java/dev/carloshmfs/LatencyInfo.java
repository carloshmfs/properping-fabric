package dev.carloshmfs;

public class LatencyInfo {
    public int averageLatency = 0;
    public long challenge = 0;
    public boolean isPending = false;
    public long pingTime = 0;
    public final LatencyQueue RTT_QUEUE = new LatencyQueue(5);

    public int calculateAverageLatency() {
        this.averageLatency = 0;
        for (int latency : RTT_QUEUE.list.elements()) {
            this.averageLatency += latency;
        }
        return this.averageLatency = this.averageLatency / RTT_QUEUE.list.size();
    }
}
