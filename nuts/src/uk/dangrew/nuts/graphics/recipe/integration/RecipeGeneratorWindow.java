package uk.dangrew.nuts.graphics.recipe.integration;

import java.util.Optional;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import uk.dangrew.nuts.graphics.common.UiWindowControls;
import uk.dangrew.nuts.graphics.common.UiWindowControlsActionsImpl;
import uk.dangrew.nuts.graphics.recipe.creator.UiRecipeCreatorPane;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.store.Database;

public class RecipeGeneratorWindow {
   
   private final Stage stage;
   private boolean cancelled;
   
   public RecipeGeneratorWindow() {
      this( new Stage() );
   }//End Constructor
   
   RecipeGeneratorWindow( Stage stage ) {
      this.cancelled = false;
      this.stage = stage;
      this.stage.hide();
   }//End Constructor
   
   public Optional< FoodHolder > generate( Database database, FoodHolder original ){
      FoodHolder recipe = original.duplicate();
      PlatformImpl.runAndWait( () -> internal_show( database, recipe ) );
      if ( cancelled ) {
         return Optional.empty();
      } else {
         return Optional.of( recipe );
      }
   }//End Method
   
   private void internal_show( Database database, FoodHolder recipe ) {
      BorderPane content = new BorderPane( new UiRecipeCreatorPane( database, recipe ) );
      content.setBottom( new UiWindowControls( new UiWindowControlsActionsImpl( 
               () -> closeWindow( false ), 
               () -> closeWindow( true ) 
      ) ) );
      
      this.stage.setScene( new Scene( content ) );
      this.stage.showAndWait();
   }//End Method
   
   private void closeWindow( boolean cancelled ) {
      this.cancelled = cancelled;
      this.stage.close();
   }//End Method
   
}//End Class
