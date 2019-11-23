package by.bsuir.documentsearch.controller;

import by.bsuir.documentsearch.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;

public interface Controller {
    void execute();

    void execute(HttpServletRequest request) throws ControllerException;
}
