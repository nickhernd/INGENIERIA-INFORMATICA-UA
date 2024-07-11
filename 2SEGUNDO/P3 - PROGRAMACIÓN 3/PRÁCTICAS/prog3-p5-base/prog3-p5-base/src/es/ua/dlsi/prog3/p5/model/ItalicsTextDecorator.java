package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExporter;

/**
 * It adds a bold emphasis to the text
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public class ItalicsTextDecorator extends AbstractTextDecorator {
    /**
     * Constructor
     *
     * @param text Text contents
     */
    public ItalicsTextDecorator(IText text) {
        super(text);
    }

    /**
     * Exports the text in italics
     * @return String in the output format
     */
    @Override
    public String export(IExporter exporter) {
        return exporter.export(this);
    }
}
