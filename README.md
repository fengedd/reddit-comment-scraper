# Reddit Comment Scraper

App that crawls comment threads asynchronously starting from the given URL (e.g https://old.reddit.com/r/all/top) and ending at the given number of pages to crawl. Output is in .txt format with the following categories: date, upvotes, and comment. Made with jsoup.

## How it works

## Installation
In the reddit-comment-scraper directory type:

```sh
> java -jar Scraper.jar
```
## Usage
Use "https://old.reddit.com/" url only. Scraping will take some time depending on the number of pages to be scraped.
```sh
> Please type subreddit (old.reddit) url to scrape e.g. 'https://old.reddit.com/': 
https://old.reddit.com/r/funny
> Please enter number of pages to scrape:
25
```

Output:
```sh
Date                       Score  Comment
2018-12-15T00:33:14+00:00 , 136 , "Don't be that guy mate"
2018-12-15T00:38:59+00:00 , 90 , "Don't be that mate guy"
2018-12-15T00:42:09+00:00 , 57 , "Don’t mate that guy, b"
2018-12-15T00:49:41+00:00 , 21 , "Don't guy that mate"
2018-12-15T01:46:52+00:00 , 12 , "Guy dont mate that"
2018-12-15T01:54:10+00:00 , 11 , "That guy don't mate"
2018-12-15T02:04:48+00:00 , 5 , "that don't mate guy"
2018-12-15T00:42:59+00:00 , 21 , "Youre not my mate bro"
2018-12-15T00:53:44+00:00 , 9 , "I not your bro guy"
2018-12-15T00:55:19+00:00 , 13 , "Im not your guy, buddy!"
2018-12-15T00:57:06+00:00 , 6 , "I’m not your buddy, pal!"
```

## License
[MIT](https://choosealicense.com/licenses/mit/)