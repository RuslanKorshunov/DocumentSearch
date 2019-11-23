package by.bsuir.documentsearch.servlet;

import by.bsuir.documentsearch.controller.Controller;
import by.bsuir.documentsearch.controller.DocumentController;
import by.bsuir.documentsearch.controller.WordController;
import by.bsuir.documentsearch.exception.ControllerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DocumentSearch", urlPatterns = "/")
public class MainServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MainServlet.class);
    private static final String TARGET = "page/search.jsp";

    @Override
    public void init() throws ServletException {
        new WordController().execute();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Controller controller = new DocumentController();
            controller.execute(req);
            req.getRequestDispatcher(TARGET).forward(req, resp);
            logger.info("Result");
        } catch (ControllerException e) {
            logger.error(e);
            resp.sendRedirect(TARGET);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
