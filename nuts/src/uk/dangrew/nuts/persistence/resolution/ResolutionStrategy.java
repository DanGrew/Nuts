package uk.dangrew.nuts.persistence.resolution;

import uk.dangrew.nuts.store.Database;

public interface ResolutionStrategy {

   public void resolve( Database database );
   
}//End Interface

