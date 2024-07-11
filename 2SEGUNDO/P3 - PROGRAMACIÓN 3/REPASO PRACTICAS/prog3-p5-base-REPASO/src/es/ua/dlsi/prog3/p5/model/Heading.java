package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExporter;

/**
 * Headings of any level starting with 1 (Heading 1, Heading 2, etc...).
 * They cannot have another decoration. This is why they are final
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public final class Heading extends AbstractTextContent implements IBlock {
    /**
     * Starting with 1
     */
    private final int level;

    /**
     * Constructor
     * @param text Heading text
     * @param level Level starting with 1
     */
    public Heading(String text, int level) throws EditorException {
        super(text);
        if (level < 1) {
            throw new EditorException("Invalid heading level " + level + ", it must start with 1");
        }
        this.level = level;
    }

    /**
     * Getter
     * @return The level
     */
    public int getLevel() {
        return level;
    }

    @Override
    public String export(IExporter exporter) {
        return exporter.export(this);
    }
}
