package uk.dangrew.nuts.graphics.meal;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Button;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.geometry.Pos;
import javafx.scene.Node;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class RecipeControlsTest {

   @Captor private ArgumentCaptor< Button > buttonCaptor;
   
   @Spy private JavaFxStyle styling;
   @Mock private RecipeController callBack;
   private RecipeControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new RecipeControls( styling, callBack );
   }//End Method

   @Test public void shouldProvideAlignment() {
      assertThat( systemUnderTest.getAlignment(), is( Pos.CENTER ) );
   }//End Method
   
   @Test public void shouldProvideInsets() {
      assertThat( systemUnderTest.getInsets().getBottom(), is( MealControls.INSETS ) );
      assertThat( systemUnderTest.getInsets().getTop(), is( MealControls.INSETS ) );
      assertThat( systemUnderTest.getInsets().getRight(), is( MealControls.INSETS ) );
      assertThat( systemUnderTest.getInsets().getLeft(), is( MealControls.INSETS ) );
   }//End Method
   
   @Test public void shouldProvideButtons() {
      assertThat( systemUnderTest.getChildren(), contains(
               systemUnderTest.addButton(), 
               systemUnderTest.copyButton(),
               systemUnderTest.removeButton(),
               systemUnderTest.shareButton()
      ) );
      
      Node addGraphic = systemUnderTest.addButton().getGraphic();
      MaterialDesignIconView addGlyph = ( MaterialDesignIconView ) addGraphic;
      assertThat( addGlyph.getGlyphName(), is( MaterialDesignIcon.PLUS.name() ) );
      
      Node removeGraphic = systemUnderTest.removeButton().getGraphic();
      MaterialDesignIconView removeGlyph = ( MaterialDesignIconView ) removeGraphic;
      assertThat( removeGlyph.getGlyphName(), is( MaterialDesignIcon.MINUS.name() ) );
      
      Node copyGraphic = systemUnderTest.copyButton().getGraphic();
      MaterialDesignIconView copyGlyph = ( MaterialDesignIconView ) copyGraphic;
      assertThat( copyGlyph.getGlyphName(), is( MaterialDesignIcon.CONTENT_COPY.name() ) );
      
      Node upGraphic = systemUnderTest.shareButton().getGraphic();
      MaterialDesignIconView shareGlyph = ( MaterialDesignIconView ) upGraphic;
      assertThat( shareGlyph.getGlyphName(), is( MaterialDesignIcon.SHARE.name() ) );
      
      verify( styling, times( 4 ) ).createGlyphButton( Mockito.any() );
      
      assertThat( systemUnderTest.addButton().getPrefHeight(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.addButton().getPrefWidth(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.removeButton().getPrefHeight(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.removeButton().getPrefWidth(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.copyButton().getPrefHeight(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.copyButton().getPrefWidth(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.shareButton().getPrefHeight(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.shareButton().getPrefWidth(), is( MealControls.BUTTON_WIDTH ) );
   }//End Method
   
   @Test public void shouldDirectCallsToCallBack() {
      systemUnderTest.addButton().fire();
      verify( callBack ).createConcept();
      
      systemUnderTest.removeButton().fire();
      verify( callBack ).removeSelectedConcept();
      
      systemUnderTest.shareButton().fire();
      verify( callBack ).share();
   }//End Method

}//End Class
