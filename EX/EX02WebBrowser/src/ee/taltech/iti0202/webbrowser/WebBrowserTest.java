package ee.taltech.iti0202.webbrowser;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WebBrowserTest {

    @org.junit.Test
    public void goToBackBackGetHistory() {
        WebBrowser webBrowser = new WebBrowser();
        assertEquals("google.com", webBrowser.getCurrentUrl());
        webBrowser.setHomePage("neti.ee");
        webBrowser.goTo("facebook.com");
        webBrowser.back();
        webBrowser.back();
        List<String> actual = webBrowser.getHistory();
        List<String> expected = Arrays.asList("google.com", "facebook.com", "google.com");
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void setHomePageGoToForwardForwardGetHistory() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.setHomePage("neti.ee");
        webBrowser.goTo("facebook.com");
        webBrowser.forward();
        webBrowser.forward();
        List<String> actual = webBrowser.getHistory();
        List<String> expected = Arrays.asList("google.com", "facebook.com");
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void setHomePageGoToBackHomePageForwardGetHistory() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.setHomePage("neti.ee");
        webBrowser.goTo("facebook.com");
        webBrowser.back();
        webBrowser.homePage();
        webBrowser.forward();
        List<String> actual = webBrowser.getHistory();
        List<String> expected = Arrays.asList("google.com", "facebook.com", "google.com", "neti.ee");
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void getBookmarksGetHistory() {
        WebBrowser webBrowser = new WebBrowser();
        assertEquals("google.com", webBrowser.getCurrentUrl());
        webBrowser.setHomePage("neti.ee");
        webBrowser.goTo("facebook.com");
        assertEquals("facebook.com", webBrowser.getCurrentUrl());
        webBrowser.goTo("google.com");
        assertEquals("google.com", webBrowser.getCurrentUrl());
        webBrowser.back();
        assertEquals("facebook.com", webBrowser.getCurrentUrl());
        webBrowser.addAsBookmark();
        webBrowser.forward();
        assertEquals("google.com", webBrowser.getCurrentUrl());
        webBrowser.homePage();
        assertEquals("neti.ee", webBrowser.getCurrentUrl());
        webBrowser.addAsBookmark();

        List<String> actual = webBrowser.getBookmarks();
        List<String> expected = Arrays.asList("facebook.com", "neti.ee");
        assertEquals(expected, actual);

        List<String> actual2 = webBrowser.getHistory();
        List<String> expected2 = Arrays.asList("google.com", "facebook.com", "google.com", "facebook.com",
                "google.com", "neti.ee");
        assertEquals(expected2, actual2);
    }

    @org.junit.Test
    public void goTOSamePageGetHistoryOnlyOne() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.goTo("google.com");
        webBrowser.goTo("google.com");

        List<String> actual = webBrowser.getHistory();
        List<String> expected = List.of("google.com");
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void goTOSamePageGetTop3OnlyOne() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.goTo("google.com");
        webBrowser.goTo("google.com");

        String actual = webBrowser.getTop3VisitedPages();
        String expected = "google.com - 1 visit";
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void getTop3TwoWithOnlyOneVisit() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.goTo("google.com");
        webBrowser.goTo("google.com");
        webBrowser.goTo("taltech.ee");
        String actual = webBrowser.getTop3VisitedPages();
        String expected = "google.com - 1 visit\n"
                + "taltech.ee - 1 visit";
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void getTop3With2x2Visits1x1Visit() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.goTo("neti.ee");
        webBrowser.goTo("facebook.com");
        webBrowser.back();
        webBrowser.forward();

        List<String> actual2 = webBrowser.getHistory();
        List<String> expected2 = Arrays.asList("google.com", "neti.ee", "facebook.com", "neti.ee", "facebook.com");
        assertEquals(expected2, actual2);

        String actual = webBrowser.getTop3VisitedPages();
        String expected = """
                neti.ee - 2 visits
                facebook.com - 2 visits
                google.com - 1 visit""";
        assertEquals(expected, actual);

    }

    @org.junit.Test
    public void test3PagesGoTo2Back1goTo1() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.goTo("neti.ee"); // Lehed, kuhu saab tagasi liikuda: ['google.com']. Lehed, kuhu saab
        // edasi liikuda: []
        webBrowser.goTo("facebook.com"); // Lehed, kuhu saab tagasi liikuda: ['neti.ee','google.com'].
        // Lehed, kuhu saab edasi liikuda: []
        webBrowser.back(); // CurrentUrl on nüüd "neti.ee". Lehed, kuhu saab tagasi liikuda: [google.com].
        // Lehed, kuhu saab edasi liikuda: ['facebook.com']
        webBrowser.goTo("google.com"); // Lehed, kuhu saab tagasi liikuda: ["neti.ee","google.com"].
        // Lehed, kuhu saab edasi liikuda: []

        List<String> actual = webBrowser.getHistory();
        List<String> expected = Arrays.asList("google.com", "neti.ee", "facebook.com", "neti.ee", "google.com");
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void test10Back10Forward() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.back();
        webBrowser.back();
        webBrowser.back();
        webBrowser.back();
        webBrowser.back();
        webBrowser.back();
        webBrowser.back();
        webBrowser.back();
        webBrowser.back();
        webBrowser.back();
        webBrowser.forward();
        webBrowser.forward();
        webBrowser.forward();
        webBrowser.forward();
        webBrowser.forward();
        webBrowser.forward();
        webBrowser.forward();
        webBrowser.forward();
        webBrowser.forward();
        webBrowser.forward();

        List<String> actual = webBrowser.getHistory();
        List<String> expected = List.of("google.com");
        assertEquals(expected, actual);
    }


    @org.junit.Test
    public void homePage() {
    }

    @org.junit.Test
    public void back() {
    }

    @org.junit.Test
    public void forward() {
    }

    @org.junit.Test
    public void goTo() {
    }

    @org.junit.Test
    public void addAsBookmark() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.goTo("taltech.ee");
        webBrowser.addAsBookmark();

        List<String> actual = webBrowser.getBookmarks();
        List<String> expected = List.of("taltech.ee");
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void addAsBookmarkAlreadyExists() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.goTo("taltech.ee");
        webBrowser.addAsBookmark();
        webBrowser.addAsBookmark();

        List<String> actual = webBrowser.getBookmarks();
        List<String> expected = List.of("taltech.ee");
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void removeBookmark() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.goTo("taltech.ee");
        webBrowser.addAsBookmark();
        webBrowser.removeBookmark("taltech.ee");

        List<String> actual = webBrowser.getBookmarks();
        List<String> expected = List.of();
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void removeBookmarkDoesNotExist() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.goTo("taltech.ee");
        webBrowser.addAsBookmark();
        webBrowser.removeBookmark("MIT.com");

        List<String> actual = webBrowser.getBookmarks();
        List<String> expected = List.of("taltech.ee");
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void getBookmarks() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.goTo("taltech.ee");
        webBrowser.addAsBookmark();
        webBrowser.goTo("MIT.com");
        webBrowser.addAsBookmark();

        List<String> actual = webBrowser.getBookmarks();
        List<String> expected = List.of("taltech.ee", "MIT.com");
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testTopPagesWithBackAndForward() {
        WebBrowser webBrowser = new WebBrowser();
        webBrowser.goTo("taltech.ee");
        webBrowser.goTo("gmail.com");
        webBrowser.goTo("facebook.com");
        webBrowser.goTo("mail.ru");
        webBrowser.back();  // facebook.com
        webBrowser.back();  // gmail.com
        webBrowser.forward(); //facebook.com
        webBrowser.forward(); //mail.ru
        webBrowser.back();  //facebook.com
        webBrowser.homePage();  //google.com

        List<String> actual = webBrowser.getHistory();
        List<String> expected = List.of("google.com", "taltech.ee", "gmail.com", "facebook.com", "mail.ru",
                "facebook.com", "gmail.com", "facebook.com", "mail.ru", "facebook.com", "google.com");
        assertEquals(expected, actual);

        String actual2 = webBrowser.getTop3VisitedPages();
        String expected2 = """
                facebook.com - 4 visits
                google.com - 2 visits
                gmail.com - 2 visits""";
        assertEquals(expected2, actual2);
    }

    @org.junit.Test
    public void getHistory() {
    }
}
