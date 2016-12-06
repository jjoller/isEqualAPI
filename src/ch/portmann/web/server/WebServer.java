package ch.portmann.web.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Embedded http server
 */
public class WebServer {

    private static final Logger log = Logger.getLogger(WebServer.class.getName());

    private static final int PORT = 8080;
    private static final int SECURE_PORT = 8443;
    private static final String WEB_ROOT = "WebContent";


    /**
     * Simple starter for a jetty HTTPS server.
     *
     * @return
     * @throws Exception
     */
    public Server server() throws Exception {

        Server server = new Server();

        // create connectors
        HttpConfiguration http_config = new HttpConfiguration();
        http_config.setSecureScheme("https");
        http_config.setSecurePort(SECURE_PORT);
        http_config.setOutputBufferSize(32768);

        // http
        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(http_config));
        http.setPort(PORT);
        http.setIdleTimeout(30000);

        Connector[] connectors;
        connectors = new Connector[]{http};

        // enable compression
        GzipHandler gzipHandlerRES = new GzipHandler();
        gzipHandlerRES.setHandler(resourceHandlerInjector(server));
        server.setHandler(gzipHandlerRES);

        server.setConnectors(connectors);
        return server;
    }

    protected Handler resourceHandlerInjector(Server server) {

        ServletContextHandler handler = new ServletContextHandler();
        handler.setSessionHandler(new SessionHandler());
        handler.setContextPath("/");

        // guice servlet injector
        handler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));

        // Lastly, the default servlet for root content (always needed, to satisfy servlet spec)
        // It is important that this is last.
        handler.setResourceBase(WEB_ROOT);
        ServletHolder holderPwd = new ServletHolder("default", DefaultServlet.class);
        holderPwd.setInitParameter("dirAllowed", "false");
        handler.addServlet(holderPwd, "/");

        handler.setServer(server);

        return handler;
    }


    protected static Injector buildInjector() {

        ServletMappings applicationServletModule = new ServletMappings();
        BusinessMappings businessModule = new BusinessMappings();

        // no need to explicitly passing the injector, guice will take care of it
        Injector injector = Guice.createInjector(businessModule, applicationServletModule);
        return injector;
    }

    /**
     * java -cp WebContent/WEB-INF/classes:WebContent/WEB-INF/lib/*
     * server.StartServer
     *
     * @param args Input arguments: properties file, server index
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        //hack for charset
        System.setProperty("file.encoding", "UTF-8");
        Field charset = Charset.class.getDeclaredField("defaultCharset");
        charset.setAccessible(true);
        charset.set(null, null);

        Injector injector = buildInjector();

        try {
            WebServer serverFactory = injector.getInstance(WebServer.class);
            org.eclipse.jetty.server.Server server = serverFactory.server();
            server.start();
            server.join();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
