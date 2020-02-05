package com.gparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PageParser {

    String url;

    public PageParser(String url) {
        this.url = url;
    }



    public void run() throws IOException {

        Document doc = Jsoup.connect(url).timeout(10*1000).get(); ;

    }

}
