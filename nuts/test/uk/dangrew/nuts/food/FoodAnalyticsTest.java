package uk.dangrew.nuts.food;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.nutrients.MacroNutrient;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class FoodAnalyticsTest {

   private FoodAnalytics systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new FoodAnalytics();
   }//End Method

}//End Class
