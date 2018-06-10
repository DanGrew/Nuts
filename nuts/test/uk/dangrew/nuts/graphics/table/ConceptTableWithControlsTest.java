package uk.dangrew.nuts.graphics.table;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.control.Label;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.food.GeneralConceptTableController;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class ConceptTableWithControlsTest {

   @Spy private JavaFxStyle styling;
   @Mock private ConceptControls controls;
   private ConceptTable< ? > table;
   private ConceptTableWithControls< ? > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      Database database = new Database();
      PlatformImpl.runAndWait( () -> table = new TableComponents< FoodItem >()
               .withSettings( new NutsSettings() )
               .withDatabase( database )
               .withColumns( FoodTableColumns< FoodItem >::new )
               .withController( new GeneralConceptTableController<>( database.foodItems() ) )
               .buildTable()
      );
      systemUnderTest = new ConceptTableWithControls<>( styling, "anything", table, controls );
   }//End Method

   @Test public void shouldProvideContent() {
      assertThat( systemUnderTest.content(), is( systemUnderTest.getContent() ) );
   }//End Method
   
   @Test public void shouldProvideTitle() {
      assertThat( systemUnderTest.getText(), is( "anything" ) );
   }//End Method
   
   @Test public void shouldNotBeCollapsible() {
      assertThat( systemUnderTest.isCollapsible(), is( false ) );
   }//End Method
   
   @Test public void shouldProvideTable() {
      assertThat( systemUnderTest.table(), is( notNullValue() ) );
      assertThat( systemUnderTest.content().getCenter(), is( systemUnderTest.table() ) );
   }//End Method
   
   @Test public void shouldProvideControls() {
      assertThat( systemUnderTest.controls(), is( notNullValue() ) );
      assertThat( systemUnderTest.content().getRight(), is( systemUnderTest.controls() ) );
   }//End Method
   
   @Test public void shouldProvideInstructionsWhenEmpty(){
      verify( styling ).createWrappedTextLabel( ConceptTableWithControls.NO_CONTENT_INFORMATION );
      Label node = ( Label ) systemUnderTest.table().getPlaceholder();
      assertThat( node.getText(), is( ConceptTableWithControls.NO_CONTENT_INFORMATION ) );
   }//End Method
   
   @Test public void shouldMaximizeSize(){
      assertThat( systemUnderTest.getMaxWidth(), is( Double.MAX_VALUE ) );
      assertThat( systemUnderTest.getMaxHeight(), is( Double.MAX_VALUE ) );
   }//End Method

}//End Class
