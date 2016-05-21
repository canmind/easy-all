package com.easy.common.core.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class LocalIPUtils {
	private static Map<String, String> ip4s = new HashMap<String, String>();

	private static Map<String, String> ip6s = new HashMap<String, String>();

	private static String ipAddress = null;

	public static String getIp4Single() {
		if (ip4s.isEmpty()) {
			return "127.0.0.1";
		}
		if (null == ipAddress) {
			Iterator<String> iter = ip4s.values().iterator();
			while (iter.hasNext()) {
				ipAddress = (String) iter.next();
				if (!StringUtils.equals("127.0.0.1", ipAddress)) {
					break;
				}
			}
		}
		if (null == ipAddress) {
			ipAddress = "127.0.0.1";
		}
		return ipAddress;
	}

	static {
		try {
			InetAddress addr = InetAddress.getLocalHost();

			String hostname = addr.getHostName();
			if ("127.0.0.1".equals(addr.getHostAddress())) {
				Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
				while (e.hasMoreElements()) {
					NetworkInterface netface = (NetworkInterface) e
							.nextElement();
					String name = netface.getName();
					if (!name.startsWith("lo")) {
						Enumeration<InetAddress> e2 = netface.getInetAddresses();
						while (e2.hasMoreElements()) {
							InetAddress ip2 = (InetAddress) e2.nextElement();
							if ((ip2 instanceof Inet4Address))
								ip4s.put(name, ip2.getHostAddress());
							else
								ip6s.put(name, ip2.getHostAddress());
						}
					}
				}
			} else {
				ip4s.put(hostname, addr.getHostAddress());
			}
		} catch (Exception e) {
		}
	}
}