package uk.dangrew.nuts.graphics.table;

import org.junit.Before;
import org.junit.Test;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.food.GeneralConceptTableController;
import uk.dangrew.nuts.store.Database;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class GeneralConceptTableControllerTest {

   private FoodItem eggs;
   private Database database;
   
   private ConceptTable< FoodItem > table;
   private GeneralConceptTableController< FoodItem > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      
      database = new Database();
      eggs = new FoodItem( "Eggs" );
      database.foodItems().store( eggs );
      
      systemUnderTest = new GeneralConceptTableController<>( database.foodItems() );
      table = new TableComponents< FoodItem >()
               .withDatabase( database )
               .applyColumns( FoodTableColumns< FoodItem >::new )
               .withController( systemUnderTest )
               .buildTable();
   }//End Method

   @Test public void shouldCreateFoodInDatabase() {
      FoodItem foodItem = systemUnderTest.createConcept();
      assertThat( foodItem, is( notNullValue() ) );
      assertThat( database.foodItems().get( foodItem.properties().id() ), is( foodItem ) );
      
      FoodItem food2 = systemUnderTest.createConcept();
      assertThat( foodItem, is( not( food2 ) ) );
      assertThat( foodItem.properties().id(), is( not( food2.properties().id() ) ) );
      assertThat( food2, is( notNullValue() ) );
      assertThat( database.foodItems().get( food2.properties().id() ), is( food2 ) );
   }//End Method
   
   @Test public void shouldRemoveSelectedFoodFromDatabaseAndTable() {
      assertThat( database.foodItems().get( eggs.properties().id() ), is( eggs ) );
      table.getSelectionModel().select( 0 );
      systemUnderTest.removeSelectedConcept();
      assertThat( database.foodItems().get( eggs.properties().id() ), is( nullValue() ) );
      assertThat( table.getItems(), is( empty() ) );
   }//End Method
   
   @Test public void shouldDuplicateSelection() {
      assertThat( database.foodItems().get( eggs.properties().id() ), is( eggs ) );
      table.getSelectionModel().select( 0 );
      systemUnderTest.copySelectedConcept();
      assertThat( database.foodItems().objectList().get( 0 ), is( eggs ) );
      assertThat( database.foodItems().objectList().get( 1 ).properties().nameProperty().get(), is( "Eggs" + Food.COPY_SUFFIX ) );
      assertThat( table.getItems().get( 0 ).concept(), is( eggs ) );
      assertThat( table.getItems().get( 1 ).concept(), is( database.foodItems().objectList().get( 1 ) ) );
   }//End Method
   
   @Test public void shouldIgnoreRemovalWithNoSelection() {
      assertThat( database.foodItems().get( eggs.properties().id() ), is( eggs ) );
      systemUnderTest.removeSelectedConcept();
      ConceptTableRow< FoodItem > row = table.getItems().get( 0 );
      assertThat( row.concept().properties().nameProperty().get(), is( eggs.properties().nameProperty().get() ) );
   }//End Method
   
   @Test public void shouldPopulateTableWithDatabaseFoods(){
      assertThat( table.getItems(), hasSize( 1 ) );
      ConceptTableRow< FoodItem > row = table.getItems().get( 0 );
      assertThat( row.concept().properties().nameProperty().get(), is( eggs.properties().nameProperty().get() ) );
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
      assertThat( table.getItems().get( 1 ).concept().properties().nameProperty().get(), is( bacon.properties().nameProperty().get() ) );
   }//End Method

}//End Class
