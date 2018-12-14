package com.scraper;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class Driver {

    public Driver(String[] args) {
        main(args);
    }

    public static void main(String[] args) {
        String regex = "^(https:\\/\\/old.reddit.com\\/){1}(new|rising|top|controversial|r|)?((?!wiki\\/index)(\\/|\\&|\\?|\\=|\\w))*$";
        String urlInput, numPagesInput;
        try {
            do {
                System.out.print("Please subreddit (old.reddit) url to scrape e.g. 'https://old.reddit.com/' and pages to scrape: ");
                Scanner sc = new Scanner(System.in);
                urlInput = sc.next();
            } while(!urlInput.matches(regex));

            do {
                System.out.print("Please enter number of pages to scrape: ");
                Scanner sc1 = new Scanner(System.in);
                numPagesInput = sc1.next();
            } while(!numPagesInput.matches("\\d{1,2}"));

            ThreadPoolExecutor pool = new ThreadPoolExecutor(2000, 2000,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>());
            CopyOnWriteArrayList<String> result = new CopyOnWriteArrayList<>();
            pool.submit(new ThreadCrawler(urlInput, Integer.valueOf(numPagesInput), 0,  pool, result));
            pool.submit(new ThreadPoolCloser(pool, System.currentTimeMillis()));
            try {
                pool.awaitTermination(1L, TimeUnit.HOURS);
                Path file = Paths.get(Instant.now().hashCode()+".txt");
                Files.write(file, result, Charset.forName("UTF-8"));
            } catch(Exception e) {
                e.printStackTrace();
            }
        } finally {
            System.exit(0);
        }
    }
}
