package com.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

class ThreadCrawler implements Callable<Void> {
    private static final String SITE_URL = "old.reddit.com";
    private static final Set<String> VISITED = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private static final Logger LOGGER = Logger.getLogger(ThreadCrawler.class.getName());
    private final int maxDepth;
    private final ThreadPoolExecutor pool;
    private final CopyOnWriteArrayList<String> words;
    private final String url;
    private final int depth;


    public ThreadCrawler(String url, int maxDepth, int depth, ThreadPoolExecutor pool, CopyOnWriteArrayList<String>  list) {
        this.pool = pool;
        this.url = url;
        this.depth = depth;
        this.words = list;
        this.maxDepth = maxDepth;
    }

    @Override
    public Void call() {
        if(!url.contains(SITE_URL)) return null;
            if((!VISITED.contains(url) && (depth < maxDepth))) {
                try {
                    VISITED.add(url);
                    Document document = Jsoup.connect(url).get();
                    pool.execute(new CommentParser(document, words));
                    Elements nextUrls = document.select("a.bylink.comments.may-blank, [rel=\"nofollow next\"]");
                    nextUrls.forEach(page -> pool.submit(new ThreadCrawler(page.attr("abs:href"), maxDepth, depth+1, pool, words)));
                } catch(IOException e) {
                    LOGGER.log(Level.FINEST, "ThreadCrawler");
                }
            }
        return null;
    }


}
