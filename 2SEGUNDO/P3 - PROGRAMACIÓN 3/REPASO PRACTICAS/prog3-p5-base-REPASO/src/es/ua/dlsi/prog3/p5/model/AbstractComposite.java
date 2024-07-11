package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExporter;

import java.util.LinkedList;
import java.util.List;

/**
 * It contains a group of document elements of any kind that uses the Composite design pattern
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 25/10/22
 */
public abstract class AbstractComposite implements IDocumentElement {
    /**
     * List of contained items. Note an item can be another AbstractComposite object
     */
    private List<IDocumentElement> items;

    /**
     * It initializes the items list
     */
    protected AbstractComposite() {
        this.items = new LinkedList<>();
    }

    /**
     * To be used by descendent classes
     * @param documentElement Item to be added
     */
    protected void addItem(IDocumentElement documentElement) {
        this.items.add(documentElement);
    }

    /**
     * Getter
     * @return Shallow copy of the items
     */
    public IDocumentElement[] getItems() {
        return items.toArray(new IDocumentElement[]{});
    }

    /**
     * It concatenates the exported format of contained items
     * @param exporter Specific exporter
     * @param separator String that separates each item. It may be null
     * @return Contatenated contents
     */
    public String exportConcatenatedContents(IExporter exporter, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        for (IDocumentElement documentElement: this.items) {
            if (stringBuilder.length() > 0 && separator != null) {
                stringBuilder.append(separator);
            }
            stringBuilder.append(documentElement.export(exporter));
        }
        return stringBuilder.toString();
    }
}
