package uk.dangrew.nuts.graphics.table;

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
import uk.dangrew.nuts.food.Food;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class FoodControlsTest {

   @Captor private ArgumentCaptor< Button > buttonCaptor;
   
   @Spy private JavaFxStyle styling;
   @Mock private FoodTableController< Food > callBack;
   private FoodControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new FoodControls( styling, callBack );
   }//End Method

   @Test public void shouldProvideAlignment() {
      assertThat( systemUnderTest.getAlignment(), is( Pos.CENTER ) );
   }//End Method
   
   @Test public void shouldProvideInsets() {
      assertThat( systemUnderTest.getInsets().getBottom(), is( FoodControls.INSETS ) );
      assertThat( systemUnderTest.getInsets().getTop(), is( FoodControls.INSETS ) );
      assertThat( systemUnderTest.getInsets().getRight(), is( FoodControls.INSETS ) );
      assertThat( systemUnderTest.getInsets().getLeft(), is( FoodControls.INSETS ) );
   }//End Method
   
   @Test public void shouldProvideButtons() {
      assertThat( systemUnderTest.getChildren(), contains(
               systemUnderTest.addButton(), 
               systemUnderTest.removeButton()
      ) );
      
      Node addGraphic = systemUnderTest.addButton().getGraphic();
      MaterialDesignIconView addGlyph = ( MaterialDesignIconView ) addGraphic;
      assertThat( addGlyph.getGlyphName(), is( MaterialDesignIcon.PLUS.name() ) );
      
      Node removeGraphic = systemUnderTest.removeButton().getGraphic();
      MaterialDesignIconView removeGlyph = ( MaterialDesignIconView ) removeGraphic;
      assertThat( removeGlyph.getGlyphName(), is( MaterialDesignIcon.MINUS.name() ) );
      
      verify( styling, times( 2 ) ).createGlyphButton( Mockito.any() );
      
      assertThat( systemUnderTest.addButton().getPrefHeight(), is( FoodControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.addButton().getPrefWidth(), is( FoodControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.removeButton().getPrefHeight(), is( FoodControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.removeButton().getPrefWidth(), is( FoodControls.BUTTON_WIDTH ) );
   }//End Method
   
   @Test public void shouldDirectCallsToCallBack() {
      systemUnderTest.addButton().fire();
      verify( callBack ).createFood();
      
      systemUnderTest.removeButton().fire();
      verify( callBack ).removeSelectedFood();
   }//End Method

}//End Class
