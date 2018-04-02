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
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.store.Database;

/**
 * {@link GoalManagerPane} provides the pane for managing {@link Goal}s.
 */
public class GoalManagerPane extends GridPane {

   static final double GOALS_HEIGHT_PROPORTION = 25.0;
   static final double CALCULATION_VIEW_HEIGHT_PROPORTION = 75.0;

   private final ConceptTableWithControls< Goal > goalsTable;
   private final GoalCalculationView goalView;

   /**
    * Constructs a new {@link GoalManagerPane}.
    * @param database the {@link Database}.
    */
   public GoalManagerPane( Database database ) {
      this( new JavaFxStyle(), database );
   }// End Constructor

   /**
    * Constructs a new {@link GoalManagerPane}.
    * @param styling the {@link JavaFxStyle}.
    * @param database the {@link Database}.
    */
   GoalManagerPane( JavaFxStyle styling, Database database ) {
      styling.configureConstraintsForRowPercentages( 
               this, 
               GOALS_HEIGHT_PROPORTION,
               CALCULATION_VIEW_HEIGHT_PROPORTION
      );
      styling.configureConstraintsForEvenColumns( this, 1 );

      add( goalsTable = new ConceptTableWithControls<>( "Goals", new GoalTable( database ) ), 0, 0 );
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
