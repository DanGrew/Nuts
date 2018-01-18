package uk.dangrew.nuts.graphics.cycle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.cycle.Cycle;

public class UiCycleSummary extends GridPane {

   private final ObjectProperty< Double > totalCalories;
   private final ObjectProperty< Double > remainingCalories;
   
   private final Label totalCaloriesLabel;
   private final Label remainingCaloriesLabel;
   
   private final UiCycleGoalsTableController controller;
   
   public UiCycleSummary( UiCycleGoalsTableController controller ) {
      this.totalCalories = new SimpleObjectProperty<>( 0.0 );
      this.remainingCalories = new SimpleObjectProperty<>( 0.0 );
      this.controller = controller;
      
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForEvenRows( this, 2 );
      styling.configureConstraintsForColumnPercentages( this, 40, 60 );
      
      add( styling.createBoldLabel( "Total Calories" ), 0, 0 );
      add( totalCaloriesLabel = new Label( "" ), 1, 0 );
      add( styling.createBoldLabel( "Remaining Calories" ), 0, 1 );
      add( remainingCaloriesLabel = new Label( "" ), 1, 1 );
      
      totalCalories.addListener( ( s, o, n ) -> totalCaloriesLabel.setText( "" + n + " kcal" ) );
      remainingCalories.addListener( ( s, o, n ) -> remainingCaloriesLabel.setText( "" + n + " kcal" ) );
      
      controller.focus().addListener( ( s, o, n ) -> updateFocus( o, n ) );
      updateSummary();
   }//End Constructor
   
   private void updateFocus( Cycle previous, Cycle focus ){
      //todo
   }//End Method
   
   private void updateSummary(){
      Cycle focus = controller.focus().get();
      totalCalories.set( focus.baseGoal().properties().calories().get() * focus.goals().size() );
   }//End Method
   
   Label totalCaloriesLabel(){
      return totalCaloriesLabel;
   }//End Method
   
   Label remainingCaloriesLabel(){
      return remainingCaloriesLabel;
   }//End Method
}//End Class
