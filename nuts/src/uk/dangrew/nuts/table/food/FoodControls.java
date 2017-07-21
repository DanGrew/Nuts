/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.food;

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
public class FoodControls extends VBox {
   
   static final double BUTTON_WIDTH = 40.0;
   static final double INSETS = 4.0;
   
   private final Button add;
   private final Button remove;
   
   /**
    * Constructs a new {@link FoodControls}.
    * @param callBack the {@link FoodControlsInterface} to call back to.
    */
   public FoodControls( FoodControlsInterface< ? > callBack ) {
      this( new JavaFxStyle(), callBack );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodControls}.
    * @param styling the {@link JavaFxStyle}.
    * @param callBack the {@link FoodControlsInterface} to call back to.
    */
   FoodControls( JavaFxStyle styling, FoodControlsInterface< ? > callBack ) {
      setAlignment( Pos.CENTER );
      setPadding( new Insets( INSETS ) );
      
      MaterialDesignIconView addGlyph = new MaterialDesignIconView( MaterialDesignIcon.PLUS );
      MaterialDesignIconView removeGlyph = new MaterialDesignIconView( MaterialDesignIcon.MINUS );
      
      getChildren().add( add = styling.createGlyphButton( addGlyph ) );
      getChildren().add( remove = styling.createGlyphButton( removeGlyph ) );
      
      add.setPrefSize( BUTTON_WIDTH, BUTTON_WIDTH );
      remove.setPrefSize( BUTTON_WIDTH, BUTTON_WIDTH );
      
      add.setOnAction( e -> callBack.createFood() );
      remove.setOnAction( e -> callBack.removeSelectedFood() );
   }//End Constructor
   
   Button addButton(){
      return add;
   }//End Method
   
   Button removeButton(){
      return remove;
   }//End Method

}//End Class
