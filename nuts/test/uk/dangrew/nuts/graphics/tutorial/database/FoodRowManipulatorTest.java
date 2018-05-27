package uk.dangrew.nuts.graphics.tutorial.database;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.TableRow;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class FoodRowManipulatorTest {

   private TableRow< ConceptTableRow< Food > > row;
   private Food food;
   
   private FoodRowManipulator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      food = new FoodItem( "Name" );
      row = new TableRow<>();
      row.setItem( new ConceptTableRow< Food >( food ) );
      
      systemUnderTest = new FoodRowManipulator( row );
   }//End Method

   @Test public void shouldChangeName() {
      assertThat( food.properties().nameProperty().get(), is( "Name" ) );
      systemUnderTest.changeName( "New Name" );
      assertThat( food.properties().nameProperty().get(), is( "New Name" ) );
   }//End Method
   
   @Test public void shouldChangeCalories() {
      assertThat( food.properties().calories().get(), is( 0.0 ) );
      systemUnderTest.changeCalories( 45.3 );
      assertThat( food.properties().calories().get(), is( 45.3 ) );
   }//End Method
   
   @Test public void shouldChangeMacros() {
      assertThat( food.properties().carbohydrates().get(), is( 0.0 ) );
      systemUnderTest.changeMacro( MacroNutrient.Carbohydrates, 45.3 );
      assertThat( food.properties().carbohydrates().get(), is( 45.3 ) );
      
      assertThat( food.properties().fats().get(), is( 0.0 ) );
      systemUnderTest.changeMacro( MacroNutrient.Fats, 25.3 );
      assertThat( food.properties().fats().get(), is( 25.3 ) );
      
      assertThat( food.properties().protein().get(), is( 0.0 ) );
      systemUnderTest.changeMacro( MacroNutrient.Protein, 5.3 );
      assertThat( food.properties().protein().get(), is( 5.3 ) );
   }//End Method
   
   @Test public void shouldChangeFibre() {
      assertThat( food.properties().fiber().get(), is( 0.0 ) );
      systemUnderTest.changeFibre( 45.3 );
      assertThat( food.properties().fiber().get(), is( 45.3 ) );
   }//End Method

}//End Class
