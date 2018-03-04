package uk.dangrew.nuts.apis.tesco.item;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import uk.dangrew.kode.launch.TestApplication;

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
      shouldProvideProperty( systemUnderTest.image(), "anything" );
      shouldProvideProperty( systemUnderTest.superDepartment(),"anything" ); 
      shouldProvideProperty( systemUnderTest.tpnb(), "anything" );
      shouldProvideProperty( systemUnderTest.unitOfSale(), 101.2 );
      shouldProvideProperty( systemUnderTest.unitQuantity(), "anything" );
      shouldProvideProperty( systemUnderTest.promotionDescription(), "anything" );
      shouldProvideProperty( systemUnderTest.contentsMeasureType(), "anything" );
      shouldProvideProperty( systemUnderTest.name(), "anything" );
      shouldProvideProperty( systemUnderTest.averageSellingUnitWeight(), 101.2 ); 
      shouldProvideProperty( systemUnderTest.id(), "anything" );
      shouldProvideProperty( systemUnderTest.contentsQuantity(), 101.2 );
      shouldProvideProperty( systemUnderTest.department(), "anything" );
      shouldProvideProperty( systemUnderTest.price(), 101.2 );
      shouldProvideProperty( systemUnderTest.unitPrice(), 101.2 );
      shouldProvideProperty( systemUnderTest.isSpecialOffer(), true );
      
      assertThat( systemUnderTest.description(), is( empty() ) );
      systemUnderTest.description().add( "anything" );
      assertThat( systemUnderTest.description().contains( "anything" ), is( true ) );
   }//End Method
   
   private < TypeT > void shouldProvideProperty( ObjectProperty< TypeT > property, TypeT value ) {
      assertThat( property.get(), is( nullValue() ) );
      property.set( value );
      assertThat( property.get(), is( value ) );
   }//End Method

}//End Class
