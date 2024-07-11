package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExportable;
import es.ua.dlsi.prog3.p5.export.IExporter;

/**
 * Contents of the editor
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public class Document extends AbstractComposite implements IExportable {
    /**
     * It adds a new block to the document
     * @param block
     */
    public void add(IBlock block) {
        addItem(block);
    }

    @Override
    public String export(IExporter exporter) {
        return exporter.export(this);
    }
}
