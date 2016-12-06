package ch.portmann.web.server;

import ch.portmann.web.MyFirstServlet;
import com.google.inject.servlet.ServletModule;

import javax.servlet.http.HttpServlet;

/**
 * Servlet mappings
 */
public class ServletMappings extends ServletModule {

    @Override
    protected void configureServlets() {

        // bind servlets
        for (ServletMapping mapping : ServletMapping.values()) {
            for (String uri : mapping.uris) {
                bind(mapping.servletClass);
                serve(uri).with(mapping.servletClass);
            }
        }
    }

    public enum ServletMapping {

        hello(MyFirstServlet.class, "/api/hello");

        ServletMapping(Class<? extends HttpServlet> servletClass, String... uris)
        {

            for (int i = 0; i < uris.length; i++) {
                String uri = uris[i];
                if (!uri.startsWith('/' + "")) {
                    uris[i] = "/" + uri;
                }
            }
            this.uris = uris;
            this.servletClass = servletClass;
        }

        private final String[] uris;
        private final Class<? extends HttpServlet> servletClass;

        public String[] getUris() {
            return uris;
        }
    }

}
