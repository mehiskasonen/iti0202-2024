package ee.taltech.iti0202.webbrowser;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WebBrowser {
    Deque<String> backStack = new ArrayDeque<>(List.of("google.com"));
    Deque<String> current = new ArrayDeque<>(List.of("google.com"));
    Deque<String> forwardStack = new ArrayDeque<>();
    List<String> bookmarks = new ArrayList<>();
    List<String> history = new ArrayList<>(List.of("google.com"));
    String home = "google.com";
    private String homePage;

    /**
     * Goes to homepage.
     */
    public void homePage() {
        forwardStack.clear();
        backStack.add(String.valueOf(home));
        history.add(home);
    }

    /**
     * Goes back to previous page.
     */
    public void back() {
        if (backStack.size() > 1) {
            String top = backStack.peekLast();
            backStack.pollLast();
            String second = backStack.peekLast();
            backStack.push(top);
            if (!Objects.equals(top, second)) {
                history.add(second);
            }
            forwardStack.add(top);
            backStack.pop();
        }
    }

    /**
     * Goes forward to next page.
     */
    public void forward() {
        if (forwardStack.size() > 0) {
            history.add(forwardStack.peekLast());
            backStack.add(forwardStack.peekLast());
            forwardStack.pollLast();
        }
    }

    /**
     * Go to a webpage.
     *
     * @param url where to go
     */
    public void goTo(String url) {
        if (!Objects.equals(url, backStack.peekLast())) {
            history.add(url);
        }
        forwardStack.clear();
        backStack.add(url);
    }

    /**
     * Add the current webpage as a bookmark.
     */
    public void addAsBookmark() {
        if (!bookmarks.contains(backStack.peekLast())) {
            bookmarks.add(backStack.peekLast());
        }
    }

    /**
     * Remove a bookmark.
     *
     * @param bookmark to remove
     */
    public void removeBookmark(String bookmark) {
        bookmarks.remove(bookmark);
    }

    public List<String> getBookmarks() {
        return bookmarks;
    }

    public void setHomePage(String homePage) {
        home = homePage;
    }
    /**
     * Get top 3 visited pages.
     * HashMap - kõige külastatavam leht võtad välja, kordad.
     * @return a String that contains top three visited pages separated with a newline "\n"
     */
    public String getTop3VisitedPages() {
        StringBuilder top3Visited = new StringBuilder();
        Map<String, Integer> topVisited = new LinkedHashMap<>();
        for (String word : history) {
            Integer count = topVisited.get(word);
            if (count == null) {
                topVisited.put(word, 1);
            } else {
                topVisited.put(word, count + 1);
            }
        }

        String mostPopular = "";
        Integer max = 0;
        int iterator = 3;
        while (iterator != 0 && topVisited.size() != 0) {
            for (Map.Entry<String, Integer> entry : topVisited.entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                    mostPopular = entry.getKey();
                }
            } if (max == 1) {
                top3Visited.append(mostPopular).append(" - ").append(max).append(" visit\n");
                topVisited.remove(mostPopular, max);
                mostPopular = "";
                max = 0;
            } else {
                top3Visited.append(mostPopular).append(" - ").append(max).append(" visits\n");
                topVisited.remove(mostPopular, max);
                mostPopular = "";
                max = 0;
            }
            iterator--;
        }
        return String.valueOf(top3Visited).trim();
    }

    /**
     * Returns a list of all visited pages.
     * <p>
     * Not to be confused with pages in your back-history.
     * <p>
     * For example, if you visit "facebook.com" and hit back(),
     * then the whole history would be: ["google.com", "facebook.com", "google.com"]
     * @return list of all visited pages
     */
    public List<String> getHistory() {
        return history;
    }

    /**
     * Returns the active web page (string).
     *
     * @return active web page
     */
    public String getCurrentUrl() {
        if (backStack.size() < 1) {
            return home;
        } else {
            return backStack.peekLast();
        }
    }
}
