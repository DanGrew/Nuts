package uk.dangrew.nuts.graphics.database;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class FoodTypesTest {

   private Database database;
   
   @Before public void initialiseSystemUnderTest(){
      database = new Database();
   }//End Method
   
   @Test public void shouldRedirectToConcepts() {
      assertThat( FoodTypes.FoodItems.redirect( database ), is( database.foodItems() ) );
      assertThat( FoodTypes.Meals.redirect( database ), is( database.meals() ) );
      assertThat( FoodTypes.Templates.redirect( database ), is( database.templates() ) );
   }//End Method
   
   @Test public void shouldMapBackToTypes(){
      assertThat( FoodTypes.typeOf( mock( FoodItem.class ) ), is( FoodTypes.FoodItems ) );
      assertThat( FoodTypes.typeOf( mock( Meal.class ) ), is( FoodTypes.Meals ) );
      assertThat( FoodTypes.typeOf( mock( Template.class ) ), is( FoodTypes.Templates ) );
   }//End Method

}//End Class
