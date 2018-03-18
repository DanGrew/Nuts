package uk.dangrew.nuts.label;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.store.Database;

public class LabelablesTest {

   private Database database;
   
   @Before public void initialiseSystemUnderTest(){
      database = new Database();
   }//End Method
   
   @Test public void shouldRedirectToConcepts() {
      assertThat( Labelables.FoodItems.redirect( database ), is( database.foodItems() ) );
      assertThat( Labelables.Meals.redirect( database ), is( database.meals() ) );
      assertThat( Labelables.Goals.redirect( database ), is( database.goals() ) );
   }//End Method

}//End Class
