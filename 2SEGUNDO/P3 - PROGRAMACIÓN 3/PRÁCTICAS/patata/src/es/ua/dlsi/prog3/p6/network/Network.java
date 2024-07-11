package es.ua.dlsi.prog3.p6.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

import es.ua.dlsi.prog3.p6.algorithms.Algorithms;
import es.ua.dlsi.prog3.p6.algorithms.GraphAlgorithmException;
import es.ua.dlsi.prog3.p6.algorithms.ICostOperators;
import es.ua.dlsi.prog3.p6.graph.Graph;
import es.ua.dlsi.prog3.p6.graph.Node;
import es.ua.dlsi.prog3.p6.graph.NodeNotFoundException;

//<NodeLabelType, EdgeLabelType>

public class Network {
	/**
	 * Devices map with latencies between devices
	 */
	private Graph<Device, Double> graph;
	
	/**
	 * Graph node corresponding to each device
	 */
	private Map<Device, Node<Device>> deviceNodes;

	/**
	 * Default constructor
	 */
	public Network() {
		this.graph = new Graph<>();
		this.deviceNodes = new HashMap<>();
	}
	
	/**
	 * It adds a device to the network
	 * @param device
	 */
	public void addDevice(Device device) {
		String a = device.getName();
		Node<Device> node = this.graph.addNode(a);
		this.deviceNodes.put(device, node);
	}
	
	/**
	 * Internal method to retrieve the node associated to the device 
	 * @param device Network device
	 * @return Node associated to the device. Always not null
	 */
	private Node<Device> getNode(Device device) {
		Node<Device> result = deviceNodes.get(device);
		if (result == null) {
			// this message should never be thrown as we use this method internally and all devices are added to the deviceNodes map
			throw new RuntimeException("Cannot find device " + device); 
		}
		return result;
	}
	
	/**
	 * Adds a latency measurement to the network
	 * @param fromDevice Source device
	 * @param toDevice Target device
	 * @param milliseconds Latency measured in milliseconds
	 */
	public void addLatency(Device fromDevice, Device toDevice, Double milliseconds) {
		Node<Device> fromNode = getNode(fromDevice);
		Node<Device> toNode = getNode(toDevice);
		String mili = milliseconds.toString();
		
		try {
			this.graph.addEdge(fromNode, mili, toNode);
		} catch (NodeNotFoundException e) {
			throw new RuntimeException(e); // this should never happen
		}
	}
	
	/**
	 * It prints the network using a GraphViz DOT format
	 * @param file Output file
	 * @throws FileNotFoundException If the file cannot be written
	 */
	public void printNetwork(File file) throws FileNotFoundException {
		Algorithms.exportDot(file, graph);
	}

	/**
	 * Inner class
	 */
	public static class Latency implements Comparable<Latency> {
		/**
		 * From device
		 */
		private Device source;
		/**
		 * To device
		 */
		private Device target;
		/**
		 * Time to reach the target device from the source device
		 */
		private double time;

		/**
		 * Constructor
		 * @param source From device
		 * @param target To Device
		 * @param time Latency
		 */
		public Latency(Device source, Device target, double time) {
			this.source = source;
			this.target = target;
			this.time = time;
		}

		/**
		 * Getter
		 * @return Source
		 */
		public Device getSource() {
			return source;
		}
		/**
		 * Getter
		 * @return Target
		 */
		public Device getTarget() {
			return target;
		}
		/**
		 * Getter
		 * @return Time
		 */
		public double getTime() {
			return time;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Latency)) return false;

			Latency latency = (Latency) o;

			if (Double.compare(latency.time, time) != 0) return false;
			if (!source.equals(latency.source)) return false;
			return target.equals(latency.target);
		}

		@Override
		public int hashCode() {
			int result;
			long temp;
			result = source.hashCode();
			result = 31 * result + target.hashCode();
			temp = Double.doubleToLongBits(time);
			result = 31 * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public int compareTo(Latency o) {
			int from = source.compareTo(o.source);
			if (from == 0) {
				return target.compareTo(o.target);
			} else {
				return from;
			}
		}
	}

	/**
	 * It computes the best latencies between all given devices and prints it to the console
	 * @param devices to be printed latencies sorted
	 */
	public SortedSet<Latency> computeLatencyMap(List<? extends Device> devices) {
		ICostOperators<Double> costOperators = new ICostOperators<Double>() {
			@Override
			public Double zero() {
				return 0.0;
			}

			@Override
			public Double maximumValue() {
				return Double.MAX_VALUE;
			}

			@Override
			public Double add(Double a, Double b) {
				return a+b;
			}
		};
		TreeSet<Latency> result = new TreeSet<>();
		try {
			for (Device fromDevice: devices) {
				Node<Device> fromNode = getNode(fromDevice);
				for (Device toDevice: devices) {
					Node<Device> toNode = getNode(toDevice);
					Double cost = (double) Algorithms.shortestPathCost(graph, fromNode, toNode, costOperators);
					result.add(new Latency(fromDevice, toDevice, cost));
				}
			}
		} catch (NodeNotFoundException | GraphAlgorithmException exception) {
			System.err.println("Cannot compute the latency map: " + exception);
		}
		return result;
	}

	/**
	 * It computes the best latencies between all given devices and prints it to the console
	 * @param devices to be printed
	 */
	public void printLatencyMap(List<? extends Device> devices) {
		Set<Latency> latencies = computeLatencyMap(devices);
		System.out.println("From device\tTo device\tLatency");
	    DecimalFormat formatter = new DecimalFormat("#0.00");
		for (Latency latency: latencies) {
				System.out.println(latency.getSource() + "\t" + latency.getTarget() + "\t" + formatter.format(latency.getTime()));
		}
	}

	
	/**
	 * Example code
	 * @param args Not used
	 */
	public static final void main(String [] args) {
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
		
		List<Computer> computers = Arrays.asList(c1, c2);
		network.printLatencyMap(computers);
		
	}
}
