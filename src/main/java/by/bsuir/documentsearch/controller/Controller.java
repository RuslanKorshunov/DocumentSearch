package by.bsuir.documentsearch.controller;

import javax.servlet.http.HttpServletRequest;

public interface Controller {
    void execute();

    void execute(HttpServletRequest request);
}
