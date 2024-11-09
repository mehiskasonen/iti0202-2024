package ee.taltech.iti0202.bookscraper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    public static void BookScraper() throws URISyntaxException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a website to scrape:");
        System.out.println("1. Apollo.ee");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String url = "";
        switch (choice) {
            case 1:
                bookScraperSubMenu();
                break;
            case 2:
                url = "https://rahvaraamat.ee/products/nädala-top-raamatud/et?categoryId=11&categoryTopInterval=" +
                        "7&language=et&page=1&per-page=80&sort=-top&type_id=148";
            default:
                System.out.println("Invalid choice.");
                System.exit(0);
        }
    }

    public static void bookScraperSubMenu() throws URISyntaxException, IOException {
        ApolloRequest.categoryRequest();
        ApolloRequest.readCategory(ApolloRequest.getCategoryString());
        List<String> categories = ApolloRequest.getCategoriesList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a book category to scrape:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + " " + categories.get(i));
        }

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > categories.size() ) {
            System.out.println("Invalid choice.");
            System.exit(0);
        }
        String stringChoice = categories.get(choice - 1);

        ApolloRequest.scrapeCategory("http://example.com/" + stringChoice); // Modify URL as needed
    }
}
