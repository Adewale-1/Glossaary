import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program to test static methods in Glossary class.
 *
 * @author Adewale Adenle
 *
 */
public class GlossaryTest {

    /*
     * buildDictionary tests
     */
    @Test
    // Test case 1: Normal input
    public void buildDictionaryTest1() {
        SimpleReader input = new SimpleReader1L("data/dictTest1.txt");
        Map<String, String> glossary = Glossary.buildDictionary(input);
        Map<String, String> expected = new Map1L<>();
        expected.add("term1", "definition1");
        expected.add("term2", "definition2");
        expected.add("term3", "definition3");
        assertEquals(expected, glossary);
        input.close();
    }

    @Test
    // Test case 2: Empty input file
    public void dictionaryFromInputTest2() {
        SimpleReader input = new SimpleReader1L("data/empty.txt");
        Map<String, String> dict = Glossary.buildDictionary(input);
        Map<String, String> expected = new Map1L<>();
        assertEquals(expected, dict);
        assertTrue(input.isOpen());
        assertTrue(input.atEOS());
        input.close();
    }

    @Test
    // Test case 3: Normal input with multiple terms and definitions
    public void dictionaryFromInputTest3() {
        SimpleReader input = new SimpleReader1L("data/dictTest3.txt");
        Map<String, String> dict = Glossary.buildDictionary(input);
        Map<String, String> expected = new Map1L<>();
        expected.add("meaning",
                "something that one wishes to convey, especially by language");
        expected.add("term", "a word whose definition is in a glossary");
        expected.add("word",
                "a string of characters in a language, which has at least one character");
        expected.add("definition",
                "a sequence of words that gives meaning to a term");
        expected.add("glossary",
                "a list of difficult or specialized terms, with their definitions,"
                        + " usually near the end of a book");
        expected.add("language",
                "a set of strings of characters, each of which has meaning");
        expected.add("book", "a printed or written literary work");
        expected.add("calculus", "used in math");
        expected.add("flutter", "used for mobile development");
        expected.add("camel", "a desert creature");
        assertEquals(expected, dict);
        assertTrue(input.isOpen());
        assertTrue(input.atEOS());
        input.close();
    }

    /*
     * extractLowestPair tests
     */
    @Test
    // Test case 1: Map with only one pair
    public void extractLowestPairTest() {
        Map<String, String> m1 = new Map1L<>();
        m1.add("", "");
        Map<String, String> m2 = new Map1L<>();
        m2.add("", "");
        String term = "";
        String definition = "";
        m2.remove(term);
        Pair<String, String> lowest = Glossary.extractLowestPair(m1);
        assertEquals(term, lowest.key());
        assertEquals(definition, lowest.value());
        assertEquals(m2, m1);
    }

    @Test
    // Test case 2: Map with multiple pairs
    public void extractLowestPairTest2() {
        Map<String, String> m1 = new Map1L<>();
        m1.add("term1", "definition1");
        m1.add("term2", "definition2");
        m1.add("term3", "definition3");
        Map<String, String> m2 = new Map1L<>();
        m2.add("term1", "definition1");
        m2.add("term2", "definition2");
        m2.add("term3", "definition3");
        String term = "term1";
        String definition = "definition1";
        m2.remove(term);
        Pair<String, String> lowest = Glossary.extractLowestPair(m1);
        assertEquals(term, lowest.key());
        assertEquals(definition, lowest.value());
        assertEquals(m2, m1);
    }

    @Test
    // Test case 1: Empty map
    public void buildSortedQueueTest1() {
        SimpleReader input1 = new SimpleReader1L("data/empty.txt");
        SimpleReader input2 = new SimpleReader1L("data/empty.txt");
        Map<String, String> map = Glossary.buildDictionary(input1);
        Map<String, String> expectedMap = Glossary.buildDictionary(input2);
        input1.close();
        input2.close();
        Queue<String> sorted = Glossary.buildSortedQueue(map);
        Queue<String> expected = new Queue1L<>();
        assertEquals(expectedMap, map);
        assertEquals(expected, sorted);
    }

    /*
     * buildSortedQueue tests
     */
    @Test
    // Test case 2: Normal input
    public void buildSortedQueueTest2() {
        SimpleReader input = new SimpleReader1L("data/dictTest1.txt");
        Map<String, String> glossary = Glossary.buildDictionary(input);
        Queue<String> sortedQueue = Glossary.buildSortedQueue(glossary);
        Queue<String> expected = new Queue1L<>();
        expected.enqueue("term1");
        expected.enqueue("term2");
        expected.enqueue("term3");
        assertEquals(expected, sortedQueue);
        input.close();
    }

    @Test
    // Test case 3: Normal input with multiple terms
    public void buildSortedQueueTest3() {
        SimpleReader input1 = new SimpleReader1L("data/dictTest3.txt");
        SimpleReader input2 = new SimpleReader1L("data/dictTest3.txt");
        Map<String, String> map = Glossary.buildDictionary(input1);
        Map<String, String> expectedMap = Glossary.buildDictionary(input2);
        input1.close();
        input2.close();
        Queue<String> sorted = Glossary.buildSortedQueue(map);
        Queue<String> expected = new Queue1L<>();
        expected.enqueue("book");
        expected.enqueue("calculus");
        expected.enqueue("camel");
        expected.enqueue("definition");
        expected.enqueue("flutter");
        expected.enqueue("glossary");
        expected.enqueue("language");
        expected.enqueue("meaning");
        expected.enqueue("term");
        expected.enqueue("word");
        assertEquals(expectedMap, map);
        assertEquals(expected, sorted);
    }

    @Test
    // Test case 1: Empty queue
    public void createIndexHTMLTest1() {
        Queue<String> expected = new Queue1L<>();
        SimpleWriter out = new SimpleWriter1L("data/IndexTestFile.txt");
        Glossary.createIndexHTML(expected, out);
        assertTrue(out.isOpen());
        out.close();
    }

    @Test
    // Test case 2: Normal input
    public void createIndexHTMLTest2() {
        Queue<String> terms = new Queue1L<>();
        terms.enqueue("boy");
        terms.enqueue("rat");
        terms.enqueue("drive");
        terms.enqueue("combination");
        terms.enqueue("enqueue");
        terms.enqueue("house");
        terms.enqueue("of");
        terms.enqueue("cards");
        terms.enqueue("language");
        terms.enqueue("meaning");
        terms.enqueue("ohio");
        terms.enqueue("state");
        terms.enqueue("university");
        terms.enqueue("columbus");
        terms.enqueue("winter");
        String testFilePath = "data/IndexTestFile2.txt";
        SimpleWriter out = new SimpleWriter1L(testFilePath);
        Glossary.createIndexHTML(terms, out);
        out.close();
        // Read the contents of the generated file
        SimpleReader inFile = new SimpleReader1L(testFilePath);
        StringBuilder fileContents = new StringBuilder();
        while (!inFile.atEOS()) {
            fileContents.append(inFile.nextLine());
            fileContents.append("\n");
        }
        inFile.close();
        // Read the expected contents from the "expectedIndex3.txt" file
        String expectedFilePath = "data/expectedIndex3.txt";
        SimpleReader expectedFile = new SimpleReader1L(expectedFilePath);
        StringBuilder expectedContents = new StringBuilder();
        while (!expectedFile.atEOS()) {
            expectedContents.append(expectedFile.nextLine());
            expectedContents.append("\n");
        }
        expectedFile.close();
        // Compare the file contents with the expected contents
        assertEquals(expectedContents.toString(), fileContents.toString());
    }

    /*
     * createUniqueChars tests
     */
    @Test
    // Test case 1: Normal input
    public void createUniqueCharsTest1() {
        String input = "hello world";
        Set<Character> charSet = new Set1L<>();
        Glossary.createUniqueChars(input, charSet);
        Set<Character> expected = new Set1L<>();
        expected.add('h');
        expected.add('e');
        expected.add('l');
        expected.add('o');
        expected.add(' ');
        expected.add('w');
        expected.add('r');
        expected.add('d');
        assertEquals(expected, charSet);
    }

    /*
     * Test case for createUniqueChars method This test case checks if the
     * method creates a set of unique characters from the given input string and
     * adds them to the given set.
     */
    @Test
    public void createUniqueCharsTest2() {
        String input = "abcde";
        // Set to store unique characters
        Set<Character> charSet = new Set1L<>();
        // Call to the method being tested
        Glossary.createUniqueChars(input, charSet);
        // Expected output
        Set<Character> expected = new Set1L<>();
        expected.add('a');
        expected.add('b');
        expected.add('c');
        expected.add('d');
        expected.add('e');
        // Assert if the expected output matches the actual output
        assertEquals(expected, charSet);
    }

    /*
     * Test cases for getNextWordOrSeparator method These test cases check if
     * the method returns the correct word or separator from the given text,
     * starting from the given position, considering the given set of
     * separators.
     */
    @Test
    // Test case 1: Check if the method returns the correct word from the given text
    public void getNextWordOrSeparatorTest1() {
        String text = "hello, world!";
        // Set of separators
        Set<Character> separators = new Set1L<>();
        Glossary.createUniqueChars(".,!?", separators);
        String result = Glossary.getNextWordOrSeparator(text, 0, separators);
        assertEquals("hello", result);
    }

    @Test
    // Test case 2: Check if the method returns the correct separator from the given text
    public void getNextWordOrSeparatorTest2() {
        String text = "hello, world!";
        Set<Character> separators = new Set1L<>();
        Glossary.createUniqueChars(".,!?", separators);
        // Call to the method being tested
        String result = Glossary.getNextWordOrSeparator(text, 5, separators);
        assertEquals(",", result);
    }

    @Test
    // Test case 3: Check if the method returns the correct word when the
    // starting position is in the middle of a separator
    public void getNextWordOrSeparatorTest3() {
        String text = "hello, world!";
        Set<Character> separators = new Set1L<>();
        Glossary.createUniqueChars(".,!?", separators);
        // Call to the method being tested
        String result = Glossary.getNextWordOrSeparator(text, 6, separators);
        assertEquals(" world", result);
    }

    /*
     * Test cases for getDefinitionWithReferences method These test cases check
     * if the method returns the correct definition string with HTML links for
     * the terms present in the given set of terms.
     */

    @Test
    // Test case 1: Check if the method returns the correct definition with
    // HTML links for the given terms
    public void getDefinitionWithReferencesTest1() {
        String definition = "term1 is related to term2";
        Set<String> terms = new Set1L<>();
        terms.add("term1");
        terms.add("term2");
        // Call to the method being tested
        String result = Glossary.getDefinitionWithReferences(definition, terms);
        // Expected output
        String expected = "<a href=\"term1.html\">term1</a> is related to <a "
                + "href=\"term2.html\">term2</a>";
        assertEquals(expected, result);
    }

    @Test
    // Test case 2: Check if the method returns the correct definition with
    // HTML links for the given terms
    public void getDefinitionWithReferencesTest2() {
        String definition = "termA and termB are related";
        // Set of terms
        Set<String> terms = new Set1L<>();
        terms.add("termA");
        terms.add("termB");
        // Call to the method being tested
        String result = Glossary.getDefinitionWithReferences(definition, terms);
        // Expected output
        String expected = "<a href=\"termA.html\">termA</a> "
                + "and <a href=\"termB.html\">termB</a> are related";
        assertEquals(expected, result);
    }

    @Test
    // Test case 3: Check if the method returns the correct definition when
    // there are no to other terms in the definition
    public void getDefinitionWithReferencesTest3() {
        String definition = "This is a definition without any references.";
        Set<String> terms = new Set1L<>();
        terms.add("term1");
        terms.add("term2");
        String result = Glossary.getDefinitionWithReferences(definition, terms);
        // Assert if the expected output matches the actual output
        assertEquals(definition, result);
    }

    /*
     * Test cases for createTermHTML method These test cases check if the method
     * generates the correct HTML file for the given term and its definition
     * from the given dictionary.
     */
    @Test
    // Test case 1: Check if the method generates the correct HTML file for
    // the given term and its definition
    public void createTermHTMLTest1() {
        SimpleReader inputs = new SimpleReader1L("data/dictTest3.txt");
        // Build the dictionary
        Map<String, String> map = Glossary.buildDictionary(inputs);
        String input = "book";
        String outputFile = "data/Index4.txt";
        // Create the output file
        SimpleWriter out = new SimpleWriter1L(outputFile);
        Glossary.createTermHTML(input, map, out);
        out.close();

        // Read the content of the generated file
        SimpleReader generatedFileReader = new SimpleReader1L(outputFile);
        StringBuilder generatedFileContent = new StringBuilder();
        while (!generatedFileReader.atEOS()) {
            generatedFileContent.append(generatedFileReader.nextLine());
        }
        generatedFileReader.close();
        // Read the content of the expected file
        SimpleReader expectedFileReader = new SimpleReader1L(
                "data/expectedIndex4.txt");
        StringBuilder expectedFileContent = new StringBuilder();
        while (!expectedFileReader.atEOS()) {
            expectedFileContent.append(expectedFileReader.nextLine());
        }
        expectedFileReader.close();
        // Compare the content of the generated file with the expected content
        assertEquals(expectedFileContent.toString(),
                generatedFileContent.toString());
    }

    @Test
    // Test case 2: Check if the method generates the correct HTML file for the
    // given term and its definition
    public void createTermHTMLTest2() {
        SimpleReader inputs = new SimpleReader1L("data/dictTest4.txt");
        // Build the dictionary
        Map<String, String> map = Glossary.buildDictionary(inputs);
        inputs.close();
        String input = "explanation";
        // Output file name
        String outputFileName = "data/IndexTestFile.html";
        // Create the output file
        SimpleWriter out = new SimpleWriter1L(outputFileName);
        Glossary.createTermHTML(input, map, out);
        out.close();
        // Read the contents of the generated file
        SimpleReader outputFileReader = new SimpleReader1L(outputFileName);
        StringBuilder outputFileContents = new StringBuilder();
        while (!outputFileReader.atEOS()) {
            outputFileContents.append(outputFileReader.nextLine());
        }
        outputFileReader.close();
        // Compare the contents of the generated file with the expected contents
        SimpleReader expectedOutput = new SimpleReader1L(
                "data/expectedIndex5.txt");
        StringBuilder expectedFileContent = new StringBuilder();
        while (!expectedOutput.atEOS()) {
            expectedFileContent.append(expectedOutput.nextLine());
        }
        // Close the expected output reader
        expectedOutput.close();
        // Assert if the expected output matches the actual output
        assertEquals(expectedFileContent.toString(),
                outputFileContents.toString());
    }

}
