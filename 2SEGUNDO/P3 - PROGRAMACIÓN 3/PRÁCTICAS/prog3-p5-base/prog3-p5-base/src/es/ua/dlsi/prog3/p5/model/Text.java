package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExporter;

/**
 * Text paragraph contents
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public class Text extends AbstractTextContent implements IText {
    /**
     * Constructor
     * @param text Text contents
     */
    public Text(String text) {
        super(text);
    }
    

    /**
     * Export the Text
     * @param exporter Object that implement the specific exporting strategy
     * @return The specific exported format
     */
    @Override
    public String export(IExporter exporter) {
        return exporter.export(this);
    }    
}
