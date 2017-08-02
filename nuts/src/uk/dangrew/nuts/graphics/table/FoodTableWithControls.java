/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.food.Food;

/**
 * {@link FoodTableWithControls} provides a {@link FoodTable} with {@link FoodControls}.
 */
public class FoodTableWithControls< FoodTypeT extends Food > extends TitledPane {

   static final String NO_CONTENT_INFORMATION = 
            "No Foods to display. Use the '+' and '-' to add and remove. Double click on the food name to change it.";
   
   private final FoodTable< FoodTypeT > table;
   private final FoodControls controls;
   
   /**
    * Constructs a new {@link FoodTableWithControls}.
    * @param title the title of the {@link TitledPane}.
    * @param table the {@link FoodTable} to hold.
    */
   public FoodTableWithControls( String title, FoodTable< FoodTypeT > table ) {
      this( new JavaFxStyle(), title, table );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodTableWithControls}.
    * @param styling the {@link JavaFxStyle}.
    * @param title the title of the {@link TitledPane}.
    * @param table the {@link FoodTable} to hold.
    */
   public FoodTableWithControls( 
            JavaFxStyle styling, 
            String title, 
            FoodTable< FoodTypeT > table 
   ) {
      super( title, new BorderPane() );
      this.table = table;
      this.setCollapsible( false );
      
      BorderPane content = ( BorderPane ) getContent();
      content.setCenter( table );
      content.setRight( controls = new FoodControls( table.controller() ) );
      
      table.setPlaceholder( styling.createWrappedTextLabel( NO_CONTENT_INFORMATION ) );
   }//End Constructor

   /**
    * Access to the {@link FoodTable}.
    * @return the {@link FoodTable}.
    */
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
