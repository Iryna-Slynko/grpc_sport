package ds.shared;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class DNSLookup {
  static public String getEndpoint(String serviceName) {
    JmDNS jmdns;
    try {
      jmdns = JmDNS.create(InetAddress.getLocalHost());
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace(System.err);
      return "";
    } catch (IOException e) {
      // TODO Auto-generated catch block
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
