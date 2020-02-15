package com.gparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        int counter = 0;
        ArrayList<String> queries;
        ArrayList<String> cities;

        // Файл с поисковыми запросами
        File fileQueries = new File("C:\\Program Project\\Gparser\\asserts\\queries.txt");
        File fileCities = new File("C:\\Program Project\\Gparser\\asserts\\cities.txt");
        File fileGoogleLinks = new File("C:\\Program Project\\Gparser\\asserts\\googleLinks.txt");
        Scanner scanner = new Scanner(fileQueries);
        Scanner scanner2 = new Scanner(fileCities);

        queries = createQueriesList(scanner);
        cities = createQueriesList(scanner2);

        queries = mixQueries(cities, queries);

        scanner.close();

        //Создаем все запросы для Google с учетом названия города и региона поиска
        //запихиваем регион и базовый запрос
        //на выходе массив с поисковыми запросами - передаем Массив в GoogleParser


        //Парсим все ссылки по ключевому слову из поисковика Google
        ArrayList<GoogleSearchPage> gsps = new GoogleParser(queries).run();

        //Пишем все ссылки от гугла в файл
        createOutGoogleLinksFile(fileGoogleLinks, gsps);

        //Печатаем все ссылки из Google по запросу.
//
//        for (GoogleSearchPage gsp : gsps
//        ) {
//            counter++;
//            System.out.println(counter + ". " + gsp.getTitle() + " -//- " + gsp.getUrl());
//
//        }


        //Парсим все email & phones на всех страницах всех сайтов, которые нашел Google
//        for (GoogleSearchPage gsp:gsps
//             ) {
//            PageParser pageParser = new PageParser(gsp.getUrl());
//            pageParser.run();
//            gsp.setEmails(pageParser.getEmails());
//            gsp.setPhones(pageParser.getPhones());
//            System.out.println(gsp.getEmails());
//            System.out.println(gsp.getPhones());
//        }

    }

    public static void createOutGoogleLinksFile(File fileGoogleLinks, ArrayList<GoogleSearchPage> gsps) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileGoogleLinks);
            for (GoogleSearchPage gsp:gsps
            ) {
                String result = gsp.getTitle() + " -//- " + gsp.getUrl() + "\\n";
                try {
                    fileOutputStream.write(result.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> mixQueries(ArrayList<String> cities, ArrayList<String> queries) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < queries.size(); j++) {
                result.add(queries.get(j) + " " + cities.get(i));
            }
        }
        return result;
    }

    public static ArrayList<String> createQueriesList(Scanner scanner) {
        ArrayList<String> arrayList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            arrayList.add(scanner.nextLine());
        }
        return arrayList;
    }

    public static void printArrayList(ArrayList arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
    }
}
