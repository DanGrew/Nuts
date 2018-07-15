package uk.dangrew.nuts.recipe.constraint;

import java.util.List;
import java.util.Optional;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NoFeasibleSolutionException;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.linear.UnboundedSolutionException;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class RecipeFunction {

   private final ObjectProperty< NutritionalUnit > unit;
   private final ObjectProperty< GoalType > goalType;
   
   public RecipeFunction() {
      this( null, null );
   }//End Constructor
   
   public RecipeFunction( NutritionalUnit unit, GoalType goal ) {
      this.unit = new SimpleObjectProperty<>( unit );
      this.goalType = new SimpleObjectProperty<>( goal );
   }//End Constructor

   public ObjectProperty< GoalType > goalType() {
      return goalType;
   }//End Method
   
   public ObjectProperty< NutritionalUnit > unit() {
      return unit;
   }//End Method

   public Optional< LinearObjectiveFunction > generate( List< Food > foods ) {
      if ( !hasSufficientParameters() ) {
         return Optional.empty();
      }
      double[] coeffecients = new double[ foods.size() ];
      for ( int i = 0; i < foods.size(); i++ ) {
         coeffecients[ i ] = foods.get( i ).nutrition().of( unit.get() ).get();
      }
      return Optional.of( new LinearObjectiveFunction( coeffecients, 0 ) );
   }//End Method
   
   private boolean hasSufficientParameters(){
      if ( !Optional.ofNullable( goalType.get() ).isPresent() ) {
         return false;
      }
      if ( !Optional.ofNullable( unit.get() ).isPresent() ) {
         return false;
      }
      return true;
   }//End Method
   
   public Optional< PointValuePair > solve( List< Food > foods, LinearConstraintSet constraints ){
      Optional< LinearObjectiveFunction > function = generate( foods );
      if ( !function.isPresent() ) {
         return Optional.empty();
      }

      try {
         return Optional.ofNullable( new SimplexSolver().optimize(
                  function.get(), 
                  constraints, 
                  goalType.get() 
         ) );
      } catch ( NoFeasibleSolutionException | UnboundedSolutionException exception ) {
         return Optional.empty();
      }
   }//End Method

}//End Class
