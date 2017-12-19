package uk.dangrew.nuts.cycle;

import java.util.function.Function;

import javafx.scene.Node;
import uk.dangrew.nuts.cycle.alternating.AlternatingCycle;
import uk.dangrew.nuts.graphics.cycle.alternating.AlternatingCyclePane;
import uk.dangrew.nuts.system.ConceptStore;
import uk.dangrew.nuts.system.InstanceVerifier;

public enum CycleType {

   Alternating(
            new InstanceVerifier<>( AlternatingCycle.class ),
            CycleStore::alternatingCycleStore, AlternatingCyclePane::new 
   )
   ;

   private final InstanceVerifier< Cycle > verifier;
   private final Function< CycleStore, ConceptStore< ? extends Cycle > > storeMapper;
   private final Function< Cycle, Node > configurationPaneSupplier;
   
   private CycleType(
            InstanceVerifier< Cycle > verifier,
            Function< CycleStore, ConceptStore< ? extends Cycle > > storeMapper,
            Function< Cycle, Node > configurationPaneSupplier
   ) {
      this.verifier = verifier;
      this.storeMapper = storeMapper;
      this.configurationPaneSupplier = configurationPaneSupplier;
   }//End Constructor
   
   public < TypeT extends Cycle > ConceptStore< TypeT > getStoreFrom( CycleStore cycles ) {
      return ( ConceptStore< TypeT > )storeMapper.apply( cycles );
   }//End Method

   public Node constructConfigurationPane( Cycle cycle ) {
      verifier.verify( cycle );
      return configurationPaneSupplier.apply( cycle );
   }//End Method
   
}//End Enum
