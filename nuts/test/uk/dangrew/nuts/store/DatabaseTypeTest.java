package uk.dangrew.nuts.store;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class DatabaseTypeTest {

   private Database database;
   
   @Before public void initialiseSystemUnderTest(){
      database = new Database();
   }//End Method
   
   @Test public void shouldRedirectToConcepts() {
      assertThat( DatabaseType.FoodItems.redirect( database ), is( database.foodItems() ) );
      assertThat( DatabaseType.Meals.redirect( database ), is( database.meals() ) );
      assertThat( DatabaseType.Templates.redirect( database ), is( database.templates() ) );
      assertThat( DatabaseType.CalorieGoals.redirect( database ), is( database.calorieGoals() ) );
      assertThat( DatabaseType.ProportionGoals.redirect( database ), is( database.proportionGoals() ) );
   }//End Method

}//End Class
