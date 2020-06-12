package uk.dangrew.nuts.graphics.meal;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.Node;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.javafx.table.controls.TableControlType;
import uk.dangrew.kode.javafx.table.controls.TableControls;

import java.awt.*;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MealControlsTest {

   @Captor private ArgumentCaptor< Button > buttonCaptor;
   private TableControls controls;
   
   @Spy private JavaFxStyle styling;
   @Mock private MealTableController callBack;
   private MealControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new MealControls( styling, callBack );
      controls = new TableControls( systemUnderTest );
   }//End Method

   @Test public void shouldProvideButtons() {
      assertThat( controls.getChildren(), contains(
               systemUnderTest.upButton(),
               systemUnderTest.downButton()
      ) );
      
      Node upGraphic = systemUnderTest.upButton().getGraphic();
      MaterialDesignIconView upGlyph = ( MaterialDesignIconView ) upGraphic;
      assertThat( upGlyph.getGlyphName(), is( MaterialDesignIcon.CHEVRON_UP.name() ) );
      
      Node downGraphic = systemUnderTest.downButton().getGraphic();
      MaterialDesignIconView downGlyph = ( MaterialDesignIconView ) downGraphic;
      assertThat( downGlyph.getGlyphName(), is( MaterialDesignIcon.CHEVRON_DOWN.name() ) );
      
      verify( styling, times( 2 ) ).createGlyphButton( Mockito.any() );
      
      assertThat( systemUnderTest.upButton().getPrefHeight(), is( TableControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.upButton().getPrefWidth(), is( TableControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.downButton().getPrefHeight(), is( TableControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.downButton().getPrefWidth(), is( TableControls.BUTTON_WIDTH ) );
   }//End Method
   
   @Test public void shouldDirectCallsToCallBack() {
      systemUnderTest.upButton().fire();
      verify( callBack ).moveUp();
      
      systemUnderTest.downButton().fire();
      verify( callBack ).moveDown();
   }//End Method
   
   @Test public void shouldProvideButtonsForTypes(){
      assertThat( systemUnderTest.getButton( TableControlType.Up ), is( systemUnderTest.upButton() ) );
      assertThat( systemUnderTest.getButton( TableControlType.Down ), is( systemUnderTest.downButton() ) );
   }//End Method

}//End Class
