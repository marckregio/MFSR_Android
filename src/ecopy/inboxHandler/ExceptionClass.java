package ecopy.inboxHandler;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

import org.apache.http.conn.util.InetAddressUtils;

public class ExceptionClass extends Declarations{
	
	public static String getLocalAddress(){
	    try {
	        String ipv4;
	        List<NetworkInterface>  nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
	        if(nilist.size() > 0){
	            for (NetworkInterface ni: nilist){
	                List<InetAddress>  ialist = Collections.list(ni.getInetAddresses());
	                if(ialist.size()>0){
	                    for (InetAddress address: ialist){
	                        if (!address.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipv4=address.getHostAddress())){ 
	                            return ipv4;
	                        }
	                    }
	                }

	            }
	        }

	    } catch (SocketException ex) {
	    }
	    return "No Network Connection";
	}
	
	public static String blankPage(){
		String html = "<html><head>"
				+ "<style>"
				+ "body{background-color:#4A9EBE;}"
				+ "</style>"
				+ "</head>"
				+ "<body><center><br><br><br>"
				+ "<h1>No <br> Internet <br>Connection</h1>"
				+ "<p>Please check Wireless Connection or 3g Connection</p>"
				+ "<br><br>"
				+ "<br><br><br><br><br>"
				+ "<b>ECOPY CORPORATION - ITDEPT</b>"
				+ "</center></body></html>";
		return html;
	}
	
}