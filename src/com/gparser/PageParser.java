package com.gparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PageParser {

    private String url;
    private ArrayList<String> emails = new ArrayList<>();
    private ArrayList<String> phones = new ArrayList<>();
    private int linkscount;

    public static Set<String> uniqueURL = new HashSet<String>();
    public static String domain;

    public PageParser(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }

    // Парсинг всех ссылок на странице
    public void run() throws IOException {
        //тут поиск всех ссылок и проход по всем страницам
        domain = domainSearch(url);
//        PageParser obj = new PageParser();
        System.out.println("Парсим все Ссылки на странице: " + domain);
        linkscount = 1;
        get_links(url);

    }

    //Поиск всех ссылок на странице
    private void get_links(String url) {

        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
            Elements links = doc.select("a");

            if (links.isEmpty()) {
                return;
            }

            links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
                boolean add = uniqueURL.add(this_url);
                if (add && this_url.contains(domain) && !this_url.contains(".pdf")
                        && !this_url.contains(".jpg") && !this_url.contains(".rss")
                        && !this_url.contains(".gif") ) {
//                    System.out.println(this_url);
                    emailSearch(this_url);
                    phoneSearch(this_url);
                    linkscount--;
                    System.out.print(linkscount + " ");
                    if(linkscount > 0) {
                        get_links(this_url);
                    }
                }
            });

        } catch (IOException ex) {

        }
    }


    // поиск домена
    private String domainSearch(String url) {
        String domain = null;
        int count = 0;

        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) == '/') {
                count++;
                if (count == 3) {
                    count = i + 1;
                    break;
                }
            }
        }
        domain = url.substring(0, count);
        return domain;
    }

    // Парсинг email на странице
    private void emailSearch(String url) {
//        System.out.println("Парсим все Почты на странице");
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String pageText = doc.text();
        String delimeter = " ";
        String[] pageWords = pageText.split(delimeter);

        for (int i = 0; i < pageWords.length; i++) {
            if (pageWords[i].contains("@")) {
                emails.add(pageWords[i]);
            }
        }
    }

    // Парсинг телефоных номеров на странице
    private void phoneSearch(String url) {
//        System.out.println("Парсим все Телефонные номера на странице");
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String pageText = doc.text();
        String delimeter = " ";
        String[] pageNumbers = pageText.split(delimeter);

        for (int i = 0; i < pageNumbers.length; i++) {
            if (pageNumbers[i].contains("8-800") || pageNumbers[i].contains("+7") || pageNumbers[i].contains("4722")) {
                phones.add(pageNumbers[i]);
            }
        }
    }

}
