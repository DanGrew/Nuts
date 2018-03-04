package uk.dangrew.nuts.apis.tesco.database;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class TescoDatabaseTest {

   private TescoDatabase systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TescoDatabase();
   }//End Method

   @Test public void shouldProvideDescriptions() {
      assertThat( systemUnderTest.descriptionStore(), is( instanceOf( TescoFoodDescriptionStore.class ) ) );
      assertThat( systemUnderTest.descriptionStore(), is( systemUnderTest.descriptionStore() ) );
   }//End Method
   
   @Test public void shouldProvideItems() {
      assertThat( systemUnderTest.itemStore(), is( instanceOf( TescoFoodItemStore.class ) ) );
      assertThat( systemUnderTest.itemStore(), is( systemUnderTest.itemStore() ) );
   }//End Method

}//End Class
