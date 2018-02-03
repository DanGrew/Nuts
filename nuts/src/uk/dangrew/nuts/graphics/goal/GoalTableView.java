/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.goal;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import uk.dangrew.nuts.graphics.main.NutsTabs;
import uk.dangrew.nuts.graphics.selection.FoodSelectionWindow;
import uk.dangrew.nuts.persistence.fooditems.FoodSessions;
import uk.dangrew.nuts.store.Database;

/**
 * The {@link GoalTableView} provides the various elements of the system related to a single {@link uk.dangrew.nuts.goal.Goal}.
 */
public class GoalTableView extends BorderPane {

   private final Database database;
   private final TabPane tabPane;
   
   /**
    * Constructs a new {@link GoalTableView}.
    */
   public GoalTableView() {
      this.database = new Database();
      new FoodSelectionWindow( database );
      setPrefSize( 800, 600 );
      
      FoodSessions sessions = new FoodSessions( database );
      sessions.read();
      
      HBox controls = new HBox();
      Button load = new Button( "Load" );
      load.setOnAction( e -> sessions.read() );
      Button save = new Button( "Save" );
      save.setOnAction( e -> sessions.write() );
      controls.getChildren().addAll( load, save );
      
      this.setTop( controls );
      
      this.tabPane = new NutsTabs( database );
      this.setCenter( tabPane );
   }//End Constructor
   
}//End Class
