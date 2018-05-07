/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.meal;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.control.Button;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.table.ConceptControls;

public class RecipeControls extends ConceptControls {
   
   static final double INSETS = ConceptControls.INSETS;
   static final double BUTTON_WIDTH = ConceptControls.BUTTON_WIDTH;
   
   private final Button share;
   
   public RecipeControls( RecipeController controller ) {
      this( new JavaFxStyle(), controller );
   }//End Constructor
   
   RecipeControls( JavaFxStyle styling, RecipeController controller ) {
      super( styling, controller );
      
      MaterialDesignIconView shareGlyph = new MaterialDesignIconView( MaterialDesignIcon.SHARE );
      getChildren().add( share = styling.createGlyphButton( shareGlyph ) );
      
      share.setPrefSize( BUTTON_WIDTH, BUTTON_WIDTH );
      share.setOnAction( e -> controller.share() );
   }//End Constructor
   
   Button shareButton() {
      return share;
   }//End Method
   
   @Override protected Button addButton() {
      return super.addButton();
   }//End Method
   
   @Override protected Button copyButton() {
      return super.copyButton();
   }//End Method
   
   @Override protected Button removeButton() {
      return super.removeButton();
   }//End Method

}//End Class
