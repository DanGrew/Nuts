package uk.dangrew.nuts.graphics.progress.custom;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.javafx.table.base.ConceptTable;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.kode.javafx.table.controller.GeneralConceptTableController;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.store.Database;

public class SelectionSynchronizerTest {

   private Database database;
   private ConceptTable< FoodItem > table1;
   private ConceptTable< FoodItem > table2;
   private Object systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      table1 = new TableComponents< FoodItem >()
               .withDatabase( database )
               .applyColumns( FoodTableColumns::new )
               .withController( new GeneralConceptTableController<>( database.foodItems() ) )
               .buildTable();
      table2 = new TableComponents< FoodItem >()
               .withDatabase( database )
               .applyColumns( FoodTableColumns::new )
               .withController( new GeneralConceptTableController<>( database.foodItems() ) )
               .buildTable();
      systemUnderTest = new SelectionSynchronizer<>( table1, table2 );
   }//End Method

   @Test public void shouldSynchronizeSelection() {
      database.foodItems().createConcept( "Food1" );
      database.foodItems().createConcept( "Food2" );
      database.foodItems().createConcept( "Food3" );
      
      assertThat( table1.getRows(), is( not( empty() ) ) );
      assertThat( table2.getRows(), is( not( empty() ) ) );
      
      assertThat( table1.getSelectionModel().getSelectedItem(), is( nullValue() ) );
      assertThat( table2.getSelectionModel().getSelectedItem(), is( nullValue() ) );
      
      table1.getSelectionModel().select( 1 );
      assertThat( table1.getSelectionModel().getSelectedItem().concept(), is( database.foodItems().objectList().get( 1 ) ) );
      assertThat( table2.getSelectionModel().getSelectedItem().concept(), is( database.foodItems().objectList().get( 1 ) ) );
      
      table1.getSelectionModel().clearSelection();
      assertThat( table1.getSelectionModel().getSelectedItem(), is( nullValue() ) );
      assertThat( table2.getSelectionModel().getSelectedItem(), is( nullValue() ) );
      
      table2.getSelectionModel().select( 2 );
      assertThat( table2.getSelectionModel().getSelectedItem().concept(), is( database.foodItems().objectList().get( 2 ) ) );
      assertThat( table1.getSelectionModel().getSelectedItem().concept(), is( database.foodItems().objectList().get( 2 ) ) );
      
      table2.getSelectionModel().clearSelection();
      assertThat( table1.getSelectionModel().getSelectedItem(), is( nullValue() ) );
      assertThat( table2.getSelectionModel().getSelectedItem(), is( nullValue() ) );
   }//End Method

}//End Class
