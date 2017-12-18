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

public class MealControls extends ConceptControls {
   
   static final double INSETS = ConceptControls.INSETS;
   static final double BUTTON_WIDTH = ConceptControls.BUTTON_WIDTH;
   
   private final Button up;
   private final Button down;
   
   public MealControls( MealTableController controller ) {
      this( new JavaFxStyle(), controller );
   }//End Constructor
   
   MealControls( JavaFxStyle styling, MealTableController controller ) {
      super( styling, controller );
      
      MaterialDesignIconView upGlyph = new MaterialDesignIconView( MaterialDesignIcon.CHEVRON_UP );
      MaterialDesignIconView downGlyph = new MaterialDesignIconView( MaterialDesignIcon.CHEVRON_DOWN );
      
      getChildren().add( 0, up = styling.createGlyphButton( upGlyph ) );
      getChildren().add( down = styling.createGlyphButton( downGlyph ) );
      
      up.setPrefSize( BUTTON_WIDTH, BUTTON_WIDTH );
      down.setPrefSize( BUTTON_WIDTH, BUTTON_WIDTH );
      
      up.setOnAction( e -> controller.moveUp() );
      down.setOnAction( e -> controller.moveDown() );
   }//End Constructor
   
   Button upButton() {
      return up;
   }//End Method

   Button downButton() {
      return down;
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
