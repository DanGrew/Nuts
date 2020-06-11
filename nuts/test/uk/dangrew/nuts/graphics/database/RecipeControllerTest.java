package uk.dangrew.nuts.graphics.database;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;

public class RecipeControllerTest {

   private Meal food;
   
   @Mock private RecipeSummaryWindow window;
   private RecipeShareControllerImpl systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      food = new Meal( "Meal" );
      JavaFxThreading.runAndWait( () -> {
         systemUnderTest = new RecipeShareControllerImpl( window );
      } );
   }//End Method

   @Test public void shouldNotShareWhenNoSelection() {
      systemUnderTest.share( null );
      verifyZeroInteractions( window );
   }//End Method
   
   @Test public void shouldNotShareWhenSelectedNotMeal() {
      systemUnderTest.share( new FoodItem( "Item" ) );
      verifyZeroInteractions( window );
   }//End Method
   
   @Test public void shouldShareSelection() {
      systemUnderTest.share( food );
      verify( window ).show( food );
   }//End Method

}//End Class
