package com.gparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GoogleParser {
    String query;
    String searchLine;
    String nextPageLink = "OLOLOLOL";
    int counter = 0;

    public GoogleParser(String query) {
        this.query = query;
    }

    public ArrayList<GoogleSearchPage> run() throws IOException {
        searchLine = "https://www.google.com/search?q=" + query + "&num=500";
        ArrayList<GoogleSearchPage> gsps = new ArrayList<>();

//        while (!nextPageLink.isEmpty() && counter <= 1) {
//            System.setProperty("http.proxyHost", "77.244.42.178");
//            System.setProperty("http.proxyPort", "4145");
            Document doc = Jsoup.connect(searchLine).timeout(10*1000).get(); ;
            Elements links = doc.select("div.r");
            Elements nextPageLinks = doc.getElementsByAttributeValue("class", "fl");
            links.forEach(link -> {
                Element a = link.child(0);
                String url = a.attr("href");
                String title = a.child(2).text();
                gsps.add(new GoogleSearchPage(title, url));
            });

            nextPageLinks.forEach(np -> {
                nextPageLink = np.attr("href");
                searchLine = "https://www.google.com" + nextPageLink;
//                System.out.println(searchLine);

            });
            counter++;
//        }


        return gsps;
    }
}
