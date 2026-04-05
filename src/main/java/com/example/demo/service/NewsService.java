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

    public void criticalSecurityBug() {
        // 심각한 정적분석 오류를 일부러 발생시킨다.
        // 심각한 보안 취약점: 하드코딩된 비밀번호 (Vulnerability)
        String dbPassword = "super_secret_password_123";
        System.out.println("DB Password is: " + dbPassword);

        // 치명적인 버그: 100% 발생하는 Null 포인터 예외 (Bug)
        String nullObject = null;
        if (nullObject.equals("test_string")) {
            System.out.println("Never reach here");
        }

        // 코드 스멜: 예외 처리 무시 (Code Smell)
        try {
            int divisionByZero = 1 / 0;
        } catch (Exception e) {
            // 아무 조치도 취하지 않음
        }
    }

    public List<NewsItem> getLatestNews() {
        // 의도적인 심각한 버그 발생 메서드 호출
        criticalSecurityBug();

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
