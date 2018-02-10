package uk.dangrew.nuts.graphics.table;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.sun.javafx.application.PlatformImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.graphics.food.GeneralFoodTable;
import uk.dangrew.nuts.store.Database;

public class RowSynchronizerTest {

   private ConceptTable< FoodItem > table;
   private ObservableList< FoodItem > items;
   private RowSynchronizer< FoodItem > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      PlatformImpl.runAndWait( () -> table = new GeneralFoodTable<>( new Database(), new FoodItemStore() ) );
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
