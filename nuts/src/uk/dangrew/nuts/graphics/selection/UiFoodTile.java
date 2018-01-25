package uk.dangrew.nuts.graphics.selection;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.food.FoodPortion;

public class UiFoodTile extends GridPane {
   
   private FoodPortion food;
   
   private final UiFoodTileTitle title;
   private final UiFoodTileMacros macros;
   private final UiFoodTilePortionControl control;
   private final Button add;
   
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
      this.title = title;
      this.macros = macros;
      this.control = control;
      
      this.setPadding( new Insets( 20 ) );
      this.setPrefHeight( 20 );
      
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForRowPercentages( this, 20, 30, 20, 10, 20 );
      styling.configureConstraintsForEvenColumns( this, 1 );
      
      this.setBackground( styling.backgroundFor( Color.WHITE ) );
      this.setBorder( styling.borderFor( Color.BLACK, 2 ) );
      
      this.add = new Button( "+" );
      this.add.setOnAction( e -> controller.addPortion( food ) );
      this.add.setShape( new Circle( 20 ) );
      this.add( add, 0, 0 );
      GridPane.setHalignment( add, HPos.RIGHT );
      
      this.add( title, 0, 1 );
      this.add( macros, 0, 2 );
      this.add( control, 0, 4 );
   }//End Constructor

   public FoodPortion food() {
      return food;
   }//End Method

   public void detach() {
      food = null;
      title.detach();
      macros.detach();
      control.detach();
   }//End Method
   
   Button addButton(){
      return add;
   }//End Method

}//End Class
