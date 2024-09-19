package common;

import java.util.Objects;

public class Client {
	private String ip;
	private String host;
	private String status;

	public Client(String ip, String host, String status) {
		super();
		this.ip = ip;
		this.host = host;
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(host, ip, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(host, other.host) && Objects.equals(ip, other.ip) && Objects.equals(status, other.status);
	}

}
