package uk.dangrew.nuts.graphics.table;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.meal.Meal;

public class ConceptTableRowTest {

   private Meal meal;
   private ConceptTableRow< Meal > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      meal = new Meal( "Meal" );
      systemUnderTest = new ConceptTableRow<>( meal );
   }//End Method

   @Test public void shouldProvideFood() {
      assertThat( systemUnderTest.concept(), is( meal ) );
   }//End Method

}//End Class
