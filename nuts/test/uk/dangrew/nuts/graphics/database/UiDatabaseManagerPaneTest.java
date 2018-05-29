package uk.dangrew.nuts.graphics.database;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.graphics.tutorial.database.DatabaseComponents;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class UiDatabaseManagerPaneTest {

   private UiDatabaseManagerPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      Database database = new Database();
      DataLocation.loadSampleFoodData( database );
      PlatformImpl.runAndWait( () -> systemUnderTest = new UiDatabaseManagerPane( database ) );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      Database database = new Database();
      DataLocation.loadSampleFoodData( database );
      
      TestApplication.launch( () -> new UiDatabaseManagerPane( database ) );
      
      Thread.sleep( 99999999 );
   }//End Method
   
   @Test public void shouldShowSelectedMeal(){
      systemUnderTest.foodTable().getSelectionModel().select( 15 );
      Food selected = systemUnderTest.foodTable().getSelectionModel().getSelectedItem().concept();
      assertThat( systemUnderTest.mealTable().controller().getShowingMeal(), is( selected ) );
      
      systemUnderTest.comparisonTable().getItems().add( new ConceptTableRow< Food >( new Meal( "Anything" ) ) );
      systemUnderTest.comparisonTable().getSelectionModel().select( 0 );
      selected = systemUnderTest.comparisonTable().getSelectionModel().getSelectedItem().concept();
      assertThat( systemUnderTest.foodTable().getSelectionModel().getSelectedItem(), is( nullValue() ) );
      assertThat( systemUnderTest.mealTable().controller().getShowingMeal(), is( selected ) );
   }//End Method
   
   @Test public void shouldProvidePopulatedComponents(){
      DatabaseComponents components = new DatabaseComponents();
      systemUnderTest.populateComponents( components );
      
      assertThat( components.parent(), is( notNullValue() ) );
      
      assertThat( components.database(), is( notNullValue() ) );
      assertThat( components.databaseManipulator(), is( notNullValue() ) );
      
      assertThat( components.mainTable(), is( notNullValue() ) );
      assertThat( components.mainTableComponents(), is( notNullValue() ) );
      assertThat( components.mainTableAddButton(), is( notNullValue() ) );
      assertThat( components.mainTableFoodTypeDialog(), is( notNullValue() ) );
      
      assertThat( components.mealTable(), is( notNullValue() ) );
      assertThat( components.mealTableComponents(), is( notNullValue() ) );
      assertThat( components.mealTableAddButton(), is( notNullValue() ) );
      assertThat( components.mealTableRemoveButton(), is( notNullValue() ) );
      assertThat( components.mealTableCopyButton(), is( notNullValue() ) );
      assertThat( components.mealTableUpButton(), is( notNullValue() ) );
      assertThat( components.mealTableDownButton(), is( notNullValue() ) );
   }//End Method

}//End Class
