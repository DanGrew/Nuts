package uk.dangrew.nuts.apis.tesco.graphics.selection;

import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.UiFoodSelectionTile;
import uk.dangrew.nuts.graphics.selection.UiFoodTileConceptTitle;

public class UiTescoFoodTile extends UiFoodSelectionTile {

   static final Color DESELECTED_BACKGROUND = Color.WHITE;
   static final Color SELECTED_BACKGROUND = Color.ORANGE;
   
   private final JavaFxStyle styling;
   private final TescoFoodItem food;
   private final UiTescoFoodSelector controller;
   private final UiFoodTileConceptTitle title;
   
   public UiTescoFoodTile( TescoFoodItem food, UiTescoFoodSelector controller ) {
      this( 
               food, 
               controller, 
               new UiFoodTileConceptTitle( food ) 
      );
   }//End Constructor
   
   UiTescoFoodTile(
            TescoFoodItem food,
            UiTescoFoodSelector controller, 
            UiFoodTileConceptTitle title
   ) {
      this.food = food;
      this.controller = controller;
      this.title = title;
      
      this.styling = new JavaFxStyle();
      this.styling.configureConstraintsForRowPercentages( this, 100 );
      this.styling.configureConstraintsForEvenColumns( this, 1 );
      
      this.setPadding( new Insets( 20 ) );
      this.setPrefHeight( 20 );

      this.setSelected( false );
      this.setBorder( styling.borderFor( Color.BLACK, 2 ) );
      
      this.add( title, 0, 0 );
      
      this.setOnMouseClicked( this::clicked );
   }//End Constructor
   
   private void clicked( MouseEvent event ) {
      if ( controller.isSelected( food ) ) {
         controller.deselect( food );
      } else {
         controller.select( food );
      }
   }//End Method
   
   @Override public void updateSelection( boolean selected ) {
      if ( selected ) {
         setBackground( styling.backgroundFor( SELECTED_BACKGROUND ) );
      } else {
         setBackground( styling.backgroundFor( DESELECTED_BACKGROUND ) );
      }
   }//End Method
   
   @Override public FoodPortion food() {
      return null;
   }//End Method
   
}//End Class
