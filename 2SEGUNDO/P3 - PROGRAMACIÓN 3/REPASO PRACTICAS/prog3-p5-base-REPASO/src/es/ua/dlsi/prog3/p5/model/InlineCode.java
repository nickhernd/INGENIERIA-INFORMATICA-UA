package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExporter;

/**
 * Inline code content
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public class InlineCode extends AbstractTextContent implements IText {
    /**
     * InlineCode text
     * @param text
     */
    public InlineCode(String text) {
        super(text);
    }

    @Override
    public String export(IExporter exporter) {
        return exporter.export(this);
    }
}
