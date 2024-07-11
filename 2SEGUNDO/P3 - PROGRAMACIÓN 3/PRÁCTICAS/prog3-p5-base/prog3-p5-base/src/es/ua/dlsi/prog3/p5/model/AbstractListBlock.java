package es.ua.dlsi.prog3.p5.model;

import java.util.List;

/**
 * It wraps several items in the form of a list
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public abstract class AbstractListBlock extends AbstractComposite implements IBlock {
    /**
     * It adds all contents to the composite
     * @param items The items that will be added to the composite
     */
    protected AbstractListBlock(List<IBlock> items) {
        for (IBlock block: items) {
            this.addItem(block);
        }
    }
}
