package me.j360.spi.test;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created with j360-lts -> me.j360.spi.test.
 * User: min_xu
 * Date: 2015/11/12
 * Time: 13:39
 * 说明：
 */
public class SearchServiceTest {
    public static void main(String[] args){
        ServiceLoader<SearchService> s = ServiceLoader.load(SearchService.class);
        Iterator<SearchService> searchs = s.iterator();
        if(searchs.hasNext()) {
            SearchService curSearch = searchs.next();
            curSearch.search("test");
        }
        System.out.println("------------------------------");
        SearchServiceFactory.getSearchService("test");
    }
}
