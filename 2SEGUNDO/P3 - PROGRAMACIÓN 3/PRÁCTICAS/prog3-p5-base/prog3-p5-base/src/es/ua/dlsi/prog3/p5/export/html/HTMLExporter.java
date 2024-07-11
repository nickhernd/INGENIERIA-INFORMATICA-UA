package es.ua.dlsi.prog3.p5.export.html;

import es.ua.dlsi.prog3.p5.export.IExporter;
import es.ua.dlsi.prog3.p5.model.*;

/**
 * It exports the document elements to HTML format
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 25/10/22
 */
public class HTMLExporter implements IExporter {
    /**
     * It generates the opening tag in HTML format
     * @param tag Element tag
     * @return Tag surrounded by angle brackets
     */
    private String createOpenTag(String tag) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('<');
        stringBuilder.append(tag);
        stringBuilder.append('>');
        return stringBuilder.toString();
    }

    /**
     * It generates the closing tag in HTML format
     * @param tag Element tag
     * @return Tag surrounded by angle brackets and a slash
     */
    private String createEndTag(String tag) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("</");
        stringBuilder.append(tag);
        stringBuilder.append('>');
        return stringBuilder.toString();
    }
    /**
     * It creates the opening and closing tags and embeds inside the content
     * @param tag HTML tag
     * @param content HTML element content
     * @return HTML compliant element
     */
    private String createElement(String tag, String content) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(createOpenTag(tag));
        stringBuilder.append(content);
        stringBuilder.append(createEndTag(tag));
        return stringBuilder.toString();

    }
    @Override
    public String export(BoldTextDecorator boldTextDecorator) {
        return createElement("strong", boldTextDecorator.getDecoratedElement().export(this));
    }

    @Override
    public String export(InlineCode code) {
        return createElement("code", code.getText());
    }

    @Override
    public String export(CodeBlock codeBlock) {
        return createElement("pre", codeBlock.getText());
    }

    @Override
    public String export(Heading heading) {
        return createElement("h" + heading.getLevel(), heading.getText());
    }

    @Override
    public String export(HorizontalRule horizontalRule) {
        return "<hr>";
    }

    @Override
    public String export(Image image) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<img src='");
        stringBuilder.append(image.getImagePath());
        stringBuilder.append("'");
        if (image.hasAlt()) {
            stringBuilder.append(" alt='");
            stringBuilder.append(image.getAlt());
            stringBuilder.append("'");
        }
        stringBuilder.append("/>");
        return stringBuilder.toString();
    }

    @Override
    public String export(ItalicsTextDecorator italicTextDecorator) {
        return createElement("em",italicTextDecorator.getDecoratedElement().export(this));
    }

    @Override
    public String export(LinkParagraphContentDecorator linkParagraphContentDecorator) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<a href='");
        stringBuilder.append(linkParagraphContentDecorator.getLink());
        stringBuilder.append("'>");
        stringBuilder.append(linkParagraphContentDecorator.getDecoratedElement().export(this));
        stringBuilder.append("</a>");
        return stringBuilder.toString();
    }

    private String exportList(IDocumentElement [] items, String tag) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(createOpenTag(tag));
        stringBuilder.append('\n');
        for (IDocumentElement documentElement: items) {
            stringBuilder.append("<li>");
            stringBuilder.append(documentElement.export(this));
            stringBuilder.append("</li>\n");
        }
        stringBuilder.append(createEndTag(tag));
        stringBuilder.append('\n');
        return stringBuilder.toString();
    }
    @Override
    public String export(OrderedListBlock orderedListBlock) {
        return exportList(orderedListBlock.getItems(), "ol");
    }

    @Override
    public String export(UnorderedListBlock unorderedListBlock) {
        return exportList(unorderedListBlock.getItems(), "ul");
    }

    @Override
    public String export(Paragraph paragraph) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<p>");
        stringBuilder.append(paragraph.exportConcatenatedContents(this, null));
        stringBuilder.append("</p>");
        return stringBuilder.toString();
    }

    @Override
    public String export(Document document) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>\n");
        stringBuilder.append("<body>\n");
        stringBuilder.append(document.exportConcatenatedContents(this, "\n"));
        stringBuilder.append("</body>\n");
        stringBuilder.append("</html>");
        return stringBuilder.toString();
    }

    @Override
    public String export(AbstractTextContent abstractTextContent) {
        return abstractTextContent.getText();
    }
    
    @Override
    public String export(BreakLine br) {
        return "<br>";
    }
    
    @Override
    public String export(StrikeThroughDecorator std) {
        return createElement("del", std.getDecoratedElement().export(this));
    }
    
    @Override
    public String export(Quote quote) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<blockquote>");
        stringBuilder.append(quote.exportConcatenatedContents(this, null));
        stringBuilder.append("</blockquote>");
        return stringBuilder.toString();
    }
}
