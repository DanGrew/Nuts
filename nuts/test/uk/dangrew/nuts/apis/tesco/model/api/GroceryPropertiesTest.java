package uk.dangrew.nuts.apis.tesco.model.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.ModelVerifier;

public class GroceryPropertiesTest {

   private GroceryProperties systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new GroceryProperties();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideStringProperty( GroceryProperties::image )
         .shouldProvideStringProperty( GroceryProperties::superDepartment )
         .shouldProvideStringProperty( GroceryProperties::tpnb )
         .shouldProvideDoubleProperty( GroceryProperties::unitOfSale )
         .shouldProvideObservableList( GroceryProperties::description )
         .shouldProvideStringProperty( GroceryProperties::unitQuantity )
         .shouldProvideStringProperty( GroceryProperties::promotionDescription )
         .shouldProvideStringProperty( GroceryProperties::contentsMeasureType )
         .shouldProvideStringProperty( GroceryProperties::name )
         .shouldProvideDoubleProperty( GroceryProperties::averageSellingUnitWeight )
         .shouldProvideStringProperty( GroceryProperties::id )
         .shouldProvideDoubleProperty( GroceryProperties::contentsQuantity )
         .shouldProvideStringProperty( GroceryProperties::department )
         .shouldProvideDoubleProperty( GroceryProperties::price )
         .shouldProvideDoubleProperty( GroceryProperties::unitOfSale )
         .shouldProvideBooleanProperty( GroceryProperties::isSpecialOffer );
   }//End Method
   
}//End Class
