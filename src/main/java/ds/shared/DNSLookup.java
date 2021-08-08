package ds.shared;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

/**
 * DNS lookup is a helper class for service discovery
 */
public class DNSLookup {
  /**
   * getEndpoint uses jmdns to find service endpoint
   * @param serviceName is service name defined in jmdns
   * @return endpoint or empty string is the endpoint is not found
   */
  static public String getEndpoint(String serviceName) {
    JmDNS jmdns;
    try {
      jmdns = JmDNS.create(InetAddress.getLocalHost());
    } catch (UnknownHostException e) {
      e.printStackTrace(System.err);
      return "";
    } catch (IOException e) {
      e.printStackTrace(System.err);
      return "";
    }
    ServiceInfo[] services = jmdns.list("_http._tcp.local.");
    for (ServiceInfo serviceInfo : services) {

      if (serviceInfo.getName().equals(serviceName)) {
        return serviceInfo.getInetAddresses()[0].getHostAddress() + ":" +
            Integer.toString(serviceInfo.getPort());
      }
    }

    return "";
  }
}
