package uk.dangrew.nuts.apis.tesco.graphics.selection;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;

public class UiTescoFoodSelectionViewTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> new UiTescoFoodSelectionView() );
      
      Thread.sleep( 999999999 );
   }//End Method

}//End Class
