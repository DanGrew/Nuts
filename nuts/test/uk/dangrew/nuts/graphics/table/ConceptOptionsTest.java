package uk.dangrew.nuts.graphics.table;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.comparator.Comparators;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class ConceptOptionsTest {

   private FoodItem foodItem1;
   private FoodItem foodItem2;
   private Meal meal1;
   private Meal meal2;
   
   private Database database;
   private ConceptOptions< Food > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      foodItem1 = new FoodItem( "Item1" );
      foodItem2 = new FoodItem( "Item2" );
      meal1 = new Meal( "Meal1" );
      meal2 = new Meal( "Meal2" );
      
      database = new Database();
      systemUnderTest = new ConceptOptions<>( Arrays.asList( database.foodItems(), database.meals() ) );
   }//End Method

   @Test public void shouldProvideCombinedFoods() {
      assertThat( systemUnderTest.options(), is( notNullValue() ) );
      assertThat( systemUnderTest.options(), is( systemUnderTest.options() ) );
   }//End Method
   
   @Test public void shouldIncludedAddedFoodItems() {
      database.foodItems().store( foodItem1 );
      assertThat( systemUnderTest.options(), contains( foodItem1 ) );
      database.foodItems().store( foodItem2 );
      assertThat( systemUnderTest.options(), contains( foodItem1, foodItem2 ) );
   }//End Method
   
   @Test public void shouldNotDuplicateFoodItems() {
      database.foodItems().store( foodItem1 );
      database.foodItems().store( foodItem1 );
      database.foodItems().store( foodItem1 );
      assertThat( systemUnderTest.options(), contains( foodItem1 ) );
   }//End Method
   
   @Test public void shouldExcludeRemovedFoodItem() {
      database.foodItems().store( foodItem1 );
      database.foodItems().store( foodItem2 );
      assertThat( systemUnderTest.options(), contains( foodItem1, foodItem2 ) );
      database.foodItems().removeConcept( foodItem1 );
      assertThat( systemUnderTest.options(), contains( foodItem2 ) );
      database.foodItems().removeConcept( foodItem2 );
      assertThat( systemUnderTest.options(), is( empty() ) );
   }//End Method
   
   @Test public void shouldIncludedAddedMeals() {
      database.meals().store( meal1 );
      assertThat( systemUnderTest.options(), contains( meal1 ) );
      database.meals().store( meal2 );
      assertThat( systemUnderTest.options(), contains( meal1, meal2 ) );
   }//End Method
   
   @Test public void shouldNotDuplicateMeals() {
      database.meals().store( meal1 );
      database.meals().store( meal1 );
      database.meals().store( meal1 );
      assertThat( systemUnderTest.options(), contains( meal1 ) );
   }//End Method
   
   @Test public void shouldExcludeRemovedMeals() {
      database.meals().store( meal1 );
      database.meals().store( meal2 );
      assertThat( systemUnderTest.options(), contains( meal1, meal2 ) );
      database.meals().removeConcept( meal1 );
      assertThat( systemUnderTest.options(), contains( meal2 ) );
      database.meals().removeConcept( meal2 );
      assertThat( systemUnderTest.options(), is( empty() ) );
   }//End Method
   
   @Test public void shouldSortFoodAlphabeticallyByName(){
      FoodItem item1 = new FoodItem( "My Food" );
      FoodItem item2 = new FoodItem( "Your Food" );
      FoodItem item3 = new FoodItem( "His Food" );
      
      Meal meal1 = new Meal( "Her Food" );
      Meal meal2 = new Meal( "Our Food" );
      Meal meal3 = new Meal( "World Food" );
      
      database.foodItems().store( item1 );
      assertThat( systemUnderTest.options(), contains( item1 ) );
      database.foodItems().store( item2 );
      assertThat( systemUnderTest.options(), contains( item1, item2 ) );
      database.foodItems().store( item3 );
      assertThat( systemUnderTest.options(), contains( item3, item1, item2 ) );
      
      database.meals().store( meal1 );
      assertThat( systemUnderTest.options(), contains( meal1, item3, item1, item2 ) );
      database.meals().store( meal2 );
      assertThat( systemUnderTest.options(), contains( meal1, item3, item1, meal2, item2 ) );
      database.meals().store( meal3 );
      assertThat( systemUnderTest.options(), contains( meal1, item3, item1, meal2, meal3, item2 ) );
   }//End Method
   
   @Test public void shouldCustomSortFood(){
      systemUnderTest.customSort( ( a, b ) -> 
         Comparators.reverseComparator( Comparators.STRING_ALPHABETICAL ).compare( 
                     a.properties().nameProperty().get(), 
                     b.properties().nameProperty().get() 
      ) );
      
      FoodItem item1 = new FoodItem( "My Food" );
      FoodItem item2 = new FoodItem( "Your Food" );
      FoodItem item3 = new FoodItem( "His Food" );
      
      Meal meal1 = new Meal( "Her Food" );
      Meal meal2 = new Meal( "Our Food" );
      Meal meal3 = new Meal( "World Food" );
      
      database.foodItems().store( item1 );
      assertThat( systemUnderTest.options(), contains( item1 ) );
      database.foodItems().store( item2 );
      assertThat( systemUnderTest.options(), contains( item2, item1 ) );
      database.foodItems().store( item3 );
      assertThat( systemUnderTest.options(), contains( item2, item1, item3  ) );
      
      database.meals().store( meal1 );
      assertThat( systemUnderTest.options(), contains( item2, item1, item3, meal1 ) );
      database.meals().store( meal2 );
      assertThat( systemUnderTest.options(), contains( item2, meal2, item1, item3, meal1 ) );
      database.meals().store( meal3 );
      assertThat( systemUnderTest.options(), contains( item2, meal3, meal2, item1, item3, meal1 ) );
   }//End Method
   
   @Test public void shouldFindFirstMatch(){
      FoodItem item1 = new FoodItem( "One" );
      FoodItem item2 = new FoodItem( "Two" );
      FoodItem item3 = new FoodItem( "Three" );
      
      Meal meal1 = new Meal( "Four" );
      Meal meal2 = new Meal( "Five" );
      Meal meal3 = new Meal( "Three" );
      
      database.foodItems().store( item1 );
      database.foodItems().store( item2 );
      database.foodItems().store( item3 );
      database.meals().store( meal1 );
      database.meals().store( meal2 );
      database.meals().store( meal3 );
      
      assertThat( systemUnderTest.find( "One" ), is( item1 ) );
      assertThat( systemUnderTest.find( "Four" ), is( meal1 ) );
      assertThat( systemUnderTest.find( "Three" ), is( item3 ) );
      assertThat( systemUnderTest.find( "something else" ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldPopulateFoodsInitially(){
      database.foodItems().store( foodItem1 );
      database.meals().store( meal1 );
      systemUnderTest = new ConceptOptions<>( Arrays.asList( database.foodItems(), database.meals() ) );
      assertThat( systemUnderTest.options(), contains( foodItem1, meal1 ) );
   }//End Method
   
   @Test public void shouldProvideFirst(){
      assertThat( systemUnderTest.first(), is( nullValue() ) );
      
      database.foodItems().store( foodItem1 );
      database.meals().store( meal1 );
      systemUnderTest = new ConceptOptions<>( Arrays.asList( database.foodItems(), database.meals() ) );
      assertThat( systemUnderTest.first(), is( foodItem1 ) );
   }//End Method
   
   @Test public void shouldRespondToNameChange(){
      database.foodItems().store( foodItem1 );
      database.foodItems().store( foodItem2 );
      
      FoodItem alphaItem = new FoodItem( "zzz" );
      database.foodItems().store( alphaItem );
      
      assertThat( systemUnderTest.options().get( 2 ), is( alphaItem ) );
      alphaItem.properties().nameProperty().set( "AAA" );
      assertThat( systemUnderTest.options().get( 0 ), is( alphaItem ) );
   }//End Method
   
   @Test public void shouldNotRespondToNameChangeWhenRemoved(){
      database.foodItems().store( foodItem1 );
      database.foodItems().store( foodItem2 );
      
      FoodItem alphaItem = new FoodItem( "zzz" );
      database.foodItems().store( alphaItem );
      
      assertThat( systemUnderTest.options().get( 2 ), is( alphaItem ) );
      database.foodItems().remove( alphaItem.properties().id() );
      alphaItem.properties().nameProperty().set( "AAA" );
      assertThat( systemUnderTest.options().contains( alphaItem ), is( false ) );
   }//End Method

}//End Class
