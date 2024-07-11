package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExporter;

import java.util.List;

/**
 * It contains a numbered list block
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public class OrderedListBlock extends AbstractListBlock {
    /**
     * It just creates the list container
     *
     * @param items List items
     */
    public OrderedListBlock(List<IBlock> items) {
        super(items);
    }

    /**
     * Generate a numbered list
     * @return Markdown code
     */
    @Override
    public String export(IExporter exporter) {
        return exporter.export(this);
    }
}
