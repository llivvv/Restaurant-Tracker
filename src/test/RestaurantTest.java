import model.Food;
import model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantTest {

    private Restaurant testrestaurant;
    private Food drink;
    private Food dessert;
    private Food dish;
    private Food side;

    @BeforeEach
    public void runBefore() {
        testrestaurant = new Restaurant("Cafe", true);
        drink = new Food("BubbleTea", 5.50, true);
        dessert = new Food("Cake", 2.50, false);
        dish = new Food("Fish", 7.0, true);
        side = new Food("Fries", 3.75, false);
    }

    @Test
    public void FoodRatingTest() {
        dessert.setRating(3.2);
        assertEquals(0.0, dessert.getRating());
        dessert.makeTried();
        dessert.setRating(1.2);
        assertEquals(1.2, dessert.getRating());
    }

    @Test
    public void RestaurantRating1FoodTest()  {
        testrestaurant.addFoodToFoodList(drink);
        testrestaurant.createRating();
        assertEquals(0.0, testrestaurant.getRating());
        drink.setRating(4.5);
        testrestaurant.createRating();
        assertEquals(4.5, testrestaurant.getRating());
    }

//    @Test
//    public void RestaurantRating2FoodTest() {
//        testrestaurant.addFoodToFoodList(drink);
//        testrestaurant.addFoodToFoodList(dessert);
//        testrestaurant.addFoodToFoodList(dish);
    }



}
