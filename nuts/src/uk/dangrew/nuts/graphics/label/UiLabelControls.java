/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.label;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

public class UiLabelControls extends VBox {
   
   protected static final double BUTTON_WIDTH = 40.0;
   protected static final double INSETS = 4.0;
   
   private final Button removeFromLabelButton;
   private final Button addToLabelButton;
   
   public UiLabelControls( UiLabelController controller ) {
      this( new JavaFxStyle(), controller );
   }//End Constructor
   
   protected UiLabelControls( JavaFxStyle styling, UiLabelController controller ) {
      setAlignment( Pos.CENTER );
      setPadding( new Insets( INSETS ) );
      
      MaterialDesignIconView rightGlyph = new MaterialDesignIconView( MaterialDesignIcon.ARROW_RIGHT_BOLD_CIRCLE_OUTLINE );
      MaterialDesignIconView leftGlyph = new MaterialDesignIconView( MaterialDesignIcon.ARROW_LEFT_BOLD_CIRCLE_OUTLINE );
      
      getChildren().add( removeFromLabelButton = styling.createGlyphButton( rightGlyph ) );
      getChildren().add( addToLabelButton = styling.createGlyphButton( leftGlyph ) );
      
      removeFromLabelButton.setPrefSize( BUTTON_WIDTH, BUTTON_WIDTH );
      addToLabelButton.setPrefSize( BUTTON_WIDTH, BUTTON_WIDTH );
      
      removeFromLabelButton.setOnAction( e -> controller.removeFromLabel() );
      addToLabelButton.setOnAction( e -> controller.addToLabel() );
   }//End Constructor
   
   Button removeButton(){
      return removeFromLabelButton;
   }//End Method
   
   Button addButton(){
      return addToLabelButton;
   }//End Method

}//End Class
