package es.ua.dlsi.prog3.p5.export;

/**
 * It adds the export behaviour
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 24/10/22
 */
public interface IExportable {
    /**
     * It delegates the export procedure to the IExporter
     * @param exporter Object that implement the specific exporting strategy
     * @return Exported string
     */    
    String export(IExporter exporter);
}
