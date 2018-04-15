package uk.dangrew.nuts.graphics.database;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.food.GeneralFoodTable;
import uk.dangrew.nuts.graphics.table.ConceptTable;
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
      
      PlatformImpl.runAndWait( () -> {
         table1 = new GeneralFoodTable<>( database, database.foodItems() );
         table2 = new GeneralFoodTable<>( database, database.foodItems() );
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
