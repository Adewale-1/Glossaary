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
 * This program generates a glossary HTML page with links to definitions and
 * terms, listed in a generated text file. The main page will show a list all
 * definitions,where each definition page will have a return button to the home
 * page.It also hyperlinks any word/s that appear elsewhere in the glossary.
 *
 *
 * @author Adewale Adenle
 *
 */
public final class Glossary {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Glossary() {
    }

    /**
     * Builds a dictionary from the given input. Reads terms and their
     * definitions from the input and stores them in a map.
     *
     * @param input
     *            The SimpleReader object containing the terms and definitions.
     * @return A map containing the terms as keys and their definitions as
     *         values.
     */
    public static Map<String, String> buildDictionary(SimpleReader input) {
        // Create a new empty map to store the glossary
        Map<String, String> glossary = new Map1L<>();

        // Loop through the input until the end of the stream is reached
        while (!input.atEOS()) {
            // Read the term and its definition from the input
            String term = input.nextLine();
            StringBuilder definition = new StringBuilder();
            definition.append(input.nextLine());

            // Check if there are more lines in the input
            if (!input.atEOS()) {
                // Read and append any additional lines for the definition
                String definitionExtended = input.nextLine();
                while (!definitionExtended.equals("")) {
                    definition.append(definitionExtended);
                    definitionExtended = input.nextLine();
                }
            }

            // Add the term and its definition to the glossary map
            glossary.add(term, definition.toString());
        }

        return glossary;
    }

    /**
     * Extracts the pair with the lowest key (case-insensitive) from the given
     * map.
     *
     * @param m
     *            The map to extract the lowest pair from.
     * @return The pair with the lowest key.
     */
    public static Pair<String, String> extractLowestPair(
            Map<String, String> m) {
        // Remove any pair from the map and store it as the current lowest
        Pair<String, String> lowest = m.removeAny();
        m.add(lowest.key(), lowest.value());

        // Iterate through the remaining pairs in the map
        for (Pair<String, String> pair : m) {
            // Compare the keys case-insensitively and update the lowest pair if necessary
            if (pair.key().compareToIgnoreCase(lowest.key()) < 0) {
                lowest = pair;
            }
        }

        // Remove and return the lowest pair from the map
        return m.remove(lowest.key());
    }

    /**
     * Builds a sorted queue of terms from the given map. The terms are sorted
     * in ascending order (case-insensitive).
     *
     * @param m
     *            The map containing the terms and their definitions.
     * @return A queue containing the sorted terms.
     */
    public static Queue<String> buildSortedQueue(Map<String, String> m) {
        // Create a new empty queue to store the sorted terms
        Queue<String> sorted = new Queue1L<>();

        // Create a temporary map to store the pairs during sorting
        Map<String, String> temp = new Map1L<>();

        // Loop through the map until all pairs have been processed
        while (m.size() > 0) {
            // Extract the lowest pair from the map
            Pair<String, String> lowest = extractLowestPair(m);

            // Enqueue the term in the sorted queue
            sorted.enqueue(lowest.key());

            // Add the pair to the temporary map
            temp.add(lowest.key(), lowest.value());
        }

        // Transfer the pairs back from the temporary map to the original map
        m.transferFrom(temp);

        return sorted;
    }

    /**
     * Creates an index HTML file with links to term definition pages.
     *
     * @param terms
     *            The queue containing the sorted terms.
     * @param output
     *            The SimpleWriter object to write the generated HTML to.
     */
    public static void createIndexHTML(Queue<String> terms,
            SimpleWriter output) {
        // Write the HTML header and title
        output.println("<html>");
        output.println("<head>");
        output.println("   <title>Sample Glossary</title>");
        output.println("</head>");
        output.println("<body>");
        output.println("   <h2>Sample Glossary</h2>");
        output.println("   <hr />");
        output.println("   <h3>Index</h3>");
        output.println("   <ul>");

        // Loop through the terms and create list items with links to their
        // definition pages
        for (String key : terms) {
            output.println("      <li><a href=\"" + key + ".html\">" + key
                    + "</a></li>");
        }

        // Close the HTML tags
        output.println("   </ul>");
        output.println("</body>");
        output.println("</html>");
    }

    /**
     * Creates a set of unique characters from the given string.
     *
     * @param str
     *            The input string.
     * @param charSet
     *            The set to store the unique characters.
     */
    public static void createUniqueChars(String str, Set<Character> charSet) {
        // Clear the set before adding characters
        charSet.clear();

        // Loop through the input string and add unique characters to the set
        for (int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);
            if (!charSet.contains(current)) {
                charSet.add(current);
            }
        }
    }

    /**
     * Gets the next word or separator from the given text starting at the
     * specified position.
     *
     * @param text
     *            The input text.
     * @param position
     *            The starting position.
     * @param separators
     *            The set of separator characters.
     * @return The next word or separator.
     */
    public static String getNextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        boolean startedOnSplit = separators.contains(text.charAt(position));
        int leftEdge = position;
        int rightEdge = position + 1;

        // Find the left edge of the word or separator
        while (leftEdge > 0 && separators
                .contains(text.charAt(leftEdge - 1)) == startedOnSplit) {
            leftEdge--;
        }

        // Find the right edge of the word or separator
        while (rightEdge < text.length() && separators
                .contains(text.charAt(rightEdge)) == startedOnSplit) {
            rightEdge++;
        }

        return text.substring(leftEdge, rightEdge);
    }

    /**
     * Gets the definition text with references to other terms in the glossary.
     *
     * @param definition
     *            The input definition text.
     * @param terms
     *            The set of terms in the glossary.
     * @return The definition text with references to other terms as HTML links.
     */
    public static String getDefinitionWithReferences(String definition,
            Set<String> terms) {
        // Create a set of separator characters
        Set<Character> separators = new Set1L<>();
        createUniqueChars(".,/!?();<>{}[]@#$%^&*| ", separators);

        // Initialize a StringBuilder to store the formatted definition text
        StringBuilder formatted = new StringBuilder();
        int position = 0;

        // Loop through the definition text and add references to other terms
        while (position < definition.length()) {
            String word = getNextWordOrSeparator(definition, position,
                    separators);
            position += word.length();

            // If the word is a term in the glossary, add a reference link
            if (terms.contains(word)) {
                formatted.append(
                        "<a href=\"" + word + ".html\">" + word + "</a>");
            } else {
                formatted.append(word);
            }
        }

        return formatted.toString();
    }

    /**
     * Creates an HTML file for a term with its definition and references to
     * other terms in the glossary.
     *
     * @param term
     *            The term for which the HTML file is being created.
     * @param glossary
     *            The map containing the terms and their definitions.
     * @param output
     *            The SimpleWriter object to write the generated HTML to.
     */
    public static void createTermHTML(String term, Map<String, String> glossary,
            SimpleWriter output) {
        // Write the HTML header and title
        output.println("<html>");
        output.println("<head>");
        output.println("   <title>" + term + "</title>");
        output.println("</head>");
        output.println("<body>");
        output.println("   <h2><b><i><font color=\"red\">" + term
                + "</font></i></b></h2>");

        // Create a set of terms from the glossary map
        Set<String> terms = new Set1L<>();
        for (Pair<String, String> combo : glossary) {
            terms.add(combo.key());
        }

        // Write the definition with references to other terms in the glossary
        output.println("   <blockquote>");
        output.println("      "
                + getDefinitionWithReferences(glossary.value(term), terms));
        output.println("   </blockquote>");

        // Add a horizontal line and a link to return to the index
        output.println("   <hr />");
        output.println("   <p>Return to <a href=\"index.html\">index</a>.</p>");

        // Close the HTML tags
        output.println("</body>");
        output.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        // Create SimpleReader and SimpleWriter objects for user input and output
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Prompt the user for the input file name and output folder
        out.print("Enter the name of the Input File: ");
        String inputFileName = in.nextLine();
        out.print("Enter the name of the Output File: ");
        String outputFolder = in.nextLine();

        // Read the glossary pairs from the input file
        SimpleReader inFile = new SimpleReader1L(inputFileName);
        Map<String, String> glossary = buildDictionary(inFile);
        Queue<String> orderedTerms = buildSortedQueue(glossary);
        inFile.close();

        // Generate the index HTML file
        SimpleWriter indexOut = new SimpleWriter1L(
                outputFolder + "/index.html");
        createIndexHTML(orderedTerms, indexOut);
        indexOut.close();

        // Generate the term definition HTML files
        for (String key : orderedTerms) {
            String termFile = outputFolder + "/" + key + ".html";
            SimpleWriter termOut = new SimpleWriter1L(termFile);
            createTermHTML(key, glossary, termOut);
            termOut.close();
        }

        // Close the SimpleReader and SimpleWriter objects
        in.close();
        out.close();
    }
}
