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
import uk.dangrew.nuts.graphics.table.controls.TableControlSet;
import uk.dangrew.nuts.graphics.table.controls.TableControlType;
import uk.dangrew.nuts.graphics.table.controls.TableControls;

public class MealControls implements TableControlSet {
   
   private final Button up;
   private final Button down;
   
   public MealControls( FoodHolderOperations controller ) {
      this( new JavaFxStyle(), controller );
   }//End Constructor
   
   MealControls( JavaFxStyle styling, FoodHolderOperations controller ) {
      MaterialDesignIconView upGlyph = new MaterialDesignIconView( MaterialDesignIcon.CHEVRON_UP );
      MaterialDesignIconView downGlyph = new MaterialDesignIconView( MaterialDesignIcon.CHEVRON_DOWN );
      
      up = styling.createGlyphButton( upGlyph );
      down = styling.createGlyphButton( downGlyph );
      
      up.setOnAction( e -> controller.moveUp() );
      down.setOnAction( e -> controller.moveDown() );
   }//End Constructor
   
   @Override public void addButtons( TableControls tableControls, double prefButtonWidth ) {
      up.setPrefSize( prefButtonWidth, prefButtonWidth );
      down.setPrefSize( prefButtonWidth, prefButtonWidth );
      
      tableControls.getChildren().add( 0, up );
      tableControls.getChildren().add( down );
   }//End Method
   
   @Override public Button getButton( TableControlType type ) {
      if ( type == TableControlType.Up ) {
         return up;
      } else if ( type == TableControlType.Down ) {
         return down;
      }
      
      return null;
   }//End Method
   
   public Button upButton() {
      return up;
   }//End Method
   
   public Button downButton() {
      return down;
   }//End Method
   
}//End Class
