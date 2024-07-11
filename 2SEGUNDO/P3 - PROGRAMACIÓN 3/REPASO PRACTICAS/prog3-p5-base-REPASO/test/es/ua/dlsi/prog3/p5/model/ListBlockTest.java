package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.html.HTMLExporter;
import es.ua.dlsi.prog3.p5.export.markdown.MarkdownExporter;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 24/10/22
 */
public class ListBlockTest {
    private List<IBlock> items;

    @Before
    public void setup() {
        items = new LinkedList<>();
        List<String> languages = new LinkedList<>();
        languages.add("Java");
        languages.add("C++");
        languages.add("C#");
        languages.add("Object pascal");
        languages.add("Javascript");
        languages.add("Python");

        for (String language: languages) {
            items.add(new Text(language));
        }
    }

    @Test
    public void testOrderedListMarkdown() {
        OrderedListBlock orderedListBlock = new OrderedListBlock(items);
        String expected = "1. Java\n" +
                "2. C++\n" +
                "3. C#\n" +
                "4. Object pascal\n" +
                "5. Javascript\n" +
                "6. Python\n";
        assertEquals(expected, orderedListBlock.export(new MarkdownExporter()));
    }

    @Test
    public void testOrderedListHTML() {
        OrderedListBlock orderedListBlock = new OrderedListBlock(items);
        String expected = "<ol>\n" +
                "<li>Java</li>\n" +
                "<li>C++</li>\n" +
                "<li>C#</li>\n" +
                "<li>Object pascal</li>\n" +
                "<li>Javascript</li>\n" +
                "<li>Python</li>\n" +
                "</ol>\n";
        assertEquals(expected, orderedListBlock.export(new HTMLExporter()));
    }


    @Test
    public void testUnorderedListMarkdown() {
        UnorderedListBlock unorderedListBlock = new UnorderedListBlock(items);
        String expected = "- Java\n" +
                "- C++\n" +
                "- C#\n" +
                "- Object pascal\n" +
                "- Javascript\n" +
                "- Python\n";
        assertEquals(expected, unorderedListBlock.export(new MarkdownExporter()));
    }

    @Test
    public void testUnorderedListHTML() {
        UnorderedListBlock unorderedListBlock = new UnorderedListBlock(items);
        String expected = "<ul>\n" +
                "<li>Java</li>\n" +
                "<li>C++</li>\n" +
                "<li>C#</li>\n" +
                "<li>Object pascal</li>\n" +
                "<li>Javascript</li>\n" +
                "<li>Python</li>\n" +
                "</ul>\n";
        assertEquals(expected, unorderedListBlock.export(new HTMLExporter()));
    }

    /*@Test void testNestedListsMarkdown() {
        String [] provinces = new String[] {"Alicante", "Murcia", "Valencia"};
        String [][] cities = new String [][] {
                {"Alicante", "Elche"},
                {"Murcia", "Cartagena"},
                {"Valencia", "Gandía"}
        };

        //TODO
        //OrderedListBlock orderedListBlock = new OrderedListBlock();
    }*/

}
