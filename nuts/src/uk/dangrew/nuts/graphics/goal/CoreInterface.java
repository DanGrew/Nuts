/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.goal;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.graphics.main.CoreInterfaceOperations;
import uk.dangrew.nuts.graphics.main.NutsTabs;
import uk.dangrew.nuts.graphics.selection.view.FoodSelectionWindow;
import uk.dangrew.nuts.store.Database;

public class CoreInterface extends BorderPane {

   private final Database database;
   private final NutsTabs tabPane;
   
   public CoreInterface() {
      this.database = new Database();
      setPrefSize( 800, 600 );
      
      CoreInterfaceOperations operations = new CoreInterfaceOperations( database );
      NutsSettings settings = new NutsSettings();
      new FoodSelectionWindow( settings, database );
      
      this.tabPane = new NutsTabs( settings, database, operations );
      this.setCenter( tabPane );
   }//End Constructor
   
}//End Class
