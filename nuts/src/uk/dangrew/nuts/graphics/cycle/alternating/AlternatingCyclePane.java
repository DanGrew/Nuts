package uk.dangrew.nuts.graphics.cycle.alternating;

import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.javafx.custom.BoundTextProperty;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.custom.ResponsiveComboProperty;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;
import uk.dangrew.kode.javafx.style.TextFlowBuilder;
import uk.dangrew.nuts.cycle.Cycle;
import uk.dangrew.nuts.cycle.CycleApproach;
import uk.dangrew.nuts.cycle.alternating.AlternatingCycle;

public class AlternatingCyclePane extends BorderPane {

   public AlternatingCyclePane( Cycle uncastCycle ) {
      if ( !( uncastCycle instanceof AlternatingCycle ) ) {
         setCenter( new TextFlowBuilder().bold( "Incorrect Object Type..." ).build() );
         return;
      }
      
      AlternatingCycle cycle = ( AlternatingCycle ) uncastCycle;
      setCenter( new PropertiesPane( "Alternating Cycle Properties",
               new PropertyRowBuilder()
                  .withLabelName( "Cycle Approach" )
                  .withBinding( new ResponsiveComboProperty<>( 
                           CycleApproach.observableOptions(), 
                           CycleApproach.LowThenHigh, 
                           ( s, o, n ) -> cycle.approach().set( n ) 
                  ) ),
               new PropertyRowBuilder()
                  .withLabelName( "Number Of Deficits" )
                  .withBinding( new BoundTextProperty( 
                           cycle.numberOfDeficits(), 
                           true 
                  ) )
      ) );
   }//End Constructor
   
}//End Class
