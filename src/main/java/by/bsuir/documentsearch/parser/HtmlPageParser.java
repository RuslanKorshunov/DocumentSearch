package by.bsuir.documentsearch.parser;

import by.bsuir.documentsearch.entity.Document;
import by.bsuir.documentsearch.exception.ParserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HtmlPageParser implements Parser {
    public Document parse(String url) throws ParserException {
        if (url == null) {
            throw new ParserException("url can't be null");
        }
        Document document = new Document();
        try {
            Connection connection = Jsoup.connect(url);
            String title = connection.get().title();
            document.setTitle(title);
            String text = "";
            Elements elements = connection.get().getElementsByClass("mw-parser-output");
            for (Element element : elements) {
                Elements elementsP = element.getElementsByTag("p");
                for (Element elementP : elementsP) {
                    text += elementP.text();
                }
            }
            document.setText(text);
            document.setUrl(url);
        } catch (IOException e) {
            throw new ParserException(e);
        }
        return document;
    }

    @Override
    public Document parse(Document document) {
        return null;
    }
}