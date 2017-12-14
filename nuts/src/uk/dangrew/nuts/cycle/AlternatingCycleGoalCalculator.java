package uk.dangrew.nuts.cycle;

import java.util.List;

import uk.dangrew.nuts.goal.Goal;

public class AlternatingCycleGoalCalculator {

   private AlternatingCycle cycle;
   
   public void initialiseGoals( AlternatingCycle cycle ) {
      this.cycle = cycle;
      
      Goal baseGoal = cycle.baseGoal();
      cycle.goals().clear();
      
      Goal lowDay = new Goal( baseGoal.properties().nameProperty().get() + "-Low" );
      cycle.goals().add( lowDay );
      
      Goal highDay = new Goal( baseGoal.properties().nameProperty().get() + "-High" );
      cycle.goals().add( highDay );
      
      updateGoals();
      
      cycle.numberOfDeficits().addListener( ( s, o, n ) -> updateGoals() );
      cycle.baseGoal().calorieDeficit().addListener( ( s, o, n ) -> updateGoals() );
   }//End Method
   
   private void updateGoals(){
      Goal baseGoal = cycle.baseGoal();
      Goal lowDay = cycle.goals().get( 0 );
      Goal highDay = cycle.goals().get( 1 );
      
      copyBaseGoal( baseGoal, lowDay );
      copyBaseGoal( baseGoal, highDay );
      calculateDeficits( baseGoal, lowDay, highDay );
   }//End Method
   
   private void copyBaseGoal( Goal baseGoal, Goal goal ) {
      goal.age().set( baseGoal.age().get() );
      goal.weight().set( baseGoal.weight().get() );
      goal.height().set( baseGoal.height().get() );
      goal.gender().set( baseGoal.gender().get() );
      
      goal.bmr().set( baseGoal.bmr().get() );
      goal.pal().set( baseGoal.pal().get() );
      goal.tee().set( baseGoal.tee().get() );
      
      goal.exerciseCalories().set( baseGoal.exerciseCalories().get() );
      goal.proteinPerPound().set( baseGoal.proteinPerPound().get() );
      goal.fatPerPound().set( baseGoal.fatPerPound().get() );
   }//End Method
   
   private void calculateDeficits( Goal baseGoal, Goal lowDay, Goal highDay ){
      double deficit = baseGoal.calorieDeficit().get();
      lowDay.calorieDeficit().set( deficit + deficit * cycle.numberOfDeficits().get() );
      highDay.calorieDeficit().set( -deficit * ( cycle.numberOfDeficits().get() - 1 ) );
   }//End Method

}//End Class
