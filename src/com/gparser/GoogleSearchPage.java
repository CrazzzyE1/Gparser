package com.gparser;

import java.util.ArrayList;

public class GoogleSearchPage {
    private String title;
    private String url;
    private String domain;
    private String queries;
    private String emails;
    private String phones;

    public GoogleSearchPage(String url, String queries) {
        this.url = url;
        this.queries = queries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getQueries() {
        return queries;
    }

    public void setQueries(String queries) {
        this.queries = queries;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return title + ", url= " + url + "\n";
    }
}
