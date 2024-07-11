package es.ua.dlsi.prog3.p5.client;

import es.ua.dlsi.prog3.p5.export.html.HTMLExporter;
import es.ua.dlsi.prog3.p5.export.markdown.MarkdownExporter;
import es.ua.dlsi.prog3.p5.model.AbstractTextDecorator;
import es.ua.dlsi.prog3.p5.model.BoldTextDecorator;
import es.ua.dlsi.prog3.p5.model.CodeBlock;
import es.ua.dlsi.prog3.p5.model.Document;
import es.ua.dlsi.prog3.p5.model.EditorException;
import es.ua.dlsi.prog3.p5.model.Heading;
import es.ua.dlsi.prog3.p5.model.HorizontalRule;
import es.ua.dlsi.prog3.p5.model.IBlock;
import es.ua.dlsi.prog3.p5.model.IDocumentElement;
import es.ua.dlsi.prog3.p5.model.Image;
import es.ua.dlsi.prog3.p5.model.ItalicsTextDecorator;
import es.ua.dlsi.prog3.p5.model.LinkParagraphContentDecorator;
import es.ua.dlsi.prog3.p5.model.OrderedListBlock;
import es.ua.dlsi.prog3.p5.model.Paragraph;
import es.ua.dlsi.prog3.p5.model.Quote;
import es.ua.dlsi.prog3.p5.model.StrikeThroughDecorator;
import es.ua.dlsi.prog3.p5.model.Text;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This test evaluates the correct creation of an object hierarchy to represent a document
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 31/10/22
 */
public class ExampleDocumentCreatorTest {
    private Document document;
    private Heading heading1;
    private Paragraph paragraph;
	private Quote quote;
	private Heading heading2;
	private Paragraph paragraph2;
	private Heading heading3;
	private Paragraph paragraph3;
	private List<IDocumentElement> items;

    private void compareResults(String fileWithExpectedContent, String result) {
        try {
            URL url = this.getClass().getResource(fileWithExpectedContent);
            if (url == null) {
                throw new RuntimeException("Cannot find file '" + fileWithExpectedContent + "'");
            }
            java.nio.file.Path path = java.nio.file.Paths.get(url.toURI());
            List<String> lines = Files.readAllLines(path);
            StringBuilder stringBuilder = new StringBuilder();
            for (String line : lines) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
            String fileContent = stringBuilder.toString().trim(); // trim to remove trailing spaces
            assertEquals(fileContent, result.trim());
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("This should not fail", e);
        }
    }

    private boolean compareHeadings(Heading h1, Heading h2) {
    	return h1.getLevel() == h2.getLevel() && h1.getText().equals(h2.getText());    	
    }
    
    private boolean compareParagraphs(Paragraph p1, Paragraph p2) {
    	return p1.getItems().length==p2.getItems().length;
    }

    private boolean compareTexts(Text t1, Text t2) {
    	return t1.getText().equals(t2.getText());
    }

	private boolean compareQuotes(Quote q1, Quote quote2) {
		return q1.getItems().length==quote2.getItems().length;
	}

    @Before
    public void setup()  {
        ExampleDocumentCreator exampleDocumentCreator = new ExampleDocumentCreator();
        document = exampleDocumentCreator.createExample();

        try {
        	Class<?> clazz = document.getClass().getSuperclass();
        	
        	Field itemsField = clazz.getDeclaredField("items");
        	itemsField.setAccessible(true);
        	items = (List<IDocumentElement>) itemsField.get(document);

        	heading1 = new Heading("Heading", 1);
			paragraph = new Paragraph(new Text("Some text to introduce the article"));
			quote = new Quote(new Text("Something someone said:"), new LinkParagraphContentDecorator(new Text("original cite site"), "https://www.somesite.edu"));
			heading2 = new Heading("First section", 2);
			paragraph2 = new Paragraph(new Text("Example of text decorators:"));
            heading3 = new Heading("Code block", 3);
            paragraph3 = new Paragraph(new Image("https://web.ua.es/secciones-ua/images/layout/logo-ua.jpg", "UA logo"));
            
        } catch (Exception e) {
			e.printStackTrace();
            throw new RuntimeException("This exception should never happen", e);
		}
    }

    
    @Test
    public void testHeading1() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	assertTrue("Heading 1",compareHeadings(heading1, (Heading)items.get(0)));
    	
    }
    
    @Test
    public void testParagraph1() {
    	Paragraph par1 = (Paragraph) items.get(1);
    	assertTrue(compareParagraphs(par1, paragraph));
    	Text t1 = (Text) par1.getItems()[0];
    	Text t2 = (Text) paragraph.getItems()[0];
    	assertTrue(compareTexts(t1, t2));
    }
   
    @Test
    public void testQuote() {
    	Quote q1 = (Quote) items.get(2);
    	assertTrue(compareQuotes(q1, quote));
    	IDocumentElement[] elements = q1.getItems();
    	assertTrue(elements[0] instanceof Text);
    	assertTrue(elements[1] instanceof LinkParagraphContentDecorator);
    	Text t1 = (Text) elements[0];
    	Text t2 = (Text) quote.getItems()[0];
    	assertTrue(compareTexts(t1, t2));
    }


    @Test
    public void testHeading2() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	assertTrue("Heading 2",compareHeadings(heading2, (Heading)items.get(3)));    	
    }

    @Test
    public void testParagraph2() {
    	// paragraph2 = new Paragraph(new Text("Example of text decorators:"));
    	Paragraph par2 = (Paragraph) items.get(4);
    	assertTrue(compareParagraphs(par2, paragraph2));
    	Text t1 = (Text) par2.getItems()[0];
    	Text t2 = (Text) paragraph2.getItems()[0];
    	assertTrue(compareTexts(t1, t2));
    }
    
    @Test
    public void testOrderedListBlock() {
    	OrderedListBlock olb = (OrderedListBlock) items.get(5);
    	IDocumentElement[] elements = olb.getItems();
    	assertTrue(elements[0] instanceof Text);
    	assertTrue(elements[1] instanceof ItalicsTextDecorator);
    	assertTrue(elements[2] instanceof BoldTextDecorator);
    	assertTrue(elements[3] instanceof StrikeThroughDecorator);
    	assertTrue(elements[4] instanceof ItalicsTextDecorator);
    	assertTrue(elements[5] instanceof Paragraph);
    	
    	ItalicsTextDecorator italics = (ItalicsTextDecorator) elements[4];
    	assertTrue(italics.getDecoratedElement() instanceof BoldTextDecorator);
    	AbstractTextDecorator decorator = (AbstractTextDecorator) italics.getDecoratedElement();
    	assertTrue(decorator.getDecoratedElement() instanceof StrikeThroughDecorator);
    	decorator = (AbstractTextDecorator) decorator.getDecoratedElement();
    	assertTrue(decorator.getDecoratedElement() instanceof Text);

    }

    @Test
    public void testHeading3() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	assertTrue("Heading 3",compareHeadings(heading3, (Heading)items.get(6)));    	
    }

    @Test
    public void testCodeblock() {
    	assertTrue(items.get(7) instanceof CodeBlock);
    	CodeBlock cb = (CodeBlock) items.get(7);
    	assertEquals("CodeBlock","java",cb.getLanguage());
    }
    
    @Test
    public void testHRule() {
    	assertTrue(items.get(8) instanceof HorizontalRule);    	
    }
    
    @Test
    public void testParagraph3() {
    	Paragraph par3 = (Paragraph) items.get(9);
    	assertTrue(compareParagraphs(par3, paragraph3));
    	assertTrue(par3.getItems()[0] instanceof Image);
    	Image t1 = (Image) par3.getItems()[0];
    	assertTrue(t1.hasAlt());
    }

    /*
    @Test
    public void testHTML() {
        String html = document.export(new HTMLExporter());
        compareResults("example_document.html", html);
    }

    @Test
    public void testMarkdown() {
        String md = document.export(new MarkdownExporter());
        compareResults("example_document.md", md);
    }
*/

}
