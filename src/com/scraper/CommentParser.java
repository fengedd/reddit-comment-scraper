package com.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.concurrent.CopyOnWriteArrayList;

class CommentParser implements Runnable {
    private final Document doc;
    private final CopyOnWriteArrayList<String> words;

    public CommentParser(Document doc, CopyOnWriteArrayList<String>  words) {
        this.doc = doc;
        this.words = words;
    }

    @Override
    public void run() {
        Elements posts = doc.select("div.entry.unvoted:not(:has(span.morecomments)):has(div.md)");
        posts.forEach(e -> {
            String date = e.select("time").not(".edited-timestamp").attr("datetime");
            String score = e.select("span.score.unvoted").attr("title");
            String content = e.select("div.md").text();
            words.add(date + " , " + score + " , " + "\""+content+"\"");
        });
    }
}
