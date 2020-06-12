package uk.dangrew.nuts.graphics.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.javafx.table.base.ConceptTable;
import uk.dangrew.kode.javafx.table.controller.GeneralConceptTableController;
import uk.dangrew.kode.javafx.table.tools.RowSynchronizer;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.store.Database;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class RowSynchronizerTest {

   private ConceptTable< FoodItem > table;
   private ObservableList< FoodItem > items;
   private RowSynchronizer< FoodItem > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      
      Database database = new Database();
      JavaFxThreading.runAndWait( () -> table = new TableComponents< FoodItem >()
               .withDatabase( database )
               .applyColumns( FoodTableColumns< FoodItem >::new )
               .withController( new GeneralConceptTableController<>( database.foodItems() ) )
               .buildTable()
      );
      items = FXCollections.observableArrayList();
      systemUnderTest = new RowSynchronizer<>( table, items );
   }//End Method
   
   @Test public void shouldInitiallyPopulate(){
      items.add( new FoodItem( "Food1" ) );
      items.add( new FoodItem( "Food2" ) );
      
      systemUnderTest = new RowSynchronizer<>( table, items );
      assertThat( table.getRows(), hasSize( 2 ) );
      assertThat( table.getRows().get( 0 ).concept(), is( items.get( 0 ) ) );
      assertThat( table.getRows().get( 1 ).concept(), is( items.get( 1 ) ) );
   }//End Method

   @Test public void shouldUpdateRowWhenAddedToList() {
      assertThat( table.getRows(), is( empty() ) );
      
      items.add( new FoodItem( "Food1" ) );
      assertThat( table.getRows(), hasSize( 1 ) );
      assertThat( table.getRows().get( 0 ).concept(), is( items.get( 0 ) ) );
      
      items.add( new FoodItem( "Food2" ) );
      assertThat( table.getRows(), hasSize( 2 ) );
      assertThat( table.getRows().get( 0 ).concept(), is( items.get( 0 ) ) );
      assertThat( table.getRows().get( 1 ).concept(), is( items.get( 1 ) ) );
   }//End Method
   
   @Test public void shouldUpdateRowWhenRemovedFromList() {
      items.add( new FoodItem( "Food1" ) );
      items.add( new FoodItem( "Food2" ) );
      
      assertThat( table.getRows(), hasSize( 2 ) );
      items.remove( 0 );
      assertThat( table.getRows(), hasSize( 1 ) );
      assertThat( table.getRows().get( 0 ).concept(), is( items.get( 0 ) ) );

      items.remove( 0 );
      assertThat( table.getRows(), is( empty() ) );
   }//End Method
   
   @Test public void shouldDetach(){
      assertThat( table.getRows(), is( empty() ) );
      
      items.add( new FoodItem( "Food1" ) );
      assertThat( table.getRows(), hasSize( 1 ) );
      assertThat( table.getRows().get( 0 ).concept(), is( items.get( 0 ) ) );
      
      systemUnderTest.detach();
      items.add( new FoodItem( "Food2" ) );
      assertThat( table.getRows(), hasSize( 1 ) );
      assertThat( table.getRows().get( 0 ).concept(), is( items.get( 0 ) ) );
   }//End Method

}//End Class
