package uk.dangrew.nuts.store;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.storage.structure.MappedObservableStoreManagerImpl;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;

public class DatabaseTest {

   private Database systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new Database();
   }//End Method

   @Test public void shouldProvideMappedFoods() {
      assertThat( systemUnderTest.foodItems(), is( instanceOf( MappedObservableStoreManagerImpl.class ) ) );
      
      FoodItem foodItem = new FoodItem( "Anything" );
      systemUnderTest.foodItems().store( foodItem );
      assertThat( systemUnderTest.foodItems().get( foodItem.properties().id() ), is( foodItem ) );
   }//End Method
   
   @Test public void shouldProvideMappedMeals() {
      assertThat( systemUnderTest.meals(), is( instanceOf( MappedObservableStoreManagerImpl.class ) ) );
      
      Meal meal = new Meal( "Meal" );
      systemUnderTest.meals().store( meal );
      assertThat( systemUnderTest.meals().get( meal.properties().id() ), is( meal ) );
   }//End Method
   
   @Test public void shouldProvideGoal(){
      assertThat( systemUnderTest.goal(), is( not( nullValue() ) ) );
      assertThat( systemUnderTest.goal(), is( systemUnderTest.goal() ) );
   }//End Method

}//End Class
