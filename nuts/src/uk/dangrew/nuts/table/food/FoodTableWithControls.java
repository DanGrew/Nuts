/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.food;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodStore;

/**
 * {@link FoodTableWithControls} wraps a {@link FoodTable} with some {@link FoodControls}.
 */
public class FoodTableWithControls< FoodTypeT extends Food > extends TitledPane {
   
   static final String NO_CONTENT_INFORMATION = 
            "No Foods to display. Use the '+' and '-' to add and remove. Double click on the food name to change it.";
   
   private final FoodTable< FoodTypeT > table;
   private final FoodControls controls;
   
   /**
    * Constructs a new {@link FoodTableWithControls}.
    * @param title the title of the {@link TitledPane}.
    * @param foods the {@link FoodStore} of data.
    */
   public FoodTableWithControls( String title, FoodStore< FoodTypeT > foods ) {
      this( new JavaFxStyle(), title, foods );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodTableWithControls}.
    * @param styling the {@link JavaFxStyle}.
    * @param title the title of the {@link TitledPane}.
    * @param foods the {@link FoodStore} of data.
    */
   public FoodTableWithControls( JavaFxStyle styling, String title, FoodStore< FoodTypeT > foods ) {
      super( title, new BorderPane() );
      setCollapsible( false );
      BorderPane content = ( BorderPane ) getContent();
      content.setCenter( table = new FoodTable<>( foods ) );
      content.setRight( controls = new FoodControls( table.controller() ) );
      
      table.setPlaceholder( styling.createWrappedTextLabel( NO_CONTENT_INFORMATION ) );
   }//End Constructor

   public FoodTable< FoodTypeT > table() {
      return table;
   }//End Method
   
   FoodControls controls(){
      return controls;
   }//End Method

   BorderPane content(){
      return ( BorderPane ) getContent();
   }//End Method
   
}//End Class
