package uk.dangrew.nuts.graphics.meal;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.Node;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.database.RecipeShareController;
import uk.dangrew.nuts.graphics.table.controls.TableControlType;
import uk.dangrew.nuts.graphics.table.controls.TableControls;

import java.awt.*;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ShareControlsTest {

   @Captor private ArgumentCaptor< Button > buttonCaptor;
   private TableControls controls;
   
   @Spy private JavaFxStyle styling;
   @Mock private RecipeShareController callBack;
   private ShareControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ShareControls( styling, callBack );
      controls = new TableControls( systemUnderTest );
   }//End Method

   @Test public void shouldProvideButtons() {
      assertThat( controls.getChildren(), contains(
               systemUnderTest.shareButton()
      ) );
      
      Node upGraphic = systemUnderTest.shareButton().getGraphic();
      MaterialDesignIconView shareGlyph = ( MaterialDesignIconView ) upGraphic;
      assertThat( shareGlyph.getGlyphName(), is( MaterialDesignIcon.SHARE.name() ) );
      
      verify( styling, times( 1 ) ).createGlyphButton( Mockito.any() );
      
      assertThat( systemUnderTest.shareButton().getPrefHeight(), is( TableControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.shareButton().getPrefWidth(), is( TableControls.BUTTON_WIDTH ) );
   }//End Method
   
   @Test public void shouldDirectCallsToCallBack() {
      systemUnderTest.shareButton().fire();
      verify( callBack ).share();
   }//End Method
   
   @Test public void shouldProvideButtonsForTypes(){
      assertThat( systemUnderTest.getButton( TableControlType.Share ), is( systemUnderTest.shareButton() ) );
   }//End Method

}//End Class
