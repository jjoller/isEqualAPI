package ch.portmann.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


public class JettyStart {
	public static void main(String[] args) throws Exception {
		
		Server server = new Server(8083);
		ServletContextHandler handler = new ServletContextHandler(server, "/api");
		handler.addServlet(ExampleServlet.class, "/");
		
		server.start();

	}
}