package by.bsuir.documentsearch.controller;

import java.net.http.HttpRequest;

public interface Controller {
    void execute();

    void execute(HttpRequest request);
}
