package by.bsuir.documentsearch.parser;

import by.bsuir.documentsearch.entity.Document;
import by.bsuir.documentsearch.exception.ParserException;

public interface Parser {
    Document parse(String url) throws ParserException;

    Document parse(Document object) throws ParserException;
}
