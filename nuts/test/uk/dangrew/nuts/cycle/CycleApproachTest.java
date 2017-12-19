package uk.dangrew.nuts.cycle;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import javafx.collections.ObservableList;

public class CycleApproachTest {

   @Test public void shouldProvideObservableOptions() {
      ObservableList< CycleApproach > options = CycleApproach.observableOptions();
      assertThat( options, hasSize( CycleApproach.values().length ) );
      for ( CycleApproach approach : CycleApproach.values() ) {
         assertThat( options.contains( approach ), is( true ) );
      }
   }//End Method

}//End Class
