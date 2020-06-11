package uk.dangrew.nuts.graphics.tutorial.architecture.manipulation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.common.UiEnumTypeSelectionDialog;
import uk.dangrew.nuts.graphics.database.FoodTypes;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EnumDialogManipulatorTest {

   private UiEnumTypeSelectionDialog< FoodTypes > dialog;
   private EnumDialogManipulator< FoodTypes > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );

      JavaFxThreading.runAndWait( () -> dialog = new UiEnumTypeSelectionDialog<>( FoodTypes.class, FoodTypes.FoodItems ) );
      systemUnderTest = new EnumDialogManipulator<>( dialog );
   }//End Method

   @Test public void shouldDisableInput() {
      assertThat( systemUnderTest.disableInput(), is( systemUnderTest ) );
      assertThat( dialog.getDialogPane().isMouseTransparent(), is( true ) );
      //should consume but assertions arent working.
   }//End Method
   
   @Test public void shouldSelect(){
      systemUnderTest.select( FoodTypes.Meals );
      assertThat( dialog.getSelectedItem(), is( FoodTypes.Meals ) );
      assertThat( dialog.getResult(), is( FoodTypes.Meals ) );
   }//End Method
   
   @Test public void shouldClose(){
      systemUnderTest.close();
      assertThat( dialog.isShowing(), is( false ) );
   }//End Method
   
   @Test public void shouldSelectAndClose(){
      systemUnderTest.selectAndClose( FoodTypes.Meals );
      assertThat( dialog.getSelectedItem(), is( FoodTypes.Meals ) );
      assertThat( dialog.getResult(), is( FoodTypes.Meals ) );
      assertThat( dialog.isShowing(), is( false ) );
   }//End Method

}//End Class
