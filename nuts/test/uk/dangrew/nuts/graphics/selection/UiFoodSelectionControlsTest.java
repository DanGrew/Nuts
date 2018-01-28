package uk.dangrew.nuts.graphics.selection;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;

public class UiFoodSelectionControlsTest {

   private ObservableList< Food > foods;
   @Mock private UiFoodSelectionController controller;
   private UiFoodSelectionControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      foods = FXCollections.observableArrayList( 
               new FoodItem( "Beans" ),
               new FoodItem( "Sausages" ),
               new FoodItem( "Chicken" )
      );
      systemUnderTest = new UiFoodSelectionControls( foods, controller );
   }//End Method

   @Test public void shouldInvertSorting() {
      systemUnderTest.sortingBox().setSelected( true );
      verify( controller ).invertSort( true );
   }//End Method
   
   @Test public void shouldFilterOptionsWhenTextEntered() {
      systemUnderTest.filterTextField().getEditor().setText( "this" );
      verify( controller ).filterOptions( "this" );
   }//End Method
   
   @Test public void shouldChangeSortType() {
      systemUnderTest.sortBox().getSelectionModel().select( FoodSelectionTypes.Fats );
      verify( controller ).useSelectionType( FoodSelectionTypes.Fats );
   }//End Method

}//End Class
