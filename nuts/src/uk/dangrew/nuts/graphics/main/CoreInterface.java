/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.main;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.graphics.selection.view.FoodSelectionWindow;
import uk.dangrew.nuts.store.Database;

public class CoreInterface extends BorderPane {

   private final Database database;
   private final NutsTabs tabPane;
   
   public CoreInterface() {
      this.database = new Database();
      this.setPrefSize( 800, 600 );
      
      CoreInterfaceOperations operations = new CoreInterfaceOperations( database );
      
      new FoodSelectionWindow( database );
      
      this.tabPane = new NutsTabs( database, operations );
      this.setCenter( tabPane );
      this.setTop( new NutsMenuBar() );
   }//End Constructor
   
}//End Class
