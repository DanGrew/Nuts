package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class FoodSelectionTypesTest {

   @Test public void shouldProvideComparators(){
      for ( FoodSelectionTypes type : FoodSelectionTypes.values() ) {
         assertThat( type.comparator(), is( notNullValue() ) );
      }
   }//End Method

}//End Class
