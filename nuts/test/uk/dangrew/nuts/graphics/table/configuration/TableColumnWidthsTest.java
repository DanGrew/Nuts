package uk.dangrew.nuts.graphics.table.configuration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class TableColumnWidthsTest {

   private TableColumnWidths systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TableColumnWidths();
   }//End Method

   @Test public void shouldProvideNameColumnWidth() {
      assertThat( systemUnderTest.foodNameWidth(), is( TableColumnWidths.DEFAULT ) );
      assertThat( systemUnderTest.withFoodNameWidth( 0.4 ), is( systemUnderTest ) );
      assertThat( systemUnderTest.foodNameWidth(), is( 0.4 ) );
   }//End Method
   
   @Test public void shouldProvidePortionColumnWidth() {
      assertThat( systemUnderTest.portionWidth(), is( TableColumnWidths.DEFAULT ) );
      assertThat( systemUnderTest.withPortionWidth( 0.4 ), is( systemUnderTest ) );
      assertThat( systemUnderTest.portionWidth(), is( 0.4 ) );
   }//End Method
   
   @Test public void shouldProvideGoalColumnWidth() {
      assertThat( systemUnderTest.goalWidth(), is( TableColumnWidths.DEFAULT ) );
      assertThat( systemUnderTest.withGoalWidth( 0.4 ), is( systemUnderTest ) );
      assertThat( systemUnderTest.goalWidth(), is( 0.4 ) );
   }//End Method
   
   @Test public void shouldProvideUnitColumnWidth() {
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.unitWidthFor( unit ), is( TableColumnWidths.DEFAULT ) );
         assertThat( systemUnderTest.withUnitWidth( unit, 0.4 ), is( systemUnderTest ) );
         assertThat( systemUnderTest.unitWidthFor( unit ), is( 0.4 ) );
      }
   }//End Method
   
   @Test public void shouldDistributeSpaceForVisibleUnits() {
      assertThat( systemUnderTest.combinedUnitWidth(), is( TableColumnWidths.DEFAULT ) );
      assertThat( systemUnderTest.withCombinedUnitWidth( 0.4 ), is( systemUnderTest ) );
      assertThat( systemUnderTest.combinedUnitWidth(), is( 0.4 ) );
      
      assertThat( systemUnderTest.individualUnitWidthFor( 4 ), is( 0.1 ) );
   }//End Method

}//End Class
