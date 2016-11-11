package org.apache.playframework.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetUtils {

	private static final Logger logger = LoggerFactory.getLogger(NetUtils.class);

	public static final String LOCALHOSTIP = "127.0.0.1";

	private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

	private static volatile InetAddress LOCAL_ADDRESS = null;

	public static InetAddress getLocalAddress() {
		if (LOCAL_ADDRESS != null)
			return LOCAL_ADDRESS;
		InetAddress localAddress = getLocalAddress0();
		LOCAL_ADDRESS = localAddress;
		return localAddress;
	}

	/**
	 * 获取ip地址，非127.0.0.1
	 * 
	 * @return
	 */
	private static InetAddress getLocalAddress0() {
		InetAddress localAddress = null;
		try {
			localAddress = InetAddress.getLocalHost();
			if (isValidAddress(localAddress))
				return localAddress;
		} catch (Throwable e) {
			logger.warn(
					new StringBuilder().append("Failed to retriving ip address, ").append(e.getMessage()).toString(),
					e);
		}
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			if (interfaces != null)
				while (interfaces.hasMoreElements()) {
					try {
						NetworkInterface network = (NetworkInterface) interfaces.nextElement();
						Enumeration<InetAddress> addresses = network.getInetAddresses();
						if (addresses != null)
							while (addresses.hasMoreElements())
								try {
									InetAddress address = (InetAddress) addresses.nextElement();
									if (isValidAddress(address))
										return address;
								} catch (Throwable e) {
									logger.warn(new StringBuilder().append("Failed to retriving ip address, ")
											.append(e.getMessage()).toString(), e);
								}
					} catch (Throwable e) {
						logger.warn(new StringBuilder().append("Failed to retriving ip address, ")
								.append(e.getMessage()).toString(), e);
					}
				}
		} catch (Throwable e) {
			logger.warn(
					new StringBuilder().append("Failed to retriving ip address, ").append(e.getMessage()).toString(),
					e);
		}
		logger.error("Could not get local host ip address, will use 127.0.0.1 instead.");
		return localAddress;
	}

	private static boolean isValidAddress(InetAddress address) {
		if ((address == null) || (address.isLoopbackAddress()))
			return false;
		String name = address.getHostAddress();
		return (name != null) && (!"0.0.0.0".equals(name)) && (!"127.0.0.1".equals(name))
				&& (IP_PATTERN.matcher(name).matches());
	}

	public static void main(String[] args) {
		System.out.println(getLocalAddress().getHostAddress());
	}
}
