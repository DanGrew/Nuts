package uk.dangrew.nuts.food;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class FoodItemStoreTest {

   private FoodItem food;
   private FoodItemStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      food = new FoodItem( "Food" );
      systemUnderTest = new FoodItemStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      FoodItem newFood = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      systemUnderTest.removeConcept( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
   }//End Method
   
}//End Class
