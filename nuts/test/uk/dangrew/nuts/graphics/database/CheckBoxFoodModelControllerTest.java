package uk.dangrew.nuts.graphics.database;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.selection.model.SimpleFoodModel;

public class CheckBoxFoodModelControllerTest {

   private Food food1;
   private Food food2;
   private SimpleFoodModel model;
   private CheckBoxFoodModelController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      food1 = new FoodItem( "Food1" );
      food2 = new FoodItem( "Food2" );
      model = new SimpleFoodModel();
      systemUnderTest = new CheckBoxFoodModelController( model );
   }//End Method

   @Test public void shouldProvidePropertyForFood() {
      assertThat( systemUnderTest.propertyFor( food1 ), is( notNullValue() ) );
      assertThat( systemUnderTest.propertyFor( food1 ), is( systemUnderTest.propertyFor( food1 ) ) );
   }//End Method
   
   @Test public void shouldAddToModelWhenChecked(){
      assertThat( model.concepts().contains( food1 ), is( false ) );
      systemUnderTest.propertyFor( food1 ).set( true );
      assertThat( model.concepts().contains( food1 ), is( true ) );
   }//End Method
   
   @Test public void shouldRemoveFromModelWhenUnchecked(){
      systemUnderTest.propertyFor( food1 ).set( true );
      assertThat( model.concepts().contains( food1 ), is( true ) );
      systemUnderTest.propertyFor( food1 ).set( false );
      assertThat( model.concepts().contains( food1 ), is( false ) );
   }//End Method
   
   @Test public void shouldUncheckWhenRemovedFromModel(){
      model.add( food1 );
      assertThat( systemUnderTest.propertyFor( food1 ).get(), is( true ) );
      model.remove( food1 );
      assertThat( systemUnderTest.propertyFor( food1 ).get(), is( false ) );
   }//End Method
   
   @Test public void shouldCheckWhenAddedToModel(){
      assertThat( systemUnderTest.propertyFor( food1 ).get(), is( false ) );
      model.add( food1 );
      assertThat( systemUnderTest.propertyFor( food1 ).get(), is( true ) );
   }//End Method

}//End Class
