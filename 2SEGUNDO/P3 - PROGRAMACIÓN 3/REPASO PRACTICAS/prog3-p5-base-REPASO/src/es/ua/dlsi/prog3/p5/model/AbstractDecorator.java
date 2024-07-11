package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExportable;
import es.ua.dlsi.prog3.p5.export.IExporter;

/**
 * Common code for all decorators
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 25/10/22
 */
public abstract class AbstractDecorator implements IExportable {
    /**
     * Decorated element
     */
    protected IDocumentElement decoratedElement;

    /**
     * Constructor
     * @param documentElement Decorated element
     */
    protected AbstractDecorator(IDocumentElement documentElement) {
        this.decoratedElement = documentElement;
    }

    /**
     * Getter
     * @return Decorated element
     */
    public IDocumentElement getDecoratedElement() {
        return decoratedElement;
    }

    /**
     * By default it exports the contained element
     * @param exporter
     * @return
     */
    @Override
    public String export(IExporter exporter) {
        return decoratedElement.export(exporter);
    }
}
