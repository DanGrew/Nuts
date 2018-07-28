/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.control.Button;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.table.controls.TableControlSet;
import uk.dangrew.nuts.graphics.table.controls.TableControlType;
import uk.dangrew.nuts.graphics.table.controls.TableControls;

public class BasicConceptControls implements TableControlSet {
   
   private final Button add;
   private final Button copy;
   private final Button remove;
   
   public BasicConceptControls( ConceptTableController< ? > controller ) {
      this( new JavaFxStyle(), controller );
   }//End Constructor
   
   protected BasicConceptControls( JavaFxStyle styling, ConceptTableController< ? > controller ) {
      MaterialDesignIconView addGlyph = new MaterialDesignIconView( MaterialDesignIcon.PLUS );
      MaterialDesignIconView copyGlyph = new MaterialDesignIconView( MaterialDesignIcon.CONTENT_COPY );
      MaterialDesignIconView removeGlyph = new MaterialDesignIconView( MaterialDesignIcon.MINUS );
      
      add = styling.createGlyphButton( addGlyph );
      copy = styling.createGlyphButton( copyGlyph );
      remove = styling.createGlyphButton( removeGlyph );
      
      add.setOnAction( e -> controller.createConcept() );
      copy.setOnAction( e -> controller.copySelectedConcept() );
      remove.setOnAction( e -> controller.removeSelectedConcept() );
   }//End Constructor
   
   @Override public void addButtons( TableControls tableControls, double prefButtonWidth ) {
      add.setPrefSize( prefButtonWidth, prefButtonWidth );
      copy.setPrefSize( prefButtonWidth, prefButtonWidth );
      remove.setPrefSize( prefButtonWidth, prefButtonWidth );
      
      tableControls.getChildren().add( add );
      tableControls.getChildren().add( copy );
      tableControls.getChildren().add( remove );
   }//End Method
   
   @Override public Button getButton( TableControlType type ) {
      switch ( type ) {
         case Add:
            return add;
         case Copy:
            return copy;
         case Remove:
            return remove; 
         case Down:
         case Share:
         case Up:
         default:
            break;
      }
      return null;
   }//End Method
   
   public Button addButton(){
      return add;
   }//End Method
   
   public Button copyButton(){
      return copy;
   }//End Method
   
   public Button removeButton(){
      return remove;
   }//End Method

}//End Class
