package uk.dangrew.nuts.graphics.label;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.label.LabelStore;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.store.Database;

public class UiLabelConfigurationViewTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      Database database = new Database();
      DataLocation.loadSampleFoodData( database );
      
      LabelStore labels = new LabelStore();
      labels.createConcept( "Keto" );
      labels.createConcept( "Refined" );
      TestApplication.launch( () -> new UiLabelConfigurationView( labels, database ) );
      
      Thread.sleep( 9999999 );
   }//End Method

}//End Class
