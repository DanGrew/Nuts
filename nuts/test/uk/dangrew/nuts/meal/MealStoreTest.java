package uk.dangrew.nuts.meal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class MealStoreTest {

   private Meal food;
   private MealStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      food = new Meal( "Meal" );
      systemUnderTest = new MealStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      Meal newFood = systemUnderTest.createFood( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      systemUnderTest.removeFood( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
   }//End Method

}//End Class
