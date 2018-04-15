package uk.dangrew.nuts.graphics.selection.model;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;

public class SimpleFoodModelTest {

   private SimpleFoodModel systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new SimpleFoodModel();
   }//End Method

   @Test public void shouldAddFoodAndNotDuplicate() {
      assertThat( systemUnderTest.concepts(), is( empty() ) );
      Food item = new FoodItem( "ANythin" );
      systemUnderTest.add( item );
      assertThat( systemUnderTest.concepts(), contains( item ) );
      systemUnderTest.add( item );
      assertThat( systemUnderTest.concepts(), hasSize( 1 ) );
   }//End Method
   
   @Test public void shouldClearModel(){
      assertThat( systemUnderTest.concepts(), is( empty() ) );
      systemUnderTest.add( new FoodItem( "1" ) );
      systemUnderTest.add( new FoodItem( "2" ) );
      systemUnderTest.add( new FoodItem( "3" ) );
      assertThat( systemUnderTest.concepts(), hasSize( 3 ) );
      systemUnderTest.clear();
      assertThat( systemUnderTest.concepts(), is( empty() ) );
   }//End Method
   
   @Test public void shouldAddMulti(){
      assertThat( systemUnderTest.concepts(), is( empty() ) );
      systemUnderTest.addAll( Arrays.asList( 
               new FoodItem( "1" ),
               new FoodItem( "2" ),
               new FoodItem( "3" ) 
      ) );
      assertThat( systemUnderTest.concepts(), hasSize( 3 ) );
   }//End Method
   
   @Test public void shouldRemove(){
      Food food = new FoodItem( "Food" );
      systemUnderTest.add( food );
      assertThat( systemUnderTest.concepts().contains( food ), is( true ) );
      systemUnderTest.remove( food );
      assertThat( systemUnderTest.concepts().contains( food ), is( false ) );
   }//End Method

}//End Class
