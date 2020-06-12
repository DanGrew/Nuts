package uk.dangrew.nuts.graphics.food;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.javafx.table.base.ConceptTable;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.graphics.deletion.FoodDeletionMechanism;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.store.Database;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class GeneralFoodTableControllerTest {

   private FoodItemStore foodItems;
   private FoodItem onlyFoodItem;
   
   @Mock private FoodDeletionMechanism deletionMechanism;
   private ConceptTable< FoodItem > table;
   private GeneralFoodTableController< FoodItem > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      Database database  = new Database();
      foodItems = database.foodItems();
      
      onlyFoodItem = foodItems.createConcept( "Anything" );
      systemUnderTest = new GeneralFoodTableController<>( deletionMechanism, foodItems );
      JavaFxThreading.runAndWait( () -> table = new TableComponents< FoodItem >()
               .withDatabase( database )
               .applyColumns( FoodTableColumns< FoodItem >::new )
               .withController( systemUnderTest )
               .buildTable()
      );
      systemUnderTest.associate( table );
   }//End Method

   @Test public void shouldUseDeletionMechanismWhenSelected() {
      when( deletionMechanism.delete( onlyFoodItem ) ).thenReturn( true );
      
      table.getSelectionModel().select( 0 );
      systemUnderTest.removeSelectedConcept();
      verify( deletionMechanism ).delete( onlyFoodItem );
      assertThat( foodItems.objectList(), hasSize( 0 ) );
   }//End Method
   
   @Test public void shouldNotUseDeletionMechanismWhenNotSelected() {
      systemUnderTest.removeSelectedConcept();
      verify( deletionMechanism, never() ).delete( any() );
      assertThat( foodItems.objectList(), is( not( empty() ) ) );
   }//End Method

}//End Class
