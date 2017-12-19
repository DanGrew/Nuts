package uk.dangrew.nuts.cycle;

import java.util.function.Function;

import uk.dangrew.nuts.system.ConceptStore;

public enum CycleType {

   Alternating( CycleStore::alternatingCycleStore )
   ;

   private final Function< CycleStore, ConceptStore< ? extends Cycle > > storeMapper;
   
   private CycleType( Function< CycleStore, ConceptStore< ? extends Cycle > > storeMapper ) {
      this.storeMapper = storeMapper;
   }//End Constructor
   
   public < TypeT extends Cycle > ConceptStore< TypeT > getStoreFrom( CycleStore cycles ) {
      return ( ConceptStore< TypeT > )storeMapper.apply( cycles );
   }//End Method
   
}//End Enum
