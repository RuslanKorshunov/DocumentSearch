package by.bsuir.documentsearch.parser;

import by.bsuir.documentsearch.entity.Document;
import by.bsuir.documentsearch.exception.ParserException;
import by.bsuir.documentsearch.service.DocumentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DocumentParser implements Parser {
    private static final Logger logger = LogManager.getLogger(DocumentService.class);
    private static final String SPACE_REGEX = " ";
    private static final String STOP_WORDS_FILE = "stopwords";
    private List<String> stopWords;

    public DocumentParser() {
        stopWords = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(STOP_WORDS_FILE).getFile());
        try (FileReader reader = new FileReader(file);
             Scanner scanner = new Scanner(reader)) {
            if (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                stopWords.add(word);
            }
        } catch (IOException e) {
            logger.error(e);
            logger.warn("stop words list is empty because stopWords.txt wasn't read");
        }
    }

    @Override
    public Document parse(String url) {
        return null;
    }

    @Override
    public Document parse(Document document) throws ParserException {
        if (document == null) {
            throw new ParserException("document can't be null");
        }
        String text = document.getText();
        if (text == null) {
            throw new ParserException("document has invalid value because his parameter \"text\" is null");
        }
        text = text.replaceAll("[^\\w]|_|[\\d]", SPACE_REGEX);
        StringTokenizer tokenizer = new StringTokenizer(text, SPACE_REGEX);
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            word = word.toLowerCase();
            if (!stopWords.contains(word) && !word.equals("")) {
                if (document.containsWord(word)) {
                    int number = document.getNumber(word);
                    document.put(word, ++number);
                } else {
                    document.put(word, 1);
                }
            }
        }
        return document;
    }
}