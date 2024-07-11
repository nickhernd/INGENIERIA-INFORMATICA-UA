package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExporter;

public class StrikeThroughDecorator extends AbstractTextDecorator {
	 /**
     * Constructor
     *
     * @param text Text contents
     */
    public StrikeThroughDecorator(IText text) {
        super(text);
    }

    /**
     * Exports the bolded text
     * @return String in the output format
     */
    @Override
    public String export(IExporter exporter) {
        return exporter.export(this);
    }
}
