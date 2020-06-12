package uk.dangrew.nuts.graphics.food;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.TableColumn;
import uk.dangrew.kode.javafx.table.base.ConceptTable;
import uk.dangrew.kode.javafx.table.base.ConceptTableRow;
import uk.dangrew.kode.javafx.table.controller.GeneralConceptTableController;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.store.Database;

public class FoodTableColumnsTest {

   private NutsSettings settings;
   private ConceptTable< FoodItem > table;
   private TableComponents< FoodItem > components;
   private FoodTableColumns< FoodItem > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      Database database = new Database();
      settings = database.settings();
      components = new TableComponents< FoodItem >()
               .withDatabase( database )
               .withController( new GeneralConceptTableController<>( database.foodItems() ) );
      systemUnderTest = new FoodTableColumns<>( components );
      table = components
               .applyColumns( c -> systemUnderTest )
               .buildTable();
   }//End Method

   @Test public void shouldProvideNameColumn() {
      TableColumn< ConceptTableRow< FoodItem >, ? > column = table.getColumns().get( 0 );
      assertThat( column.getText(), is( FoodTableColumns.COLUMN_TITLE_FOOD ) );
   }//End Method
   
   @Test public void shouldProvideDefaultNutritionalUnitColumns() {
      TableColumn< ConceptTableRow< FoodItem >, ? > column = table.getColumns().get( 1 );
      assertThat( column.getText(), is( NutritionalUnit.Calories.name() ) );
      column = table.getColumns().get( 2 );
      assertThat( column.getText(), is( NutritionalUnit.Carbohydrate.name() ) );
      column = table.getColumns().get( 3 );
      assertThat( column.getText(), is( NutritionalUnit.Fat.name() ) );
      column = table.getColumns().get( 4 );
      assertThat( column.getText(), is( NutritionalUnit.Protein.name() ) );
      column = table.getColumns().get( 5 );
      assertThat( column.getText(), is( NutritionalUnit.Fibre.name() ) );
   }//End Method
   
   @Test public void shouldProvideConfiguredNutritionalUnitColumns() {
      settings.showingPropertyFor( NutritionalUnit.Fat ).set( false );
      table = components.buildTable();
      
      TableColumn< ConceptTableRow< FoodItem >, ? > column = table.getColumns().get( 1 );
      assertThat( column.getText(), is( NutritionalUnit.Calories.name() ) );
      column = table.getColumns().get( 2 );
      assertThat( column.getText(), is( NutritionalUnit.Carbohydrate.name() ) );
      column = table.getColumns().get( 3 );
      assertThat( column.getText(), is( NutritionalUnit.Protein.name() ) );
      column = table.getColumns().get( 4 );
      assertThat( column.getText(), is( NutritionalUnit.Fibre.name() ) );
   }//End Method
   
   @Test public void shouldRespondToNutritionalUnitSettingChanges() {
      table = components.buildTable();
      
      TableColumn< ConceptTableRow< FoodItem >, ? > column = table.getColumns().get( 1 );
      assertThat( column.getText(), is( NutritionalUnit.Calories.name() ) );
      column = table.getColumns().get( 2 );
      assertThat( column.getText(), is( NutritionalUnit.Carbohydrate.name() ) );
      column = table.getColumns().get( 3 );
      assertThat( column.getText(), is( NutritionalUnit.Fat.name() ) );
      column = table.getColumns().get( 4 );
      assertThat( column.getText(), is( NutritionalUnit.Protein.name() ) );
      column = table.getColumns().get( 5 );
      assertThat( column.getText(), is( NutritionalUnit.Fibre.name() ) );
      
      settings.showingPropertyFor( NutritionalUnit.Fat ).set( false );
      
      column = table.getColumns().get( 1 );
      assertThat( column.getText(), is( NutritionalUnit.Calories.name() ) );
      column = table.getColumns().get( 2 );
      assertThat( column.getText(), is( NutritionalUnit.Carbohydrate.name() ) );
      column = table.getColumns().get( 3 );
      assertThat( column.getText(), is( NutritionalUnit.Protein.name() ) );
      column = table.getColumns().get( 4 );
      assertThat( column.getText(), is( NutritionalUnit.Fibre.name() ) );
   }//End Method

}//End Class
