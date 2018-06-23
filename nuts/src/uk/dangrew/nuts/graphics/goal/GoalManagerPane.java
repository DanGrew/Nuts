/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.goal;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.graphics.food.FoodTableColumns;
import uk.dangrew.nuts.graphics.table.ConceptControls;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.store.Database;

public class GoalManagerPane extends GridPane {

   static final double GOALS_HEIGHT_PROPORTION = 25.0;
   static final double CALCULATION_VIEW_HEIGHT_PROPORTION = 75.0;

   private final ConceptTableWithControls< Goal > goalsTable;
   private final GoalTableController goalsController;
   private final GoalCalculationView goalView;

   public GoalManagerPane( Database database ) {
      this( new JavaFxStyle(), database );
   }// End Constructor

   GoalManagerPane( JavaFxStyle styling, Database database ) {
      styling.configureConstraintsForRowPercentages( 
               this, 
               GOALS_HEIGHT_PROPORTION,
               CALCULATION_VIEW_HEIGHT_PROPORTION
      );
      styling.configureConstraintsForEvenColumns( this, 1 );

      add( goalsTable =  
               new TableComponents< Goal >()
                  .withDatabase( database )
                  .withColumns( FoodTableColumns< Goal >::new )
                  .withController( goalsController = new GoalTableController( database.calorieGoals(), database.proportionGoals() ) )
                  .withControls( new ConceptControls( goalsController ) )
                  .buildTableWithControls( "Goals" ),
      0, 0 );
      ScrollPane scroller = new ScrollPane( goalView = new GoalCalculationView() );
      scroller.setFitToWidth( true );
      add( scroller, 0, 1 );
      
      goalsTable.table().getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         goalView.show( n.concept() );
      } );
   }// End Constructor

   ConceptTableWithControls< Goal > templatesTable() {
      return goalsTable;
   }// End Method

   GoalCalculationView goalView(){
      return goalView;
   }//End Method

}//End Class
