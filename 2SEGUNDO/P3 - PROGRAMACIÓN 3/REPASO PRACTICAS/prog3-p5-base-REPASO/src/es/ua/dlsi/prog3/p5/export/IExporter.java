package es.ua.dlsi.prog3.p5.export;

import es.ua.dlsi.prog3.p5.model.*;

/**
 * This is the base class of any document exporter in any format
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 24/10/22
 */
public interface IExporter {
	
    String export(BoldTextDecorator boldTextDecorator);

    String export(InlineCode code);

    String export(CodeBlock codeBlock);

    String export(Heading heading);

    String export(HorizontalRule horizontalRule);

    String export(Image image);

    String export(ItalicsTextDecorator italicTextDecorator);

    String export(LinkParagraphContentDecorator linkParagraphContentDecorator);

    String export(OrderedListBlock orderedListBlock);

    String export(UnorderedListBlock unorderedListBlock);

    String export(Paragraph paragraph);

    String export(Document document);

    String export(AbstractTextContent abstractTextContent);
}
