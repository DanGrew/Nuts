package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;

public class UiFoodSelectionControllerTest {

   private Meal meal;
   private UiFoodSelectionController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      meal = new Meal( "Meal" );
      systemUnderTest = new UiFoodSelectionController( meal );
   }//End Method

   @Test public void shouldAddPortionToMealByDuplicating() {
      FoodPortion portion = new FoodPortion( new FoodItem( "anything" ), 134.4 );
      systemUnderTest.addPortion( portion );
      assertThat( meal.portions(), hasSize( 1 ) );
      assertThat( meal.portions().get( 0 ), is( not( portion ) ) );
      assertThat( meal.portions().get( 0 ).food().get(), is( portion.food().get() ) );
      assertThat( meal.portions().get( 0 ).portion().get(), is( portion.portion().get() ) );
   }//End Method

}//End Class
