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

        ArrayList<GoogleSearchPage> gsps = new GoogleParser(query).run();
//        System.out.println(gsps);

    }
}
