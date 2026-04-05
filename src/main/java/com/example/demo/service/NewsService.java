package com.example.demo.service;

import com.example.demo.dto.NewsItem;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    private static final String GOOGLE_NEWS_RSS_URL = "https://news.google.com/rss/search?q=bitcoin=ko&gl=KR&ceid=KR:ko";

    public List<NewsItem> getLatestNews() {
        // 소나큐브 연동 테스트 4
        List<NewsItem> newsItems = new ArrayList<>();
        try {
            URL feedUrl = new URL(GOOGLE_NEWS_RSS_URL);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            for (SyndEntry entry : feed.getEntries()) {
                newsItems.add(new NewsItem(
                        entry.getTitle(),
                        entry.getLink(),
                        entry.getPublishedDate()));
                // limit to top 15 news for display
                if (newsItems.size() >= 15) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error minimally for sample
        }
        return newsItems;
    }
}
