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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

/**
 * {@link FoodControls} provides a set of basic controls for the {@link FoodTable}s.
 */
public class ConceptControls extends VBox {
   
   protected static final double BUTTON_WIDTH = 40.0;
   protected static final double INSETS = 4.0;
   
   private final Button add;
   private final Button copy;
   private final Button remove;
   
   public ConceptControls( ConceptTableController< ? > controller ) {
      this( new JavaFxStyle(), controller );
   }//End Constructor
   
   protected ConceptControls( JavaFxStyle styling, ConceptTableController< ? > controller ) {
      setAlignment( Pos.CENTER );
      setPadding( new Insets( INSETS ) );
      
      MaterialDesignIconView addGlyph = new MaterialDesignIconView( MaterialDesignIcon.PLUS );
      MaterialDesignIconView copyGlyph = new MaterialDesignIconView( MaterialDesignIcon.CONTENT_COPY );
      MaterialDesignIconView removeGlyph = new MaterialDesignIconView( MaterialDesignIcon.MINUS );
      
      getChildren().add( add = styling.createGlyphButton( addGlyph ) );
      getChildren().add( copy = styling.createGlyphButton( copyGlyph ) );
      getChildren().add( remove = styling.createGlyphButton( removeGlyph ) );
      
      add.setPrefSize( BUTTON_WIDTH, BUTTON_WIDTH );
      copy.setPrefSize( BUTTON_WIDTH, BUTTON_WIDTH );
      remove.setPrefSize( BUTTON_WIDTH, BUTTON_WIDTH );
      
      add.setOnAction( e -> controller.createConcept() );
      copy.setOnAction( e -> controller.copySelectedConcept() );
      remove.setOnAction( e -> controller.removeSelectedConcept() );
   }//End Constructor
   
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
