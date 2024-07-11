package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExporter;

/**
 * It adds a link to the paragraph content
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public class LinkParagraphContentDecorator extends AbstractParagraphContentDecorator {
    /**
     * Link target
     */
    private final String link;

    /**
     * Constructor
     * @param decoratedParagraphContent Paragraph content to decorate
     * @param alink Link target
     */
    public LinkParagraphContentDecorator(IParagraphContent decoratedParagraphContent, String alink) {
        super(decoratedParagraphContent);
        this.link = alink;
    }

    /**
     * Getter
     * @return The link
     */
    public String getLink() {
        return link;
    }

    /**
     * It adds the link code to the rendered content
     * @return Markdown rendering
     */
    @Override
    public String export(IExporter exporter) {
        return exporter.export(this);
    }
}
