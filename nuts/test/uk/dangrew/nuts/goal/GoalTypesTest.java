package uk.dangrew.nuts.goal;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GoalTypesTest {

   @Test public void shouldProvideCalculator() {
      for ( GoalTypes type : GoalTypes.values() ) {
         assertThat( type.calculator(), is( notNullValue() ) );
         assertThat( type.calculator(), is( type.calculator() ) );
      }
   }//End Method

}//End Class
