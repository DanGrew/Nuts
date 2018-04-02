package uk.dangrew.nuts.goal.proportion;

import uk.dangrew.nuts.goal.proportion.ProportionFunctions.CalorieProportionFunction;
import uk.dangrew.nuts.goal.proportion.ProportionFunctions.PercentageOfCaloriesProportionFunction;
import uk.dangrew.nuts.goal.proportion.ProportionFunctions.PercentageOfWeightProportionFunction;
import uk.dangrew.nuts.goal.proportion.ProportionFunctions.ProportionFunction;
import uk.dangrew.nuts.goal.proportion.ProportionFunctions.WeightProportionFunction;

public enum ProportionType {

   Weight( new WeightProportionFunction() ),
   Calorie( new CalorieProportionFunction() ),
   PercentageOfWeight( new PercentageOfWeightProportionFunction() ),
   PercentageOfCalories( new PercentageOfCaloriesProportionFunction() );
   
   private final ProportionFunction function;
   
   private ProportionType( ProportionFunction function ) {
      this.function = function;
   }//End Constructor
   
   public ProportionFunction function(){
      return function;
   }//End Method
   
}//End Enum
