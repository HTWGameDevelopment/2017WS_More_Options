package com.moreoptions.prototype.level

import org.junit.Test

/**
 * Created by Andreas on 02.11.2017.
 */
class MapTest extends GroovyTestCase {

    @Test
    public void test() {
        for(int i = 0; i < 1000000000; i++) {
            Map m = new Map(10,10,40);
            System.out.println(i);
        }
    }

}
