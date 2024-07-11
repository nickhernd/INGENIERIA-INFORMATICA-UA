package es.ua.dlsi.prog3.p6.network;

/**
 * Base network device.
 */
public abstract class Device implements Comparable<Device> {
	/**
	 * Name of the device
	 */
	private String name;
	/**
	 * Address of the device
	 */
	private String address;
	/**
	 * Constructor
	 * @param name Name of the device
	 * @param address Address of the device
	 */
	public Device(String name, String address) {
		this.name = name;
		this.address = address;
	}
	/**
	 * Getter
	 * @return Name of the device
	 */
	public String getName() {
		return name;
	}
	/**
	 * Getter
	 * @return Address of the device
	 */
	public String getAddress() {
		return address;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return name + "[" + address + "]";
	}

	@Override
	public int compareTo(Device o) {
		int cn = name.compareTo(o.name);
		if (cn == 0) {
			return address.compareTo(o.address);
		} else {
			return cn;
		}
	}
}
