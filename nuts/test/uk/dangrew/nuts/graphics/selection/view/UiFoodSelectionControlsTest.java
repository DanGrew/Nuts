package uk.dangrew.nuts.graphics.selection.view;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.graphics.selection.model.FoodFilters;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionTypes;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelectionController;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelectionControls;
import uk.dangrew.nuts.label.Label;

public class UiFoodSelectionControlsTest {

   private ObservableList< Label > labels;
   @Mock private UiFoodSelectionController controller;
   private UiFoodSelectionControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      labels = FXCollections.observableArrayList( 
               new Label( "Label1" ),
               new Label( "Label2" ),
               new Label( "Label3" )
      );
      systemUnderTest = new UiFoodSelectionControls( labels, controller );
   }//End Method

   @Test public void shouldInvertSorting() {
      systemUnderTest.sortingBox().setSelected( true );
      verify( controller ).invertSort( true );
   }//End Method
   
   @Test public void shouldFilterOptionsWhenTextEntered() {
      systemUnderTest.filterTextField().setText( "this" );
      verify( controller ).filterOptions( "this" );
   }//End Method
   
   @Test public void shouldChangeSortType() {
      systemUnderTest.sortBox().getSelectionModel().select( FoodSelectionTypes.Fats );
      verify( controller ).useSelectionType( FoodSelectionTypes.Fats );
   }//End Method

   @Test public void shouldApplyFiltersWhenSelected(){
      systemUnderTest.filterBox().getCheckModel().checkAll();
      verify( controller, atLeastOnce() ).applyFilters( Arrays.asList( FoodFilters.values() ) );
   }//End Method
   
   @Test public void shouldApplyLabelsWhenSelected(){
      systemUnderTest.labelBox().getCheckModel().checkAll();
      verify( controller, atLeastOnce() ).applyLabels( labels );      
   }//End Method
}//End Class
