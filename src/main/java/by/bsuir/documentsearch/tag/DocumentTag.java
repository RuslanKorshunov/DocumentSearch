package by.bsuir.documentsearch.tag;

import by.bsuir.documentsearch.entity.SearchResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class DocumentTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(DocumentTag.class);
    private List<SearchResult> documents;

    public void setDocuments(List<SearchResult> documents) {
        this.documents = documents;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter writer = pageContext.getOut();
            for (SearchResult searchResult : documents) {
                writer.write("<h4><div class=\"review\">");
                writer.write(searchResult.getDocumentID() +
                        ". <a href=\"" + searchResult.getUrl() + "\">" + searchResult.getTitle() +
                        "</a></h4>");
                writer.write("<h4>Rank: " + searchResult.getRank() + "</h4>");
                String words = "";
                for (String word : searchResult.getTerms()) {
                    words += word + " ";
                }
                writer.write("<h4>Words: " + words + "</h4>");
                writer.write("<h4>" + searchResult.getSnippet() + "...</h4>");
                writer.write("</div>");
            }
        } catch (IOException e) {
            logger.error(e);
        }
        return SKIP_BODY;
    }
}
