/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.cycle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.cycle.alternating.AlternatingCycleStore;
import uk.dangrew.nuts.system.ConceptStore;

public class CycleStore implements ConceptStore< Cycle > {

   private static final String NON_WRITEABLE = "Cycle Store only wraps, creation not supported.";
   
   private final AlternatingCycleStore alternatingStore;
   private final ObservableList< Cycle > cycles;
   
   public CycleStore() {
      this( new AlternatingCycleStore() );
   }//End Constructor
   
   CycleStore( AlternatingCycleStore alternatingCycles ) {
      this.cycles = FXCollections.observableArrayList();
      this.alternatingStore = alternatingCycles;
      this.alternatingStore.objectList().addListener( new FunctionListChangeListenerImpl<>( 
               cycles::add, cycles::remove 
      ) );
   }//End Constructor
   
   public AlternatingCycleStore alternatingCycleStore(){
      return alternatingStore;
   }//End Method

   @Override public ObservableList< Cycle > objectList() {
      return cycles;
   }//End Method

   @Override public Cycle createConcept( String name ) {
      throw new UnsupportedOperationException( NON_WRITEABLE );
   }//End Method

   @Override public Cycle createConcept( String id, String name ) {
      throw new UnsupportedOperationException( NON_WRITEABLE );
   }//End Method

   @Override public void store( Cycle cycle ) {
      throw new UnsupportedOperationException( NON_WRITEABLE );
   }//End Method

   @Override public Cycle get( String id ) {
      Cycle found = alternatingStore.get( id );
      return found;
   }//End Method

   @Override public void removeConcept( Cycle cycle ) {
      throw new UnsupportedOperationException( NON_WRITEABLE );
   }//End Method

}//End Class
