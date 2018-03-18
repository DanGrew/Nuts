package uk.dangrew.nuts.apis.tesco.model.nuts;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.ModelVerifier;

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
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideStringProperty( d -> d.groceryProperties().image() )
         .shouldProvideStringProperty( d -> d.groceryProperties().superDepartment() )
         .shouldProvideStringProperty( d -> d.groceryProperties().tpnb() )
         .shouldProvideDoubleProperty( d -> d.groceryProperties().unitOfSale() )
         .shouldProvideStringProperty( d -> d.groceryProperties().unitQuantity() )
         .shouldProvideStringProperty( d -> d.groceryProperties().promotionDescription() )
         .shouldProvideStringProperty( d -> d.groceryProperties().contentsMeasureType() )
         .shouldProvideStringProperty( d -> d.groceryProperties().name() )
         .shouldProvideDoubleProperty( d -> d.groceryProperties().averageSellingUnitWeight() )
         .shouldProvideStringProperty( d -> d.groceryProperties().id() )
         .shouldProvideStringProperty( d -> d.groceryProperties().department() )
         .shouldProvideDoubleProperty( d -> d.groceryProperties().price() )
         .shouldProvideDoubleProperty( d -> d.groceryProperties().unitPrice() )
         .shouldProvideBooleanProperty( d -> d.groceryProperties().isSpecialOffer() )
         .shouldProvideCollection( d -> d.groceryProperties().description() );
   }//End Method

}//End Class
