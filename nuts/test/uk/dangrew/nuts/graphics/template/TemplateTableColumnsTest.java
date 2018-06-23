package uk.dangrew.nuts.graphics.template;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.TableColumn;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.graphics.food.GeneralConceptTableController;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class TemplateTableColumnsTest {

   private NutsSettings settings;
   private ConceptTable< Template > table;
   private TableComponents< Template > components;
   private TemplateTableColumns systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      Database database = new Database();
      settings = database.settings();
      components = new TableComponents< Template >()
               .withDatabase( database )
               .withController( new GeneralConceptTableController<>( database.templates() ) );
      systemUnderTest = new TemplateTableColumns( components );
      table = components
               .withColumns( c -> systemUnderTest )
               .buildTable();
   }//End Method

   @Test public void shouldProvideNameColumn() {
      TableColumn< ConceptTableRow< Template >, ? > column = table.getColumns().get( 0 );
      assertThat( column.getText(), is( TemplateTableColumns.COLUMN_TITLE_TEMPLATE ) );
   }//End Method
   
   @Test public void shouldProvideGoalColumn() {
      TableColumn< ConceptTableRow< Template >, ? > column = table.getColumns().get( 1 );
      assertThat( column.getText(), is( TemplateTableColumns.COLUMN_TITLE_GOAL ) );
   }//End Method
   
   @Test public void shouldProvideDefaultNutritionalUnitColumns() {
      int columnIndex = 2;
      TableColumn< ConceptTableRow< Template >, ? > column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Calories.name() ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Carbohydrate.name() ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Fat.name() ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Protein.name() ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Fibre.name() ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Calories.name() + " %" ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Carbohydrate.name() + " %" ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Fat.name() + " %" ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Protein.name() + " %" ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Fibre.name() + " %" ) );
   }//End Method
   
   @Test public void shouldProvideConfiguredNutritionalUnitColumns() {
      settings.showingPropertyFor( NutritionalUnit.Fat ).set( false );
      table = components.buildTable();
      
      int columnIndex = 2;
      TableColumn< ConceptTableRow< Template >, ? > column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Calories.name() ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Carbohydrate.name() ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Protein.name() ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Fibre.name() ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Calories.name() + " %" ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Carbohydrate.name() + " %" ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Protein.name() + " %" ) );
      column = table.getColumns().get( columnIndex++ );
      assertThat( column.getText(), is( NutritionalUnit.Fibre.name() + " %" ) );   
   }//End Method

}//End Class
