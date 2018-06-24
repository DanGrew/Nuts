package uk.dangrew.nuts.nutrients;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;

public class NutritionalUnitTest {

   private Food food;
   
   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
     
      food = new FoodItem( "anything" );
   }//End Method
   
   @Test public void shouldProvideProperty() {
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( unit.of( food ).property(), is( food.nutrition().of( unit ) ) );
         assertThat( unit.of( food.foodAnalytics() ).property(), is( food.foodAnalytics().of( unit ) ) );
         assertThat( unit.of( food.nutrition() ).property(), is( food.nutrition().of( unit ) ) );
      }
   }//End Method
   
   @Test public void shouldHandleEmptyRequests(){
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( unit.of( ( Food )null ).property(), is( nullValue() ) );
      }
   }//End Method
   
   @Test public void shouldSetProperty() {
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         unit.of( food ).set( 20.0 );
         assertThat( unit.of( food ).get(), is( 20.0 ) );
      }
   }//End Method
   
   @Test public void shouldProvideMacros(){
      assertThat( NutritionalUnit.macros(), contains( 
               NutritionalUnit.Carbohydrate, 
               NutritionalUnit.Fat, 
               NutritionalUnit.Protein 
      ) );
   }//End Method
   
}//End Class
