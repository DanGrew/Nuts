package uk.dangrew.nuts.graphics.selection;

import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.food.FoodPortion;

public class UiFoodTile extends GridPane {
   
   static final Color DESELECTED_BACKGROUND = Color.WHITE;
   static final Color SELECTED_BACKGROUND = Color.YELLOW;
   
   private final JavaFxStyle styling;
   private final FoodPortion food;
   private final UiFoodSelectionController controller;
   private final UiFoodTileTitle title;
   private final UiFoodTileMacros macros;
   private final UiFoodTilePortionControl control;
   
   public UiFoodTile( FoodPortion food, UiFoodSelectionController controller ) {
      this( 
               food, 
               controller, 
               new UiFoodTileTitle( food.food().get() ), 
               new UiFoodTileMacros( food ), 
               new UiFoodTilePortionControl( food )
      );
   }//End Constructor
   
   UiFoodTile(
            FoodPortion food,
            UiFoodSelectionController controller, 
            UiFoodTileTitle title,
            UiFoodTileMacros macros,
            UiFoodTilePortionControl control 
   ) {
      this.food = food;
      this.controller = controller;
      this.title = title;
      this.macros = macros;
      this.control = control;
      
      this.styling = new JavaFxStyle();
      this.styling.configureConstraintsForRowPercentages( this, 40, 30, 10, 20 );
      this.styling.configureConstraintsForEvenColumns( this, 1 );
      
      this.setPadding( new Insets( 20 ) );
      this.setPrefHeight( 20 );

      this.setSelected( false );
      this.setBorder( styling.borderFor( Color.BLACK, 2 ) );
      
      this.add( title, 0, 0 );
      this.add( macros, 0, 1 );
      this.add( control, 0, 3 );
      
      this.setOnMouseClicked( this::clicked );
   }//End Constructor
   
   private void clicked( MouseEvent event ) {
      if ( controller.isSelected( food ) ) {
         controller.deselect( food );
      } else {
         controller.select( food );
      }
   }//End Method
   
   public void setSelected( boolean selected ) {
      if ( selected ) {
         setBackground( styling.backgroundFor( SELECTED_BACKGROUND ) );
      } else {
         setBackground( styling.backgroundFor( DESELECTED_BACKGROUND ) );
      }
   }//End Method

   public FoodPortion food() {
      return food;
   }//End Method

}//End Class
