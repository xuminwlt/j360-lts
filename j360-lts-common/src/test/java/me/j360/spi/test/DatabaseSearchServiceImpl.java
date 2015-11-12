package me.j360.spi.test;

import java.util.List;

/**
 * Created with j360-lts -> me.j360.spi.test.
 * User: min_xu
 * Date: 2015/11/12
 * Time: 13:39
 * 说明：
 */
public class DatabaseSearchServiceImpl implements SearchService{

    @Override
    public List<Doc> search(String keyword) {
        System.out.println("now use database search. keyword:" + keyword);
        return null;
    }
}
