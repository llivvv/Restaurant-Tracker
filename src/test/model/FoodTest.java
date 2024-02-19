package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FoodTest {

    private Food testfoodtried;
    private Food testfoodwish;

    @BeforeEach
    public void runBefore() {
        testfoodtried = new Food("tea", 7, true);
        testfoodwish = new Food("melon", 2, false);
    }

    @Test
    public void setRatingWishTest() {
        testfoodwish.setRating(2);
        assertEquals(0, testfoodwish.getRating());
    }

    @Test
    public void setRatingTriedTest() {
        testfoodtried.setRating(3);
        assertEquals(3, testfoodtried.getRating());
    }

    @Test
    public void setRatingTried0Test() {
        testfoodtried.setRating(0);
        assertEquals(0, testfoodtried.getRating());
    }

    @Test
    public void setRatingTried5Test() {
        testfoodtried.setRating(5);
        assertEquals(5, testfoodtried.getRating());
    }

    @Test
    public void setRatingTried6Test() {
        testfoodtried.setRating(6);
        assertEquals(5, testfoodtried.getRating());
    }

    @Test
    public void setRatingTriedNegTest() {
        testfoodtried.setRating(-1);
        assertEquals(0, testfoodtried.getRating());
    }

    @Test
    public void makeTriedTest() {
        assertFalse(testfoodwish.getisTried());
        testfoodwish.makeTried();
        assertTrue(testfoodwish.getisTried());
    }

    @Test
    public void setNameTest() {
        testfoodtried.setName("yo");
        assertEquals("yo", testfoodtried.getName());
    }

    @Test
    public void setPriceTest() {
        testfoodtried.setPrice(1.2);
        assertEquals(1.2, testfoodtried.getPrice());
    }
}
