package uk.dangrew.nuts.graphics.table;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.graphics.table.FoodTableRow;
import uk.dangrew.nuts.meal.Meal;

public class FoodTableRowTest {

   private Meal meal;
   private FoodTableRow< Meal > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      meal = new Meal( "Meal" );
      systemUnderTest = new FoodTableRow<>( meal );
   }//End Method

   @Test public void shouldProvideFood() {
      assertThat( systemUnderTest.food(), is( meal ) );
   }//End Method

}//End Class
