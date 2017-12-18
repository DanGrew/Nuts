package uk.dangrew.nuts.graphics.meal;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class MealTableControllerImplTest {

   private FoodPortion portion1;
   private FoodPortion portion2;
   private FoodPortion portion3;
   
   private Meal meal;
   private MealTable table; 
   private MealTableControllerImpl systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      
      meal = new Meal( "Meal" );
      meal.portions().add( portion1 = new FoodPortion( new FoodItem( "Food1" ), 100 ) );
      meal.portions().add( portion2 = new FoodPortion( new FoodItem( "Food2" ), 200 ) );
      meal.portions().add( portion3 = new FoodPortion( new FoodItem( "Food3" ), 50 ) );
      
      table = new MealTable( new Database() );
      systemUnderTest = new MealTableControllerImpl();
      systemUnderTest.associate( table );
      
      systemUnderTest.showMeal( meal );
   }//End Method

   @Test public void shouldMoveEntryUp() {
      assertThat( meal.portions(), contains( portion1, portion2, portion3 ) );
      assertTablePositions( portion1, portion2, portion3 );
      table.getSelectionModel().select( 1 );
      systemUnderTest.moveUp();
      assertThat( meal.portions(), contains( portion2, portion1, portion3 ) );
      assertTablePositions( portion2, portion1, portion3 );
   }//End Method
   
   @Test public void shouldMoveEntryDown() {
      assertThat( meal.portions(), contains( portion1, portion2, portion3 ) );
      table.getSelectionModel().select( 1 );
      systemUnderTest.moveDown();
      assertThat( meal.portions(), contains( portion1, portion3, portion2 ) );
      assertTablePositions( portion1, portion3, portion2 );
   }//End Method
   
   @Test public void shouldIgnoreMoveUpOnFirst(){
      assertThat( meal.portions(), contains( portion1, portion2, portion3 ) );
      table.getSelectionModel().select( 0 );
      systemUnderTest.moveUp();
      assertThat( meal.portions(), contains( portion1, portion2, portion3 ) );
      assertTablePositions( portion1, portion2, portion3 );
   }//End Method
   
   @Test public void shouldIgnoreMoveDownOnLast(){
      assertThat( meal.portions(), contains( portion1, portion2, portion3 ) );
      table.getSelectionModel().select( 2 );
      systemUnderTest.moveDown();
      assertThat( meal.portions(), contains( portion1, portion2, portion3 ) );
      assertTablePositions( portion1, portion2, portion3 );
   }//End Method
   
   @Test public void shouldIgnoreMoveUpWhenNoSelection(){
      assertThat( meal.portions(), contains( portion1, portion2, portion3 ) );
      systemUnderTest.moveUp();
      assertThat( meal.portions(), contains( portion1, portion2, portion3 ) );
      assertTablePositions( portion1, portion2, portion3 );
   }//End Method
   
   @Test public void shouldIgnoreMoveDownWhenNoSelection(){
      assertThat( meal.portions(), contains( portion1, portion2, portion3 ) );
      systemUnderTest.moveDown();
      assertThat( meal.portions(), contains( portion1, portion2, portion3 ) );
      assertTablePositions( portion1, portion2, portion3 );
   }//End Method
   
   @Test public void shouldCopySelection() {
      table.getSelectionModel().select( 1 );
      systemUnderTest.copySelectedConcept();
      assertThat( meal.portions(), hasSize( 4 ) );
      assertThat( meal.portions().get( 1 ).properties().nameProperty().get(), is( meal.portions().get( 3 ).properties().nameProperty().get() ) );
   }//End Method
   
   @Test public void shouldNotCopyWhenNoSelection() {
      systemUnderTest.copySelectedConcept();
      assertThat( meal.portions(), hasSize( 3 ) );
   }//End Method
   
   private void assertTablePositions( FoodPortion... foodPortions ) {
      for ( int i = 0; i < foodPortions.length; i++ ) {
         assertThat( table.getRows().get( i ).concept(), is( foodPortions[ i ] ) );
      }
   }//End Method

}//End Class
