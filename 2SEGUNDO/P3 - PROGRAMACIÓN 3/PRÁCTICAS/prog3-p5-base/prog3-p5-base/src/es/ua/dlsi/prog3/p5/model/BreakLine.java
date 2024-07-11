package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.IExporter;

public class BreakLine implements IMark{
	@Override
    public String export(IExporter exporter) {
        return exporter.export(this);
    }
}
