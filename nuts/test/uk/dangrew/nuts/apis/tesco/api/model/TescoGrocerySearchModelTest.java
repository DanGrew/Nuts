package uk.dangrew.nuts.apis.tesco.api.model;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.api.model.TescoGrocerySearchModel;
import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;
import uk.dangrew.nuts.apis.tesco.model.TescoFoodDescription;

public class TescoGrocerySearchModelTest {

   private static final String SAMPLE_TPNB = "3847563";
   
   private TescoFoodDescriptionStore store;
   private TescoGrocerySearchModel systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      store = new TescoFoodDescriptionStore();
      systemUnderTest = new TescoGrocerySearchModel( store );
   }//End Method

   @Test public void shouldFindExistingDescriptionAndUpdate() {
      store.createConcept( SAMPLE_TPNB, "anything" ).groceryProperties().image().set( "anything" );
      
      systemUnderTest.setTpnb( null, SAMPLE_TPNB );
      systemUnderTest.setImageLink( null, "image" );
      systemUnderTest.finishResult( null );
      
      assertThat( store.objectList(), hasSize( 1 ) );
      TescoFoodDescription description = store.get( SAMPLE_TPNB );
      assertThat( description, is( notNullValue() ) );
      assertThat( description.properties().id(), is( SAMPLE_TPNB ) );
      assertThat( description.groceryProperties().tpnb().get(), is( SAMPLE_TPNB ) );
      assertThat( description.groceryProperties().image().get(), is( "image" ) );
   }//End Method
   
   @Test public void shouldCreateNewDescription() {
      systemUnderTest.setTpnb( null, SAMPLE_TPNB );
      systemUnderTest.setImageLink( null, "image" );
      systemUnderTest.finishResult( null );
      
      TescoFoodDescription description = store.get( SAMPLE_TPNB );
      assertThat( description, is( notNullValue() ) );
      assertThat( description.properties().id(), is( SAMPLE_TPNB ) );
      assertThat( description.groceryProperties().tpnb().get(), is( SAMPLE_TPNB ) );
      assertThat( description.groceryProperties().image().get(), is( "image" ) );
   }//End Method
   
   @Test public void shouldFixImageLinkIssueWithHttps(){
      systemUnderTest.setTpnb( null, SAMPLE_TPNB );
      systemUnderTest.setImageLink( null, "http://anything" );
      systemUnderTest.finishResult( null );
      
      TescoFoodDescription description = store.get( SAMPLE_TPNB );
      assertThat( description.groceryProperties().image().get(), is( "https://anything" ) );
   }//End Method

}//End Class
