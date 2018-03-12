package uk.dangrew.nuts.apis.tesco.model;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.dangrew.nuts.TestCommon.shouldProvideProperty;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.model.TescoFoodDescription;

public class TescoFoodDescriptionTest {

   private static final String ID = "elifbvlz";
   private static final String NAME = "skldhbva,sd";
   private TescoFoodDescription systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TescoFoodDescription( ID, NAME );
   }//End Method

   @Test public void shouldProvideProperties() {
      assertThat( systemUnderTest.properties().nameProperty().get(), is( NAME ) );
      assertThat( systemUnderTest.properties().id(), is( ID ) );
   }//End Method
   
   @Test public void shouldProvideDescriptionProperties(){
      shouldProvideProperty( systemUnderTest.groceryProperties().image(), "anything" );
      shouldProvideProperty( systemUnderTest.groceryProperties().superDepartment(),"anything" ); 
      shouldProvideProperty( systemUnderTest.groceryProperties().tpnb(), "anything" );
      shouldProvideProperty( systemUnderTest.groceryProperties().unitOfSale(), 101.2 );
      shouldProvideProperty( systemUnderTest.groceryProperties().unitQuantity(), "anything" );
      shouldProvideProperty( systemUnderTest.groceryProperties().promotionDescription(), "anything" );
      shouldProvideProperty( systemUnderTest.groceryProperties().contentsMeasureType(), "anything" );
      shouldProvideProperty( systemUnderTest.groceryProperties().name(), "anything" );
      shouldProvideProperty( systemUnderTest.groceryProperties().averageSellingUnitWeight(), 101.2 ); 
      shouldProvideProperty( systemUnderTest.groceryProperties().id(), "anything" );
      shouldProvideProperty( systemUnderTest.groceryProperties().contentsQuantity(), 101.2 );
      shouldProvideProperty( systemUnderTest.groceryProperties().department(), "anything" );
      shouldProvideProperty( systemUnderTest.groceryProperties().price(), 101.2 );
      shouldProvideProperty( systemUnderTest.groceryProperties().unitPrice(), 101.2 );
      shouldProvideProperty( systemUnderTest.groceryProperties().isSpecialOffer(), true );
      
      assertThat( systemUnderTest.groceryProperties().description(), is( empty() ) );
      systemUnderTest.groceryProperties().description().add( "anything" );
      assertThat( systemUnderTest.groceryProperties().description().contains( "anything" ), is( true ) );
   }//End Method

}//End Class
