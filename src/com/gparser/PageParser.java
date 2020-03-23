package com.gparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;

public class PageParser {

    private ArrayList<GoogleSearchPage> gsps;
    private String url;
    private StringBuilder emails;
    private StringBuilder phones;
    private String domain;
    private String title;
    private Document doc;
    private int counter = 4;

    public PageParser(ArrayList<GoogleSearchPage> gsps) {
        this.gsps = gsps;
    }

    public void run () throws IOException {
        for (int i = 0; i < gsps.size(); i++) {
            counter = 4;
            url = gsps.get(i).getUrl();
            domain = domainSearch(url);
            doc = getDocPage(url);
            title = doc.title();
            gsps.get(i).setDomain(domain);
            gsps.get(i).setTitle(title);

//            getDocLinks(url, counter);

            delayTimer(2000);
        }
    }

    //Поиск всех ссылок на странице
    private void getDocLinks(String url, int counter) throws IOException {
        if (counter == 0) {
            return;
        }
        ArrayList<String> linksList = new ArrayList<>();
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0")
                .timeout(0).header("Content-Language", "en-US").get();
        Elements links = doc.select("a");
        for (int i = 0; i < links.size(); i++) {
            String link = links.get(i).attr("abs:href");
            if(link.contains(domain) && !link.contains("#")){
                getDocLinks(link, counter--);
            }
        }
    }

    //Поиск всех телефонов на странице
    private void getDocPhones(Document doc) {
        System.out.println("getDocPhones " + title);
    }

    //Поиск всех E-mails на странице
    private void getDocEmails(Document doc) {
        System.out.println("getDocEmails " + title);

    }

    //Поиск всех ссылок на странице
    private Document getDocPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0")
                .timeout(0).header("Content-Language", "en-US").get();
        return doc;
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

    //TimerDelay
    public void delayTimer(int timer) {
        try {
            Thread.sleep(timer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // **************************************************************************************************

//    // Парсинг всех ссылок на странице
//    public void run2() throws IOException {
//        //тут поиск всех ссылок и проход по всем страницам
//        domain = domainSearch(url);
////        PageParser obj = new PageParser();
//        System.out.println("Парсим все Ссылки на странице: " + domain);
//        linkscount = 1;
//        get_links(url);
//
//    }
//
//    //Поиск всех ссылок на странице
//    private void get_links(String url) {
//
//        try {
//            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
//            Elements links = doc.select("a");
//
//            if (links.isEmpty()) {
//                return;
//            }
//
//            links.stream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
//                boolean add = uniqueURL.add(this_url);
//                if (add && this_url.contains(domain) && !this_url.contains(".pdf")
//                        && !this_url.contains(".jpg") && !this_url.contains(".rss")
//                        && !this_url.contains(".gif") ) {
////                    System.out.println(this_url);
//                    emailSearch(this_url);
//                    phoneSearch(this_url);
//                    linkscount--;
//                    System.out.print(linkscount + " ");
//                    if(linkscount > 0) {
//                        get_links(this_url);
//                    }
//                }
//            });
//
//        } catch (IOException ex) {
//
//        }
//    }
//
//
//
//
//    // Парсинг email на странице
//    private void emailSearch(String url) {
////        System.out.println("Парсим все Почты на странице");
//        Document doc = null;
//        try {
//            doc = Jsoup.connect(url).userAgent("Mozilla").get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String pageText = doc.text();
//        String delimeter = " ";
//        String[] pageWords = pageText.split(delimeter);
//
//        for (int i = 0; i < pageWords.length; i++) {
//            if (pageWords[i].contains("@")) {
//                emails.add(pageWords[i]);
//            }
//        }
//    }
//
//    // Парсинг телефоных номеров на странице
//    private void phoneSearch(String url) {
//        String text = null;
////        System.out.println("Парсим все Телефонные номера на странице");
//        try {
//            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            text = doc.text();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        StringBuilder tmp = new StringBuilder();
//        boolean flag = false;
//        String ss = "0123456789+().,- ";
//        String result;
//        for (int i = 0; i < text.length(); i++) {
//            for (int j = 0; j < ss.length(); j++) {
//                if (text.charAt(i) == ss.charAt(j)) {
//                    flag = true;
//                    break;
//                } else {
//                    flag = false;
//                }
//            }
//            if (flag) {
//                tmp.append(text.charAt(i));
//            } else {
//                result = tmp.toString().trim();
//                if (result.length() >= 8 && result.length() < 43 && result.charAt(0) != ',' && result.charAt(0) != '-' && result.charAt(0) != '.') {
//                    System.out.println(result);
//                }
//                tmp.setLength(0);
//            }
//        }
//    }

}
