package uk.dangrew.nuts.graphics.meal;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.event.structure.Event;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.main.OpenTabEvent;
import uk.dangrew.nuts.graphics.main.TabDefinition;
import uk.dangrew.nuts.graphics.selection.UiMealFoodSelectionPane;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class MealTableControllerImplTest {

   private FoodPortion portion1;
   private FoodPortion portion2;
   private FoodPortion portion3;
   
   @Mock private OpenTabEvent openTabEvents;
   @Captor private ArgumentCaptor< Event< TabDefinition > > eventCaptor;
   
   private Meal meal;
   private MealTable table; 
   private MealTableControllerImpl systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      meal = new Meal( "Meal" );
      meal.portions().add( portion1 = new FoodPortion( new FoodItem( "Food1" ), 100 ) );
      meal.portions().add( portion2 = new FoodPortion( new FoodItem( "Food2" ), 200 ) );
      meal.portions().add( portion3 = new FoodPortion( new FoodItem( "Food3" ), 50 ) );
      
      Database database = new Database();
      table = new MealTable( database );
      systemUnderTest = new MealTableControllerImpl( database, openTabEvents );
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
   
   @Test public void shouldOpenTab() {
      systemUnderTest.openTab();
      verify( openTabEvents ).fire( eventCaptor.capture() );
      assertThat( eventCaptor.getValue().getValue().title(), is( meal.properties().nameProperty().get() ) );
      assertThat( eventCaptor.getValue().getValue().node(), is( instanceOf( UiMealFoodSelectionPane.class ) ) );
   }//End Method
   
   @Test public void shouldNotOpenTabWhenNoSelection() {
      systemUnderTest.showMeal( null );
      systemUnderTest.openTab();
      verify( openTabEvents, never() ).fire( Mockito.any() );
   }//End Method

}//End Class
