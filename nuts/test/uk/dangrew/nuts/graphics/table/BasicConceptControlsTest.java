package uk.dangrew.nuts.graphics.table;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.Node;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.table.controls.TableControlType;
import uk.dangrew.nuts.graphics.table.controls.TableControls;

import java.awt.*;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BasicConceptControlsTest {

   private TableControls controls;
   @Captor private ArgumentCaptor< Button > buttonCaptor;
   
   @Spy private JavaFxStyle styling;
   @Mock private ConceptTableController< Food > callBack;
   private BasicConceptControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new BasicConceptControls( styling, callBack );
      controls = new TableControls( systemUnderTest );
   }//End Method

   @Test public void shouldProvideButtons() {
      assertThat( controls.getChildren(), contains(
               systemUnderTest.addButton(),
               systemUnderTest.copyButton(), 
               systemUnderTest.removeButton()
      ) );
      
      Node addGraphic = systemUnderTest.addButton().getGraphic();
      MaterialDesignIconView addGlyph = ( MaterialDesignIconView ) addGraphic;
      assertThat( addGlyph.getGlyphName(), is( MaterialDesignIcon.PLUS.name() ) );
      
      Node copyGraphic = systemUnderTest.copyButton().getGraphic();
      MaterialDesignIconView copyGlyph = ( MaterialDesignIconView ) copyGraphic;
      assertThat( copyGlyph.getGlyphName(), is( MaterialDesignIcon.CONTENT_COPY.name() ) );
      
      Node removeGraphic = systemUnderTest.removeButton().getGraphic();
      MaterialDesignIconView removeGlyph = ( MaterialDesignIconView ) removeGraphic;
      assertThat( removeGlyph.getGlyphName(), is( MaterialDesignIcon.MINUS.name() ) );
      
      verify( styling, times( 3 ) ).createGlyphButton( Mockito.any() );
      
      assertThat( systemUnderTest.addButton().getPrefHeight(), is( TableControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.addButton().getPrefWidth(), is( TableControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.copyButton().getPrefHeight(), is( TableControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.copyButton().getPrefWidth(), is( TableControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.removeButton().getPrefHeight(), is( TableControls.BUTTON_WIDTH ) );
      assertThat( systemUnderTest.removeButton().getPrefWidth(), is( TableControls.BUTTON_WIDTH ) );
   }//End Method
   
   @Test public void shouldDirectCallsToCallBack() {
      systemUnderTest.addButton().fire();
      verify( callBack ).createConcept();
      
      systemUnderTest.copyButton().fire();
      verify( callBack ).copySelectedConcept();
      
      systemUnderTest.removeButton().fire();
      verify( callBack ).removeSelectedConcept();
   }//End Method

   @Test public void shouldProvideButtonsForTypes(){
      assertThat( systemUnderTest.getButton( TableControlType.Add ), is( systemUnderTest.addButton() ) );
      assertThat( systemUnderTest.getButton( TableControlType.Copy ), is( systemUnderTest.copyButton() ) );
      assertThat( systemUnderTest.getButton( TableControlType.Remove ), is( systemUnderTest.removeButton() ) );
   }//End Method
   
}//End Class
