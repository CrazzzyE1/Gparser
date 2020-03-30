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
    String que = "";

    public GoogleParser(ArrayList<String> queries) {
        this.queries = queries;
    }

    public ArrayList<GoogleSearchPage> run() throws IOException {
        ArrayList<GoogleSearchPage> gsps = new ArrayList<>();
        Random rnd = new Random();

        for (int i = 0; i < queries.size(); i++) {
            int timer = (rnd.nextInt(9) + 5) * 1000;
            que = queries.get(i);
            searchLine = "https://www.google.com/search?q=" + queries.get(i) + "&num=500";
            Document doc = Jsoup.connect(searchLine).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0")
                    .timeout(5000).header("Content-Language", "en-US").get();
            Elements links = doc.select("div.r");
            links.forEach(link -> {
                Element a = link.child(0);
                String url = a.attr("href");
                gsps.add(new GoogleSearchPage(url, que));
            });
            delayTimer(timer);
        }
        return gsps;
    }

    public void delayTimer(int timer) {
        try {
            Thread.sleep(timer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
