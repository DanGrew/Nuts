/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.goal;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.graphics.main.CoreInterfaceOperations;
import uk.dangrew.nuts.graphics.main.NutsTabs;
import uk.dangrew.nuts.graphics.selection.view.FoodSelectionWindow;
import uk.dangrew.nuts.store.Database;

/**
 * The {@link GoalTableView} provides the various elements of the system related to a single {@link uk.dangrew.nuts.goal.Goal}.
 */
public class CoreInterface extends BorderPane {

   private final Database database;
   private final Node tabPane;
   
   /**
    * Constructs a new {@link GoalTableView}.
    */
   public CoreInterface() {
      this.database = new Database();
      setPrefSize( 800, 600 );
      
      CoreInterfaceOperations operations = new CoreInterfaceOperations( database );
      new FoodSelectionWindow( database );
      
      this.tabPane = new NutsTabs( database, operations );
      this.setCenter( tabPane );
   }//End Constructor
   
}//End Class
