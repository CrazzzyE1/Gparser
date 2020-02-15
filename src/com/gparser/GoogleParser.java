package com.gparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GoogleParser {
    ArrayList<String> queries;
    String searchLine;

    public GoogleParser(ArrayList<String> queries) {
        this.queries = queries;
    }

    public ArrayList<GoogleSearchPage> run() throws IOException {
        ArrayList<GoogleSearchPage> gsps = new ArrayList<>();
        for (int i = 0; i < queries.size(); i++) {
            System.out.println("Типа парсю Google");
//            searchLine = "https://www.google.com/search?q=" + queries.get(i) + "&num=500";
//            // вписать задержку
//            Document doc = Jsoup.connect(searchLine).timeout(50*1000).get(); ;
//            Elements links = doc.select("div.r");
//            links.forEach(link -> {
//                Element a = link.child(0);
//                String url = a.attr("href");
//                String title = a.child(2).text();
//                gsps.add(new GoogleSearchPage(title, url));
//            });
            try {
                System.out.println("Delay...");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return gsps;
    }
}
