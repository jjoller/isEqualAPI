package ch.portmann.web;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Example servlet
 */
@Singleton
public class MyFirstServlet extends HttpServlet {

    @Inject
    public Gson gson;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.getWriter().print(gson.toJson(new MyObject()));
    }

    class MyObject {

        int status = 1;
        String message = "Message";

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }

}
