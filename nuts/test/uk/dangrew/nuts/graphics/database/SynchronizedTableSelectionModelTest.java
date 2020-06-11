package uk.dangrew.nuts.graphics.database;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.food.GeneralFoodTableController;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.store.Database;

public class SynchronizedTableSelectionModelTest {

   private ConceptTable< FoodItem > table1;
   private ConceptTable< FoodItem > table2;
   private SynchronizedTableSelectionModel< FoodItem > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      Database database = new Database();
      database.foodItems().createConcept( "Food1" );
      database.foodItems().createConcept( "Food2" );
      database.foodItems().createConcept( "Food3" );

      JavaFxThreading.runAndWait( () -> {
         table1 = new TableComponents< FoodItem >()
                     .withDatabase( database )
                     .applyColumns( FoodTableColumns< FoodItem >::new )
                     .withController( new GeneralFoodTableController<>( database, database.foodItems() ) )
                     .buildTable();
         table2 = new TableComponents< FoodItem >()
                     .withDatabase( database )
                     .applyColumns( FoodTableColumns< FoodItem >::new )
                     .withController( new GeneralFoodTableController<>( database, database.foodItems() ) )
                     .buildTable();
      } );
      systemUnderTest = new SynchronizedTableSelectionModel<>( table1, table2 );
   }//End Method

   @Test public void shouldUpdateSelectionWhenTableSelectionsChange() {
      assertThat( systemUnderTest.selected().get(), is( nullValue() ) );
      table1.getSelectionModel().select( 1 );
      assertThat( systemUnderTest.selected().get(), is( table1.getItems().get( 1 ).concept() ) );
      table2.getSelectionModel().select( 1 );
      assertThat( systemUnderTest.selected().get(), is( table2.getItems().get( 1 ).concept() ) );
      assertThat( table1.getSelectionModel().getSelectedItem(), is( nullValue() ) );
      table1.getSelectionModel().select( 0 );
      assertThat( systemUnderTest.selected().get(), is( table1.getItems().get( 0 ).concept() ) );
      assertThat( table2.getSelectionModel().getSelectedItem(), is( nullValue() ) );
   }//End Method

}//End Class
