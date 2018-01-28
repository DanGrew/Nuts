package uk.dangrew.nuts.graphics.table;

import java.util.Comparator;

import javafx.collections.ObservableList;
import uk.dangrew.nuts.system.Concept;

public interface ConceptOptions< TypeT extends Concept > {

   public void customSort( Comparator< TypeT > comparator );//End Method

   public Comparator< TypeT > comparator();//End Method

   /**
    * Access to the {@link ObservableList} options.
    * @return the {@link ObservableList}.
    */
   public ObservableList< TypeT > options();//End Method

   /**
    * Method to find the first {@link Concept} with the given name.
    * @param name the name to look for.
    * @return the found {@link Concept}.
    */
   public TypeT find( String name );//End Method

   public TypeT first();//End Method

}//End Interface
