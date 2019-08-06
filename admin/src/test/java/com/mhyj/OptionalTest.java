package com.mhyj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author MHYJ
 * @date 2019/7/11 17:13
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class OptionalTest {

    @Test
    public void test() {
        Integer i = 1;
        Optional<Integer> optional = Optional.ofNullable(null);
        System.out.println(optional.isPresent());
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1,1);
        map.put(1,2);
        System.out.println(map.get(1));
    }

}
