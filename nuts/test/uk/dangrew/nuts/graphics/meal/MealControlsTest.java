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

public class MealControlsTest {

   @Captor private ArgumentCaptor< Button > buttonCaptor;
   
   @Spy private JavaFxStyle styling;
   @Mock private MealTableController callBack;
   private MealControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new MealControls( styling, callBack );
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
               systemUnderTest.upButton(),
               systemUnderTest.openButton(),
               systemUnderTest.addButton(), 
               systemUnderTest.copyButton(),
               systemUnderTest.removeButton(),
               systemUnderTest.downButton()
      ) );
      
      Node addGraphic = systemUnderTest.addButton().getGraphic();
      MaterialDesignIconView addGlyph = ( MaterialDesignIconView ) addGraphic;
      assertThat( addGlyph.getGlyphName(), is( MaterialDesignIcon.PLUS.name() ) );
      
      Node openGraphic = systemUnderTest.openButton().getGraphic();
      MaterialDesignIconView openGlyph = ( MaterialDesignIconView ) openGraphic;
      assertThat( openGlyph.getGlyphName(), is( MaterialDesignIcon.VIEW_GRID.name() ) );
      
      Node removeGraphic = systemUnderTest.removeButton().getGraphic();
      MaterialDesignIconView removeGlyph = ( MaterialDesignIconView ) removeGraphic;
      assertThat( removeGlyph.getGlyphName(), is( MaterialDesignIcon.MINUS.name() ) );
      
      Node copyGraphic = systemUnderTest.copyButton().getGraphic();
      MaterialDesignIconView copyGlyph = ( MaterialDesignIconView ) copyGraphic;
      assertThat( copyGlyph.getGlyphName(), is( MaterialDesignIcon.CONTENT_COPY.name() ) );
      
      Node upGraphic = systemUnderTest.upButton().getGraphic();
      MaterialDesignIconView upGlyph = ( MaterialDesignIconView ) upGraphic;
      assertThat( upGlyph.getGlyphName(), is( MaterialDesignIcon.CHEVRON_UP.name() ) );
      
      Node downGraphic = systemUnderTest.downButton().getGraphic();
      MaterialDesignIconView downGlyph = ( MaterialDesignIconView ) downGraphic;
      assertThat( downGlyph.getGlyphName(), is( MaterialDesignIcon.CHEVRON_DOWN.name() ) );
      
      verify( styling, times( 6 ) ).createGlyphButton( Mockito.any() );
      
      assertThat( systemUnderTest.addButton().getPrefHeight(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.addButton().getPrefWidth(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.openButton().getPrefHeight(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.openButton().getPrefWidth(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.removeButton().getPrefHeight(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.removeButton().getPrefWidth(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.copyButton().getPrefHeight(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.copyButton().getPrefWidth(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.upButton().getPrefHeight(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.upButton().getPrefWidth(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.downButton().getPrefHeight(), is( MealControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.downButton().getPrefWidth(), is( MealControls.BUTTON_WIDTH ) );
   }//End Method
   
   @Test public void shouldDirectCallsToCallBack() {
      systemUnderTest.addButton().fire();
      verify( callBack ).createConcept();
      
      systemUnderTest.openButton().fire();
      verify( callBack ).openTab();
      
      systemUnderTest.removeButton().fire();
      verify( callBack ).removeSelectedConcept();
      
      systemUnderTest.upButton().fire();
      verify( callBack ).moveUp();
      
      systemUnderTest.downButton().fire();
      verify( callBack ).moveDown();
   }//End Method

}//End Class
