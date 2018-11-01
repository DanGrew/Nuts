/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.recipe.integration;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.control.Button;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.table.controls.TableControlSet;
import uk.dangrew.nuts.graphics.table.controls.TableControlType;
import uk.dangrew.nuts.graphics.table.controls.TableControls;

public class RecipeGeneratorControls implements TableControlSet {
   
   private final Button generate;
   
   public RecipeGeneratorControls( RecipeGeneratorController controller ) {
      this( new JavaFxStyle(), controller );
   }//End Constructor
   
   RecipeGeneratorControls( JavaFxStyle styling, RecipeGeneratorController controller ) {
      MaterialDesignIconView generateGlyph = new MaterialDesignIconView( MaterialDesignIcon.SCALE );
      generate = styling.createGlyphButton( generateGlyph );
      generate.setOnAction( e -> controller.generate() );
   }//End Constructor
   
   @Override public void addButtons( TableControls tableControls, double prefButtonWidth ) {
      generate.setPrefSize( prefButtonWidth, prefButtonWidth );
      tableControls.getChildren().add( generate );
   }//End Method
   
   @Override public Button getButton( TableControlType type ) {
      if ( type == TableControlType.GenerateRecipe ) {
         return generate;
      }
      
      return null;
   }//End Method
   
   Button generateButton() {
      return generate;
   }//End Method
   
}//End Class
