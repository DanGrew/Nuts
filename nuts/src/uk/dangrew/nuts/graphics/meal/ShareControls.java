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
import uk.dangrew.nuts.graphics.database.RecipeShareController;
import uk.dangrew.kode.javafx.table.controls.TableControlSet;
import uk.dangrew.kode.javafx.table.controls.TableControlType;
import uk.dangrew.kode.javafx.table.controls.TableControls;

public class ShareControls implements TableControlSet {
   
   private final Button share;
   
   public ShareControls( RecipeShareController controller ) {
      this( new JavaFxStyle(), controller );
   }//End Constructor
   
   ShareControls( JavaFxStyle styling, RecipeShareController controller ) {
      MaterialDesignIconView shareGlyph = new MaterialDesignIconView( MaterialDesignIcon.SHARE );
      share = styling.createGlyphButton( shareGlyph );
      share.setOnAction( e -> controller.share() );
   }//End Constructor
   
   @Override public void addButtons( TableControls tableControls, double prefButtonWidth ) {
      share.setPrefSize( prefButtonWidth, prefButtonWidth );
      tableControls.getChildren().add( share );
   }//End Method
   
   @Override public Button getButton( TableControlType type ) {
      if ( type == TableControlType.Share ) {
         return share;
      }
      
      return null;
   }//End Method
   
   Button shareButton() {
      return share;
   }//End Method
   
}//End Class
