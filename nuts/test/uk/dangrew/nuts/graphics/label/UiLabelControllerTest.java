package uk.dangrew.nuts.graphics.label;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableListImpl;
import uk.dangrew.nuts.concept.ConceptTestCommon;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.label.Label;
import uk.dangrew.nuts.label.Labelables;
import uk.dangrew.nuts.store.Database;

public class UiLabelControllerTest {

   private ObservableList< Concept > labelSelection;
   private ObservableList< Concept > databaseSelection;
   
   private Database database;
   private Label label;
   
   private UiLabelController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      label = database.labels().createConcept( "Anything" );
      labelSelection = FXCollections.observableArrayList();
      databaseSelection = FXCollections.observableArrayList();
      
      systemUnderTest = new UiLabelController( database );
      
      for ( int i = 0; i < 10; i++ ) {
         label.concepts().add( new FoodItem( UUID.randomUUID().toString() ) );
         database.foodItems().createConcept( UUID.randomUUID().toString() );
      }
   }//End Method

   @Test public void shouldProvideSelectedLabelConceptsUnmodifiable(){
      assertThat( systemUnderTest.selectedLabelConcepts(), is( instanceOf( PrivatelyModifiableObservableListImpl.class ) ) );
      
      systemUnderTest.selectLabel( label );
      assertThat( systemUnderTest.selectedLabelConcepts(), containsInAnyOrder( label.concepts().stream().toArray() ) );
      
      ConceptTestCommon.assertInAlphabeticalOrder( systemUnderTest.selectedLabelConcepts() );
   }//End Method
   
   @Test public void shouldProvideSelectedDatabaseConceptsUnmodifiable(){
      assertThat( systemUnderTest.selectedLabelConcepts(), is( instanceOf( PrivatelyModifiableObservableListImpl.class ) ) );
      
      systemUnderTest.selectLabelable( Labelables.FoodItems );
      assertThat( systemUnderTest.selectedDatabaseConcepts(), containsInAnyOrder( database.foodItems().objectList().stream().toArray() ) );
      
      ConceptTestCommon.assertInAlphabeticalOrder( systemUnderTest.selectedDatabaseConcepts() );
   }//End Method
   
   @Test public void shouldRemoveSelectionFromLabel() {
      systemUnderTest.monitorLabelSelection( labelSelection );
      systemUnderTest.selectLabel( label );
      
      Concept first = label.concepts().get( 3 );
      Concept second = label.concepts().get( 7 );
      labelSelection.add( first );
      labelSelection.add( second );
      systemUnderTest.removeFromLabel();
      
      assertThat( label.concepts(), hasSize( 8 ) );
      assertThat( label.concepts().contains( first ), is( false ) );
      assertThat( label.concepts().contains( second ), is( false ) );
   }//End Method
   
   @Test public void shouldIgnoreRemoveSelectionWhenNotSelected() {
      systemUnderTest.monitorLabelSelection( labelSelection );
      
      Concept first = label.concepts().get( 3 );
      Concept second = label.concepts().get( 7 );
      labelSelection.add( first );
      labelSelection.add( second );
      systemUnderTest.removeFromLabel();
   }//End Method
   
   @Test public void shouldAddSelectionToLabel(){
      systemUnderTest.monitorDatabaseSelection( databaseSelection );
      systemUnderTest.selectLabel( label );
      
      FoodItem first = database.foodItems().objectList().get( 2 );
      FoodItem second = database.foodItems().objectList().get( 9 );
      databaseSelection.add( first );
      databaseSelection.add( second );
      systemUnderTest.addToLabel();
      
      assertThat( label.concepts(), hasSize( 12 ) );
      assertThat( label.concepts().contains( first ), is( true ) );
      assertThat( label.concepts().contains( second ), is( true ) );
   }//End Method
   
   @Test public void shouldIgnoreAddSelectionWhenNotSelected(){
      systemUnderTest.monitorDatabaseSelection( databaseSelection );
      FoodItem first = database.foodItems().objectList().get( 2 );
      FoodItem second = database.foodItems().objectList().get( 9 );
      databaseSelection.add( first );
      databaseSelection.add( second );
      systemUnderTest.addToLabel();
   }//End Method
   
   @Test public void shouldShouldClearSelectionWhenNullProvided(){
      systemUnderTest.selectLabel( label );
      systemUnderTest.selectLabelable( Labelables.FoodItems );
      assertThat( systemUnderTest.selectedDatabaseConcepts(), is( not( empty() ) ) );
      assertThat( systemUnderTest.selectedLabelConcepts(), is( not( empty() ) ) );
      
      systemUnderTest.selectLabel( null );
      systemUnderTest.selectLabelable( null );
      assertThat( systemUnderTest.selectedDatabaseConcepts(), is( empty() ) );
      assertThat( systemUnderTest.selectedLabelConcepts(), is( empty() ) );
   }//End Method
   
   @Test public void shouldExcludeConceptsFromDatabaseIfInLabel(){
      label.concepts().clear();
      label.concepts().add( database.foodItems().objectList().get( 0 ) );
      label.concepts().add( database.foodItems().objectList().get( 2 ) );
      label.concepts().add( database.foodItems().objectList().get( 3 ) );
      label.concepts().add( database.foodItems().objectList().get( 6 ) );
      
      systemUnderTest.selectLabel( label );
      assertThat( systemUnderTest.selectedLabelConcepts(), containsInAnyOrder( label.concepts().toArray() ) );
      assertThat( systemUnderTest.selectedDatabaseConcepts(), is( empty() ) );
      
      systemUnderTest.selectLabelable( Labelables.FoodItems );
      assertThat( systemUnderTest.selectedLabelConcepts(), containsInAnyOrder( label.concepts().toArray() ) );
      List< Concept > expected = new ArrayList<>( database.foodItems().objectList() );
      expected.removeIf( c -> label.concepts().contains( c ) );
      assertThat( systemUnderTest.selectedDatabaseConcepts(), containsInAnyOrder( expected.toArray() ) );
      
      systemUnderTest.selectLabel( null );
      assertThat( systemUnderTest.selectedLabelConcepts(), is( empty() ) );
      assertThat( systemUnderTest.selectedDatabaseConcepts(), containsInAnyOrder( database.foodItems().objectList().toArray() ) );
   }//End Method

   @Test public void shouldUpdateConceptsWhenLabelChanges(){
      label.concepts().clear();
      systemUnderTest.selectLabel( label );
      systemUnderTest.selectLabelable( Labelables.FoodItems );
      assertThat( systemUnderTest.selectedLabelConcepts(), is( empty() ) );
      assertThat( systemUnderTest.selectedDatabaseConcepts(), containsInAnyOrder( database.foodItems().objectList().toArray() ) );
      
      Concept concept = database.foodItems().objectList().get( 3 );
      label.concepts().add( concept );
      assertThat( systemUnderTest.selectedLabelConcepts(), contains( concept ) );
      assertThat( systemUnderTest.selectedDatabaseConcepts(), not( contains( concept ) ) );
   }//End Method
   
   @Test public void shouldChangeName(){
      systemUnderTest.selectLabel( label );
      systemUnderTest.changeName( "something specific" );
      assertThat( label.properties().nameProperty().get(), is( "something specific" ) );
   }//End Method
   
   @Test public void shouldNotChangeNameIfEmpty(){
      systemUnderTest.selectLabel( label );
      systemUnderTest.changeName( "    " );
      assertThat( label.properties().nameProperty().get(), is( "Anything" ) );
   }//End Method
   
   @Test public void shouldCreateLabel(){
      systemUnderTest.createLabel( "something specific" );
      assertThat( label.properties().nameProperty().get(), is( "Anything" ) );
      
      Label newLabel = database.labels().objectList().get( 1 );
      assertThat( newLabel.properties().nameProperty().get(), is( "something specific" ) );
      assertThat( systemUnderTest.selectedLabel().get(), is( newLabel ) );
   }//End Method
   
   @Test public void shouldNotCreateLabelIfEmpty(){
      systemUnderTest.createLabel( "    " );
      assertThat( database.labels().objectList(), contains( label ) );
      assertThat( systemUnderTest.selectedLabel().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldDeleteLabel(){
      systemUnderTest.selectLabel( label );
      systemUnderTest.deleteLabel();
      assertThat( systemUnderTest.selectedLabel().get(), is( nullValue() ) );
      assertThat( database.labels().objectList(), is( empty() ) );
   }//End Method
   
   @Test public void shouldNotChangeNameWhenNotSelected(){
      systemUnderTest.changeName( "something" );
      assertThat( label.properties().nameProperty().get(), is( "Anything" ) );
   }//End Method
   
   @Test public void shouldNotDeleteLabelWhenNotSelected(){
      systemUnderTest.deleteLabel();
      assertThat( database.labels().objectList(), contains( label ) );
   }//End Method
}//End Class
