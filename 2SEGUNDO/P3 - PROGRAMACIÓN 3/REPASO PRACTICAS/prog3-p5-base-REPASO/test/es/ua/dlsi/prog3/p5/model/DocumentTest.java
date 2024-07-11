package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.markdown.MarkdownExporter;
import es.ua.dlsi.prog3.p5.export.html.HTMLExporter;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This test creates a complete document and checks the markdown rendering
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 24/10/22
 */
public class DocumentTest {
    private Document document;

    @Before
    public void setup() throws EditorException {
        document = new Document();
        document.add(new Heading("PROG3 test", 1));
        document.add(new Paragraph(new Text("This is a test of a simple document")));
        List<IParagraphContent> paragraphContentList = Arrays.asList(new Text("The following UML diagram describes the system to be implemented:"),
                new Image("uml.jpg", "UML diagram"));
        document.add(new Paragraph(paragraphContentList));
        document.add(new HorizontalRule());
        document.add(new Heading("Tasks", 2));
        List<IBlock> tasks = Arrays.asList(new Text("Read the assignment"),
                new Paragraph(new Text("Implement and "), new BoldTextDecorator(new Text("document code"))),
                new Paragraph(new Text("Test using "), new ItalicsTextDecorator(new Text("JUnit"))));
        document.add(new OrderedListBlock(tasks));
    }

    @Test
    public void renderMarkdown() {
        String expectedMarkDown = "#PROG3 test\n" +
                "This is a test of a simple document\n" +
                "The following UML diagram describes the system to be implemented:![UML diagram](uml.jpg)\n" +
                "\n" +
                "-----\n" +
                "\n" +
                "##Tasks\n" +
                "1. Read the assignment\n" +
                "2. Implement and **document code**\n" +
                "3. Test using *JUnit*\n";
        assertEquals(expectedMarkDown, document.export(new MarkdownExporter()));
    }

    @Test
    public void renderHTML() {
        String expectedMarkDown =
            "<html>\n" +
            "<body>\n" +
            "<h1>PROG3 test</h1>\n" +
            "<p>This is a test of a simple document</p>\n" +
                    "<p>The following UML diagram describes the system to be implemented:<img src='uml.jpg' alt='UML diagram'/></p>\n" +
                "<hr>\n" +
                "<h2>Tasks</h2>\n" +
                    "<ol>\n" +
                "<li>Read the assignment</li>\n" +
                "<li><p>Implement and <strong>document code</strong></p></li>\n" +
                "<li><p>Test using <em>JUnit</em></p></li>\n" +
                "</ol>\n" +
                    "</body>\n" +
                    "</html>"
                ;
        assertEquals(expectedMarkDown, document.export(new HTMLExporter()));
    }
}
