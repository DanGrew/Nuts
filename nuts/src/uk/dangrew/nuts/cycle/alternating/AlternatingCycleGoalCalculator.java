package uk.dangrew.nuts.cycle.alternating;

import uk.dangrew.nuts.goal.Goal;

public class AlternatingCycleGoalCalculator {

   private AlternatingCycle cycle;
   private Goal lowDay;
   private Goal highDay;
   
   public void initialiseGoals( AlternatingCycle cycle ) {
      this.cycle = cycle;
      cycle.goals().clear();
      
      lowDay = new Goal( cycle.properties().nameProperty().get() + "-Low" );
      highDay = new Goal( cycle.properties().nameProperty().get() + "-High" );

      orderGoals();
      updateGoals();
      
      cycle.numberOfDeficits().addListener( ( s, o, n ) -> updateGoals() );
      cycle.approach().addListener( ( s, o, n ) -> orderGoals() );
      cycle.baseGoal().calorieDeficit().addListener( ( s, o, n ) -> updateGoals() );
      cycle.properties().nameProperty().addListener( ( s, o, n ) -> renameGoals( o, n ) );
   }//End Method
   
   private void renameGoals( String originalCycleName, String newCycleName ) {
      cycle.goals().forEach( g -> {
         String newName = g.properties().nameProperty().get().replaceFirst( originalCycleName, newCycleName );
         g.properties().nameProperty().set( newName );
      } );
   }//End Method
   
   private void orderGoals(){
      cycle.goals().clear();
      switch ( cycle.approach().get() ) {
         case HighThenLow:
            cycle.goals().add( highDay );
            cycle.goals().add( lowDay );
            break;
         case LowThenHigh:
            cycle.goals().add( lowDay );
            cycle.goals().add( highDay );
            break;
         default:
            break;
      }
   }//End Method
   
   private void updateGoals(){
      Goal baseGoal = cycle.baseGoal();
      if ( baseGoal == null ) {
         return;
      }
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
