package uk.dangrew.nuts.graphics.cycle;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.GoalImpl;

public class UiCycleCreationDialogResultConverterTest {

   private ButtonType acceptButtonType;
   private ButtonType cancelButtonType;
   private ObservableList< Goal > goalOptions;
   private ComboBox< Goal > goals;
   private UiCycleCreationDialogResultConverter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      
      acceptButtonType = ButtonType.OK;
      cancelButtonType = ButtonType.CANCEL;
      
      goalOptions = FXCollections.observableArrayList();
      goalOptions.add( new GoalImpl( "GoalA" ) );
      goalOptions.add( new GoalImpl( "GoalB" ) );
      goalOptions.add( new GoalImpl( "GoalC" ) );
      
      goals = new ComboBox<>( goalOptions );
      systemUnderTest = new UiCycleCreationDialogResultConverter( acceptButtonType, goals );
   }//End Method

   @Test public void shouldConstructResultFromChosenOption() {
      goals.getSelectionModel().select( 1 );
      
      CycleCreationResult result = systemUnderTest.call( acceptButtonType );
      assertThat( result.baseGoal(), is( goalOptions.get( 1 ) ) );
   }//End Method
   
   @Test public void shouldProvideNoResultWhenOptionMissing() {
      CycleCreationResult result = systemUnderTest.call( acceptButtonType );
      assertThat( result, is( nullValue() ) );
      
      result = systemUnderTest.call( acceptButtonType );
      assertThat( result, is( nullValue() ) );
   }//End Method
   
   @Test public void shouldProvideNoResultWhenCancelled() {
      goals.getSelectionModel().select( 1 );
      
      CycleCreationResult result = systemUnderTest.call( cancelButtonType );
      assertThat( result, is( nullValue() ) );
   }//End Method

}//End Class
