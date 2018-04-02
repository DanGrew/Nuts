package uk.dangrew.nuts.goal.proportion;

public class ProportionFunctions {

   interface ProportionFunction {
      
      public double calculateProgress( 
               double totalCalories,
               double totalWeight,
               double macroCalories,
               double macroWeight,
               double target
      );
      
      
   }//End Interface
   
   static class WeightProportionFunction implements ProportionFunction {

      @Override public double calculateProgress( 
               double totalCalories, 
               double totalWeight, 
               double macroCalories, 
               double macroWeight,
               double target
      ) {
         return macroWeight * 100.0 / target;
      }//End Method
      
   }//End Class
   
   static class CalorieProportionFunction implements ProportionFunction {

      @Override public double calculateProgress( 
               double totalCalories, 
               double totalWeight, 
               double macroCalories, 
               double macroWeight,
               double target
      ) {
         return macroCalories * 100.0 / target;
      }//End Method
      
   }//End Class
   
   static class PercentageOfWeightProportionFunction implements ProportionFunction {

      @Override public double calculateProgress( 
               double totalCalories, 
               double totalWeight, 
               double macroCalories, 
               double macroWeight,
               double target
      ) {
         if ( totalWeight == 0.0 ) {
            return 0.0;
         }
         double portionOfWeight = macroWeight * 100 / totalWeight;
         double portionOfTarget = portionOfWeight * 100 / target;
         return portionOfTarget;
      }//End Method
      
   }//End Class
   
   static class PercentageOfCaloriesProportionFunction implements ProportionFunction {

      @Override public double calculateProgress( 
               double totalCalories, 
               double totalWeight, 
               double macroCalories, 
               double macroWeight,
               double target
      ) {
         if ( totalCalories == 0.0 ) {
            return 0.0;
         }
         double portionOfCalories = macroCalories * 100 / totalCalories;
         double portionOfTarget = portionOfCalories * 100 / target;
         return portionOfTarget;
      }//End Method
      
   }//End Class
}//End Class
