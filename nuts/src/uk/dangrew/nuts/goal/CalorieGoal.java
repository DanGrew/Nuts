package uk.dangrew.nuts.goal;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.nuts.food.Food;

public interface CalorieGoal extends Food {

   /**
    * Access to the age property, measured in years.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > age();//End Method

   /**
    * Access to the weight property, measured in pounds.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > weight();//End Method

   /**
    * Access to the age height, measured in metres.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > height();//End Method

   /**
    * Access to the basal metabolic rate property, measured in calories.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > bmr();//End Method

   /**
    * Access to the physical activity level property, measured as a percentage of bmr.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > pal();//End Method

   /**
    * Access to the total energy expenditure calories property, measured in calories.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > tee();//End Method

   /**
    * Access to the exercise calories property.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > exerciseCalories();//End Method

   /**
    * Access to the calorie deficit property.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > calorieDeficit();//End Method

   /**
    * Access to the number of grams of protein per pound property. Recommended and default is 
    * {@value #RECOMMENDED_PROTEIN_PER_POUND}.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > proteinPerPound();//End Method

   /**
    * Access to the number of grams of fat per pound property. Recommended and default is 
    * {@value #RECOMMENDED_FAT_PER_POUND}.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > fatPerPound();//End Method

   /**
    * Access to the {@link Gender} of the user.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Gender > gender();//End Method

}//End Interface