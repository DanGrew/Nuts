package uk.dangrew.nuts.graphics.cycle;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.cycle.CycleType;
import uk.dangrew.nuts.goal.Goal;

public class UiCycleCreationDialogResultConverterTest {

   private ButtonType acceptButtonType;
   private ButtonType cancelButtonType;
   private ComboBox< CycleType > types;
   private ObservableList< Goal > goalOptions;
   private ComboBox< Goal > goals;
   private UiCycleCreationDialogResultConverter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      
      acceptButtonType = ButtonType.OK;
      cancelButtonType = ButtonType.CANCEL;
      
      types = new ComboBox<>( FXCollections.observableArrayList( Arrays.asList( CycleType.values() ) ) );
      
      goalOptions = FXCollections.observableArrayList();
      goalOptions.add( new Goal( "GoalA" ) );
      goalOptions.add( new Goal( "GoalB" ) );
      goalOptions.add( new Goal( "GoalC" ) );
      
      goals = new ComboBox<>( goalOptions );
      systemUnderTest = new UiCycleCreationDialogResultConverter( acceptButtonType, types, goals );
   }//End Method

   @Test public void shouldConstructResultFromChosenOptions() {
      types.getSelectionModel().select( 0 );
      goals.getSelectionModel().select( 1 );
      
      CycleCreationResult result = systemUnderTest.call( acceptButtonType );
      assertThat( result.type(), is( CycleType.Alternating ) );
      assertThat( result.baseGoal(), is( goalOptions.get( 1 ) ) );
   }//End Method
   
   @Test public void shouldProvideNoResultWhenOptionMissing() {
      CycleCreationResult result = systemUnderTest.call( acceptButtonType );
      assertThat( result, is( nullValue() ) );
      
      types.getSelectionModel().select( 0 );
      result = systemUnderTest.call( acceptButtonType );
      assertThat( result, is( nullValue() ) );
      
      types.getSelectionModel().clearSelection();
      goals.getSelectionModel().select( 1 );
      result = systemUnderTest.call( acceptButtonType );
      assertThat( result, is( nullValue() ) );
   }//End Method
   
   @Test public void shouldProvideNoResultWhenCancelled() {
      types.getSelectionModel().select( 0 );
      goals.getSelectionModel().select( 1 );
      
      CycleCreationResult result = systemUnderTest.call( cancelButtonType );
      assertThat( result, is( nullValue() ) );
   }//End Method

}//End Class
