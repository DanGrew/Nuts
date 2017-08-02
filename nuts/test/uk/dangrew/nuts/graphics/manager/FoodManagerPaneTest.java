package uk.dangrew.nuts.graphics.manager;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class FoodManagerPaneTest {

   @Spy private JavaFxStyle styling;
   private Database database;
   private FoodManagerPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      database = new Database();
      systemUnderTest = new FoodManagerPane( styling, database );
   }//End Method

   @Test public void shouldConfigureRowsAndColumns() {
      verify( styling ).configureConstraintsForRowPercentages( 
               systemUnderTest, 
               FoodManagerPane.FOOD_ITEMS_HEIGHT_PROPORTION,
               FoodManagerPane.MEALS_HEIGHT_PROPORTION,
               FoodManagerPane.MEAL_VIEW_HEIGHT_PROPORTION 
      );
      
      verify( styling ).configureConstraintsForEvenColumns( systemUnderTest, 1 );
   }//End Method
   
   @Test public void shouldProvideContent(){
      assertThat( systemUnderTest.foodItemsTable(), is( notNullValue() ) );
      assertThat( systemUnderTest.mealsTable(), is( notNullValue() ) );
      assertThat( systemUnderTest.mealView(), is( notNullValue() ) );
      
      assertThat( systemUnderTest.getChildren(), contains( 
               systemUnderTest.foodItemsTable(),
               systemUnderTest.mealsTable(),
               systemUnderTest.mealView()
      ) );
   }//End Method
   
   @Test public void shouldShowMealsOnSelection(){
      Meal meal = new Meal( "Meal" );
      database.meals().store( meal );
      
      assertThat( systemUnderTest.mealsTable().table().getSelectionModel().getSelectedItem(), is( nullValue() ) );
      assertThat( systemUnderTest.mealView().table().controller().getShowingMeal(), is( nullValue() ) );
      
      systemUnderTest.mealsTable().table().getSelectionModel().select( 0 );
      
      assertThat( systemUnderTest.mealView().table().controller().getShowingMeal(), is( meal ) );
   }//End Method

}//End Class
