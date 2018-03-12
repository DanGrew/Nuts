package uk.dangrew.nuts.apis.tesco.database;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;
import uk.dangrew.nuts.apis.tesco.model.TescoFoodDescription;

public class TescoFoodDescriptionStoreTest {

   private TescoFoodDescription food;
   private TescoFoodDescriptionStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      food = new TescoFoodDescription( "Food" );
      systemUnderTest = new TescoFoodDescriptionStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      TescoFoodDescription newFood = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      systemUnderTest.removeConcept( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
   }//End Method

}//End Class
