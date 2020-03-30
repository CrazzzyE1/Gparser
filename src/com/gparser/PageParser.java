package com.gparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class PageParser {

    private ArrayList<GoogleSearchPage> gsps;
    private String url;
    private StringBuilder emails = new StringBuilder();
    private StringBuilder phones = new StringBuilder();
    private String domain;
    private String title;

    public PageParser(ArrayList<GoogleSearchPage> gsps) {
        this.gsps = gsps;
    }

    public void run () {
        ArrayList<String> linksList = new ArrayList<>();
        ArrayList<String> linksList2 = new ArrayList<>();
        ArrayList<String> linksList3 = new ArrayList<>();
        ArrayList<String> linksListResult = new ArrayList<>();

        for (int i = 0; i < gsps.size(); i++) {
            url = gsps.get(i).getUrl();
            domain = domainSearch(url);
            System.out.println(domain);

          // **********************************************
            Document doc = null;

            try {
                doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0")
                        .timeout(0).header("Content-Language", "en-US").ignoreHttpErrors(true).get();
            } catch (IOException e) {
                System.out.println("Link is FAILED");
                continue;
            }
            Elements links = doc.select("a");
            title = doc.title();
            gsps.get(i).setDomain(domain);
            gsps.get(i).setTitle(title);

            for (int j = 0; j < links.size(); j++) {
                String link = links.get(j).attr("abs:href");
                if (!link.isEmpty() && link.contains(domain) && !link.contains("#") && !linksList.contains(link)
                        && !link.contains(".rss") && !link.contains(".jpg") && !link.contains(".gif")
                        && !link.contains(".pdf") && !link.contains(".xls") && !link.contains(".doc")
                        && !link.contains(".avi") && !link.contains(".zip") && !link.contains("upload")) {
                    linksList.add(link);
                    linksListResult.add(link);
                }
            }

//            for (int ii = 0; ii < linksList.size(); ii++) {
//                doc = Jsoup.connect(linksList.get(ii)).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0")
//                        .timeout(0).header("Content-Language", "en-US").ignoreHttpErrors(true).get();
//                Elements links2 = doc.select("a");
//                for (int j = 0; j < links2.size(); j++) {
//                    String link = links2.get(j).attr("abs:href");
//                    if (link.contains(domain) && !link.contains("#") && !linksList2.contains(link) && !linksList.contains(link)
//                            && !link.contains(".rss") && !link.contains(".jpg") && !link.contains(".gif")
//                            && !link.contains(".pdf") && !link.contains(".xls") && !link.contains(".doc")
//                            && !link.contains(".avi") && !link.contains(".zip") && !link.contains("upload")) {
//                        linksList2.add(link);
//                        linksListResult.add(link);
//
//                    }
//                }
//            }

//            for (int jj = 0; jj < linksList2.size(); jj++) {
//                doc = Jsoup.connect(linksList2.get(jj)).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0")
//                        .timeout(0).header("Content-Language", "en-US").ignoreHttpErrors(true).get();
//                Elements links3 = doc.select("a");
//                for (int j = 0; j < links3.size(); j++) {
//                    String link = links3.get(j).attr("abs:href");
//                    if (link.contains(domain) && !link.contains("#") && !linksList3.contains(link) && !linksList2.contains(link) && !linksList.contains(link)
//                            && !link.contains(".rss") && !link.contains(".jpg") && !link.contains(".gif")
//                            && !link.contains(".pdf") && !link.contains(".xls") && !link.contains(".doc")
//                            && !link.contains(".avi") && !link.contains(".zip") && !link.contains("upload")) {
//                        linksList3.add(link);
//                        linksListResult.add(link);
//                    }
//                }
//            }

          // ************************************************

            for (int ff = 0; ff < linksListResult.size(); ff++) {

                try {
                    doc = Jsoup.connect(linksListResult.get(ff)).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0")
                            .timeout(0).header("Content-Language", "en-US").ignoreHttpErrors(true).get();
                } catch (IOException e) {
                    System.out.println("Error Link");
                    continue;
                }
                emails.append(getDocEmails(doc));
//                getDocPhones(doc);
            }
            System.out.println(emails);
        }



    }

    //Поиск всех телефонов на странице
    private void getDocPhones(Document doc) {
        System.out.println("getDocPhones");
    }

    //Поиск всех E-mails на странице
    private StringBuilder getDocEmails(Document doc) {
        StringBuilder em = new StringBuilder();
        String pageText = doc.text();
        String delimeter = " ";
        String[] pageWords = pageText.split(delimeter);

        for (int i = 0; i < pageWords.length; i++) {
            if(pageWords[i].contains("@") && !emails.toString().contains(pageWords[i])) {
                em.append(pageWords[i] + "; ");;
            }
        }
        return em;
    }

    // поиск домена
    private String domainSearch(String url) {
        String domain = null;
        int count = 0;
        int start = 0;

        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) == '/') {
                count++;
                if (count == 2) {
                    start = i + 1;
                }
                if (count == 3) {
                    count = i + 1;
                    break;
                }
            }
        }
        domain = url.substring(start, count);
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
