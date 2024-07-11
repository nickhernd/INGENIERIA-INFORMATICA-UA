package es.ua.dlsi.prog3.p5.export.markdown;

import es.ua.dlsi.prog3.p5.export.IExporter;
import es.ua.dlsi.prog3.p5.model.*;

/**
 * It exports the document elements to markdown format 
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 25/10/22
 */
public class MarkdownExporter implements IExporter {
    @Override
    public String export(BoldTextDecorator boldTextDecorator) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("**");
        stringBuilder.append(boldTextDecorator.getDecoratedElement().export(this));
        stringBuilder.append("**");
        return stringBuilder.toString();
    }

    @Override
    public String export(InlineCode code) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('`');
        stringBuilder.append(code.getText());
        stringBuilder.append('`');
        return stringBuilder.toString();
    }

    @Override
    public String export(CodeBlock codeBlock) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("```");
        if (codeBlock.hasLanguage()) {
            stringBuilder.append(codeBlock.getLanguage());
        }
        stringBuilder.append('\n');
        stringBuilder.append(codeBlock.getText());
        stringBuilder.append('\n');
        stringBuilder.append("```");
        return stringBuilder.toString();
    }

    @Override
    public String export(Heading heading) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<heading.getLevel(); i++) {
            stringBuilder.append('#');
        }
        stringBuilder.append(heading.getText());
        return stringBuilder.toString();
    }

    @Override
    public String export(HorizontalRule horizontalRule) {
            return "\n-----\n";
    }

    @Override
    public String export(Image image) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("![");
        if (image.hasAlt()) {
            stringBuilder.append(image.getAlt());
        }
        stringBuilder.append("](");
        stringBuilder.append(image.getImagePath());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public String export(ItalicsTextDecorator italicTextDecorator) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("*");
        stringBuilder.append(italicTextDecorator.getDecoratedElement().export(this));
        stringBuilder.append("*");
        return stringBuilder.toString();
    }

    @Override
    public String export(LinkParagraphContentDecorator linkParagraphContentDecorator) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        stringBuilder.append(linkParagraphContentDecorator.getDecoratedElement().export(this));
        stringBuilder.append(']');
        stringBuilder.append('(');
        stringBuilder.append(linkParagraphContentDecorator.getLink());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    @Override
    public String export(OrderedListBlock orderedListBlock) {
        StringBuilder stringBuilder = new StringBuilder();
        int i=1;
        for (IDocumentElement block: orderedListBlock.getItems()) {
            stringBuilder.append(i++);
            stringBuilder.append('.');
            stringBuilder.append(' ');
            stringBuilder.append(block.export(this));
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    @Override
    public String export(UnorderedListBlock unorderedListBlock) {
        StringBuilder stringBuilder = new StringBuilder();
        for (IDocumentElement block: unorderedListBlock.getItems()) {
            stringBuilder.append('-');
            stringBuilder.append(' ');
            stringBuilder.append(block.export(this));
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    @Override
    public String export(Paragraph paragraph) {
        return paragraph.exportConcatenatedContents(this, null);
    }

    @Override
    public String export(Document document) {
        return document.exportConcatenatedContents(this, "\n");
    }

    @Override
    public String export(AbstractTextContent abstractTextContent) {
        return abstractTextContent.getText();
    }
}
