package uk.dangrew.nuts.graphics.database;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.common.UiEnumTypeSelectionDialog;
import uk.dangrew.nuts.graphics.deletion.FoodDeletionMechanism;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.selection.model.FoodFilterModel;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class MixedFoodTableControllerTest {

   @Mock private FoodDeletionMechanism deletionMechanism;
   @Mock private UiEnumTypeSelectionDialog< FoodTypes > dialog;
   @Mock private RecipeShareControllerImpl shareController;
   private Database database;
   
   private FoodFilterModel model;
   private ConceptTable< Food > table;
   private MixedFoodTableController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      database = new Database();
      database.foodItems().createConcept( "FoodItem1" );
      database.foodItems().createConcept( "FoodItem2" );
      database.meals().createConcept( "Meal1" );
      database.meals().createConcept( "Meal2" );
      database.templates().createConcept( "Template1" );
      database.templates().createConcept( "Template2" );
      model = new FoodFilterModel( database );
      
      systemUnderTest = new MixedFoodTableController( 
               deletionMechanism, 
               dialog,
               shareController,
               database, 
               model 
      );
      JavaFxThreading.runAndWait( () -> table = new TableComponents< Food >()
               .withDatabase( database )
               .applyColumns( FoodTableColumns< Food >::new )
               .withController( systemUnderTest )
               .buildTable() 
      );
   }//End Method

   @Test public void shouldProvideRows() {
      assertThat( table.getRows().get( 0 ).concept(), is( database.foodItems().objectList().get( 0 ) ) );
      assertThat( table.getRows().get( 1 ).concept(), is( database.foodItems().objectList().get( 1 ) ) );
      assertThat( table.getRows().get( 2 ).concept(), is( database.meals().objectList().get( 0 ) ) );
      assertThat( table.getRows().get( 3 ).concept(), is( database.meals().objectList().get( 1 ) ) );
      assertThat( table.getRows().get( 4 ).concept(), is( database.templates().objectList().get( 0 ) ) );
      assertThat( table.getRows().get( 5 ).concept(), is( database.templates().objectList().get( 1 ) ) );
      
      database.foodItems().createConcept( "FoodItem3" );
      database.meals().createConcept( "Meal3" );
      database.templates().createConcept( "Template3" );
      
      assertThat( table.getRows().get( 2 ).concept(), is( database.foodItems().objectList().get( 2 ) ) );
      assertThat( table.getRows().get( 5 ).concept(), is( database.meals().objectList().get( 2 ) ) );
      assertThat( table.getRows().get( 8 ).concept(), is( database.templates().objectList().get( 2 ) ) );
   }//End Method
   
   @Test public void shouldCreateConcepts(){
      assertThatFoodIsCreated( FoodTypes.FoodItems, FoodItem.class );
      assertThatFoodIsCreated( FoodTypes.Meals, Meal.class );
      assertThatFoodIsCreated( FoodTypes.Templates, Template.class );
   }//End Method
   
   private void assertThatFoodIsCreated( FoodTypes type, Class< ? extends Food > impl ){
      when( dialog.friendly_showAndWait() ).thenReturn( Optional.of( type ) );
      Food food = systemUnderTest.createConcept();
      assertThat( food, is( instanceOf( impl ) ) );
      assertThat( type.redirect( database ).hasKey( food.properties().id() ), is( true ) );
   }//End Method
   
   @Test public void shouldNotCreateConceptIfCancelled(){
      assertThat( systemUnderTest.createConcept(), is( nullValue() ) );
      
      when( dialog.friendly_showAndWait() ).thenReturn( Optional.empty() );
      assertThat( systemUnderTest.createConcept(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldUseDeletionMechanismWhenSelected() {
      FoodItem foodItem = database.foodItems().objectList().get( 0 );
      when( deletionMechanism.delete( foodItem ) ).thenReturn( true );
      
      table.getSelectionModel().select( 0 );
      systemUnderTest.removeSelectedConcept();
      verify( deletionMechanism ).delete( foodItem );
      assertThat( database.foodItems().objectList(), hasSize( 1 ) );
      
      Meal meal = database.meals().objectList().get( 0 );
      when( deletionMechanism.delete( meal ) ).thenReturn( true );
      
      table.getSelectionModel().select( 1 );
      systemUnderTest.removeSelectedConcept();
      verify( deletionMechanism ).delete( meal );
      assertThat( database.meals().objectList(), hasSize( 1 ) );
      
      Template template = database.templates().objectList().get( 0 );
      when( deletionMechanism.delete( template ) ).thenReturn( true );
      
      table.getSelectionModel().select( 2 );
      systemUnderTest.removeSelectedConcept();
      verify( deletionMechanism ).delete( template );
      assertThat( database.meals().objectList(), hasSize( 1 ) );
   }//End Method
   
   @Test public void shouldNotRemoveConceptIfNotSelected(){
      assertThat( database.foodItems().objectList(), is( not( empty() ) ) );
      assertThat( database.meals().objectList(), is( not( empty() ) ) );
      assertThat( database.templates().objectList(), is( not( empty() ) ) );
      systemUnderTest.removeSelectedConcept();
      assertThat( database.foodItems().objectList(), is( not( empty() ) ) );
      assertThat( database.meals().objectList(), is( not( empty() ) ) );
      assertThat( database.templates().objectList(), is( not( empty() ) ) );
   }//End Method
   
   @Test public void shouldNotCopyConcepts(){
      assertThat( database.foodItems().objectList(), hasSize( 2 ) );
      table.getSelectionModel().select( 0 );
      systemUnderTest.copySelectedConcept();
      assertThat( database.foodItems().objectList(), hasSize( 3 ) );
      
      assertThat( database.meals().objectList(), hasSize( 2 ) );
      table.getSelectionModel().select( 4 );
      systemUnderTest.copySelectedConcept();
      assertThat( database.meals().objectList(), hasSize( 3 ) );
      
      assertThat( database.templates().objectList(), hasSize( 2 ) );
      table.getSelectionModel().select( 6 );
      systemUnderTest.copySelectedConcept();
      assertThat( database.templates().objectList(), hasSize( 3 ) );
   }//End Method

   @Test public void shouldShareWhenPressed(){
      table.getSelectionModel().select( 0 );
      
      systemUnderTest.share();
      verify( shareController ).share( database.foodItems().objectList().get( 0 ) );
   }//End Method
   
}//End Class
