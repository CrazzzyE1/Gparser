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

    public GoogleParser(String query) {
        this.query = query;
    }

    public ArrayList<GoogleSearchPage>  run() throws IOException {
        searchLine = "https://www.google.com/search?q=" + query + "&num=20";
        ArrayList <GoogleSearchPage> gsps = new ArrayList<>();
        Document doc = Jsoup.connect(searchLine).get();
        Elements links = doc.select("div.r");
        links.forEach(link -> {
            Element a = link.child(0);
            String url = a.attr("href");
            String title = a.child(2).text();
            gsps.add(new GoogleSearchPage(title, url));
        });

        return gsps;
    }
}
