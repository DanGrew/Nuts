package uk.dangrew.nuts.apis.tesco.database;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.apis.tesco.item.TescoFoodItem;

public class TescoFoodItemStoreTest {

   private TescoFoodItem food;
   private TescoFoodItemStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      food = new TescoFoodItem( "Food" );
      systemUnderTest = new TescoFoodItemStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      TescoFoodItem newFood = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      systemUnderTest.removeConcept( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
   }//End Method

}//End Class
