package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExporter;

/**
 * Block containing source code
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public class CodeBlock extends AbstractTextContent implements IBlock{
    /**
     * Optional language
     */
    private final String language;

    /**
     * Constructor
     * @param text Source code
     * @param language Optional text
     */
    public CodeBlock(String text, String language) {
        super(text);
        this.language = language;
    }
    /**
     * Constructor
     * @param text Source code
     */
    public CodeBlock(String text) {
        this(text, null);
    }

    /**
     * Getter
     * @return The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * It asks for the availability of the language property
     * @return True if language is not null
     */
    public boolean hasLanguage() {
        return language != null;
    }

    @Override
    public String export(IExporter exporter) {
        return exporter.export(this);
    }
}
