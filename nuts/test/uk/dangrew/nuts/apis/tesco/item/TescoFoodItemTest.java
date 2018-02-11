package uk.dangrew.nuts.apis.tesco.item;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class TescoFoodItemTest {

   private static final String ID = "aliuflakjdnfv";
   private static final String NAME = "Tesco Food";
   
   private TescoFoodItem systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TescoFoodItem( ID, NAME );
   }//End Method

   @Test public void shouldProvideConceptProperties() {
      assertThat( systemUnderTest.properties().id(), is( ID ) );
      assertThat( systemUnderTest.properties().nameProperty().get(), is( NAME ) );
   }//End Method
   
   @Test public void shouldProvideTescoProperties() {
      assertThat( systemUnderTest.productNumberForBaseProduct(), is( notNullValue() ) );
      assertThat( systemUnderTest.productNumberForConsumerUnit(), is( notNullValue() ) );
      assertThat( systemUnderTest.catalogueNumber(), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldProvideAssoiatedFoodItem() {
      assertThat( systemUnderTest.association(), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldNotDuplicate(){
      assertThat( systemUnderTest.duplicate( "" ), is( systemUnderTest ) );
   }//End Method

}//End Class
