//package model;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class Reviews {
//
//    private List<Restaurant> reviews;
//
//    public Reviews() {
//        reviews = new ArrayList<Restaurant>();
//    }
//
//    public void addRestaurant(Restaurant restaurant) {
//        if (!reviews.contains(restaurant)) {
//            reviews.add(restaurant);
//            findSetRestaurantReviewNumber(restaurant);
//        }
//    }
//
//
//    public Restaurant removeRestaurant(Restaurant restaurant) {
//        if (reviews.contains(restaurant)) {
//            return reviews.remove(reviews.indexOf(restaurant));
//        } else {
//            return null;
//        }
//    }
//
//    // MODIFIES: Restaurant
//    // EFFECTS: gives the restaurant a review number based on when it was created
//    public void findSetRestaurantReviewNumber(Restaurant restaurant) {
//        int max = 0;
//        for (Restaurant r : reviews) {
//            if (r.getReviewNumber() > max) {
//                max = r.getReviewNumber();
//            }
//        }
//        restaurant.setReviewNumber(max + 1);
//    }
//
//    // EFFECTS: finds and returns a restaurant review with the corresponding name, otherwise returns null
//    public Restaurant findRestaurant(String restaurantName) {
//        for (Restaurant r : reviews) {
//            if (restaurantName.equals(r.getRestaurantName())) {
//                return r;
//            }
//        }
//        return null;
//    }
//
//    // MODIFIES: Restaurant
//    // EFFECTS: sets a new name for the restaurant review if there is no other review with the same name
//    //          if desired name was the same as its original name or the name was successfully changed,
//    //          returns true; returns false if the name was not successfully changed
//    public boolean checkandSetNewRname(String restaurantName, Restaurant restaurant) {
//        if (restaurantName.equals(restaurant.getRestaurantName())) {
//            return true;
//        } else if (findRestaurant(restaurantName) == null) {
//            restaurant.setRestaurantName(restaurantName);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: sorts all reviews based on rating (ascending order)
//    public void sortAllReviews() {
//        Collections.sort(reviews, new RestaurantRatingComparator());
//    }
//
//    public List<Restaurant> getLikedReviews() {
//        List<Restaurant> likedReviews = new ArrayList<Restaurant>();
//        for (Restaurant r : reviews) {
//            if (r.getIsLiked()) {
//                likedReviews.add(r);
//            }
//        }
//        return likedReviews;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: sorts liked reviews based on rating (ascending order)
//    public void sortLikedReviews() {
//        Collections.sort(getLikedReviews(), new RestaurantRatingComparator());
//    }
//
//    public List<Restaurant> getDislikedReviews() {
//        List<Restaurant> dislikedReviews = new ArrayList<Restaurant>();
//        for (Restaurant r : reviews) {
//            if (!r.getIsLiked()) {
//                dislikedReviews.add(r);
//            }
//        }
//        return dislikedReviews;
//    }
//
//    public void sortDislikedReviews() {
//        Collections.sort(getDislikedReviews(), new RestaurantRatingComparator());
//    }
//
//    // MODIFIES: this
//    // EFFECTS: sorts allRestaurants by descending review number
//    public void sortAllRestaurantsNumber() {
//        Collections.sort(reviews, new RestaurantNumberComparator());
//        Collections.reverse(reviews);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: sorts liked reviews by descending review number
//    public void sortLikedReviewsByNum() {
//        List<Restaurant> likedReviews = getLikedReviews();
//        Collections.sort(likedReviews, new RestaurantNumberComparator());
//        Collections.reverse(likedReviews);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: sorts disliked reviews by descending review number
//    public void sortDislikedReviewsByNum() {
//        List<Restaurant> dislikedReviews =
//        Collections.sort((), new RestaurantNumberComparator());
//        Collections.reverse(allRestaurants);
//    }
//
//    public List<Restaurant> getReviews() {
//        return reviews;
//    }
//
//    public int getNumReviews() {
//        return reviews.size();
//    }
//}
