package com.gparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        String query = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите ключевое слово:");

        query = sc.nextLine();
        String searchLine = "https://www.google.com/search?q=" + query + "&num=20";
        Document doc = Jsoup.connect(searchLine).get();
        Elements links = doc.select("div.r");
        links.forEach(link -> {
            Element a = link.child(0);
            String url = a.attr("href");
            String title = a.child(2).text();
            System.out.println(url);
        });
    }
}
