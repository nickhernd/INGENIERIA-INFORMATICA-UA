package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExporter;

/**
 * Image paragraph contents
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public class Image implements IParagraphContent {
    /**
     * Path or URL of the image
     */
    private String imagePath;
    /**
     * Alternative text for broken images
     */
    private String alt;

    /**
     * Constructor
     * @param imagePath Path or URL of the image
     * @param alt Alternative text for broken images
     */
    public Image(String imagePath, String alt) {
        this.imagePath = imagePath;
        this.alt = alt;
    }

    /**
     * Getter
     * @return The image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Checks the availability of the alternative content
     * @return True if alt property is not null
     */
    public boolean hasAlt() {
        return alt != null;
    }
    /**
     * Getter
     * @return The alternative content
     */
    public String getAlt() {
        return alt;
    }

    /**
     * Render of the image
     * @return Markdown code
     */
    @Override
    public String export(IExporter exporter) {
        return exporter.export(this);
    }
}
