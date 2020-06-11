package uk.dangrew.nuts.graphics.database;

import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.nuts.graphics.recipe.summary.UiRecipeSummary;
import uk.dangrew.nuts.meal.FoodHolder;

public class RecipeSummaryWindow {
   
   private final Stage stage;
   
   public RecipeSummaryWindow() {
      this( new Stage() );
   }//End Constructor
   
   RecipeSummaryWindow( Stage stage ) {
      this.stage = stage;
      this.stage.setAlwaysOnTop( true );
      this.stage.hide();
   }//End Constructor
   
   public void show( FoodHolder meal ){
      JavaFxThreading.runAndWait( () -> internal_show( meal ) );
   }//End Method
   
   private void internal_show( FoodHolder meal ) {
      this.stage.setScene( new Scene( new UiRecipeSummary( meal ) ) );
      this.stage.showAndWait();
   }//End Method
   
}//End Class
