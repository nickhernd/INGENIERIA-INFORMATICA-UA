package es.ua.dlsi.prog3.p5.client;

import java.util.ArrayList;
import java.util.List;

import es.ua.dlsi.prog3.p5.export.html.HTMLExporter;
import es.ua.dlsi.prog3.p5.export.markdown.MarkdownExporter;
import es.ua.dlsi.prog3.p5.model.*;

/**
 * This class is used for the evaluation of the assignment
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 31/10/22
 */
public class ExampleDocumentCreator {

    /**
     * --- IMPLEMENT this method ---
     *
     * @return Document object
     * @throws EditorException
     */
	public Document createExample() {
	    Document document = new Document();

	    try {
	        Heading heading1 = new Heading("Heading", 1);
	        Paragraph introduction = new Paragraph(new Text("Some text to introduce the article"));
	        Quote quote = new Quote(new Text("Something someone said:"), new LinkParagraphContentDecorator(new Text("original cite site"), "https://www.somesite.edu"));

	        Heading heading2 = new Heading("First section", 2);
	        
	        Paragraph decoratorsExample = new Paragraph(new Text("Example of text decorators:"));
	        
	        List<IBlock> items = new ArrayList<>();
	        
	        items.add(new Text("Raw text"));
	        Text t = new Text("Italics");
	        items.add(new ItalicsTextDecorator(t));
	        Text t1 = new Text("Bold");
	        items.add(new BoldTextDecorator(t1));
	        Text t2 = new Text("Strike through");
	        items.add(new StrikeThroughDecorator(t2));
	        Text t3 = new Text("The three above");
	        items.add(new ItalicsTextDecorator(new BoldTextDecorator(new StrikeThroughDecorator(t3))));
	        items.add(new Paragraph(new Text("Raw text inside a paragraph")));
	        
	        OrderedListBlock decoratorsList = new OrderedListBlock(items);
	        
	        
	        Heading heading3 = new Heading("Code block", 3);
	        CodeBlock code = new CodeBlock("class PROG3 {}", "java");
            Paragraph paragraph3 = new Paragraph(new Image("https://web.ua.es/secciones-ua/images/layout/logo-ua.jpg", "UA logo"));

	        document.add(heading1);
	        document.add(introduction);
	        document.add(quote);
	        document.add(heading2);
	        document.add(decoratorsExample);
	        document.add(decoratorsList); 
	        document.add(heading3);
	        document.add(code);
	        document.add(new HorizontalRule());
	        document.add(paragraph3);

	       // MarkdownExporter markdownExporter = new MarkdownExporter();
	        //HTMLExporter htmlExporter = new HTMLExporter();

	        

	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }

	    return document;
	}

}


