package es.ua.dlsi.prog3.p6.network;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 11/11/22
 */
public class NetworkTest {

    @Test
    public void computeLatencyMap() {
        Network network = new Network();
        Computer c1 = new Computer("Computer #1", "192.168.1.2");
        Computer c2 = new Computer("Computer #2", "192.168.1.32");
        Router r = new Router("Main router", "192.168.1.1");
        network.addDevice(c1);
        network.addDevice(c2);
        network.addDevice(r);
        network.addLatency(r, c1, 20.2);
        network.addLatency(c1, r, 19.6);
        network.addLatency(r, c2, 11.3);
        network.addLatency(c2, r, 12.1);
        network.addLatency(c1, c2, 44.2);
        network.addLatency(c2, c1, 42.9);

        SortedSet<Network.Latency> expected = new TreeSet<>();
        expected.add(new Network.Latency(c1, c1, 0));
        expected.add(new Network.Latency(c1, c2, 30.9));
        expected.add(new Network.Latency(c2, c1, 32.3));
        expected.add(new Network.Latency(c2, c2, 0));

        List<Computer> computers = Arrays.asList(c1, c2);
        assertEquals(expected, network.computeLatencyMap(computers));
    }
}
