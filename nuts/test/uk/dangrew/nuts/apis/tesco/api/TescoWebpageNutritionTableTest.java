package uk.dangrew.nuts.apis.tesco.api;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.manual.data.TescoExamples;

public class TescoWebpageNutritionTableTest {

   @Mock private Document document;
   @Spy private Elements tableElements;
   @Mock private Element tableElement;
   @Spy private Elements headerElements;
   @Spy private Elements rowElements;
   
   private TescoWebpageNutritionTable systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      when( document.select( TescoWebpageNutritionTable.NUTRITION_TABLE_TAG ) ).thenReturn( tableElements );
      when( tableElements.first() ).thenReturn( tableElement );
      when( tableElement.select( TescoWebpageNutritionTable.HEADER_ENTRY ) ).thenReturn( headerElements );
      when( tableElement.select( TescoWebpageNutritionTable.ROW_ENTRY ) ).thenReturn( rowElements );
      
      systemUnderTest = new TescoWebpageNutritionTable();
   }//End Method

   @Test public void integrationTestWithRealData(){
      Document document = TescoExamples.crandaleMilkHtml();
      systemUnderTest = new TescoWebpageNutritionTable( document );
      
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
   
   @Test public void shouldProvideRowsAndColumnsForParsedData() {
      systemUnderTest.modifyColumnRow( 0, 4, "anything" );
      assertThat( systemUnderTest.columnRow( 0, 4 ), is( "anything" ) );
      assertThat( systemUnderTest.columnCount(), is( 1 ) );
      assertThat( systemUnderTest.rowCount(), is( 5 ) );
   }//End Method
   
   @Test public void shouldIgnoreNullDocument(){
      systemUnderTest = new TescoWebpageNutritionTable( null );
      assertThat( systemUnderTest.columnCount(), is( 1 ) );
      assertThat( systemUnderTest.rowCount(), is( 1 ) );
   }//End Method
   
   @Test public void shouldHandleMissingTableTag(){
      when( document.select( TescoWebpageNutritionTable.NUTRITION_TABLE_TAG ) ).thenReturn( new Elements() );
      
      systemUnderTest = new TescoWebpageNutritionTable( document );
      assertThat( systemUnderTest.columnCount(), is( 1 ) );
      assertThat( systemUnderTest.rowCount(), is( 1 ) );
   }//End Method
   
   @Test public void shouldHandleMissingHeadersTags(){
      when( tableElement.select( TescoWebpageNutritionTable.HEADER_ENTRY ) ).thenReturn( new Elements() );
      
      systemUnderTest = new TescoWebpageNutritionTable( document );
      assertThat( systemUnderTest.columnCount(), is( 1 ) );
      assertThat( systemUnderTest.rowCount(), is( 1 ) );
   }//End Method
   
   @Test public void shouldHandleMissingRowTags(){
      when( tableElement.select( TescoWebpageNutritionTable.ROW_ENTRY ) ).thenReturn( new Elements() );
      
      systemUnderTest = new TescoWebpageNutritionTable( document );
      assertThat( systemUnderTest.columnCount(), is( 1 ) );
      assertThat( systemUnderTest.rowCount(), is( 1 ) );
   }//End Method
   
   @Test public void shouldProvideBoundsCheck(){
      assertThat( systemUnderTest.columnRow( 34, 101 ), is( nullValue() ) );
      assertThat( systemUnderTest.columnRow( -34, -101 ), is( nullValue() ) );
   }//End Method

}//End Class
