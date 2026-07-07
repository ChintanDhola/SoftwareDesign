package exercise_5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WebsiteComparisonTest {

    // Test Class 1 for the Null Values
    @Test
    void testNullInputs() {
        WebsiteComparisonStrategy htmlStrategy = new HtmlComparisonStrategy();
        assertTrue(htmlStrategy.isContentDifferent(null, "<html>New</html>"),
                "Should return true if old content is null");
        assertTrue(htmlStrategy.isContentDifferent("<html>Old</html>", null),
                "Should return true if new content is null");
    }

    // Test Class 2 for the Identical Content
    @Test
    void testIdenticalContent() {
        WebsiteComparisonStrategy textStrategy = new TextComparisonStrategy();
        assertFalse(textStrategy.isContentDifferent("Hello World", "Hello World"),
                "Should return false because the strings are identical");
    }

    // Test Class 3 for the Completely Different Content
    @Test
    void testDifferentContent() {
        WebsiteComparisonStrategy htmlStrategy = new HtmlComparisonStrategy();
        assertTrue(htmlStrategy.isContentDifferent("<h1>Apples</h1>", "<h1>Oranges</h1>"),
                "Should return true because the text changed");
    }

    // Test Class 4 for the HTML changed, but text is identical
    @Test
    void testHtmlChangedTextSame() {
        String oldSite = "<h1>Welcome</h1>";
        String newSite = "<h2>Welcome</h2>";

        WebsiteComparisonStrategy htmlStrategy = new HtmlComparisonStrategy();
        WebsiteComparisonStrategy textStrategy = new TextComparisonStrategy();

        // HTML Strategy should see them as DIFFERENT
        assertTrue(htmlStrategy.isContentDifferent(oldSite, newSite));

        // Text Strategy should see them as the SAME
        assertFalse(textStrategy.isContentDifferent(oldSite, newSite));
    }
}
