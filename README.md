# My Personal Project - A Restaurant Tracker
## Proposal

I plan to build a restaurant tracker. Using this tracker, the user will be able to store information
about restaurants they have been to. Sometimes, it is difficult to remember which restaurants were good and which were
not, so this tracker can be something that the user refers back to often. They can choose whether they liked the
restaurant and write a list of items they liked with their prices, and a list of items they disliked.

The application can be useful for anyone, but those who visit many different restaurants often, may find it more useful.
Additionally, those who tend to be forgetful may find it useful too! Personally, one of the reasons that I am interested
in building this project is because find that I often forget the name of a good restaurant that I have been to, and 
sometimes forget the names of good food that were there. Having a tracker that I can refer back to often, is something 
that may be useful.

## User Stories

- As a user, I want to be able to add a new restaurant review to my liked or disliked restaurants.
- As a user, I want to be able to delete a review from my list of reviews.
- As a user, I want to be able to add tried foods and wishlist items to each review.
- As a user, I want to be able to see the restaurants in lists of liked or disliked restaurants.
- As a user, I want to be able to select the title of a restaurant review and be able to view and edit the review.
- As a user, I want to be able to have the option of sorting the reviews by rating or by order of creation.
- As a user, I want to be able to save my reviews to my file (if I so choose).
- As a user, I want to be able to load my reviews from my file (if I so choose). 

## Instructions For Grader

- You can generate the first required action related to the user story "adding multiply reviews to a list of reviews" 
by clicking on the " + " button in the top right corner of the screen after leaving the home page.
- You can generate the second required action related to the user story of viewing a filtered list of reviews, by 
leaving the home page and selecting an item from the combo box located in the centre top of the screen.
- You can locate my visual component when you first open the application. An image is displayed on the home page 
behind the two buttons. 
- You can save the application state after clicking X to close the application window. A dialog box will ask you 
whether you would like to save your progress.
- You can choose to load existing reviews on the home page when you first open the application by clicking 
on the "Load and add to my existing reviews" button. If you would like to write a new set of reviews, you can click the
"New Set of Reviews" button instead. 

#### Image Reference: 

- https://www.realsimple.com/holidays-entertaining/entertaining/everyday-celebrations/picnic-packing-checklist 
_(Edited)_ 

## Phase 4: Task 2
<br>Thu Apr 04 11:56:17 PDT 2024
<br> ShinytTteaaa review loaded to reviews.
<br>Thu Apr 04 11:56:17 PDT 2024
  <br> (L)-carvone review loaded to reviews.
<br>Thu Apr 04 11:56:17 PDT 2024
  <br> heyTeaaa review loaded to reviews.
<br>Thu Apr 04 11:56:17 PDT 2024
  <br> ChatTtime review loaded to reviews.
<br>Thu Apr 04 11:56:17 PDT 2024
  <br> heyTeaaa review added to list of liked reviews.
<br>Thu Apr 04 11:56:17 PDT 2024
  <br> ChatTtime review added to list of liked reviews.
<br>Thu Apr 04 11:56:17 PDT 2024
  <br> ShinytTteaaa review added to list of disliked reviews.
<br>Thu Apr 04 11:56:17 PDT 2024
  <br> (L)-carvone review added to list of disliked reviews.
<br>Thu Apr 04 11:56:28 PDT 2024
  <br> Disliked Reviews was sorted by rating.
<br>Thu Apr 04 11:56:32 PDT 2024
  <br> (L)-carvone review deleted from list of disliked reviews.
<br>Thu Apr 04 11:56:32 PDT 2024
  <br> (L)-carvone review deleted from list of all reviews.
<br>Thu Apr 04 11:56:39 PDT 2024
  <br> All Reviews was sorted by rating.
<br>Thu Apr 04 11:56:46 PDT 2024
  <br> (R)-carvone review added to list of disliked reviews.
<br>Thu Apr 04 11:56:46 PDT 2024
  <br> (R)-carvone review added to reviews.
<br>Thu Apr 04 11:57:06 PDT 2024
  <br> All Reviews was sorted by review number.
<br>Thu Apr 04 11:57:19 PDT 2024
  <br> Liked Reviews was sorted by rating.
<br>Thu Apr 04 11:57:25 PDT 2024
  <br> All reviews saved to the list of all reviews.
<br>Thu Apr 04 11:57:25 PDT 2024
  <br> Liked reviews saved to the list of liked reviews.
<br>Thu Apr 04 11:57:25 PDT 2024
  <br> Disliked reviews saved to the list of disliked reviews.

## Phase 4: Task 3

If I had more time to work on the project, I might redesign some classes in my model package, such as the Food and 
Reviews classes. Currently, both the wishlist foods and tried-foods have a set price and rating, however, it does not 
make a lot of sense for a wishlist item to have those fields set to something other than null. While the user is unable 
to see the wishlist item's price nor rating, I think that making the Food class abstract would solve the problem of 
wishlist items having unnecessary fields, as the tried-foods classes and wishlist food classes could extend the Food
class.

Another class to refactor may be the Reviews class. It currently contains 3 lists of reviews, where 2 of 3 lists are
'filtered' lists. I think that if I were to redesign this class, I would make it have 1 field of a collection of 
Restaurant reviews as it might be more efficient to add/delete restaurants to/from 1 list instead of 3. 
When filtering is needed, a method can be called that returns a filtered list of certain criteria.
