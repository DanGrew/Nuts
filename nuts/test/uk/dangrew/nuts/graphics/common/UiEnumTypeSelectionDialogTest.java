package uk.dangrew.nuts.graphics.common;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.database.FoodTypes;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UiEnumTypeSelectionDialogTest {

   private UiEnumTypeSelectionDialog< FoodTypes > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      JavaFxThreading.runAndWait( () -> systemUnderTest = new UiEnumTypeSelectionDialog<>( FoodTypes.class, FoodTypes.Meals ) );
   }//End Method

   @Test public void shouldContainOptions() {
      assertThat( systemUnderTest.getItems(), containsInAnyOrder( FoodTypes.values() ) );
   }//End Method
   
   @Test public void shouldProvideDefaultChoice(){
      assertThat( systemUnderTest.getSelectedItem(), is( FoodTypes.Meals ) );
   }//End Method

}//End Class
