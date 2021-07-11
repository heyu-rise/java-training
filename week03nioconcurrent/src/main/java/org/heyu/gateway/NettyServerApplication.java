package org.heyu.gateway;


import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import lombok.extern.slf4j.Slf4j;
import org.heyu.gateway.inbound.HttpInboundServer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

/**
 * @author heyu
 */
@Slf4j
public class NettyServerApplication {

    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0";

    public static void main(String[] args) throws FileNotFoundException, YamlException {
        URL url = NettyServerApplication.class.getResource("/gateway.yml");
        assert url != null;
        YamlReader reader = new YamlReader(new FileReader(url.getFile()));
        GatewayProperties gatewayProperties = reader.read(GatewayProperties.class);
        int proxyPort = Integer.parseInt(System.getProperty("proxyPort", gatewayProperties.getPort().toString()));
        log.info(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting...");
        HttpInboundServer server = new HttpInboundServer(proxyPort, gatewayProperties.getRoutes());
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
