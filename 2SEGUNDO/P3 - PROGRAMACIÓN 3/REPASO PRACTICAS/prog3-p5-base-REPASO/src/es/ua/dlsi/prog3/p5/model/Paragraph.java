package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExporter;

import java.util.List;

/**
 * Document paragraph
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 24/10/22
 */
public class Paragraph extends AbstractComposite implements IBlock {
    /**
     * Constructor
     * @param paragraphContentList Contents of the paragraph
     */
    public Paragraph(List<IParagraphContent> paragraphContentList) {
        for (IParagraphContent paragraphContent: paragraphContentList) {
            this.addItem(paragraphContent);
        }
    }

    /**
     * Constructor
     * @param paragraphContents None or several paragraph contents
     */    
    public Paragraph(IParagraphContent ... paragraphContents) {
        for (IParagraphContent paragraphContent: paragraphContents) {
            this.addItem(paragraphContent);
        }
    }

    /**
     * It delegates the paragraph export to the IExporter
     * @param exporter Object that implement the specific exporting strategy
     * @return Exported string
     */    
    @Override
    public String export(IExporter exporter) {
        return exporter.export(this);
    }
}
