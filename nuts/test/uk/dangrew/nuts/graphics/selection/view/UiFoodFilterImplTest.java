package uk.dangrew.nuts.graphics.selection.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.selection.model.FoodFilterModel;
import uk.dangrew.nuts.store.Database;

public class UiFoodFilterImplTest {

   private Database database;
   private FoodFilterModel model;
   private UiFoodFilterImpl systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new UiFoodFilterImpl( database, model );
   }//End Method

   @Test public void untested() {
      System.out.println( "Not tested" );
   }//End Method

}//End Class
