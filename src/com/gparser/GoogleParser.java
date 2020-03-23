package com.gparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GoogleParser {
    ArrayList<String> queries;
    String searchLine;

    public GoogleParser(ArrayList<String> queries) {
        this.queries = queries;
    }

    public ArrayList<GoogleSearchPage> run() throws IOException {
        ArrayList<GoogleSearchPage> gsps = new ArrayList<>();
        Random rnd = new Random();

        for (int i = 0; i < queries.size(); i++) {
            int timer = (rnd.nextInt(4) + 1) * 1000;
//            System.out.println("Типа парсю Google // " + queries.get(i));
            searchLine = "https://www.google.com/search?q=" + queries.get(i) + "&num=500";
            Document doc = Jsoup.connect(searchLine).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").get(); ;
            Elements links = doc.select("div.r");
            links.forEach(link -> {
                Element a = link.child(0);
                String url = a.attr("href");
                String title = a.child(2).text();
                gsps.add(new GoogleSearchPage(title, url));
            });

            delayTimer(timer);
        }

        return gsps;
    }

    public void delayTimer(int timer) {
        try {
            System.out.println("Delay timer: " + timer);
            Thread.sleep(timer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
