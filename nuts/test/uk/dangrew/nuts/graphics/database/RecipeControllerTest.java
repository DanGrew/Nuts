package uk.dangrew.nuts.graphics.database;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.selection.model.SimpleFoodModel;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class RecipeControllerTest {

   private ConceptTable< Food > table;
   
   @Mock private RecipeSummaryWindow window;
   private RecipeController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      Database database = new Database();
      DataLocation.loadSampleFoodData( database );
      SimpleFoodModel model = new SimpleFoodModel();
      database.foodItems().objectList().forEach( model::add );
      database.meals().objectList().forEach( model::add );
      
      PlatformImpl.runAndWait( () -> {
         systemUnderTest = new RecipeController( window, database, model );
         table = new TableComponents< Food >()
                  .withSettings( new NutsSettings() )
                  .withColumns( FoodTableColumns< Food >::new )
                  .withController( systemUnderTest )
                  .buildTable();
      } );
   }//End Method

   @Test public void shouldNotShareWhenNoSelection() {
      systemUnderTest.share();
      verifyZeroInteractions( window );
   }//End Method
   
   @Test public void shouldNotShareWhenSelectedNotMeal() {
      ConceptTableRow< Food > itemRow = table.getRows().stream()
         .filter( r -> r.concept() instanceof FoodItem )
         .findAny()
         .get();
      table.getSelectionModel().select( itemRow );
      
      systemUnderTest.share();
      verifyZeroInteractions( window );
   }//End Method
   
   @Test public void shouldShareSelection() {
      ConceptTableRow< Food > itemRow = table.getRows().stream()
         .filter( r -> r.concept() instanceof Meal )
         .findAny()
         .get();
      table.getSelectionModel().select( itemRow );
      
      systemUnderTest.share();
      verify( window ).show( ( Meal )itemRow.concept() );
   }//End Method

}//End Class
