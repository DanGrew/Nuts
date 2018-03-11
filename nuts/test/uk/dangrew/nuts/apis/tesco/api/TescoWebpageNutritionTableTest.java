package uk.dangrew.nuts.apis.tesco.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;

public class TescoWebpageNutritionTableTest {

   private TescoWebsiteParser websiteParser;
   private TescoWebpageNutritionTable systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      websiteParser = new TescoWebsiteParser();
      
      TescoFoodDescription description = new TescoFoodDescription( "Cravendale Filtered Whole Milk 2 Litre" );
      description.productDetail().tpncs().add( "257265436" );
      Document document = websiteParser.connectToProductPage( description );
      systemUnderTest = new TescoWebpageNutritionTable( document );
   }//End Method

//   @Test public void shouldProvideRowsAndColumnsForParsedData() {
//      fail( "Not yet implemented" );
//   }//End Method
   
   @Test public void integrationTestWithRealData(){
      assertThat( systemUnderTest.columnCount(), is( 3 ) );
      assertThat( systemUnderTest.rowCount(), is( 11 ) );

      assertThat( systemUnderTest.columnRow( 1, 0 ), is( "Typical Values Per 100ml" ) );
      assertThat( systemUnderTest.columnRow( 2, 0 ), is( "Per 200ml" ) );
      assertThat( systemUnderTest.columnRow( 1, 1 ), is( "271kJ/65kcal" ) );
      assertThat( systemUnderTest.columnRow( 2, 1 ), is( "542kJ/130kcal" ) );
      assertThat( systemUnderTest.columnRow( 1, 2 ), is( "3.6g" ) );
      assertThat( systemUnderTest.columnRow( 2, 2 ), is( "7.2g" ) );
      assertThat( systemUnderTest.columnRow( 1, 3 ), is( "2.3g" ) );
      assertThat( systemUnderTest.columnRow( 2, 3 ), is( "4.6g" ) );
      assertThat( systemUnderTest.columnRow( 1, 4 ), is( "4.7g" ) );
      assertThat( systemUnderTest.columnRow( 2, 4 ), is( "9.4g" ) );
      assertThat( systemUnderTest.columnRow( 1, 5 ), is( "4.7g" ) );
      assertThat( systemUnderTest.columnRow( 2, 5 ), is( "9.4g" ) );
      assertThat( systemUnderTest.columnRow( 1, 6 ), is( "3.4g" ) );
      assertThat( systemUnderTest.columnRow( 2, 6 ), is( "6.8g" ) );
      assertThat( systemUnderTest.columnRow( 1, 7 ), is( "0.1g" ) );
      assertThat( systemUnderTest.columnRow( 2, 7 ), is( "0.2g" ) );
      assertThat( systemUnderTest.columnRow( 1, 8 ), is( "122mg (15.3% RI)" ) );
      assertThat( systemUnderTest.columnRow( 2, 8 ), is( "243mg (30.4% RI)" ) );
   }// End Method
   
//   @Test public void shouldProvideBoundsCheck(){
//      fail();
//   }//End Method

}//End Class
