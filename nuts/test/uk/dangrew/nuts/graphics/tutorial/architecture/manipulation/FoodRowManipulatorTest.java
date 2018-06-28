package uk.dangrew.nuts.graphics.tutorial.architecture.manipulation;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.control.TableRow;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.food.GeneralConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.store.Database;

public class FoodRowManipulatorTest {

   private ConceptTable< FoodItem > table;
   private TableRow< ConceptTableRow< FoodItem > > row;
   private FoodItem food;
   
   private FoodRowManipulator< FoodItem > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      food = new FoodItem( "Name" );
      row = new TableRow<>();
      row.setItem( new ConceptTableRow<>( food ) );
      
      Database database = new Database();
      PlatformImpl.runAndWait( () -> table = new TableComponents< FoodItem >()
               .withDatabase( database )
               .withColumns( FoodTableColumns< FoodItem >::new )
               .withController( new GeneralConceptTableController<>( database.foodItems() ) )
               .buildTable()
       );
      row.updateTableView( table );
      systemUnderTest = new FoodRowManipulator<>( row, 3 );
   }//End Method
   
   @Test public void shouldProvideIndex(){
      assertThat( systemUnderTest.index(), is( 3 ) );
   }//End Method
   
   @Test public void shouldProvideConcept(){
      assertThat( systemUnderTest.concept(), is( food ) );
   }//End Method

   @Test public void shouldChangeName() {
      assertThat( food.properties().nameProperty().get(), is( "Name" ) );
      systemUnderTest.changeName( "New Name" );
      assertThat( food.properties().nameProperty().get(), is( "New Name" ) );
   }//End Method
   
   @Test public void shouldChangeCalories() {
      assertThat( food.nutrition().of( NutritionalUnit.Calories ).get(), is( 0.0 ) );
      systemUnderTest.change( NutritionalUnit.Calories, 45.3 );
      assertThat( food.nutrition().of( NutritionalUnit.Calories ).get(), is( 45.3 ) );
   }//End Method
   
   @Test public void shouldChangeMacros() {
      assertThat( food.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 0.0 ) );
      systemUnderTest.change( NutritionalUnit.Carbohydrate, 45.3 );
      assertThat( food.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 45.3 ) );
      
      assertThat( food.nutrition().of( NutritionalUnit.Fat ).get(), is( 0.0 ) );
      systemUnderTest.change( NutritionalUnit.Fat, 25.3 );
      assertThat( food.nutrition().of( NutritionalUnit.Fat ).get(), is( 25.3 ) );
      
      assertThat( food.nutrition().of( NutritionalUnit.Protein ).get(), is( 0.0 ) );
      systemUnderTest.change( NutritionalUnit.Protein, 5.3 );
      assertThat( food.nutrition().of( NutritionalUnit.Protein ).get(), is( 5.3 ) );
   }//End Method
   
   @Test public void shouldChangeFibre() {
      assertThat( food.nutrition().of( NutritionalUnit.Fibre ).get(), is( 0.0 ) );
      systemUnderTest.change( NutritionalUnit.Fibre, 45.3 );
      assertThat( food.nutrition().of( NutritionalUnit.Fibre ).get(), is( 45.3 ) );
   }//End Method
   
   @Test public void shouldTriggerCellEdit(){
      systemUnderTest.triggerCellEdit( 4 );
      assertThat( table.editingCellProperty().get().getRow(), is( 3 ) );
      assertThat( table.editingCellProperty().get().getColumn(), is( 4 ) );
   }//End Method
   
   @Test public void shouldSelectInTable(){
      systemUnderTest.selectInTable();
      assertThat( table.getSelectionModel().getSelectedItem(), is( systemUnderTest.node().getItem() ) );
   }//End Method
   
   @Test public void shouldFinishEdit(){
      systemUnderTest.triggerCellEdit( 4 );
      systemUnderTest.finishCellEdit();
      assertThat( table.editingCellProperty().get(), is( nullValue() ) );
   }//End Method

}//End Class
