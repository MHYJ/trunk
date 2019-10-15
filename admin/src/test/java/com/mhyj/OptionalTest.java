package com.mhyj;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author MHYJ
 * @date 2019/7/11 17:13
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
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

    @Test
    public void String() {
        String temp = "http://{0}:{1,number,#.#}{2,time,HH-mm}";
        String tempNew = MessageFormat.format(temp,"localhost",800.665,new Date());
        System.out.println(tempNew);
        log.info("{}","hello");
    }

}
