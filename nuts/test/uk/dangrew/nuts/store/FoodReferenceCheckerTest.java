package uk.dangrew.nuts.store;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.startsWith;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.stock.Stock;
import uk.dangrew.nuts.template.Template;

public class FoodReferenceCheckerTest {

   private Database database;
   
   private Meal meal1;
   private Meal meal2;
   private Meal meal3;
   
   private Template template1;
   private Template template2;
   private Template template3;
   
   private DayPlan dayPlan1;
   private DayPlan dayPlan2;
   private DayPlan dayPlan3;
   
   private Meal shoppingList1;
   private Meal shoppingList2;
   
   private Label label1;
   private Label label2;
   
   private Stock stock;
   
   private FoodItem searchFor;
   private FoodReferenceChecker systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      
      database.meals().store( meal1 = new Meal( "Meal1" ) );
      database.meals().store( meal2 = new Meal( "Meal2" ) );
      database.meals().store( meal3 = new Meal( "Meal3" ) );
      
      database.templates().store( template1 = new Template( "Template1" ) );
      database.templates().store( template2 = new Template( "Template2" ) );
      database.templates().store( template3 = new Template( "Template3" ) );
      
      database.dayPlans().store( dayPlan1 = new DayPlan( LocalDate.now() ) );
      database.dayPlans().store( dayPlan2 = new DayPlan( LocalDate.now().plusDays( 1 ) ) );
      database.dayPlans().store( dayPlan3 = new DayPlan( LocalDate.now().plusDays( 2 ) ) );
      
      database.shoppingLists().store( shoppingList1 = new Meal( "Shopping1" ) );
      database.shoppingLists().store( shoppingList2 = new Meal( "Shopping2" ) );
      
      database.labels().store( label1 = new Label( "Label1" ) );
      database.labels().store( label2 = new Label( "Label2" ) );
      
      database.stockLists().store( stock = new Stock( "Stock" ) );
      database.foodItems().store( searchFor = new FoodItem( "Anything" ) );
      
      systemUnderTest = new FoodReferenceChecker( database );
      
      meal1.portions().add( new FoodPortion( searchFor, 100 ) );
      meal3.portions().add( new FoodPortion( searchFor, 100 ) );
      template2.portions().add( new FoodPortion( searchFor, 100 ) );
      template3.portions().add( new FoodPortion( searchFor, 100 ) );
      dayPlan1.portions().add( new FoodPortion( searchFor, 100 ) );
      dayPlan2.portions().add( new FoodPortion( searchFor, 100 ) );
      shoppingList1.portions().add( new FoodPortion( searchFor, 100 ) );
      label1.concepts().add( searchFor );
      stock.linkWithFoodItems( database.foodItems() );
   }//End Method

   @Test public void shouldFindAndCacheReferencesToFood() {
      systemUnderTest.searchFor( searchFor );
      assertThat( systemUnderTest.lastSearchResult(), containsInAnyOrder(  
               meal1, meal3, template2, template3, dayPlan1, dayPlan2, shoppingList1, label1, stock
      ) );
      assertThat( systemUnderTest.lastSearchResult(), hasSize( 9 ) );
      
      //check avoid duplicates
      systemUnderTest.searchFor( searchFor );
      assertThat( systemUnderTest.lastSearchResult(), containsInAnyOrder( 
               meal1, meal3, template2, template3, dayPlan1, dayPlan2, shoppingList1, label1, stock
      ) );
      assertThat( systemUnderTest.lastSearchResult(), hasSize( 9 ) );
   }//End Method
   
   @Test public void shouldClearCacheBetweenSearches() {
      systemUnderTest.searchFor( searchFor );
      assertThat( systemUnderTest.lastSearchResult(), containsInAnyOrder(  
               meal1, meal3, template2, template3, dayPlan1, dayPlan2, shoppingList1, label1, stock 
      ) );
      
      systemUnderTest.searchFor( new FoodItem( "Not Found" ) );
      assertThat( systemUnderTest.lastSearchResult(), is( empty() ) );
   }//End Method
   
   @Test public void shouldRemoveReferences(){
      systemUnderTest.searchFor( searchFor );
      systemUnderTest.removeReferences();
      assertThat( systemUnderTest.lastSearchResult(), is( empty() ) );
      
      systemUnderTest.searchFor( searchFor );
      assertThat( systemUnderTest.lastSearchResult(), is( empty() ) );
   }//End Method
   
   @Test public void shouldIgnoreRemoveWhenNoSearchFor(){
      systemUnderTest.removeReferences();
      shouldFindAndCacheReferencesToFood();
   }//End Method
   
   @Test public void shouldReplaceReferences(){
      systemUnderTest.searchFor( searchFor );
      
      Food replacement = new FoodItem( "Replacement" );
      systemUnderTest.replaceWith( replacement );
      assertThat( systemUnderTest.lastSearchResult(), is( empty() ) );
      
      systemUnderTest.searchFor( replacement );
      assertThat( systemUnderTest.lastSearchResult(), containsInAnyOrder( 
               meal1, meal3, template2, template3, dayPlan1, dayPlan2, shoppingList1, label1, stock
      ) );
      
      systemUnderTest.searchFor( searchFor );
      assertThat( systemUnderTest.lastSearchResult(), is( empty() ) );
   }//End Method
   
   @Test public void shouldIgnoreReplaceWhenNoSearchFor(){
      systemUnderTest.replaceWith( new FoodItem( "replacement" ) );
      shouldFindAndCacheReferencesToFood();
   }//End Method
}//End Class
