import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    private void AddRestaurantDetails() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        restaurant.openingTime=LocalTime.now().minusHours(6);
        restaurant.closingTime=LocalTime.now().plusHours(4);

        boolean restaurantIsOpen =  restaurant.isRestaurantOpen();
        assertTrue(restaurantIsOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        restaurant.openingTime=LocalTime.now().minusHours(6);
        restaurant.closingTime=LocalTime.now().minusHours(4);

        boolean restaurantIsOpen = restaurant.isRestaurantOpen();
        assertFalse(restaurantIsOpen);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }


    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<Order price>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void get_total_price_for_all_items_selected_in_menu(){

        List<Item> menu = restaurant.getMenu();
        List<String> selectedItems=new ArrayList<>();
        for(Item item : menu){
            selectedItems.add(item.getName());
        }

        int orderPrice = restaurant.calculateOrderPrice(selectedItems);
        assertEquals(388,orderPrice);


        // restaurant.calculateOrderPrice()
    }

    @Test
    public void get_total_price_when_no_items_selected(){

        List<String> selectedItems=new ArrayList<String>();
        int orderPrice = restaurant.calculateOrderPrice(selectedItems);

        assertEquals(0,orderPrice);
    }

    @Test
    public void get_total_price_when_one_item_selected(){

        List<Item> menu = restaurant.getMenu();
        List<String> selectedItems=new ArrayList<>();
        for(Item item : menu){
            selectedItems.add(item.getName());
        }
        selectedItems.remove(1);
        int orderPrice = restaurant.calculateOrderPrice(selectedItems);

        assertEquals(119,orderPrice);
    }
}