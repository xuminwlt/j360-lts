package me.j360.spi.test;

import java.util.ServiceLoader;

/**
 * Created with j360-lts -> me.j360.spi.test.
 * User: min_xu
 * Date: 2015/11/12
 * Time: 13:45
 * 说明：
 */
public class SearchServiceFactory {
    private static ServiceLoader<SearchService> searchServiceLoader = ServiceLoader.load(SearchService.class);

    public static void getSearchService(String name){
        for(SearchService searchService:searchServiceLoader){
            //System.out.println(searchService.search(name));
        }
    }
}
