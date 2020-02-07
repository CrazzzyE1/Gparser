package com.gparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        String query;
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите ключевое слово:");
        query = sc.nextLine();

        //Парсим все ссылки по ключевому слову из поисковика Google
        ArrayList<GoogleSearchPage> gsps = new GoogleParser(query).run();

        //Парсим все email & phones на всех страницах всех сайтов, которые нашел Google
        for (GoogleSearchPage gsp:gsps
             ) {
            PageParser pageParser = new PageParser(gsp.getUrl());
            pageParser.run();
            gsp.setEmails(pageParser.getEmails());
            gsp.setPhones(pageParser.getPhones());
            System.out.println(gsp.getEmails());
            System.out.println(gsp.getPhones());
        }

    }
}
