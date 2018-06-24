package uk.dangrew.nuts.nutrients;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.beans.value.ChangeListener;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;

public class NutritionTest {

   private Nutrition systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new Nutrition();
   }//End Method

   @Test public void shouldProvideNutritionForEachNutrient() {
      for ( NutritionalUnit nutritionalUnit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.of( nutritionalUnit ), is( notNullValue() ) );
         assertThat( systemUnderTest.of( nutritionalUnit ).get(), is( 0.0 ) );
      }
   }//End Method
   
   @Test public void shouldRedirect(){
      Food food = new FoodItem( "Anything" );
      assertThat( Nutrition.of( food ).get(), is( food.properties().nutrition() ) );
      assertThat( Nutrition.of( food.properties() ).get(), is( food.properties().nutrition() ) );
   }//End Method
   
   @Test public void shouldListenToAllChanges(){
      ChangeListener< ? super Double > listener = mock( ChangeListener.class );
      systemUnderTest.listen( listener );
      
      Random random = new Random();
      for ( NutritionalUnit nutritionalUnit : NutritionalUnit.values() ) {
         double test = random.nextDouble() * 100;
         systemUnderTest.of( nutritionalUnit ).set( test );
         verify( listener ).changed( systemUnderTest.of( nutritionalUnit ), 0.0, test );
      }
      
      systemUnderTest.stopListening( listener );
      for ( NutritionalUnit nutritionalUnit : NutritionalUnit.values() ) {
         double test = random.nextDouble() * 100;
         systemUnderTest.of( nutritionalUnit ).set( test );
         verify( listener, never() ).changed( systemUnderTest.of( nutritionalUnit ), 0.0, test );
      }
   }//End Method
   
   @Test public void shouldSetMacros(){
      assertThat( systemUnderTest.of( NutritionalUnit.Carbohydrate ).get(), is( 0.0 ) );
      assertThat( systemUnderTest.of( NutritionalUnit.Fat ).get(), is( 0.0 ) );
      assertThat( systemUnderTest.of( NutritionalUnit.Protein ).get(), is( 0.0 ) );
      
      systemUnderTest.setMacroNutrients( 34, 4.2, 100.9 );
      
      assertThat( systemUnderTest.of( NutritionalUnit.Carbohydrate ).get(), is( 34.0 ) );
      assertThat( systemUnderTest.of( NutritionalUnit.Fat ).get(), is( 4.2 ) );
      assertThat( systemUnderTest.of( NutritionalUnit.Protein ).get(), is( 100.9 ) );
   }//End Method

}//End Class
