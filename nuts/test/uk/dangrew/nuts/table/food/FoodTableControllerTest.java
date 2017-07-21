package uk.dangrew.nuts.table.food;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class FoodTableControllerTest {

   private FoodItem eggs;
   private Database database;
   
   private FoodTable< FoodItem > table;
   private FoodTableController< FoodItem > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      
      database = new Database();
      eggs = new FoodItem( "Eggs" );
      database.foodItems().store( eggs );
      
      systemUnderTest = new FoodTableController<>( database.foodItems() );
      table = new FoodTable<>( systemUnderTest );
   }//End Method

   @Test public void shouldCreateFoodInDatabase() {
      FoodItem foodItem = systemUnderTest.createFood();
      assertThat( foodItem, is( notNullValue() ) );
      assertThat( database.foodItems().get( foodItem.properties().id() ), is( foodItem ) );
      
      FoodItem food2 = systemUnderTest.createFood();
      assertThat( foodItem, is( not( food2 ) ) );
      assertThat( foodItem.properties().id(), is( not( food2.properties().id() ) ) );
      assertThat( food2, is( notNullValue() ) );
      assertThat( database.foodItems().get( food2.properties().id() ), is( food2 ) );
   }//End Method
   
   @Test public void shouldRemoveSelectedFoodFromDatabaseAndTable() {
      assertThat( database.foodItems().get( eggs.properties().id() ), is( eggs ) );
      table.getSelectionModel().select( 0 );
      systemUnderTest.removeSelectedFood();
      assertThat( database.foodItems().get( eggs.properties().id() ), is( nullValue() ) );
      assertThat( table.getItems(), is( empty() ) );
   }//End Method
   
   @Test public void shouldIgnoreRemovalWithNoSelection() {
      assertThat( database.foodItems().get( eggs.properties().id() ), is( eggs ) );
      systemUnderTest.removeSelectedFood();
      FoodTableRow< FoodItem > row = table.getItems().get( 0 );
      assertThat( row.food().properties().nameProperty().get(), is( eggs.properties().nameProperty().get() ) );
   }//End Method
   
   @Test public void shouldPopulateTableWithDatabaseFoods(){
      assertThat( table.getItems(), hasSize( 1 ) );
      FoodTableRow< FoodItem > row = table.getItems().get( 0 );
      assertThat( row.food().properties().nameProperty().get(), is( eggs.properties().nameProperty().get() ) );
   }//End Method
   
   @Test public void shouldRemoveFromTableWhenRemovedFromDatabase(){
      assertThat( database.foodItems().get( eggs.properties().id() ), is( eggs ) );
      
      database.foodItems().remove( eggs.properties().id() );
      assertThat( database.foodItems().get( eggs.properties().id() ), is( nullValue() ) );
      assertThat( table.getItems(), is( empty() ) );
   }//End Method
   
   @Test public void shouldAddToTableWhenAddedToDatabase(){
      FoodItem bacon = new FoodItem( "Bacon" );
      database.foodItems().store( bacon );
      assertThat( table.getItems(), hasSize( 2 ) );
      assertThat( table.getItems().get( 1 ).food().properties().nameProperty().get(), is( bacon.properties().nameProperty().get() ) );
   }//End Method

}//End Class
