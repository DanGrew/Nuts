package uk.dangrew.nuts.apis.tesco.graphics.selection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class TescoStringParserTest {

   private TescoStringParser systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TescoStringParser();
   }//End Method

   @Test public void shouldParsePer100Header() {
      assertThat( systemUnderTest.parsePer100Header( "100g contains" ), is( "100g" ) );
      assertThat( systemUnderTest.parsePer100Header( "100g" ), is( "100g" ) );
      assertThat( systemUnderTest.parsePer100Header( "for 100g" ), is( "100g" ) );
      assertThat( systemUnderTest.parsePer100Header( "for 100ml" ), is( "100ml" ) );
      assertThat( systemUnderTest.parsePer100Header( "100ml contains" ), is( "100ml" ) );
      assertThat( systemUnderTest.parsePer100Header( "not normal" ), is( "not normal" ) );
   }//End Method
   
   @Test public void shouldParsePerServingHeader() {
      assertThat( systemUnderTest.parsePerServingHeader( "Each tablespoon (15g) contains" ), is( "15g" ) );
      assertThat( systemUnderTest.parsePerServingHeader( "Per 15g" ), is( "15g" ) );
      assertThat( systemUnderTest.parsePerServingHeader( "Each 15ml Portion" ), is( "15ml" ) );
      assertThat( systemUnderTest.parsePerServingHeader( "Cup (15ml)" ), is( "15ml" ) );
   }//End Method
   
   @Test public void shouldProvideDefaultHeader(){
      assertThat( systemUnderTest.parsePer100Header( null ), is( TescoStringParser.UNKNOWN_PER_100 ) );
      assertThat( systemUnderTest.parsePerServingHeader( null ), is( TescoStringParser.UNKNOWN_PER_SERVING ) );
   }//End Method

}//End Class
