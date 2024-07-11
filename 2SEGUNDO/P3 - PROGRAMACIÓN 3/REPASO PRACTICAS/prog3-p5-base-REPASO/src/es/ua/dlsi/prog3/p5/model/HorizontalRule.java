package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExporter;

/**
 * It draws an horitontal line
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public class HorizontalRule implements IMark {
    /**
     * It generates the markdown for the horizontal line
     * @return Markdown code
     */
    @Override
    public String export(IExporter exporter) {
        return exporter.export(this);
    }
}
